package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import standard.oomall.domain.TopicPo;
import xmu.oomall.domain.MallTopic;
import xmu.oomall.mapper.TopicMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  TODO redis
 * @author JerryShen710
 */
@Repository
public class TopicDao {
    @Autowired
    private TopicMapper topicMapper;

    /**
     * 添加专题
     * @param topic 专题信息
     * @return 行数
     */
    public int addTopic(MallTopic topic){
        return topicMapper.addTopic(topic);
    }

    /**
     * 删除专题
     * @param id 专题 id
     * @return 行数
     */
    public int deleteTopicById(Integer id){
        return topicMapper.deleteTopicById(id);
    }

    /**
     * 更新专题
     * @param topic 专题信息
     * @return 行数
     */
    public MallTopic updateTopic(MallTopic topic){
        if(topicMapper.updateTopic(topic)==1){
            return topicMapper.findTopicById(topic.getId());
        }
        else{
            return null;
        }
    }

    /**
     * 根据 id 查找未删除的专题
     * @param id 专题 id
     * @return 专题信息
     */
    public MallTopic findNotDeletedTopicById(Integer id){
        return topicMapper.findNotDeletedTopicById(id).picturesset();
    }

    /**
     * 根据分页条件查找未删除的专题信息
     * @param page
     * @param limit
     * @return 专题信息列表
     */
    public List<MallTopic> findNotDeletedTopicsByCondition(Integer page,Integer limit){
        return topicMapper.findNotDeletedTopicsByCondition(page, limit).stream().map(MallTopic::picturesset).collect(Collectors.toList());
    }
}
