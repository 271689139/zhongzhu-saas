package com.zhongzhu.message;

import org.dromara.easyes.starter.register.EsMapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 处理信息消息 服务
 *
 * @author admin
 */
@SpringBootApplication(scanBasePackages = {"com.zhongzhu.*"})
@MapperScan({"com.zhongzhu.business.*.mapper"})
@EsMapperScan(value = "com.zhongzhu.elasticsearch.mapper")
public class MessageListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageListenerApplication.class, args);
    }

}
