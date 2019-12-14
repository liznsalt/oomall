package oomall.service.impl;

import common.oomall.util.JacksonUtil;
import common.oomall.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.UserGatewayApplication;
import xmu.oomall.domain.MallAdmin;
import xmu.oomall.service.AdminService;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserGatewayApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void login1() {
        String token = adminService.login("lizn", "123");
        System.out.println(token);
        System.out.println(JwtTokenUtil.getClaimsFromToken(token));
    }

    @Test
    public void findById() {
        System.out.println(adminService.findById(1));
    }

    @Test
    public void findByName() {
        System.out.println(adminService.findByName("mai"));
    }

    @Test
    public void findAdmins() {
        System.out.println(adminService.findAdmins(1, 1));
    }

    @Test
    public void findDetailsByName() {
        System.out.println(adminService.findDetailsByName("mai"));
    }

    @Test
    public void findMemberByName() {
        System.out.println(adminService.findMemberByName("mai"));
    }

    @Test
    public void delete() {
        System.out.println(adminService.delete(1));
    }

    @Test
    public void update() {
        MallAdmin admin = new MallAdmin();
        admin.setRoleId(1);
        System.out.println(adminService.update(1, admin));
    }

    @Test
    public void add() {
        MallAdmin admin = new MallAdmin();
        admin.setUsername("lizn");
        admin.setPassword("123");
        admin.setRoleId(2);
        System.out.println(adminService.add(admin));
    }

    @Test
    public void login() {
        String token = adminService.login("mai", "12345");
        System.out.println(token);
        System.out.println(JwtTokenUtil.getUserNameFromToken(token));
        Claims jwt = JwtTokenUtil.getClaimsFromToken(token);
        String json = JacksonUtil.toJson(jwt);

        String exp = JacksonUtil.parseString(json, "exp");
        assert exp != null;
        System.out.println(new Date(Integer.parseInt(exp)));
        System.out.println(JacksonUtil.parseString(json, "role"));
        System.out.println(JacksonUtil.parseString(json, "username"));

        System.out.println(JwtTokenUtil.getMapFromToken(token));
    }

}