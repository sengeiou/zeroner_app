package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.Log;
import com.alibaba.android.arouter.utils.Consts;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class zzae {
    public static boolean DEBUG;
    private static String TAG;

    static class zza {
        public static final boolean zzbl = zzae.DEBUG;
        private final List<zzaf> zzbm = new ArrayList();
        private boolean zzbn = false;

        zza() {
        }

        /* access modifiers changed from: protected */
        public final void finalize() throws Throwable {
            if (!this.zzbn) {
                zzc("Request on the loose");
                zzae.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        public final synchronized void zza(String str, long j) {
            if (this.zzbn) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.zzbm.add(new zzaf(str, j, SystemClock.elapsedRealtime()));
        }

        public final synchronized void zzc(String str) {
            long j;
            this.zzbn = true;
            if (this.zzbm.size() == 0) {
                j = 0;
            } else {
                j = ((zzaf) this.zzbm.get(this.zzbm.size() - 1)).time - ((zzaf) this.zzbm.get(0)).time;
            }
            if (j > 0) {
                long j2 = ((zzaf) this.zzbm.get(0)).time;
                zzae.zzb("(%-4d ms) %s", Long.valueOf(j), str);
                long j3 = j2;
                for (zzaf zzaf : this.zzbm) {
                    long j4 = zzaf.time;
                    zzae.zzb("(+%-4d) [%2d] %s", Long.valueOf(j4 - j3), Long.valueOf(zzaf.zzbo), zzaf.name);
                    j3 = j4;
                }
            }
        }
    }

    static {
        String str = "Volley";
        TAG = str;
        DEBUG = Log.isLoggable(str, 2);
    }

    public static void zza(String str, Object... objArr) {
        if (DEBUG) {
            Log.v(TAG, zzd(str, objArr));
        }
    }

    public static void zza(Throwable th, String str, Object... objArr) {
        Log.e(TAG, zzd(str, objArr), th);
    }

    public static void zzb(String str, Object... objArr) {
        Log.d(TAG, zzd(str, objArr));
    }

    public static void zzc(String str, Object... objArr) {
        Log.e(TAG, zzd(str, objArr));
    }

    private static String zzd(String str, Object... objArr) {
        String str2;
        if (objArr != null) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        String str3 = "<unknown>";
        int i = 2;
        while (true) {
            if (i >= stackTrace.length) {
                str2 = str3;
                break;
            } else if (!stackTrace[i].getClass().equals(zzae.class)) {
                String className = stackTrace[i].getClassName();
                String substring = className.substring(className.lastIndexOf(46) + 1);
                String substring2 = substring.substring(substring.lastIndexOf(36) + 1);
                String methodName = stackTrace[i].getMethodName();
                str2 = new StringBuilder(String.valueOf(substring2).length() + 1 + String.valueOf(methodName).length()).append(substring2).append(Consts.DOT).append(methodName).toString();
                break;
            } else {
                i++;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), str2, str});
    }
}
