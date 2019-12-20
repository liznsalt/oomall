package xmu.oomall.service.impl;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import xmu.oomall.service.LogService;

/**
 * @author liznsalt
 */
@Component
public class LogServiceFactory implements FallbackFactory<LogService> {

    private final LogServiceFallbackImpl logServiceFallbackImpl;

    public LogServiceFactory(LogServiceFallbackImpl logServiceFallbackImpl) {
        this.logServiceFallbackImpl = logServiceFallbackImpl;
    }

    @Override
    public LogService create(Throwable throwable) {
        return logServiceFallbackImpl;
    }
}
