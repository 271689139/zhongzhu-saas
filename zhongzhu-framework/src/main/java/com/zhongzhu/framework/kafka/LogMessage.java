package com.zhongzhu.framework.kafka;

import lombok.Data;

@Data
public class LogMessage {

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
