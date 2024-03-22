package com.zhongzhu.rabbit.annotation;

import org.springframework.lang.Nullable;

import java.util.concurrent.Executor;

/**
 * @author admin
 */
public interface SecureInvokeConfigurer {

    /**
     * 返回一个线程池
     */
    @Nullable
    default Executor getSecureInvokeExecutor() {
        return null;
    }

}
