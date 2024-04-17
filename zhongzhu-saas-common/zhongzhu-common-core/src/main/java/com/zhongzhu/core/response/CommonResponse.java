package com.zhongzhu.core.response;


import lombok.Data;

/**
 * @author shihao.liu
 */
@Data
public class CommonResponse {

    /**
     * 返回状态码
     */
    private Integer responseCode;

    /**
     * 错误信息
     */
    private String responseMessage;

    /**
     * 链路id
     */
    private String traceId;
}