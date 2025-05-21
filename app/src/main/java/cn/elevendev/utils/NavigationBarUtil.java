package cn.elevendev.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
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
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
        int height = resources.getDimensionPixelSize(resourceId);
        boolean isNavBarHidden = (systemUiVisibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0;

        if (isNavBarHidden || height < 60) {
            return 0;
        }

        return height;
    }
    
    /**
     * 判断导航栏是否可见
     *
     * @param activity 当前 Activity
     * @return 如果导航栏可见返回 true，否则返回 false
     */
    public static boolean isNavigationBarVisible(Activity activity) {
        int val = Settings.Global.getInt(activity.getContentResolver(), getDeviceInfo(), 0);
        boolean isNavBarShown = (val == 0);
        
        int navBarHeight = getNavigationBarHeight(activity);
        if (navBarHeight < 60) {
            return false;
        }

        return isNavBarShown;
    }

    /**
     * 根据设备品牌返回判断导航栏是否显示的系统设置字段名
     *
     * @return 系统设置中用于判断导航栏显示状态的键名
     */
    private static String getDeviceInfo() {
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) return "navigationbar_is_min";

        switch (brand.toLowerCase()) {
            case "xiaomi":
                return "force_fsg_nav_bar";
            case "vivo":
            case "oppo":
            case "oneplus":
                return "navigation_gesture_on";
            case "huawei":
            default:
                return "navigationbar_is_min";
        }
    }
}
