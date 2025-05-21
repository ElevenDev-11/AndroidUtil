package cn.elevendev.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayMetricsUtil {
    
    /**
     * 获取屏幕的宽度（像素）
     *
     * @param context 上下文
     * @return 屏幕宽度（像素）
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null && wm.getDefaultDisplay() != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
            return metrics.widthPixels;
        }
        return 0;
    }

    /**
     * 获取屏幕的高度（像素）
     *
     * @param context 上下文
     * @return 屏幕高度（像素）
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null && wm.getDefaultDisplay() != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
            return metrics.heightPixels;
        }
        return 0;
    }

    /**
     * 获取屏幕的密度
     *
     * @param context 上下文
     * @return 屏幕密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕的密度 DPI
     *
     * @param context 上下文
     * @return 屏幕密度 DPI
     */
    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * px转dp
     *
     * @param context 上下文
     * @param px 值
     * @return dp 值
     */
    public static int px2dp(Context context, int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dp 值
     * @return px 值
     */
    public static int dp2px(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
    
    /**
     * sp转px
     *
     * @param context 上下文
     * @param sp 值
     * @return px 值
     */
    public static int sp2px(Context context, int sp) {
        return (int) (sp * context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * px 转 sp
     *
     * @param context 上下文
     * @param px 像素值
     * @return sp 值
     */
    public static int px2sp(Context context, int px) {
        return (int) (px / context.getResources().getDisplayMetrics().scaledDensity);
    }
    
}
