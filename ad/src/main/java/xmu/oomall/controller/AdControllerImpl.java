package xmu.oomall.controller;

import common.oomall.api.CommonResult;
import common.oomall.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import standard.oomall.domain.Log;
import xmu.oomall.domain.MallAd;
import xmu.oomall.service.AdService;
import xmu.oomall.service.LogService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liznsalt
 */
@RestController
public class AdControllerImpl {

    @Autowired
    private AdService adService;

    @Autowired
    private LogService logService;

    private final static Integer INSERT = 1;
    private final static Integer DELETE = 3;
    private final static Integer UPDATE = 2;
    private final static Integer SELECT = 0;
    private void writeLog(Integer adminId, String ip, Integer type,
                          String action, Integer statusCode, Integer actionId) {
        Log log = new Log();
        log.setAdminId(adminId);
        log.setIp(ip);
        log.setType(type);
        log.setActions(action);
        log.setStatusCode(statusCode);
        log.setActionId(actionId);
        logService.addLog(log);
    }

    private Integer getUserId(HttpServletRequest request) {
        String idString = request.getHeader("userId");
        if (idString == null) {
            return null;
        }
        return Integer.valueOf(idString);
    }

    @GetMapping("/admin/ads")
    public Object adminFindAdList(@RequestParam String adTitle,
                                  @RequestParam String adContent,
                                  @RequestParam Integer page,
                                  @RequestParam Integer limit,
                                  HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip = request.getHeader("ip");

        if (page == null || limit == null || page <= 0 || limit <= 0) {
            return ResponseUtil.fail(680,"获取广告失败");
        }

        List<MallAd> ads = adService.adminList(adTitle, adContent, page, limit);
        writeLog(adminId, ip, SELECT, "查看广告列表", 1, null);
        return ResponseUtil.ok(ads);
    }

    @PostMapping("/ads")
    public Object adminCreateAad(@RequestBody MallAd ad, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip = request.getHeader("ip");

        if (ad == null||ad.getStartTime().isAfter(ad.getEndTime())) {
            return ResponseUtil.fail(681,"创建广告失败");
        }

        MallAd newAd = adService.add(ad);
        writeLog(adminId, ip, INSERT, "创建广告", 1, newAd.getId());
        return ResponseUtil.ok(newAd);
    }

    @GetMapping("/ads/{id}")
    public Object adminFindAd(@PathVariable Integer id, HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip = request.getHeader("ip");

        if (id == null || id<=0) {
            return ResponseUtil.fail(680,"获取广告失败");
        }

        writeLog(adminId, ip, SELECT, "查看广告", 1, id);
        MallAd ad = adService.find(id);
        return ResponseUtil.ok(ad);
    }

    @PutMapping("/ads/{id}")
    public Object adminUpdateAd(@PathVariable Integer id, @RequestBody MallAd ad,
                                HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip = request.getHeader("ip");

        if (id == null || id<=0 || ad.getStartTime().isAfter(ad.getEndTime())) {
            return ResponseUtil.fail(682,"修改广告失败");
        }

        ad.setId(id);
        ad = adService.update(ad);
        writeLog(adminId, ip,UPDATE, "修改广告", 1, id);
        return ResponseUtil.ok(ad);
    }

    @DeleteMapping("/ads/{id}")
    public Object adminDeleteAd(@PathVariable Integer id,
                                HttpServletRequest request) {
        Integer adminId = getUserId(request);
        if (adminId == null) {
            return ResponseUtil.fail(668,"管理员未登录");
        }
        String ip = request.getHeader("ip");

        if (id == null || id <= 0) {
            return ResponseUtil.fail(683,"删除广告失败");
        }

        boolean ok = adService.deleteById(id);
        if (ok) {
            writeLog(adminId, ip, DELETE, "删除广告", 1, id);
            return ResponseUtil.ok();
        } else {
            writeLog(adminId, ip, DELETE, "删除广告", 0, id);
            return ResponseUtil.fail(683,"删除广告失败");
        }
    }

    @GetMapping("/ads")
    public Object userFindAd() {
        List<MallAd> ads = adService.userList();
        return ResponseUtil.ok(ads);
    }
}
