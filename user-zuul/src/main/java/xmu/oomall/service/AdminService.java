package xmu.oomall.service;

import common.oomall.api.CommonResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallMember;
import xmu.oomall.domain.details.MallMemberDetails;

import java.util.List;

/**
 * @author liznsalt
 */
public interface AdminService {
    /**
     * 通过id寻找admin
     * @param id admin id
     * @return 管理员
     */
    MallAdmin findById(Integer id);

    /**
     * 通过name查找admin
     * @param name admin name
     * @return 管理员
     */
    MallAdmin findByName(String name);

    /**
     * 查找管理员 分页
     * @param page 第几页
     * @param limit 每页行数
     * @return 管理员列表
     */
    List<MallAdmin> findAdmins(Integer page, Integer limit);

    /**
     * 通过名字查找管理员，封装成detail
     * @see AdminService#findByName(String)
     * @see org.springframework.security.core.userdetails.UserDetails
     * @param username 管理员名字
     * @return 管理员detail
     */
    MallMemberDetails findDetailsByName(String username);

    /**
     * 通过名字查找管理员，封装成该模块统一member
     * @see AdminService#findByName(String)
     * @see MallMember
     * @param username  管理员名字
     * @return 管理员member
     */
    MallMember findMemberByName(String username);

    /**
     * 删除管理员
     * @param id admin id
     * @return 删除结果
     */
    boolean delete(Integer id);

    /**
     * 更新管理员
     * @param id admin id
     * @param admin admin
     * @return 更新后返回
     */
    MallAdmin update(Integer id, MallAdmin admin);

    /**
     * 添加管理员
     * @param admin admin
     * @return 添加后返回，得到id
     */
    MallAdmin add(MallAdmin admin);

    /**
     * 登录
     * @param username admin name
     * @param password 密码
     * @return token
     * @throws AuthenticationException 用户不存在 密码错误
     * @see org.springframework.security.core.userdetails.UsernameNotFoundException 用户不存在
     * @see BadCredentialsException 密码错误
     */
    String login(String username, String password) throws AuthenticationException;

    /**
     * 管理员退出
     * @param username 用户名
     * @return 一般是成功
     */
    CommonResult logout(String username);

    /**
     * 刷新token
     * @param token 旧token
     * @return 新token
     */
    String refreshToken(String token);

    /**
     * 查找所有管理员
     * @return 管理员列表
     */
    List<MallAdmin> list();

    /**
     * 通过条件查找管理员
     * @param adminName 管理员名字
     * @param page 第几页
     * @param limit 每页行数
     * @return 管理员列表
     */
    List<MallAdmin> list(String adminName, Integer page, Integer limit);
}
