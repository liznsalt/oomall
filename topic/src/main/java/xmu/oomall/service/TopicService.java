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
     * @throws Exception
     */
    MallTopic addTopic(MallTopic topic) throws Exception;

    /**
     * 更新专题信息
     * @param topic 专题信息
     * @return 更新是否成功
     * @throws Exception
     */
    MallTopic updateTopic(MallTopic topic) throws Exception;

    /**
     * 删除专题
     * @param id 删除专题的 id
     * @return 删除是否成功
     * @throws Exception
     */
    Boolean deleteTopicById(Integer id) throws Exception;

    /**
     * 查找专题且该专题未被删除
     * @param id 专题 id
     * @return 专题信息
     */
    MallTopic findNotDeletedTopicById(Integer id);

    /**
     * 通过条件查找未被删除的专题信息
     * @return 符合情况且未被删除的专题信息列表
     * @param limit
     * @param page
     */
    List<MallTopic> findNotDeletedTopicsByCondition(Integer page,Integer limit);
}
