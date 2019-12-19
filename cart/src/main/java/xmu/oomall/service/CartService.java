package xmu.oomall.service;

import xmu.oomall.domain.MallCartItem;

import java.util.List;

/**
 * @author liznsalt
 */
public interface CartService {
    List<MallCartItem> list(Integer userId);
    MallCartItem add(Integer userId, MallCartItem cartItem);
    MallCartItem update(Integer userId, MallCartItem cartItem);
    boolean delete(Integer id);
    MallCartItem findCartItemById(Integer id);
    MallCartItem findByUserIdAndProductId(Integer userId, MallCartItem cartItem);
}
