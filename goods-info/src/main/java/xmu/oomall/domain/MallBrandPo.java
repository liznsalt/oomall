package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.BrandPo;

@Alias("mallBrandPo")
public class MallBrandPo extends BrandPo {
    public MallBrand setToMallBrand(){
        MallBrand brand = new MallBrand();
        brand.setId(this.getId());
        brand.setName(this.getName());
        brand.setDescription(this.getDescription());
        brand.setPicUrl(this.getPicUrl());
        brand.setGmtCreate(this.getGmtCreate());
        brand.setGmtModified(this.getGmtModified());
        brand.setBeDeleted(this.getBeDeleted());
        return brand;
    }
}
