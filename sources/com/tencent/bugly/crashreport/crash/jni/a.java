package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.b;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.ap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: BUGLY */
public class a implements NativeExceptionHandler {
    private final Context a;
    private final b b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final com.tencent.bugly.crashreport.common.strategy.a d;

    public a(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2) {
        this.a = context;
        this.b = bVar;
        this.c = aVar;
        this.d = aVar2;
    }

    public CrashDetailBean packageCrashDatas(String processName, String threadName, long crashTime, String crashType, String crashAddr, String crashStack, String sendingType, String sendingProcessName, String coreDumpFile, String sysLogPath, String jniLogPath, String nativeVersion, byte[] userLog, Map<String, String> userKeyValue, boolean isHappenNow, boolean hasPendingException) {
        boolean l = c.a().l();
        if (l) {
            an.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.b = 1;
        crashDetailBean.e = this.c.h();
        crashDetailBean.f = this.c.o;
        crashDetailBean.g = this.c.w();
        crashDetailBean.m = this.c.g();
        crashDetailBean.n = crashType;
        crashDetailBean.o = l ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        crashDetailBean.p = crashAddr;
        if (crashStack == null) {
            crashStack = "";
        }
        crashDetailBean.q = crashStack;
        crashDetailBean.r = crashTime;
        crashDetailBean.u = ap.b(crashDetailBean.q.getBytes());
        crashDetailBean.A = processName;
        crashDetailBean.B = threadName;
        crashDetailBean.I = this.c.y();
        crashDetailBean.h = this.c.v();
        crashDetailBean.i = this.c.J();
        crashDetailBean.v = coreDumpFile;
        String str = null;
        NativeCrashHandler instance = NativeCrashHandler.getInstance();
        if (instance != null) {
            str = instance.getDumpFilePath();
        }
        String a2 = b.a(str, coreDumpFile);
        if (!ap.a(a2)) {
            crashDetailBean.V = a2;
        }
        crashDetailBean.W = b.c(str);
        crashDetailBean.w = b.a(sysLogPath, c.e, c.h, c.m);
        crashDetailBean.x = b.a(jniLogPath, c.e, null, true);
        crashDetailBean.K = sendingProcessName;
        crashDetailBean.L = sendingType;
        crashDetailBean.M = nativeVersion;
        crashDetailBean.F = this.c.p();
        crashDetailBean.G = this.c.o();
        crashDetailBean.H = this.c.q();
        if (isHappenNow) {
            crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.i();
            crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.g();
            crashDetailBean.E = com.tencent.bugly.crashreport.common.info.b.k();
            if (crashDetailBean.w == null) {
                crashDetailBean.w = ap.a(this.a, c.e, c.h);
            }
            crashDetailBean.y = ao.a();
            crashDetailBean.N = this.c.a;
            crashDetailBean.O = this.c.a();
            crashDetailBean.Q = this.c.H();
            crashDetailBean.R = this.c.I();
            crashDetailBean.S = this.c.B();
            crashDetailBean.T = this.c.G();
            crashDetailBean.z = ap.a(c.f, false);
            String str2 = "java:\n";
            int indexOf = crashDetailBean.q.indexOf(str2);
            if (indexOf > 0) {
                int length = indexOf + str2.length();
                if (length < crashDetailBean.q.length()) {
                    String substring = crashDetailBean.q.substring(length, crashDetailBean.q.length() - 1);
                    if (substring.length() > 0 && crashDetailBean.z.containsKey(crashDetailBean.B)) {
                        String str3 = (String) crashDetailBean.z.get(crashDetailBean.B);
                        int indexOf2 = str3.indexOf(substring);
                        if (indexOf2 > 0) {
                            String substring2 = str3.substring(indexOf2);
                            crashDetailBean.z.put(crashDetailBean.B, substring2);
                            crashDetailBean.q = crashDetailBean.q.substring(0, length);
                            crashDetailBean.q += substring2;
                        }
                    }
                }
            }
            if (processName == null) {
                crashDetailBean.A = this.c.e;
            }
            this.b.c(crashDetailBean);
        } else {
            crashDetailBean.C = -1;
            crashDetailBean.D = -1;
            crashDetailBean.E = -1;
            if (crashDetailBean.w == null) {
                crashDetailBean.w = "this crash is occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.N = -1;
            crashDetailBean.Q = -1;
            crashDetailBean.R = -1;
            crashDetailBean.S = userKeyValue;
            crashDetailBean.T = this.c.G();
            crashDetailBean.z = null;
            if (processName == null) {
                crashDetailBean.A = "unknown(record)";
            }
            if (userLog != null) {
                crashDetailBean.y = userLog;
            }
        }
        return crashDetailBean;
    }

    public void handleNativeException(int pid, int tid, long exTimesInSecond, long exTimeInMicosecond, String exType, String exAddress, String exStack, String tombPath, int siCode, String siCodeType, int sendingPid, int sendingUid, int siErrno, String siErrnoMsg, String nativeRqdVersion) {
        an.a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(pid, tid, exTimesInSecond, exTimeInMicosecond, exType, exAddress, exStack, tombPath, siCode, siCodeType, sendingPid, sendingUid, siErrno, siErrnoMsg, nativeRqdVersion, null);
    }

    public void handleNativeException2(int pid, int tid, long exTimesInSecond, long exTimeInMicosecond, String exType, String exAddress, String exStack, String tombPath, int siCode, String siCodeType, int sendingPid, int sendingUid, int siErrno, String siErrnoMsg, String nativeRqdVersion, String[] extraMsg) {
        String str;
        String str2;
        String str3;
        String str4;
        boolean z;
        an.a("Native Crash Happen v2", new Object[0]);
        try {
            String b2 = b.b(exStack);
            String str5 = "UNKNOWN";
            if (siCode > 0) {
                str = "KERNEL";
                str2 = exType + "(" + siCodeType + ")";
            } else {
                if (sendingPid > 0) {
                    str5 = AppInfo.a(this.a, sendingPid);
                }
                if (!str5.equals(String.valueOf(sendingPid))) {
                    str5 = str5 + "(" + sendingPid + ")";
                    str = siCodeType;
                    str2 = exType;
                } else {
                    str = siCodeType;
                    str2 = exType;
                }
            }
            HashMap hashMap = new HashMap();
            if (extraMsg != null) {
                for (int i = 0; i < extraMsg.length; i++) {
                    String str6 = extraMsg[i];
                    if (str6 != null) {
                        an.a("Extra message[%d]: %s", Integer.valueOf(i), str6);
                        String[] split = str6.split("=");
                        if (split.length == 2) {
                            hashMap.put(split[0], split[1]);
                        } else {
                            an.d("bad extraMsg %s", str6);
                        }
                    }
                }
            } else {
                an.c("not found extraMsg", new Object[0]);
            }
            boolean z2 = false;
            String str7 = (String) hashMap.get("HasPendingException");
            if (str7 != null && str7.equals("true")) {
                an.a("Native crash happened with a Java pending exception.", new Object[0]);
                z2 = true;
            }
            String str8 = (String) hashMap.get("ExceptionProcessName");
            if (str8 == null || str8.length() == 0) {
                str3 = this.c.e;
            } else {
                an.c("Name of crash process: %s", str8);
                str3 = str8;
            }
            String str9 = (String) hashMap.get("ExceptionThreadName");
            if (str9 == null || str9.length() == 0) {
                Thread currentThread = Thread.currentThread();
                str4 = currentThread.getName() + "(" + currentThread.getId() + ")";
            } else {
                an.c("Name of crash thread: %s", str9);
                Iterator it = Thread.getAllStackTraces().keySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        str4 = str9;
                        z = false;
                        break;
                    }
                    Thread thread = (Thread) it.next();
                    if (thread.getName().equals(str9)) {
                        str4 = str9 + "(" + thread.getId() + ")";
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    str4 = str4 + "(" + tid + ")";
                }
            }
            long j = (exTimeInMicosecond / 1000) + (1000 * exTimesInSecond);
            String str10 = (String) hashMap.get("SysLogPath");
            String str11 = (String) hashMap.get("JniLogPath");
            if (!this.d.b()) {
                an.d("no remote but still store!", new Object[0]);
            }
            if (this.d.c().g || !this.d.b()) {
                CrashDetailBean packageCrashDatas = packageCrashDatas(str3, str4, j, str2, exAddress, b2, str, str5, tombPath, str10, str11, nativeRqdVersion, null, null, true, z2);
                if (packageCrashDatas == null) {
                    an.e("pkg crash datas fail!", new Object[0]);
                    return;
                }
                b.a("NATIVE_CRASH", ap.a(), str3, str4, str2 + "\n" + exAddress + "\n" + b2, packageCrashDatas);
                boolean z3 = !this.b.a(packageCrashDatas, siCode);
                String str12 = null;
                NativeCrashHandler instance = NativeCrashHandler.getInstance();
                if (instance != null) {
                    str12 = instance.getDumpFilePath();
                }
                b.a(true, str12);
                if (z3) {
                    this.b.a(packageCrashDatas, 3000, true);
                }
                this.b.b(packageCrashDatas);
                return;
            }
            an.e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
            b.a("NATIVE_CRASH", ap.a(), str3, str4, str2 + "\n" + exAddress + "\n" + b2, null);
            ap.b(tombPath);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
    }
}
