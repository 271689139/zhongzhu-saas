package com.zhongzhu.property.exception;

import com.zhongzhu.core.exception.ExceptionCodeMappingEnum;
import com.zhongzhu.core.exception.FrequencyControlException;
import com.zhongzhu.core.exception.SystemException;
import com.zhongzhu.core.response.GenericBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 *
 * @author shihao.liu
 */
@RestControllerAdvice(basePackages = "com.zhongzhu.property")
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * http请求方式不支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public GenericBaseResponse<String> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return GenericBaseResponse.failure(-1, String.format("不支持'%s'请求", e.getMethod()));
    }

    /**
     * 处理异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public GenericBaseResponse<String> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return GenericBaseResponse.failure(Integer.valueOf(ExceptionCodeMappingEnum.CODE_500.getCode()), ExceptionCodeMappingEnum.CODE_500.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FrequencyControlException.class)
    public GenericBaseResponse<String> handleException(FrequencyControlException e) {
        log.error(e.getMessage(), e);
        return GenericBaseResponse.failure(Integer.valueOf(ExceptionCodeMappingEnum.CAPACITY_REFILL_ERROR.getCode()), ExceptionCodeMappingEnum.CAPACITY_REFILL_ERROR.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SystemException.class)
    public GenericBaseResponse<String> handleException(SystemException e) {
        log.error(e.getMessage(), e);
        return GenericBaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
