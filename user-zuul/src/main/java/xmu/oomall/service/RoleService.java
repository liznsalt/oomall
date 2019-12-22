package xmu.oomall.service;

import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.domain.MallRole;

import java.util.List;

/**
 * @author liznsalt
 */
public interface RoleService {
    /**
     * 添加角色
     * @param role 角色
     * @return 角色
     */
    MallRole insert(MallRole role);

    /**
     * 删除角色
     * @param id 角色id
     * @return 结果
     */
    boolean deleteById(Integer id);

    /**
     * 更新角色
     * @param role 角色
     * @return 角色
     */
    MallRole update(MallRole role);

    /**
     * 通过id寻找角色
     * @param id 角色id
     * @return 角色
     */
    MallRole findById(Integer id);

    /**
     * 角色分页列表
     * @param page 第几页
     * @param limit 每页行数
     * @return 角色列表
     */
    List<MallRole> findRoles(Integer page, Integer limit);

    /**
     * 通过角色id寻找管理员
     * @param id 角色id
     * @return 管理员列表
     */
    List<MallAdmin> findAdmins(Integer id);

    /**
     * 得到所有角色
     * @return 角色列表
     */
    List<MallRole> getAllRoles();

    /**
     * 通过角色id得到所有权限
     * @param id 角色id
     * @return 权限列表
     */
    List<MallPrivilege> getPrivilegesByRoleId(Integer id);

    /**
     * 更新角色权限
     * @param id 角色id
     * @param privileges 权限列表
     * @return 权限列表
     */
    List<MallPrivilege> updatePrivilegesByRoleId(Integer id, List<MallPrivilege> privileges);
}
