package xmu.oomall.controller;

import org.springframework.web.bind.annotation.*;
import xmu.oomall.domain.MallCartItem;

/**
 * @author liznsalt
 */
@RestController
@RequestMapping("")
public class CartControllerImpl {

    @GetMapping("/carts")
    public Object cartIndex(Integer userId) {
        return null;
    }

    @PostMapping("/carts")
    public Object add(Integer userId, MallCartItem cart) {
        return null;
    }

    @PostMapping("/carts/{id}")
    public Object fastadd(Integer userId, MallCartItem cart) {
        return null;
    }

    @PutMapping("/carts/{id}")
    public Object update(Integer userId, MallCartItem cart) {
        return null;
    }

    @DeleteMapping("/carts/{id}")
    public Object delete(@PathVariable String id, Integer userId, String body) {
        return null;
    }

    @GetMapping("/carts/count")
    public Object goodscount(Integer userId) {
        return null;
    }

    @PostMapping("/orders")
    public Object checkout(Integer userId, Integer cartId, Integer addressId,
                           Integer couponId, Integer grouponRulesId) {
        return null;
    }
}
