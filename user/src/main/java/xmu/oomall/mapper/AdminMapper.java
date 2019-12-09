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
    int addAdmin(MallAdmin admin);
    int deleteById(Integer id);
    int updateAdmin(MallAdmin admin);
    MallAdmin findById(Integer id);
    List<MallAdmin> findByCondition(int page, int limit, String sort, String order);
    List<MallAdmin> findByName(String name);
}
