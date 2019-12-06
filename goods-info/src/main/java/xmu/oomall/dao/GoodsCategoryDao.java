package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.MallGoodsCategory;
import xmu.oomall.mapper.GoodsCategoryMapper;

import java.util.List;

/**
 * @author liznsalt
 */
@CacheConfig(cacheNames = "goodsCategory")
@Repository
public class GoodsCategoryDao {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    /**
     * 添加商品种类
     * @param goodsCategory 商品种类信息
     * @return 添加后的商品种类
     */
    public MallGoodsCategory addGoodsCategory(MallGoodsCategory goodsCategory) {
        goodsCategoryMapper.addGoodsCategory(goodsCategory);
        return goodsCategory;
    }
    /**
     * 删除种类，级联将商品的种类ID设NULL
     * @param id 种类ID
     */
    @CacheEvict(key = "#p0")
    public void deleteGoodsCategoryById(Integer id) {
        //TODO 级联处理缓存
        goodsCategoryMapper.setGoodsCategoryIdNull(id);
        goodsCategoryMapper.deleteGoodsCategoryById(id);
    }

    /**
     * 更新商品种类信息
     * @param goodsCategory 商品种类信息
     * @return 更新后的商品种类
     */
    @CachePut(key = "#p0.id")
    public MallGoodsCategory updateGoodsCategory(MallGoodsCategory goodsCategory) {
        goodsCategoryMapper.updateGoodsCategory(goodsCategory);
        return goodsCategory;
    }

    /**
     * 根据商品种类ID检索商品种类
     * @param id 商品种类ID
     * @return 商品种类信息
     */
    @Cacheable(key = "#p0")
    public MallGoodsCategory findGoodsCategoryById(Integer id) {
        return goodsCategoryMapper.findGoodsCategoryById(id);
    }

    /**
     * 得到所有一级分类
     * @return 一级分类列表
     */
    @Cacheable(cacheNames = "L1")
    public List<MallGoodsCategory> findAllGoodsCategoriesOfL1() {
        return goodsCategoryMapper.findAllGoodsCategoriesOfL1();
    }
}
