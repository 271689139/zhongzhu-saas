package com.zhongzhu.mq.event;


import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.context.ApplicationEvent;

/**
 * @author shihao.liu
 * 消息事件
 */
@Getter
@Setter
public class RabbitSendEvent extends ApplicationEvent {

    private String exchange;

    private String routingKey;

    private Message message;

    private CorrelationData correlationData;

    public RabbitSendEvent(Object source, String exchange, String routingKey, Message message, CorrelationData correlationData) {
        super(source);
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.message = message;
        this.correlationData = correlationData;
    }
}
