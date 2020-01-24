package com.mob.commons.deviceinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Parcelable;
import com.mob.commons.a;
import com.mob.commons.c;
import com.mob.commons.e;
import com.mob.commons.f;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class DeviceInfoCollector implements Callback {
    private static DeviceInfoCollector a;
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public DeviceHelper c;
    /* access modifiers changed from: private */
    public Hashon d = new Hashon();
    private Handler e;
    private Random f = new Random();

    public static synchronized void startCollector(Context context) {
        synchronized (DeviceInfoCollector.class) {
            if (a == null) {
                a = new DeviceInfoCollector(context);
                a.a();
            }
        }
    }

    private DeviceInfoCollector(Context context) {
        this.b = context.getApplicationContext();
        this.c = DeviceHelper.getInstance(context);
    }

    private void a() {
        AnonymousClass1 r0 = new MobHandlerThread() {
            public void run() {
                e.a(new File(R.getCacheRoot(DeviceInfoCollector.this.b), "comm/locks/.dic_lock"), new Runnable() {
                    public void run() {
                        AnonymousClass1.this.a();
                    }
                });
            }

            /* access modifiers changed from: private */
            public void a() {
                super.run();
            }
        };
        r0.start();
        this.e = new Handler(r0.getLooper(), this);
        this.e.sendEmptyMessage(1);
        this.e.sendEmptyMessage(2);
        this.e.sendEmptyMessage(3);
        this.e.sendEmptyMessage(5);
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                if (a.g(this.b)) {
                    b();
                    break;
                }
                break;
            case 2:
                if (a.l(this.b)) {
                    if (c()) {
                        d();
                    }
                    e();
                    break;
                }
                break;
            case 3:
                if (a.h(this.b)) {
                    f();
                }
                this.e.sendEmptyMessageDelayed(4, (long) ((this.f.nextInt(120) + 180) * 1000));
                break;
            case 4:
                if (a.h(this.b) && (a.a(this.b) + (((long) a.i(this.b)) * 1000) < a.a(this.b) || g())) {
                    f();
                }
                this.e.sendEmptyMessageDelayed(4, (long) ((this.f.nextInt(120) + 180) * 1000));
                break;
            case 5:
                if (a.j(this.b)) {
                    a(this.c.getLocation(30, 0, true), 1);
                    a(this.c.getLocation(0, 15, true), 2);
                }
                this.e.sendEmptyMessageDelayed(5, (long) (a.k(this.b) * 1000));
                break;
        }
        return false;
    }

    private void b() {
        HashMap hashMap = new HashMap();
        hashMap.put("phonename", this.c.getBluetoothName());
        hashMap.put("signmd5", this.c.getSignMD5());
        String MD5 = Data.MD5(this.d.fromHashMap(hashMap));
        String a2 = f.a(this.b);
        if (a2 == null || !a2.equals(MD5)) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "DEVEXT");
            hashMap2.put("data", hashMap);
            hashMap2.put("datetime", Long.valueOf(a.a(this.b)));
            c.a(this.b).a(hashMap2);
            f.a(this.b, MD5);
        }
    }

    private boolean c() {
        long b2 = f.b(this.b);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(b2);
        int i = instance.get(1);
        int i2 = instance.get(2);
        int i3 = instance.get(5);
        long a2 = a.a(this.b);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(a2);
        int i4 = instance2.get(1);
        int i5 = instance2.get(2);
        int i6 = instance2.get(5);
        if (i == i4 && i2 == i5 && i3 == i6) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void d() {
        synchronized (a) {
            HashMap hashMap = new HashMap();
            hashMap.put("ssid", this.c.getSSID());
            hashMap.put("bssid", this.c.getBssid());
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "WIFI_INFO");
            hashMap2.put("data", hashMap);
            long a2 = a.a(this.b);
            hashMap2.put("datetime", Long.valueOf(a2));
            c.a(this.b).a(hashMap2);
            f.a(this.b, a2);
            f.b(this.b, Data.MD5(this.d.fromHashMap(hashMap)));
        }
    }

    private void e() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.b.registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction())) {
                    Parcelable parcelableExtra = intent.getParcelableExtra("networkInfo");
                    if (parcelableExtra != null && ((NetworkInfo) parcelableExtra).isAvailable()) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("ssid", DeviceInfoCollector.this.c.getSSID());
                        hashMap.put("bssid", DeviceInfoCollector.this.c.getBssid());
                        String MD5 = Data.MD5(DeviceInfoCollector.this.d.fromHashMap(hashMap));
                        String c = f.c(context);
                        if ((c == null || !c.equals(MD5)) && a.l(context)) {
                            DeviceInfoCollector.this.d();
                        }
                    }
                }
            }
        }, intentFilter);
    }

    private void f() {
        HashMap hashMap = new HashMap();
        int i = -1;
        try {
            i = Integer.parseInt(this.c.getCarrier());
        } catch (Throwable th) {
        }
        hashMap.put("carrier", Integer.valueOf(i));
        hashMap.put("simopname", this.c.getCarrierName());
        hashMap.put("lac", Integer.valueOf(this.c.getCellLac()));
        hashMap.put("cell", Integer.valueOf(this.c.getCellId()));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("type", "BSINFO");
        hashMap2.put("data", hashMap);
        hashMap2.put("datetime", Long.valueOf(a.a(this.b)));
        c.a(this.b).a(hashMap2);
        f.c(this.b, Data.MD5(this.d.fromHashMap(hashMap)));
        f.b(this.b, a.a(this.b) + (((long) a.i(this.b)) * 1000));
    }

    private boolean g() {
        HashMap hashMap = new HashMap();
        int i = -1;
        try {
            i = Integer.parseInt(this.c.getCarrier());
        } catch (Throwable th) {
        }
        hashMap.put("carrier", Integer.valueOf(i));
        hashMap.put("simopname", this.c.getCarrierName());
        hashMap.put("lac", Integer.valueOf(this.c.getCellLac()));
        hashMap.put("cell", Integer.valueOf(this.c.getCellId()));
        String MD5 = Data.MD5(this.d.fromHashMap(hashMap));
        String d2 = f.d(this.b);
        return d2 == null || !d2.equals(MD5);
    }

    private void a(Location location, int i) {
        if (location != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("accuracy", Float.valueOf(location.getAccuracy()));
            hashMap.put("latitude", Double.valueOf(location.getLatitude()));
            hashMap.put("longitude", Double.valueOf(location.getLongitude()));
            hashMap.put("location_type", Integer.valueOf(i));
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "LOCATION");
            hashMap2.put("data", hashMap);
            hashMap2.put("datetime", Long.valueOf(a.a(this.b)));
            c.a(this.b).a(hashMap2);
        }
    }
}
