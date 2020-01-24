package com.alibaba.android.arouter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.alibaba.android.arouter.launcher.ARouter;

public class PackageUtils {
    private static int NEW_VERSION_CODE;
    private static String NEW_VERSION_NAME;

    public static boolean isNewVersion(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) {
            return true;
        }
        String versionName = packageInfo.versionName;
        int versionCode = packageInfo.versionCode;
        SharedPreferences sp = context.getSharedPreferences(Consts.AROUTER_SP_CACHE_KEY, 0);
        if (versionName.equals(sp.getString(Consts.LAST_VERSION_NAME, null)) && versionCode == sp.getInt(Consts.LAST_VERSION_CODE, -1)) {
            return false;
        }
        NEW_VERSION_NAME = versionName;
        NEW_VERSION_CODE = versionCode;
        return true;
    }

    public static void updateVersion(Context context) {
        if (!TextUtils.isEmpty(NEW_VERSION_NAME) && NEW_VERSION_CODE != 0) {
            context.getSharedPreferences(Consts.AROUTER_SP_CACHE_KEY, 0).edit().putString(Consts.LAST_VERSION_NAME, NEW_VERSION_NAME).putInt(Consts.LAST_VERSION_CODE, NEW_VERSION_CODE).apply();
        }
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e) {
            ARouter.logger.error("ARouter::", "Get package info error.");
            return packageInfo;
        }
    }
}
