package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import common.oomall.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import standard.oomall.domain.Topic;
import xmu.oomall.domain.MallTopic;
import xmu.oomall.service.LogService;
import xmu.oomall.service.TopicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liznsalt
 */
@RestController
@RequestMapping("/topicService")
public class TopicControllerImpl {

    private static final Logger logger = LoggerFactory.getLogger(TopicControllerImpl.class);

    @Autowired
    private TopicService topicService;

    @Autowired
    private LogService logService;

    private final static Integer INSERT = 0;
    private final static Integer DELETE = 1;
    private final static Integer UPDATE = 2;
    private final static Integer SELECT = 3;
    private void writeLog(Integer adminId, String ip, Integer type,
                          String action, Integer statusCode, Integer actionId) {
        Log log = new Log();
        log.setAdminId(adminId);
        log.setIp(ip);
        log.setType(type);
        log.setAction(action);
        log.setStatusCode(statusCode);
        log.setActionId(actionId);
        logService.addLog(log);
    }

    @GetMapping("/topics")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       HttpServletRequest request) {
        List<MallTopic> topicList = topicService.findNotDeletedTopicsByCondition(page, limit);
        return CommonResult.success(topicList);
    }

    @GetMapping("/topics/{id}")
    public Object detail(@PathVariable("id") Integer id) {
        MallTopic topic = topicService.findNotDeletedTopicById(id);
        return CommonResult.success(topic);
    }

    @PostMapping("/topics")
    public Object create(@RequestBody MallTopic topic,
                         HttpServletRequest request) {
        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");

        MallTopic resTopic = topicService.addTopic(topic);
        if (resTopic.getId() == null) {
            writeLog(adminId, ip, INSERT, "添加专题", 0, null);
            return CommonResult.failed();
        } else {
            writeLog(adminId, ip, INSERT, "添加专题", 1, resTopic.getId());
            return CommonResult.success(resTopic);
        }
    }

    @PutMapping("/topics/{id}")
    public Object update(@RequestBody Topic topic, @PathVariable Integer id,
                         HttpServletRequest request) {
        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");

        topic.setId(id);
        Boolean result = topicService.updateTopic((MallTopic) topic);
        if (result) {
            writeLog(adminId, ip, UPDATE, "修改专题", 1, id);
            return CommonResult.success(result);
        } else {
            writeLog(adminId, ip, UPDATE, "修改专题", 0, id);
            return CommonResult.failed();
        }
    }

    @DeleteMapping("/topics/{id}")
    public Object delete(@PathVariable Integer id,
                         HttpServletRequest request) {
        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");

        Boolean result = topicService.deleteTopicById(id);
        if (result) {
            writeLog(adminId, ip, DELETE, "删除专题", 1, id);
            return CommonResult.success(result);
        } else {
            writeLog(adminId, ip, DELETE, "删除专题", 0, id);
            return CommonResult.failed();
        }
    }
}
