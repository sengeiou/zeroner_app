package com.tencent.bugly.crashreport.crash.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import com.alibaba.android.arouter.utils.Consts;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.b;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.ap;
import java.io.File;
import org.apache.commons.cli.HelpFormatter;

/* compiled from: BUGLY */
public class NativeCrashHandler implements a {
    private static NativeCrashHandler a;
    private static boolean l = false;
    private static boolean m = false;
    /* access modifiers changed from: private */
    public static boolean o = true;
    /* access modifiers changed from: private */
    public final Context b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final am d;
    /* access modifiers changed from: private */
    public NativeExceptionHandler e;
    /* access modifiers changed from: private */
    public String f;
    private final boolean g;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    /* access modifiers changed from: private */
    public b n;

    /* access modifiers changed from: protected */
    public native boolean appendNativeLog(String str, String str2, String str3);

    /* access modifiers changed from: protected */
    public native boolean appendWholeNativeLog(String str);

    /* access modifiers changed from: protected */
    public native String getNativeKeyValueList();

    /* access modifiers changed from: protected */
    public native String getNativeLog();

    /* access modifiers changed from: protected */
    public native boolean putNativeKeyValue(String str, String str2);

    /* access modifiers changed from: protected */
    public native String regist(String str, boolean z, int i2);

    /* access modifiers changed from: protected */
    public native String removeNativeKeyValue(String str);

    /* access modifiers changed from: protected */
    public native void setNativeInfo(int i2, String str);

    /* access modifiers changed from: protected */
    public native void testCrash();

    /* access modifiers changed from: protected */
    public native String unregist();

    private static void a(String str) {
        an.c("[Native] Check extra jni for Bugly NDK v%s", str);
        String replace = "2.1.1".replace(Consts.DOT, "");
        String replace2 = "2.3.0".replace(Consts.DOT, "");
        String replace3 = str.replace(Consts.DOT, "");
        if (replace3.length() == 2) {
            replace3 = replace3 + "0";
        } else if (replace3.length() == 1) {
            replace3 = replace3 + "00";
        }
        try {
            if (Integer.parseInt(replace3) >= Integer.parseInt(replace)) {
                l = true;
            }
            if (Integer.parseInt(replace3) >= Integer.parseInt(replace2)) {
                m = true;
            }
        } catch (Throwable th) {
        }
        if (m) {
            an.a("[Native] Info setting jni can be accessed.", new Object[0]);
        } else {
            an.d("[Native] Info setting jni can not be accessed.", new Object[0]);
        }
        if (l) {
            an.a("[Native] Extra jni can be accessed.", new Object[0]);
        } else {
            an.d("[Native] Extra jni can not be accessed.", new Object[0]);
        }
    }

    @SuppressLint({"SdCardPath"})
    protected NativeCrashHandler(Context context, com.tencent.bugly.crashreport.common.info.a comInfo, b crashHandler, com.tencent.bugly.crashreport.common.strategy.a strategyManager, am asyncHandler, boolean isDebug, String tombPath) {
        this.b = ap.a(context);
        try {
            if (ap.a(tombPath)) {
                tombPath = context.getDir("bugly", 0).getAbsolutePath();
            }
        } catch (Throwable th) {
            tombPath = "/data/data/" + com.tencent.bugly.crashreport.common.info.a.a(context).d + "/app_bugly";
        }
        this.n = crashHandler;
        this.f = tombPath;
        this.c = comInfo;
        this.d = asyncHandler;
        this.g = isDebug;
        this.e = new a(context, comInfo, crashHandler, com.tencent.bugly.crashreport.common.strategy.a.a());
    }

