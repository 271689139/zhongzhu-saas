package com.zhongzhu.utils.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author admin
 * 重试
 */
@Slf4j
public class RetryUtils {
    public static <T extends Throwable> void execute(RunnableWithException runnable, int maxRetryCount, Class<? extends Throwable> retryWhenError, String logTitle) throws T {
        int retryCount = 0;
        while (retryCount <= maxRetryCount) {
            if (retryCount != 0) {
                log.info(logTitle + "失败重试中,当前是第{}次重试", retryCount);
            }
            try {
                runnable.run();
                break;
            } catch (Exception e) {
                if (!e.getClass().isAssignableFrom(retryWhenError)) {
                    throw e;
                }
            }
            retryCount++;

        }

    }

    static <T extends Throwable, V> V retry(RunnableWithExceptionAndResult runnable, int maxRetryCount, Class<? extends Throwable> retryWhenError, String logTitle) throws T {
        int retryCount = 0;
        while (retryCount <= maxRetryCount) {

            if (retryCount != 0) {
                log.info(logTitle + "失败重试中,当前是第{}次重试", retryCount);
            }

            try {
                return runnable.run();
            } catch (Exception e) {
                if (!e.getClass().isAssignableFrom(retryWhenError)) {
                    throw e;
                }
            }
            retryCount++;

        }
        return null;
    }


    @FunctionalInterface
    interface RunnableWithException {
        /**
         * 执行
         *
         * @param <T> 异常类型
         * @throws T 异常
         */
        <T extends Throwable> void run() throws T;
    }

    @FunctionalInterface
    interface RunnableWithExceptionAndResult {

        /**
         * 执行并获取结果
         *
         * @param <T> 异常
         * @param <V> 结果类型
         * @return 结果
         * @throws T 异常
         */
        <T extends Exception, V> V run() throws T;
    }

}
