package com.zhongzhu.utils.response;

import lombok.Data;

/**
 * @author admin
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
}
