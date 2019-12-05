package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.MallProduct;
import xmu.oomall.mapper.ProductMapper;

import java.util.List;

/**
 * @author liznsalt
 */
@Repository
public class ProductDao {

    /**
     * 添加产品
     * @param product 产品信息
     * @return 添加后的产品ID
     */
    public int addProduct(MallProduct product) {
        return 1;
    }

    /**
     * 更新产品
     * @param product 产品信息
     * @return 更新的行数
     */
    public int updateProduct(MallProduct product) {
        return 0;
    }

    /**
     * 通过产品ID检索产品
     * @param id 产品ID
     * @return 产品信息
     */
    public MallProduct findProductById(Integer id) {
        return null;
    }

    /**
     * 通过产品ID检索其所有子产品
     * @param id 产品ID
     * @return 子产品列表
     */
    List<MallProduct> findSubProductsById(Integer id) {
        return null;
    }
}
