package com.zhongzhu.framework.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 * knife4j
 * @link <a href="https://doc.xiaominfo.com/">knife4j官网</a>
 * 增加header/ 分组等方法看官网教程
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("中柱服务")
                        .description("中柱服务接口")
                        .version("v1")
                        .contact(new Contact().name("yuxiang.dai").email("yuxiang.dai@gmail.com"))
                );
    }
}
