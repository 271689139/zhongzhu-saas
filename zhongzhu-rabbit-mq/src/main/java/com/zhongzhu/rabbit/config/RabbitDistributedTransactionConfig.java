package com.zhongzhu.rabbit.config;

import com.zhongzhu.rabbit.mq.service.TransMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 * rabbit mq 配置
 */
@Configuration
@Slf4j
public class RabbitDistributedTransactionConfig {

    private TransMessageService transMessageService;

//    private RabbitMqProperty rabbitMqProperty;


    @Autowired
    public void setTransMessageService(TransMessageService transMessageService) {
        this.transMessageService = transMessageService;
    }

//    @Autowired
//    public void setRabbitMqProperty(RabbitMqProperty rabbitMqProperty) {
//        this.rabbitMqProperty = rabbitMqProperty;
//    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(rabbitMqProperty.getHost());
//        connectionFactory.setPort(rabbitMqProperty.getPort());
//        connectionFactory.setUsername(rabbitMqProperty.getUsername());
//        connectionFactory.setPassword(rabbitMqProperty.getPassword());
//        connectionFactory.setVirtualHost(rabbitMqProperty.getVhost());
//        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
//        connectionFactory.setPublisherReturns(true);
//        connectionFactory.createConnection();
//        return connectionFactory;
//    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
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
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息无法路由！message:{}, replyCode:{} replyText:{} exchange:{} routingKey:{}", message, replyCode, replyText, exchange, routingKey);
            transMessageService.handleMessageReturn(message.getMessageProperties().getMessageId(), exchange, routingKey, new String(message.getBody()));
        });
        return rabbitTemplate;
    }
}
