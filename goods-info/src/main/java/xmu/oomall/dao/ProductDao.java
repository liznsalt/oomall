package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.MallProduct;
import xmu.oomall.domain.MallProductPo;
import xmu.oomall.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 需要管理缓存，因为有PO，所以较为复杂，不能单纯用注解
 *
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
    public MallProduct addProduct(MallProduct product) {
        MallProductPo productPo = product.getRealObj();
        productMapper.addProduct(productPo);
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
    public MallProduct updateProduct(MallProduct product) {
        productMapper.updateProduct(product.getRealObj());
        product = findProductById(product.getId());
        return product;
    }

    /**
     * 通过产品ID检索产品
     * @param id 产品ID
     * @return 产品信息
     */
    public MallProduct findProductById(Integer id) {
        MallProductPo productPo = productMapper.findProductById(id);
        return new MallProduct(productPo);
    }

    /**
     * 通过产品ID检索其所有子产品
     * @param id 产品ID
     * @return 子产品列表
     */
    List<MallProduct> findSubProductsById(Integer id) {
        List<MallProductPo> productPoList = productMapper.findSubProductsById(id);
        return productPoList.stream().map(MallProduct::new).collect(Collectors.toList());
    }

    /**
     * 批量添加产品
     * @param productList 产品信息列表
     * @return 添加后的产品信息列表
     */
    public List<MallProduct> addProducts(List<MallProduct> productList){
        List<MallProductPo> productPoList = productList.stream().map(MallProduct::getRealObj).collect(Collectors.toList());
        productMapper.addProducts(productPoList);
        return productList;
    }
}
