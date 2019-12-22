package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallAdmin;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface AdminMapper {
    /**
     * 添加管理员
     * @param admin 管理员
     * @return 行数
     */
    int addAdmin(MallAdmin admin);

    /**
     * 删除管理员
     * @param id 管理员id
     * @return 行数
     */
    int deleteById(Integer id);

    /**
     * 更新管理员
     * @param admin 管理员
     * @return 行数
     */
    int updateAdmin(MallAdmin admin);

    /**
     * 查找
     * @param id 管理员id
     * @return 管理员
     */
    MallAdmin findById(Integer id);

    /**
     * 分页
     * @param page 第几页
     * @param limit 每页行数
     * @return 管理员列表
     */
    List<MallAdmin> findByCondition(int page, int limit);

    /**
     * 查找管理员
     * @param name 管理员name
     * @return 管理员列表
     */
    List<MallAdmin> findByName(String name);

    /**
     * 得到所有管理员
     * @return 管理员列表
     */
    List<MallAdmin> getAllAdmins();

    /**
     * 条件查找
     * @param adminName 管理员名字
     * @param page 第几页
     * @param limit 每页行数
     * @return 管理员列表
     */
    List<MallAdmin> getByCondition(String adminName, Integer page, Integer limit);
}
