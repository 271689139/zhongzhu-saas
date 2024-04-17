package com.zhongzhu.core.exception;


/**
 * @author shihao.liu
 * 参数异常
 */
public final class ParamException extends GlobalException {

    public ParamException(String code) {
        super(code);
    }

    public ParamException(String code, String msg) {
        super(code, msg);
    }

}
