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

    /**
     * @deprecated
     * @return 全部权限
     */
    Map<Integer, List<MallPrivilege>> getAllPrivileges();

    /**
     * @deprecated
     * @return 白名单
     */
    List<String> getWhiteList();

    /**
     * 白名单
     * @return 白名单
     */
    List<MallPrivilege> getWhiteUrlList();

    /**
     *  检验url+method是否合法
     * @param method
     * @param url
     * @param roleId
     * @return
     */
    boolean matchAuth(String method, String url, Integer roleId);

    /**
     *  检查是否是白名单url
     * @param method
     * @param url
     * @return
     */
    boolean isWhiteUrl(String method, String url);
}
