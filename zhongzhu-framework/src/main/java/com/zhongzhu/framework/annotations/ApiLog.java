package com.zhongzhu.framework.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author admin
 * 日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ApiLog {

    /**
     * 标题
     */
    @AliasFor("name") String title() default "";

    @AliasFor("title") String name() default "";

    /**
     * 日志打印时排除的类型（例如 File）,对入参,出参都有效
     */
    Class<?>[] excludes() default {};

    LogLevel level() default LogLevel.DEBUG;

    LogType type() default LogType.BOTH;

    /**
     * 是否开启
     */
    boolean enable() default true;

    /**
     * 是否打印方法信息
     */
    boolean printMethodInfo() default true;

    /**
     * 是否打印请求信息
     */
    boolean printRequestInfo() default true;

    /**
     * 是否打印耗时
     */
    boolean timeConsumption() default true;

    /**
     * 日志级别
     */
    enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }

    /**
     * 记日志类型
     */
    enum LogType {
        /**
         * 入参
         */
        PARAM,
        /**
         * 返回值
         */
        RETURN,
        /**
         * 入参+返回值
         */
        BOTH
    }

}