    public static synchronized NativeCrashHandler getInstance(Context context, com.tencent.bugly.crashreport.common.info.a cominfoManager, b crashHandler, com.tencent.bugly.crashreport.common.strategy.a strategyManager, am asyncHandler, boolean isDebug, String tombPath) {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            if (a == null) {
                a = new NativeCrashHandler(context, cominfoManager, crashHandler, strategyManager, asyncHandler, isDebug, tombPath);
            }
            nativeCrashHandler = a;
        }
        return nativeCrashHandler;
    }

    public static synchronized NativeCrashHandler getInstance() {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            nativeCrashHandler = a;
        }
        return nativeCrashHandler;
    }

    public synchronized String getDumpFilePath() {
        return this.f;
    }

    public synchronized void setDumpFilePath(String dumpFilePath) {
        this.f = dumpFilePath;
    }

    public static void setShouldHandleInJava(boolean shouldHandleInJava) {
        o = shouldHandleInJava;
        if (a != null) {
            a.a(999, "" + shouldHandleInJava);
        }
    }

    public static boolean isShouldHandleInJava() {
        return o;
    }

    /* access modifiers changed from: protected */
    public synchronized void a(boolean z) {
        if (this.j) {
            an.d("[Native] Native crash report has already registered.", new Object[0]);
        } else {
            if (this.i) {
                try {
                    String regist = regist(this.f, z, 1);
                    if (regist != null) {
                        an.a("[Native] Native Crash Report enable.", new Object[0]);
                        a(regist);
                        this.c.s = regist;
                        ao.a(l);
                        this.j = true;
                    }
                } catch (Throwable th) {
                    an.c("[Native] Failed to load Bugly SO file.", new Object[0]);
                }
            } else if (this.h) {
                String str = "com.tencent.feedback.eup.jni.NativeExceptionUpload";
                String str2 = "registNativeExceptionHandler2";
                try {
                    Class[] clsArr = {String.class, String.class, Integer.TYPE, Integer.TYPE};
                    Object[] objArr = new Object[4];
                    objArr[0] = this.f;
                    objArr[1] = com.tencent.bugly.crashreport.common.info.b.a(this.b, false);
                    objArr[2] = Integer.valueOf(z ? 1 : 5);
                    objArr[3] = Integer.valueOf(1);
                    String str3 = (String) ap.a(str, str2, null, clsArr, objArr);
                    if (str3 == null) {
                        str3 = (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler", null, new Class[]{String.class, String.class, Integer.TYPE}, new Object[]{this.f, com.tencent.bugly.crashreport.common.info.b.a(this.b, false), Integer.valueOf(com.tencent.bugly.crashreport.common.info.a.b().K())});
                    }
                    if (str3 != null) {
                        this.j = true;
                        com.tencent.bugly.crashreport.common.info.a.b().s = str3;
                        Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "checkExtraJni", null, new Class[]{String.class}, new Object[]{str3});
                        if (bool != null) {
                            l = bool.booleanValue();
                            ao.a(l);
                        }
                        ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", null, new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(true)});
                        ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "setLogMode", null, new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(z ? 1 : 5)});
                    }
                } catch (Throwable th2) {
                }
            }
            this.i = false;
            this.h = false;
        }
    }

    public synchronized void startNativeMonitor() {
        boolean z;
        if (this.i || this.h) {
            a(this.g);
        } else {
            String str = "Bugly";
            String str2 = "NativeRQD";
            if (!ap.a(this.c.r)) {
                z = true;
            } else {
                z = false;
            }
            if (c.b) {
                this.i = a(z ? this.c.r : str + "-rqd", z);
                if (!this.i && !z) {
                    this.h = a(str2, false);
                }
            } else {
                String str3 = this.c.r;
                if (!z) {
                    this.c.getClass();
                    if ("".length() > 0) {
                        this.c.getClass();
                        if (!"".contains("@")) {
                            StringBuilder append = new StringBuilder().append(str).append(HelpFormatter.DEFAULT_OPT_PREFIX);
                            this.c.getClass();
                            str = append.append("").toString();
                        }
                    }
                } else {
                    str = str3;
                }
                this.i = a(str, z);
            }
            if (this.i || this.h) {
                a(this.g);
                if (l) {
                    setNativeAppVersion(this.c.o);
                    setNativeAppChannel(this.c.q);
                    setNativeAppPackage(this.c.d);
                    setNativeUserId(this.c.g());
                    setNativeIsAppForeground(this.c.a());
                    setNativeLaunchTime(this.c.a);
                }
            }
        }
    }

    public void checkUploadRecordCrash(long delay) {
        this.d.a(new Runnable() {
            public void run() {
                if (!ap.a(NativeCrashHandler.this.b, "native_record_lock", 10000)) {
                    an.a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
                    return;
                }
                if (!NativeCrashHandler.o) {
                    NativeCrashHandler.this.a(999, "false");
                }
                CrashDetailBean a2 = b.a(NativeCrashHandler.this.b, NativeCrashHandler.this.f, NativeCrashHandler.this.e);
                if (a2 != null) {
                    an.a("[Native] Get crash from native record.", new Object[0]);
                    if (!NativeCrashHandler.this.n.a(a2)) {
                        NativeCrashHandler.this.n.a(a2, 3000, false);
                    }
                    b.a(false, NativeCrashHandler.this.f);
                }
                NativeCrashHandler.this.b();
                ap.c(NativeCrashHandler.this.b, "native_record_lock");
            }
        }, delay);
    }

    private boolean a(String str, boolean z) {
        Throwable th;
        boolean z2;
        try {
            an.a("[Native] Trying to load so: %s", str);
            if (z) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
            try {
                an.a("[Native] Successfully loaded SO: %s", str);
                return true;
            } catch (Throwable th2) {
                th = th2;
                z2 = true;
            }
        } catch (Throwable th3) {
            th = th3;
            z2 = false;
        }
        an.d(th.getMessage(), new Object[0]);
        an.d("[Native] Failed to load so: %s", str);
        return z2;
    }

    /* access modifiers changed from: protected */
    public synchronized void a() {
        if (!this.j) {
            an.d("[Native] Native crash report has already unregistered.", new Object[0]);
        } else {
            try {
                if (unregist() != null) {
                    an.a("[Native] Successfully closed native crash report.", new Object[0]);
                    this.j = false;
                }
            } catch (Throwable th) {
                an.c("[Native] Failed to close native crash report.", new Object[0]);
            }
            try {
                ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", null, new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(false)});
                this.j = false;
                an.a("[Native] Successfully closed native crash report.", new Object[0]);
            } catch (Throwable th2) {
                an.c("[Native] Failed to close native crash report.", new Object[0]);
                this.i = false;
                this.h = false;
            }
        }
        return;
    }

    public void testNativeCrash() {
        if (!this.i) {
            an.d("[Native] Bugly SO file has not been load.", new Object[0]);
        } else {
            testCrash();
        }
    }

    public void testNativeCrash(boolean testSubThread, boolean testSigabrt, boolean testPendingException) {
        a(16, "" + testSubThread);
        a(17, "" + testSigabrt);
        a(18, "" + testPendingException);
        testNativeCrash();
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void b() {
        long b2 = ap.b() - c.g;
        long b3 = 86400000 + ap.b();
        File file = new File(this.f);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                int i2 = 0;
                int i3 = 0;
                for (File file2 : listFiles) {
                    String name = file2.getName();
                    int lastIndexOf = name.lastIndexOf("_");
                    if (lastIndexOf > 0) {
                        try {
                            int indexOf = name.indexOf(".txt");
                            if (indexOf > 0) {
                                long parseLong = Long.parseLong(name.substring(lastIndexOf + 1, indexOf));
                                if (parseLong >= b2 && parseLong < b3) {
                                }
                            }
                        } catch (NumberFormatException e2) {
                            an.e("[Native] Name of record file is invalid: %s", name);
                        }
                        an.a("[Native] Delete record file: %s", file2.getAbsolutePath());
                        i3++;
                        if (file2.delete()) {
                            i2++;
                        }
                    }
                }
                an.c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i3), Integer.valueOf(i2));
            }
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void b(boolean z) {
        if (z) {
            startNativeMonitor();
        } else {
            a();
        }
    }

    public synchronized boolean isUserOpened() {
        return this.k;
    }

    private synchronized void c(boolean z) {
        if (this.k != z) {
            an.a("user change native %b", Boolean.valueOf(z));
            this.k = z;
        }
    }

    public synchronized void setUserOpened(boolean isUserOpened) {
        boolean z = true;
        synchronized (this) {
            c(isUserOpened);
            boolean isUserOpened2 = isUserOpened();
            com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
            if (a2 == null) {
                z = isUserOpened2;
            } else if (!isUserOpened2 || !a2.c().g) {
                z = false;
            }
            if (z != this.j) {
                an.a("native changed to %b", Boolean.valueOf(z));
                b(z);
            }
        }
    }

    public synchronized void onStrategyChanged(StrategyBean newStrategy) {
        boolean z = true;
        synchronized (this) {
            if (newStrategy != null) {
                if (newStrategy.g != this.j) {
                    an.d("server native changed to %b", Boolean.valueOf(newStrategy.g));
                }
            }
            if (!com.tencent.bugly.crashreport.common.strategy.a.a().c().g || !this.k) {
                z = false;
            }
            if (z != this.j) {
                an.a("native changed to %b", Boolean.valueOf(z));
                b(z);
            }
        }
    }

    public boolean appendLogToNative(String level, String tag, String log) {
        if ((!this.h && !this.i) || !l || level == null || tag == null || log == null) {
            return false;
        }
        try {
            if (this.i) {
                return appendNativeLog(level, tag, log);
            }
            Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", null, new Class[]{String.class, String.class, String.class}, new Object[]{level, tag, log});
            return bool != null ? bool.booleanValue() : false;
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return false;
        } catch (Throwable th) {
            if (an.a(th)) {
                return false;
            }
            ThrowableExtension.printStackTrace(th);
            return false;
        }
    }

    public String getLogFromNative() {
        if (!this.h && !this.i) {
            return null;
        }
        if (!l) {
            return null;
        }
        try {
            if (this.i) {
                return getNativeLog();
            }
            return (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", null, null, null);
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return null;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    public boolean putKeyValueToNative(String key, String value) {
        if ((!this.h && !this.i) || !l || key == null || value == null) {
            return false;
        }
        try {
            if (this.i) {
                return putNativeKeyValue(key, value);
            }
            Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", null, new Class[]{String.class, String.class}, new Object[]{key, value});
            return bool != null ? bool.booleanValue() : false;
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return false;
        } catch (Throwable th) {
            if (an.a(th)) {
                return false;
            }
            ThrowableExtension.printStackTrace(th);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public boolean a(int i2, String str) {
        if (!this.i || !m) {
            return false;
        }
        try {
            setNativeInfo(i2, str);
            return true;
        } catch (UnsatisfiedLinkError e2) {
            m = false;
            return false;
        } catch (Throwable th) {
            if (an.a(th)) {
                return false;
            }
            ThrowableExtension.printStackTrace(th);
            return false;
        }
    }

    public boolean filterSigabrtSysLog() {
        return a(998, "true");
    }

    public boolean setNativeAppVersion(String appVersion) {
        return a(10, appVersion);
    }

    public boolean setNativeAppChannel(String appChannel) {
        return a(12, appChannel);
    }

    public boolean setNativeAppPackage(String appPackage) {
        return a(13, appPackage);
    }

    public boolean setNativeUserId(String userId) {
        return a(11, userId);
    }

    public boolean setNativeIsAppForeground(boolean isAppForeground) {
        return a(14, isAppForeground ? "true" : "false");
    }

    public boolean setNativeLaunchTime(long launchTime) {
        try {
            return a(15, String.valueOf(launchTime));
        } catch (NumberFormatException e2) {
            if (!an.a(e2)) {
                ThrowableExtension.printStackTrace(e2);
            }
            return false;
        }
    }
}
