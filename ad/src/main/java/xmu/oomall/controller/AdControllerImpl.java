package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Ad;
import xmu.oomall.domain.MallAd;
import xmu.oomall.service.AdService;

import java.util.List;

/**
 * @author liznsalt
 */
@RestController
@RequestMapping("/adService")
public class AdControllerImpl {

    @Autowired
    private AdService adService;

    @GetMapping("/admin/ads")
    public Object adminFindAdList() {
        List<MallAd> ads = adService.adminList();
        return CommonResult.success(ads);
    }

    @PostMapping("/admin/ads")
    public Object adminCreateAad(MallAd ad) {
        MallAd newAd = adService.add(ad);
        return CommonResult.success(newAd);
    }

    @GetMapping("/ads/{id}")
    public Object adminFindAd(@PathVariable Integer id) {
        return CommonResult.success(adService.find(id));
    }

    @PutMapping("/ads/{id}")
    public Object adminUpdateAd(@PathVariable Integer id, @RequestBody MallAd ad) {
        ad.setId(id);
        ad = adService.update(ad);
        return CommonResult.success(ad);
    }

    @DeleteMapping("/ads/{id}")
    public Object adminDeleteAd(@PathVariable Integer id) {
        boolean ok = adService.deleteById(id);
        return CommonResult.success(ok);
    }

    @GetMapping("/ads")
    public Object userFindAd() {
        List<MallAd> ads = adService.userList();
        return CommonResult.success(ads);
    }
}
