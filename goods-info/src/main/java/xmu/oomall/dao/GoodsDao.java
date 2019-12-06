package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.MallGoods;
import xmu.oomall.domain.MallProduct;
import xmu.oomall.domain.MallProductPo;
import xmu.oomall.mapper.GoodsMapper;
import xmu.oomall.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liznsalt
 */
@CacheConfig(cacheNames = "goods")
@Repository
public class GoodsDao {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 增加商品
     * @param goods 商品信息
     * @return 添加后的商品信息
     */
    public MallGoods addGoods(MallGoods goods) {
        goodsMapper.addGoods(goods);
        //FIXME 添加其子产品
        goods.setProductsGoodsId();
        List<MallProductPo> productPoList = goods.getProducts().stream()
                .map(MallProduct::getRealObj).collect(Collectors.toList());
        productMapper.addProducts(productPoList);
        //TODO 设置其属性实体

        return goods;
    }

    /**
     * 删除商品，并将其子产品删除
     * @param id 商品ID
     */
    @CacheEvict(key = "#p0")
    public void deleteGoodsById(Integer id) {
        goodsMapper.deleteBrandsById(id);
        goodsMapper.deleteGoodsById(id);
    }

    /**
     * 更新商品
     * @param goods 商品信息
     * @return 修改后的商品信息
     */
    @CachePut(key = "#p0.id")
    public MallGoods updateGoods(MallGoods goods) {
        goodsMapper.updateGoods(goods);
        return goods;
    }

    /**
     * 通过商品ID检索商品
     * @param id 商品ID
     * @return 商品信息
     */
    @Cacheable(key = "#p0")
    public MallGoods findGoodsById(Integer id) {
        return goodsMapper.findGoodsById(id);
    }

    /**
     * 通过商品ID检索其下所有产品
     * @param id 商品ID
     * @return 产品列表
     */
    public List<MallProduct> findProductsById(Integer id) {
        List<MallProductPo> productPoList = goodsMapper.findProductsById(id);
        return productPoList.stream().map(MallProduct::new).collect(Collectors.toList());
    }
}
