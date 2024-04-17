package com.zhongzhu.mq.transmitter;


import com.alibaba.fastjson.JSON;
import com.zhongzhu.core.i18n.SysConstants;
import com.zhongzhu.mq.domain.TransMessage;
import com.zhongzhu.mq.factory.EventFactory;
import com.zhongzhu.mq.service.TransMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * 业务代码与RabbitTemplate的中间层
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TransMessageTransmitter {


    private final TransMessageService transMessageService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final EventFactory eventFactory;


    public void send(String exchange, String routingKey, Object payload) {
        log.info("send(): exchange: {}, routingKey: {}, payload: {}", exchange, routingKey, payload);
        // 将要发送的各种类型的数据结构序列化
        String payloadStr = JSON.toJSONString(payload);
        // 调用发送前的服务
        TransMessage transMessage = transMessageService.messageBeforeSend(exchange, routingKey, payloadStr);
        // rabbitTemplate 发送给MQ
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(SysConstants.CONTENT_TYPE);
        Message message = new Message(payloadStr.getBytes(), messageProperties);
        message.getMessageProperties().setMessageId(transMessage.getMessageId());
        applicationEventPublisher.publishEvent(eventFactory.createEvent(exchange, routingKey, message, new CorrelationData(transMessage.getMessageId())));
    }
}
