package xmu.oomall.service;

import xmu.oomall.domain.MallTopic;

import java.util.List;

/**
 * @author liznsalt
 * @author YaNai
 */
public interface TopicService {
    /**
     * 添加专题
     * @param topic 专题
     * @return 添加后的专题
     */
    MallTopic addTopic(MallTopic topic);

    /**
     * 更新专题信息
     * @param topic 专题信息
     * @return 更新是否成功
     */
    Boolean updateTopic(MallTopic topic);

    /**
     * 删除专题
     * @param id 删除专题的 id
     * @return 删除是否成功
     */
    Boolean deleteTopicById(Integer id);

    /**
     * 查找专题
     * @param id 专题 id
     * @return 专题信息
     */
    MallTopic findTopicById(Integer id);

    /**
     * 查找专题且该专题未被删除
     * @param id 专题 id
     * @return 专题信息
     */
    MallTopic findNotDeletedTopicById(Integer id);

    /**
     * 通过条件查找专题信息
     * @param page 当前第几页
     * @param limit 每页记录条数
     * @param sort 按什么字段排序
     * @param order 升序 or 降序
     * @return 符合情况的专题信息列表
     */
    List<MallTopic> findTopicsByCondition(Integer page, Integer limit,
                                          String sort, String order);

    /**
     * 通过条件查找未被删除的专题信息
     * @param page 当前第几页
     * @param limit 每页记录条数
     * @param sort 按什么字段排序
     * @param order 升序 or 降序
     * @return 符合情况且未被删除的专题信息列表
     */
    List<MallTopic> findNotDeletedTopicsByCondition(Integer page, Integer limit,
                                                    String sort, String order);
}
