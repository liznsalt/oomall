package xmu.oomall.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xmu.oomall.CartApplication;
import xmu.oomall.domain.MallCartItem;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CartApplication.class)
@AutoConfigureMockMvc
//@Transactional
public class CartControllerImplTest {

    @Autowired
    private CartControllerImpl cartController;

    @Test
    public void cartIndex() {
        System.out.println(cartController.cartIndex(1));
    }

    @Test
    public void add() {
        MallCartItem cartItem = new MallCartItem();
        cartItem.setNumber(1);
        cartItem.setProductId(1);
        cartItem.setBeCheck(true);
        System.out.println(cartController.add(1, cartItem));
    }

    @Test
    public void fastadd() {
        MallCartItem cartItem = new MallCartItem();
        cartItem.setId(1);
        cartItem.setNumber(2);
        cartItem.setProductId(1);
        cartItem.setBeCheck(true);
        System.out.println(cartController.add(1, cartItem));
    }

    @Test
    public void update() {
        MallCartItem cartItem = new MallCartItem();
        cartItem.setId(2);
        cartItem.setNumber(2);
        cartItem.setProductId(1);
        System.out.println(cartController.update(2, cartItem));
    }

    @Test
    public void delete() {
        System.out.println(cartController.delete("3", 1, "aaa"));
    }

    @Test
    public void goodscount() {
        System.out.println(cartController.goodscount(1));
    }
}