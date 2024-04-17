package com.zhongzhu.mq;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 发送mq工具类
 *
 * @author shihao.liu
 */
@Component
@RequiredArgsConstructor
public class MQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMsg(String exchange, String routingKey, Message message, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

}
