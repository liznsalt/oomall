package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallBrand;
import xmu.oomall.domain.MallGoods;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface BrandMapper {
    /**
     * 添加品牌
     * @param brand 品牌信息
     * @return 行数
     */
    int addBrand(MallBrand brand);

    /**
     * 根据品牌ID删除品牌
     * @param id 品牌ID
     * @return 删除行数
     */
    int deleteBrandById(Integer id);

    /**
     * 更新品牌
     * @param brand 品牌信息
     * @return 更新的行数
     */
    int updateBrand(MallBrand brand);

    /**
     * 通过品牌ID检索品牌
     * @param id 品牌ID
     * @return 品牌信息
     */
    MallBrand findBrandById(Integer id);

    /**
     * 得到所有品牌
     * @return 品牌列表
     */
    List<MallBrand> getAllBrands();

    /**
     * 根据品牌ID得到所有属于这个品牌的商品
     * @param id 品牌ID
     * @return 商品列表
     */
    List<MallGoods> findAllGoodsById(Integer id);

    /**
     * 通过品牌ID，将该品牌下所有商品的品牌ID属性设NULL
     * @param id 品牌ID
     * @return 修改行数
     */
    int setBrandIdNull(Integer id);
}
