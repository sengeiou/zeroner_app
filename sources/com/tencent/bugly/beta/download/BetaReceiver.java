package com.tencent.bugly.beta.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.beta.global.a;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.beta.ui.c;
import com.tencent.bugly.beta.ui.f;
import com.tencent.bugly.beta.ui.g;
import com.tencent.bugly.beta.ui.h;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.open.SocialConstants;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: BUGLY */
public class BetaReceiver extends BroadcastReceiver {
    public static String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static ConcurrentHashMap<String, c> netListeners = new ConcurrentHashMap<>(3);
    public static int netStatus = 0;

    public static synchronized void addTask(DownloadTask task) {
        synchronized (BetaReceiver.class) {
            netStatus = a.a(e.E.s);
            f fVar = new f();
            fVar.n = task;
            netListeners.put(task.getDownloadUrl(), new c(2, task, Boolean.valueOf(false), fVar));
        }
    }

    public synchronized void onReceive(final Context context, final Intent intent) {
        am.a().a(new Runnable() {
            public void run() {
                try {
                    if (intent.getAction().equals(BetaReceiver.CONNECTIVITY_CHANGE)) {
                        int i = BetaReceiver.netStatus;
                        BetaReceiver.netStatus = a.a(context);
                        for (c a2 : BetaReceiver.netListeners.values()) {
                            a2.a(i, BetaReceiver.netStatus);
                        }
                    } else if (intent.getAction().equals(c.a.c)) {
                        switch (intent.getIntExtra(SocialConstants.TYPE_REQUEST, -1)) {
                            case 1:
                                DownloadTask downloadTask = c.a.b;
                                if (downloadTask != null) {
                                    switch (downloadTask.getStatus()) {
                                        case 0:
                                        case 3:
                                        case 4:
                                        case 5:
                                            BetaReceiver.addTask(downloadTask);
                                            downloadTask.download();
                                            break;
                                        case 1:
                                            a.a(e.E.s, downloadTask.getSaveFile(), downloadTask.getMD5());
                                            break;
                                        case 2:
                                            BetaReceiver.netListeners.remove(downloadTask.getDownloadUrl());
                                            downloadTask.stop();
                                            break;
                                    }
                                    if (com.tencent.bugly.beta.upgrade.c.a.e == null && h.v != null) {
                                        h.v.a(downloadTask);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                g.a(c.a.e, true, true, 0);
                                return;
                            default:
                                Log.v("", "do nothing");
                                return;
                        }
                    }
                } catch (Exception e) {
                    if (!an.b(e)) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }
        });
    }
}
