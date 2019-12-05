package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.domain.MallGoods;
import xmu.oomall.domain.MallGoodsCategory;

import java.util.List;

/**
 * @author liznsalt
 */
@Mapper
public interface GoodsCategoryMapper {
    /**
     * 添加商品种类
     * @param goodsCategory 商品种类信息
     * @return 添加后的商品种类ID
     */
    int addGoodsCategory(MallGoodsCategory goodsCategory);

    /**
     * 根据种类ID删除种类
     * @param id 种类ID
     * @return 删除的行数
     */
    int deleteGoodsCategoryById(Integer id);

    /**
     * 更新商品种类信息
     * @param goodsCategory 商品种类信息
     * @return 更新的行数
     */
    int updateGoodsCategory(MallGoodsCategory goodsCategory);

    /**
     * 根据商品种类ID检索商品种类
     * @param id 商品种类ID
     * @return 商品种类信息
     */
    MallGoodsCategory findGoodsCategoryById(Integer id);

    /**
     * 得到所有商品种类
     * @return 种类列表
     */
    List<MallGoodsCategory> getAllGoodsCategories();

    /**
     * 根据商品种类ID得到所有属于这个种类的商品
     * @param id 商品种类ID
     * @return 商品列表
     * @deprecated 似乎不能实现，先不做
     */
    List<MallGoods> findAllGoodsById(Integer id);

    /**
     * 根据分类ID得到其下所有子分类
     * @param id 分类ID
     * @return 子分类列表
     */
    List<MallGoodsCategory> findSubGoodsCategoriesById(Integer id);

    /**
     * 得到所有一级分类
     * @return 一级分类列表
     */
    List<MallGoodsCategory> findAllGoodsCategoriesOfL1();
}
