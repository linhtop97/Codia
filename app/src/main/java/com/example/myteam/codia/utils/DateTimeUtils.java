package com.example.myteam.codia.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static Date convertTimeMillisToDate(long timeMillis) {
        return new Date(timeMillis);
    }
}
