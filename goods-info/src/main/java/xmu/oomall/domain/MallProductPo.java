package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Product;

import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
@Alias("mallProductPo")
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
