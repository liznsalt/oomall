package xmu.oomall.service.impl;

import common.oomall.component.ERole;
import common.oomall.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.mapper.PrivilegeMapper;
import xmu.oomall.service.PrivilegeService;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author liznsalt
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Override
    public List<MallPrivilege> getAll() {
        return privilegeMapper.getAll();
    }

    @Override
    public Map<Integer, List<String>> getAllPrivileges() {
        if (MallPrivilege.getAllPrivilege() == null) {
            List<MallPrivilege> privileges = getAll();
            Map<Integer, List<String>> res = new HashMap<>(200);
            for (MallPrivilege privilege : privileges) {
                if (!res.containsKey(privilege.getRoleId())) {
                    res.put(privilege.getRoleId(), new ArrayList<>());
                }
                res.get(privilege.getRoleId()).add(privilege.getName());
            }
            MallPrivilege.setAllPrivilege(res);
        }
        return MallPrivilege.getAllPrivilege();
    }

    @Override
    public List<String> getWhiteList() {
        // FIXME
        return Arrays.asList(
                "/userInfoService/admins/login",
                "/userInfoService/admins/logout",
                "/userInfoService/register",
                "/userInfoService/login",
                "/userInfoService/reCaptcha",
                "/userInfoService/captcha"
        );
    }

    @Override
    public boolean matchAuth(String method, String url, ERole role) {
        Map<Integer, List<String>> privilegesMap = getAllPrivileges();
        List<String> rolePrivilegeList = privilegesMap.getOrDefault(role.getRoleId(), null);
        if (rolePrivilegeList == null) {
            return false;
        }
        for (String p : rolePrivilegeList) {
            String pMethod = JacksonUtil.parseString(p, "method");
            String pUrl = JacksonUtil.parseString(p, "url");
            if (pUrl == null) {
                continue;
            }
            pUrl = pUrl.replaceAll("\\{id}", "\\\\d+");
            if (pMethod == null) {
                continue;
            }
            if (method.toLowerCase().equals(method)  || Pattern.matches(pUrl, url)) {
                return true;
            }
        }
        return false;
    }
}
