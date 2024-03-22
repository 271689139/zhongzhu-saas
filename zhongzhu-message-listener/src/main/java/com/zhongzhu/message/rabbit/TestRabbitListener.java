package com.zhongzhu.message.rabbit;

import com.zhongzhu.utils.constant.QueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * 测试消费
 */
@Slf4j
@Component
public class TestRabbitListener {

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueConstant.QUEUE_TEST, durable = "false", autoDelete = "true"),
            exchange = @Exchange(value = QueueConstant.EXCHANGE_TEST_BUSINESS, type = ExchangeTypes.TOPIC), key = QueueConstant.TEST_ROUTE_KEY),
            concurrency = "2")
    public void consumer(String data) {
        log.info("收消息:" + data);
    }
}
