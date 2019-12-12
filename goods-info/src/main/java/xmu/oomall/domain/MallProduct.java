package xmu.oomall.domain;


import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
@Alias("mallProduct")
public class MallProduct extends Product{
    public MallProduct(MallProductPo productPo)
    {
        this.setBeDeleted(productPo.getBeDeleted());
        this.setGmtCreate(productPo.getGmtCreate());
        this.setGmtModified(productPo.getGmtModified());
        this.setGoodsId(productPo.getGoodsId());
        this.setId(productPo.getId());
        this.setPicUrl(productPo.getPicUrl());
        this.setPrice(productPo.getPrice());
        this.setSafetyStock(productPo.getSafetyStock());
        this.setSpecifications(productPo.getSpecifications());
    }
}
