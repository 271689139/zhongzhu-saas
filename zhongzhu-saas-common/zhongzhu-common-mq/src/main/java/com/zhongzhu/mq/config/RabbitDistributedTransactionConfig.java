package com.zhongzhu.mq.config;


import com.zhongzhu.mq.service.TransMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shihao.liu
 * rabbit mq 配置
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class RabbitDistributedTransactionConfig {

    private final TransMessageService transMessageService;


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean("rabbitListenerContainerFactory")
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 为初始化连接池最小连接数(常驻链接)
        factory.setConcurrentConsumers(3);
        // 为最大连接池数量
        factory.setMaxConcurrentConsumers(10);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public RabbitTemplate customRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("correlationData:{}, ack:{}, cause:{}", correlationData, ack, cause);
            if (ack && null != correlationData) {
                // 通过correlationData找到确认的是哪条消息
                String messageId = correlationData.getId();
                log.info("消息已经正确投递到交换机, id:{}", messageId);
                transMessageService.sendMessageSuccess(messageId);
            } else {
                // 如果投递失败 定时任务会讲消息捞起来
                log.error("消息投递至交换机失败, correlationData:{}", correlationData);
            }
        });
        rabbitTemplate.setReturnsCallback((returnCallback) -> {
            log.error("消息无法路由！message:{}, replyCode:{} replyText:{} exchange:{} routingKey:{}", new String(returnCallback.getMessage().getBody()), returnCallback.getReplyCode(), returnCallback.getReplyText(), returnCallback.getExchange(), returnCallback.getRoutingKey());
            transMessageService.handleMessageReturn(returnCallback.getMessage().getMessageProperties().getMessageId(), returnCallback.getExchange(), returnCallback.getRoutingKey(), new String(returnCallback.getMessage().getBody()));
        });
        return rabbitTemplate;
    }
}