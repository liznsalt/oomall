package xmu.oomall.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:二级目录对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Alias("goodsCategory")
public class GoodsCategory extends GoodsCategoryPo {
    private List<GoodsPo> goodsPoList;

    public GoodsCategory(){

    }

    public GoodsCategory(GoodsCategoryPo goodsCategoryPo){
        this.setId(goodsCategoryPo.getId());
        this.setName(goodsCategoryPo.getName());
        this.setPid(goodsCategoryPo.getPid());
        this.setGmtCreate(goodsCategoryPo.getGmtCreate());
        this.setGmtModified(goodsCategoryPo.getGmtModified());
        this.setBeDeleted(goodsCategoryPo.getBeDeleted());
        this.setPicUrl(goodsCategoryPo.getPicUrl());
    }

    public GoodsCategoryPo getGoodsCategoryPo(){
        GoodsCategoryPo goodsCategoryPo = new GoodsCategoryPo();
        goodsCategoryPo.setId(this.getId());
        goodsCategoryPo.setName(this.getName());
        goodsCategoryPo.setPid(this.getPid());
        goodsCategoryPo.setGmtCreate(this.getGmtCreate());
        goodsCategoryPo.setGmtModified(this.getGmtModified());
        goodsCategoryPo.setBeDeleted(this.getBeDeleted());
        goodsCategoryPo.setPicUrl(this.getPicUrl());
        return goodsCategoryPo;
    }

}
