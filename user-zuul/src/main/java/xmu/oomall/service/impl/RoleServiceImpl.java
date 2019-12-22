package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.domain.MallRole;
import xmu.oomall.mapper.PrivilegeMapper;
import xmu.oomall.mapper.RoleMapper;
import xmu.oomall.service.RedisService;
import xmu.oomall.service.RoleService;
import xmu.oomall.util.UriUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private RedisService redisService;

    @Override
    public MallRole insert(MallRole role) {
        // 先查看这个role是否存在
        MallRole oldRole = roleMapper.findByName(role.getName());
        if (oldRole != null) {
            return null;
        }

        role.setGmtCreate(LocalDateTime.now());
        role.setGmtModified(LocalDateTime.now());
        role.setBeDeleted(false);

        int count = roleMapper.insert(role);
        return count == 0 ? null : role;
    }

    @Override
    public boolean deleteById(Integer id) {
        if (MallRole.isCannotDelete(id)) {
            return false;
        }
        // 删掉角色下所有权限
        roleMapper.deletePrivilegesByRoleId(id);
        // 删掉角色下所有管理员
        roleMapper.deleteAdminsByRoleId(id);
        int count = roleMapper.deleteById(id);
        if (count == 0) {
            return false;
        } else {
            // 去掉redis中存在的记录
            redisService.deleteByPrefix(UriUtil.METHOD_URL_PREFIX);
            return true;
        }
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
        // 确认角色不存在
        MallRole role = roleMapper.findById(id);
        if (role == null) {
            return new ArrayList<>(0);
        }
        return roleMapper.getPrivilegesByRoleId(id);
    }

    @Override
    public List<MallPrivilege> updatePrivilegesByRoleId(Integer id, List<MallPrivilege> privileges) {
        roleMapper.deletePrivilegesByRoleId(id);
        for (MallPrivilege privilege : privileges) {
            privilege.setRoleId(id);
        }
        if (privileges.size() != 0) {
            privilegeMapper.addPrivileges(privileges);
        }
        // 去掉redis中存在的记录
        redisService.deleteByPrefix(UriUtil.METHOD_URL_PREFIX);
        return privileges;
    }
}
