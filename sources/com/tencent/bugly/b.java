package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.proguard.ac;
import com.tencent.bugly.proguard.ad;
import com.tencent.bugly.proguard.ae;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.ap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public class b {
    public static boolean a = true;
    public static List<a> b = new ArrayList();
    public static boolean c;
    private static ae d;
    private static boolean e;

    private static boolean a(a aVar) {
        String str;
        List<String> list = aVar.t;
        aVar.getClass();
        if ("".equals("")) {
            str = "bugly";
        } else {
            aVar.getClass();
            str = "";
        }
        if (list == null || !list.contains(str)) {
            return false;
        }
        return true;
    }

    public static synchronized void a(Context context) {
        synchronized (b.class) {
            a(context, (BuglyStrategy) null);
        }
    }

    public static synchronized void a(Context context, BuglyStrategy buglyStrategy) {
        synchronized (b.class) {
            if (e) {
                an.d("[init] initial Multi-times, ignore this.", new Object[0]);
            } else if (context == null) {
                Log.w(an.b, "[init] context of init() is null, check it.");
            } else {
                a a2 = a.a(context);
                if (a(a2)) {
                    a = false;
                } else {
                    String f = a2.f();
                    if (f == null) {
                        Log.e(an.b, "[init] meta data of BUGLY_APPID in AndroidManifest.xml should be set.");
                    } else {
                        a(context, f, a2.z, buglyStrategy);
                    }
                }
            }
        }
    }

    public static synchronized void a(Context context, String str, boolean z, BuglyStrategy buglyStrategy) {
        int i = 0;
        synchronized (b.class) {
            if (e) {
                an.d("[init] initial Multi-times, ignore this.", new Object[0]);
            } else if (context == null) {
                Log.w(an.b, "[init] context is null, check it.");
            } else if (str == null) {
                Log.e(an.b, "init arg 'crashReportAppID' should not be null!");
            } else {
                e = true;
                if (z) {
                    c = true;
                    an.c = true;
                    an.d("Bugly debug模式开启，请在发布时把isDebug关闭。 -- Running in debug model for 'isDebug' is enabled. Please disable it when you release.", new Object[0]);
                    an.e("--------------------------------------------------------------------------------------------", new Object[0]);
                    an.d("Bugly debug模式将有以下行为特性 -- The following list shows the behaviour of debug model: ", new Object[0]);
                    an.d("[1] 输出详细的Bugly SDK的Log -- More detailed log of Bugly SDK will be output to logcat;", new Object[0]);
                    an.d("[2] 每一条Crash都会被立即上报 -- Every crash caught by Bugly will be uploaded immediately.", new Object[0]);
                    an.d("[3] 自定义日志将会在Logcat中输出 -- Custom log will be output to logcat.", new Object[0]);
                    an.e("--------------------------------------------------------------------------------------------", new Object[0]);
                    an.b("[init] Open debug mode of Bugly.", new Object[0]);
                }
                an.a("[init] Bugly version: v%s", "2.6.5");
                an.a(" crash report start initializing...", new Object[0]);
                an.b("[init] Bugly start initializing...", new Object[0]);
                an.a("[init] Bugly complete version: v%s", "2.6.5(1.3.5)");
                Context a2 = ap.a(context);
                a a3 = a.a(a2);
                a3.t();
                ao.a(a2);
                d = ae.a(a2, b);
                ak.a(a2);
                com.tencent.bugly.crashreport.common.strategy.a a4 = com.tencent.bugly.crashreport.common.strategy.a.a(a2, b);
                ac a5 = ac.a(a2);
                if (a(a3)) {
                    a = false;
                } else {
                    a3.a(str);
                    an.a("[param] Set APP ID:%s", str);
                    a(buglyStrategy, a3);
                    com.tencent.bugly.crashreport.biz.b.a(a2, buglyStrategy);
                    while (true) {
                        int i2 = i;
                        if (i2 < b.size()) {
                            try {
                                if (a5.a(((a) b.get(i2)).id)) {
                                    ((a) b.get(i2)).init(a2, z, buglyStrategy);
                                }
                            } catch (Throwable th) {
                                if (!an.a(th)) {
                                    ThrowableExtension.printStackTrace(th);
                                }
                            }
                            i = i2 + 1;
                        } else {
                            a4.a(buglyStrategy != null ? buglyStrategy.getAppReportDelay() : 0);
                            an.b("[init] Bugly initialization finished.", new Object[0]);
                        }
                    }
                }
            }
        }
    }

    private static void a(BuglyStrategy buglyStrategy, a aVar) {
        String str;
        String str2;
        String str3;
        String str4;
        if (buglyStrategy != null) {
            String appVersion = buglyStrategy.getAppVersion();
            if (!TextUtils.isEmpty(appVersion)) {
                if (appVersion.length() > 100) {
                    str4 = appVersion.substring(0, 100);
                    an.d("appVersion %s length is over limit %d substring to %s", appVersion, Integer.valueOf(100), str4);
                } else {
                    str4 = appVersion;
                }
                aVar.o = str4;
                an.a("[param] Set App version: %s", buglyStrategy.getAppVersion());
            }
            try {
                if (buglyStrategy.isReplaceOldChannel()) {
                    String appChannel = buglyStrategy.getAppChannel();
                    if (!TextUtils.isEmpty(appChannel)) {
                        if (appChannel.length() > 100) {
                            String substring = appChannel.substring(0, 100);
                            an.d("appChannel %s length is over limit %d substring to %s", appChannel, Integer.valueOf(100), substring);
                            str3 = substring;
                        } else {
                            str3 = appChannel;
                        }
                        d.a(556, "app_channel", str3.getBytes(), (ad) null, false);
                        aVar.q = str3;
                    }
                } else {
                    Map a2 = d.a(556, (ad) null, true);
                    if (a2 != null) {
                        byte[] bArr = (byte[]) a2.get("app_channel");
                        if (bArr != null) {
                            aVar.q = new String(bArr);
                        }
                    }
                }
                an.a("[param] Set App channel: %s", aVar.q);
            } catch (Exception e2) {
                if (c) {
                    ThrowableExtension.printStackTrace(e2);
                }
            }
            String appPackageName = buglyStrategy.getAppPackageName();
            if (!TextUtils.isEmpty(appPackageName)) {
                if (appPackageName.length() > 100) {
                    str2 = appPackageName.substring(0, 100);
                    an.d("appPackageName %s length is over limit %d substring to %s", appPackageName, Integer.valueOf(100), str2);
                } else {
                    str2 = appPackageName;
                }
                aVar.d = str2;
                an.a("[param] Set App package: %s", buglyStrategy.getAppPackageName());
            }
            String deviceID = buglyStrategy.getDeviceID();
            if (deviceID != null) {
                if (deviceID.length() > 100) {
                    str = deviceID.substring(0, 100);
                    an.d("deviceId %s length is over limit %d substring to %s", deviceID, Integer.valueOf(100), str);
                } else {
                    str = deviceID;
                }
                aVar.c(str);
                an.a("[param] Set device ID: %s", str);
            }
            aVar.g = buglyStrategy.isUploadProcess();
            ao.a = buglyStrategy.isBuglyLogUpload();
        }
    }

    public static synchronized void a(a aVar) {
        synchronized (b.class) {
            if (!b.contains(aVar)) {
                b.add(aVar);
            }
        }
    }
}
