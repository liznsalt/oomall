package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.MallBrand;
import xmu.oomall.domain.MallGoods;
import xmu.oomall.mapper.BrandMapper;

import java.util.List;

/**
 * @author liznsalt
 */
@CacheConfig(cacheNames = "brand")
@Repository
public class BrandDao {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 添加品牌
     * @param brand 品牌信息
     * @return 添加后的品牌信息
     */
    public MallBrand addBrand(MallBrand brand) {
        brandMapper.addBrand(brand);
        return brand;
    }

    /**
     * 删除品牌，级联将商品的品牌ID设NULL
     * @param id 品牌ID
     */
    @CacheEvict(key = "#p0")
    public void deleteBrandById(Integer id) {
        //TODO 级联处理缓存
        brandMapper.setBrandIdNull(id);
        brandMapper.deleteBrandById(id);
    }

    /**
     * 更新品牌
     * @param brand 品牌信息
     * @return 更新后的品牌
     */
    @CachePut(key = "#p0.id")
    public MallBrand updateBrand(MallBrand brand) {
        brandMapper.updateBrand(brand);
        return brand;
    }

    /**
     * 通过品牌ID检索品牌
     * @param id 品牌ID
     * @return 品牌信息
     */
    @Cacheable(key = "#p0")
    public MallBrand findBrandById(Integer id) {
        return brandMapper.findBrandById(id);
    }
}
