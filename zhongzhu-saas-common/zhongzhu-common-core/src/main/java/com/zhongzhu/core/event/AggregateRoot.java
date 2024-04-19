package com.zhongzhu.core.event;

import com.zhongzhu.core.exception.AuthException;
import com.zhongzhu.core.exception.ExceptionCodeMappingEnum;
import com.zhongzhu.core.exception.SystemException;
import com.zhongzhu.core.i18n.ObjectUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author shihao.liu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Schema(name = "AggregateRoot", description = "聚合根")
public abstract class AggregateRoot<ID> extends Identifier<ID> {

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

    @Schema(name = "events", description = "事件集合")
    private List<DomainEvent<ID>> events;

    public void checkNullId() {
        if (ObjectUtil.isNull(this.id)) {
            throw new SystemException(ExceptionCodeMappingEnum.P_SYSTEM_ID_IS_NULL.getCode(), ExceptionCodeMappingEnum.P_SYSTEM_ID_IS_NULL.getMessage());
        }
    }

    protected void checkNullTenantId() {
        if (ObjectUtil.isNull(this.tenantId)) {
            throw new AuthException(ExceptionCodeMappingEnum.OAUTH2_TENANT_ID_REQUIRE.getCode(), ExceptionCodeMappingEnum.OAUTH2_TENANT_ID_REQUIRE.getMessage());
        }
    }

    protected void addEvent(DomainEvent<ID> event) {
        events().add(event);
    }

    public void clearEvents() {
        events = null;
    }

    private List<DomainEvent<ID>> events() {
        if (ObjectUtil.isNull(events)) {
            events = new ArrayList<>(16);
        }
        return events;
    }

}
