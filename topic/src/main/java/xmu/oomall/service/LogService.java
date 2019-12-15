package xmu.oomall.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import xmu.oomall.service.impl.LogServiceFactory;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@FeignClient(name = "oomall-log", decode404 = true,
        fallbackFactory = LogServiceFactory.class,
        configuration = FeignClientsConfiguration.class
//            fallback = GoodsServiceFallback.class
            )
@RequestMapping("/logService")
public interface LogService {
    /**
     * 添加日志
     * @param log 日志信息
     * @return /
     */
    @PostMapping("/logs")
    Object addLog(@RequestBody Log log);
}
