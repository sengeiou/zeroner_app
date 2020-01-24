package com.iwown.lib_common.toast;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;
import com.iwown.my_module.utility.Constants;

public class CustomToast extends Toast {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static boolean isOk;

    public CustomToast(Context context) {
        super(context);
    }

    public static void makeText(Activity activity, CharSequence text, boolean isOks) {
        isOk = isOks;
        makeText(activity, text);
    }

    public static void makeText(Activity activity, CharSequence text) {
        ToastUtils.showShortToast(text);
    }

    public static void setStatusBarVisibility(Window window, boolean enable) {
        LayoutParams lp = window.getAttributes();
        if (enable) {
            lp.flags |= 2048;
        } else {
            lp.flags &= -2049;
        }
        window.setAttributes(lp);
        window.clearFlags(512);
    }

    public static void setFullScreen(Window window, boolean enable) {
        LayoutParams lp = window.getAttributes();
        if (enable) {
            lp.flags |= 1024;
        } else {
            lp.flags &= -1025;
        }
        window.setAttributes(lp);
        window.clearFlags(512);
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

    public static boolean hasKitKat() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean hasLollipop() {
        return VERSION.SDK_INT >= 21;
    }
}
