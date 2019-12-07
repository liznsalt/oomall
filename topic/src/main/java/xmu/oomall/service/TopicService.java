package xmu.oomall.service;

import xmu.oomall.domain.MallTopic;

/**
 * @author liznsalt
 */
public interface TopicService {
    /**
     * 添加专题
     * @param topic 专题
     * @return 添加后的专题
     */
    MallTopic addTopic(MallTopic topic);
}
