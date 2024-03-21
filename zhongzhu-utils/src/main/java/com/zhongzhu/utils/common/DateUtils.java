package com.zhongzhu.utils.common;

import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class DateUtils extends DateUtil {
    public static Long getEndTimeByToday() {
        Calendar instance = Calendar.getInstance();
        Date now = new Date();
        instance.setTime(now);
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        return instance.getTime().getTime() - now.getTime();
    }
}
