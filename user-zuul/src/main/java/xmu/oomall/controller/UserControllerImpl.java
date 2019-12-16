package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import common.oomall.util.IpAddressUtil;
import common.oomall.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.vo.LoginVo;
import xmu.oomall.vo.ResetPasswordVo;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallRole;
import xmu.oomall.domain.MallUser;
import xmu.oomall.service.AdminService;
import xmu.oomall.service.LogService;
import xmu.oomall.service.RoleService;
import xmu.oomall.service.UserService;
import xmu.oomall.vo.ResetPhoneVo;
import xmu.oomall.vo.UserRegisterVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
@RestController
@RequestMapping("/userInfoService")
public class UserControllerImpl {

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
        String token = request.getHeader("token");
        if (token == null) {
            return null;
        }
        return JwtTokenUtil.getMapFromToken(token);
    }

    private Integer getUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
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
            return CommonResult.badArgumentValue();
        }
        return userService.updateRebate(userId, rebate);
    }

    /**
     * @deprecated 不要这个api了
     * @param userName 用户名
     * @return 用户信息
     */
    @GetMapping("/user/username")
    public Object getUser(@RequestParam String userName) {
        if (userName == null) {
            return CommonResult.badArgumentValue();
        }
        return CommonResult.success(userService.findByName(userName));
    }

    @GetMapping("/user/validate")
    public Object userValidate(@RequestParam Integer userId) {
        if (userId == null || userId < 0) {
            return CommonResult.badArgumentValue();
        }

        MallUser user = userService.findById(userId);
        if (user == null) {
            return CommonResult.success(false);
        } else {
            return CommonResult.success(true);
        }
    }

    // 管理员

    /**
     * TODO
     * 获取管理员列表
     * @return 管理员列表
     */
    @GetMapping("/admins")
    public Object adminList(@RequestParam String adminName,
                            @RequestParam Integer page,
                            @RequestParam Integer limit,
                            HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (page == null || limit == null || page <= 0 || limit <= 0) {
            return CommonResult.badArgumentValue();
        }

        List<MallAdmin> admins = adminService.list();
        for (MallAdmin admin : admins) {
            admin.setPassword(null);
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看所有管理员", 1, null);
        return CommonResult.success(admins);
    }

    /**
     * 创建一个管理员
     *
     * @param admin 管理员信息
     * @return 新增的admin对象
     */
    @PostMapping(value = {"/admins", "/admin"})
    public Object createAdmin(@RequestBody MallAdmin admin, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (admin == null || admin.getUsername() == null || admin.getPassword() == null || admin.getRoleId() == null) {
            return CommonResult.badArgumentValue();
        }

        MallAdmin newAdmin = adminService.add(admin);
        if (newAdmin == null || newAdmin.getId() == null) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), INSERT, "添加管理员", 0, null);
            return CommonResult.updatedDataFailed("已经存在此管理员名字");
        } else {
            newAdmin.setPassword(null);
            writeLog(adminId, IpAddressUtil.getIpAddress(request), INSERT, "添加管理员", 1, newAdmin.getId());
            return CommonResult.success(newAdmin);
        }
    }

    /**
     * 查看管理员信息
     *
     * @param id 管理员的id
     * @return admin对象
     */
    @GetMapping(value = {"/admins/{id}", "/admin/{id}"})
    public Object getAdminInfo(@PathVariable Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        MallAdmin admin = adminService.findById(id);
        admin.setPassword(null);
        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看管理员", 1, admin.getId());
        return CommonResult.success(admin);
    }

    /**
     * 修改管理员信息
     *
     * @param id 管理员id
     * @param admin 新的管理员信息
     * @return 修改后的admin对象
     */
    @PutMapping(value = {"/admins/{id}", "/admin/{id}"})
    public Object updateAdmin(@PathVariable Integer id, @RequestBody MallAdmin admin, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0 || admin == null) {
            return CommonResult.badArgumentValue();
        }

        MallAdmin newAdmin = adminService.update(id, admin);
        if (newAdmin == null) {
            return CommonResult.updatedDataFailed();
        }

        newAdmin.setPassword(null);

        writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新管理员信息", 1, newAdmin.getId());
        return CommonResult.success(newAdmin);
    }

    /**
     * 删除某个管理员
     *
     * @param id 管理员id
     * @return 删除结果
     */
    @DeleteMapping(value = {"/admins/{id}", "/admin/{id}"})
    public Object delete(@PathVariable Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        return CommonResult.success(adminService.delete(id));
    }

    /**
     * 管理员查看自己的信息
     * @return admin对象
     */
    @GetMapping("/admins/info")
    public Object adminInfo(HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        MallAdmin admin = adminService.findById(adminId);
        admin.setPassword(null);

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看自己的信息", 1, admin.getId());
        return CommonResult.success(admin);
    }

    /**
     * 管理员根据请求信息登陆
     * @return admin对象
     */
    @PostMapping("/admins/login")
    public Object adminLogin(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        if (loginVo == null) {
            return CommonResult.badArgumentValue("loginVo为空");
        }
        if (loginVo.getUsername() == null) {
            return CommonResult.badArgumentValue("username不能为空");
        }
        if (loginVo.getPassword() == null) {
            return CommonResult.badArgumentValue("password不能为空");
        }

        String token = adminService.login(loginVo.getUsername(), loginVo.getPassword());
        if (token == null) {
            return CommonResult.badArgumentValue("账号或密码不正确");
        }
        Map<String, Object> map = new HashMap<>(5);
        map.put("token", token);
        map.put("tokenHead", tokenHead);
        MallAdmin admin = adminService.findByName(loginVo.getUsername());
        admin.setPassword(null);
        map.put("data", admin);

        writeLog(admin.getId(), IpAddressUtil.getIpAddress(request), null, "管理员登录", 1, admin.getId());
        return CommonResult.success(map);
    }

    /**
     * 管理员注销
     * @return admin对象
     */
    @PostMapping("/admins/logout")
    public Object adminLogOut(HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), null, "管理员登出", 1, adminId);
        return CommonResult.success(null);
    }

    /**
     * 管理员修改密码
     * @return admin对象
     */
    @PutMapping(value = {"/admins/password", "/admin/password"})
    public Object updateAdminPassword(@RequestParam String oldPassword,
                                      @RequestParam String newPassword,
                                      HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (oldPassword == null || newPassword == null) {
            return CommonResult.badArgumentValue("密码不能为空");
        }

        MallAdmin admin = adminService.findById(adminId);
        if (admin == null) {
            return CommonResult.updatedDataFailed("用户已经不存在");
        }

        MallAdmin newAdmin = adminService.update(adminId, admin);
        if (newAdmin == null) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新密码", 0, adminId);
            return CommonResult.updatedDataFailed();
        }

        newAdmin.setPassword(null);

        writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新密码", 1, adminId);
        return CommonResult.success();
    }


    /**
     * 管理员查看所有角色
     * @return role的列表
     */
    @GetMapping("/roles")
    public Object roleList(HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看所有角色", 1, null);
        return CommonResult.success(roleService.getAllRoles());
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
            return CommonResult.unLogin();
        }

        // 参数校验
        if (role == null) {
            return CommonResult.badArgumentValue();
        }

        MallRole newRole = roleService.insert(role);
        if (newRole == null) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "创建角色", 0, null);
            return CommonResult.updatedDataFailed();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "创建角色", 1, newRole.getId());
        return CommonResult.success(newRole);
    }

    /**
     * 查看单个角色的详细信息
     * @param id role的id
     * @return role实例
     */
    @GetMapping("/roles/{id}")
    public Object getRole(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看某角色", 1, id);
        return CommonResult.success(roleService.findById(id));
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
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0 || role == null) {
            return CommonResult.badArgumentValue();
        }

        role.setId(id);
        MallRole newRole = roleService.update(role);

        if (newRole == null) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新角色", 0, id);
            return CommonResult.updatedDataFailed();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新角色", 1, id);
        return CommonResult.success(newRole);
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
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        boolean ok = roleService.deleteById(id);

        if (!ok) {
            writeLog(adminId, IpAddressUtil.getIpAddress(request), DELETE, "删除角色", 0, id);
            return CommonResult.failed();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), DELETE, "删除角色", 1, id);
        return CommonResult.success();
    }

    /**
     * 修改role权限
     * @param id role的id
     * @return 没有permission表，暂时返回role
     */
    @GetMapping("/roles/{id}/privileges")
    public Object getRolePermission(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看角色的权限", 1, null);
        return CommonResult.success(roleService.getPrivilegesByRoleId(id));
    }

    /**
     * 修改role权限
     * @param id role的id
     * @return 没有permission表，暂时返回role
     */
    @PutMapping("/roles/{id}/privileges")
    public Object updateRolePermission(@PathVariable("id") Integer id,
                                       @RequestBody List<MallPrivilege> privileges,
                                       HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }
        if (privileges == null) {
            return CommonResult.badArgument("privileges不能为空，如果是空列表请传入空列表[]");
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), UPDATE, "更新角色权限", 1, null);
        return CommonResult.success(roleService.updatePrivilegesByRoleId(id, privileges));
    }

    /**
     * 返回一个权限对应的管理员
     * @param id 权限的id
     * @return 管理员的列表
     */
    @GetMapping("/roles/{id}/admins")
    public Object getAdmin(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        writeLog(adminId, IpAddressUtil.getIpAddress(request), SELECT, "查看角色下所有管理员", 1, null);
        return CommonResult.success(roleService.findAdmins(id));
    }


    // 用户

    /**
     * 请求验证码
     *
     * 这里需要一定机制防止短信验证码被滥用
     * @return 验证码captcha
     */
    @PostMapping("/captcha")
    public Object captcha(@RequestParam String telephone) {
        // 参数校验
        if (telephone == null) {
            return CommonResult.badArgument("telephone为空");
        }

        String code = userService.generateAuthCode(telephone);
        if (code == null) {
            return CommonResult.serious();
        }
        return CommonResult.success(code);
    }

    /**
     * 用户账号登录
     * @return 数据是userInfoVo
     */
    @PostMapping("/login")
    public Object userLogin(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        // 参数校验
        if (loginVo == null) {
            return CommonResult.badArgument("loginVo为空");
        }
        if (loginVo.getUsername() == null) {
            return CommonResult.badArgumentValue("username不能为空");
        }
        if (loginVo.getPassword() == null) {
            return CommonResult.badArgumentValue("password不能为空");
        }

        String token = userService.login(loginVo.getUsername(), loginVo.getPassword(), request);
        if (token == null) {
            return CommonResult.badArgumentValue("用户名或密码错误");
        }

        Map<String, Object> tokenMap = new HashMap<>(5);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        MallUser user = userService.findByName(loginVo.getUsername());
        tokenMap.put("data", user);
        return CommonResult.success(tokenMap);
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
        if (resetPasswordVo == null) {
            return CommonResult.badArgument("resetPasswordVo为空");
        }
        if (resetPasswordVo.getTelephone() == null) {
            return CommonResult.badArgumentValue("手机号为空");
        }
        if (resetPasswordVo.getPassword() == null) {
            return CommonResult.badArgumentValue("密码为空");
        }
        if (resetPasswordVo.getCode() == null) {
            return CommonResult.badArgumentValue("验证码为空");
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
        if (resetPhoneVo == null) {
            return CommonResult.badArgument("resetPasswordVo为空");
        }
        if (resetPhoneVo.getTelephone() == null) {
            return CommonResult.badArgumentValue("手机号为空");
        }
        if (resetPhoneVo.getPassword() == null) {
            return CommonResult.badArgumentValue("密码为空");
        }
        if (resetPhoneVo.getCode() == null) {
            return CommonResult.badArgumentValue("验证码为空");
        }
        if (resetPhoneVo.getNewTelephone() == null) {
            return CommonResult.badArgumentValue("新的手机号为空");
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
//    @PostMapping("/regCaptcha")
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
        if (userRegisterVo == null) {
            return CommonResult.badArgument("vo为空");
        }
        if (userRegisterVo.getUsername() == null) {
            return CommonResult.badArgumentValue("用户名为空");
        }
        if (userRegisterVo.getPassword() == null) {
            return CommonResult.badArgumentValue("密码为空");
        }
        if (userRegisterVo.getTelephone() == null) {
            return CommonResult.badArgumentValue("手机号为空");
        }
        if (userRegisterVo.getCode() == null) {
            return CommonResult.badArgumentValue("验证码为空");
        }

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
            return CommonResult.unLogin();
        }

        MallUser user = userService.findById(userId);
        return CommonResult.success(user);
    }
}
