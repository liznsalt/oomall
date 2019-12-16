package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.MallLog;
import xmu.oomall.service.LogService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liznsalt
 */
@RestController
@RequestMapping("/logService")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    private Integer getUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("userId");
        if (userIdStr == null) {
            return null;
        }
        return Integer.valueOf(userIdStr);
    }

    // 内部接口

    @PostMapping("/log")
    public Object addLog(@RequestBody MallLog log) {
        if (log == null) {
            return CommonResult.badArgumentValue("log不能为空");
        }
        MallLog newLog = logService.addLog(log);
        return CommonResult.success(newLog);
    }

    // 外部接口

    @GetMapping("/logs")
    public Object list(@RequestParam(required = false) Integer adminId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       HttpServletRequest request) {
        Integer loginAdminId = getUserId(request);
        if (loginAdminId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (page == null || limit == null || page <= 0 || limit <= 0) {
            return CommonResult.badArgumentValue();
        }

        if (adminId == null) {
            return CommonResult.success(logService.findLogsByCondition(page, limit));
        } else {
            return CommonResult.success(logService.findByAdminId(page, limit, adminId));
        }
    }
}
