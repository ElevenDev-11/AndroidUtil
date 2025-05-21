package cn.elevendev.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtils {

    /**
     * 设置状态栏颜色和状态栏图标颜色
     *
     * @param activity   当前Activity
     * @param colorResId 状态栏背景颜色
     * @param isDarkText 状态栏图标颜色，true 表示黑色，false 表示白色
     */
    public static void setStatusBar(Activity activity, int color, boolean isDarkText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
        
            int visibility = decorView.getSystemUiVisibility();
            visibility &= ~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN; // 移除全屏标志
            visibility &= ~View.SYSTEM_UI_FLAG_LAYOUT_STABLE; // 避免布局适配问题
            decorView.setSystemUiVisibility(visibility);

            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.setStatusBarColor(color);

            if (isDarkText) {
                visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(visibility);
        }
    }


    /**
     * 沉浸式状态栏样式，并根据导航栏的可见性动态调整底部 padding
     *
     * @param activity 当前的 Activity
     * @param isDarkText 状态栏图标颜色，true 表示黑色，false 表示白色
     */
    public static void setTransparentStatusBar(Activity activity, boolean isDarkText) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        ViewGroup rootLayout = (ViewGroup) contentView.getChildAt(0);
        View rootView = activity.getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if (NavigationBarUtil.isNavigationBarVisible(rootView)) {
                    setBottomPadding(rootLayout, NavigationBarUtil.getNavigationBarHeight(rootView));
                } else {
                    setBottomPadding(rootLayout, 0);
                }                     
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    View decorView = activity.getWindow().getDecorView();
                    int flags = decorView.getSystemUiVisibility();
                    if (isDarkText) {
                        flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    } else {
                        flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    }
            
                    decorView.setSystemUiVisibility(flags);
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        });
    }
    
    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return 状态栏的高度（单位：像素）
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置根布局的底部内边距
     *
     * @param rootLayout 根布局
     * @param originalBottomPadding 原始的底部内边距
     * @param navigationHeight 导航栏的高度
     */
    private static void setBottomPadding(ViewGroup rootLayout, int navigationHeight) {
        rootLayout.setPadding(
            rootLayout.getPaddingLeft(),
            rootLayout.getPaddingTop(),
            rootLayout.getPaddingRight(),
            navigationHeight
        );
    }
}
