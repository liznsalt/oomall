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
    List<MallPrivilege> getAll();
}
