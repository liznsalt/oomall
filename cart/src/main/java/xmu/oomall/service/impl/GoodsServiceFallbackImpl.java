package xmu.oomall.service.impl;

import common.oomall.api.CommonResult;
import common.oomall.util.ResponseUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import standard.oomall.domain.Product;
import xmu.oomall.service.GoodsService;

/**
 * @author yanai
 */
@Component
@RequestMapping("/fallback/goods")
public class GoodsServiceFallbackImpl implements GoodsService {
    @Override
    public Object getProductById(Integer id) {
        Product product = new Product();
        product.setId(-1);
        return ResponseUtil.ok(product);
    }
}
