package com.zhongzhu.core.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author shihao.liu
 */
@Schema(name = "EventStatusEnums", description = "事件状态枚举")
public enum EventStatusEnum {

	@Schema(name = "CREATED", description = "创建")
	CREATED,

	@Schema(name = "PUBLISH_SUCCEED", description = "发布成功")
	PUBLISH_SUCCEED,

	@Schema(name = "PUBLISH_FAILED", description = "发布失败")
	PUBLISH_FAILED,

	@Schema(name = "CONSUME_SUCCEED", description = "消费成功")
	CONSUME_SUCCEED,

	@Schema(name = "CONSUME_FAILED", description = "消费失败")
	CONSUME_FAILED

}
