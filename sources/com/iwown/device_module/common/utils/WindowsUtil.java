package com.iwown.device_module.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.iwown.my_module.utility.Constants;

public class WindowsUtil {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    public static void setTopWindows(Window window) {
        if (hasKitKat() && !hasLollipop()) {
            window.addFlags(67108864);
        } else if (hasLollipop()) {
            window.clearFlags(201326592);
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
        }
    }

    public static boolean hasKitKat() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean hasLollipop() {
        return VERSION.SDK_INT >= 21;
    }

    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int resourceId = res.getIdentifier(key, "dimen", Constants.APPSYSTEM);
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static void isWindowToDark(Activity activity, boolean isToDark) {
        LayoutParams windowLP = activity.getWindow().getAttributes();
        if (isToDark) {
            windowLP.alpha = 0.7f;
        } else {
            windowLP.alpha = 0.0f;
        }
        activity.getWindow().setAttributes(windowLP);
    }
}
