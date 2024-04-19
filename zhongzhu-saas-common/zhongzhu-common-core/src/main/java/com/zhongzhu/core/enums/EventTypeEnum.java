package com.zhongzhu.core.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author shihao.liu
 */
@Schema(name = "EventTypeEnums", description = "事件类型枚举")
public enum EventTypeEnum {

    LOGIN_FAILED, LOGIN_SUCCEEDED, OPERATE_FAILED, OPERATE_SUCCEEDED, FILE_UPLOAD_FAILED, FILE_UPLOAD_SUCCEEDED

}
