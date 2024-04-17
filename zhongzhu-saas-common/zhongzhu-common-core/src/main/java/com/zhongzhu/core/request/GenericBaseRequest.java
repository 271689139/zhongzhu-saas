package com.zhongzhu.core.request;


import lombok.Data;

import java.io.Serializable;

/**
 * @param <T> data
 * @author shihao.liu 公共请求
 */
@Data
public class GenericBaseRequest<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T param;

}

