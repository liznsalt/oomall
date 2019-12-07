package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallTopic;
import xmu.oomall.mapper.TopicMapper;
import xmu.oomall.service.TopicService;

import java.time.LocalDateTime;

/**
 * @author liznsalt
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public MallTopic addTopic(MallTopic topic) {
        topic.setGmtCreate(LocalDateTime.now());
        topic.setGmtModified(LocalDateTime.now());
        topic.setBeDeleted(false);
        topicMapper.addTopic(topic);
        return topic;
    }
}
