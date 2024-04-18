package com.zhongzhu.mq.enums;

/**
 * @author admin
 */
public enum TransMessageType {
    /**
     * 发送
     */
    SEND,
    /**
     * 开始消费
     */
    RECEIVE,
    /**
     * 交换机路由失败
     */
    LOST,
    /**
     * 死信队列
     */
    DEAD,
    /**
     * 消费失败
     */
    ERROR
}
