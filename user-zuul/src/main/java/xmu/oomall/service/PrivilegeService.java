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
     * 得到所有权限
     * @deprecated
     * @return 全部权限
     */
    Map<Integer, List<MallPrivilege>> getAllPrivileges();

    /**
     * 得到白名单
     * 弃用
     * @see PrivilegeService#getWhiteUrlList()
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
     * @param method 请求方法
     * @param url 请求url
     * @param roleId 角色id
     * @return 是否匹配
     */
    boolean matchAuth(String method, String url, Integer roleId);

    /**
     *  检查是否是白名单url
     * @param method 请求方法
     * @param url 请求url
     * @return 是否在白名单
     */
    boolean isWhiteUrl(String method, String url);
}
