package com.zhongzhu.utils.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @author admin
 * 获取请求ip
 */
public class IpUtils {
    private static final String IP_ADDRESS_LOCALHOST = "127.0.0.1";

    private static final String UNKNOWN = "unknown";


    /**
     * HttpServletRequest获取Ip地址
     *
     * @param request 请求信息
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String flag = ",";
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && !ip.isEmpty() && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(flag)) {
                ip = ip.split(flag)[0];
            }
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 将ip地址转换成long类型的数字,仅适用于ipv4
     * 例如:192.168.0.1 to 3232235521
     *
     * @param ipStr ip字符串
     * @return long类型数据
     */
    public static long ipStr2Long(String ipStr) {
        AssertUtil.isNotEmpty(ipStr, "ipStr must not be null or blank");
        String[] ipSubArray = ipStr.split("\\.");
        return (Long.parseLong(ipSubArray[0]) << 24) + (Long.parseLong(ipSubArray[1]) << 16) + (Long.parseLong(ipSubArray[2]) << 8) + (Long.parseLong(ipSubArray[0]));
    }

    /**
     * 将由字符串ip转换的数字还原成对应ip字符串
     *
     * @param number 数字
     * @return ip
     */
    public static String long2IpStr(long number) {
        return (number >>> 24) + "." + ((number >>> 16) & 0xFF) + "." + (number >>> 8 & 0xFF) + "." + (number & 0xFF);
    }

}
