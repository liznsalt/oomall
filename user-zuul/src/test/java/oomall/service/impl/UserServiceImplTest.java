package oomall.service.impl;

import common.oomall.api.CommonResult;
import common.oomall.util.JwtTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xmu.oomall.UserGatewayApplication;
import xmu.oomall.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserGatewayApplication.class)
@AutoConfigureMockMvc
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void login() {
        String token = userService.login("mai", "123");
        System.out.println(token);
        System.out.println(JwtTokenUtil.getClaimsFromToken(token));
    }

    @Test
    public void generateAuthCode() {
        CommonResult result = userService.generateAuthCode("13959257650");
        String code = (String) result.getData();
        CommonResult registerResult = userService.register("lizn", "1234567", "13959257650", code);
        System.out.println(registerResult);
    }

    @Test
    public void testUpdatePass() {
        CommonResult result = userService.generateAuthCode("13959257650");
        String code = (String) result.getData();
        CommonResult registerResult = userService.updatePassword("13959257650", "123", code);
        System.out.println(registerResult);
    }
}