package xmu.oomall.service;

import xmu.oomall.domain.*;

import java.util.List;

/**
 * @author liznsalt
 * @author YaNai
 */
public interface GoodsService {
    Boolean goodsOn(MallGoods goods);
    Boolean goodsOff(MallGoods goods);
    Integer getStockInDB(Integer id);
    void updateStockInDB(Integer id, Integer quantity);
    MallGoods findGoodsById(Integer id);
    MallProduct addProductByGoodsId(Integer id, MallProduct product);
    MallProduct updateProductById(Integer id, MallProduct product);
    Boolean deleteProductById(Integer id);
    MallGoods addGoods(MallGoods goods);
    MallGoods updateGoodsById(Integer id, MallGoods goods);
    Boolean deleteGoodsById(Integer id);
    List<MallGoods> getCategoriesInfoById(Integer id);
    MallBrand addBrand(MallBrand brand);
    MallBrand findBrandById(Integer id);
    MallBrand updateBrandById(Integer id, MallBrand brand);
    Boolean deleteBrandById(Integer id);
    List<MallGoodsCategory> getGoodsCategory();
    MallGoodsCategory addGoodsCategory(MallGoodsCategory goodsCategory);
    MallGoodsCategory findGoodsCategoryById(Integer id);
    MallGoodsCategory updateGoodsCategoryById(Integer id, MallGoodsCategory goodsCategory);
    Boolean deleteGoodsCategoryById(Integer id);
    List<MallGoodsCategory> getOneLevelGoodsCategory();
}
