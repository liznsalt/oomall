package xmu.oomall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import standard.oomall.domain.*;
import xmu.oomall.domain.MallGoods;
import xmu.oomall.domain.MallGoodsPo;
import xmu.oomall.domain.MallProduct;
import xmu.oomall.domain.MallProductPo;
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
    public MallGoods addGoods(MallGoods goods) {
        MallGoodsPo mallGoodsPo = goods.getMallGoodsPo();
        goodsMapper.addGoods(mallGoodsPo);
        goods.setId(mallGoodsPo.getId());
        goods.getProductPoList().forEach(po -> po.setGoodsId(goods.getId()));
        List<MallProductPo> mallProductPoList = goods.getProductPoList()
                .stream()
                .map(po -> (MallProductPo)po)
                .collect(Collectors.toList());
        productMapper.addProducts(mallProductPoList);
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
        goodsMapper.updateGoods(goods.getMallGoodsPo());
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
            MallGoodsPo goodsPo = goodsMapper.findGoodsById(id);
            if (goodsPo == null) {
                return null;
            }
            goods = new MallGoods(goodsPo);
            redisService.set(key, goods);
        }
        return goods;
    }

    /**
     * 通过商品ID检索其下所有产品
     * @param id 商品ID
     * @return 产品列表
     */
    public List<MallProductPo> findProductsById(Integer id) {
        List<MallProductPo> productList = goodsMapper.findProductsById(id);
        return productList;
    }

    /**
     * 通过条件返回商品列表
     */
    public List<MallGoods> findGoodsByCondition(Integer page, Integer limit) {
        if (page <= 0 || limit <= 0) {
            return new ArrayList<>();
        }
        return goodsMapper.findGoodsByCondition(page, limit)
                .stream()
                .map(MallGoods::new)
                .collect(Collectors.toList());
    }
}
