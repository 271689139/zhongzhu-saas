package com.zhongzhu.framework.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author admin
 * 方法失败重试注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Retry {

    /**
     * 重试日志标题
     */
    @AliasFor("value") String title() default "";

    @AliasFor("title") String value() default "";

    /**
     * 重试次数
     */
    int retryTimes() default 1;

    /**
     * 重试时间间隔,单位毫秒
     */
    long executeInterval() default 0;

    /**
     * 为指定异常重试
     */
    Class<? extends Throwable>[] retryFor() default {Exception.class};

    /**
     * 排除的异常不进行重试,注意如果一个异常在retryFor和noRetryFor中同时指定,
     * 则不再进行重试
     */
    Class<? extends Throwable>[] noRetryFor() default {};
}
