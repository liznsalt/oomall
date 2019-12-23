package xmu.oomall.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import standard.oomall.domain.Log;
import xmu.oomall.service.impl.LogServiceFactory;

/**
 * @author liznsalt
 */
@Component
@FeignClient(name = "logService",
        decode404 = true,
        fallbackFactory = LogServiceFactory.class,
        configuration = FeignClientsConfiguration.class)
public interface LogService {
    /**
     * 添加日志
     * @param log 日志信息
     * @return /
     */
    @PostMapping("/log")
    Object addLog(@RequestBody Log log);
}
