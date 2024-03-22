package com.zhongzhu.rabbit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * 连接信息
 */
@Component
@ConfigurationProperties(prefix = "zhongzhu.rabbit.mq")
@Data
public class RabbitMqProperty {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String vhost;

    private Integer concurrentConsumers;

    private Integer maxConcurrentConsumers;
}
