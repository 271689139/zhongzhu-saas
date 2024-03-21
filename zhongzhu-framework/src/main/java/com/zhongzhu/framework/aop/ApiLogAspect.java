package com.zhongzhu.framework.aop;

import com.zhongzhu.framework.annotations.ApiLog;
import com.zhongzhu.framework.utils.ApiLogUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author admin
 * 日志切面
 */
@Aspect
@Component
@Slf4j
public class ApiLogAspect {
    private ApiLogUtils logHelper;

    @Autowired
    public void setLogHelper(ApiLogUtils logHelper) {
        this.logHelper = logHelper;
    }

    @Pointcut("@annotation(com.zhongzhu.framework.annotations.ApiLog)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object log(@NonNull ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //获取此方法上的注解
        ApiLog apiLog = AnnotationUtils.findAnnotation(method, ApiLog.class);

        assert apiLog != null;
        if (!apiLog.enable()) {
            return point.proceed();
        }

        switch (apiLog.level()) {
            case DEBUG:
                if (!log.isDebugEnabled()) {
                    return point.proceed();
                }
                break;
            case INFO:
                if (!log.isInfoEnabled()) {
                    return point.proceed();
                }
                break;
            case WARN:
                if (!log.isWarnEnabled()) {
                    return point.proceed();
                }
                break;
            case ERROR:
                if (!log.isErrorEnabled()) {
                    return point.proceed();
                }
                break;
            default:
                break;
        }

        //记录时间
        long start = System.currentTimeMillis(), end;
        logHelper.logBeforeProceed(apiLog, method, point.getArgs());
        Object result = point.proceed();
        end = System.currentTimeMillis();
        logHelper.logAfterProceed(apiLog, result, end - start);
        return result;
    }
}
