package com.zhongzhu.core.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author shihao.liu
 * 返回code
 */
@AllArgsConstructor
@Getter
public enum CommonResponseCode {

    /**
     * 成功返回
     */
    RC_SUCCESS(200, "success");
    /**
     * responseCode
     */
    private final Integer responseCode;

    /**
     * responseMessage
     */
    private final String responseMessage;


}