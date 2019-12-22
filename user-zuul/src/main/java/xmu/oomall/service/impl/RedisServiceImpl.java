package xmu.oomall.service.impl;

import common.oomall.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import xmu.oomall.domain.MallPrivilege;
import xmu.oomall.service.RedisService;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 *
 * @author liznsalt
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static Map<String, String> redisMap = new HashMap<>();

    @Override
    public void set(String key, String value) {
//        redisMap.put(key, value);
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
//        return redisMap.get(key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key,delta);
    }

    // 对象

    @Override
    public void setPrivilegeList(String key, List<MallPrivilege> privileges) {
        Map<String, List<MallPrivilege>> map = new HashMap<>(1);
        map.put(key, privileges);
        stringRedisTemplate.opsForValue().set(key, Objects.requireNonNull(JacksonUtil.toJson(map)));
    }

    @Override
    public List<MallPrivilege> getPrivilegeList(String key) {
        String privilegeJsonList = stringRedisTemplate.opsForValue().get(key);
        List<String> list = JacksonUtil.parseStringList(privilegeJsonList, key);
        if (list == null || list.size() == 0) {
            return new ArrayList<>(0);
        }

        // TODO
        return null;
    }

    //

    @Override
    public void deleteByPrefix(String prefix) {
        Set<String> keys = stringRedisTemplate.keys(prefix + "*");
        if (keys == null) {
            return;
        }
        stringRedisTemplate.delete(keys);
    }
}
