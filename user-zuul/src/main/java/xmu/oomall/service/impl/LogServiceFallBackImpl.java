package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import standard.oomall.domain.Log;
import xmu.oomall.service.LogService;

/**
 * @author liznsalt
 */
@Component
@RequestMapping("/fallback/logService")
public class LogServiceFallBackImpl implements LogService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object addLog(Log log) {
        logger.info("添加日志失败");
        return CommonResult.serious();
    }
}
