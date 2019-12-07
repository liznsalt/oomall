package xmu.oomall.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import standard.oomall.domain.Log;

import java.util.List;

/**
 * @author liznsalt
 */
@FeignClient("oomall-log")
public interface LogService {

    @PostMapping("logs")
    Object addLog(@RequestBody Log log);

    @GetMapping("logs")
    Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "gmt_create") String sort,
                       @RequestParam(defaultValue = "desc") String order);

    @GetMapping("logs/")
    Object list();
}
