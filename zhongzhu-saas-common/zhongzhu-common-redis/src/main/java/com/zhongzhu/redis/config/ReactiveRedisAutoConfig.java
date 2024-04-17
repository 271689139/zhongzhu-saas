package com.zhongzhu.redis.config;

import com.zhongzhu.redis.utils.ReactiveRedisUtil;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Flux;


/**
 * @author shihao.liu
 */
@AutoConfiguration
@ConditionalOnClass({RedissonAutoConfig.class, ReactiveRedisConnectionFactory.class, ReactiveRedisTemplate.class, Flux.class})
public class ReactiveRedisAutoConfig {

    @Bean
    @ConditionalOnMissingBean(name = "reactiveRedisTemplate")
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = GlobalJsonJacksonCodec.getJsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = GlobalJsonJacksonCodec.getStringRedisSerializer();
        RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext.<String, Object>newSerializationContext().key(stringRedisSerializer).value(jsonRedisSerializer).hashKey(stringRedisSerializer).hashValue(jsonRedisSerializer).build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, serializationContext);
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveStringRedisTemplate.class)
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return new ReactiveStringRedisTemplate(reactiveRedisConnectionFactory);
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonReactiveClient.class)
    public RedissonReactiveClient redissonReactiveClient(RedissonClient redissonClient) {
        return redissonClient.reactive();
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveRedisUtil.class)
    public ReactiveRedisUtil reactiveRedisUtil(RedissonReactiveClient redissonReactiveClient, ReactiveRedisTemplate<String, Object> reactiveRedisTemplate) {
        return new ReactiveRedisUtil(reactiveRedisTemplate, redissonReactiveClient);
    }

}
