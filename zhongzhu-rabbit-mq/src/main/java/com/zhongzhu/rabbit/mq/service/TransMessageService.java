package com.zhongzhu.rabbit.mq.service;


import com.zhongzhu.rabbit.mq.domain.TransMessage;

import java.util.List;

/**
 * @author admin
 * mq 消息入库操作
 */
public interface TransMessageService {

    /**
     * messageBeforeSend
     * 消息准备好被发送，发送前暂存消息到数据库
     *
     * @param exchange   目标交换机
     * @param routingKey 目标路由key
     * @param body       消息体
     * @return TransMessage 存储到数据库的事务消息
     */
    TransMessage messageBeforeSend(String exchange, String routingKey, String body);

    /**
     * sendMessageSuccess
     * 设置消息发送成功的处理，消息被Mq确认
     *
     * @param messageId 消息messageId
     */
    void sendMessageSuccess(String messageId);

    /**
     * messageSendReturn
     * 消息无法被路由后返回的处理
     *
     * @param messageId  事务消息的messageId
     * @param exchange   目标交换机
     * @param routingKey 目标路由key
     * @param body       消息体
     * @return TransMessage
     */
    TransMessage handleMessageReturn(String messageId, String exchange, String routingKey, String body);

    /**
     * getReadyMessages
     * 查询准备好发送但还未发送的消息
     *
     * @return List<TransMessage>
     */
    List<TransMessage> getReadyMessages();

    /**
     * resendMessage
     * 消息发送失败后的重试，记录消息发送次数
     *
     * @param messageId 消息messageId
     */
    void resendMessage(String messageId);

    /**
     * messageDead
     * 消息重发多次，放弃
     *
     * @param messageId 消息messageId
     */
    void handleMessageDead(String messageId);

    /**
     * 保存并告警监听到的死信消息
     *
     * @param messageId  消息messageId
     * @param exchange   目标交换机
     * @param routingKey 目标路由key
     * @param queue      目标队列
     * @param body       消息体
     */
    void handleMessageDead(String messageId, String exchange, String routingKey, String queue, String body);

    /**
     * messageBeforeConsume
     * 消息消费前保存
     *
     * @param messageId  消息messageId
     * @param exchange   从哪个交换机来
     * @param routingKey 从哪个路由key来
     * @param queue      接收队列
     * @param body       消息体
     * @return TransMessage
     */
    TransMessage messageBeforeConsume(String messageId, String exchange, String routingKey, String queue, String body);

    /**
     * consumeMessageSuccess
     * 消息消费成功
     *
     * @param messageId 消息messageId
     */
    void consumeMessageSuccess(String messageId);

    /**
     * consumeMessageFailed
     * 消息消费失败
     *
     * @param messageId 消息messageId
     */
    void consumeMessageFailed(String messageId);
}
