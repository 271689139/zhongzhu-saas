package com.zhongzhu.lock.annotation;

import com.zhongzhu.lock.enums.LockTypeEnums;

import java.lang.annotation.*;

/**
 * 分布式式锁注解.
 *
 * @author shihao.liu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock4j {

    /**
     * 键.
     */
    String key();

    /**
     * 过期时间 单位：毫秒 过期时间一定是要长于业务的执行时间.
     */
    long expire() default 5000;

    /**
     * 获取锁超时时间 单位：毫秒 结合业务,建议该时间不宜设置过长,特别在并发高的情况下.
     */
    long timeout() default 50;

    /**
     * 分布式锁类型.
     */
    LockTypeEnums type() default LockTypeEnums.LOCK;

    /**
     * 开启标识(解决公共模块用同一把锁).
     */
    boolean enable() default false;

}
