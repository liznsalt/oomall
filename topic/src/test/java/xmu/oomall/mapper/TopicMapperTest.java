package xmu.oomall.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xmu.oomall.domain.MallTopic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicMapperTest {
    @Autowired
    private TopicMapper topicMapper;

    @Test
    public void addTopic() {
        MallTopic mallTopic=new MallTopic();
        mallTopic.setId(1);
        mallTopic.setContent("...");
        mallTopic.setPicUrlList("--");
        mallTopic.setBeDeleted(false);
        mallTopic.setGmtCreate(LocalDateTime.now());
        mallTopic.setGmtModified(LocalDateTime.now());
        topicMapper.addTopic(mallTopic);
    }

    @Test
    public void deleteTopicById() {


    }

    @Test
    public void updateTopic() {
    }

    @Test
    public void findTopicById() {
        topicMapper.findTopicById(1);
    }

    @Test
    public void findNotDeletedTopicById() {
    }

    @Test
    public void findTopicsByCondition() {
    }

    @Test
    public void findNotDeletedTopicsByCondition() {
    }
}
