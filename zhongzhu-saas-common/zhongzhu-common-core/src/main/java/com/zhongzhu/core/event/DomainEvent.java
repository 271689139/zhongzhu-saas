package com.zhongzhu.core.event;

import com.zhongzhu.core.enums.EventStatusEnum;
import com.zhongzhu.core.enums.EventTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.zhongzhu.core.event.Identifier.*;
import static lombok.AccessLevel.PROTECTED;

/**
 * @author shihao.liu
 */
@Data
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Schema(name = "DomainEvent", description = "领域事件")
public abstract class DomainEvent<ID> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1532877866226749304L;

    @Schema(name = "id", description = "ID")
    protected ID id;

    @Schema(name = "aggregateId", description = "聚合根ID")
    protected ID aggregateId;

    @Schema(name = "eventType", description = "事件类型")
    protected EventTypeEnum eventType;

    @Schema(name = "eventStatus", description = "事件状态")
    protected EventStatusEnum eventStatus;

    @Schema(name = "topic", description = "MQ主题")
    protected String topic;

    @Schema(name = "sourceName", description = "数据源名称")
    private String sourceName;

    @Schema(name = "appName", description = "应用名称")
    private String appName;

    @Schema(name = CREATOR, description = "创建人")
    protected ID creator;

    @Schema(name = EDITOR, description = "编辑人")
    protected ID editor;

    @Schema(name = DEPT_ID, description = "部门ID")
    protected ID deptId;


    @Schema(name = TENANT_ID, description = "租户ID")
    protected ID tenantId;

    @Schema(name = CREATE_DATE, description = "创建时间")
    protected LocalDateTime createDate;

    @Schema(name = UPDATE_DATE, description = "修改时间")
    protected LocalDateTime updateDate;

    public DomainEvent(ID id, EventStatusEnum eventStatus, String sourceName) {
        this.id = id;
        this.eventStatus = eventStatus;
        this.sourceName = sourceName;
    }

}
