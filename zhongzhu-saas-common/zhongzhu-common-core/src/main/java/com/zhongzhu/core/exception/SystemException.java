package com.zhongzhu.core.exception;


/**
 * @author shihao.liu
 * 系统异常
 */

public final class SystemException extends GlobalException {

    public SystemException(String code) {
        super(code);
    }

    public SystemException(String code, String msg) {
        super(code, msg);
    }

}
