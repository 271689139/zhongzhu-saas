package com.zhongzhu.redis.ops;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author shihao.liu
 */
@Slf4j
@RequiredArgsConstructor
public class RedisOpsKeyClient extends RedisOpsBaseClient {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis中删除key
     */
    public void delete(String key) {
        String opsKey = wrapperKey(key);
        redisTemplate.delete(opsKey);
    }


    /**
     * 判断指定的key是否存在
     */
    public boolean hasKey(String key) {
        String opsKey = wrapperKey(key);
        return Boolean.TRUE.equals(redisTemplate.hasKey(opsKey));
    }

    /**
     * 设置指定key的过期时间，时间单位自己指定
     */
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        String opsKey = wrapperKey(key);
        return Boolean.TRUE.equals(redisTemplate.expire(opsKey, timeout, timeUnit));
    }

    /**
     * 设置指定key的过期时间，单位为秒
     */
    public boolean expire(String key, long timeout) {
        String opsKey = wrapperKey(key);
        return Boolean.TRUE.equals(redisTemplate.expire(opsKey, timeout, TimeUnit.SECONDS));
    }

    /**
     * 获取某个key还有多久过期，单位是秒
     */
    public Long getExpire(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.getExpire(opsKey);
    }

    /**
     * 获取某个key还有多久过期，单位自己指定
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        String opsKey = wrapperKey(key);
        return redisTemplate.getExpire(opsKey, timeUnit);
    }
}
