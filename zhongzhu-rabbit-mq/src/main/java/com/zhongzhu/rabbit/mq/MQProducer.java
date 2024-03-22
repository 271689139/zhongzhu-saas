package com.zhongzhu.rabbit.mq;

import com.zhongzhu.rabbit.annotation.SecureInvoke;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 发送mq工具类
 * @author admin
 */
public class MQProducer {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMsg(String exchange, String routingKey, Message message, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    /**
     * 发送可靠消息，在事务提交后保证发送成功
     */
    @SecureInvoke
    public void sendSecureMsg(String exchange, String routingKey, Message message, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }
}
