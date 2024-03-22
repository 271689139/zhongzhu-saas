package com.zhongzhu.rabbit.event.listener;

import com.zhongzhu.rabbit.event.RabbitSendEvent;
import com.zhongzhu.rabbit.mq.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * 事件监听
 */
@Component
@Slf4j
public class RabbitSendEventListener {

    private MQProducer producer;

    @Autowired
    public void setProducer(MQProducer producer) {
        this.producer = producer;
    }

    @EventListener(classes = RabbitSendEvent.class)
    public void listen(RabbitSendEvent rabbitSendEvent) {
        log.info("message ready to send, id: {}", rabbitSendEvent.getCorrelationData().getId());
        producer.sendMsg(rabbitSendEvent.getExchange(),
                rabbitSendEvent.getRoutingKey(),
                rabbitSendEvent.getMessage(),
                rabbitSendEvent.getCorrelationData());
    }
}
