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
@Schema(name = "AssigneeCO", description = "查看执行人员命令请求")
public class AssigneeDTO implements Serializable {

    @Schema(name = "assignee", description = "执行人员")
    private String assignee;

}
