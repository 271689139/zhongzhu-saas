package com.zhongzhu.flowable.dto.task;

import com.zhongzhu.core.event.AggregateRoot;
import com.zhongzhu.core.i18n.ObjectUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author shihao.liu
 */
@Data
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Schema(name = "Audit", description = "审批")
public class Audit extends AggregateRoot<Long> {

    @Schema(name = "taskId", description = "任务ID")
    private String taskId;

    @Schema(name = "values", description = "流程变量")
    private Map<String, Object> values;

    public void checkTask(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            throw new RuntimeException("任务不存在");
        }
    }

    public void checkPending(boolean pending) {
        if (pending) {
            throw new RuntimeException("非审批任务，请处理任务");
        }
    }

}
