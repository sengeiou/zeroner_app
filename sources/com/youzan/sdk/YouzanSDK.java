package com.youzan.sdk;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.youzan.sdk.tool.Preference;
import com.youzan.sdk.tool.f;
import com.youzan.sdk.tool.i;

public final class YouzanSDK {
    public static boolean READY = false;

    public static void init(Context context, String clientId) {
        init(context, clientId, true);
    }

    public static void init(Context context, String clientId, boolean autoPrefix) {
        try {
            READY = true;
            f.m119(context, clientId, autoPrefix);
            Preference.instance().init(context);
        } catch (Throwable e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static void isDebug(boolean debug2) {
        YouzanLog.m16(debug2);
        i.m131(debug2);
    }

    public static void userLogout(Context context) {
        f.m117(context);
    }

    public static void sync(Context context, YouzanToken token) {
        Utils.sync(context, token);
    }
}
