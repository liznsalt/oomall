package xmu.oomall.service;

import xmu.oomall.domain.*;

import java.util.List;

/**
 * @author liznsalt
 * @author YaNai
 */
public interface GoodsService {
    Boolean goodsOn(Goods goods);
    Boolean goodsOff(Goods goods);
    Integer getStockInDB(Integer id);
    void updateStockInDB(Integer id, Integer quantity);
    Goods findAllGoodsById (Integer id);
    Goods findGoodsById(Integer id);
    Product addProductByGoodsId(Integer id, Product product);
    Product findProductById(Integer id);
    Product updateProductById(Integer id, Product product);
    Boolean deleteProductById(Integer id);
    Goods addGoods(Goods goods);
    Goods updateGoodsById(Integer id, Goods goods);
    Boolean deleteGoodsById(Integer id);
    List<GoodsPo> getCategoriesInfoById(Integer id, Integer page, Integer limit);
    Brand addBrand(Brand brand);
    Brand findBrandById(Integer id);
    Brand updateBrandById(Integer id, Brand brand);
    List<GoodsPo> getGoodsByBrandId(Integer id);
    Boolean deleteBrandById(Integer id);
    List<GoodsCategoryPo> getGoodsCategory(Integer page, Integer limit);
    GoodsCategory addGoodsCategory(GoodsCategory goodsCategory);
    GoodsCategory findGoodsCategoryById(Integer id);
    GoodsCategory updateGoodsCategoryById(Integer id, GoodsCategory goodsCategory);
    Boolean deleteGoodsCategoryById(Integer id);
    List<GoodsCategory> getOneLevelGoodsCategory();
}
