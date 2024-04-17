package com.zhongzhu.core.exception;


/**
 * @author shihao.liu
 * 业务异常
 */
public final class BizException extends GlobalException {

    public BizException(String code) {
        super(code);
    }

    public BizException(String code, String msg) {
        super(code, msg);
    }

}
