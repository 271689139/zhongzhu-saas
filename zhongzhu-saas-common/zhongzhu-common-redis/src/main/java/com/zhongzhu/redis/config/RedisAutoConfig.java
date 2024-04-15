/*
 * Copyright (c) 2022-2024 KCloud-Platform-Alibaba Author or Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.zhongzhu.redis.config;

import com.zhongzhu.redis.ops.*;
import com.zhongzhu.redis.utils.RedisUtil;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Redis配置.
 *
 * @author laokou
 */
@AutoConfiguration
@ConditionalOnClass(LettuceConnectionFactory.class)
public class RedisAutoConfig {

    /**
     * 自定义RedisTemplate.
     *
     * @param lettuceConnectionFactory 工厂
     * @return RedisTemplate
     */
    @Bean("redisTemplate")
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = GlobalJsonJacksonCodec.getJsonRedisSerializer();
        // string序列化
        StringRedisSerializer stringRedisSerializer = GlobalJsonJacksonCodec.getStringRedisSerializer();
        // key
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // value
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash-key
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // hash-value
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 初始化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisUtil redisUtil(RedissonClient redissonClient, RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        return new RedisUtil(redisTemplate, redissonClient, stringRedisTemplate);
    }

    @Bean
    public RedisOpsHashClient redisOpsHashClient(RedisTemplate<String, Object> redisTemplate) {
        return new RedisOpsHashClient(redisTemplate);
    }

    @Bean
    public RedisOpsKeyClient redisOpsKeyClient(RedisTemplate<String, Object> redisTemplate) {
        return new RedisOpsKeyClient(redisTemplate);
    }

    @Bean
    public RedisOpsListClient redisOpsListClient(RedisTemplate<String, Object> redisTemplate) {
        return new RedisOpsListClient(redisTemplate);
    }

    @Bean
    public RedisOpsSortSetClient redisOpsSortSetClient(RedisTemplate<String, Object> redisTemplate) {
        return new RedisOpsSortSetClient(redisTemplate);
    }

    @Bean
    public RedisOpsValueClient<String> redisOpsValueClient(RedisTemplate<String, String> redisTemplate) {
        return new RedisOpsValueClient<>(redisTemplate);
    }


}
