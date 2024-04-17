package com.zhongzhu.mq.factory;


import com.zhongzhu.mq.event.RabbitSendEvent;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * 发布事件
 */
@Component
public class EventFactory {

    public ApplicationEvent createEvent(String exchange, String routingKey, Message message, CorrelationData correlationData) {

        return new RabbitSendEvent(this, exchange, routingKey, message, correlationData);
    }
}