package xmu.oomall.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import common.oomall.api.CommonResult;
import common.oomall.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import standard.oomall.domain.Topic;
import standard.oomall.domain.TopicPo;
import sun.management.snmp.AdaptorBootstrap;
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
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return ResponseUtil.fail(660,"用户未登录");
        }

        List<MallTopic> topicList = topicService.findNotDeletedTopicsByCondition(page, limit);
        if(topicList==null){
            return ResponseUtil.fail(650,"该话题是无效话题");
        }
        return ResponseUtil.ok(topicList);
    }

    @GetMapping("/admin/topics")
    public Object adminlist(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                            HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(669,"管理员未登录");
        }

        List<MallTopic> topicList = topicService.findNotDeletedTopicsByCondition(page, limit);
        if(topicList==null){
            return ResponseUtil.fail(650,"该话题是无效话题");
        }
        return ResponseUtil.ok(topicList);
    }

    @GetMapping("/topics/{id}")
    public Object detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return ResponseUtil.fail(660,"用户未登录");
        }

        // 参数校验
        if (id == null || id < 0) {
            return ResponseUtil.fail(654,"话题查看失败");
        }

        MallTopic topic = topicService.findNotDeletedTopicById(id);
        if(topic==null){
            return ResponseUtil.fail(650,"该话题是无效话题");
        }
        return ResponseUtil.ok(topic);
    }

    @GetMapping("/admin/topics/{id}")
    public Object admindetail(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return ResponseUtil.fail(669,"管理员未登录");
        }

        // 参数校验
        if (id == null || id < 0) {
            return ResponseUtil.fail(654,"话题查看失败");
        }

        MallTopic topic = topicService.findNotDeletedTopicById(id);
        if(topic==null){
            return ResponseUtil.fail(650,"该话题是无效话题");
        }
        return ResponseUtil.ok(topic);
    }

    @PostMapping("/topics")
    public Object create(@RequestBody TopicPo topicPo, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return ResponseUtil.fail(669,"管理员未登录");
        }

        //参数校验
        if((topicPo.getPicUrlList()==null)||(topicPo.getContent()==null)) {
            return ResponseUtil.fail(652,"话题添加失败");
        }

        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");

        try {
            MallTopic mallTopic=new MallTopic(topicPo);
            MallTopic resTopic = topicService.addTopic(mallTopic);
            writeLog(adminId, ip, INSERT, "添加专题", 1, resTopic.getId());
            return ResponseUtil.ok(resTopic.toTopicPo());
        } catch (Exception e) {
            writeLog(adminId, ip, INSERT, "添加专题", 0, null);
            return ResponseUtil.fail(652,"话题添加失败");
        }
    }

    @PutMapping("/topics/{id}")
    public Object update(@RequestBody TopicPo topicPo, @PathVariable Integer id,
                         HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return ResponseUtil.fail(669,"管理员未登录");
        }

        //参数校验
        if((topicPo.getPicUrlList()==null)||(topicPo.getContent()==null)) {
            return ResponseUtil.fail(651,"话题更新失败");
        }
        if(id==null || id < 0){
            return ResponseUtil.fail(651,"话题更新失败");
        }

        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");
        topicPo.setId(id);
        try {
            MallTopic result=topicService.updateTopic(new MallTopic(topicPo));
            if (result!=null) {
                writeLog(adminId, ip, UPDATE, "修改专题", 1, id);
                return ResponseUtil.ok(result.toTopicPo());
            }
            else {
                writeLog(adminId, ip, UPDATE, "修改专题", 0, id);
                return ResponseUtil.fail(650,"该话题是无效话题");
            }
        } catch(Exception e) {
            writeLog(adminId, ip, INSERT, "添加专题", 0, null);
            return ResponseUtil.fail(651,"话题更新失败");
        }
    }

    @DeleteMapping("/topics/{id}")
    public Object delete(@PathVariable Integer id,
                         HttpServletRequest request){
        Integer userId = getUserId(request);
        if (userId == null) {
            return ResponseUtil.fail(669,"管理员未登录");
        }

        // 参数校验
        if (id == null || id < 0) {
            return ResponseUtil.fail(653,"话题删除失败");
        }

        Integer adminId = Integer.valueOf(request.getHeader("userId"));
        String ip = request.getHeader("ip");

        try {
            Boolean result = topicService.deleteTopicById(id);
            if (result) {
                writeLog(adminId, ip, DELETE, "删除专题", 1, id);
                return ResponseUtil.ok();
            } else {
                writeLog(adminId, ip, DELETE, "删除专题", 0, id);
                return ResponseUtil.fail(650,"该话题是无效话题");
            }
        } catch(Exception e) {
            writeLog(adminId, ip, INSERT, "添加专题", 0, null);
            return ResponseUtil.fail(653,"话题删除失败");
        }
    }
}
