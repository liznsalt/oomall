package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.ProductPo;

@Alias("mallProductPo")
public class MallProductPo extends ProductPo {
    public MallProductPo(MallProduct mallProduct)
    {
        this.setId(mallProduct.getId());
        this.setBeDeleted(mallProduct.getBeDeleted());
        this.setGmtModified(mallProduct.getGmtModified());
        this.setGmtCreate(mallProduct.getGmtCreate());
        this.setPrice(mallProduct.getPrice());
        this.setSpecifications(mallProduct.getSpecifications());
        this.setPicUrl(mallProduct.getPicUrl());
        this.setGoodsId(mallProduct.getGoodsId());
        this.setSafetyStock(mallProduct.getSafetyStock());
    }
    public MallProductPo() {    }
}
