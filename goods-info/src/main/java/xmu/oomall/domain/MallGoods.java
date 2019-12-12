package xmu.oomall.domain;

import org.apache.ibatis.type.Alias;
import standard.oomall.domain.Goods;

/**
 * @author liznsalt
 */
@Alias("mallGoods")
public class MallGoods extends Goods {
    public MallGoods(MallGoodsPo goodsPo) {
        this.setId(goodsPo.getId());
        this.setGmtCreate(goodsPo.getGmtCreate());
        this.setGmtModified(goodsPo.getGmtModified());
        this.setName(goodsPo.getName());
        this.setGoodsSn(goodsPo.getGoodsSn());
        this.setShortName(goodsPo.getShortName());
        this.setDescription(goodsPo.getDescription());
        this.setBrief(goodsPo.getBrief());
        this.setPicUrl(goodsPo.getPicUrl());
        this.setDetail(goodsPo.getDetail());
        this.setStatusCode(goodsPo.getStatusCode());
        this.setShareUrl(goodsPo.getShareUrl());
        this.setGallery(goodsPo.getGallery());
        this.setGoodsCategoryId(goodsPo.getGoodsCategoryId());
        this.setBrandId(goodsPo.getBrandId());
        this.setBeDeleted(goodsPo.getBeDeleted());
        this.setWeight(goodsPo.getWeight());
        this.setVolume(goodsPo.getVolume());
        this.setSpecialFreightId(goodsPo.getSpecialFreightId());
        this.setBeSpecial(goodsPo.getBeSpecial());
        this.setPrice(goodsPo.getPrice());
    }

    public MallGoodsPo getMallGoodsPo() {
        MallGoodsPo mallGoodsPo = new MallGoodsPo();
        mallGoodsPo.setId(this.getId());
        mallGoodsPo.setGmtCreate(this.getGmtCreate());
        mallGoodsPo.setGmtModified(this.getGmtModified());
        mallGoodsPo.setName(this.getName());
        mallGoodsPo.setGoodsSn(this.getGoodsSn());
        mallGoodsPo.setShortName(this.getShortName());
        mallGoodsPo.setDescription(this.getDescription());
        mallGoodsPo.setBrief(this.getBrief());
        mallGoodsPo.setPicUrl(this.getPicUrl());
        mallGoodsPo.setDetail(this.getDetail());
        mallGoodsPo.setStatusCode(this.getStatusCode());
        mallGoodsPo.setShareUrl(this.getShareUrl());
        mallGoodsPo.setGallery(this.getGallery());
        mallGoodsPo.setGoodsCategoryId(this.getGoodsCategoryId());
        mallGoodsPo.setBrandId(this.getBrandId());
        mallGoodsPo.setBeDeleted(this.getBeDeleted());
        mallGoodsPo.setWeight(this.getWeight());
        mallGoodsPo.setVolume(this.getVolume());
        mallGoodsPo.setSpecialFreightId(this.getSpecialFreightId());
        mallGoodsPo.setBeSpecial(this.getBeSpecial());
        mallGoodsPo.setPrice(this.getPrice());
        return mallGoodsPo;
    }
}
