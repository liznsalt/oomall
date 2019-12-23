package xmu.oomall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.mapper.PrivilegeMapper;
import xmu.oomall.service.PrivilegeService;
import xmu.oomall.service.RedisService;
import xmu.oomall.service.RoleService;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author liznsalt
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisService redisService;

    @Override
    public List<MallPrivilege> getAll() {
        return privilegeMapper.getAll();
    }

    @Override
    public List<MallPrivilege> getWhiteUrlList() {
        if (MallPrivilege.getWhitePrivilegeList() == null) {
            MallPrivilege.setWhitePrivilegeList(privilegeMapper.getWhiteUrlList());
        }
        return MallPrivilege.getWhitePrivilegeList();
        // TODO Redis缓存更好 没时间做了
//        List<MallPrivilege> privileges = redisService.getPrivilegeList(UriUtil.WHITE_URL_KEY_PREFIX);
//        if (privileges == null || privileges.size() == 0) {
//            System.out.println("buzairedis");
//            privileges = privilegeMapper.getWhiteUrlList();
//            // 白名单存在redis里面
////            redisService.setPrivilegeList(UriUtil.WHITE_URL_KEY_PREFIX, privileges);
//            System.out.println("..");
////            redisService.expire(UriUtil.WHITE_URL_KEY_PREFIX, UriUtil.WHITE_URL_TIME);
//        }
//        return privileges;
    }

    @Override
    public boolean matchAuth(String method, String url, Integer roleId) {
        logger.info("->matchAuth");
        List<MallPrivilege> rolePrivilegeList = roleService.getPrivilegesByRoleId(roleId);

        if (rolePrivilegeList == null) {
            return true;
        }
        for (MallPrivilege p : rolePrivilegeList) {
            if (isMatch(p, method, url)) {
                logger.info("matchAuth：method+url匹配");
                return false;
            }
        }
        logger.info("无权限");
        return true;
    }

    private boolean isMatch(MallPrivilege privilege, String method, String url) {

        if (privilege == null) {
            return false;
        }
        String pMethod = privilege.getMethod();
        String pUrl = privilege.getUrl();
        if (pUrl == null || pMethod == null || method == null || url == null) {
            return false;
        }
        // 修改成正则表达式
        try {
            pUrl = pUrl.replaceAll("\\{id}", "\\\\d+");
            return pMethod.toLowerCase().equals(method.toLowerCase())
                    && Pattern.matches(pUrl, url);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isWhiteUrl(String method, String url) {
        List<MallPrivilege> privileges = getWhiteUrlList();
        for (MallPrivilege privilege : privileges) {
            if (isMatch(privilege, method, url)) {
                logger.info("isWhiteUrl：method+url匹配");
                return true;
            }
        }
        return false;
    }
}
