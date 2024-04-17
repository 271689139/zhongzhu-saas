package com.zhongzhu.core.i18n;

import java.util.Objects;

/**
 * 对象工具类.
 *
 * @author shihao.liu
 */
public class ObjectUtil {

	public static boolean isNotNull(Object o) {
		return Objects.nonNull(o);
	}

	public static boolean isNull(Object o) {
		return Objects.isNull(o);
	}

	public static boolean equals(Object o1, Object o2) {
		return Objects.equals(o1, o2);
	}

	/**
	 * 哈希码.
	 * @param args 参数
	 * @return 哈希码
	 */
	public static int hash(Object... args) {
		return Objects.hash(args);
	}

	/**
	 * 对象不允许为空.
	 * @param obj 对象
	 * @param <T> 泛型
	 * @return 对象
	 */
	public static <T> T requireNotNull(T obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		return obj;
	}

}
