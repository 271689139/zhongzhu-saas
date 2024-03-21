package com.zhongzhu.framework.kafka;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author admin
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class KafkaAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    protected Encoder<ILoggingEvent> encoder;
    /**
     * 服务名称
     */
    private String appName;
    /**
     * kafka 地址
     */
    private String hosts;
    /**
     * 主题
     */
    private String topic;


    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        try {
            LogMessage logMessage = new LogMessage();
            logMessage.setServerName(appName);
            logMessage.setIp(getServerIp());
            logMessage.setLoggerName(iLoggingEvent.getLoggerName());
            logMessage.setLevel(iLoggingEvent.getLevel().toString());
            logMessage.setThreadName(iLoggingEvent.getThreadName());
            logMessage.setMessage(processMessage(iLoggingEvent));
            //日志生产的时间
            logMessage.setTimeStamp(iLoggingEvent.getTimeStamp());

            //MDC请求跟踪
            Map<String, String> mdc = iLoggingEvent.getMDCPropertyMap();
            String traceId = mdc.get("TRACE_ID");
            logMessage.setTraceId(traceId);
            KafkaProduceClient.getInstance(hosts).push(topic, JSON.toJSONString(logMessage));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取服务器ip
     *
     * @return ip地址
     */
    public String getServerIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 处理日志信息
     */
    private String processMessage(ILoggingEvent event) {
        if (event.getLevel().equals(Level.ERROR)) {
            if (event.getThrowableProxy() != null) {
                ThrowableProxy proxy = (ThrowableProxy) event.getThrowableProxy();
                return getStackTrace(proxy.getThrowable());
            } else {
                return event.getFormattedMessage();
            }
        }
        return event.getFormattedMessage();
    }

    /**
     * 获取异常日志
     */
    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
