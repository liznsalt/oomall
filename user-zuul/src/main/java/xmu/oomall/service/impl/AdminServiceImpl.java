package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
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
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallMember;
import xmu.oomall.domain.details.MallMemberDetails;
import xmu.oomall.mapper.AdminMapper;
import xmu.oomall.service.AdminService;
import xmu.oomall.service.RedisService;

import java.util.List;

/**
 * @author liznsalt
 */
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisService redisService;

    @Value("${redis.key.admin.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Value("${redis.key.admin.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;


    @Override
    public MallAdmin findById(Integer id) {
        return adminMapper.findById(id);
    }

    @Override
    public MallAdmin findByName(String name) {
        List<MallAdmin> admins = adminMapper.findByName(name);
        if (admins == null || admins.isEmpty()) {
            return null;
        }
        return admins.get(0);
    }

    @Override
    public List<MallAdmin> findAdmins(Integer page, Integer limit) {
        return adminMapper.findByCondition(page, limit);
    }

    @Override
    public MallMemberDetails findDetailsByName(String username) {
        MallAdmin admin = findByName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return  new MallMemberDetails(admin);
    }

    @Override
    public MallMember findMemberByName(String username) {
        MallAdmin admin = findByName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return  new MallMember(admin);
    }

    @Override
    public boolean delete(Integer id) {
        int num = adminMapper.deleteById(id);
        return num > 0;
    }

    @Override
    public MallAdmin update(Integer id, MallAdmin admin) {
        admin.setId(id);
        adminMapper.updateAdmin(admin);
        return admin;
    }

    @Override
    public MallAdmin add(MallAdmin admin) {
        MallAdmin mallAdmin = findByName(admin.getUsername());
        if (mallAdmin != null) {
            // 已经存在此用户
            return null;
        }
        adminMapper.addAdmin(admin);
        return admin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        // try
        try {
            MallMember member = findMemberByName(username);
            if (!password.equals(member.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(member.getUserDetails(),
                            null, member.getUserDetails().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = JwtTokenUtil.generateToken(member.generateClaims());
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public CommonResult logout(String username) {
        return CommonResult.success(null, "退出成功");
    }

    @Override
    public String refreshToken(String token) {
        return JwtTokenUtil.refreshHeadToken(token);
    }

    @Override
    public List<MallAdmin> list() {
        return adminMapper.getAllAdmins();
    }
}
