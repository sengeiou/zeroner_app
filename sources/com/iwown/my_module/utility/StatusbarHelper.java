package com.iwown.my_module.utility;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.view.Window;

public class StatusbarHelper {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    public static void hideStatusBar(Activity activity) {
        if (hasKitKat() && !hasLollipop()) {
            activity.getWindow().addFlags(67108864);
        } else if (hasLollipop()) {
            Window window = activity.getWindow();
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
}
