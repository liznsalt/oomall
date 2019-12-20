package xmu.oomall.service;

import xmu.oomall.domain.MallCartItem;

import java.util.List;

/**
 * @author liznsalt
 */
public interface CartService {
    /**
     * 得到userId相应用户的购物车列表
     * @param userId 用户id
     * @return 购物车项的列表
     */
    List<MallCartItem> list(Integer userId);

    /**
     * 添加购物车项
     * @param userId 用户id
     * @param cartItem 购物车信息
     * @return 返回添加后的购物车信息
     */
    MallCartItem add(Integer userId, MallCartItem cartItem);

    /**
     * 更新购物车项
     * @param userId 用户id
     * @param cartItem 需要更新的购物车信息
     * @return 更新的购物车信息
     */
    MallCartItem update(Integer userId, MallCartItem cartItem);

    /**
     * 删除购物车项
     * @param id 购物车项的id
     * @return 是否删除成功
     */
    boolean delete(Integer id);

    /**
     * 通过id查询购物车项
     * @param id 购物车项id
     * @return 查询的购物车项信息
     */
    MallCartItem findCartItemById(Integer id);

    /**
     * 通过用户id和产品id查询购物车项
     * @param userId 用户id
     * @param cartItem 购物车项（包含产品id）
     * @return 相应的购物车项信息
     */
    MallCartItem findByUserIdAndProductId(Integer userId, MallCartItem cartItem);
}
