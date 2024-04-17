package com.zhongzhu.mq.event;


import com.zhongzhu.mq.MQProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author shihao.liu
 * 事件监听
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitSendEventListener {

    private final MQProducer producer;

    @EventListener(classes = RabbitSendEvent.class)
    public void listener(RabbitSendEvent rabbitSendEvent) {
        log.info("message ready to send, id: {}", rabbitSendEvent.getCorrelationData().getId());
        producer.sendMsg(rabbitSendEvent.getExchange(),
                rabbitSendEvent.getRoutingKey(),
                rabbitSendEvent.getMessage(),
                rabbitSendEvent.getCorrelationData());
    }
}