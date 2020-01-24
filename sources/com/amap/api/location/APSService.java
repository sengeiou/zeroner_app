package com.amap.api.location;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.loc.ba;
import com.loc.di;
import com.loc.dm;
import com.loc.dp;
import com.loc.dr;
import com.loc.f;
import com.loc.w;

public class APSService extends Service {
    APSServiceBase a;
    int b = 0;
    boolean c = false;

    private boolean a() {
        if (dr.k(getApplicationContext())) {
            int i = -1;
            try {
                i = dm.b(getApplication().getBaseContext(), "checkSelfPermission", "android.permission.FOREGROUND_SERVICE");
            } catch (Throwable th) {
            }
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public IBinder onBind(Intent intent) {
        try {
            return this.a.onBind(intent);
        } catch (Throwable th) {
            di.a(th, "APSService", "onBind");
            return null;
        }
    }

    public void onCreate() {
        onCreate(this);
    }

    public void onCreate(Context context) {
        try {
            if (dp.d(context)) {
                Context context2 = context;
                this.a = (APSServiceBase) ba.a(context2, di.b(), w.c("AY29tLmFtYXAuYXBpLmxvY2F0aW9uLkFQU1NlcnZpY2VXcmFwcGVy"), f.class, new Class[]{Context.class}, new Object[]{context});
            } else if (this.a == null) {
                this.a = new f(context);
            }
        } catch (Throwable th) {
        }
        try {
            if (this.a == null) {
                this.a = new f(context);
            }
            this.a.onCreate();
        } catch (Throwable th2) {
            di.a(th2, "APSService", "onCreate");
        }
        super.onCreate();
    }

    public void onDestroy() {
        try {
            this.a.onDestroy();
            if (this.c) {
                stopForeground(true);
            }
        } catch (Throwable th) {
            di.a(th, "APSService", "onDestroy");
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            try {
                if (a()) {
                    int intExtra = intent.getIntExtra("g", 0);
                    if (intExtra == 1) {
                        int intExtra2 = intent.getIntExtra("i", 0);
                        Notification notification = (Notification) intent.getParcelableExtra("h");
                        if (!(intExtra2 == 0 || notification == null)) {
                            startForeground(intExtra2, notification);
                            this.c = true;
                            this.b++;
                        }
                    } else if (intExtra == 2) {
                        if (intent.getBooleanExtra("j", true) && this.b > 0) {
                            this.b--;
                        }
                        if (this.b <= 0) {
                            stopForeground(true);
                            this.c = false;
                        } else {
                            stopForeground(false);
                        }
                    }
                }
            } catch (Throwable th) {
            }
        }
        try {
            return this.a.onStartCommand(intent, i, i2);
        } catch (Throwable th2) {
            di.a(th2, "APSService", "onStartCommand");
            return super.onStartCommand(intent, i, i2);
        }
    }
}
