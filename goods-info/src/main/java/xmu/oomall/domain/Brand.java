package xmu.oomall.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:品牌对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Alias("brand")
public class Brand extends BrandPo {

    private List<GoodsPo> goodsPoList;

    public Brand(){

    }

    public Brand(BrandPo brandPo){
        this.setId(brandPo.getId());
        this.setName(brandPo.getName());
        this.setDescription(brandPo.getDescription());
        this.setPicUrl(brandPo.getPicUrl());
        this.setGmtCreate(brandPo.getGmtCreate());
        this.setGmtModified(brandPo.getGmtModified());
        this.setBeDeleted(brandPo.getBeDeleted());
    }

    public BrandPo getBrandPo(){
        BrandPo brandPo = new BrandPo();
        brandPo.setId(this.getId());
        brandPo.setName(this.getName());
        brandPo.setDescription(this.getDescription());
        brandPo.setPicUrl(this.getPicUrl());
        brandPo.setGmtCreate(this.getGmtCreate());
        brandPo.setGmtModified(this.getGmtModified());
        brandPo.setBeDeleted(this.getBeDeleted());
        return brandPo;
    }
}
