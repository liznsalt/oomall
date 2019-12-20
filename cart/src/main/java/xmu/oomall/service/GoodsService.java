package xmu.oomall.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xmu.oomall.service.impl.GoodsServiceFactory;

/**
 * @author yanai
 */
@Component
@FeignClient(name = "goodsService",
        decode404 = true,
        fallbackFactory = GoodsServiceFactory.class,
        configuration = FeignClientsConfiguration.class)
public interface GoodsService {
    /**
     * 通过产品id查找产品信息
     * @param id 产品id
     * @return 产品信息
     */
    @GetMapping("/products/{id}")
    Object getProductById(@PathVariable Integer id);
}
