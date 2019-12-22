package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallUser;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface UserMapper {
    /**
     * 添加用户
     * @param user 用户
     * @return 行数
     */
    int addUser(MallUser user);

    /**
     * 删除用户
     * @param id 用户id
     * @return 行数
     */
    int deleteById(Integer id);

    /**
     * 更新用户
     * @param user 用户
     * @return 行数
     */
    int updateUser(MallUser user);

    /**
     * 查找用户
     * @param id 用户id
     * @return 用户
     */
    MallUser findById(Integer id);

    /**
     * 分页
     * @param page 第几页
     * @param limit 每页行数
     * @return 用户列表
     */
    List<MallUser> findByCondition(int page, int limit);

    /**
     * 查找用户
     * @param name 用户名
     * @return 用户列表
     */
    List<MallUser> findByName(String name);

    /**
     * 查找用户
     * @param telephone 手机号
     * @return 用户列表
     */
    List<MallUser> findByTelephone(String telephone);

    /**
     * 得到所有用户
     * @return 用户列表
     */
    List<MallUser> getAllUsers();

    /**
     * 条件查找
     * @param username 用户名
     * @param page 第几页
     * @param limit 每页行数
     * @return 用户列表
     */
    List<MallUser> getUsersByCondition(String username, Integer page, Integer limit);
}
