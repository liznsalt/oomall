package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import xmu.oomall.domain.standard.Goods;

import java.util.List;

/**
 * @author liznsalt
 */
@Alias("mallGoods")
public class MallGoods extends Goods {
    private MallBrand brand;
    private MallGoodsCategory goodsCategory;
    private List<MallProduct> products;

    public void setProductsGoodsId() {
        for (MallProduct product : products) {
            product.setGoodsId(this.getId());
        }
    }

    /****************************************************
     * 生成代码
     ****************************************************/

    public MallBrand getBrand() {
        return brand;
    }

    public void setBrand(MallBrand brand) {
        this.brand = brand;
    }

    public MallGoodsCategory getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(MallGoodsCategory goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public List<MallProduct> getProducts() {
        return products;
    }

    public void setProducts(List<MallProduct> products) {
        this.products = products;
    }
}
