package common.oomall.component;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author liznsalt
 */
public interface MallMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
