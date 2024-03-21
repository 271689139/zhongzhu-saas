package com.zhongzhu.framework.utils;

import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author admin
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }

    public static <T> T getBean(String name, Class<T> clz) {
        return applicationContext.getBean(name, clz);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

}
