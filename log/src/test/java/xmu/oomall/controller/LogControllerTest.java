package xmu.oomall.controller;

import common.oomall.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import standard.oomall.domain.Log;
import xmu.oomall.LogApplication;
import xmu.oomall.domain.MallLog;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LogApplication.class)
@AutoConfigureMockMvc
//@Transactional
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LogController logController;

    @Test
    public void addLog() throws Exception {
        MallLog log = new MallLog();
        log.setAdminId(1);
        log.setIp("112.124.128.11");
        log.setType(1);
        log.setAction("addGoods()");
        log.setStatusCode(1);
        log.setGmtCreate(LocalDateTime.now());
        log.setGmtModified(LocalDateTime.now());
        log.setActionId(1);

        String jsonString = JacksonUtil.toJson(log);

        String res = this.mockMvc
                .perform(post("/logs").contentType("application/json;charset=UTF-8").content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String errMsg = JacksonUtil.parseObject(res, "errmsg", String.class);
        Integer errNo = JacksonUtil.parseObject(res, "errno", Integer.class);
        MallLog resLog = JacksonUtil.parseObject(res, "data", MallLog.class);

        System.out.println(resLog);
    }

    @Test
    public void list() {
    }

    @Test
    public void testList() {
    }
}