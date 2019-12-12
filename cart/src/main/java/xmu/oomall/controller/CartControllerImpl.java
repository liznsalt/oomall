package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.MallCartItem;
import xmu.oomall.service.CartService;

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
    public Object cartIndex(Integer userId) {
        List<MallCartItem> cartItems = cartService.list(userId);
        return CommonResult.success(cartItems);
    }

    @PostMapping("/carts")
    public Object add(Integer userId, MallCartItem cart) {
        MallCartItem cartItem = cartService.add(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @PostMapping("/carts/{id}")
    public Object fastadd(Integer userId, MallCartItem cart) {
        MallCartItem cartItem = cartService.fastAdd(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @PutMapping("/carts/{id}")
    public Object update(Integer userId, MallCartItem cart) {
        if (cart.getId() == null) {
            return CommonResult.failed("改购物车项不存在");
        }
        MallCartItem cartItem = cartService.update(userId, cart);
        if (cartItem == null || cartItem.getId() == null) {
            return CommonResult.failed();
        } else {
            return CommonResult.success(cartItem);
        }
    }

    @DeleteMapping("/carts/{id}")
    public Object delete(@PathVariable String id, Integer userId, String body) {
        boolean ok = cartService.delete(Integer.valueOf(id));
        if (ok) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @GetMapping("/carts/count")
    public Object goodscount(Integer userId) {
        int count = cartService.goodsCount(userId);
        return CommonResult.success(count);
    }
}
