package com.zhongzhu.flowable.dto.task;

import com.zhongzhu.core.event.AggregateRoot;
import com.zhongzhu.core.i18n.ObjectUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author shihao.liu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Schema(name = "Start", description = "开始")
public class Start extends AggregateRoot<Long> {

    @Schema(name = "definitionKey", description = "定义Key")
    private String definitionKey;

    @Schema(name = "businessKey", description = "业务Key")
    private String businessKey;

    @Schema(name = "instanceName", description = "实例名称")
    private String instanceName;

    public void checkDefinition(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            throw new RuntimeException("流程未定义");
        }
    }

    public void checkInstance(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            throw new RuntimeException("流程不存在");
        }
    }

    public void checkSuspended(boolean suspended) {
        if (suspended) {
            throw new RuntimeException("挂起失败，流程已挂起");
        }
    }

}
