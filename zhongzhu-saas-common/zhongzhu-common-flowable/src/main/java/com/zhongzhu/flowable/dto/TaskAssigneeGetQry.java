package com.zhongzhu.flowable.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shihao.liu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TaskAssigneeGetQry", description = "查看任务流程执行人员命令请求")
public class TaskAssigneeGetQry implements Serializable {

    @Schema(name = "instanceId", description = "实例ID")
    private String instanceId;

}
