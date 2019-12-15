package xmu.oomall.service.impl;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import xmu.oomall.service.LogService;

@Component
public class LogServiceFactory implements FallbackFactory<LogService> {

    private final LogServiceFallback logServiceFallback;

    public LogServiceFactory(LogServiceFallback logServiceFallback) {
        this.logServiceFallback = logServiceFallback;
    }

    @Override
    public LogService create(Throwable throwable) {
        return logServiceFallback;
    }
}
