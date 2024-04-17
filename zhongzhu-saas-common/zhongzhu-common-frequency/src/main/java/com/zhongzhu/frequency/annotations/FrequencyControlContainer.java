package com.zhongzhu.frequency.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shihao.liu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FrequencyControlContainer {
    /**
     * 可重复 多个频控
     */
    FrequencyControl[] value();
}
