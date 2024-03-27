package com.zhongzhu.message.rabbit;

import com.rabbitmq.client.Channel;
import com.zhongzhu.rabbit.mq.domain.TransMessage;
import com.zhongzhu.rabbit.mq.service.TransMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author admin
 * rabbit 消费模版
 */
@Slf4j
@Component
public abstract class AbstractMessageListener {

    private TransMessageService transMessageService;

    @Autowired
    public void setTransMessageService(TransMessageService transMessageService) {
        this.transMessageService = transMessageService;
    }


    public void onMessage(Message message, Channel channel, Consumer<String> consumer) throws Exception {
        MessageProperties properties = message.getMessageProperties();
        long deliveryTag = properties.getDeliveryTag();

        TransMessage transMessage = transMessageService.messageBeforeConsume(
                properties.getMessageId(), properties.getReceivedExchange(),
                properties.getReceivedRoutingKey(), properties.getConsumerQueue(), new String(message.getBody()));
        log.info("消费端收到消息, id: {}, 消费次数: {}", transMessage.getId(), transMessage.getSequence());

        try {
            // 接收处理业务逻辑的函数
            consumer.accept(new String(message.getBody()));
            channel.basicAck(deliveryTag, false);
            transMessageService.consumeMessageSuccess(transMessage.getMessageId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (transMessage.getSequence() > 3) {
                channel.basicReject(deliveryTag, false);
                transMessageService.consumeMessageFailed(transMessage.getMessageId());
            } else {
                // 等待一段时间，等待时间由重试次数决定
                Thread.sleep((long) (Math.pow(2, transMessage.getSequence()) * 1000));
                // 拒收消息 并设置为重回队列
                channel.basicNack(deliveryTag, false, true);
            }
        }
    }
}
