package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import com.amap.api.location.APSServiceBase;

/* compiled from: ApsServiceCore */
public class f implements APSServiceBase {
    e a = null;
    Context b = null;
    Messenger c = null;

    public f(Context context) {
        this.b = context.getApplicationContext();
        this.a = new e(this.b);
    }

    public IBinder onBind(Intent intent) {
        this.a.b(intent);
        this.a.a(intent);
        this.c = new Messenger(this.a.b());
        return this.c.getBinder();
    }

    public void onCreate() {
        try {
            e.f();
            this.a.j = dr.c();
            this.a.k = dr.b();
            this.a.a();
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "onCreate");
        }
    }

    public void onDestroy() {
        try {
            if (this.a != null) {
                this.a.b().sendEmptyMessage(11);
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "onDestroy");
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 0;
    }
}
