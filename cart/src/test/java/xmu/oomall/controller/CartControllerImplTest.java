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

}