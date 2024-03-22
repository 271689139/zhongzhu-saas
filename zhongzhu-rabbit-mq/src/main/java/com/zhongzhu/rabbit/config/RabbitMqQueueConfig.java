package com.zhongzhu.rabbit.config;

import com.zhongzhu.utils.constant.QueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * @author admin
 * 队列配置
 */
@Slf4j
public class RabbitMqQueueConfig {

    /**
     * 测试交换机
     */
    @Bean
    public Exchange testExchange() {
        return ExchangeBuilder.directExchange(QueueConstant.EXCHANGE_TEST_BUSINESS).build();
    }


    @Bean
    public Queue testQueue() {
        return QueueBuilder.durable(QueueConstant.QUEUE_TEST).deadLetterExchange(QueueConstant.DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(QueueConstant.TEST_ROUTE_KEY).maxLength(100).build();
    }

    @Bean
    public Binding testtBinding(@Qualifier("testQueue") Queue queue,
                                @Qualifier("testExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueueConstant.TEST_ROUTE_KEY).noargs();
    }

}
