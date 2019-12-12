package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallTopic;

import java.util.List;

/**
 * @author liznsalt
 * @author YaNai
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

    /**
     * 删除专题
     * @param id 专题 id
     * @return 行数
     */
    int deleteTopicById(Integer id);

    /**
     * 更新专题
     * @param topic 专题信息
     * @return 行数
     */
    int updateTopic(MallTopic topic);

    /**
     * 根据 id 查找专题
     * @param id 专题 id
     * @return 专题信息
     */
    MallTopic findTopicById(Integer id);

    /**
     * 根据 id 查找未删除的专题
     * @param id 专题 id
     * @return 专题信息
     */
    MallTopic findNotDeletedTopicById(Integer id);

    /**
     * 根据分页条件查找专题信息
     * @param page 当前页数
     * @param limit 每页记录条数
     * @param sort 根据什么字段来排序
     * @param order 升序 or 降序
     * @return 专题信息列表
     */
    List<MallTopic> findTopicsByCondition(Integer page, Integer limit,
                                          String sort, String order);

    /**
     * 根据分页条件查找未删除的专题信息
     * @param page 当前页数
     * @param limit 每页记录条数
     * @param sort 根据什么字段来排序
     * @param order 升序 or 降序
     * @return 专题信息列表
     */
    List<MallTopic> findNotDeletedTopicsByCondition(Integer page, Integer limit,
                                                    String sort, String order);
}
