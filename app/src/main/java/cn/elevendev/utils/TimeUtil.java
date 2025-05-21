package cn.elevendev.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    /** 默认时间格式 */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间字符串
     *
     * @return 当前时间字符串
     */
    public static String getCurrentTime() {
        return getCurrentTime(DEFAULT_FORMAT);
    }

    /**
     * 获取当前时间字符串，使用指定格式
     *
     * @param pattern 时间格式，如 "yyyy-MM-dd"
     * @return 当前时间字符串
     */
    public static String getCurrentTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * 获取当前年份
     *
     * @return 当前年份
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;  // Calendar.MONTH 从0开始
    }

    /**
     * 获取当前日
     *
     * @return 当前日
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 将时间戳（毫秒）转换为时间字符串
     *
     * @param timestamp 时间戳（毫秒）
     * @param pattern   时间格式
     * @return 格式化后的时间字符串
     */
    public static String formatTime(long timestamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    /**
     * 将时间字符串解析为时间戳（毫秒）
     *
     * @param timeStr 时间字符串
     * @param pattern 时间格式
     * @return 对应的时间戳（毫秒），解析失败返回 -1
     */
    public static long parseTime(String timeStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date date = sdf.parse(timeStr);
            return date != null ? date.getTime() : -1;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 将秒级时间戳格式化为时间字符串
     *
     * @param seconds 秒级时间戳
     * @param pattern 时间格式
     * @return 格式化后的时间字符串
     */
    public static String formatSeconds(long seconds, String pattern) {
        return formatTime(seconds * 1000, pattern);
    }

    /**
     * 将时间字符串转换为秒级时间戳
     *
     * @param timeStr 时间字符串
     * @param pattern 时间格式
     * @return 秒级时间戳，解析失败返回 -1
     */
    public static long parseToSeconds(String timeStr, String pattern) {
        long millis = parseTime(timeStr, pattern);
        return millis == -1 ? -1 : millis / 1000;
    }
}
