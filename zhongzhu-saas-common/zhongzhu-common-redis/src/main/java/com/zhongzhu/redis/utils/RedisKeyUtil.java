package com.zhongzhu.redis.utils;

/**
 * @author shihao.liu
 */
public final class RedisKeyUtil {

	/**
	 * 布隆过滤器Key.
	 */
	public static String getBloomFilterKey() {
		return "bloom:filter";
	}

}
