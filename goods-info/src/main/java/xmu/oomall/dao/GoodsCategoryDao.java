package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.MallGoodsCategory;
import xmu.oomall.domain.MallGoodsCategoryPo;
import xmu.oomall.mapper.GoodsCategoryMapper;
import xmu.oomall.service.RedisService;

import java.util.List;

/**
 * @author liznsalt
 */
@Repository
public class GoodsCategoryDao {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 添加商品种类
     * @param goodsCategory 商品种类信息
     * @return 添加后的商品种类
     */
    public MallGoodsCategory addGoodsCategory(MallGoodsCategory goodsCategory) {
        MallGoodsCategoryPo goodsCategoryPo = new MallGoodsCategoryPo(goodsCategory);
        goodsCategoryMapper.addGoodsCategory(goodsCategoryPo);
        return goodsCategory;
    }

    /**
     * 删除种类，级联将商品的种类ID设NULL
     * @param id 种类ID
     */
    public void deleteGoodsCategoryById(Integer id) {
        //TODO 级联处理缓存
        String key = "C_" + id;
        redisService.remove(key);
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
        if (goodsCategory.getId() == null) {
            return null;
        }
        MallGoodsCategoryPo goodsCategoryPo = new MallGoodsCategoryPo(goodsCategory);
        goodsCategoryMapper.updateGoodsCategory(goodsCategoryPo);
        goodsCategory = findGoodsCategoryById(goodsCategory.getId());
        return goodsCategory;
    }

    /**
     * 根据商品种类ID检索商品种类
     * @param id 商品种类ID
     * @return 商品种类信息
     */
    public MallGoodsCategory findGoodsCategoryById(Integer id) {
        String key = "C_" + id;
        MallGoodsCategory goodsCategory = (MallGoodsCategory) redisService.get(key);
        MallGoodsCategoryPo goodsCategoryPo = new MallGoodsCategoryPo(goodsCategory);
        if (goodsCategory == null) {
            goodsCategoryPo = goodsCategoryMapper.findGoodsCategoryById(id);
            if (goodsCategory == null) {
                return null;
            }
            redisService.set(key, goodsCategory);
        }
        return new MallGoodsCategory(goodsCategoryPo);
        //return goodsCategoryMapper.findGoodsCategoryById(id);
    }

    /**
     * 得到所有一级分类
     * @return 一级分类列表
     */
    public List<MallGoodsCategoryPo> findAllGoodsCategoriesOfL1() {
        return goodsCategoryMapper.findAllGoodsCategoriesOfL1();
    }
}
