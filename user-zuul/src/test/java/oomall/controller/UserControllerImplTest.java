package oomall.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.UserGatewayApplication;
import xmu.oomall.controller.UserControllerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserGatewayApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerImplTest {

    @Autowired
    private UserControllerImpl userController;

    @Test
    public void login() {
//        System.out.println(userController.log("mai", "123456"));
    }
}