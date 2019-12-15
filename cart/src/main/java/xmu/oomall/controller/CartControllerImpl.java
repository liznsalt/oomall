package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.MallCartItem;
import xmu.oomall.service.CartService;

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

    private Integer getUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("userId");
        if (userIdStr == null) {
            return null;
        }
        return Integer.valueOf(userIdStr);
    }

    // 内部接口

    @GetMapping("/cartItem/{userId}")
    public Object userCart(@PathVariable Integer userId) {
        if (userId == null) {
            return CommonResult.badArgument("userId为空");
        }
        return CommonResult.success(cartService.list(userId));
    }


    // 外部接口

    @GetMapping("/cartItems")
    public Object cartIndex(HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        List<MallCartItem> cartItems = cartService.list(userId);
        return CommonResult.success(cartItems);
    }

    @PostMapping("/cartItems")
    public Object add(MallCartItem cart, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (cart == null) {
            return CommonResult.badArgument("cart 为空");
        }

        MallCartItem cartItem = cartService.add(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @PostMapping("/carts/{id}")
    public Object fastAdd(@PathVariable Integer id, MallCartItem cart, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null) {
            return CommonResult.badArgument("id为空");
        }
        if (cart == null) {
            return CommonResult.badArgument("cart为空");
        }

        cart.setId(id);
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
        if (userId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null) {
            return CommonResult.badArgument("id为空");
        }
        if (cart == null) {
            return CommonResult.badArgument("该购物车项不存在");
        }

        MallCartItem cartItem = cartService.update(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @DeleteMapping("/cartItems/{id}")
    public Object delete(@PathVariable Integer id, HttpServletRequest request) {
        Integer userId = getUserId(request);
        if (userId == null) {
            return CommonResult.unLogin();
        }

        // 参数校验
        if (id == null) {
            return CommonResult.badArgument("id为空");
        }

        boolean ok = cartService.delete(id);
        if (ok) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }
}
