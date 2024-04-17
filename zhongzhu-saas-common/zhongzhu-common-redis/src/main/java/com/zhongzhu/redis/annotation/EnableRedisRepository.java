package com.zhongzhu.redis.annotation;

import com.zhongzhu.redis.config.RedisAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RedisAutoConfig.class)
public @interface EnableRedisRepository {

}
