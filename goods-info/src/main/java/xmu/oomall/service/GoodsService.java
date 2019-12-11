package xmu.oomall.service;

import xmu.oomall.domain.MallGoods;

/**
 * @author liznsalt
 * @author YaNai
 */
public interface GoodsService {
    Boolean goodsOn(MallGoods goods);
    Boolean goodsOff(MallGoods goods);
    Integer getStockInDB(Integer id);
    void updateStockInDB(Integer id, Integer quantity);
    MallGoods findGoodsById(Integer id);

}
