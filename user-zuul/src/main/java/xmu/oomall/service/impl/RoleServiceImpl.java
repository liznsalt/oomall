package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.domain.MallRole;
import xmu.oomall.mapper.PrivilegeMapper;
import xmu.oomall.mapper.RoleMapper;
import xmu.oomall.service.RoleService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liznsalt
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Override
    public MallRole insert(MallRole role) {
        role.setGmtCreate(LocalDateTime.now());
        role.setGmtModified(LocalDateTime.now());
        role.setBeDeleted(false);

        int count = roleMapper.insert(role);
        return count == 0 ? null : role;
    }

    @Override
    public boolean deleteById(Integer id) {
        // 删掉角色下所有权限
        roleMapper.deletePrivilegesByRoleId(id);
        int count = roleMapper.deleteById(id);
        return count >= 1;
    }

    @Override
    public MallRole update(MallRole role) {
        int count = roleMapper.update(role);
        return count == 0 ? null : role;
    }

    @Override
    public MallRole findById(Integer id) {
        return roleMapper.findById(id);
    }

    @Override
    public List<MallRole> findRoles(Integer page, Integer limit) {
        return roleMapper.findRoles(page, limit);
    }

    @Override
    public List<MallAdmin> findAdmins(Integer id) {
        return roleMapper.findAdmins(id);
    }

    @Override
    public List<MallRole> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    @Override
    public List<MallPrivilege> getPrivilegesByRoleId(Integer id) {
        return roleMapper.getPrivilegesByRoleId(id);
    }

    @Override
    public List<MallPrivilege> updatePrivilegesByRoleId(Integer id, List<MallPrivilege> privileges) {
        roleMapper.deletePrivilegesByRoleId(id);
        for (MallPrivilege privilege : privileges) {
            privilege.setRoleId(id);
        }
        privilegeMapper.addPrivileges(privileges);
        return privileges;
    }
}
