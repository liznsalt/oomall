package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import standard.oomall.domain.Log;
import xmu.oomall.service.LogService;

@Component
@RequestMapping("/fallback/logService")
public class LogServiceFallback implements LogService {
    @Override
    public Object addLog(Log log) {
        return CommonResult.serious();
    }
}
