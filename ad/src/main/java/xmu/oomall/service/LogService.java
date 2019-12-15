package xmu.oomall.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import standard.oomall.domain.Log;

/**
 * @author liznsalt
 */
@FeignClient("oomall-log")
@RequestMapping("/logService")
public interface LogService {
    /**
     * 添加日志
     * @param log 日志信息
     * @return /
     */
    @PostMapping("/log")
    Object addLog(@RequestBody Log log);
}
