package xmu.oomall.domain;

import xmu.oomall.domain.standard.Goods;

import java.util.List;

/**
 * @author liznsalt
 */
public class MallGoods extends Goods {
    private List<String> galleryUrlList;
    private MallBrand brand;
    private MallGoodsCategory goodsCategory;
    private List<MallProduct> products;
}
