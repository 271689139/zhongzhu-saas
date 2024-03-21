package com.zhongzhu.utils.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @param <T>
 * @author admin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GenericBaseResponse<T> extends CommonResponse {

    private T data;


    public static <T> GenericBaseResponse<T> ok(T data) {
        GenericBaseResponse<T> response = new GenericBaseResponse<>();
        response.setData(data);
        response.setResponseCode(CommonResponseCode.RC_SUCCESS.getResponseCode());
        response.setResponseMessage(CommonResponseCode.RC_SUCCESS.getResponseMessage());
        return response;
    }

    public static <T> GenericBaseResponse<T> failure(Integer code, String message) {
        GenericBaseResponse<T> response = new GenericBaseResponse<>();
        response.setData(null);
        response.setResponseCode(code);
        response.setResponseMessage(message);
        return response;
    }

    public static <T> GenericBaseResponse<T> failure(CommonResponseCode commonResponseCode) {
        GenericBaseResponse<T> response = new GenericBaseResponse<>();
        response.setData(null);
        response.setResponseCode(commonResponseCode.getResponseCode());
        response.setResponseMessage(commonResponseCode.getResponseMessage());
        return response;
    }

}
