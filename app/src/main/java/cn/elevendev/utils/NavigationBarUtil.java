package cn.elevendev.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;

public class NavigationBarUtil {

    /**
     * 获取导航栏的高度（像素）
     *
     * @param activity 当前 Activity
     * @return 导航栏高度（像素），如果导航栏被隐藏或高度小于 60 则返回 0
     */
    public static int getNavigationBarHeight(Activity activity) {
        Window window = activity.getWindow();
        if (window != null) {
            View viewNavigation = window.getDecorView().findViewById(android.R.id.navigationBarBackground);
            if (viewNavigation != null) {
                return viewNavigation.getMeasuredHeight();
            }
        }
        
        return 0;
    }
    
    /**
     * 判断导航栏是否可见
     *
     * @param activity 当前 Activity
     * @return 如果导航栏可见返回 true，否则返回 false
     */
    public static boolean isNavigationBarVisible(Activity activity) {
        return getNavigationBarHeight(activity) != 0;
    }
    
    /**
     * 隐藏导航栏
     *
     * @param activity 当前 Activity
     */
    public static void hideNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    /**
     * 显示导航栏
     *
     * @param activity 当前 Activity
     */
    public static void showNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 设置导航栏颜色
     *
     * @param activity 当前 Activity
     * @param color 颜色字符串
     */
    public static void setNavigationBarColor(Activity activity, String color) {
        setNavigationBarColor(activity, Color.parseColor(color));
    }
    
    /**
     * 设置导航栏颜色
     *
     * @param activity 当前 Activity
     * @param color 颜色值（十六进制 int 类型，例如 0xFFFFFFFF）
     */
    public static void setNavigationBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }
    
    /**
     * 设置导航栏图标为深色或浅色
     *
     * @param activity 当前 Activity
     * @param dark 是否深色图标
     */
    public static void setNavigationBarIconDark(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View decorView = activity.getWindow().getDecorView();
            int flags = decorView.getSystemUiVisibility();
            if (dark) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            decorView.setSystemUiVisibility(flags);
        }
    }
    
}
