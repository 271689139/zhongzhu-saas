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

package com.zhongzhu.core.i18n;


import java.util.regex.Pattern;

/**
 * @author laokou
 */
public final class SysConstants {

	private SysConstants() {
	}

	/**
	 * 版本
	 */
	public static final String VERSION = "3.2.5";

	/**
	 * 优雅停机URL
	 */
	public static final String GRACEFUL_SHUTDOWN_URL = "/graceful-shutdown";
	/**
	 * UNDEFINED
	 */
	public static final String UNDEFINED = "undefined";

	public static final String ALGORITHM_RSA = "RSA";

	public static final String SUN_RSA_SIGN_PROVIDER = "SunRsaSign";

	public static final String ENABLED = "enabled";

	public static final String SPRING = "spring";

	public static final String EMPTY_LOG_MSG = "暂无信息";

	public static final String ALGORITHM_AES = "aes";

	public static final String AES_INSTANCE = "AES/CBC/PKCS5Padding";

	public static final String COMMON_DATA_ID = "application-common.yaml";

	public static final String APPLICATION = "application";

	public static final String RATE_LIMITER_KEY = "___%s_KEY___";

	public static final String REDIS_PROTOCOL_PREFIX = "redis://";

	public static final String REDISS_PROTOCOL_PREFIX = "rediss://";

	public static final String CRYPTO_PREFIX = "ENC(";

	public static final String CRYPTO_SUFFIX = ")";

	public static final String PRIVATE_KEY = "private-key";

	public static final String ALL_PATTERNS = "/**";

	public static final String EXCEL_EXT = ".xlsx";

	public static final String TLS_PROTOCOL_VERSION = "TLSv1.3";

	public static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

	public static final String EMPTY_JSON = "{}";

	public static final String DEFAULT_USERNAME = "laokou";

	public static final String DEFAULT_PASSWORD = "laokou123";

	public static final String DOWN_STATUS = "DOWN";

	public static final String OFFLINE_STATUS = "OFFLINE";

	public static final String UP_STATUS = "UP";

	public static final String UNKNOWN_STATUS = "UNKNOWN";

	public static final String WEBSOCKET_PATH = "/ws";

	public static final String THREAD_POOL_TASK_EXECUTOR_NAME = "executor";

	public static final String SHA256 = "SHA-256";

	public static final String THREADS_VIRTUAL_ENABLED = "threads.virtual.enabled";

}