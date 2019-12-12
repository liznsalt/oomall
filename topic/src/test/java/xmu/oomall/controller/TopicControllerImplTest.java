package xmu.oomall.controller;

import common.oomall.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import standard.oomall.domain.Topic;
import xmu.oomall.TopicApplication;
import xmu.oomall.domain.MallTopic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TopicApplication.class})
@AutoConfigureMockMvc
@Transactional
public class TopicControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TopicControllerImpl topicController;

    @Test
    public void createTest() throws Exception {
//        MallTopic topic = new MallTopic();
//        topic.setContent("1234");
//        topic.setPicUrlList("{picUrlList: [1,2,3]}");
////        topicController.create(topic);
////        System.out.println(topic);
//
//        String jsonString = JacksonUtil.toJson(topic);
//
//        assert jsonString != null;
//        String responseString = this.mockMvc
//                .perform(post("/topics").contentType("application/json;charset=UTF-8").content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//
//        String errMsg = JacksonUtil.parseObject(responseString,"errmsg", String.class);
//        Integer errNo = JacksonUtil.parseObject(responseString,"errno", Integer.class);
//        Topic topic1 = JacksonUtil.parseObject(responseString,"data", Topic.class);
//
//        System.out.println(topic1);
    }

}