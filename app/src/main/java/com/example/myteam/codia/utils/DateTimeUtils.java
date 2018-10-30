package com.example.myteam.codia.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static Date convertTimeMillisToDate(long timeMillis) {
        return new Date(timeMillis);
    }

    public static String dateTimeToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy hh:mm");
        return format.format(date);
    }
}
