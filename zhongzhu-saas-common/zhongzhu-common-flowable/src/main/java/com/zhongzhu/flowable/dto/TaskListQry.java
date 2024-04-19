package com.zhongzhu.flowable.dto;

import com.zhongzhu.core.dto.PageQuery;
import com.zhongzhu.core.utils.StringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shihao.liu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "TaskListQry", description = "任务流程列表查询参数")
public class TaskListQry extends PageQuery {

    @Schema(name = "name", description = "流程名称")
    private String name;

    @Schema(name = "userId", description = "用户ID")
    private Long userId;

    @Schema(name = "key", description = "定义key")
    private String key;

    public void setName(String name) {
        this.name = StringUtil.like(name);
    }

}
