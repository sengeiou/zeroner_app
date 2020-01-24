package com.tencent.bugly.crashreport.inner;

import com.tencent.bugly.crashreport.crash.d;
import com.tencent.bugly.proguard.an;
import java.util.Map;

/* compiled from: BUGLY */
public class InnerApi {
    public static void postU3dCrashAsync(String errorType, String errorMsg, String errorStack) {
        if (errorType == null || errorMsg == null || errorStack == null) {
            an.e("post u3d fail args null", new Object[0]);
        }
        an.a("post u3d crash %s %s", errorType, errorMsg);
        d.a(Thread.currentThread(), 4, errorType, errorMsg, errorStack, null);
    }

    public static void postCocos2dxCrashAsync(int category, String errorType, String errorMsg, String errorStack) {
        if (errorType == null || errorMsg == null || errorStack == null) {
            an.e("post cocos2d-x fail args null", new Object[0]);
        } else if (category == 5 || category == 6) {
            an.a("post cocos2d-x crash %s %s", errorType, errorMsg);
            d.a(Thread.currentThread(), category, errorType, errorMsg, errorStack, null);
        } else {
            an.e("post cocos2d-x fail category illeagle: %d", Integer.valueOf(category));
        }
    }

    public static void postH5CrashAsync(Thread thread, String errorType, String errorMsg, String errorStack, Map<String, String> h5ExtraInfos) {
        if (errorType == null || errorMsg == null || errorStack == null) {
            an.e("post h5 fail args null", new Object[0]);
            return;
        }
        an.a("post h5 crash %s %s", errorType, errorMsg);
        d.a(thread, 8, errorType, errorMsg, errorStack, h5ExtraInfos);
    }
}
