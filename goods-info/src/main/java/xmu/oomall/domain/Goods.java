package xmu.oomall.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import standard.oomall.domain.GrouponRule;
import standard.oomall.domain.PresaleRule;
import standard.oomall.domain.ShareRule;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:商品对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Alias("mallGoods")
public class Goods extends GoodsPo {
    private BrandPo brandPo;
    private GoodsCategoryPo goodsCategoryPo;
    private List<ProductPo> productPoList;
    private GrouponRule grouponRule;
    private ShareRule shareRule;
    private PresaleRule presaleRule;

    public Goods(GoodsPo goodsPo) {
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

    public GoodsPo getGoodsPo() {
        GoodsPo goodsPo = new GoodsPo();
        goodsPo.setId(this.getId());
        goodsPo.setGmtCreate(this.getGmtCreate());
        goodsPo.setGmtModified(this.getGmtModified());
        goodsPo.setName(this.getName());
        goodsPo.setGoodsSn(this.getGoodsSn());
        goodsPo.setShortName(this.getShortName());
        goodsPo.setDescription(this.getDescription());
        goodsPo.setBrief(this.getBrief());
        goodsPo.setPicUrl(this.getPicUrl());
        goodsPo.setDetail(this.getDetail());
        goodsPo.setStatusCode(this.getStatusCode());
        goodsPo.setShareUrl(this.getShareUrl());
        goodsPo.setGallery(this.getGallery());
        goodsPo.setGoodsCategoryId(this.getGoodsCategoryId());
        goodsPo.setBrandId(this.getBrandId());
        goodsPo.setBeDeleted(this.getBeDeleted());
        goodsPo.setWeight(this.getWeight());
        goodsPo.setVolume(this.getVolume());
        goodsPo.setSpecialFreightId(this.getSpecialFreightId());
        goodsPo.setBeSpecial(this.getBeSpecial());
        goodsPo.setPrice(this.getPrice());
        return goodsPo;
    }
}