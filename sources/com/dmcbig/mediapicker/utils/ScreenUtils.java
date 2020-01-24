package com.dmcbig.mediapicker.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class ScreenUtils {
    private ScreenUtils() {
        throw new UnsupportedOperationException("本屏幕工具类不能被实例化,直接调用静态方法就好");
    }

    public static boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public static int getDpi(Context context) {
        int dpi = 0;
        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[]{DisplayMetrics.class}).invoke(display, new Object[]{displayMetrics});
            return displayMetrics.heightPixels;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return dpi;
        }
    }

    public static int getBottomStatusHeight(Context context) {
        return getDpi(context) - getScreenHeight(context);
    }

    public static int getTitleHeight(Activity activity) {
        return activity.getWindow().findViewById(16908290).getTop();
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(clazz.getField("status_bar_height").get(clazz.newInstance()).toString()));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return statusHeight;
        }
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(1, dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(2, spVal, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxVal) {
        return pxVal / context.getResources().getDisplayMetrics().density;
    }

    public static float px2sp(Context context, float pxVal) {
        return pxVal / context.getResources().getDisplayMetrics().scaledDensity;
    }
}
