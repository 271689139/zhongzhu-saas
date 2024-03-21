package com.zhongzhu.elasticsearch.domain;

import lombok.Data;
import org.dromara.easyes.annotation.IndexName;

import java.io.Serializable;

/**
 * @author admin
 * 服务日志
 */
@Data
@IndexName(value = "zhongzhu-service-log", aliasName = "zhongzhu-service-log-alias")
public class ZhongzhuServiceLog implements Serializable {

    /**
     * es自动生成
     *
     * @see org.dromara.easyes.annotation.IndexField
     */
    private String id;

    /**
     * 时间
     */
    private Long timeStamp;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 类名
     */
    private String loggerName;

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 日志内容
     */
    private String message;

    /**
     * 服务器ip
     */
    private String ip;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 链路id
     */
    private String traceId;
}
