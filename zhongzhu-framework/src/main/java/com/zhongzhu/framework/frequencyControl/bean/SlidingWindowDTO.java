package com.zhongzhu.framework.frequencyControl.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author admin
 * 限流策略定义
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SlidingWindowDTO extends FrequencyControlDTO {

    /**
     * 窗口大小，默认 10 s
     */
    private Integer windowSize;

    /**
     * 窗口最小周期 1s (窗口大小是 10s， 1s一个小格子，-共10个格子)
     */
    private Integer period;
}
