package com.zhongzhu.message.rabbit;

import com.zhongzhu.utils.constant.QueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

/**
 * @author admin
 * 测试消费
 */
@Slf4j
@Component

public class TestRabbitListener extends AbstractMessageListener {


    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueConstant.QUEUE_TEST, durable = "false", autoDelete = "true"
//            arguments = {@Argument(name = "x-dead-letter-exchange", value = QueueConstant.DEAD_LETTER_EXCHANGE),
//                    @Argument(name = "x-dead-letter-routing-key", value = QueueConstant.DEAD_QUEUE_ROUTE_KEY),
//                    @Argument(name = "x-message-ttl", value = QueueConstant.EXCHANGE_TTL)}
    ),
            exchange = @Exchange(value = QueueConstant.EXCHANGE_TEST_BUSINESS, type = ExchangeTypes.TOPIC), key = QueueConstant.TEST_ROUTE_KEY),
            concurrency = "2", containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(Message message, Channel channel) throws Exception {
        onMessage(message, channel, msg -> log.info("收到消息:" + msg));
    }
}
