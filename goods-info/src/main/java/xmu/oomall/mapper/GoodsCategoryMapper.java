package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallGoodsCategoryPo;
import xmu.oomall.domain.MallGoodsPo;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface GoodsCategoryMapper {
    /**
     * 添加商品种类
     * @param goodsCategory 商品种类信息
     * @return 行数
     */
    int addGoodsCategory(MallGoodsCategoryPo goodsCategory);

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
    int updateGoodsCategory(MallGoodsCategoryPo goodsCategory);

    /**
     * 根据商品种类ID检索商品种类
     * @param id 商品种类ID
     * @return 商品种类信息
     */
    MallGoodsCategoryPo findGoodsCategoryById(Integer id);

    /**
     * 得到所有商品种类
     * @return 种类列表
     */
    List<MallGoodsCategoryPo> getAllGoodsCategories();

    /**
     * 根据商品种类ID得到所有属于这个种类的商品
     * @param id 商品种类ID
     * @return 商品列表
     */
    List<MallGoodsPo> findAllGoodsById(Integer id);

    /**
     * 根据分类ID得到其下所有子分类
     * @param id 分类ID
     * @return 子分类列表
     */
    List<MallGoodsCategoryPo> findSubGoodsCategoriesById(Integer id);

    /**
     * 得到所有一级分类
     * @return 一级分类列表
     */
    List<MallGoodsCategoryPo> findAllGoodsCategoriesOfL1();

    /**
     * 级联将该类别下的所有商品的类别ID设为NULL
     * @param id 类别ID
     * @return 行数
     */
    int setGoodsCategoryIdNull(Integer id);
}
