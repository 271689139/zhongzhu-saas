package com.zhongzhu.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author shihao.liu
 */
@Configuration
public class SwaggerConfig {

    public static final String SPRING_APPLICATION_NAME = "spring.application.name";


    private Environment environment;


    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
//    @Bean
//    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
//        return openApi -> {
//            if (openApi.getTags()!=null){
//                openApi.getTags().forEach(tag -> {
//                    Map<String,Object> map= Maps.newHashMap();
//                    map.put("x-order", "123");
//                    tag.setExtensions(map);
//                });
//            }
//            if(openApi.getPaths()!=null){
//                openApi.addExtension("x-test123","333");
//                openApi.getPaths().addExtension("x-abb",RandomUtil.randomInt(1,100));
//            }
//
//        };
//    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(environment.getProperty(SPRING_APPLICATION_NAME))
                        .version("1.0")
                        .description(environment.getProperty(SPRING_APPLICATION_NAME))
                        .contact(new Contact().name("shihao.liu").email("271689139@qq.com")));
    }

}
