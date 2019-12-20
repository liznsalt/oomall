package xmu.oomall.service.impl;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import xmu.oomall.service.GoodsService;

/**
 * @author yanai
 */
@Component
public class GoodsServiceFactory implements FallbackFactory<GoodsService> {

    private final GoodsServiceFallbackImpl goodsServiceFallbackImpl;

    public GoodsServiceFactory(GoodsServiceFallbackImpl goodsServiceFallbackImpl) {
        this.goodsServiceFallbackImpl = goodsServiceFallbackImpl;
    }

    @Override
    public GoodsService create(Throwable throwable) {
        return goodsServiceFallbackImpl;
    }
}
