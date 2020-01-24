package com.iwown.my_module.utility.flynstatusbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Eyes {
    public static void setStatusBarColor(Activity activity, int statusColor) {
        if (VERSION.SDK_INT >= 21) {
            EyesLollipop.setStatusBarColor(activity, statusColor);
        } else if (VERSION.SDK_INT >= 19) {
            EyesKitKat.setStatusBarColor(activity, statusColor);
        }
    }

    public static void translucentStatusBar(Activity activity) {
        translucentStatusBar(activity, false);
    }

    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        if (VERSION.SDK_INT >= 21) {
            EyesLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if (VERSION.SDK_INT >= 19) {
            EyesKitKat.translucentStatusBar(activity);
        }
    }

    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, @ColorInt int statusColor) {
        if (VERSION.SDK_INT >= 21) {
            EyesLollipop.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        } else if (VERSION.SDK_INT >= 19) {
            EyesKitKat.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        }
    }

    public static void setStatusBarLightMode(Activity activity, int color) {
        if (VERSION.SDK_INT < 19) {
            return;
        }
        if (MIUISetStatusBarLightMode(activity, true) || FlymeSetStatusBarLightMode(activity, true)) {
            if (VERSION.SDK_INT >= 21) {
                activity.getWindow().setStatusBarColor(color);
            } else if (VERSION.SDK_INT >= 19) {
                setStatusBarColor(activity, color);
            }
        } else if (VERSION.SDK_INT >= 23) {
            activity.getWindow().setBackgroundDrawableResource(17170445);
            activity.getWindow().getDecorView().setSystemUiVisibility(9216);
            activity.getWindow().setStatusBarColor(color);
            View mChildView = ((ViewGroup) activity.getWindow().findViewById(16908290)).getChildAt(0);
            if (mChildView != null) {
                mChildView.setFitsSystemWindows(true);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

    public static void setStatusBarLightForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, int statusBarColor) {
        if (VERSION.SDK_INT >= 21) {
            EyesLollipop.setStatusBarWhiteForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusBarColor);
        } else if (VERSION.SDK_INT >= 19) {
            EyesKitKat.setStatusBarWhiteForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusBarColor);
        }
    }

    static boolean MIUISetStatusBarLightMode(Activity activity, boolean darkmode) {
        int i = 0;
        Window window = activity.getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.getDecorView().setSystemUiVisibility(8192);
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int darkModeFlag = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", new Class[]{Integer.TYPE, Integer.TYPE});
            Window window2 = activity.getWindow();
            Object[] objArr = new Object[2];
            if (darkmode) {
                i = darkModeFlag;
            }
            objArr[0] = Integer.valueOf(i);
            objArr[1] = Integer.valueOf(darkModeFlag);
            extraFlagField.invoke(window2, objArr);
            return true;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }

    static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
        int value;
        try {
            LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value2 = meizuFlags.getInt(lp);
            if (darkmode) {
                value = value2 | bit;
            } else {
                value = value2 & (bit ^ -1);
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            return true;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }

    static void setContentTopPadding(Activity activity, int padding) {
        ((ViewGroup) activity.getWindow().findViewById(16908290)).setPadding(0, padding, 0, 0);
    }

    static int getPxFromDp(Context context, float dp) {
        return (int) TypedValue.applyDimension(1, dp, context.getResources().getDisplayMetrics());
    }
}
