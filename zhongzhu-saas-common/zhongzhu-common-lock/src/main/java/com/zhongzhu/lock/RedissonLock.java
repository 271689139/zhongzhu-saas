package com.zhongzhu.lock;

import com.zhongzhu.core.i18n.ObjectUtil;
import com.zhongzhu.lock.enums.LockTypeEnums;
import com.zhongzhu.redis.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

/**
 * 分布式锁实现类.
 *
 * @author shihao.liu
 */
@RequiredArgsConstructor
@Slf4j
public class RedissonLock extends AbstractLock<RLock> {

    private final RedisUtil redisUtil;

    /**
     * 获取锁.
     *
     * @param lockTypeEnums 锁类型
     * @param key           键
     * @return RLock
     */
    @Override
    public RLock getLock(LockTypeEnums lockTypeEnums, String key) {
        return switch (lockTypeEnums) {
            case LOCK -> redisUtil.getLock(key);
            case FAIR_LOCK -> redisUtil.getFairLock(key);
            case READ_LOCK -> redisUtil.getReadLock(key);
            case WRITE_LOCK -> redisUtil.getWriteLock(key);
            case FENCED_LOCK -> redisUtil.getFencedLock(key);
        };
    }

    /**
     * 尝试加锁.
     *
     * @param lock    锁
     * @param expire  过期时间
     * @param timeout 超时时间
     */
    @Override
    public Boolean tryLock(RLock lock, long expire, long timeout) throws InterruptedException {
        // 线程名称
        String threadName = Thread.currentThread().getName();
        if (redisUtil.tryLock(lock, expire, timeout)) {
            log.info("线程：{}，加锁成功", threadName);
            return true;
        } else {
            log.info("线程：{}，获取锁失败", threadName);
            return false;
        }
    }

    /**
     * 释放锁.
     *
     * @param lock 锁
     */
    @Override
    public void unlock(RLock lock) {
        if (ObjectUtil.isNotNull(lock)) {
            // 线程名称
            String threadName = Thread.currentThread().getName();
            if (redisUtil.isLocked(lock)) {
                log.info("锁名：{}，线程：{}，该锁被线程持有", lock, threadName);
                if (redisUtil.isHeldByCurrentThread(lock)) {
                    log.info("线程：{}，开始解锁", threadName);
                    redisUtil.unlock(lock);
                    log.info("解锁成功");
                }
            } else {
                log.info("无线程持有，无需解锁");
            }
        }
    }

}
