package com.zhongzhu.flowable.dto;

import com.zhongzhu.core.dto.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author shihao.liu
 */
@Data
@Schema(name = "DefinitionListQry", description = "流程列表查询参数")
public class DefinitionListQry extends PageQuery {

    @Serial
    private static final long serialVersionUID = -818653141079850719L;

    @Schema(name = "name", description = "流程名称")
    private String name;

}
