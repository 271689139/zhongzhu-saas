package com.zhongzhu.rabbit.aspect;


import com.alibaba.fastjson.JSON;
import com.zhongzhu.rabbit.annotation.SecureInvoke;
import com.zhongzhu.rabbit.mq.domain.SecureInvokeRecord;
import com.zhongzhu.rabbit.mq.dto.SecureInvokeDTO;
import com.zhongzhu.rabbit.mq.service.Impl.SecureInvokeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author admin
 * 安全执行切面
 */
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Component
public class SecureInvokeAspect {

    private SecureInvokeService secureInvokeService;

    @Autowired
    public void setSecureInvokeService(SecureInvokeService secureInvokeService) {
        this.secureInvokeService = secureInvokeService;
    }

    @Around("@annotation(secureInvoke)")
    public Object around(ProceedingJoinPoint joinPoint, SecureInvoke secureInvoke) throws Throwable {
        boolean async = secureInvoke.async();
        boolean inTransaction = TransactionSynchronizationManager.isActualTransactionActive();
        // 非事务状态，直接执行，不做任何保证。
        if (!inTransaction) {
            return joinPoint.proceed();
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        List<String> parameters = Stream.of(method.getParameterTypes()).map(Class::getName).collect(Collectors.toList());
        SecureInvokeDTO dto = SecureInvokeDTO.builder().args(joinPoint.getArgs()).className(method.getDeclaringClass().getName()).methodName(method.getName()).parameterTypes(JSON.toJSONString(parameters)).build();
        SecureInvokeRecord record = SecureInvokeRecord.builder().secureInvokeDTO(dto).maxRetryTimes(secureInvoke.maxRetryTimes()).build();
        secureInvokeService.invoke(record, async);
        return null;
    }
}
