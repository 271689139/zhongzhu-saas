package com.zhongzhu.rabbit.transmitter;

import com.alibaba.fastjson.JSON;
import com.zhongzhu.rabbit.factory.EventFactory;
import com.zhongzhu.rabbit.mq.domain.TransMessage;
import com.zhongzhu.rabbit.mq.service.TransMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * 业务代码与RabbitTemplate的中间层
 */
@Component
@Slf4j
public class TransMessageTransmitter {

    @Value("${zhongzhu.content-type:application/json}")
    private String contentType;

    private TransMessageService transMessageService;

    private ApplicationEventPublisher applicationEventPublisher;

    private EventFactory eventFactory;

    @Autowired
    public void setTransMessageService(TransMessageService transMessageService) {
        this.transMessageService = transMessageService;
    }

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Autowired
    public void setEventFactory(EventFactory eventFactory) {
        this.eventFactory = eventFactory;
    }

    public void send(String exchange, String routingKey, Object payload) {
        log.info("send(): exchange: {}, routingKey: {}, payload: {}", exchange, routingKey, payload);
        // 将要发送的各种类型的数据结构序列化
        String payloadStr = JSON.toJSONString(payload);
        // 调用发送前的服务
        TransMessage transMessage = transMessageService.messageBeforeSend(exchange, routingKey, payloadStr);
        // rabbitTemplate 发送给MQ
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(contentType);
        Message message = new Message(payloadStr.getBytes(), messageProperties);
        message.getMessageProperties().setMessageId(transMessage.getMessageId());
        applicationEventPublisher.publishEvent(eventFactory.createEvent(exchange, routingKey, message, new CorrelationData(transMessage.getMessageId())));
    }
}
