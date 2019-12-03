package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.domain.TestTable;

/**
 * @author liznsalt
 */
@Mapper
public interface TestTableMapper {
    TestTable findById(Integer id);
}
