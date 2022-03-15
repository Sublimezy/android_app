package com.xueyiche.zjyk.xueyiche.homepage.view;

import android.annotation.SuppressLint;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 日期时间工具类
 */
public class DateUtils {
    public static final int Second = 0;
    public static final int Minute = 1;
    public static final int Hour = 2;
    public static final int Day = 3;

    @IntDef(value = {Second, Minute, Hour, Day})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DifferenceMode {
    }

    /**
     * 计算每月的天数
     */
    public static int calculateDaysInMonth(int month) {
        return calculateDaysInMonth(0, month);
    }

    /**
     * 根据年份及月份计算每月的天数
     */
    public static int calculateDaysInMonth(int year, int month) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] bigMonths = {"1", "3", "5", "7", "8", "10", "12"};
        String[] littleMonths = {"4", "6", "9", "11"};
        List<String> bigList = Arrays.asList(bigMonths);
        List<String> littleList = Arrays.asList(littleMonths);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (bigList.contains(String.valueOf(month))) {
            return 31;
        } else if (littleList.contains(String.valueOf(month))) {
            return 30;
        } else {
            if (year <= 0) {
                return 29;
            }
            // 是否闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
    }
    /*将字符串转为毫秒*/
    public static long getStringToHm(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            try{
                date = sdf.parse(time);
            } catch(ParseException e) {
                e.printStackTrace();
            }
            return date.getTime();
        }else {
            return 0;
        }

    }
    /**
     * 获取当前时间间隔半小时
     *
     * @return
     */
    public static ArrayList<String> getHh() {
        ArrayList<String> a = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String dangqian_time = formatter.format(curDate);
        String dangqian_shi = dangqian_time.substring(11, 13);
        String dangqian_fen = dangqian_time.substring(14, 16);
        int dangqian_shi_int;
        int dangqian_fen_int;
        dangqian_shi_int = Integer.parseInt(dangqian_shi);
        dangqian_fen_int = Integer.parseInt(dangqian_fen);
        if (dangqian_fen_int < 30) {
            for (int i = dangqian_shi_int; i < 24; i++) {
                a.add(fillZero(dangqian_shi_int) + ":30");
                dangqian_shi_int = dangqian_shi_int + 1;
                a.add(fillZero(dangqian_shi_int) + ":00");
            }
            return a;
        } else {
            for (int i = dangqian_shi_int; i < 23; i++) {
                dangqian_shi_int = dangqian_shi_int + 1;
                a.add(fillZero(dangqian_shi_int) + ":00");
                a.add(fillZero(dangqian_shi_int) + ":30");
            }
            a.add("24:00");
            return a;
        }
    }


    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String getWeek(Date time) {
        String Week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "星期日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "星期一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "星期二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "星期三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "星期四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "星期五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "星期六";
        }
        return Week;
    }


    /**
     * 获取今天往后30天的日期（几月几号星期几）
     */
    public static List<String> getMonthdate( String mYear, String mMonth, String mDay) {
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        for (int i = Integer.parseInt(mDay); i <= MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), Integer.parseInt(mMonth)); i++) {
            if (mMonth.length() < 2)
                mMonth = "0" + mMonth;
            String Day = String.valueOf(i);
            String string = mYear + "-" + mMonth + "-" + Day;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = null;
            try {
                parse = sdf.parse(string);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String week = getWeek(parse);
            String date = mMonth + "月" + fillZero(Integer.parseInt(Day)) + "日 " + week;
            dates.add(date);
        }
        int nextMonth = Integer.parseInt(mMonth) + 1;
        int nownian = Integer.parseInt(mYear);
        if (nextMonth>12) {
            nextMonth = 1;
            nownian+=1;
        }
        for (int i = 1; i <= Integer.parseInt(mDay)&& i <=MaxDayFromDay_OF_MONTH(nownian,nextMonth); i++) {
            String Day = String.valueOf(i);
            String string = nownian + "-" + nextMonth + "-" + Day;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = null;
            try {
                parse = sdf.parse(string);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String week = getWeek(parse);
            String date = fillZero(nextMonth) + "月" + fillZero(Integer.parseInt(Day)) + "日" + week;
            dates.add(date);
        }
        return dates;
    }
    public static long dateDiff(String startTime, String endTime) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("MM月dd日hh:mm");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println(day + "天" + hour + "小时" + min
                    + "分钟");
            String tent_time;
            String tent_day = day+"天";
            String tent_hour;
            String tent_min;
            if (0==day) {
//                tent_time =
            }

//            PrefUtils.putString("rent_time",);


            if (day>=1) {
                return day;
            }else {
                if (day==0) {
                    return 1;
                }else {
                    return 0;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }
    /**
     * 得到当年当月的最大日期
     **/
    public static int MaxDayFromDay_OF_MONTH(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month - 1);//注意,Calendar对象默认一月为0
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

    /**
     * 月日时分秒，0-9前补0
     */
    @NonNull
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    /**
     * 截取掉前缀0以便转换为整数
     *
     * @see #fillZero(int)
     */
    public static int trimZero(@NonNull String text) {
        if (text.startsWith("0")) {
            text = text.substring(1);
        }
        return Integer.parseInt(text);
    }

    /**
     * 功能：判断日期是否和当前date对象在同一天。
     * 参见：http://www.cnblogs.com/myzhijie/p/3330970.html
     *
     * @param date 比较的日期
     * @return boolean 如果在返回true，否则返回false。
     */
    public static boolean isSameDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        Calendar nowCalendar = Calendar.getInstance();
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(date);
        return (nowCalendar.get(Calendar.ERA) == newCalendar.get(Calendar.ERA) &&
                nowCalendar.get(Calendar.YEAR) == newCalendar.get(Calendar.YEAR) &&
                nowCalendar.get(Calendar.DAY_OF_YEAR) == newCalendar.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr    时间字符串
     * @param dataFormat 当前时间字符串的格式。
     * @return Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr, String dataFormat) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
            Date date = dateFormat.parse(dateStr);
            return new Date(date.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @return Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

}
