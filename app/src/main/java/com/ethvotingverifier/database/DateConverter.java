package com.ethvotingverifier.database;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static DateFormat dfDayMonth = new SimpleDateFormat("MM-dd");

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(Date value) {
        return value == null ? null : df.format(value);
    }

    public static String dateToDayAndMonth(Date value) {
        return value == null ? null : dfDayMonth.format(value);
    }
}
