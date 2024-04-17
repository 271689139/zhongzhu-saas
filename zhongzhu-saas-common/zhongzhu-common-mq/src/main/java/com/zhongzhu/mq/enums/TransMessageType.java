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
     * 发送到交换机成功
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
