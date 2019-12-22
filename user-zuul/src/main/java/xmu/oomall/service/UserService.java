package xmu.oomall.service;

import common.oomall.api.CommonResult;
import common.oomall.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.domain.MallMember;
import xmu.oomall.domain.MallUser;
import xmu.oomall.domain.details.MallMemberDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liznsalt
 */
public interface UserService {
    /**
     * 查找用户
     * @param username 用户名
     * @return 用户
     */
    MallUser findByName(String username);

    /**
     * 查找用户
     * @param telephone 手机号
     * @return 用户
     */
    MallUser findByTelephone(String telephone);

    /**
     * 查找用户
     * @param id 用户id
     * @return 用户
     */
    MallUser findById(Integer id);

    /**
     * 分页 查找用户
     * @param page 第几页
     * @param limit 每页行数
     * @return 用户列表
     */
    List<MallUser> findUsers(Integer page, Integer limit);

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @param telephone 手机号
     * @param authCode 验证码
     * @return 结果
     */
    Object register(String username, String password, String telephone, String authCode);

    /**
     * 注册
     * @see UserService#register(String, String, String, String)
     * @deprecated
     */
    @Deprecated
    CommonResult register(MallUser user, String authCode);

    /**
     * 生成验证码
     * @param telephone 手机号
     * @return 验证码
     */
    String generateAuthCode(String telephone);

    /**
     * 更新密码
     * @param telephone 手机号
     * @param password 新密码
     * @param authCode 验证码
     * @return 结果
     */
    Object updatePassword(String telephone, String password, String authCode);

    /**
     * 更新手机号
     * @param telephone 手机号
     * @param password 密码
     * @param authCode 验证码
     * @param newPhone 新手机号
     * @return 结果
     */
    Object updateTelephone(String telephone, String password, String authCode, String newPhone);

    /**
     * 将返点还给用户 即用户旧返点+rebate
     * @param userId 用户id
     * @param rebate 返点
     * @return 结果
     */
    Object updateRebate(Integer userId, Integer rebate);

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param request http请求
     * @return token
     * @throws AuthenticationException 用户名不存在 密码错误
     */
    String login(String username, String password, HttpServletRequest request) throws AuthenticationException;

    /**
     * 刷新token
     * @param token 旧token
     * @return 新token
     */
    String refreshToken(String token);

    /**
     * 查找用户封装成detail
     * @see org.springframework.security.core.userdetails.UserDetails
     * @see UserService#findByName(String)
     * @param username 用户名
     * @return 用户details
     */
    MallMemberDetails findDetailsByName(String username);

    /**
     * 查找用户封装成member
     * @see MallMember
     * @see UserService#findByName(String)
     * @param username 用户名
     * @return 用户member
     */
    MallMember findMemberByName(String username);

    /**
     * 全部用户
     * @return 全部用户
     */
    @Deprecated
    List<MallUser> list();

    /**
     * 通过条件得到用户列表
     * @param username 用户名
     * @param page 第几页
     * @param limit 每页行数
     * @return 用户列表
     */
    List<MallUser> listByCondition(String username, Integer page, Integer limit);
}
