package com.zhongzhu.frequency.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shihao.liu
 * 限流策略定义
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FixedWindowDTO extends FrequencyControlDTO {

    /**
     * 频控时间范围，默认单位秒
     *
     * @return 时间范围
     */
    private Integer time;
}