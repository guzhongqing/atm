package com.agufish.project.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String dateToString(String timestamp){
        long longTime = Long.parseLong(timestamp);
        Date date = new Date(longTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        return format;
    }
    public static String timeToString(String timestamp){
        long longTime = Long.parseLong(timestamp);
        Date date = new Date(longTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        return format;
    }
}
