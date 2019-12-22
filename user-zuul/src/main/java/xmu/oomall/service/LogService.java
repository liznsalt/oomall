package xmu.oomall.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import xmu.oomall.service.impl.LogServiceFactory;
import xmu.oomall.service.impl.LogServiceFallBackImpl;

/**
 * @author liznsalt
 */
@Component
@FeignClient(
        name = "logService",
        url = "http://101.132.152.28:3410",
        decode404 = true,
        fallbackFactory = LogServiceFactory.class,
        configuration = FeignClientsConfiguration.class
)
public interface LogService {
    /**
     * 添加日志
     * @param log 日志
     * @return 结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/log")
    Object addLog(@RequestBody Log log);
}
