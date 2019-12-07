package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.GoodsCategory;

/**
 * @author liznsalt
 */
@Alias("mallGoodsCategory")
public class MallGoodsCategory extends GoodsCategory {
    private MallGoodsCategory parentCategory;

    /****************************************************
     * 生成代码
     ****************************************************/

    public MallGoodsCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(MallGoodsCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
}
