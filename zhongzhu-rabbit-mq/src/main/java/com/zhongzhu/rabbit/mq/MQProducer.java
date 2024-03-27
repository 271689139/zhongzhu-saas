package com.zhongzhu.rabbit.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送mq工具类
 *
 * @author admin
 */
@Component
public class MQProducer {

    private static RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        MQProducer.rabbitTemplate = rabbitTemplate;
    }

    public void sendMsg(String exchange, String routingKey, Message message, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

}
