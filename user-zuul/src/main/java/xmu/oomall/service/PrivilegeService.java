package xmu.oomall.service;

import xmu.oomall.domain.MallPrivilege;

import java.util.List;

/**
 * @author liznsalt
 */
public interface PrivilegeService {
    /**
     * 全部权限
     * @return 全部权限
     */
    List<MallPrivilege> getAll();

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
