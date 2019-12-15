package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import common.oomall.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import xmu.oomall.VO.LoginVo;
import xmu.oomall.VO.ResetVo;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallRole;
import xmu.oomall.domain.MallUser;
import xmu.oomall.service.AdminService;
import xmu.oomall.service.LogService;
import xmu.oomall.service.RoleService;
import xmu.oomall.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    private final static Integer INSERT = 0;
    private final static Integer DELETE = 1;
    private final static Integer UPDATE = 2;
    private final static Integer SELECT = 3;
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
        String userIdStr = request.getHeader("userId");
        if (userIdStr == null) {
            return null;
        }
        return Integer.valueOf(userIdStr);
    }

    // 内部接口

    /**
     * 修改用户返点
     * @param rebate 返点
     * @return 修改结果
     */
    @PutMapping("/user/rebate")
    public Object updateUserRebate(@RequestParam Integer userId,
                                   @RequestParam Integer rebate) {
        if (userId == null || rebate == null) {
            return CommonResult.badArgument();
        }
        return userService.updateRebate(userId, rebate);
    }

    // 管理员

    /**
     * 获取管理员列表
     * @return 管理员列表
     */
    @GetMapping("/admin")
    public Object adminList(HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        writeLog(adminId, request.getHeader("ip"), SELECT, "查看所有管理员", 1, null);
        return CommonResult.success(adminService.list());
    }

    /**
     * 创建一个管理员
     *
     * @param admin 管理员信息
     * @return 新增的admin对象
     */
    @PostMapping("/admin")
    public Object createAdmin(@RequestBody MallAdmin admin,
                              HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (admin == null) {
            return CommonResult.badArgument();
        }

        MallAdmin newAdmin = adminService.add(admin);
        if (newAdmin == null || newAdmin.getId() == null) {
            writeLog(adminId, request.getHeader("ip"), INSERT, "添加管理员", 0, null);
            return CommonResult.updatedDataFailed("已经存在此管理员名字");
        } else {
            newAdmin.setPassword(null);
            writeLog(adminId, request.getHeader("ip"), INSERT, "添加管理员", 1, newAdmin.getId());
            return CommonResult.success(newAdmin);
        }
    }

    /**
     * 查看管理员信息
     *
     * @param id 管理员的id
     * @return admin对象
     */
    @GetMapping("/admin/{id}")
    public Object getAdminInfo(@PathVariable Integer id,
                               HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null) {
            return CommonResult.badArgument();
        }

        MallAdmin admin = adminService.findById(id);
        admin.setPassword(null);
        writeLog(adminId, request.getHeader("ip"), SELECT, "查看管理员", 1, admin.getId());
        return CommonResult.success(admin);
    }

    /**
     * 修改管理员信息
     *
     * @param id 管理员id
     * @param admin 新的管理员信息
     * @return 修改后的admin对象
     */
    @PutMapping("/admin/{id}")
    public Object updateAdmin(@PathVariable Integer id,
                              @RequestBody MallAdmin admin,
                              HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || admin == null) {
            return CommonResult.badArgument();
        }

        MallAdmin newAdmin = adminService.update(id, admin);
        if (newAdmin == null) {
            return CommonResult.updatedDataFailed();
        }

        newAdmin.setPassword(null);

        writeLog(adminId, request.getHeader("ip"), UPDATE, "更新管理员信息", 1, newAdmin.getId());
        return CommonResult.success(newAdmin);
    }

    /**
     * 删除某个管理员
     *
     * @param id 管理员id
     * @return 删除结果
     */
    @DeleteMapping("/admin/{id}")
    public Object delete(@PathVariable Integer id,
                         HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null) {
            return CommonResult.badArgument();
        }

        return CommonResult.success(adminService.delete(id));
    }

    /**
     * 管理员查看自己的信息
     * @return admin对象
     */
    @GetMapping("/admins/info")
    public Object adminInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            return CommonResult.unLogin();
        }
        // 解析token，得到用户的id和role
        Map<String, String> tokenMap = JwtTokenUtil.getMapFromToken(token);
        if (tokenMap == null) {
            return CommonResult.unauthorized(null);
        }
        Integer adminId = Integer.valueOf(tokenMap.get(JwtTokenUtil.CLAIM_KEY_USERID));

        MallAdmin admin = adminService.findById(adminId);
        admin.setPassword(null);

        writeLog(adminId, request.getHeader("ip"), SELECT, "查看自己的信息", 1, admin.getId());
        return CommonResult.success(admin);
    }

    /**
     * 管理员根据请求信息登陆
     * @return admin对象
     */
    @PostMapping("/admins/login")
    public Object adminLogin(@RequestBody LoginVo loginVo,
                             HttpServletRequest request) {
        if (loginVo == null || loginVo.getUsername() == null || loginVo.getPassword() == null) {
            return CommonResult.badArgument();
        }

        String token = adminService.login(loginVo.getUsername(), loginVo.getPassword());
        if (token == null) {
            return CommonResult.failed("账号或密码不正确");
        }
        Map<String, Object> map = new HashMap<>(5);
        map.put("token", token);
        map.put("tokenHead", tokenHead);
        MallAdmin admin = adminService.findByName(loginVo.getUsername());
        admin.setPassword(null);
        map.put("data", admin);

        writeLog(admin.getId(), request.getHeader("ip"), null, "管理员登录", 1, admin.getId());
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

        writeLog(adminId, request.getHeader("ip"), null, "管理员登出", 1, adminId);
        return CommonResult.success(null);
    }

    /**
     * 管理员修改密码
     * @param admin admin对象
     * @return admin对象
     */
    @PutMapping("/admin/password")
    public Object updateAdminPassword(@RequestBody MallAdmin admin,
                                      HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (admin == null || admin.getPassword() == null) {
            return CommonResult.badArgumentValue("密码不能为空");
        }

        MallAdmin newAdmin = adminService.update(adminId, admin);
        if (newAdmin == null) {
            writeLog(adminId, request.getHeader("ip"), UPDATE, "更新密码", 0, adminId);
            return CommonResult.updatedDataFailed();
        }

        newAdmin.setPassword(null);

        writeLog(adminId, request.getHeader("ip"), UPDATE, "更新密码", 1, adminId);
        return CommonResult.success(newAdmin);
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

        writeLog(adminId, request.getHeader("ip"), SELECT, "查看所有角色", 1, null);
        return CommonResult.success(roleService.getAllRoles());
    }

    /**
     * 管理员新建角色
     * @param role role实例
     * @return role的实例
     */
    @PostMapping("/roles")
    public Object addRole(@RequestBody MallRole role,
                          HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (role == null) {
            return CommonResult.badArgument();
        }

        MallRole newRole = roleService.insert(role);
        if (newRole == null) {
            writeLog(adminId, request.getHeader("ip"), SELECT, "创建角色", 0, null);
            return CommonResult.updatedDataFailed();
        }

        writeLog(adminId, request.getHeader("ip"), SELECT, "创建角色", 1, newRole.getId());
        return CommonResult.success(newRole);
    }

    /**
     * 查看单个角色的详细信息
     * @param id role的id
     * @return role实例
     */
    @GetMapping("/roles/{id}")
    public Object getRole(@PathVariable("id") Integer id,
                          HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null) {
            return CommonResult.badArgument();
        }

        writeLog(adminId, request.getHeader("ip"), SELECT, "查看某角色", 1, id);
        return CommonResult.success(roleService.findById(id));
    }

    /**
     * 修改某个role的信息
     * @param id role的id
     * @param role role实例
     * @return 修改后的role
     */
    @PutMapping("/roles/{id}")
    public Object updateRole(@PathVariable("id") Integer id,
                             @RequestBody MallRole role,
                             HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || role == null) {
            return CommonResult.badArgument();
        }

        role.setId(id);
        MallRole newRole = roleService.update(role);

        if (newRole == null) {
            writeLog(adminId, request.getHeader("ip"), UPDATE, "更新角色", 0, id);
            return CommonResult.updatedDataFailed();
        }

        writeLog(adminId, request.getHeader("ip"), UPDATE, "更新角色", 1, id);
        return CommonResult.success(newRole);
    }

    /**
     * 删除某个role
     * @param id role的id
     * @return 删除结果
     */
    @DeleteMapping("/roles/{id}")
    public Object deleteRole(@PathVariable("id") Integer id,
                             HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null) {
            return CommonResult.badArgument();
        }

        boolean ok = roleService.deleteById(id);

        if (!ok) {
            writeLog(adminId, request.getHeader("ip"), DELETE, "删除角色", 0, id);
            return CommonResult.updatedDataFailed();
        }

        writeLog(adminId, request.getHeader("ip"), DELETE, "删除角色", 1, id);
        return CommonResult.success(roleService.deleteById(id));
    }

    /**
     * TODO
     * 修改role权限
     * @param id role的id
     * @return 没有permission表，暂时返回role
     */
    @PutMapping("/roles/{id}/permission")
    public Object updateRolePermission(@PathVariable("id") Integer id) {
        return CommonResult.success(roleService.getPrivilegesByRoleId(id));
    }

    /**
     * 返回一个权限对应的管理员
     * @param id 权限的id
     * @return 管理员的列表
     */
    @GetMapping("/roles/{id}/admins")
    public Object getAdmin(@PathVariable("id") Integer id,
                           HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null) {
            return CommonResult.badArgument();
        }

        writeLog(adminId, request.getHeader("ip"), SELECT, "查看角色下所有管理员", 1, null);
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

        return userService.generateAuthCode(telephone);
    }

    /**
     * 用户账号登录
     * @return 数据是userInfoVo
     */
    @PostMapping("/login")
    public Object userLogin(@RequestParam String username,
                            @RequestParam String password) {
        String token = userService.login(username, password);
        if (token == null) {
            return CommonResult.badArgumentValue("用户名或密码错误");
        }
        Map<String, Object> tokenMap = new HashMap<>(5);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        MallUser user = userService.findByName(username);
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
    public Object reset(@RequestBody ResetVo resetPhoneVo) {
        return userService.updatePassword(resetPhoneVo.getTelephone(), resetPhoneVo.getPassword(), resetPhoneVo.getCode());
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
    public Object resetPhone(@RequestBody ResetVo resetPhoneVo) {
        return userService.updateTelephone(resetPhoneVo.getTelephone(), resetPhoneVo.getPassword(), resetPhoneVo.getCode());
    }

    /**
     * 请求注册验证码
     *
     * 这里需要一定机制防止短信验证码被滥用
     * param body 包括phoneNumber,captchaType
     * @return 验证码
     */
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
     * @param request 请求对象
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
    public Object register(@RequestBody MallUser user,
                           @RequestParam String code,
                           HttpServletRequest request) {
        return userService.register(user, code);
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
        String token = request.getHeader("token");
        if (token == null) {
            return CommonResult.unauthorized(null);
        }
        // 解析token，得到用户的id和role
        Map<String, String> tokenMap = JwtTokenUtil.getMapFromToken(token);
        if (tokenMap == null) {
            return CommonResult.unauthorized(null);
        }
        Integer userId = Integer.valueOf(tokenMap.get(JwtTokenUtil.CLAIM_KEY_USERID));

        MallUser user = userService.findById(userId);
        return CommonResult.success(user);
    }
}
