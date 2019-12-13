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

    @GetMapping("/topics")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       HttpServletRequest request) {
        System.out.println(request.getHeader("token"));
        System.out.println(request.getHeader("userId"));
        System.out.println(request.getHeader("ip"));
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

        logger.debug("addTopic参数为：" + topic);

        // FIXME 测试先将管理员ID设为1 IP设为1.1.1.1
        Log log = new Log();
        log.setAdminId(Integer.valueOf(request.getHeader("userId")));
        log.setIp(request.getHeader("ip"));
        log.setType(0);
        log.setAction("添加专题");

        MallTopic resTopic = topicService.addTopic(topic);
        log.setStatusCode(1);
        log.setActionId(resTopic.getId());
        Object retObj = CommonResult.success(resTopic);

        // FIXME 记录日志
        logService.addLog(log);
        logger.debug("addTopic返回值为：" + retObj);
        return retObj;
    }

    @PutMapping("/topics/{id}")
    public Object update(@RequestBody Topic topic, @PathVariable Integer id) {
        topic.setId(id);
        Boolean result = topicService.updateTopic((MallTopic) topic);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @DeleteMapping("/topics/{id}")
    public Object delete(@PathVariable Integer id,
                         HttpServletRequest request) {
        Boolean result = topicService.deleteTopicById(id);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}
