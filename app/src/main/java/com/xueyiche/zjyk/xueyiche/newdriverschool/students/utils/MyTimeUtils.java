package com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils
 * @ClassName: TimeUtils
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/1/29 16:25
 */
public class MyTimeUtils {
    public static boolean isSameDay(String time, String sameTime) {






        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = sdf2.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sameDate= null;
        try {
            sameDate = sdf2.parse(sameTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar nowCalendar = Calendar.getInstance();

        nowCalendar.setTime(sameDate);

        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(date);

        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)

                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)

                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {

            return true;

        }

        // if (date.getYear() == sameDate.getYear() && date.getMonth() == sameDate.getMonth()

        // && date.getDate() == sameDate.getDate()) {

        // return true;

        // }

        return false;

    }

    public static String dateFormat(String dateTime) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        String[] split = dateTime.split("-");
        instance.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1, Integer.parseInt(split[2]));
        String format = sdf2.format(instance.getTime());
        return format;
    }
}
