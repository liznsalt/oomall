package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
import common.oomall.util.IpAddressUtil;
import common.oomall.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xmu.oomall.domain.MallMember;
import xmu.oomall.domain.MallUser;
import xmu.oomall.domain.details.MallMemberDetails;
import xmu.oomall.mapper.UserMapper;
import xmu.oomall.service.RedisService;
import xmu.oomall.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * @author liznsalt
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Value("${redis.key.user.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Value("${redis.key.user.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public CommonResult register(String username, String password, String telephone, String authCode) {
        // 验证验证码
        if (!verifyAuthCode(authCode, telephone)) {
            return CommonResult.badArgumentValue("验证码错误");
        }
        // 查询是否已经有该用户
        MallUser user1 = findByName(username);
        if (user1 != null) {
            return CommonResult.badArgumentValue("该用户名已被占用");
        }
        MallUser user2 = findByTelephone(telephone);
        if (user2 != null) {
            return CommonResult.badArgumentValue("该手机已经被注册");
        }
        //没有用户的话进行注册
        MallUser newUser = new MallUser();
        newUser.setName(username);
        newUser.setMobile(telephone);
        newUser.setPassword(password);
        // 添加进数据库
        int count = userMapper.addUser(newUser);
        if (count == 0) {
            return CommonResult.serious();
        } else {
            newUser.setPassword(null);
            return CommonResult.success(newUser, "注册成功");
        }
    }

    @Override
    public CommonResult register(MallUser user, String authCode) {
        // 验证验证码
        if (!verifyAuthCode(authCode, user.getMobile())) {
            return CommonResult.failed("验证码错误");
        }
        // 查询是否已经有该用户
        MallUser user1 = findByName(user.getName());
        MallUser user2 = findByTelephone(user.getMobile());
        if (user1 != null || user2 != null) {
            return CommonResult.failed("该用户已经存在");
        }
        // 添加进数据库
        userMapper.addUser(user);
        user.setPassword(null);
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
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return sb.toString();
    }

    @Override
    public CommonResult updatePassword(String telephone, String password, String authCode) {
        MallUser user = findByTelephone(telephone);
        if (user == null) {
            return CommonResult.badArgumentValue("该手机号没注册过");
        }
        //验证验证码
        if (!verifyAuthCode(authCode,telephone)) {
            return CommonResult.badArgumentValue("验证码错误");
        }
        user.setPassword(password);
        int count = userMapper.updateUser(user);
        if (count == 0) {
            return CommonResult.updatedDataFailed("更新失败");
        } else {
            user.setPassword(null);
            return CommonResult.success(user);
        }
    }

    @Override
    public CommonResult updateTelephone(String telephone, String password, String authCode, String newPhone) {
        MallUser user = findByTelephone(telephone);
        if (user == null) {
            return CommonResult.failed("该手机号没注册过");
        }
        //验证验证码
        if (!verifyAuthCode(authCode,telephone)) {
            return CommonResult.failed("验证码错误");
        }
        user.setMobile(newPhone);
        userMapper.updateUser(user);
        user.setPassword(null);
        return CommonResult.success(user, "手机号码修改成功");
    }

    @Override
    public CommonResult updateRebate(Integer userId, Integer rebate) {
        MallUser user = findById(userId);
        if (user == null) {
            return CommonResult.failed("该账号不存在");
        }
        int oldRebate = user.getRebate();
        user.setRebate(rebate);
        int count = userMapper.updateUser(user);
        if (count >= 1) {
            return CommonResult.success(rebate);
        } else {
            return CommonResult.failed("修改失败");
        }
    }

    @Override
    public String login(String username, String password, HttpServletRequest request) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            // 找到用户
            MallUser user = findByName(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            // security 登录 生成 token
            MallMember member = new MallMember(user);
            if (!password.equals(member.getPassword())) {
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
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
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

    // util

    /**
     * 对输入的验证码进行校验
     */
    private boolean verifyAuthCode(String authCode, String telephone){
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }
}
