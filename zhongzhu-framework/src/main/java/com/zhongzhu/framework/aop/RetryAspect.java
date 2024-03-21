package com.zhongzhu.framework.aop;

import cn.hutool.core.thread.ThreadUtil;
import com.zhongzhu.framework.annotations.Retry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author admin
 */
@Component
@Slf4j
@Aspect
@ConditionalOnProperty(prefix = "retry", name = "enabled", havingValue = "true")
public class RetryAspect {
    @Pointcut("@annotation(com.zhongzhu.framework.annotations.Retry)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doRetry(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            Retry retry = AnnotationUtils.findAnnotation(method, Retry.class);
            if (retry != null) {
                int retryTimes = retry.retryTimes();
                Class<? extends Throwable>[] retryForExceptions = retry.retryFor();
                Class<? extends Throwable>[] excludeExceptions = retry.noRetryFor();
                String title = retry.title();
                long executeInterval = retry.executeInterval();
                if (retryTimes < 1 || isInstance(excludeExceptions, throwable) || !isInstance(retryForExceptions, throwable)) {
                    throw throwable;
                }
                int currentRetryTimes = 1;
                while (true) {
                    log.info("{} 接口失败重试中,第 {} 次重试...", title, currentRetryTimes++);
                    try {
                        if (executeInterval > 0) {
                            ThreadUtil.safeSleep(executeInterval);
                        }
                        return joinPoint.proceed();
                    } catch (Throwable e) {
                        if (currentRetryTimes > retryTimes) {
                            throw e;
                        }
                    }
                }
            }

            throw throwable;
        }
    }

    private boolean isInstance(Class<? extends Throwable>[] array, Throwable e) {
        if (ArrayUtils.isEmpty(array) || e == null) {
            return false;
        }

        for (Class<? extends Throwable> th : array) {
            if (th.isInstance(e)) {
                return true;
            }
        }
        return false;
    }
}
