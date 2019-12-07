package xmu.oomall.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.LogApplication;
import xmu.oomall.domain.MallLog;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LogApplication.class)
@AutoConfigureMockMvc
@Transactional
class LogMapperTest {

    @Autowired
    private LogMapper logMapper;

    @Test
    void addLog() {
        MallLog log = new MallLog();
        log.setAdminId(1);
        log.setIp("112.124.128.11");
        log.setType(1);
        log.setAction("addGoods()");
        log.setStatusCode(1);
        log.setGmtCreate(LocalDateTime.now());
        log.setGmtModified(LocalDateTime.now());
        log.setActionId(1);
        logMapper.addLog(log);
    }

    @Test
    void getAllLogs() {
    }

    @Test
    void findLogById() {
    }
}