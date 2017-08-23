package com.allen.onlyweather.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Allen.
 */

public class TimeUtil {

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat WEEK_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat HOUR_MINUTE = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat HOUR = new SimpleDateFormat("HH");

    private static String AM_TIP = "";
    private static String PM_TIP = "";
    private static String YESTERDAY = "昨天";
    private static String BEFORE_YESTERDAY = "前天";
    private static long DAY_OF_YEAR = 365;
    private static long DAY_OF_MONTH = 30;
    private static long HOUR_DAY = 24;
    private static long MIN_OF_HOUR = 60;
    private static long SEC_OF_MIN = 60;
    private static long MILLIS_OF_SEC = 1000;

    /**
     * 新时间戳显示
     * 0<X<1分钟     ：  刚刚
     * 1分钟<=X<60分钟    :    X分钟前         EX:5分钟前
     * 1小时<=X<24小时    ：  X小时前        EX：3小时前
     * 1天<=X<7天         ：   X天前           Ex： 4天前
     * 1周<=X<1个月     ：   X周前           EX：3周前
     * X>=1个月           :      YY/MM/DD   HH:MM       EX：15/05/15  15:34
     */

    public static boolean isNight() {
        int currentHour = Integer.parseInt(HOUR.format(new Date()));
        return currentHour >= 19 || currentHour <= 6;
    }

    public static String getTimeTips(String formatTime) {
        String timeTips = formatTime;
        try {
            Date date = DATE_FORMAT.parse(formatTime);
            timeTips = getTimeTips(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeTips;
    }

    private static String getTimeTips(long timeStamp) {
        long now = System.currentTimeMillis() / 1000;
        String tips;
        long diff = now - timeStamp / 1000;
        if (diff < 60) {
            tips = "刚刚";
        } else if ((diff /= 60) < 60) {
            tips = String.format("%d分钟前", diff);
        } else if ((diff /= 60) < 24) {
            tips = String.format("%d小时前", diff);
        } else if ((diff /= 24) < 7) {
            tips = String.format("%d天前", diff);
        } else if ((diff /= 7) < 7) {
            tips = String.format("%d周前", diff);
        } else {
            tips = DATE_FORMAT.format(new Date(timeStamp * 1000));
        }
        return tips;
    }

    public static String getDateWeek(String formatTime) {
        String dateWeek = formatTime;
        try {
            Date date = WEEK_FORMAT.parse(formatTime);
            dateWeek = getDateWeek(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateWeek;
    }

    private static String getDateWeek(long timeStamp) {
        long zero = System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        int diff = (int)(timeStamp - zero) / (1000 * 3600 * 24);
        String dateWeek = "$";
        switch (diff) {
            case -1:
                dateWeek = "昨天";
                break;
            case -2:
                dateWeek = "前天";
                break;
            case 0:
                dateWeek = "今天";
                break;
            case 1:
                dateWeek = "明天";
                break;
            case 2:
                dateWeek = "后天";
                break;
        }
        return dateWeek;
    }

}
