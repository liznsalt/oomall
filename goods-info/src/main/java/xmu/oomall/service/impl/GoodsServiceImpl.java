package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import xmu.oomall.dao.GoodsDao;
import xmu.oomall.dao.ProductDao;
import xmu.oomall.domain.MallGoods;
import xmu.oomall.domain.MallProduct;
import xmu.oomall.mapper.GoodsMapper;
import xmu.oomall.service.GoodsService;

public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Boolean goodsOn(MallGoods goods) {
        if (goods.getId() == null) {
            return false;
        }
        goods.setStatusCode(false);
        goodsDao.updateGoods(goods);
        return true;
    }

    @Override
    public Boolean goodsOff(MallGoods goods) {
        if (goods.getId() == null) {
            return false;
        }
        goods.setStatusCode(true);
        goodsDao.updateGoods(goods);
        return true;
    }

    @Override
    public Integer getStockInDB(Integer id) {
        MallProduct product = productDao.findProductById(id);
        return product.getSaftyStock();
    }

    @Override
    public void updateStockInDB(Integer id, Integer quantity) {
        MallProduct product = productDao.findProductById(id);
        product.setSaftyStock(quantity);
        productDao.updateProduct(product);
    }

    @Override
    public MallGoods findGoodsById(Integer id) {
        return goodsDao.findGoodsById(id);
    }
}
