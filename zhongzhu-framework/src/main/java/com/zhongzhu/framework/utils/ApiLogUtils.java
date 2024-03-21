package com.zhongzhu.framework.utils;

import com.alibaba.fastjson.JSON;
import com.zhongzhu.framework.annotations.ApiLog;
import com.zhongzhu.utils.common.IpUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.MessageFormat;

@Slf4j
@Component
public class ApiLogUtils {

    @Resource
    private HttpServletRequest request;

    private static final String SEPARATOR = " | ";

    private static final String REQUEST_INFO = "###请求信息###: URI:{0},Content-Type:{1},请求IP:{2}";

    private static final String METHOD_INFO = "###方法信息###: 方法名称:{0}";

    /**
     * 记录在执行proceed()方法之前的日志,包括:
     * 方法信息
     * 请求信息
     * 参数信息
     *
     * @param apiLog 注解
     * @param method 方法
     * @param args   方法参数
     */
    public void logBeforeProceed(ApiLog apiLog, Method method, Object[] args) {
        StringBuilder content = new StringBuilder("######日志######\n");
        content.append("Title:").append(StringUtils.isEmpty(apiLog.title()) ? apiLog.name() : apiLog.title()).append("\n");
        if (apiLog.printRequestInfo()) {
            content.append(MessageFormat.format(REQUEST_INFO, request.getRequestURI(), request.getContentType(), IpUtils.getIpAddress(request))).append("\n");
        }
        if (apiLog.printMethodInfo()) {
            content.append(MessageFormat.format(METHOD_INFO, method.getDeclaringClass().getSimpleName() + SEPARATOR + method.getName())).append("\n");
        }
        if (apiLog.type() == ApiLog.LogType.RETURN) {
            content.append("参数打印未启用!\n");
        } else {
            //排除类型
            Class<?>[] excludes = apiLog.excludes();
            content.append(getParamContent(args, excludes));
        }
        print(content.toString(), apiLog.level());
    }

    private StringBuilder getParamContent(Object[] args, Class<?>[] excludes) {
        StringBuilder paramContent = new StringBuilder("###参数信息###: ");
        for (Object arg : args) {
            if (arg == null) {
                continue;
            }
            if (exclude(arg.getClass(), excludes)) {
                paramContent.append("#排除的参数类型:").append(arg.getClass()).append(SEPARATOR);
            } else {
                paramContent.append("#参数类型:").append(arg.getClass()).append(" ").append("参数值:").append(arg.toString()).append(SEPARATOR);
            }
        }
        return paramContent;
    }

    /**
     * 判断指定类型是否需要排除
     *
     * @param target   指定类型
     * @param excludes 需要排除的类型集合
     * @return 排除:true
     */
    private boolean exclude(@Nullable Class<?> target, @NonNull Class<?>[] excludes) {
        if (ArrayUtils.isEmpty(excludes) || target == null) {
            return false;
        }

        for (Class<?> clazz : excludes) {
            if (clazz.equals(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 记录在执行proceed()方法之后的日志,包括:
     * 返回值信息
     * 执行耗时
     *
     * @param apiLog          注解
     * @param result          返回结果
     * @param timeConsumption 耗时
     */
    public void logAfterProceed(ApiLog apiLog, Object result, long timeConsumption) {
        StringBuilder content = new StringBuilder("###返回值信息###: ");
        if (apiLog.type() == ApiLog.LogType.PARAM) {
            content.append("未启用返回值打印");
        } else {
            content.append(getReturnContent(result, apiLog.excludes()));
        }

        if (apiLog.timeConsumption()) {
            content.append("执行耗时:").append(timeConsumption).append("ms");
        } else {
            content.append("未启用方法耗时打印");
        }
        print(content.toString(), apiLog.level());
    }

    private StringBuilder getReturnContent(@Nullable Object result, @NonNull Class<?>[] excludes) {
        StringBuilder content = new StringBuilder();
        try {
            if (result == null) {
                content.append("null");
                return content;
            }
            Class<?> clazz = result.getClass();
            if (exclude(clazz, excludes)) {
                content.append("被排除的类型:").append(clazz.getSimpleName());
            } else {
                content.append("返回值类型:").append(clazz.getSimpleName()).append(SEPARATOR).append("返回值:").append(JSON.toJSONString(result));
            }
            content.append("\n");
        } catch (Exception e) {
            log.error("Java对象转Json字符串失败!", e);
        }
        return content;
    }


    /**
     * 打印日志
     *
     * @param content 日志内容
     * @param level   日志级别
     */
    public void print(String content, ApiLog.LogLevel level) {

        switch (level) {
            case DEBUG:
                log.debug(content);
                break;
            case INFO:
                log.info(content);
                break;
            case WARN:
                log.warn(content);
                break;
            case ERROR:
                log.error(content);
                break;
            default:
                break;
        }
    }
}
