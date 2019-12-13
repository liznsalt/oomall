package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.Goods;
import xmu.oomall.domain.Product;
import xmu.oomall.domain.ProductPo;
import xmu.oomall.mapper.BrandMapper;
import xmu.oomall.mapper.GoodsCategoryMapper;
import xmu.oomall.mapper.GoodsMapper;
import xmu.oomall.mapper.ProductMapper;
import xmu.oomall.service.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liznsalt
 * @author YaNai
 */
@Repository
public class GoodsDao {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 增加商品
     * @param goods 商品信息
     * @return 添加后的商品信息
     */
    public Goods addGoods(Goods goods) {
        goodsMapper.addGoods(goods);
        for (ProductPo productPo : goods.getProductPoList()) {
            Product product = new Product(productPo);
            product.setGoodsId(goods.getId());
            productMapper.addProduct(product);
            productPo.setId(product.getId());
        }
        return goods;
    }

    /**
     * 删除商品，并将其子产品删除
     * @param id 商品ID
     */
    public void deleteGoodsById(Integer id) {
        redisService.remove("GOODS" + id);
        redisService.remove("PRODUCTS_OF_GOODS" + id);
        deleteProductsByGoodsId(id);
        goodsMapper.deleteGoodsById(id);
    }

    /**
     * 更新商品
     * @param goods 商品信息
     * @return 修改后的商品信息
     */
    public Goods updateGoods(Goods goods) {
        if (goods.getId() == null) {
            return null;
        }
        goodsMapper.updateGoods(goods);
        redisService.remove("GOODS" + goods.getId());
        goods = findGoodsById(goods.getId());
        return goods;
    }

    /**
     * 通过商品ID检索商品
     * @param id 商品ID
     * @return 商品信息
     */
    public Goods findGoodsById(Integer id) {
        String key = "GOODS" + id;
        Goods goods = (Goods) redisService.get(key);
        if (goods == null) {
            goods = goodsMapper.findGoodsById(id);
            if (goods == null) {
                return null;
            }
            List<Product> productList = findProductsById(id);
            goods.setProductPoList(productList.stream().map(Product::getProductPo).collect(Collectors.toList()));
            redisService.set(key, goods);
        }
        return goods;
    }

    /**
     * 通过商品ID检索其下所有产品
     * @param id 商品ID
     * @return 产品列表
     */
    public List<Product> findProductsById(Integer id) {
        String key = "PRODUCTS_OF_GOODS" + id;
        List<Product> productList = (List<Product>) redisService.get(key);
        if (productList == null) {
            productList = goodsMapper.findProductsById(id);
            if (productList == null) {
                return null;
            }
            redisService.set(key, productList);
        }
        return productList;
    }

    /**
     * 通过条件返回商品列表
     */
    public List<Goods> findGoodsByCondition(String goodsSn, String goodsName,
                                            Integer status, Integer page,
                                            Integer limit) {
        if (page <= 0 || limit <= 0) {
            return new ArrayList<>();
        }
        return goodsMapper.findGoodsByCondition(goodsSn, goodsName, status, page, limit);
    }

    public void deleteProductsByGoodsId(Integer id) {
        List<Product> productList = goodsMapper.findProductsById(id);
        for (Product product : productList) {
            redisService.remove("BRAND" + product.getId());
        }
        goodsMapper.deleteProductsByGoodsId(id);
    }
}
