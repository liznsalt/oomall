package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.Brand;
import xmu.oomall.domain.BrandPo;
import xmu.oomall.domain.Goods;
import xmu.oomall.domain.GoodsPo;
import xmu.oomall.mapper.BrandMapper;
import xmu.oomall.service.RedisService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liznsalt
 */

@Repository
public class BrandDao {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private RedisService redisService;
    /**
     * 添加品牌
     * @param brand 品牌信息
     * @return 添加后的品牌信息
     */
    public Brand addBrand(Brand brand) {
        brandMapper.addBrand(brand);
        return brand;
    }

    /**
     * 删除品牌，级联将商品的品牌ID设NULL
     * @param id 品牌ID
     */

    public boolean deleteBrandById(Integer id) {
        //TODO 级联处理缓存
        if((findBrandById(id)==null)||findBrandById(id).getBeDeleted()) {
            return false;
        }
        String key = "BRAND" + id;
        redisService.remove(key);
        List<GoodsPo> goodsPoList= brandMapper.findAllGoodsById(id);
        for(GoodsPo goodsPo:goodsPoList){
            Goods goods = new Goods(goodsPo);
            String goodsKey = "GOODS" + goods.getId();
            redisService.remove(goodsKey);
        }
        brandMapper.setBrandIdNull(id);
        brandMapper.deleteBrandById(id);
        return true;
    }

    /**
     * 更新品牌
     * @param brand 品牌信息
     * @return 更新后的品牌
     */
    public Brand updateBrand(Brand brand) {
        if(brand.getId()==null){
            return null;
        }
        // TODO 删除redis
        String key = "BRAND" + brand.getId();
        redisService.remove(key);
        brandMapper.updateBrand(brand);
        brand=findBrandById(brand.getId());
        return brand;
    }

    /**
     * 通过品牌ID检索品牌
     * @param id 品牌ID
     * @return 品牌信息
     */

    public Brand findBrandById(Integer id) {
        String key = "BRAND" + id;
        Brand brand = (Brand) redisService.get(key);
        if (brand == null) {
            brand = brandMapper.findBrandById(id);
            if (brand == null) {
                return null;
            }
            redisService.set(key, brand);
        }
        return brand;
    }

    /**
     * 分页得到品牌
     * @param page 页数
     * @param limit 每页行数
     * @return 商品列表
     */
    public List<BrandPo> findBrandsByCondition(Integer id, String name,
                                               Integer page, Integer limit){
        if (page <= 0 || limit <= 0) {
            return new ArrayList<>();
        }
        return brandMapper.findBrandsByCondition(id, name, page, limit);
    }

    /**
     * 通过品牌ID查找该品牌所有商品
     * @param id
     * @return
     */
    public List<GoodsPo> findGoodsByBrandId(Integer id){
        return brandMapper.findAllGoodsById(id);
    }
}
