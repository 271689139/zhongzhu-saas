package com.zhongzhu.core.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author shihao.liu
 */
@Data
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Schema(name = "Identifier", description = "标识")
public abstract class Identifier<ID> implements Serializable {

    @Schema(name = "PRIMARY_KEY", description = "ID")
    public static final String PRIMARY_KEY = "id";

    @Schema(name = "CREATOR", description = "创建人")
    public static final String CREATOR = "creator";

    @Schema(name = "EDITOR", description = "编辑人")
    public static final String EDITOR = "editor";

    @Schema(name = "CREATE_DATE", description = "创建时间")
    public static final String CREATE_DATE = "createDate";

    @Schema(name = "UPDATE_DATE", description = "修改时间")
    public static final String UPDATE_DATE = "updateDate";

    @Schema(name = "DEL_FLAG", description = "删除标识")
    public static final String DEL_FLAG = "delFlag";

    @Schema(name = "DEPT_ID", description = "部门ID")
    public static final String DEPT_ID = "deptId";

    @Schema(name = "TENANT_ID", description = "租户ID")
    public static final String TENANT_ID = "tenantId";

    @Schema(name = PRIMARY_KEY, description = "ID")
    protected ID id;

}
