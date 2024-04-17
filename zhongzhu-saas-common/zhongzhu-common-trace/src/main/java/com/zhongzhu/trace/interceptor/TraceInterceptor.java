package com.zhongzhu.trace.interceptor;

import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.lang.Nullable;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * @author shihao.liu
 */
@NonNullApi
@Slf4j
public class TraceInterceptor implements HandlerInterceptor {

    private static final String TRACE_ID = "trace-id";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = UUID.randomUUID().toString().replace("-", "");
        //可以考虑让客户端传入链路ID，但需保证一定的复杂度唯一性；如果没使用默认UUID自动生成
        if (StringUtils.isNotBlank(request.getHeader(TRACE_ID))) {
            traceId = request.getHeader(TRACE_ID);
        }
        ThreadContext.put(TRACE_ID, traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        if (ex == null) {
            ThreadContext.clearMap();
        }
    }

}
