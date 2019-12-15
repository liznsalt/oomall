package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.Goods;
import xmu.oomall.domain.GoodsCategory;
import xmu.oomall.domain.GoodsPo;
import xmu.oomall.mapper.GoodsCategoryMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO redis
 * @author liznsalt
 */
@Repository
public class GoodsCategoryDao {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    /**
     * 添加商品种类
     * @param goodsCategory 商品种类信息
     * @return 添加后的商品种类
     */
    public GoodsCategory addGoodsCategory(GoodsCategory goodsCategory) {
        goodsCategoryMapper.addGoodsCategory(goodsCategory);
        return goodsCategory;
    }
    /**
     * 删除种类，级联将商品的种类ID设NULL
     * @param id 种类ID
     */
    public void deleteGoodsCategoryById(Integer id) {
        // TODO 级联处理缓存
        goodsCategoryMapper.setGoodsCategoryIdNull(id);
        goodsCategoryMapper.deleteGoodsCategoryById(id);
    }

    /**
     * 更新商品种类信息
     * @param goodsCategory 商品种类信息
     * @return 更新后的商品种类
     */
    public GoodsCategory updateGoodsCategory(GoodsCategory goodsCategory) {
        goodsCategoryMapper.updateGoodsCategory(goodsCategory);
        return goodsCategory;
    }

    /**
     * 根据商品种类ID检索商品种类
     * @param id 商品种类ID
     * @return 商品种类信息
     */
    public GoodsCategory findGoodsCategoryById(Integer id) {
        return goodsCategoryMapper.findGoodsCategoryById(id);
    }

    /**
     * 根据商品种类ID得到该种类所有商品
     * @param id 商品种类ID
     * @param page 第几页
     * @param limit 一页多少
     * @return List<Goods> 即是商品的一个列表
     */
    public List<GoodsPo> findGoodsByCategoryId(Integer id, Integer page, Integer limit) {
        if(page<=0||limit<=0){
            return new ArrayList<>();
        }
        return goodsCategoryMapper.findAllGoodsById(id,page,limit);
    }

    /**
     * 得到所有一级分类
     * @return 一级分类列表
     */
    public List<GoodsCategory> findAllGoodsCategoriesOfL1() {
        return goodsCategoryMapper.findAllGoodsCategoriesOfL1();
    }
}
