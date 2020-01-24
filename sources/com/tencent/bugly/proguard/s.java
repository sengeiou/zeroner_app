package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.text.TextUtils;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.download.b;
import com.tencent.bugly.beta.global.e;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/* compiled from: BUGLY */
public class s implements b {
    public static s a = new s();
    public ConcurrentHashMap<String, DownloadTask> b = new ConcurrentHashMap<>(3);
    private ScheduledExecutorService c = null;

    public s() {
        try {
            this.c = Executors.newScheduledThreadPool(3, new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("BETA_SDK_DOWNLOAD");
                    return thread;
                }
            });
            if (this.c.isShutdown()) {
                throw new IllegalArgumentException("ScheduledExecutorService is not available!");
            }
        } catch (Exception e) {
            an.a(e);
        }
    }

    public DownloadTask a(String str, String str2, String str3, String str4) {
        t tVar = null;
        if (TextUtils.isEmpty(str)) {
            an.e("downloadUrl is null!", new Object[0]);
            return null;
        } else if (TextUtils.isEmpty(str2)) {
            an.e("saveDir is null!", new Object[0]);
            return null;
        } else if (this.b.get(str) != null) {
            return (DownloadTask) this.b.get(str);
        } else {
            ContentValues a2 = p.a.a(str);
            if (!(a2 == null || a2.get("_dUrl") == null || a2.getAsString("_sFile") == null || a2.getAsLong("_sLen") == null || a2.getAsLong("_tLen") == null || a2.getAsString("_MD5") == null)) {
                t tVar2 = new t((String) a2.get("_dUrl"), a2.getAsString("_sFile"), a2.getAsLong("_sLen").longValue(), a2.getAsLong("_tLen").longValue(), a2.getAsString("_MD5"));
                if (a2.getAsLong("_DLTIME") != null) {
                    tVar2.k = a2.getAsLong("_DLTIME").longValue();
                }
                tVar = tVar2;
            }
            if (tVar == null) {
                tVar = new t(str, str2, str3, str4);
            }
            tVar.setDownloadType(e.E.ae);
            return tVar;
        }
    }

    public synchronized boolean a(Runnable runnable) {
        boolean z = false;
        synchronized (this) {
            if (this.c == null || this.c.isShutdown()) {
                an.d("async handler was closed , should not post task!", new Object[0]);
            } else if (runnable == null) {
                an.d("async task = null", new Object[0]);
            } else {
                an.d("task start %s", runnable.getClass().getName());
                this.c.execute(runnable);
                z = true;
            }
        }
        return z;
    }
}
