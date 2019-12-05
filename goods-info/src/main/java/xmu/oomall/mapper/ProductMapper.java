package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.domain.MallProductPo;

import java.util.List;

/**
 * @author liznsalt
 */
@Mapper
public interface ProductMapper {
    /**
     * 添加产品
     * @param product 产品信息
     * @return 添加后的产品ID
     */
    int addProduct(MallProductPo product);

    /**
     * 通过产品ID删除产品
     * @param id 产品ID
     * @return 删除的行数
     */
    int deleteProductById(Integer id);

    /**
     * 更新产品
     * @param product 产品信息
     * @return 更新的行数
     */
    int updateProduct(MallProductPo product);

    /**
     * 通过产品ID检索产品
     * @param id 产品ID
     * @return 产品信息
     */
    MallProductPo findProductById(Integer id);

    /**
     * 通过产品ID检索其所有子产品
     * @param id 产品ID
     * @return 子产品列表
     */
    List<MallProductPo> findSubProductsById(Integer id);
}
