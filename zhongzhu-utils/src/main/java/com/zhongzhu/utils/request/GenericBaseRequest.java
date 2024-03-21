package com.zhongzhu.utils.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @param <T> data
 * @author admin 公共请求
 */
@Data
public class GenericBaseRequest<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T param;

}
