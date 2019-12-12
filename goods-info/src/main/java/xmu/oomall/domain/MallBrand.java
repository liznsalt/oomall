package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Brand;

/**
 * @author liznsalt
 */
@Alias("mallBrand")
public class MallBrand extends Brand {
    public MallBrandPo setToMallBrandPo(){
         MallBrandPo brandPo = new MallBrandPo();
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
