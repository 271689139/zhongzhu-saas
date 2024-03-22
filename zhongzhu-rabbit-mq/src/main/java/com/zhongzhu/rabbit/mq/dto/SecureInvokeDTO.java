package com.zhongzhu.rabbit.mq.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author admin
 */
@Data
@Builder
public class SecureInvokeDTO {

    private String className;

    private String methodName;

    private String parameterTypes;

    private Object[] args;
}

