package com.zhongzhu.task;

import com.zhongzhu.task.config.XxlJobProperties;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author admin
 * 定时任务
 */
@SpringBootApplication(scanBasePackages = {"com.zhongzhu.*"})
@MapperScan({"com.zhongzhu.*.*.mapper"})
@EsMapperScan(value = "com.zhongzhu.elasticsearch.mapper")
@EnableConfigurationProperties(XxlJobProperties.class)
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
