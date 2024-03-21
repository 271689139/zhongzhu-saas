package com.zhongzhu.framework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author admin
 */
@Component
public class RedisOpsListClient extends RedisOpsBaseClient {

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取列表中指定索引处的元素
     */
    public Object index(String key, long index) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().index(opsKey, index);
    }

    /**
     * 从列表的左边删除第一个元素，并返回该元素
     */
    public Object leftPop(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().leftPop(opsKey);
    }

    /**
     * 将某个元素放入到列表的头
     */
    public Long leftPush(String key, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().leftPush(opsKey, value);
    }

    /**
     * 将新元素放value入到列表中指定元素pivot的前面
     */
    public Long leftPush(String key, Object pivot, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().leftPush(opsKey, pivot, value);
    }

    /**
     * 批量插入元素到列表中
     */
    public Long leftPushAll(String key, List<Object> value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().leftPushAll(opsKey, value);
    }

    /**
     * 在列表中插入元素，当且仅当key存在，并且是一个列表的时候
     */
    public Long leftPushIfPresent(String key, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().leftPushIfPresent(opsKey, value);
    }

    /**
     * 返回列表中index在start（含）和end（含）之间的所有元素
     */
    public List<Object> range(String key, Long start, Long end) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().range(opsKey, start, end);
    }

    /**
     * 移除列表中count个与value值相等的数据，当count等于0的时候表示移除所有与value值相等的数据
     */
    public Long remove(String key, Long count, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().remove(opsKey, count, value);
    }

    /**
     * 从列表的尾部添加一个value元素
     */
    public Long rightPush(String key, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().rightPush(opsKey, value);
    }

    /**
     * 在列表的pivot元素之后添加一个value元素
     */
    public Long rightPush(String key, Object pivot, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().rightPush(opsKey, pivot, value);
    }

    /**
     * 从列表尾部，批量插入元素
     */
    public Long rightPushAll(String key, List<Object> value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().rightPushAll(opsKey, value);
    }

    /**
     * 当且仅当列表存在的时候，在列表的尾部添加元素value
     */
    public Long rightPushIfPresent(String key, Object value) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().rightPushIfPresent(opsKey, value);
    }

    /**
     * 从列表的尾部删除最后一个元素
     */
    public Object rightPop(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().rightPop(opsKey);
    }

    /**
     * 设置列表中第index个元素的值为value
     */
    public void set(String key, Long index, Object value) {
        String opsKey = wrapperKey(key);
        redisTemplate.opsForList().set(opsKey, index, value);
    }

    /**
     * 返回列表的长度
     */
    public Long size(String key) {
        String opsKey = wrapperKey(key);
        return redisTemplate.opsForList().size(opsKey);
    }

    /**
     * 保留start（含）到end（含）之间的所有元素
     */
    public void trim(String key, Long start, Long end) {
        String opsKey = wrapperKey(key);
        redisTemplate.opsForList().trim(opsKey, start, end);
    }
}
