package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import standard.oomall.domain.Topic;
import standard.oomall.domain.TopicPo;
import xmu.oomall.domain.MallTopic;
import xmu.oomall.service.LogService;
import xmu.oomall.service.TopicService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liznsalt
 */
@RestController
public class TopicControllerImpl {

    private static final Logger logger = LoggerFactory.getLogger(TopicControllerImpl.class);

    @Autowired
    private TopicService topicService;

    @Autowired
    private LogService logService;

    private Integer getUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("userId");
        if (userIdStr == null) {
            return null;
        }
        return Integer.valueOf(userIdStr);
    }

    private final static Integer INSERT = 1;
    private final static Integer DELETE = 3;
    private final static Integer UPDATE = 2;
    private final static Integer SELECT = 0;
    private void writeLog(Integer adminId, String ip, Integer type,
                          String action, Integer statusCode, Integer actionId) {
        Log log = new Log();
        log.setAdminId(adminId);
        log.setIp(ip);
        log.setType(type);
        log.setActions(action);
        log.setStatusCode(statusCode);
        log.setActionId(actionId);
        logService.addLog(log);
    }

    @GetMapping("/topics")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (page == null || page < 0) {
            return CommonResult.badArgumentValue();
        }
        if (limit == null || limit < 0) {
            return CommonResult.badArgumentValue();
        }

        List<MallTopic> topicList = topicService.findNotDeletedTopicsByCondition(page, limit);
        return CommonResult.success(topicList);
    }

    @GetMapping("/admin/topics")
    public Object adminlist(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (page == null || page < 0) {
            return CommonResult.badArgumentValue();
        }
        if (limit == null || limit < 0) {
            return CommonResult.badArgumentValue();
        }

        List<MallTopic> topicList = topicService.findNotDeletedTopicsByCondition(page, limit);
        return CommonResult.success(topicList);
    }

    @GetMapping("/topics/{id}")
    public Object detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        MallTopic topic = topicService.findNotDeletedTopicById(id);
        return CommonResult.success(topic);
    }

    @GetMapping("/admin/topics/{id}")
    public Object admindetail(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        MallTopic topic = topicService.findNotDeletedTopicById(id);
        return CommonResult.success(topic);
    }

    @PostMapping("/topics")
    public Object create(@RequestBody TopicPo topicPo, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        //参数校验
        if((topicPo.getPicUrlList()==null)||(topicPo.getContent()==null)) {
            return CommonResult.badArgument();
        }

        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");

        try {
            MallTopic mallTopic=new MallTopic(topicPo);
            MallTopic resTopic = topicService.addTopic(mallTopic);
            writeLog(adminId, ip, INSERT, "添加专题", 1, resTopic.getId());
            return CommonResult.success(resTopic.toTopicPo());
        } catch (Exception e) {
            writeLog(adminId, ip, INSERT, "添加专题", 0, null);
            return CommonResult.updatedDataFailed();
        }
    }

    @PutMapping("/topics/{id}")
    public Object update(@RequestBody TopicPo topicPo, @PathVariable Integer id,
                         HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        //参数校验
        if((topicPo.getPicUrlList()==null)||(topicPo.getContent()==null)) {
            return CommonResult.badArgument();
        }
        if(id==null || id < 0){
            return CommonResult.badArgument();
        }

        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");
        topicPo.setId(id);
        try {
            MallTopic result=topicService.updateTopic(new MallTopic(topicPo));
            if (result!=null) {
                writeLog(adminId, ip, UPDATE, "修改专题", 1, id);
                return CommonResult.success(result.toTopicPo());
            }
            else {
                writeLog(adminId, ip, UPDATE, "修改专题", 0, id);
                return CommonResult.updatedDataFailed();
            }
        } catch(Exception e) {
            writeLog(adminId, ip, INSERT, "添加专题", 0, null);
            return CommonResult.updatedDataFailed();
        }
    }

    @DeleteMapping("/topics/{id}")
    public Object delete(@PathVariable Integer id,
                         HttpServletRequest request){
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgument();
        }

        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");

        try {
            Boolean result = topicService.deleteTopicById(id);
            if (result) {
                writeLog(adminId, ip, DELETE, "删除专题", 1, id);
                return CommonResult.success();
            } else {
                writeLog(adminId, ip, DELETE, "删除专题", 0, id);
                return CommonResult.failed();
            }
        } catch(Exception e) {
            writeLog(adminId, ip, INSERT, "添加专题", 0, null);
            return CommonResult.failed();
        }
    }
}
