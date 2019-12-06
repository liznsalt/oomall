package xmu.oomall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.MallLog;
import xmu.oomall.service.LogService;
import xmu.oomall.util.ResponseUtil;

import java.util.List;

/**
 * @author liznsalt
 */
@RestController
@RequestMapping(value = "/logs", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    @PostMapping("")
    public Object addLog(@RequestBody MallLog log) {
        logger.debug("addLog参数：" + log);
        MallLog newLog = logService.addLog(log);
        Object retObj = ResponseUtil.ok(newLog);
        logger.debug("addLog返回值：" + retObj);
        return retObj;
    }

    @GetMapping("")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "gmt_create") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<MallLog> logList = logService.getAllLogs();
        Object retObj = ResponseUtil.ok(logList);
        logger.debug("list返回值：" + retObj);
        return retObj;
    }
}
