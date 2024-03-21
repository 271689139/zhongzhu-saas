package com.zhongzhu.application.exception;

import com.zhongzhu.utils.enums.BusinessException;
import com.zhongzhu.utils.enums.CommonErrorEnum;
import com.zhongzhu.utils.response.GenericBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 *
 * @author admin
 */
@RestControllerAdvice(basePackages = "com.zhongzhu.application")
@Slf4j
public class ZhongzhuGlobalExceptionHandler {
    /**
     * validation参数校验异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericBaseResponse<String> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(x -> errorMsg.append(x.getField()).append(x.getDefaultMessage()).append(","));
        String message = errorMsg.toString();
        log.info("validation parameters error！The reason is:{}", message);
        return GenericBaseResponse.failure(CommonErrorEnum.PARAM_VALID.getErrorCode(), message.substring(0, message.length() - 1));
    }


    /**
     * 处理异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public GenericBaseResponse<String> exceptionHandler(Exception e) {
        log.error("null point exception！The reason is: ", e);
        return GenericBaseResponse.failure(CommonErrorEnum.SYSTEM_ERROR.getCode(), CommonErrorEnum.SYSTEM_ERROR.getMsg());
    }

    /**
     * 自定义校验异常（如参数校验等）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    public GenericBaseResponse<String> businessExceptionHandler(BusinessException e) {
        log.info("business exception！The reason is：{}", e.getMessage(), e);
        return GenericBaseResponse.failure(e.getErrorCode(), e.getMessage());
    }

    /**
     * http请求方式不支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public GenericBaseResponse<String> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return GenericBaseResponse.failure(-1, String.format("不支持'%s'请求", e.getMethod()));
    }
}
