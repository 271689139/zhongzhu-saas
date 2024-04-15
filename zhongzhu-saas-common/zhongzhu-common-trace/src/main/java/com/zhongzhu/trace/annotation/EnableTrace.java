package com.zhongzhu.trace.annotation;

import com.zhongzhu.trace.config.TraceAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TraceAutoConfig.class)
public @interface EnableTrace {
}
