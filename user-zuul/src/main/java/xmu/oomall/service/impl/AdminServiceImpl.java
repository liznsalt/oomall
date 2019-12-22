package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
import common.oomall.util.JwtTokenUtil;
import common.oomall.util.Md5Util;
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
import xmu.oomall.domain.MallRole;
import xmu.oomall.domain.details.MallMemberDetails;
import xmu.oomall.mapper.AdminMapper;
import xmu.oomall.mapper.RoleMapper;
import xmu.oomall.service.AdminService;
import xmu.oomall.service.RedisService;
import xmu.oomall.service.RoleService;

import java.time.LocalDateTime;
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
    private RoleMapper roleMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RoleService roleService;

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
        return new MallMemberDetails(admin);
    }

    @Override
    public MallMember findMemberByName(String username) {
        MallAdmin admin = findByName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new MallMember(admin);
    }

    @Override
    public boolean delete(Integer id) {
        int num = adminMapper.deleteById(id);
        return num > 0;
    }

    @Override
    public MallAdmin update(Integer id, MallAdmin admin) {
        admin.setId(id);
        // 查看role id是否存在
        // admin role id 为空代表不更新此项，不空则检查
        if (admin.getRoleId() != null) {
            MallRole role = roleMapper.findById(admin.getId());
            // role不存在
            if (role == null) {
                return null;
            }
        }
        // 检查用户名是否已经存在
        List<MallAdmin> dbAdmin = adminMapper.findByName(admin.getUsername());
        // 数据库中的admin 那么和此项不一样，不更新
        if (dbAdmin != null && dbAdmin.size() > 0 && !dbAdmin.get(0).getId().equals(id)) {
            return null;
        }
        // 更新
        int count = adminMapper.updateAdmin(admin);
        return count == 0 ? null : admin;
    }

    @Override
    public MallAdmin add(MallAdmin admin) {
        MallAdmin mallAdmin = findByName(admin.getUsername());
        if (mallAdmin != null) {
            // 已经存在此用户
            LOGGER.info("已经存在该用户");
            return null;
        }
        // 检查role是否存在
        MallRole role = roleService.findById(admin.getRoleId());
        if (role == null) {
            LOGGER.info("角色不存在");
            return null;
        }
        // 加密密码
        admin.setPassword(Md5Util.encode(admin.getPassword()));
        admin.setGmtCreate(LocalDateTime.now());
        admin.setBeDeleted(false);
        int count = adminMapper.addAdmin(admin);
        return count == 0 ? null : admin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        MallAdmin admin = findByName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        MallMember member = new MallMember(admin);
        if (!member.getPassword().equals(Md5Util.encode(password))) {
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(member.getUserDetails(),
                        null, member.getUserDetails().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = JwtTokenUtil.generateToken(member.generateClaims());
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

    @Override
    public List<MallAdmin> list(String adminName, Integer page, Integer limit) {
        return adminMapper.getByCondition(adminName, page, limit);
    }
}
