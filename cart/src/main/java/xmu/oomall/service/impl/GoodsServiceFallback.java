package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import standard.oomall.domain.Product;
import xmu.oomall.service.GoodsService;

@Component
@RequestMapping("/fallback/goodsService")
public class GoodsServiceFallback implements GoodsService {
    @Override
    public Object getProductById(Integer id) {
//        Product product = new Product();
//        product.setId(1);
        return CommonResult.serious();
    }
}
