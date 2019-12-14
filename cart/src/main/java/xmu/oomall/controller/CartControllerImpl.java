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

    @GetMapping("/carts")
    public Object cartIndex(Integer userId, HttpServletRequest request) {
        if (userId == null) {
            return CommonResult.unLogin();
        }
        List<MallCartItem> cartItems = cartService.list(userId);
        return CommonResult.success(cartItems);
    }

    @PostMapping("/carts")
    public Object add(MallCartItem cart, HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getHeader("userId"));
        MallCartItem cartItem = cartService.add(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @PostMapping("/carts/{id}")
    public Object fastAdd(@PathVariable Integer id,
                          MallCartItem cart,
                          HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getHeader("userId"));
        cart.setId(id);
        MallCartItem cartItem = cartService.fastAdd(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @PutMapping("/carts/{id}")
    public Object update(MallCartItem cart,
                         HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getHeader("userId"));
        if (cart.getId() == null) {
            return CommonResult.failed("该购物车项不存在");
        }
        MallCartItem cartItem = cartService.update(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @DeleteMapping("/carts/{id}")
    public Object delete(@PathVariable String id,
                         HttpServletRequest request) {
        boolean ok = cartService.delete(Integer.valueOf(id));
        if (ok) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }
}
