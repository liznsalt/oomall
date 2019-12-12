package xmu.oomall.service;

import common.oomall.component.ERole;
import xmu.oomall.domain.MallPrivilege;

import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
public interface PrivilegeService {
    List<MallPrivilege> getAll();
    Map<Integer, List<String>> getAllPrivileges();
    List<String> getWhiteList();
    boolean matchAuth(String method, String url, ERole role);
}
