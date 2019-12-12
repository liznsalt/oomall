package xmu.oomall.service;

/**
 * @author liznsalt
 */
public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, Object value);

    /**
     * 获取数据
     */
    Object get(String key);

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

    /**
     * 获取配置文件中设置的缓存时间
     * @return 缓存时间
     */
    Integer getRedisExpireTime();
}
