package xmu.oomall.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.LogApplication;
import xmu.oomall.domain.MallLog;
import xmu.oomall.util.JacksonUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LogApplication.class)
@AutoConfigureMockMvc
@Transactional
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LogController logController;

    @Test
    void addLog() throws Exception {
        MallLog log = new MallLog();
        log.setAdminId(1);
        log.setIp("112.124.128.11");
        log.setType(1);
        log.setAction("addGoods()");
        log.setStatusCode((short) 1);
        log.setGmtCreate(LocalDateTime.now());
        log.setGmtModified(LocalDateTime.now());
        log.setActionId(1);

        String jsonString = JacksonUtil.toJson(log);

        String res = this.mockMvc.perform(post("/logs").contentType("application/json;charset=UTF-8").content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String errMsg = JacksonUtil.parseObject(res, "errmsg", String.class);
        Integer errNo = JacksonUtil.parseObject(res, "errno", Integer.class);
        MallLog resLog = JacksonUtil.parseObject(res, "data", MallLog.class);

        System.out.println(resLog);

        assertEquals(errMsg, "成功");
        assertEquals(errNo, 0);
        assert resLog != null;
        assertNotNull(resLog.getId());
        assertEquals(resLog.getAdminId(), 1);
        assertEquals(resLog.getAdminId(), 1);
    }


    @Test
    void list() {
        Object res = logController.list(1, 10, "id", "desc");
        System.out.println(res);
    }
}