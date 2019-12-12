package xmu.oomall.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import standard.oomall.domain.Ad;
import xmu.oomall.AdApplication;
import xmu.oomall.domain.MallAd;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdApplication.class)
@AutoConfigureMockMvc
//@Transactional
public class AdControllerImplTest {

    @Autowired
    private AdControllerImpl adController;

    @Test
    public void adminFindAdList() {
        System.out.println(adController.adminFindAdList());
    }

    @Test
    public void adminCreateAad() {
        MallAd ad = new MallAd();
        ad.setContent("dadada");
        ad.setStartTime(LocalDateTime.now());
        ad.setEndTime(LocalDateTime.now());
        ad.setLink("13131131");
        System.out.println(adController.adminCreateAad(ad));
    }

    @Test
    public void adminFindAd() {
        System.out.println(adController.adminFindAd(1));
    }

    @Test
    public void adminUpdateAd() {
        MallAd ad = new MallAd();
        ad.setId(1);
        ad.setContent("dadada");
        ad.setStartTime(LocalDateTime.now());
        ad.setEndTime(LocalDateTime.now());
        ad.setLink("13131131");
        System.out.println(adController.adminUpdateAd(1, ad));
    }

    @Test
    public void adminDeleteAd() {
        System.out.println(adController.adminDeleteAd(1));
    }

    @Test
    public void userFindAd() {
        System.out.println(adController.userFindAd());
    }
}