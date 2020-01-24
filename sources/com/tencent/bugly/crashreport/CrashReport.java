package com.tencent.bugly.crashreport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.BuglyStrategy.a;
import com.tencent.bugly.CrashModule;
import com.tencent.bugly.b;
import com.tencent.bugly.crashreport.crash.BuglyBroadcastReceiver;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.crashreport.crash.d;
import com.tencent.bugly.crashreport.crash.h5.H5JavaScriptInterface;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.af;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: BUGLY */
public class CrashReport {
    private static Context a;

    /* compiled from: BUGLY */
    public static class CrashHandleCallback extends a {
    }

    /* compiled from: BUGLY */
    public static class UserStrategy extends BuglyStrategy {
        CrashHandleCallback a;

        public UserStrategy(Context context) {
        }

        public synchronized CrashHandleCallback getCrashHandleCallback() {
            return this.a;
        }

        public synchronized void setCrashHandleCallback(CrashHandleCallback crashHandleCallback) {
            this.a = crashHandleCallback;
        }
    }

    /* compiled from: BUGLY */
    public interface WebViewInterface {
        void addJavascriptInterface(H5JavaScriptInterface h5JavaScriptInterface, String str);

        CharSequence getContentDescription();

        String getUrl();

        void loadUrl(String str);

        void setJavaScriptEnabled(boolean z);
    }

    public static void enableBugly(boolean enableBugly) {
        b.a = enableBugly;
    }

    public static void initCrashReport(Context appContext) {
        a = appContext;
        b.a((com.tencent.bugly.a) CrashModule.getInstance());
        b.a(appContext);
    }

    public static void initCrashReport(Context appContext, UserStrategy userStrategy) {
        a = appContext;
        b.a((com.tencent.bugly.a) CrashModule.getInstance());
        b.a(appContext, (BuglyStrategy) userStrategy);
    }

    public static void initCrashReport(Context appContext, String crashReportAppId, boolean isDebug) {
        initCrashReport(appContext, crashReportAppId, isDebug, null);
    }

    public static void initCrashReport(Context appContext, String appId, boolean isDebug, UserStrategy userStrategy) {
        if (appContext != null) {
            a = appContext;
            b.a((com.tencent.bugly.a) CrashModule.getInstance());
            b.a(appContext, appId, isDebug, userStrategy);
        }
    }

    public static String getBuglyVersion(Context context) {
        if (context != null) {
            return com.tencent.bugly.crashreport.common.info.a.a(context).c();
        }
        an.d("Please call with context.", new Object[0]);
        return "unknown";
    }

