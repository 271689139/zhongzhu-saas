package com.zhongzhu.redis.ops;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author admin
 */
@Slf4j
@RequiredArgsConstructor
public class RedisOpsHashClient extends RedisOpsBaseClient {

    private final RedisTemplate<String, Object> redisTemplate;



    /**
     * 删除某个hashmap中指定的field
     */
    public long delete(String key, String... hashKeys) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().delete(opsKey, hashKeys);
    }

    /**
     * 获取此key所对应的整个HashMap
     */
    public Map<Object, Object> entries(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().entries(opsKey);
    }

    /**
     * 获取hashMap中指定hashKey的值
     */
    public Object get(String key, String hashKey) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().get(opsKey, hashKey);
    }

    /**
     * 指定的hashKey是否存在
     */
    public boolean hasKey(String key, String hashKey) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().hasKey(opsKey, hashKey);
    }

    /**
     * HashMap中某个Hashkey的值增加delta
     */
    public long increment(String key, String hashKey, long delta) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().increment(opsKey, hashKey, delta);
    }

    /**
     * hashMap中所有的key
     */
    public Set<Object> keys(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().keys(opsKey);
    }

    /**
     * 获取指定的hashKeys所对应的值
     */
    public List<Object> multiGet(String key, Collection<Object> hashKeys) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().multiGet(opsKey, hashKeys);
    }

    /**
     * 设置hashmap中的key value
     */
    public void put(String key, String hashKey, Object value) {
        String opsKey = wrapperKey(key);
        redisTemplate.opsForHash().put(opsKey, hashKey, value);
    }

    /**
     * 批量设置hashMap
     */
    public void putAll(String key, Map<String, Object> paramMap) {
        String opsKey = wrapperKey(key);
        redisTemplate.opsForHash().putAll(opsKey, paramMap);
    }

    /**
     * 当hashkey不存在的时候设置value成功，并返回true;否则返回false。
     */
    public boolean putIfAbsent(String key, String hashKey, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().putIfAbsent(opsKey, hashKey, value);
    }

    /**
     * 获取此hash表的长度
     */
    public long size(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().size(opsKey);
    }

    /**
     * 获取hashMap的所有value
     */
    public List<Object> values(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForHash().values(opsKey);
    }
}