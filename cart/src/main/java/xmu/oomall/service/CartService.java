package xmu.oomall.service;

import xmu.oomall.domain.MallCartItem;

import java.util.List;

/**
 * @author liznsalt
 */
public interface CartService {
    List<MallCartItem> list(Integer userId);
    MallCartItem add(Integer userId, MallCartItem cartItem);
    MallCartItem fastAdd(Integer userId, MallCartItem cartItem);
    MallCartItem update(Integer userId, MallCartItem cartItem);
    boolean delete(Integer id);
    int goodsCount(Integer userId);
    boolean clear(Integer userId);
    MallCartItem findCartItemById(Integer id);
}
