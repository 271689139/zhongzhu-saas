package com.zhongzhu.redis.ops;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * @author admin
 */
@Slf4j
@RequiredArgsConstructor
public class RedisOpsSortSetClient extends RedisOpsBaseClient {

    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * SortSet列表中添加元素
     */
    public Boolean add(String key, Object value, Double score) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().add(opsKey, value, score);
    }

    /**
     * 获取sortset中元素的个数
     */
    public Long size(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().size(opsKey);
    }

    /**
     * 获取sortset中分数介于start(含)和end(含)之间的元素个数
     */
    public Long count(String key, Double start, Double end) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().count(opsKey, start, end);
    }

    /**
     * 获取sortset中index从start(含)到end(含)的元素
     */
    public Set<Object> range(String key, Long start, Long end) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().range(opsKey, start, end);
    }

    /**
     * 获取sortset中所有的分数在min和max之间的所有元素
     */
    public Set<Object> rangeByScore(String key, Double min, Double max) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().rangeByScore(opsKey, min, max);
    }

    /**
     * 获取sortset中分数位于min和max之间的所有元素，同时每个元素还带有各自的分数
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String key, Double min, Double max) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().rangeByScoreWithScores(opsKey, min, max);
    }

    /**
     * sortSet  倒排数据，从开始到结束范围之间
     */
    public Set<Object> reverseRange(String key, long start, long end) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().reverseRange(opsKey, start, end);
    }

    /**
     * zset移除某个范围内的数据
     */
    public Long removeRange(String key, long start, long end) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().removeRange(opsKey, start, end);
    }


    /**
     * 移除sortset中的指定元素
     */
    public Long remove(String key, Object... members) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().remove(opsKey, members);
    }

    /**
     * 获取sortset中元素的分数
     *
     * @param key
     * @param value
     * @return
     */
    public Double score(String key, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForZSet().score(opsKey, value);
    }
}