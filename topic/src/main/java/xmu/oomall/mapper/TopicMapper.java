package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallTopic;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface TopicMapper {
    /**
     * 添加专题
     * @param topic 专题信息
     * @return 行数
     */
    int addTopic(MallTopic topic);
}
