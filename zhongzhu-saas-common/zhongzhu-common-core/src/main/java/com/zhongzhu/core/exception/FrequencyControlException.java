package com.zhongzhu.core.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义限流异常
 *
 * @author admin
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class FrequencyControlException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     *  错误码
     */
    protected String errorCode;

    /**
     *  错误信息
     */
    protected String errorMsg;

    public FrequencyControlException() {
        super();
    }

    public FrequencyControlException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public FrequencyControlException(ExceptionCodeMappingEnum exceptionCodeMappingEnum) {
        super(exceptionCodeMappingEnum.getMessage());
        this.errorCode = exceptionCodeMappingEnum.getCode();
        this.errorMsg = exceptionCodeMappingEnum.getMessage();
    }
}