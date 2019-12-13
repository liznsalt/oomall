package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.GoodsCategoryPo;

@Alias("mallGoodsCategoryPo")
public class MallGoodsCategoryPo extends GoodsCategoryPo {
    public MallGoodsCategoryPo(MallGoodsCategory mallGoodsCategory)
    {
        this.setId(mallGoodsCategory.getId());
        this.setName(mallGoodsCategory.getName());
        this.setPid(mallGoodsCategory.getPid());
        this.setGmtModified(mallGoodsCategory.getGmtModified());
        this.setGmtCreate(mallGoodsCategory.getGmtCreate());
        this.setBeDeleted(mallGoodsCategory.getBeDeleted());
    }
}
