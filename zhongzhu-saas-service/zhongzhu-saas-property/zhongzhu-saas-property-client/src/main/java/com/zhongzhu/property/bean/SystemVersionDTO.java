package com.zhongzhu.property.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author shihao.liu
 */
@Data
@Schema(name = "SystemVersionDTO", description = "系统版本")
public class SystemVersionDTO {

    @Schema(title = "id", description = "主键id", defaultValue = "1")
    private Long id;

    @Schema(description = "系统名称", title = "systemName")
    private String systemName;

    @Schema(description = "版本", title = "version")
    private String name;


}
