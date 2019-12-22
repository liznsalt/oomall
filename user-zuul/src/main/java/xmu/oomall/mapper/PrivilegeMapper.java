package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallPrivilege;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface PrivilegeMapper {
    /**
     * 所有权限
     * @return 权限列表
     */
    List<MallPrivilege> getAll();

    /**
     * 批量添加权限
     * @param privileges 权限列表
     * @return 行数
     */
    int addPrivileges(List<MallPrivilege> privileges);

    /**
     * 得到白名单列表
     * @return 白名单
     */
    List<MallPrivilege> getWhiteUrlList();
}
