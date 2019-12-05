package xmu.oomall.service;

import xmu.oomall.domain.MallGoods;

/**
 * @author liznsalt
 */
public interface GoodsService {
    boolean goodsOn(MallGoods goods);
    boolean goodsOff(MallGoods goods);
}
