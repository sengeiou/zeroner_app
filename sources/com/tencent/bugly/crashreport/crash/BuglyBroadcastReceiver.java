package com.tencent.bugly.crashreport.crash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.common.info.b;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;

/* compiled from: BUGLY */
public class BuglyBroadcastReceiver extends BroadcastReceiver {
    /* access modifiers changed from: private */
    public static BuglyBroadcastReceiver d = null;
    /* access modifiers changed from: private */
    public IntentFilter a = new IntentFilter();
    /* access modifiers changed from: private */
    public Context b;
    private String c;
    private boolean e = true;

    public static synchronized BuglyBroadcastReceiver getInstance() {
        BuglyBroadcastReceiver buglyBroadcastReceiver;
        synchronized (BuglyBroadcastReceiver.class) {
            if (d == null) {
                d = new BuglyBroadcastReceiver();
            }
            buglyBroadcastReceiver = d;
        }
        return buglyBroadcastReceiver;
    }

    public synchronized void addFilter(String action) {
        if (!this.a.hasAction(action)) {
            this.a.addAction(action);
        }
        an.c("add action %s", action);
    }

    public synchronized void register(Context context) {
        this.b = context;
        ap.a((Runnable) new Runnable() {
            public void run() {
                try {
                    an.a(BuglyBroadcastReceiver.d.getClass(), "Register broadcast receiver of Bugly.", new Object[0]);
                    synchronized (this) {
                        BuglyBroadcastReceiver.this.b.registerReceiver(BuglyBroadcastReceiver.d, BuglyBroadcastReceiver.this.a);
                    }
                } catch (Throwable th) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        });
    }

    public synchronized void unregister(Context context) {
        try {
            an.a(getClass(), "Unregister broadcast receiver of Bugly.", new Object[0]);
            context.unregisterReceiver(this);
            this.b = context;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
    }

    public final void onReceive(Context context, Intent intent) {
        try {
            a(context, intent);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final synchronized boolean a(Context context, Intent intent) {
        boolean z = true;
        synchronized (this) {
            if (!(context == null || intent == null)) {
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    if (this.e) {
                        this.e = false;
                    } else {
                        String f = b.f(this.b);
                        an.c("is Connect BC " + f, new Object[0]);
                        an.a("network %s changed to %s", "" + this.c, "" + f);
                        if (f == null) {
                            this.c = null;
                        } else {
                            String str = this.c;
                            this.c = f;
                            long currentTimeMillis = System.currentTimeMillis();
                            a a2 = a.a();
                            ak a3 = ak.a();
                            com.tencent.bugly.crashreport.common.info.a a4 = com.tencent.bugly.crashreport.common.info.a.a(context);
                            if (a2 == null || a3 == null || a4 == null) {
                                an.d("not inited BC not work", new Object[0]);
                            } else if (!f.equals(str)) {
                                if (currentTimeMillis - a3.a(c.a) > 30000) {
                                    an.a("try to upload crash on network changed.", new Object[0]);
                                    c a5 = c.a();
                                    if (a5 != null) {
                                        a5.a(0);
                                    }
                                }
                                if (currentTimeMillis - a3.a(1001) > 30000) {
                                    an.a("try to upload userinfo on network changed.", new Object[0]);
                                    com.tencent.bugly.crashreport.biz.b.b.b();
                                }
                            }
                        }
                    }
                }
            }
            z = false;
        }
        return z;
    }
}
