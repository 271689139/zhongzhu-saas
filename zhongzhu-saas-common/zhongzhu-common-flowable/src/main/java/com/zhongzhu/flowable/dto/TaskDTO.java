package com.zhongzhu.flowable.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author shihao.liu
 */
@Data
@Schema(name = "TaskDO", description = "任务")
public class TaskDTO {

    @Schema(name = "taskId", description = "任务ID")
    private String taskId;

    @Schema(name = "taskName", description = "任务名称")
    private String taskName;

    @Schema(name = "definitionId", description = "定义ID")
    private String definitionId;

    @Schema(name = "instanceId", description = "实例ID")
    private String instanceId;

    @Schema(name = "createDate", description = "创建时间")
    private LocalDateTime createDate;

    @Schema(name = "definitionKey", description = "定义Key")
    private String definitionKey;

    @Schema(name = "name", description = "流程名称")
    private String name;

    @Schema(name = "instanceName", description = "实例名称")
    private String instanceName;

    @Schema(name = "businessKey", description = "业务Key")
    private String businessKey;

}
