package com.zhongzhu.lock;


import com.zhongzhu.lock.enums.LockTypeEnums;

/**
 * 分布式锁.
 *
 * @author shihao.liu
 */
public interface Lock {

    /**
     * 尝试加锁.
     *
     * @param lockTypeEnums 类型
     * @param key           键
     * @param expire        过期时间
     * @param timeout       锁等待超时时间
     * @return Boolean
     * @throws InterruptedException 线程中断异常
     */
    Boolean tryLock(LockTypeEnums lockTypeEnums, String key, long expire, long timeout) throws InterruptedException;

    /**
     * 释放锁.
     *
     * @param lockTypeEnums 锁类型
     * @param key           键
     */
    void unlock(LockTypeEnums lockTypeEnums, String key);

}
