package xmu.oomall.service;

import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.domain.MallRole;

import java.util.List;

/**
 * @author liznsalt
 */
public interface RoleService {
    MallRole insert(MallRole role);
    boolean deleteById(Integer id);
    MallRole update(MallRole role);
    MallRole findById(Integer id);
    List<MallRole> findRoles(Integer page, Integer limit);
    List<MallAdmin> findAdmins(Integer id);
    List<MallRole> getAllRoles();

    List<MallPrivilege> getPrivilegesByRoleId(Integer id);
    List<MallPrivilege> updatePrivilegesByRoleId(Integer id, List<MallPrivilege> privileges);
}
