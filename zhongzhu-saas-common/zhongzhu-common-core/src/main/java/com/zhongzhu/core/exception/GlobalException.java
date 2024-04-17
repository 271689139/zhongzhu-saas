package com.zhongzhu.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author shihao.liu
 * 全局异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
abstract class GlobalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4102669900127613541L;

    private final String code;

    private final String msg;

    protected GlobalException(String code) {
        this.code = code;
        this.msg = Enum.valueOf(ExceptionCodeMappingEnum.class, code).getMessage();
    }

    protected GlobalException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
