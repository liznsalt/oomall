package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.MallGoods;
import xmu.oomall.domain.MallProduct;
import xmu.oomall.domain.MallProductPo;
import xmu.oomall.mapper.BrandMapper;
import xmu.oomall.mapper.GoodsCategoryMapper;
import xmu.oomall.mapper.GoodsMapper;
import xmu.oomall.mapper.ProductMapper;
import xmu.oomall.service.RedisService;

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
    public void deleteGoodsById(Integer id) {
        String key = "G_" + id;
        redisService.remove(key);
        goodsMapper.deleteProductsByGoodsId(id);
        goodsMapper.deleteGoodsById(id);
    }

    /**
     * 更新商品
     * @param goods 商品信息
     * @return 修改后的商品信息
     */
    public MallGoods updateGoods(MallGoods goods) {
        if (goods.getId() == null) {
            return null;
        }
        goodsMapper.updateGoods(goods);
        goods = findGoodsById(goods.getId());
        return goods;
    }

    /**
     * 通过商品ID检索商品
     * @param id 商品ID
     * @return 商品信息
     */
    public MallGoods findGoodsById(Integer id) {
        String key = "G_" + id;
        MallGoods goods = (MallGoods) redisService.get(key);
        if (goods == null) {
            goods = goodsMapper.findGoodsById(id);
            List<MallProduct> productList = findProductsById(id);
            goods.setProducts(productList);
            redisService.set(key, goods);
        }
        return goods;
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

    /**
     * 通过条件返回商品列表
     */
    public List<MallGoods> findGoodsByCondition() {
        return null;
    }
}
