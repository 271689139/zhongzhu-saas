package com.zhongzhu.lock.enums;


/**
 * @author shihao.liu
 * 分布式锁类型枚举
 */
public enum LockTypeEnums {

    /**
     * 普通锁(默认)
     */
    LOCK,

    /**
     * 公平锁
     */
    FAIR_LOCK,

    /**
     * 读锁
     */
    READ_LOCK,

    /**
     * 写锁
     */
    WRITE_LOCK,

    /**
     * 强一致性锁(可以解决主从延迟)
     */
    FENCED_LOCK

}
