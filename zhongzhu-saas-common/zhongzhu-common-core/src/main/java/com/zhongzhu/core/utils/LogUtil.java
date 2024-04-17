package com.zhongzhu.core.utils;

/**
 * 日志工具类.
 *
 * @author shihao.liu
 */
public class LogUtil {

    public static final String EMPTY_LOG_MSG = "暂无信息";

    public static String result(String msg) {
        return StringUtil.isEmpty(msg) ? EMPTY_LOG_MSG : msg;
    }


}
