package com.zhongzhu.flowable.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author shihao.liu
 */
@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Schema(name = "DefinitionDTO", description = "流程定义")
public class DefinitionDTO implements Serializable {

    @Schema(name = "definitionId", description = "定义ID")
    private String definitionId;

    @Schema(name = "processName", description = "流程名称")
    private String processName;

    @Schema(name = "processKey", description = "流程Key")
    private String processKey;

    @Schema(name = "deploymentId", description = "部署ID")
    private String deploymentId;

    @Schema(name = "isSuspended", description = "流程状态 0激活 1挂起")
    private Boolean isSuspended;

}
