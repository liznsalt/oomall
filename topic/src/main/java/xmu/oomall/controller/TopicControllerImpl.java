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

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liznsalt
 */
@RestController
@RequestMapping("/topics")
public class TopicControllerImpl {

    private static final Logger logger = LoggerFactory.getLogger(TopicControllerImpl.class);

    @Autowired
    private TopicService topicService;

    @Autowired
    private LogService logService;

    @PostMapping("")
    public Object create(@RequestBody MallTopic topic) {
        logger.debug("addTopic参数为：" + topic);

        // FIXME 测试先将管理员ID设为1 IP设为1.1.1.1
        Log log = new Log();
        log.setAdminId(1);
        log.setIp("1.1.1.1");
        log.setType(1);
        log.setAction("1213");

        MallTopic resTopic = topicService.addTopic(topic);
        log.setStatusCode(1);
        log.setActionId(resTopic.getId());
        Object retObj = CommonResult.success(resTopic);

        // FIXME 记录日志
        logService.addLog(log);
        logger.debug("addTopic返回值为：" + retObj);
        return retObj;
    }

    @GetMapping("/")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "gmt_create") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<MallTopic> topicList = topicService
                .findNotDeletedTopicsByCondition(page, limit, sort, order);
        return CommonResult.success(topicList);
    }

    @GetMapping("/{id}/detail")
    public Object detail(@PathVariable("id") @NotNull Integer id) {
        MallTopic topic = topicService.findNotDeletedTopicById(id);
        return CommonResult.success(topic);
    }

//    @GetMapping("/{id}/related")
//    public Object related(@PathVariable @NotNull String id) {
//
//        return null;
//    }

//    @GetMapping("")
//    public Object list(String title, String subtitle,
//                       @RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer limit,
//                       @RequestParam(defaultValue = "add_time") String sort,
//                       @RequestParam(defaultValue = "desc") String order) {
//        return null;
//    }

    @GetMapping("/{id}")
    public Object read(@PathVariable("id") @NotNull Integer id) {
        MallTopic topic = topicService.findTopicById(id);
        return CommonResult.success(topic);
    }

    @PutMapping("/{id}")
    public Object update(@RequestBody Topic topic, @PathVariable Integer id) {
        topic.setId(id);
        Boolean result = topicService.updateTopic((MallTopic) topic);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @DeleteMapping("/{id}")
    public Object delete(@RequestBody Topic topic, @PathVariable Integer id) {
        Boolean result = topicService.deleteTopicById(id);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    @GetMapping("/test")
    @ResponseBody
    public Object test() {
        Log log = new Log();
        log.setAdminId(1);
        log.setIp("1.1.1.1");
        log.setType(1);
        log.setAction("1213");
        log.setStatusCode(1);
        log.setActionId(1);
        return logService.addLog(log);
    }
}
