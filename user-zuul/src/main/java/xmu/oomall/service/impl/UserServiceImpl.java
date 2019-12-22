package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
import common.oomall.util.IpAddressUtil;
import common.oomall.util.JwtTokenUtil;
import common.oomall.util.Md5Util;
import common.oomall.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xmu.oomall.domain.MallMember;
import xmu.oomall.domain.MallRole;
import xmu.oomall.domain.MallUser;
import xmu.oomall.domain.details.MallMemberDetails;
import xmu.oomall.mapper.UserMapper;
import xmu.oomall.service.RedisService;
import xmu.oomall.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author liznsalt
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

//    @Value("${redis.key.user.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE = "user:authCode:";

//    @Value("${redis.key.user.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS = 180L;

    @Override
    public Object register(String username, String password, String telephone, String authCode) {
        // 验证验证码
        if (!verifyAuthCode(authCode, telephone)) {
            return ResponseUtil.fail(667, "验证码错误");
        }
        // 查询是否已经有该用户
        MallUser user1 = findByName(username);
        if (user1 != null) {
            return ResponseUtil.fail(661, "用户名已被注册");
        }
        MallUser user2 = findByTelephone(telephone);
        if (user2 != null) {
            return ResponseUtil.fail(662, "手机号已被注册");
        }
        // 没有用户的话进行注册
        MallUser newUser = new MallUser();
        newUser.setName(username);
        newUser.setMobile(telephone);
        newUser.setPassword(Md5Util.encode(password));
        newUser.setRoleId(MallRole.USER);
        newUser.setRebate(0);
        newUser.setUserLevel(0);
        newUser.setBeDeleted(false);
        // 添加进数据库
        int count = userMapper.addUser(newUser);
        if (count == 0) {
            return CommonResult.serious();
        } else {
            newUser.setPassword(null);
            return ResponseUtil.ok(newUser);
        }
    }

    @Override
    public CommonResult register(MallUser user, String authCode) {
        // 验证验证码
        if (!verifyAuthCode(authCode, user.getMobile())) {
            return CommonResult.codeError();
        }
        // 查询是否已经有该用户
        MallUser user1 = findByName(user.getName());
        MallUser user2 = findByTelephone(user.getMobile());
        if (user1 != null || user2 != null) {
            return CommonResult.failed("该用户已经存在");
        }
        // 添加进数据库
        user.setRebate(0);
        userMapper.addUser(user);
        user.setPassword(null);
        user.setRoleId(MallRole.USER);
        // 成功
        return CommonResult.success(user, "注册成功");
    }

    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        // 验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        LOGGER.debug("generate key:{} code: {} ", REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return sb.toString();
    }

    @Override
    public Object updatePassword(String telephone, String password, String authCode) {
        MallUser user = findByTelephone(telephone);
        if (user == null) {
            return ResponseUtil.fail(665, "修改用户信息失败");
        }
        // 验证验证码
        if (!verifyAuthCode(authCode,telephone)) {
            return ResponseUtil.fail(667, "验证码错误");
        }
        // md5加密
        user.setPassword(Md5Util.encode(password));
        int count = userMapper.updateUser(user);
        if (count == 0) {
            return ResponseUtil.fail(665, "修改用户信息失败");
        } else {
            user.setPassword(null);
            return ResponseUtil.ok(user);
        }
    }

    @Override
    public Object updateTelephone(String telephone, String password, String authCode, String newPhone) {
        MallUser user = findByTelephone(telephone);
        if (user == null) {
            return ResponseUtil.fail(665, "修改用户信息失败");
        }
        // 验证验证码
        if (!verifyAuthCode(authCode,telephone)) {
            return ResponseUtil.fail(667, "验证码错误");
        }
        // 检查
        MallUser mallUser = findByTelephone(newPhone);
        if (mallUser != null) {
            return ResponseUtil.fail(662, "手机号已被注册");
        }
        // 成功
        user.setMobile(newPhone);
        userMapper.updateUser(user);
        user.setPassword(null);
        return ResponseUtil.ok(user);
    }

    @Override
    public Object updateRebate(Integer userId, Integer rebate) {
        MallUser user = findById(userId);
        if (user == null) {
            return ResponseUtil.fail(665, "修改用户信息失败");
        }
        if (user.getRebate() == null) {
            user.setRebate(0);
        }
        user.setRebate(user.getRebate() + rebate);
        int count = userMapper.updateUser(user);
        if (count >= 1) {
            return ResponseUtil.ok(rebate);
        } else {
            return ResponseUtil.fail(665, "修改用户信息失败");
        }
    }

    @Override
    public String login(String username,
                        String password,
                        HttpServletRequest request) throws AuthenticationException {
        String token = null;
        // 找到用户
        MallUser user = findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // security 登录 生成 token
        MallMember member = new MallMember(user);
        // 比较密码摘要
        if (!member.getPassword().equals(Md5Util.encode(password))) {
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                member.getUserDetails(),
                null,
                member.getUserDetails().getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = JwtTokenUtil.generateToken(member.generateClaims());

        // 生成结束 修改登录时间 ip
        user.setLastLoginIp(IpAddressUtil.getIpAddress(request));
        user.setLastLoginTime(LocalDateTime.now());
        // 更新用户 应该不会失败
        userMapper.updateUser(user);

        return token;
    }

    @Override
    public String refreshToken(String token) {
        return JwtTokenUtil.refreshHeadToken(token);
    }

    // find

    @Override
    public MallUser findByName(String username) {
        List<MallUser> userList =  userMapper.findByName(username);
        if (userList == null || userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public MallUser findByTelephone(String telephone) {
        List<MallUser> userList =  userMapper.findByTelephone(telephone);
        if (userList == null || userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public MallUser findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public List<MallUser> findUsers(Integer page, Integer limit) {
        return userMapper.findByCondition(page, limit);
    }

    @Override
    public MallMemberDetails findDetailsByName(String username) {
        MallUser user = findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new MallMemberDetails(user);
    }

    @Override
    public MallMember findMemberByName(String username) {
        MallUser user = findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new MallMember(user);
    }

    @Override
    public List<MallUser> list() {
        return userMapper.getAllUsers();
    }

    @Override
    public List<MallUser> listByCondition(String username, Integer page, Integer limit) {
        return userMapper.getUsersByCondition(username, page, limit);
    }

    // util

    /**
     * 对输入的验证码进行校验
     */
    private boolean verifyAuthCode(String authCode, String telephone) {
        if (StringUtils.isEmpty(authCode)) {
            LOGGER.info("authCode empty");
            return false;
        }
        LOGGER.debug("code key:{}", REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        LOGGER.info("realAuthCode:{}", realAuthCode);
        return authCode.equals(realAuthCode);
    }
}
