package xmu.oomall.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:产品对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Alias("mallProduct")
public class Product extends ProductPo {
    private GoodsPo goodsPo;
    public Product() {

    }

    public Product(ProductPo productPo) {
        // TODO
        this.setId(productPo.getId());
        this.setPicUrl(productPo.getPicUrl());
        this.setSpecifications(productPo.getSpecifications());
        this.setGoodsId(productPo.getGoodsId());
        this.setPrice(productPo.getPrice());
        this.setSafetyStock(productPo.getSafetyStock());
        this.setGmtCreate(productPo.getGmtCreate());
        this.setGmtModified(productPo.getGmtModified());
        this.setBeDeleted(productPo.getBeDeleted());
    }
    public ProductPo getProductPo() {
        ProductPo productPo = new ProductPo();
        // TODO
        productPo.setId(this.getId());
        productPo.setPicUrl(this.getPicUrl());
        productPo.setSpecifications(this.getSpecifications());
        productPo.setGoodsId(this.getGoodsId());
        productPo.setPrice(this.getPrice());
        productPo.setSafetyStock(this.getSafetyStock());
        productPo.setGmtCreate(this.getGmtCreate());
        productPo.setGmtModified(this.getGmtModified());
        productPo.setBeDeleted(this.getBeDeleted());
        return productPo;
    }
}
