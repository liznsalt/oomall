package xmu.oomall.controller;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.util.UriUtil;
import xmu.oomall.vo.*;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallRole;
import xmu.oomall.domain.MallUser;
import xmu.oomall.service.AdminService;
import xmu.oomall.service.LogService;
import xmu.oomall.service.RoleService;
import xmu.oomall.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
@RestController
public class UserControllerImpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LogService logService;

    private final static Integer INSERT = 1;
    private final static Integer DELETE = 3;
    private final static Integer UPDATE = 2;
    private final static Integer SELECT = 0;
    private void writeLog(Integer adminId, String ip, Integer type,
                          String actions, Integer statusCode, Integer actionId) {
        Log log = new Log();
        log.setAdminId(adminId);
        log.setIp(ip);
        log.setType(type);
        log.setActions(actions);
        log.setStatusCode(statusCode);
        log.setActionId(actionId);
        logService.addLog(log);
    }

    private Map<String, String> getTokenMap(HttpServletRequest request) {
        String token = request.getHeader(UriUtil.TOKEN_NAME);
        if (token == null) {
            return null;
        }
        return JwtTokenUtil.getMapFromToken(token);
    }

    private Integer getUserId(HttpServletRequest request) {
        String token = request.getHeader(UriUtil.TOKEN_NAME);
        if (token == null) {
            return null;
        }
        // 解析token，得到用户的id和role
        Map<String, String> tokenMap = JwtTokenUtil.getMapFromToken(token);
        if (tokenMap == null) {
            return null;
        }

        return Integer.valueOf(tokenMap.get(JwtTokenUtil.CLAIM_KEY_USERID));
    }

    // 内部接口

    /**
     * 修改用户返点
     * @param rebate 返点
     * @return 修改结果
     */
    @PutMapping("/user/rebate")
    public Object updateUserRebate(@RequestParam Integer userId, @RequestParam Integer rebate) {
        if (userId == null || rebate == null || userId < 0) {
            return ResponseUtil.fail(664, "修改用户信息失败");
        }
        return userService.updateRebate(userId, rebate);
    }

    /**
     * @deprecated 不要这个api了
     * @param userName 用户名
     * @return 用户信息
     */
    @Deprecated
    @GetMapping("/user/username")
    public Object getUser(@RequestParam String userName) {
        if (userName == null) {
            return CommonResult.badArgumentValue();
        }
        return CommonResult.success(userService.findByName(userName));
    }

    @GetMapping("/user/validate")
    public Boolean userValidate(@RequestParam Integer userId) {
        if (userId == null || userId < 0) {
            return false;
        }

        MallUser user = userService.findById(userId);
        return user != null;
    }

    @GetMapping("/in/user")
    public MallUser getUserById(@RequestParam Integer userId) {
        if (userId == null) {
            return null;
        }
        return userService.findById(userId);
    }

    // 管理员

    /**
     * 获取管理员列表
     * @return 管理员列表
     */
    @GetMapping("/admin")
    public Object adminList(@RequestParam(required = false) String adminName,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (page == null || limit == null || page <= 0 || limit <= 0) {
            return ResponseUtil.fail(676, "管理员操作失败");
        }

        List<MallAdmin> admins;

        if (adminName == null) {
            admins = adminService.findAdmins(page, limit);
        } else {
            admins = adminService.list(adminName, page, limit);
        }

        if (admins == null) {
            return ResponseUtil.ok(new ArrayList<MallAdmin>(0));
        }

        for (MallAdmin admin : admins) {
            admin.setPassword(null);
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看所有管理员", 1, null);
        return ResponseUtil.ok(admins);
    }

    /**
     * 创建一个管理员
     *
     * @param admin 管理员信息
     * @return 新增的admin对象
     */
    @PostMapping("/admin")
    public Object createAdmin(@RequestBody MallAdmin admin, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (admin == null || !admin.isValid()) {
            return ResponseUtil.fail(672, "新增管理员失败");
        }

        MallAdmin newAdmin = adminService.add(admin);
        if (newAdmin == null || newAdmin.getId() == null) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), INSERT, "添加管理员", 0, null);
            return ResponseUtil.fail(672, "新增管理员失败");
        } else {
            newAdmin.setPassword(null);
            writeLog(adminId, IpAddressUtil.getIpAddress(request), INSERT, "添加管理员", 1, newAdmin.getId());
            return ResponseUtil.ok(newAdmin);
        }
    }

    /**
     * 查看管理员信息
     *
     * @param id 管理员的id
     * @return admin对象
     */
    @GetMapping("/admin/{id}")
    public Object getAdminInfo(@PathVariable Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (id == null || id < 0) {
            return ResponseUtil.fail(676, "管理员操作失败");
        }

        MallAdmin admin = adminService.findById(id);
        Integer aId = null;
        if (admin != null) {
            admin.setPassword(null);
            aId = admin.getId();
        }
        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看管理员", 1, aId);
        return ResponseUtil.ok(admin);
    }

    /**
     * 修改管理员信息
     *
     * @param id 管理员id
     * @param admin 新的管理员信息
     * @return 修改后的admin对象
     */
    @PutMapping("/admin/{id}")
    public Object updateAdmin(@PathVariable Integer id, @RequestBody MallAdmin admin, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (id == null || id < 0 || admin == null) {
            return ResponseUtil.fail(674, "更新管理员失败");
        }

        // 防止修改密码
        admin.setPassword(null);

        MallAdmin newAdmin = adminService.update(id, admin);
        if (newAdmin == null) {
            return ResponseUtil.fail(674, "更新管理员失败");
        }

        newAdmin.setPassword(null);

        writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新管理员信息", 1, newAdmin.getId());
        return ResponseUtil.ok(newAdmin);
    }

    /**
     * 删除某个管理员
     *
     * @param id 管理员id
     * @return 删除结果
     */
    @DeleteMapping("/admin/{id}")
    public Object delete(@PathVariable Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (id == null || id < 0) {
            return ResponseUtil.fail(673, "删除管理员失败");
        }

        boolean ok = adminService.delete(id);
        if (ok) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), DELETE, "删除管理员", 0, id);
            return ResponseUtil.ok();
        } else {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), DELETE, "删除管理员", 1, id);
            return ResponseUtil.fail(673, "删除管理员失败");
        }
    }

    /**
     * 管理员查看自己的信息
     * @return admin对象
     */
    @GetMapping("/admin/info")
    public Object adminInfo(HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        MallAdmin admin = adminService.findById(adminId);
        admin.setPassword(null);

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看自己的信息", 1, admin.getId());
        return ResponseUtil.ok(admin);
    }

    /**
     * 管理员根据请求信息登陆
     * @return admin对象
     */
    @PostMapping("/admin/login")
    public Object adminLogin(@RequestBody LoginVo loginVo,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        if (loginVo == null || loginVo.getUsername() == null || loginVo.getPassword() == null) {
            return ResponseUtil.fail(676, "管理员操作失败");
        }

        String token = null;
        try {
            token = adminService.login(loginVo.getUsername(), loginVo.getPassword());
        } catch (UsernameNotFoundException e) {
            logger.warn("登录异常:{}", e.getMessage());
            return ResponseUtil.fail(671, "管理员名不存在");
        } catch (BadCredentialsException e) {
            logger.warn("登录异常:{}", e.getMessage());
            return ResponseUtil.fail(670, "管理员密码错误");
        }

        Map<String, Object> map = new HashMap<>(5);
        map.put("token", token);
        map.put(UriUtil.TOKEN_NAME, token);
        map.put("tokenHead", tokenHead);
        MallAdmin admin = adminService.findByName(loginVo.getUsername());
        admin.setPassword(null);
        map.put("data", admin);

        // FIXME
//        writeLog(admin.getId(), IpAddressUtil.getIpAddress(request), null, "管理员登录", 1, admin.getId());

        // 返回token
        response.setHeader(UriUtil.TOKEN_NAME, token);
        return ResponseUtil.ok(map);
    }

    /**
     * 管理员注销
     * @return admin对象
     */
    @PostMapping("/admin/logout")
    public Object adminLogOut(HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), null, "管理员登出", 1, adminId);
        return ResponseUtil.ok();
    }

    /**
     * FIXME
     * 管理员修改密码
     * @return admin对象
     */
    @PutMapping("/admin/password")
    public Object updateAdminPassword(@RequestBody String oldPassword,
                                      @RequestBody String newPassword,
                                      HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (oldPassword == null || newPassword == null) {
            return ResponseUtil.fail(676, "管理员操作失败");
        }

        MallAdmin admin = adminService.findById(adminId);
        if (admin == null) {
            return ResponseUtil.fail(676, "管理员操作失败");
        }

        if (!admin.getPassword().equals(Md5Util.encode(oldPassword))) {
            return ResponseUtil.fail(670, "管理员密码错误");
        }

        // 开始修改
        admin.setPassword(Md5Util.encode(newPassword));
        MallAdmin newAdmin = adminService.update(adminId, admin);
        if (newAdmin == null) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新密码", 0, adminId);
            return ResponseUtil.fail(674, "更新管理员失败");
        }

        newAdmin.setPassword(null);

        writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新密码", 1, adminId);
        return ResponseUtil.ok();
    }


    /**
     * 管理员查看所有角色
     * @return role的列表
     */
    @GetMapping("/roles")
    public Object roleList(HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看所有角色", 1, null);
        return ResponseUtil.ok(roleService.getAllRoles());
    }

    /**
     * 管理员新建角色
     * @param role role实例
     * @return role的实例
     */
    @PostMapping("/roles")
    public Object addRole(@RequestBody MallRole role, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (role == null || role.getName() == null) {
            return ResponseUtil.fail(676, "管理员操作失败");
        }

        MallRole newRole = roleService.insert(role);
        if (newRole == null) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "创建角色", 0, null);
            return ResponseUtil.fail(750, "角色已存在");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "创建角色", 1, newRole.getId());
        return ResponseUtil.ok(newRole);
    }

    /**
     * 查看单个角色的详细信息
     * @param id role的id
     * @return role实例
     */
    @Deprecated
    @GetMapping("/roles/{id}")
    public Object getRole(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (id == null || id <= 0) {
            return ResponseUtil.fail(753, "获取角色失败");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看某角色", 1, id);
        return ResponseUtil.ok(roleService.findById(id));
    }

    /**
     * 修改某个role的信息
     * @param id role的id
     * @param role role实例
     * @return 修改后的role
     */
    @PutMapping("/roles/{id}")
    public Object updateRole(@PathVariable("id") Integer id, @RequestBody MallRole role,
                             HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (id == null || id <= 0 || role == null) {
            return ResponseUtil.fail(752, "更新角色失败");
        }

        role.setId(id);
        MallRole newRole = roleService.update(role);

        if (newRole == null) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新角色", 0, id);
            return ResponseUtil.fail(752, "更新角色失败");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新角色", 1, id);
        return ResponseUtil.ok(newRole);
    }

    /**
     * 删除某个role
     * @param id role的id
     * @return 删除结果
     */
    @DeleteMapping("/roles/{id}")
    public Object deleteRole(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (id == null || MallRole.isCannotDelete(id)) {
            return ResponseUtil.fail(751, "删除角色失败");
        }

        boolean ok = roleService.deleteById(id);

        if (!ok) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), DELETE, "删除角色", 0, id);
            return ResponseUtil.fail(751, "删除角色失败");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), DELETE, "删除角色", 1, id);
        return ResponseUtil.ok();
    }

    /**
     * 获得role权限
     * @param id role的id
     * @return 没有permission表，暂时返回role
     */
    @GetMapping("/roles/{id}/privileges")
    public Object getRolePermission(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (id == null || id <= 0) {
            return ResponseUtil.fail(676, "管理员操作失败");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看角色的权限", 1, null);
        return ResponseUtil.ok(roleService.getPrivilegesByRoleId(id));
    }

    /**
     * 修改role权限
     * @param id role的id
     * @return /
     */
    @PutMapping("/roles/{id}/privileges")
    public Object updateRolePermission(@PathVariable("id") Integer id,
                                       @RequestBody List<MallPrivilege> privileges,
                                       HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (id == null || id <= 0) {
            return ResponseUtil.fail(752, "更新角色失败");
        }
        if (privileges == null) {
            return ResponseUtil.fail(752, "更新角色失败");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新角色权限", 1, null);
        return ResponseUtil.ok(roleService.updatePrivilegesByRoleId(id, privileges));
    }

    /**
     * 返回一个权限对应的管理员
     * @param id 权限的id
     * @return 管理员的列表
     */
    @Deprecated
    @GetMapping("/roles/{id}/admins")
    public Object getAdmin(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(668, "管理员未登录");
        }

        // 参数校验
        if (id == null || id <= 0) {
            return CommonResult.badArgumentValue();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看角色下所有管理员", 1, null);
        return ResponseUtil.ok(roleService.findAdmins(id));
    }

    @GetMapping("/admin/users")
    public Object getAllUsers(@RequestParam(required = false) String userName,
                              @RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer limit,
                              HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669, "管理员未登录");
        }

        // 参数校验
        if (page == null || limit == null || page <= 0 || limit <= 0) {
            return ResponseUtil.fail(668, "获取用户信息失败");
        }

        List<MallUser> users;

        if (userName == null) {
            users = userService.findUsers(page, limit);
        } else {
            users = userService.listByCondition(userName, page, limit);
        }

        if (users == null) {
            return ResponseUtil.ok(new ArrayList<MallUser>(0));
        }

        // 不返回密码
        for (MallUser user : users) {
            user.setPassword(null);
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看用户", 1, null);
        return ResponseUtil.ok(users);
    }


    // 用户

    /**
     * 请求验证码
     *
     * 这里需要一定机制防止短信验证码被滥用
     * @return 验证码captcha
     */
    @PostMapping("/captcha")
    public Object captcha(@RequestBody String telephone) {
        // 参数校验
        if (telephone == null) {
            // TODO
            return ResponseUtil.fail();
        }

        String code = userService.generateAuthCode(telephone);
        if (code == null) {
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok(code);
    }

    /**
     * 用户账号登录
     * @return 数据是userInfoVo
     */
    @PostMapping("/login")
    public Object userLogin(@RequestBody LoginVo loginVo,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        // 参数校验
        if (loginVo == null) {
            return CommonResult.badArgumentValue("loginVo为空");
        }
        if (loginVo.getUsername() == null) {
            return CommonResult.badArgumentValue("username不能为空");
        }
        if (loginVo.getPassword() == null) {
            return CommonResult.badArgumentValue("password不能为空");
        }

        String token = null;
        try {
            token = userService.login(loginVo.getUsername(), loginVo.getPassword(), request);
        } catch (UsernameNotFoundException e) {
            logger.warn("登录异常:{}", e.getMessage());
            return ResponseUtil.fail(663, "用户名不存在");
        } catch (BadCredentialsException e) {
            logger.warn("登录异常:{}", e.getMessage());
            return ResponseUtil.fail(664, "登录密码错误");
        }

        Map<String, Object> tokenMap = new HashMap<>(5);
        tokenMap.put("token", token);
        tokenMap.put(UriUtil.TOKEN_NAME, token);
        tokenMap.put("tokenHead", tokenHead);
        MallUser user = userService.findByName(loginVo.getUsername());
        user.setPassword(null);
        tokenMap.put("data", user);
        // 设置token
        response.setHeader(UriUtil.TOKEN_NAME, token);
        return ResponseUtil.ok(tokenMap);
    }

    /**
     * 账号密码重置
     *
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PutMapping("/password")
    public Object reset(@RequestBody ResetPasswordVo resetPasswordVo) {
        // 参数校验
        if (resetPasswordVo == null
                || resetPasswordVo.getTelephone() == null
                || resetPasswordVo.getPassword() == null
                || resetPasswordVo.getCode() == null) {
            return ResponseUtil.fail(665, "修改用户信息失败");
        }

        return userService.updatePassword(resetPasswordVo.getTelephone(), resetPasswordVo.getPassword(),
                resetPasswordVo.getCode());
    }

    /**
     * 账号手机号码重置
     *
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PutMapping("/phone")
    public Object resetPhone(@RequestBody ResetPhoneVo resetPhoneVo) {
        // 参数校验
        if (resetPhoneVo == null
                || resetPhoneVo.getTelephone() == null
                || resetPhoneVo.getPassword() == null
                || resetPhoneVo.getCode() == null
                || resetPhoneVo.getNewTelephone() == null) {
            return ResponseUtil.fail(665, "修改用户信息失败");
        }

        return userService.updateTelephone(resetPhoneVo.getTelephone(), resetPhoneVo.getPassword(),
                resetPhoneVo.getCode(), resetPhoneVo.getNewTelephone());
    }

    /**
     * 请求注册验证码
     *
     * 这里需要一定机制防止短信验证码被滥用
     * param body 包括phoneNumber,captchaType
     * @deprecated
     * @return 验证码
     */
    @Deprecated
    @PostMapping("/regCaptcha")
    public Object registerCaptcha(@RequestParam String telephone) {
        return userService.generateAuthCode(telephone);
    }

    /**
     * 用户账号注册
     *
     *     请求内容
     *                {
     *                username: xxx,
     *                password: xxx,
     *                mobile: xxx
     *                wxCode: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @return 注册结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * token: xxx,
     * tokenExpire: xxx,
     * userInfo: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("/register")
    public Object register(@RequestBody UserRegisterVo userRegisterVo) {
        // 参数校验
        if (userRegisterVo == null
                || userRegisterVo.getUsername() == null
                || userRegisterVo.getPassword() == null
                || userRegisterVo.getTelephone() == null
                || userRegisterVo.getCode() == null) {
            // TODO
            return CommonResult.badArgument("vo缺失属性");
        }
        logger.debug("code:{}", userRegisterVo.getCode());
        return userService.register(userRegisterVo.getUsername(), userRegisterVo.getPassword(),
                userRegisterVo.getTelephone(), userRegisterVo.getCode());
    }


    /**
     * 用户个人页面数据
     *
     * 目前是用户订单统计信息
     * 具体的userId去解析token获得
     * @return 用户个人页面数据userInfoVo
     */
    @GetMapping("/user")
    public Object userInfo(HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return ResponseUtil.fail(660, "用户未登录");
        }

        MallUser user = userService.findById(userId);
        user.setPassword(null);
        return ResponseUtil.ok(user);
    }
}
