package com.zhongzhu.flowable.dto.definition;

import com.zhongzhu.core.event.AggregateRoot;
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
@Schema(name = "Activate", description = "激活")
public class Activate extends AggregateRoot<Long> {

    @Schema(name = "definitionId", description = "定义ID")
    private String definitionId;

    public void checkActivated(boolean activated) {
        if (activated) {
            throw new RuntimeException("激活失败，流程已激活");
        }
    }

}
