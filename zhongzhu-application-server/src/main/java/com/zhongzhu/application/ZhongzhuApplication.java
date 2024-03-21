package com.zhongzhu.application;

import com.zhongzhu.framework.interceptor.LogInterceptor;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author admin
 * 启动类
 */
@SpringBootApplication(scanBasePackages = {"com.zhongzhu.*"})
@MapperScan({"com.zhongzhu.business.*.mapper"})
@EsMapperScan(value = "com.zhongzhu.elasticsearch.mapper")
public class ZhongzhuApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ZhongzhuApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
