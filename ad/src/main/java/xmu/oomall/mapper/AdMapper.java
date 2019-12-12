package xmu.oomall.mapper;

import common.oomall.component.MallMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallAd;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface AdMapper extends MallMapper<MallAd> {
}
