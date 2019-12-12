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
import xmu.oomall.service.RedisService;

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
    public MallBrand addBrand(MallBrand brand) {
        brandMapper.addBrand(brand.setToMallBrandPo());
        return brand;
    }

    /**
     * 删除品牌，级联将商品的品牌ID设NULL
     * @param id 品牌ID
     */

    public boolean deleteBrandById(Integer id) {
        //TODO 级联处理缓存
        if((findBrandById(id)==null)||findBrandById(id).getBeDeleted())
            return false;
        String key = "B_" + id;
        redisService.remove(key);
        brandMapper.setBrandIdNull(id);
        brandMapper.deleteBrandById(id);
        return true;
    }

    /**
     * 更新品牌
     * @param brand 品牌信息
     * @return 更新后的品牌
     */

    public MallBrand updateBrand(MallBrand brand) {
        if(brand.getId()==null){
            return null;
        }
        brandMapper.updateBrand(brand.setToMallBrandPo());
        brand=findBrandById(brand.getId());
        return brand;
    }

    /**
     * 通过品牌ID检索品牌
     * @param id 品牌ID
     * @return 品牌信息
     */

    public MallBrand findBrandById(Integer id) {
        String key = "B_" + id;
        MallBrand brand = (MallBrand) redisService.get(key);
        if (brand == null) {
            brand = brandMapper.findBrandById(id).setToMallBrand();
            if (brand == null) {
                return null;
            }
            redisService.set(key, brand);
        }
        return brand;
    }

    

}
