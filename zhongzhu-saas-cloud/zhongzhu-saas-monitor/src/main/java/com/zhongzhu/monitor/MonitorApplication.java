package com.zhongzhu.monitor;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.SneakyThrows;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.net.InetAddress;

/**
 * @author shihao.liu
 * 服务监控启动
 */
@SpringBootApplication
@EnableAdminServer
@EnableEncryptableProperties
public class MonitorApplication {

    @SneakyThrows
    public static void main(String[] args) {
        System.setProperty("ip", InetAddress.getLocalHost().getHostAddress());
        new SpringApplicationBuilder(MonitorApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