    public static void testJavaCrash() {
        if (!b.a) {
            Log.w(an.b, "Can not test Java crash because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            com.tencent.bugly.crashreport.common.info.a b = com.tencent.bugly.crashreport.common.info.a.b();
            if (b != null) {
                b.b(24096);
            }
            throw new RuntimeException("This Crash create for Test! You can go to Bugly see more detail!");
        }
    }

    public static void testNativeCrash() {
        testNativeCrash(false, false, false);
    }

    public static void testNativeCrash(boolean testSubThread, boolean testSigabrt, boolean testPendingException) {
        if (!b.a) {
            Log.w(an.b, "Can not test native crash because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            an.a("start to create a native crash for test!", new Object[0]);
            c.a().a(testSubThread, testSigabrt, testPendingException);
        }
    }

    public static void testANRCrash() {
        if (!b.a) {
            Log.w(an.b, "Can not test ANR crash because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            an.a("start to create a anr crash for test!", new Object[0]);
            c.a().k();
        }
    }

    public static void postException(Thread thread, int category, String errorType, String errorMsg, String stack, Map<String, String> extraInfo) {
        if (!b.a) {
            Log.w(an.b, "Can not post crash caught because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            d.a(thread, category, errorType, errorMsg, stack, extraInfo);
        }
    }

    public static void postException(int category, String errorType, String errorMsg, String stack, Map<String, String> extraInfo) {
        postException(Thread.currentThread(), category, errorType, errorMsg, stack, extraInfo);
    }

    public static void postCatchedException(Throwable catchedThrowable) {
        postCatchedException(catchedThrowable, Thread.currentThread());
    }

    public static void postCatchedException(Throwable catchedThrowable, Thread thread) {
        postCatchedException(catchedThrowable, thread, false);
    }

    public static void postCatchedException(Throwable catchedThrowable, Thread thread, boolean clearUserData) {
        if (!b.a) {
            Log.w(an.b, "Can not post crash caught because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else if (catchedThrowable == null) {
            an.d("throwable is null, just return", new Object[0]);
        } else {
            if (thread == null) {
                thread = Thread.currentThread();
            }
            c.a().a(thread, catchedThrowable, false, (String) null, (byte[]) null, clearUserData);
        }
    }

    public static void closeNativeReport() {
        if (!b.a) {
            Log.w(an.b, "Can not close native report because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            c.a().g();
        }
    }

    public static void startCrashReport() {
        if (!b.a) {
            Log.w(an.b, "Can not start crash report because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.w(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            c.a().c();
        }
    }

    public static void closeCrashReport() {
        if (!b.a) {
            Log.w(an.b, "Can not close crash report because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.w(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            c.a().d();
        }
    }

    public static void closeBugly() {
        if (!b.a) {
            Log.w(an.b, "Can not close bugly because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.w(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else if (a != null) {
            BuglyBroadcastReceiver instance = BuglyBroadcastReceiver.getInstance();
            if (instance != null) {
                instance.unregister(a);
            }
            closeCrashReport();
            com.tencent.bugly.crashreport.biz.b.a(a);
            am a2 = am.a();
            if (a2 != null) {
                a2.b();
            }
        }
    }

    public static void setUserSceneTag(Context context, int tagId) {
        if (!b.a) {
            Log.w(an.b, "Can not set tag caught because bugly is disable.");
        } else if (context == null) {
            Log.e(an.b, "setTag args context should not be null");
        } else {
            if (tagId <= 0) {
                an.d("setTag args tagId should > 0", new Object[0]);
            }
            com.tencent.bugly.crashreport.common.info.a.a(context).a(tagId);
            an.b("[param] set user scene tag: %d", Integer.valueOf(tagId));
        }
    }

    public static int getUserSceneTagId(Context context) {
        if (!b.a) {
            Log.w(an.b, "Can not get user scene tag because bugly is disable.");
            return -1;
        } else if (context != null) {
            return com.tencent.bugly.crashreport.common.info.a.a(context).H();
        } else {
            Log.e(an.b, "getUserSceneTagId args context should not be null");
            return -1;
        }
    }

    public static String getUserData(Context context, String key) {
        if (!b.a) {
            Log.w(an.b, "Can not get user data because bugly is disable.");
            return "unknown";
        } else if (context == null) {
            Log.e(an.b, "getUserDataValue args context should not be null");
            return "unknown";
        } else if (ap.a(key)) {
            return null;
        } else {
            return com.tencent.bugly.crashreport.common.info.a.a(context).g(key);
        }
    }

    public static void putUserData(Context context, String key, String value) {
        if (!b.a) {
            Log.w(an.b, "Can not put user data because bugly is disable.");
        } else if (context == null) {
            Log.w(an.b, "putUserData args context should not be null");
        } else if (key == null) {
            String key2 = "" + key;
            an.d("putUserData args key should not be null or empty", new Object[0]);
        } else if (value == null) {
            String value2 = "" + value;
            an.d("putUserData args value should not be null", new Object[0]);
        } else if (!key.matches("[a-zA-Z[0-9]]+")) {
            an.d("putUserData args key should match [a-zA-Z[0-9]]+  {" + key + "}", new Object[0]);
        } else {
            if (value.length() > 200) {
                an.d("user data value length over limit %d, it will be cutted!", Integer.valueOf(200));
                value = value.substring(0, 200);
            }
            com.tencent.bugly.crashreport.common.info.a a2 = com.tencent.bugly.crashreport.common.info.a.a(context);
            if (a2.E().contains(key)) {
                NativeCrashHandler instance = NativeCrashHandler.getInstance();
                if (instance != null) {
                    instance.putKeyValueToNative(key, value);
                }
                com.tencent.bugly.crashreport.common.info.a.a(context).b(key, value);
                an.c("replace KV %s %s", key, value);
            } else if (a2.D() >= 10) {
                an.d("user data size is over limit %d, it will be cutted!", Integer.valueOf(10));
            } else {
                if (key.length() > 50) {
                    an.d("user data key length over limit %d , will drop this new key %s", Integer.valueOf(50), key);
                    key = key.substring(0, 50);
                }
                NativeCrashHandler instance2 = NativeCrashHandler.getInstance();
                if (instance2 != null) {
                    instance2.putKeyValueToNative(key, value);
                }
                com.tencent.bugly.crashreport.common.info.a.a(context).b(key, value);
                an.b("[param] set user data: %s - %s", key, value);
            }
        }
    }

    public static String removeUserData(Context context, String dataKey) {
        if (!b.a) {
            Log.w(an.b, "Can not remove user data because bugly is disable.");
            return "unknown";
        } else if (context == null) {
            Log.e(an.b, "removeUserData args context should not be null");
            return "unknown";
        } else if (ap.a(dataKey)) {
            return null;
        } else {
            an.b("[param] remove user data: %s", dataKey);
            return com.tencent.bugly.crashreport.common.info.a.a(context).f(dataKey);
        }
    }

    public static Set<String> getAllUserDataKeys(Context context) {
        if (!b.a) {
            Log.w(an.b, "Can not get all keys of user data because bugly is disable.");
            return new HashSet();
        } else if (context != null) {
            return com.tencent.bugly.crashreport.common.info.a.a(context).E();
        } else {
            Log.e(an.b, "getAllUserDataKeys args context should not be null");
            return new HashSet();
        }
    }

    public static int getUserDatasSize(Context context) {
        if (!b.a) {
            Log.w(an.b, "Can not get size of user data because bugly is disable.");
            return -1;
        } else if (context != null) {
            return com.tencent.bugly.crashreport.common.info.a.a(context).D();
        } else {
            Log.e(an.b, "getUserDatasSize args context should not be null");
            return -1;
        }
    }

    public static String getAppID() {
        if (!b.a) {
            Log.w(an.b, "Can not get App ID because bugly is disable.");
            return "unknown";
        } else if (CrashModule.hasInitialized()) {
            return com.tencent.bugly.crashreport.common.info.a.a(a).f();
        } else {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return "unknown";
        }
    }

    public static void setUserId(String userId) {
        if (!b.a) {
            Log.w(an.b, "Can not set user ID because bugly is disable.");
        } else if (!CrashModule.hasInitialized()) {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            setUserId(a, userId);
        }
    }

    public static void setUserId(Context context, String userId) {
        if (!b.a) {
            Log.w(an.b, "Can not set user ID because bugly is disable.");
        } else if (context == null) {
            Log.e(an.b, "Context should not be null when bugly has not been initialed!");
        } else if (userId == null) {
            an.d("userId should not be null", new Object[0]);
        } else {
            if (userId.length() > 100) {
                String userId2 = userId.substring(0, 100);
                an.d("userId %s length is over limit %d substring to %s", userId, Integer.valueOf(100), userId2);
                userId = userId2;
            }
            if (!userId.equals(com.tencent.bugly.crashreport.common.info.a.a(context).g())) {
                com.tencent.bugly.crashreport.common.info.a.a(context).b(userId);
                an.b("[user] set userId : %s", userId);
                NativeCrashHandler instance = NativeCrashHandler.getInstance();
                if (instance != null) {
                    instance.setNativeUserId(userId);
                }
                if (CrashModule.hasInitialized()) {
                    com.tencent.bugly.crashreport.biz.b.a();
                }
            }
        }
    }

    public static String getUserId() {
        if (!b.a) {
            Log.w(an.b, "Can not get user ID because bugly is disable.");
            return "unknown";
        } else if (CrashModule.hasInitialized()) {
            return com.tencent.bugly.crashreport.common.info.a.a(a).g();
        } else {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return "unknown";
        }
    }

    public static String getAppVer() {
        if (!b.a) {
            Log.w(an.b, "Can not get app version because bugly is disable.");
            return "unknown";
        } else if (CrashModule.hasInitialized()) {
            return com.tencent.bugly.crashreport.common.info.a.a(a).o;
        } else {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return "unknown";
        }
    }

    public static String getAppChannel() {
        if (!b.a) {
            Log.w(an.b, "Can not get App channel because bugly is disable.");
            return "unknown";
        } else if (CrashModule.hasInitialized()) {
            return com.tencent.bugly.crashreport.common.info.a.a(a).q;
        } else {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return "unknown";
        }
    }

    public static void setContext(Context context) {
        a = context;
    }

    public static boolean isLastSessionCrash() {
        if (!b.a) {
            Log.w(an.b, "The info 'isLastSessionCrash' is not accurate because bugly is disable.");
            return false;
        } else if (CrashModule.hasInitialized()) {
            return c.a().b();
        } else {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return false;
        }
    }

    public static void setSdkExtraData(Context context, String sdkId, String version) {
        if (!b.a) {
            Log.w(an.b, "Can not put SDK extra data because bugly is disable.");
        } else if (context != null && !ap.a(sdkId) && !ap.a(version)) {
            com.tencent.bugly.crashreport.common.info.a.a(context).a(sdkId, version);
        }
    }

    public static Map<String, String> getSdkExtraData() {
        if (!b.a) {
            Log.w(an.b, "Can not get SDK extra data because bugly is disable.");
            return new HashMap();
        } else if (CrashModule.hasInitialized()) {
            return com.tencent.bugly.crashreport.common.info.a.a(a).H;
        } else {
            Log.e(an.b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return null;
        }
    }

    public static Map<String, String> getSdkExtraData(Context context) {
        if (!b.a) {
            Log.w(an.b, "Can not get SDK extra data because bugly is disable.");
            return new HashMap();
        } else if (context != null) {
            return com.tencent.bugly.crashreport.common.info.a.a(context).H;
        } else {
            an.d("Context should not be null.", new Object[0]);
            return null;
        }
    }

    private static void putSdkData(Context context, String key, String value) {
        if (context != null && !ap.a(key) && !ap.a(value)) {
            String key2 = key.replace("[a-zA-Z[0-9]]+", "");
            if (key2.length() > 100) {
                Log.w(an.b, String.format("putSdkData key length over limit %d, will be cutted.", new Object[]{Integer.valueOf(50)}));
                key2 = key2.substring(0, 50);
            }
            if (value.length() > 500) {
                Log.w(an.b, String.format("putSdkData value length over limit %d, will be cutted!", new Object[]{Integer.valueOf(200)}));
                value = value.substring(0, 200);
            }
            com.tencent.bugly.crashreport.common.info.a.a(context).c(key2, value);
            an.b(String.format("[param] putSdkData data: %s - %s", new Object[]{key2, value}), new Object[0]);
        }
    }

    public static void setIsAppForeground(Context context, boolean isAppForeground) {
        if (!b.a) {
            Log.w(an.b, "Can not set 'isAppForeground' because bugly is disable.");
        } else if (context == null) {
            an.d("Context should not be null.", new Object[0]);
        } else {
            if (isAppForeground) {
                an.c("App is in foreground.", new Object[0]);
            } else {
                an.c("App is in background.", new Object[0]);
            }
            com.tencent.bugly.crashreport.common.info.a.a(context).a(isAppForeground);
        }
    }

    public static void setIsDevelopmentDevice(Context context, boolean isDevelopmentDevice) {
        if (!b.a) {
            Log.w(an.b, "Can not set 'isDevelopmentDevice' because bugly is disable.");
        } else if (context == null) {
            an.d("Context should not be null.", new Object[0]);
        } else {
            if (isDevelopmentDevice) {
                an.c("This is a development device.", new Object[0]);
            } else {
                an.c("This is not a development device.", new Object[0]);
            }
            com.tencent.bugly.crashreport.common.info.a.a(context).D = isDevelopmentDevice;
        }
    }

    public static void setSessionIntervalMills(long sessionIntervalMills) {
        if (!b.a) {
            Log.w(an.b, "Can not set 'SessionIntervalMills' because bugly is disable.");
        } else {
            com.tencent.bugly.crashreport.biz.b.a(sessionIntervalMills);
        }
    }

    public static void setAppVersion(Context context, String appVersion) {
        if (!b.a) {
            Log.w(an.b, "Can not set App version because bugly is disable.");
        } else if (context == null) {
            Log.w(an.b, "setAppVersion args context should not be null");
        } else if (appVersion == null) {
            Log.w(an.b, "App version is null, will not set");
        } else {
            com.tencent.bugly.crashreport.common.info.a.a(context).o = appVersion;
            NativeCrashHandler instance = NativeCrashHandler.getInstance();
            if (instance != null) {
                instance.setNativeAppVersion(appVersion);
            }
        }
    }

    public static void setAppChannel(Context context, String appChannel) {
        if (!b.a) {
            Log.w(an.b, "Can not set App channel because Bugly is disable.");
        } else if (context == null) {
            Log.w(an.b, "setAppChannel args context should not be null");
        } else if (appChannel == null) {
            Log.w(an.b, "App channel is null, will not set");
        } else {
            com.tencent.bugly.crashreport.common.info.a.a(context).q = appChannel;
            NativeCrashHandler instance = NativeCrashHandler.getInstance();
            if (instance != null) {
                instance.setNativeAppChannel(appChannel);
            }
        }
    }

    public static void setAppPackage(Context context, String appPackage) {
        if (!b.a) {
            Log.w(an.b, "Can not set App package because bugly is disable.");
        } else if (context == null) {
            Log.w(an.b, "setAppPackage args context should not be null");
        } else if (appPackage == null) {
            Log.w(an.b, "App package is null, will not set");
        } else {
            com.tencent.bugly.crashreport.common.info.a.a(context).d = appPackage;
            NativeCrashHandler instance = NativeCrashHandler.getInstance();
            if (instance != null) {
                instance.setNativeAppPackage(appPackage);
            }
        }
    }

    public static void setCrashFilter(String filter) {
        if (!b.a) {
            Log.w(an.b, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(an.b, "Set crash stack filter: " + filter);
        c.n = filter;
    }

    public static void setCrashRegularFilter(String filter) {
        if (!b.a) {
            Log.w(an.b, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(an.b, "Set crash stack filter: " + filter);
        c.o = filter;
    }

    public static void setHandleNativeCrashInJava(boolean handleNativeCrashInJava) {
        if (!b.a) {
            Log.w(an.b, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(an.b, "Should handle native crash in Java profile after handled in native profile: " + handleNativeCrashInJava);
        NativeCrashHandler.setShouldHandleInJava(handleNativeCrashInJava);
    }

    public static void setBuglyDbName(String dbName) {
        if (!b.a) {
            Log.w(an.b, "Can not set DB name because bugly is disable.");
            return;
        }
        Log.i(an.b, "Set Bugly DB name: " + dbName);
        af.a = dbName;
    }

    public static void enableObtainId(Context context, boolean enable) {
        if (!b.a) {
            Log.w(an.b, "Can not set DB name because bugly is disable.");
        } else if (context == null) {
            Log.w(an.b, "enableObtainId args context should not be null");
        } else {
            Log.i(an.b, "Enable identification obtaining? " + enable);
            com.tencent.bugly.crashreport.common.info.a.a(context).b(enable);
        }
    }

    public static void setAuditEnable(Context context, boolean enable) {
        if (!b.a) {
            Log.w(an.b, "Can not set App package because bugly is disable.");
        } else if (context == null) {
            Log.w(an.b, "setAppPackage args context should not be null");
        } else {
            Log.i(an.b, "Set audit enable: " + enable);
            com.tencent.bugly.crashreport.common.info.a.a(context).I = enable;
        }
    }

    public static boolean setJavascriptMonitor(WebView webView, boolean autoInject) {
        return setJavascriptMonitor(webView, autoInject, false);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public static boolean setJavascriptMonitor(final WebView webView, boolean autoInject, boolean force) {
        if (webView != null) {
            return setJavascriptMonitor((WebViewInterface) new WebViewInterface() {
                public String getUrl() {
                    return webView.getUrl();
                }

                public void setJavaScriptEnabled(boolean flag) {
                    WebSettings settings = webView.getSettings();
                    if (!settings.getJavaScriptEnabled()) {
                        settings.setJavaScriptEnabled(true);
                    }
                }

                public void loadUrl(String url) {
                    webView.loadUrl(url);
                }

                public void addJavascriptInterface(H5JavaScriptInterface jsInterface, String name) {
                    webView.addJavascriptInterface(jsInterface, name);
                }

                public CharSequence getContentDescription() {
                    return webView.getContentDescription();
                }
            }, autoInject, force);
        }
        Log.w(an.b, "WebView is null.");
        return false;
    }

    public static boolean setJavascriptMonitor(WebViewInterface webView, boolean autoInject) {
        return setJavascriptMonitor(webView, autoInject, false);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public static boolean setJavascriptMonitor(WebViewInterface webView, boolean autoInject, boolean force) {
        if (webView == null) {
            Log.w(an.b, "WebViewInterface is null.");
            return false;
        } else if (!CrashModule.hasInitialized()) {
            an.e("CrashReport has not been initialed! please to call method 'initCrashReport' first!", new Object[0]);
            return false;
        } else {
            an.a("Set Javascript exception monitor of webview.", new Object[0]);
            if (!b.a) {
                Log.w(an.b, "Can not set JavaScript monitor because bugly is disable.");
                return false;
            }
            an.c("URL of webview is %s", webView.getUrl());
            if (force || VERSION.SDK_INT >= 19) {
                an.a("Enable the javascript needed by webview monitor.", new Object[0]);
                webView.setJavaScriptEnabled(true);
                H5JavaScriptInterface instance = H5JavaScriptInterface.getInstance(webView);
                if (instance != null) {
                    an.a("Add a secure javascript interface to the webview.", new Object[0]);
                    webView.addJavascriptInterface(instance, "exceptionUploader");
                }
                if (autoInject) {
                    an.a("Inject bugly.js(v%s) to the webview.", com.tencent.bugly.crashreport.crash.h5.b.b());
                    String a2 = com.tencent.bugly.crashreport.crash.h5.b.a();
                    if (a2 == null) {
                        an.e("Failed to inject Bugly.js.", com.tencent.bugly.crashreport.crash.h5.b.b());
                        return false;
                    }
                    webView.loadUrl("javascript:" + a2);
                }
                return true;
            }
            an.e("This interface is only available for Android 4.4 or later.", new Object[0]);
            return false;
        }
    }
}
