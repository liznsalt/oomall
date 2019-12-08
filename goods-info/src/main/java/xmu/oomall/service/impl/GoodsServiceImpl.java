package xmu.oomall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import xmu.oomall.dao.GoodsDao;
import xmu.oomall.domain.MallGoods;
import xmu.oomall.mapper.GoodsMapper;
import xmu.oomall.service.GoodsService;

public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean goodsOn(MallGoods goods) {
        if (goods.getId() == null) {
            return false;
        }
        goods.setStatusCode(false);
        goodsDao.updateGoods(goods);
        return true;
    }

    @Override
    public boolean goodsOff(MallGoods goods) {
        if (goods.getId() == null) {
            return false;
        }
        goods.setStatusCode(true);
        goodsDao.updateGoods(goods);
        return true;
    }
}
