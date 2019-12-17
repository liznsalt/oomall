package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.Product;
import xmu.oomall.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  TODO redis
 * @author liznsalt
 */
@Repository
public class ProductDao {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加产品
     * @param product 产品信息
     * @return 添加后的产品
     */
    public Product addProduct(Product product) {
        productMapper.addProduct(product);
        return product;
    }

    /**
     * 删除产品
     * @param id 产品ID
     */
    public void deleteProductById(Integer id) {
        productMapper.deleteProductById(id);
    }

    /**
     * 更新产品
     * @param product 产品信息
     * @return 更新的行数
     */
    public Product updateProduct(Product product) {
        productMapper.updateProduct(product);
        product = findProductById(product.getId());
        return product;
    }

    /**
     * 通过产品ID检索产品
     * @param id 产品ID
     * @return 产品信息
     */
    public Product findProductById(Integer id) {
        Product product = productMapper.findProductById(id);
        return product;
    }

    /**
     * 通过产品ID检索其所有子产品
     * @param id 产品ID
     * @return 子产品列表
     */
    List<Product> findSubProductsById(Integer id) {
        return productMapper.findSubProductsById(id);
    }

    /**
     * 批量添加产品
     * @param productList 产品信息列表
     * @return 添加后的产品信息列表
     */
    public List<Product> addProducts(List<Product> productList){
        List<Product> productPoList = productList.stream().map(Product::new).collect(Collectors.toList());
        productMapper.addProducts(productPoList);
        return productList;
    }
}
