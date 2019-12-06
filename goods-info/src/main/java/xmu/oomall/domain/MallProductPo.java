package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import xmu.oomall.domain.standard.Product;

import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
@Alias("mallProduct")
public class MallProductPo extends Product {
    private MallGoods goods;

    /****************************************************
     * 生成代码
     ****************************************************/

    public MallGoods getGoods() {
        return goods;
    }

    public void setGoods(MallGoods goods) {
        this.goods = goods;
    }
}
