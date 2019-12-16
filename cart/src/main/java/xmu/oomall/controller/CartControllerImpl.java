package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import common.oomall.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Product;
import xmu.oomall.domain.MallCartItem;
import xmu.oomall.service.CartService;
import xmu.oomall.service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liznsalt
 */
@RestController
@RequestMapping("/cartService")
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
        Product product = JacksonUtil.parseObject(JacksonUtil.toJson(object),
                "data", Product.class);
        return product;
    }

    public Boolean isValid(MallCartItem cartItem) {
        if (cartItem == null
                || cartItem.getProductId() == null
                || cartItem.getProductId() < 0
                || cartItem.getNumber() == null
                || cartItem.getNumber() < 0) {
            return false;
        }
        return true;
    }

    // 内部接口
    @Deprecated
    @GetMapping("/cartItem/{userId}")
    public Object userCart(@PathVariable Integer userId) {
        if (userId == null || userId < 0) {
            return CommonResult.unLogin();
        }
        List<MallCartItem> cartItems = cartService.list(userId);
        for (MallCartItem mallCartItem : cartItems) {
            Product product = findProductById(mallCartItem.getProductId());
            if (product == null) {
                return CommonResult.serious();
            }
            mallCartItem.setProduct(product);
        }
        return CommonResult.success(cartItems);
    }


    // 外部接口

    @GetMapping("/cartItems")
    public Object cartIndex(HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return CommonResult.unLogin();
        }

        List<MallCartItem> cartItems = cartService.list(userId);
        for (MallCartItem mallCartItem : cartItems) {
            Product product = findProductById(mallCartItem.getProductId());
            if (product == null) {
                return CommonResult.serious();
            }
            mallCartItem.setProduct(product);
        }
        return CommonResult.success(cartItems);
    }

    @GetMapping("/cartItems/{id}")
    public Object cartItemDetail(@PathVariable Integer id, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return CommonResult.unLogin();
        }
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }
        MallCartItem cartItem = cartService.findCartItemById(id);
        if (cartItem == null) {
            return CommonResult.success(null);
        }
        if (userId.equals(cartItem.getUserId())) {
            return CommonResult.unauthorized();
        }
        Product product = findProductById(cartItem.getProductId());
        if (product == null) {
            return CommonResult.serious();
        }
        cartItem.setProduct(product);
        return CommonResult.success(cartItem);
    }

    @PostMapping("/cartItems")
    public Object add(MallCartItem cart, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (!isValid(cart)) {
            return CommonResult.badArgumentValue();
        }

        MallCartItem cartItem = cartService.add(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.updatedDataFailed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @Deprecated
    @PostMapping("/carts/{id}")
    public Object fastAdd(@PathVariable Integer id, MallCartItem cart, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }
        cart.setId(id);
        if (!isValid(cart)) {
            return CommonResult.badArgumentValue();
        }

        MallCartItem cartItem = cartService.fastAdd(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @PutMapping("/cartItems/{id}")
    public Object update(@PathVariable Integer id, MallCartItem cart, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }
        cart.setId(id);
        if (!isValid(cart)) {
            return CommonResult.badArgumentValue();
        }

        MallCartItem cartItem = cartService.update(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.updatedDataFailed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @DeleteMapping("/cartItems/{id}")
    public Object delete(@PathVariable Integer id, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null || userId < 0) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null || id < 0) {
            return CommonResult.badArgumentValue();
        }

        boolean ok = cartService.delete(id);
        if (ok) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @PostMapping("/fastAddCartItems")
    public Object fastAddCartItems(MallCartItem cartItem,
                                   HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }
        if (!isValid(cartItem)) {
            return CommonResult.badArgumentValue();
        }
        Product product = findProductById(cartItem.getProductId());
        if (product == null) {
            return CommonResult.serious();
        }
        cartItem.setProduct(product);
        return CommonResult.success(cartItem);
    }
}
