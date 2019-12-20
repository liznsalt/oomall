package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import standard.oomall.domain.Log;
import xmu.oomall.service.LogService;

/**
 * @author Sky_Fire
 */
@Component
@RequestMapping("/fallback/logService")
public class LogServiceFallbackImpl implements LogService {
    @Override
    public Object addLog(@RequestBody Log log){ return CommonResult.serious();}

}
