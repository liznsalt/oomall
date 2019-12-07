package xmu.oomall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import xmu.oomall.LogApplication;
import xmu.oomall.domain.MallLog;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LogApplication.class)
@Transactional
class LogServiceTest {

    @Autowired
    private LogService logService;

    @Test
    void addLog() {
    }

    @Test
    void getAllLogs() {
        PageHelper.startPage(2, 2);
        List<MallLog> logList = logService.getAllLogs();
        for (MallLog log : logList) {
            System.out.println(log);
        }

        System.out.println("-----------");

        PageInfo<MallLog> pageInfo = new PageInfo<>(logList);
        List<MallLog> logs = pageInfo.getList();
        for (MallLog log : logs) {
            System.out.println(log);
        }
    }

    @Test
    void testExample() {
        Example example = new Example(MallLog.class);
        example.setOrderByClause("id desc");
        List<MallLog> logList = logService.findLogsByExample(example);
        for (MallLog log : logList) {
            System.out.println(log);
        }
    }
}