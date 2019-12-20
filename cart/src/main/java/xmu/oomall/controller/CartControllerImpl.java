package xmu.oomall.controller;

import common.oomall.util.JacksonUtil;
import common.oomall.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Product;
import xmu.oomall.domain.MallCartItem;
import xmu.oomall.service.CartService;
import xmu.oomall.service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liznsalt
 * @author yanai
 */
@RestController
public class CartControllerImpl {

    @Autowired
    private CartService cartService;

    @Autowired
    private GoodsService goodsService;

    private Integer getUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("userId");
        if (userIdStr == null) {
            return null;
        }
        return Integer.valueOf(userIdStr);
    }

    private Product findProductById(Integer id) {
        Object object = goodsService.getProductById(id);
        try {
            Product product = JacksonUtil.parseObject(JacksonUtil.toJson(object),
                    "data", Product.class);
            if (product == null) {
                Integer errno = JacksonUtil.parseInteger(JacksonUtil.toJson(object),
                        "errno");
                Integer ok = 0;
                if (errno != null && errno.equals(ok)) {
                    return new Product();
                } else {
                    return null;
                }
            }
            if (product.getId() == -1) {
                return null;
            }
            return product;
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isValid(MallCartItem cartItem) {
        if (cartItem == null
                || cartItem.getProductId() == null
                || cartItem.getProductId() < 0
                || cartItem.getNumber() == null
                || cartItem.getNumber() <= 0) {
            return false;
        }
        Product product = findProductById(cartItem.getProductId());
        if (product == null || product.getId() == null) {
            return false;
        }
        if (cartItem.getBeCheck() == null) {
            cartItem.setBeCheck(false);
        }
        return true;
    }

    @GetMapping("/cartItem/{userId}")
    public Object userCart(@PathVariable Integer userId) {
        if (userId == null || userId < 0) {
            return ResponseUtil.fail(660, "用户未登录");
        }
        List<MallCartItem> cartItems = cartService.list(userId);
        List<MallCartItem> resultCartItems = new ArrayList<>();
        for (MallCartItem mallCartItem : cartItems) {
            Product product = findProductById(mallCartItem.getProductId());
            if (product == null) {
                return ResponseUtil.fail(744, "购物车明细不存在");
            }
            if (product.getId() == null) {
                cartService.delete(mallCartItem.getId());
            } else {
                mallCartItem.setProduct(product);
                resultCartItems.add(mallCartItem);
            }
        }
        return ResponseUtil.ok(resultCartItems);
    }

    @GetMapping("/cartItems")
    public Object cartIndex(HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return ResponseUtil.fail(660, "用户未登录");
        }

        List<MallCartItem> cartItems = cartService.list(userId);
        List<MallCartItem> resultCartItems = new ArrayList<>();
        // filter items whose product is wrong
        for (MallCartItem mallCartItem : cartItems) {
            Product product = findProductById(mallCartItem.getProductId());
            if (product == null) {
                return ResponseUtil.fail(744, "购物车明细不存在");
            }
            if (product.getId() == null) {
                cartService.delete(mallCartItem.getId());
            } else {
                mallCartItem.setProduct(product);
                resultCartItems.add(mallCartItem);
            }
        }
        return ResponseUtil.ok(resultCartItems);
    }

    @PostMapping("/cartItems")
    public Object add(@RequestBody MallCartItem cart, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return ResponseUtil.fail(660, "用户未登录");
        }

        // 参数校验
        if (!isValid(cart)) {
            return ResponseUtil.fail(741, "购物车明细新增失败");
        }

        MallCartItem cartItem = cartService.add(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return ResponseUtil.fail(741, "购物车明细新增失败");
        } else {
            return ResponseUtil.ok(cartItem);
        }
    }

    @PutMapping("/cartItems/{id}")
    public Object update(@PathVariable Integer id, @RequestBody MallCartItem cart,
                         HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return ResponseUtil.fail(660, "用户未登录");
        }
        cart.setUserId(userId);
        // 参数校验
        if (id == null || id < 0) {
            return ResponseUtil.fail(742, "购物车明细修改失败");
        }
        MallCartItem dbCartItem = cartService.findCartItemById(id);
        if (dbCartItem == null) {
            return ResponseUtil.fail(742, "购物车明细修改失败");
        }
        if (!userId.equals(dbCartItem.getUserId())) {
            return ResponseUtil.fail(742, "购物车明细修改失败");
        }
        cart.setId(id);
        if (!isValid(cart)) {
            return ResponseUtil.fail(742, "购物车明细修改失败");
        }
        MallCartItem cartItem = cartService.update(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return ResponseUtil.fail(742, "购物车明细修改失败");
        } else {
            return ResponseUtil.ok(cartItem);
        }
    }

    @DeleteMapping("/cartItems/{id}")
    public Object delete(@PathVariable Integer id, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return ResponseUtil.fail(660, "用户未登录");
        }

        // 参数校验
        if (id == null || id < 0) {
            return ResponseUtil.fail(743, "购物车明细删除失败");
        }

        MallCartItem cartItem = cartService.findCartItemById(id);
        if (cartItem == null) {
            return ResponseUtil.fail(743, "购物车明细删除失败");
        }
        if (!userId.equals(cartItem.getUserId())) {
            return ResponseUtil.fail(743, "购物车明细删除失败");
        }

        boolean ok = cartService.delete(id);
        if (ok) {
            return ResponseUtil.ok(null);
        } else {
            return ResponseUtil.fail(743, "购物车明细删除失败");
        }
    }

    @PostMapping("/fastAddCartItems")
    public Object fastAddCartItems(@RequestBody MallCartItem cartItem,
                                   HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return ResponseUtil.fail(660, "用户未登录");
        }
        if (!isValid(cartItem)) {
            return ResponseUtil.fail(741, "购物车明细新增失败");
        }
        Product product = findProductById(cartItem.getProductId());
        if (product == null) {
            return ResponseUtil.fail(741, "购物车明细新增失败");
        }
        cartItem.setProduct(product);
        return ResponseUtil.ok(cartItem);
    }
}
