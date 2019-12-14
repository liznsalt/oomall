package xmu.oomall.service.impl;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import xmu.oomall.service.GoodsService;

@Component
public class GoodsServiceFactory implements FallbackFactory<GoodsService> {

    private final GoodsServiceFallback goodsServiceFallback;

    public GoodsServiceFactory(GoodsServiceFallback goodsServiceFallback) {
        this.goodsServiceFallback = goodsServiceFallback;
    }

    @Override
    public GoodsService create(Throwable throwable) {
//        throwable.printStackTrace();
        return goodsServiceFallback;
    }
}
