package com.zhongzhu.property;

import com.zhongzhu.redis.annotation.EnableRedisRepository;
import com.zhongzhu.redis.annotation.EnableRedissonRepository;
import com.zhongzhu.trace.annotation.EnableTrace;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.InetAddress;

/**
 * @author admin
 * 启动类
 */
@SpringBootApplication(scanBasePackages = "com.zhongzhu.*")
@EnableConfigurationProperties
@EnableRedisRepository
@EnableRedissonRepository
@MapperScan({"com.zhongzhu.*.mapper"})
@EnableTrace
public class PropertyApplication {

    public static void main(String[] args) throws Exception {
        System.setProperty("ip", InetAddress.getLocalHost().getHostAddress());
        new SpringApplicationBuilder(PropertyApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
