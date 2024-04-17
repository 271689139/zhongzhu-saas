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
public class RedisOpsValueClient<T> extends RedisOpsBaseClient {

    private final RedisTemplate<String, T> redisTemplate;


    /**
     * 获取一个key对应的值
     */
    public T get(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForValue().get(opsKey);
    }

    /**
     * 给一个key设置新值，同时返回旧值
     */
    public T getAndSet(String key, T value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForValue().getAndSet(opsKey, value);
    }

    /**
     * 给某个key的值增加delta，返回增加后的值
     */
    public Long increment(String key, long delta) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForValue().increment(opsKey, delta);
    }

    /**
     * 设置一个key和value
     */
    public void set(String key, T value) {
        String opsKey = wrapperKey(key);
        redisTemplate.opsForValue().set(opsKey, value);
    }


    /**
     * 设置key的value，同时指定过期时间与时间单位
     */
    public void set(String key, T value, long timeout, TimeUnit timeUnit) {
        String opsKey = wrapperKey(key);
        redisTemplate.opsForValue().set(opsKey, value, timeout, timeUnit);
    }

    /**
     * 当key不存在的时候设置成功，返回true；否则设置失败，返回false。
     */
    public Boolean setIfAbsent(String key, T value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForValue().setIfAbsent(opsKey, value);
    }
}