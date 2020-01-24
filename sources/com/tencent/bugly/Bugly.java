package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.proguard.ad;
import com.tencent.bugly.proguard.ae;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import java.util.Map;

/* compiled from: BUGLY */
public class Bugly {
    private static boolean a;
    public static Context applicationContext = null;
    public static boolean enable = true;

    public static void init(Context context, String appId, boolean isDebug) {
        init(context, appId, isDebug, null);
    }

    public static synchronized void init(Context context, String appId, boolean isDebug, BuglyStrategy strategy) {
        synchronized (Bugly.class) {
            if (!a) {
                a = true;
                applicationContext = ap.a(context);
                if (applicationContext == null) {
                    Log.e(an.b, "init arg 'context' should not be null!");
                } else {
                    b.a((a) CrashModule.getInstance());
                    b.a((a) Beta.getInstance());
                    b.a = enable;
                    b.a(applicationContext, appId, isDebug, strategy);
                }
            }
        }
    }

    public static synchronized String getAppChannel() {
        String str = null;
        synchronized (Bugly.class) {
            a b = a.b();
            if (b != null) {
                if (TextUtils.isEmpty(b.q)) {
                    ae a2 = ae.a();
                    if (a2 == null) {
                        str = b.q;
                    } else {
                        Map a3 = a2.a(556, (ad) null, true);
                        if (a3 != null) {
                            byte[] bArr = (byte[]) a3.get("app_channel");
                            if (bArr != null) {
                                str = new String(bArr);
                            }
                        }
                    }
                }
                str = b.q;
            }
        }
        return str;
    }

    public static void setIsDevelopmentDevice(Context context, boolean isDevelopmentDevice) {
        CrashReport.setIsDevelopmentDevice(context, isDevelopmentDevice);
    }

    public static void setAppChannel(Context context, String channel) {
        CrashReport.setAppChannel(context, channel);
    }

    public static void setUserId(Context context, String userId) {
        CrashReport.setUserId(context, userId);
    }

    public static void setUserTag(Context context, int tag) {
        CrashReport.setUserSceneTag(context, tag);
    }

    public static void putUserData(Context context, String key, String value) {
        CrashReport.putUserData(context, key, value);
    }
}
