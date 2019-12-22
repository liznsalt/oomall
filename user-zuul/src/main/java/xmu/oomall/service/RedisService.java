package xmu.oomall.service;

import xmu.oomall.domain.MallPrivilege;

import java.util.List;

/**
 * @author liznsalt
 */
public interface RedisService {
    /**
     * 存储数据
     * @param key k
     * @param value v
     */
    void set(String key, String value);

    /**
     * 获取数据
     * @param key key
     * @return redis中数据
     */
    String get(String key);

    /**
     * 设置超期时间
     * @param key key
     * @param expire 时间
     * @return true
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     * @param key key
     */
    void remove(String key);

    /**
     * 自增操作
     * @param key key
     * @param delta 自增步长
     * @return l
     */
    Long increment(String key, long delta);

    // 对象redis

    /**
     * 存储权限列表
     * @param key k
     * @param privileges 权限列表
     */
    void setPrivilegeList(String key, List<MallPrivilege> privileges);

    /**
     * 获取权限列表
     * @param key k
     * @return 权限列表
     */
    List<MallPrivilege> getPrivilegeList(String key);

    // 额外

    /**
     * 删除前缀
     * @param prefix 前缀key
     */
    void deleteByPrefix(String prefix);
}
