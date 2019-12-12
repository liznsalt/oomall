package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallTopic;
import xmu.oomall.mapper.TopicMapper;
import xmu.oomall.service.TopicService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liznsalt
 * @author YaNai
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public MallTopic addTopic(MallTopic topic) {
        topic.setBeDeleted(false);
        topicMapper.addTopic(topic);
        return topic;
    }

    @Override
    public Boolean updateTopic(MallTopic topic) {
        if (topic.getId() == null) {
            return false;
        }
        topicMapper.updateTopic(topic);
        return true;
    }

    @Override
    public Boolean deleteTopicById(Integer id) {
        if (id == null) {
            return false;
        }
        topicMapper.deleteTopicById(id);
        return true;
    }

    @Override
    public MallTopic findTopicById(Integer id) {
        if (id == null) {
            return null;
        }
        return topicMapper.findTopicById(id);
    }

    @Override
    public MallTopic findNotDeletedTopicById(Integer id) {
        if (id == null) {
            return null;
        }
        return topicMapper.findNotDeletedTopicById(id);
    }

    @Override
    public List<MallTopic> findTopicsByCondition(Integer page, Integer limit,
                                                 String sort, String order) {
        if (page <= 0 && limit <= 0) {
            return null;
        }
        if (!order.toLowerCase().equals("asc")
                && !order.toLowerCase().equals("desc")) {
            return null;
        }
        try {
            return topicMapper.findTopicsByCondition(page, limit, sort, order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MallTopic> findNotDeletedTopicsByCondition(Integer page, Integer limit,
                                                           String sort, String order) {
        if (page <= 0 && limit <= 0) {
            return null;
        }
        if (!order.toLowerCase().equals("asc")
                && !order.toLowerCase().equals("desc")) {
            return null;
        }
        try {
            return topicMapper.findNotDeletedTopicsByCondition(page, limit, sort, order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
