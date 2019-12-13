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
    int insert(MallRole role);
    int deleteById(Integer id);
    int update(MallRole role);
    MallRole findById(Integer id);
    List<MallRole> findRoles(Integer page, Integer limit);
    List<MallAdmin> findAdmins(Integer id);
    List<MallRole> getAllRoles();

    List<MallPrivilege> getPrivilegesByRoleId(Integer id);
}
