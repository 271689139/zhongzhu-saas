package com.zhongzhu.trace.aop;

import com.zhongzhu.core.response.GenericBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * @author shihao.liu
 */
@Component
@Aspect
@Slf4j
public class TraceLogAop {

    private static final String TRACE_ID = "trace-id";

    @Around("execution(* com.zhongzhu.*.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Object proceed = point.proceed();
        if (proceed instanceof GenericBaseResponse<?> result) {
            result.setTraceId(ThreadContext.get(TRACE_ID));
            return result;
        }
        return proceed;
    }
}
