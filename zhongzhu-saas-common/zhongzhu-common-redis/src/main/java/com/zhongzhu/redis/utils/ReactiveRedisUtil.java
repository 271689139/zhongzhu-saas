package com.zhongzhu.redis.utils;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

/**
 * @author shihao.liu
 */
@RequiredArgsConstructor
public class ReactiveRedisUtil {

	private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

	private final RedissonReactiveClient redissonReactiveClient;

	public Mono<Object> get(String key) {
		return redissonReactiveClient.getBucket(key).get();
	}

	public Mono<Object> hGet(String key, String field) {
		return redissonReactiveClient.getMap(key).get(field);
	}

	public Mono<Boolean> hasKey(String key) {
		return reactiveRedisTemplate.hasKey(key);
	}

	public Mono<Boolean> hasHashKey(String key, String field) {
		return redissonReactiveClient.getMap(key).containsKey(field);
	}

	public Mono<Void> set(String key, Object obj, long expire) {
		if (expire == -1) {
			return redissonReactiveClient.getBucket(key).set(obj);
		}
		else {
			return redissonReactiveClient.getBucket(key).set(obj, Duration.ofSeconds(expire));
		}
	}

	public Mono<Long> delete(String key) {
		return redissonReactiveClient.getKeys().delete(key);
	}

	public Flux<Object> values(String key) {
		return redissonReactiveClient.getMap(key).valueIterator();
	}

	public Mono<Boolean> fastPutIfAbsent(String key, String field, Object value) {
		return redissonReactiveClient.getMap(key).fastPutIfAbsent(field, value);
	}

	public Mono<Void> putAll(String key, Map<String, Object> map) {
		return redissonReactiveClient.getMap(key).putAll(map);
	}

	public Mono<Object> putIfAbsent(String key, String field, Object value) {
		return redissonReactiveClient.getMap(key).putIfAbsent(field, value);
	}

}
