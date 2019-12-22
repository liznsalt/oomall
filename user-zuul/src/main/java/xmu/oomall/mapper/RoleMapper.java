package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.domain.MallRole;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface RoleMapper {
    /**
     * 添加角色
     * @param role 角色
     * @return 行数
     */
    int insert(MallRole role);

    /**
     * 删除角色
     * @param id 角色id
     * @return 行数
     */
    int deleteById(Integer id);

    /**
     * 更新角色
     * @param role 角色
     * @return 行数
     */
    int update(MallRole role);

    /**
     * 查找角色
     * @param id 角色id
     * @return 角色
     */
    MallRole findById(Integer id);

    /**
     * 查找角色
     * @param page 第几页
     * @param limit 每页行数
     * @return 角色列表
     */
    List<MallRole> findRoles(Integer page, Integer limit);

    /**
     * 查找改角色所有管理员
     * @param id 角色id'
     * @return 管理员列表
     */
    List<MallAdmin> findAdmins(Integer id);

    /**
     * 得到所有角色
     * @return 角色列表
     */
    List<MallRole> getAllRoles();

    /**
     * 得到改角色的权限
     * @param id 角色id
     * @return 权限列表
     */
    List<MallPrivilege> getPrivilegesByRoleId(Integer id);

    /**
     * 删除改角色权限
     * @param id 角色id
     * @return 行数
     */
    int deletePrivilegesByRoleId(Integer id);

    /**
     * 删除角色下管理员
     * @param id 角色id
     * @return 行数
     */
    int deleteAdminsByRoleId(Integer id);

    /**
     * 查找角色
     * @param name 角色名
     * @return 角色
     */
    MallRole findByName(String name);
}
