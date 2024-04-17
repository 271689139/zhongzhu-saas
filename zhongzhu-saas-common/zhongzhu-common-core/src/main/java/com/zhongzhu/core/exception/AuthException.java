package com.zhongzhu.core.exception;


/**
 * @author shihao.liu
 * 认证异常
 */
public final class AuthException extends GlobalException {

    public AuthException(String code) {
        super(code);
    }

    public AuthException(String code, String msg) {
        super(code, msg);
    }

}
