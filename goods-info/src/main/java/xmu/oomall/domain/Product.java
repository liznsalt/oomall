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
    }
    public ProductPo getProductPo() {
        ProductPo productPo = new ProductPo();
        // TODO
        return productPo;
    }
}
