package com.zhongzhu.redis.annotation;

import com.zhongzhu.redis.config.RedissonAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RedissonAutoConfig.class)
public @interface EnableRedissonRepository {

}
