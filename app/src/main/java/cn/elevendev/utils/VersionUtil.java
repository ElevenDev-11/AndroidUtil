package cn.elevendev.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class VersionUtil {

    /**
     * 判断是否需要更新
     *
     * @param context 上下文
     * @param context 服务器版本号
     * @return 是否需要更新
     */
    public static boolean isUpdateNeeded(Context context, String serverVersion) {
        int[] versions = padVersions(getVersion(context), Integer.parseInt(serverVersion.replace(".", "")));
        return versions[0] < versions[1];
    }
    
    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }
    
    /**
     * 获取版本 code
     *
     * @param context 上下文
     * @return 版本 code
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    /**
     * 获取当前应用的版本号
     *
     * @return 当前版本号的整数表示形式
     */
    private static int getVersion(Context context) {
        return Integer.parseInt(getVersionName(context).replace(".", ""));
    }

    /**
     * 将两个版本号字符串补齐至相同长度
     *
     * @param version 当前版本号
     * @param newVersion 新版本号
     * @return 返回补齐后的两个版本号数组
     */
    private static int[] padVersions(int localVersion, int serverVersion) {
        String versionStr1 = String.valueOf(localVersion);
        String versionStr2 = String.valueOf(localVersion);
        int maxLength = Math.max(versionStr1.length(), versionStr2.length());
        
        while (versionStr1.length() < maxLength) {
            versionStr1 += "0";
        }
        while (versionStr2.length() < maxLength) {
            versionStr2 += "0";
        }

        int paddedVersion1 = Integer.parseInt(versionStr1);
        int paddedVersion2 = Integer.parseInt(versionStr2);

        return new int[]{paddedVersion1, paddedVersion2};
    }
}
