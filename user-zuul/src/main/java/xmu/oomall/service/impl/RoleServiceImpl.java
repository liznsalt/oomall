package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.domain.MallRole;
import xmu.oomall.mapper.RoleMapper;
import xmu.oomall.service.RoleService;

import java.util.List;

/**
 * @author liznsalt
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public MallRole insert(MallRole role) {
        int count = roleMapper.insert(role);
        return count == 0 ? null : role;
    }

    @Override
    public boolean deleteById(Integer id) {
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
}
