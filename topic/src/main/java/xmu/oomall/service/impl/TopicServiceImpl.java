package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.TopicDao;
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
public class TopicServiceImpl implements TopicService{

    @Autowired
    private TopicDao topicDao;


    @Override
    public MallTopic addTopic(MallTopic topic) throws Exception{
        topicDao.addTopic(topic);
        return topic;
    }

    @Override
    public MallTopic updateTopic(MallTopic topic) throws Exception{
        return topicDao.updateTopic(topic);
    }

    @Override
    public Boolean deleteTopicById(Integer id) throws Exception{
        int result = topicDao.deleteTopicById(id);
        return result >= 1;
    }

    @Override
    public MallTopic findNotDeletedTopicById(Integer id) {
        if (id == null) {
            return null;
        }
        return topicDao.findNotDeletedTopicById(id);
    }

    @Override
    public List<MallTopic> findNotDeletedTopicsByCondition(Integer page,Integer limit) {
        try {
            return topicDao.findNotDeletedTopicsByCondition(page, limit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
