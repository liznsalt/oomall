package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.GoodsCategory;

/**
 * @author liznsalt
 */
@Alias("mallGoodsCategory")
public class MallGoodsCategory extends GoodsCategory {
    public MallGoodsCategory(MallGoodsCategoryPo goodsCategoryPo){
        this.setId(goodsCategoryPo.getId());
        this.setName(goodsCategoryPo.getName());
        this.setPid(goodsCategoryPo.getPid());
        this.setGmtCreate(goodsCategoryPo.getGmtCreate());
        this.setGmtModified(goodsCategoryPo.getGmtModified());
        this.setBeDeleted(goodsCategoryPo.getBeDeleted());
    }
}
