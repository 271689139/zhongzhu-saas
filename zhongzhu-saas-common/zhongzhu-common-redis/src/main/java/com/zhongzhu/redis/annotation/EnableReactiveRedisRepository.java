package com.zhongzhu.redis.annotation;

import com.zhongzhu.redis.config.ReactiveRedisAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ReactiveRedisAutoConfig.class)
public @interface EnableReactiveRedisRepository {

}
