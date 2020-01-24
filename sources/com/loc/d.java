package com.loc;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.PointerIconCompat;
import android.text.TextUtils;
import android.webkit.WebView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.location.APSService;
import com.amap.api.location.LocationManagerBase;
import com.amap.api.location.UmidtokenInfo;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: AmapLocationManager */
public class d implements LocationManagerBase {
    /* access modifiers changed from: private */
    public static boolean E = false;
    /* access modifiers changed from: private */
    public boolean A = false;
    private volatile boolean B = false;
    private boolean C = true;
    /* access modifiers changed from: private */
    public boolean D = true;
    private h F = null;
    private ServiceConnection G = new ServiceConnection() {
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                d.this.h = new Messenger(iBinder);
                d.this.A = true;
                d.this.q = true;
            } catch (Throwable th) {
                di.a(th, "AmapLocationManager", "onServiceConnected");
            }
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            d.this.h = null;
            d.this.A = false;
        }
    };
    AMapLocationClientOption a = new AMapLocationClientOption();
    public c b;
    g c = null;
    ArrayList<AMapLocationListener> d = new ArrayList<>();
    boolean e = false;
    public boolean f = true;
    i g;
    Messenger h = null;
    Messenger i = null;
    Intent j = null;
    int k = 0;
    b l = null;
    boolean m = false;
    AMapLocationMode n = AMapLocationMode.Hight_Accuracy;
    Object o = new Object();
    Cdo p = null;
    boolean q = false;
    e r = null;
    String s = null;
    AMapLocationQualityReport t = null;
    boolean u = false;
    boolean v = false;
    a w = null;
    String x = null;
    boolean y = false;
    private Context z;

    /* compiled from: AmapLocationManager */
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            try {
                super.handleMessage(message);
                switch (message.what) {
                    case 1002:
                        d.a(d.this, (AMapLocationListener) message.obj);
                        return;
                    case PointerIconCompat.TYPE_HELP /*1003*/:
                        try {
                            d.this.c();
                            return;
                        } catch (Throwable th) {
                            di.a(th, "AMapLocationManage$MHandlerr", "handleMessage START_LOCATION");
                            return;
                        }
                    case 1004:
                        try {
                            d.this.d();
                            return;
                        } catch (Throwable th2) {
                            di.a(th2, "AMapLocationManage$MHandlerr", "handleMessage STOP_LOCATION");
                            return;
                        }
                    case 1005:
                        try {
                            d.b(d.this, (AMapLocationListener) message.obj);
                            return;
                        } catch (Throwable th3) {
                            di.a(th3, "AMapLocationManage$MHandlerr", "handleMessage REMOVE_LISTENER");
                            return;
                        }
                    case PointerIconCompat.TYPE_TEXT /*1008*/:
                        try {
                            d.g(d.this);
                            return;
                        } catch (Throwable th4) {
                            di.a(th4, "AMapLocationManage$MHandlerr", "handleMessage START_SOCKET");
                            return;
                        }
                    case PointerIconCompat.TYPE_VERTICAL_TEXT /*1009*/:
                        try {
                            d.h(d.this);
                            return;
                        } catch (Throwable th5) {
                            di.a(th5, "AMapLocationManage$MHandlerr", "handleMessage STOP_SOCKET");
                            return;
                        }
                    case 1011:
                        try {
                            d.this.a();
                            return;
                        } catch (Throwable th6) {
                            di.a(th6, "AMapLocationManage$MHandlerr", "handleMessage DESTROY");
                            return;
                        }
                    case PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW /*1014*/:
                        d.b(d.this, message);
                        return;
                    case PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW /*1015*/:
                        try {
                            d.this.c.a(d.this.a);
                            d.this.a((int) InputDeviceCompat.SOURCE_GAMEPAD, (Object) null, 300000);
                            return;
                        } catch (Throwable th7) {
                            di.a(th7, "AMapLocationManage$MHandlerr", "handleMessage START_GPS_LOCATION");
                            return;
                        }
                    case PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW /*1016*/:
                        try {
                            if (d.this.c.b()) {
                                d.this.a((int) PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, (Object) null, 1000);
                                return;
                            } else {
                                d.d(d.this);
                                return;
                            }
                        } catch (Throwable th8) {
                            di.a(th8, "AMapLocationManage$MHandlerr", "handleMessage START_LBS_LOCATION");
                            return;
                        }
                    case PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW /*1017*/:
                        try {
                            d.this.c.a();
                            d.this.a((int) InputDeviceCompat.SOURCE_GAMEPAD);
                            return;
                        } catch (Throwable th9) {
                            di.a(th9, "AMapLocationManage$MHandlerr", "handleMessage STOP_GPS_LOCATION");
                            return;
                        }
                    case PointerIconCompat.TYPE_ZOOM_IN /*1018*/:
                        try {
                            d.this.a = (AMapLocationClientOption) message.obj;
                            if (d.this.a != null) {
                                d.f(d.this);
                                return;
                            }
                            return;
                        } catch (Throwable th10) {
                            di.a(th10, "AMapLocationManage$MHandlerr", "handleMessage SET_OPTION");
                            return;
                        }
                    case 1023:
                        try {
                            d.c(d.this, message);
                            return;
                        } catch (Throwable th11) {
                            di.a(th11, "AMapLocationManage$MHandlerr", "handleMessage ACTION_ENABLE_BACKGROUND");
                            return;
                        }
                    case 1024:
                        try {
                            d.d(d.this, message);
                            return;
                        } catch (Throwable th12) {
                            di.a(th12, "AMapLocationManage$MHandlerr", "handleMessage ACTION_DISABLE_BACKGROUND");
                            return;
                        }
                    case InputDeviceCompat.SOURCE_GAMEPAD /*1025*/:
                        try {
                            if (d.this.c != null) {
                                if (d.this.c.f()) {
                                    d.this.c.a();
                                    d.this.c.a(d.this.a);
                                }
                                d.this.a((int) InputDeviceCompat.SOURCE_GAMEPAD, (Object) null, 300000);
                                return;
                            }
                            return;
                        } catch (Throwable th13) {
                            di.a(th13, "AMapLocationManage$MHandlerr", "handleMessage ACTION_REBOOT_GPS_LOCATION");
                            return;
                        }
                    default:
                        return;
                }
            } catch (Throwable th14) {
                di.a(th14, "AMapLocationManage$MHandlerr", "handleMessage");
            }
            di.a(th14, "AMapLocationManage$MHandlerr", "handleMessage");
        }
    }

    /* compiled from: AmapLocationManager */
    static class b extends HandlerThread {
        d a = null;

        public b(String str, d dVar) {
            super(str);
            this.a = dVar;
        }

        /* access modifiers changed from: protected */
        public final void onLooperPrepared() {
            try {
                this.a.g.a();
                this.a.f();
                super.onLooperPrepared();
            } catch (Throwable th) {
            }
        }

        public final void run() {
            try {
                super.run();
            } catch (Throwable th) {
            }
        }
    }

    /* compiled from: AmapLocationManager */
    public class c extends Handler {
        public c() {
        }

        public c(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r4) {
            /*
                r3 = this;
                super.handleMessage(r4)     // Catch:{ Throwable -> 0x002b }
                com.loc.d r0 = com.loc.d.this     // Catch:{ Throwable -> 0x002b }
                boolean r0 = r0.m     // Catch:{ Throwable -> 0x002b }
                if (r0 == 0) goto L_0x0010
                boolean r0 = com.loc.di.d()     // Catch:{ Throwable -> 0x002b }
                if (r0 != 0) goto L_0x0010
            L_0x000f:
                return
            L_0x0010:
                int r0 = r4.what     // Catch:{ Throwable -> 0x002b }
                switch(r0) {
                    case 1: goto L_0x0016;
                    case 2: goto L_0x003c;
                    case 3: goto L_0x000f;
                    case 4: goto L_0x0015;
                    case 5: goto L_0x004d;
                    case 6: goto L_0x0070;
                    case 7: goto L_0x008d;
                    case 8: goto L_0x0036;
                    case 9: goto L_0x00ab;
                    default: goto L_0x0015;
                }
            L_0x0015:
                goto L_0x000f
            L_0x0016:
                android.os.Bundle r0 = r4.getData()     // Catch:{ Throwable -> 0x0020 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ Throwable -> 0x0020 }
                com.loc.d.a(r1, r0)     // Catch:{ Throwable -> 0x0020 }
                goto L_0x000f
            L_0x0020:
                r0 = move-exception
                java.lang.String r1 = "AmapLocationManager$ActionHandler"
                java.lang.String r2 = "handleMessage RESULT_LBS_LOCATIONSUCCESS"
                com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x002b }
                goto L_0x000f
            L_0x002b:
                r0 = move-exception
                java.lang.String r1 = "AmapLocationManager$MainHandler"
                java.lang.String r2 = "handleMessage"
                com.loc.di.a(r0, r1, r2)
                goto L_0x000f
            L_0x0036:
                r0 = 0
                r1 = 2141(0x85d, float:3.0E-42)
                com.loc.Cdo.a(r0, r1)     // Catch:{ Throwable -> 0x002b }
            L_0x003c:
                com.loc.d r0 = com.loc.d.this     // Catch:{ Throwable -> 0x0042 }
                com.loc.d.a(r0, r4)     // Catch:{ Throwable -> 0x0042 }
                goto L_0x000f
            L_0x0042:
                r0 = move-exception
                java.lang.String r1 = "AmapLocationManager$ActionHandler"
                java.lang.String r2 = "handleMessage RESULT_GPS_LOCATIONSUCCESS"
                com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x002b }
                goto L_0x000f
            L_0x004d:
                android.os.Bundle r0 = r4.getData()     // Catch:{ Throwable -> 0x0065 }
                java.lang.String r1 = "optBundle"
                com.loc.d r2 = com.loc.d.this     // Catch:{ Throwable -> 0x0065 }
                com.amap.api.location.AMapLocationClientOption r2 = r2.a     // Catch:{ Throwable -> 0x0065 }
                android.os.Bundle r2 = com.loc.di.a(r2)     // Catch:{ Throwable -> 0x0065 }
                r0.putBundle(r1, r2)     // Catch:{ Throwable -> 0x0065 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ Throwable -> 0x0065 }
                r1.a(10, r0)     // Catch:{ Throwable -> 0x0065 }
                goto L_0x000f
            L_0x0065:
                r0 = move-exception
                java.lang.String r1 = "AmapLocationManager$ActionHandler"
                java.lang.String r2 = "handleMessage RESULT_GPS_LOCATIONCHANGE"
                com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x002b }
                goto L_0x000f
            L_0x0070:
                android.os.Bundle r0 = r4.getData()     // Catch:{ Throwable -> 0x0082 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ Throwable -> 0x0082 }
                com.loc.g r1 = r1.c     // Catch:{ Throwable -> 0x0082 }
                if (r1 == 0) goto L_0x000f
                com.loc.d r1 = com.loc.d.this     // Catch:{ Throwable -> 0x0082 }
                com.loc.g r1 = r1.c     // Catch:{ Throwable -> 0x0082 }
                r1.a(r0)     // Catch:{ Throwable -> 0x0082 }
                goto L_0x000f
            L_0x0082:
                r0 = move-exception
                java.lang.String r1 = "AmapLocationManager$ActionHandler"
                java.lang.String r2 = "handleMessage RESULT_GPS_GEO_SUCCESS"
                com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x002b }
                goto L_0x000f
            L_0x008d:
                android.os.Bundle r0 = r4.getData()     // Catch:{ Throwable -> 0x009f }
                com.loc.d r1 = com.loc.d.this     // Catch:{ Throwable -> 0x009f }
                java.lang.String r2 = "ngpsAble"
                boolean r0 = r0.getBoolean(r2)     // Catch:{ Throwable -> 0x009f }
                r1.D = r0     // Catch:{ Throwable -> 0x009f }
                goto L_0x000f
            L_0x009f:
                r0 = move-exception
                java.lang.String r1 = "AmapLocationManager$ActionHandler"
                java.lang.String r2 = "handleMessage RESULT_NGPS_ABLE"
                com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x002b }
                goto L_0x000f
            L_0x00ab:
                android.os.Bundle r0 = r4.getData()     // Catch:{ Throwable -> 0x00bb }
                java.lang.String r1 = "installMockApp"
                boolean r0 = r0.getBoolean(r1)     // Catch:{ Throwable -> 0x00bb }
                com.loc.d.E = r0     // Catch:{ Throwable -> 0x00bb }
                goto L_0x000f
            L_0x00bb:
                r0 = move-exception
                java.lang.String r1 = "AmapLocationManager$ActionHandler"
                java.lang.String r2 = "handleMessage RESULT_INSTALLED_MOCK_APP"
                com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x002b }
                goto L_0x000f
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.d.c.handleMessage(android.os.Message):void");
        }
    }

    public d(Context context, Intent intent) {
        this.z = context;
        this.j = intent;
        if (di.d()) {
            try {
                dp.a(this.z, di.b());
            } catch (Throwable th) {
            }
        }
        try {
            if (Looper.myLooper() == null) {
                this.b = new c(this.z.getMainLooper());
            } else {
                this.b = new c();
            }
        } catch (Throwable th2) {
            di.a(th2, "AmapLocationManager", "init 1");
        }
        try {
            this.g = new i(this.z);
        } catch (Throwable th3) {
            di.a(th3, "AmapLocationManager", "init 5");
        }
        this.l = new b("amapLocManagerThread", this);
        this.l.setPriority(5);
        this.l.start();
        this.w = a(this.l.getLooper());
        try {
            this.c = new g(this.z, this.b);
        } catch (Throwable th4) {
            di.a(th4, "AmapLocationManager", "init 3");
        }
        if (this.p == null) {
            this.p = new Cdo();
        }
    }

    private AMapLocationServer a(co coVar) {
        if (this.a.isLocationCacheEnable()) {
            try {
                return coVar.j();
            } catch (Throwable th) {
                di.a(th, "AmapLocationManager", "doFirstCacheLoc");
            }
        }
        return null;
    }

    private a a(Looper looper) {
        a aVar;
        synchronized (this.o) {
            this.w = new a(looper);
            aVar = this.w;
        }
        return aVar;
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        synchronized (this.o) {
            if (this.w != null) {
                this.w.removeMessages(i2);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, Bundle bundle) {
        if (bundle == null) {
            try {
                bundle = new Bundle();
            } catch (Throwable th) {
                Throwable th2 = th;
                boolean z2 = (th2 instanceof IllegalStateException) && th2.getMessage().contains("sending message to a Handler on a dead thread");
                if ((th2 instanceof RemoteException) || z2) {
                    this.h = null;
                    this.A = false;
                }
                di.a(th2, "AmapLocationManager", "sendLocMessage");
                return;
            }
        }
        if (TextUtils.isEmpty(this.s)) {
            this.s = di.b(this.z);
        }
        bundle.putString("c", this.s);
        Message obtain = Message.obtain();
        obtain.what = i2;
        obtain.setData(bundle);
        obtain.replyTo = this.i;
        if (this.h != null) {
            this.h.send(obtain);
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, Object obj, long j2) {
        synchronized (this.o) {
            if (this.w != null) {
                Message obtain = Message.obtain();
                obtain.what = i2;
                if (obj instanceof Bundle) {
                    obtain.setData((Bundle) obj);
                } else {
                    obtain.obj = obj;
                }
                this.w.sendMessageDelayed(obtain, j2);
            }
        }
    }

    private void a(Intent intent, boolean z2) {
        if (this.z != null) {
            if (VERSION.SDK_INT < 26 || !z2) {
                this.z.startService(intent);
            } else {
                try {
                    this.z.getClass().getMethod("startForegroundService", new Class[]{Intent.class}).invoke(this.z, new Object[]{intent});
                } catch (Throwable th) {
                    this.z.startService(intent);
                }
            }
            this.y = true;
        }
    }

    private void a(AMapLocation aMapLocation) {
        try {
            if (aMapLocation.getErrorCode() != 0) {
                aMapLocation.setLocationType(0);
            }
            if (aMapLocation.getErrorCode() == 0) {
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                if ((latitude == Utils.DOUBLE_EPSILON && longitude == Utils.DOUBLE_EPSILON) || latitude < -90.0d || latitude > 90.0d || longitude < -180.0d || longitude > 180.0d) {
                    Cdo.a("errorLatLng", aMapLocation.toStr());
                    aMapLocation.setLocationType(0);
                    aMapLocation.setErrorCode(8);
                    aMapLocation.setLocationDetail("LatLng is error#0802");
                }
            }
            if ("gps".equalsIgnoreCase(aMapLocation.getProvider()) || !this.c.b()) {
                aMapLocation.setAltitude(dr.b(aMapLocation.getAltitude()));
                aMapLocation.setBearing(dr.a(aMapLocation.getBearing()));
                aMapLocation.setSpeed(dr.a(aMapLocation.getSpeed()));
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    try {
                        ((AMapLocationListener) it.next()).onLocationChanged(aMapLocation);
                    } catch (Throwable th) {
                    }
                }
            }
        } catch (Throwable th2) {
        }
    }

    private synchronized void a(AMapLocation aMapLocation, Throwable th, long j2) {
        try {
            if (!di.d() || aMapLocation != null) {
                if (aMapLocation == null) {
                    aMapLocation = new AMapLocation("");
                    aMapLocation.setErrorCode(8);
                    aMapLocation.setLocationDetail("amapLocation is null#0801");
                }
                if (!"gps".equalsIgnoreCase(aMapLocation.getProvider())) {
                    aMapLocation.setProvider("lbs");
                }
                if (this.t == null) {
                    this.t = new AMapLocationQualityReport();
                }
                this.t.setLocationMode(this.a.getLocationMode());
                if (this.c != null) {
                    this.t.setGPSSatellites(this.c.e());
                    this.t.setGpsStatus(this.c.d());
                }
                this.t.setWifiAble(dr.h(this.z));
                this.t.setNetworkType(dr.i(this.z));
                if (aMapLocation.getLocationType() == 1 || "gps".equalsIgnoreCase(aMapLocation.getProvider())) {
                    j2 = 0;
                }
                this.t.setNetUseTime(j2);
                this.t.setInstallHighDangerMockApp(E);
                aMapLocation.setLocationQualityReport(this.t);
                try {
                    if (this.B) {
                        String str = this.x;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("loc", aMapLocation);
                        bundle.putString("lastLocNb", str);
                        a((int) PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW, (Object) bundle, 0);
                        Cdo.a(this.z, aMapLocation);
                        Cdo.b(this.z, aMapLocation);
                        a(aMapLocation.clone());
                    }
                } catch (Throwable th2) {
                    di.a(th2, "AmapLocationManager", "handlerLocation part2");
                }
                if (!this.m || di.d()) {
                    dp.b(this.z);
                    if (this.a.isOnceLocation()) {
                        d();
                    }
                }
            } else if (th != null) {
                dp.a(this.z, "loc", th.getMessage());
            } else {
                dp.a(this.z, "loc", "amaplocation is null");
            }
        } catch (Throwable th3) {
            di.a(th3, "AmapLocationManager", "handlerLocation part3");
        }
        return;
    }

    private static void a(co coVar, AMapLocationServer aMapLocationServer) {
        if (aMapLocationServer != null) {
            try {
                if (aMapLocationServer.getErrorCode() == 0) {
                    coVar.a(aMapLocationServer);
                }
            } catch (Throwable th) {
                di.a(th, "AmapLocationManager", "apsLocation:doFirstAddCache");
            }
        }
    }

    static /* synthetic */ void a(d dVar, Bundle bundle) {
        long j2;
        AMapLocation aMapLocation;
        Throwable th;
        long j3;
        AMapLocation aMapLocation2 = null;
        if (bundle != null) {
            try {
                bundle.setClassLoader(AMapLocation.class.getClassLoader());
                aMapLocation = (AMapLocation) bundle.getParcelable("loc");
                dVar.x = bundle.getString("nb");
                long j4 = bundle.getLong("netUseTime", 0);
                if (!(aMapLocation == null || aMapLocation.getErrorCode() != 0 || dVar.c == null)) {
                    dVar.c.c();
                    if (!TextUtils.isEmpty(aMapLocation.getAdCode())) {
                        dVar.c.y = aMapLocation;
                    }
                }
                j2 = j4;
            } catch (Throwable th2) {
                th = th2;
                j3 = 0;
                di.a(th, "AmapLocationManager", "resultLbsLocationSuccess");
                dVar.a(aMapLocation2, th, j3);
            }
        } else {
            j2 = 0;
            aMapLocation = null;
        }
        try {
            if (dVar.c != null) {
                aMapLocation = dVar.c.a(aMapLocation, dVar.x);
            }
            long j5 = j2;
            th = null;
            aMapLocation2 = aMapLocation;
            j3 = j5;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            j3 = j2;
            th = th4;
            di.a(th, "AmapLocationManager", "resultLbsLocationSuccess");
            dVar.a(aMapLocation2, th, j3);
        }
        dVar.a(aMapLocation2, th, j3);
    }

    static /* synthetic */ void a(d dVar, Message message) {
        try {
            AMapLocation aMapLocation = (AMapLocation) message.obj;
            if (dVar.f && dVar.h != null) {
                Bundle bundle = new Bundle();
                bundle.putBundle("optBundle", di.a(dVar.a));
                dVar.a(0, bundle);
                dVar.f = false;
            }
            dVar.a(aMapLocation, (Throwable) null, 0);
            if (dVar.D) {
                dVar.a(7, (Bundle) null);
            }
            dVar.a((int) InputDeviceCompat.SOURCE_GAMEPAD);
            dVar.a((int) InputDeviceCompat.SOURCE_GAMEPAD, (Object) null, 300000);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "resultGpsLocationSuccess");
        }
    }

    static /* synthetic */ void a(d dVar, AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener == null) {
            throw new IllegalArgumentException("listener参数不能为null");
        }
        if (dVar.d == null) {
            dVar.d = new ArrayList<>();
        }
        if (!dVar.d.contains(aMapLocationListener)) {
            dVar.d.add(aMapLocationListener);
        }
    }

    private AMapLocationServer b(co coVar) {
        Throwable th;
        AMapLocationServer aMapLocationServer;
        dn dnVar;
        boolean z2;
        long j2;
        AMapLocation aMapLocation;
        boolean z3 = false;
        String str = null;
        try {
            dnVar = new dn();
            dnVar.a(dr.c());
            String apikey = AMapLocationClientOption.getAPIKEY();
            if (!TextUtils.isEmpty(apikey)) {
                m.a(this.z, apikey);
            }
        } catch (Throwable th2) {
            th = th2;
            aMapLocationServer = null;
        }
        try {
            String umidtoken = UmidtokenInfo.getUmidtoken();
            if (!TextUtils.isEmpty(umidtoken)) {
                p.a(umidtoken);
            }
        } catch (Throwable th3) {
            di.a(th3, "AmapLocationManager", "apsLocation setUmidToken");
        }
        try {
            coVar.a(this.z);
            coVar.a(this.a);
            Context context = this.z;
            coVar.i();
        } catch (Throwable th4) {
            di.a(th4, "AmapLocationManager", "initApsBase");
        }
        long j3 = 0;
        boolean w2 = dh.w();
        AMapLocationServer a2 = a(coVar);
        if (a2 == null) {
            if (!w2) {
                z3 = true;
            }
            try {
                a2 = coVar.a(z3);
                if (a2 != null) {
                    j3 = a2.k();
                }
                if (!w2) {
                    a(coVar, a2);
                }
                j2 = j3;
                z2 = true;
            } catch (Throwable th5) {
                Throwable th6 = th5;
                aMapLocationServer = a2;
                th = th6;
                try {
                    di.a(th, "AmapLocationManager", "apsLocation");
                    if (coVar == null) {
                        return aMapLocationServer;
                    }
                    try {
                        coVar.f();
                        return aMapLocationServer;
                    } catch (Throwable th7) {
                        return aMapLocationServer;
                    }
                } catch (Throwable th8) {
                }
            }
        } else {
            z2 = false;
            j2 = 0;
        }
        dnVar.b(dr.c());
        dnVar.a(a2);
        if (a2 != null) {
            str = a2.l();
            aMapLocation = a2.clone();
        } else {
            aMapLocation = null;
        }
        try {
            if (this.a.isLocationCacheEnable() && this.g != null) {
                aMapLocation = this.g.a(aMapLocation, str, this.a.getLastLocationLifeCycle());
            }
        } catch (Throwable th9) {
            di.a(th9, "AmapLocationManager", "fixLastLocation");
        }
        try {
            Bundle bundle = new Bundle();
            if (aMapLocation != null) {
                bundle.putParcelable("loc", aMapLocation);
                bundle.putString("nb", a2.l());
                bundle.putLong("netUseTime", j2);
            }
            Message obtain = Message.obtain();
            obtain.setData(bundle);
            obtain.what = 1;
            this.b.sendMessage(obtain);
        } catch (Throwable th10) {
            di.a(th10, "AmapLocationManager", "apsLocation:callback");
        }
        Cdo.a(this.z, dnVar);
        if (z2 && w2) {
            try {
                coVar.c();
                a(coVar, coVar.a(true));
            } catch (Throwable th11) {
                di.a(th11, "AmapLocationManager", "apsLocation:doFirstNetLocate 2");
            }
        }
        if (coVar != null) {
            try {
                coVar.f();
            } catch (Throwable th12) {
                return a2;
            }
        }
        return a2;
        throw th;
    }

    static /* synthetic */ void b(d dVar, Message message) {
        try {
            Bundle data = message.getData();
            AMapLocation aMapLocation = (AMapLocation) data.getParcelable("loc");
            String string = data.getString("lastLocNb");
            if (aMapLocation != null) {
                AMapLocation aMapLocation2 = null;
                try {
                    if (i.b != null) {
                        aMapLocation2 = i.b.a();
                    } else if (dVar.g != null) {
                        aMapLocation2 = dVar.g.b();
                    }
                    Cdo.a(aMapLocation2, aMapLocation);
                } catch (Throwable th) {
                }
            }
            if (dVar.g.a(aMapLocation, string)) {
                dVar.g.d();
            }
        } catch (Throwable th2) {
            di.a(th2, "AmapLocationManager", "doSaveLastLocation");
        }
    }

    static /* synthetic */ void b(d dVar, AMapLocationListener aMapLocationListener) {
        if (!dVar.d.isEmpty() && dVar.d.contains(aMapLocationListener)) {
            dVar.d.remove(aMapLocationListener);
        }
        if (dVar.d.isEmpty()) {
            dVar.d();
        }
    }

    private boolean b() {
        boolean z2 = true;
        int i2 = 0;
        do {
            try {
                if (this.h != null) {
                    break;
                }
                Thread.sleep(100);
                i2++;
            } catch (Throwable th) {
                di.a(th, "AmapLocationManager", "checkAPSManager");
                z2 = false;
            }
        } while (i2 < 50);
        if (this.h == null) {
            Message obtain = Message.obtain();
            Bundle bundle = new Bundle();
            AMapLocation aMapLocation = new AMapLocation("");
            aMapLocation.setErrorCode(10);
            if (!dr.l(this.z.getApplicationContext())) {
                aMapLocation.setLocationDetail("请检查配置文件是否配置服务，并且manifest中service标签是否配置在application标签内#1003");
            } else {
                aMapLocation.setLocationDetail("启动ApsServcie失败#1001");
            }
            bundle.putParcelable("loc", aMapLocation);
            obtain.setData(bundle);
            obtain.what = 1;
            this.b.sendMessage(obtain);
            z2 = false;
        }
        if (!z2) {
            if (!dr.l(this.z.getApplicationContext())) {
                Cdo.a((String) null, 2103);
            } else {
                Cdo.a((String) null, 2101);
            }
        }
        return z2;
    }

    /* access modifiers changed from: private */
    public synchronized void c() {
        long j2 = 0;
        synchronized (this) {
            if (this.a == null) {
                this.a = new AMapLocationClientOption();
            }
            if (!this.B) {
                this.B = true;
                switch (this.a.getLocationMode()) {
                    case Battery_Saving:
                        a((int) PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, (Object) null, 0);
                        a((int) PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, (Object) null, 0);
                        break;
                    case Device_Sensors:
                        a((int) PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW);
                        a((int) PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW, (Object) null, 0);
                        break;
                    case Hight_Accuracy:
                        a((int) PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW, (Object) null, 0);
                        if (this.a.isGpsFirst() && this.a.isOnceLocation()) {
                            j2 = this.a.getGpsFirstTimeout();
                        }
                        a((int) PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, (Object) null, j2);
                        break;
                }
            }
        }
    }

    static /* synthetic */ void c(d dVar, Message message) {
        if (message != null) {
            try {
                Bundle data = message.getData();
                if (data != null) {
                    int i2 = data.getInt("i", 0);
                    Notification notification = (Notification) data.getParcelable("h");
                    Intent g2 = dVar.g();
                    g2.putExtra("i", i2);
                    g2.putExtra("h", notification);
                    g2.putExtra("g", 1);
                    dVar.a(g2, true);
                }
            } catch (Throwable th) {
                di.a(th, "AmapLocationManager", "doEnableBackgroundLocation");
            }
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        try {
            a((int) InputDeviceCompat.SOURCE_GAMEPAD);
            if (this.c != null) {
                this.c.a();
            }
            a((int) PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW);
            this.B = false;
            this.k = 0;
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "stopLocation");
        }
    }

    static /* synthetic */ void d(d dVar) {
        try {
            if (dVar.C) {
                dVar.C = false;
                AMapLocationServer b2 = dVar.b(new co());
                if (dVar.b()) {
                    Bundle bundle = new Bundle();
                    String str = "0";
                    if (b2 != null && (b2.getLocationType() == 2 || b2.getLocationType() == 4)) {
                        str = "1";
                    }
                    bundle.putBundle("optBundle", di.a(dVar.a));
                    bundle.putString("isCacheLoc", str);
                    dVar.a(0, bundle);
                }
            } else {
                try {
                    if (dVar.q && !dVar.isStarted() && !dVar.v) {
                        dVar.v = true;
                        dVar.f();
                    }
                } catch (Throwable th) {
                    dVar.v = true;
                    di.a(th, "AmapLocationManager", "doLBSLocation reStartService");
                }
                if (dVar.b()) {
                    dVar.v = false;
                    Bundle bundle2 = new Bundle();
                    bundle2.putBundle("optBundle", di.a(dVar.a));
                    bundle2.putString("d", UmidtokenInfo.getUmidtoken());
                    if (!dVar.c.b()) {
                        dVar.a(1, bundle2);
                    }
                }
            }
            try {
                if (!dVar.a.isOnceLocation()) {
                    dVar.e();
                }
            } catch (Throwable th2) {
            }
        } catch (Throwable th3) {
        }
    }

    static /* synthetic */ void d(d dVar, Message message) {
        if (message != null) {
            try {
                Bundle data = message.getData();
                if (data != null) {
                    boolean z2 = data.getBoolean("j", true);
                    Intent g2 = dVar.g();
                    g2.putExtra("j", z2);
                    g2.putExtra("g", 2);
                    dVar.a(g2, false);
                }
            } catch (Throwable th) {
                di.a(th, "AmapLocationManager", "doDisableBackgroundLocation");
            }
        }
    }

    private void e() {
        long j2 = 1000;
        if (this.a.getLocationMode() != AMapLocationMode.Device_Sensors) {
            if (this.a.getInterval() >= 1000) {
                j2 = this.a.getInterval();
            }
            a((int) PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, (Object) null, j2);
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        try {
            if (this.i == null) {
                this.i = new Messenger(this.b);
            }
            this.z.bindService(g(), this.G, 1);
        } catch (Throwable th) {
        }
    }

    static /* synthetic */ void f(d dVar) {
        dVar.c.b(dVar.a);
        if (dVar.B && !dVar.a.getLocationMode().equals(dVar.n)) {
            dVar.d();
            dVar.c();
        }
        dVar.n = dVar.a.getLocationMode();
        if (dVar.p != null) {
            if (dVar.a.isOnceLocation()) {
                dVar.p.a(dVar.z, 0);
            } else {
                dVar.p.a(dVar.z, 1);
            }
            dVar.p.a(dVar.z, dVar.a);
        }
    }

    private Intent g() {
        if (this.j == null) {
            this.j = new Intent(this.z, APSService.class);
        }
        String str = "";
        try {
            str = !TextUtils.isEmpty(AMapLocationClientOption.getAPIKEY()) ? AMapLocationClientOption.getAPIKEY() : l.f(this.z);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "startServiceImpl p2");
        }
        this.j.putExtra("a", str);
        this.j.putExtra("b", l.c(this.z));
        this.j.putExtra("d", UmidtokenInfo.getUmidtoken());
        this.j.putExtra("f", AMapLocationClientOption.isDownloadCoordinateConvertLibrary());
        return this.j;
    }

    static /* synthetic */ void g(d dVar) {
        try {
            if (dVar.h != null) {
                dVar.k = 0;
                Bundle bundle = new Bundle();
                bundle.putBundle("optBundle", di.a(dVar.a));
                dVar.a(2, bundle);
                return;
            }
            dVar.k++;
            if (dVar.k < 10) {
                dVar.a((int) PointerIconCompat.TYPE_TEXT, (Object) null, 50);
            }
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "startAssistantLocationImpl");
        }
    }

    static /* synthetic */ void h(d dVar) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBundle("optBundle", di.a(dVar.a));
            dVar.a(3, bundle);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "stopAssistantLocationImpl");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        a(12, (Bundle) null);
        this.C = true;
        this.f = true;
        this.A = false;
        this.q = false;
        d();
        if (this.p != null) {
            this.p.b(this.z);
        }
        Cdo.a(this.z);
        if (this.r != null) {
            this.r.b().sendEmptyMessage(11);
        } else if (this.G != null) {
            this.z.unbindService(this.G);
        }
        try {
            if (this.y) {
                this.z.stopService(g());
            }
        } catch (Throwable th) {
        }
        this.y = false;
        if (this.d != null) {
            this.d.clear();
            this.d = null;
        }
        this.G = null;
        synchronized (this.o) {
            if (this.w != null) {
                this.w.removeCallbacksAndMessages(null);
            }
            this.w = null;
        }
        if (this.l != null) {
            if (VERSION.SDK_INT >= 18) {
                try {
                    dm.a((Object) this.l, HandlerThread.class, "quitSafely", new Object[0]);
                } catch (Throwable th2) {
                    this.l.quit();
                }
            } else {
                this.l.quit();
            }
        }
        this.l = null;
        if (this.b != null) {
            this.b.removeCallbacksAndMessages(null);
        }
        if (this.g != null) {
            this.g.c();
            this.g = null;
        }
    }

    public void disableBackgroundLocation(boolean z2) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("j", z2);
            a(1024, (Object) bundle, 0);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "disableBackgroundLocation");
        }
    }

    public void enableBackgroundLocation(int i2, Notification notification) {
        if (i2 != 0 && notification != null) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt("i", i2);
                bundle.putParcelable("h", notification);
                a(1023, (Object) bundle, 0);
            } catch (Throwable th) {
                di.a(th, "AmapLocationManager", "disableBackgroundLocation");
            }
        }
    }

    public AMapLocation getLastKnownLocation() {
        AMapLocation aMapLocation = null;
        try {
            if (this.g != null) {
                aMapLocation = this.g.b();
                if (aMapLocation != null) {
                    aMapLocation.setTrustedLevel(3);
                }
            }
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "getLastKnownLocation");
        }
        return aMapLocation;
    }

    public boolean isStarted() {
        return this.A;
    }

    public void onDestroy() {
        try {
            if (this.F != null) {
                this.F.b();
                this.F = null;
            }
            a(1011, (Object) null, 0);
            this.m = true;
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "onDestroy");
        }
    }

    public void setLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            a(1002, (Object) aMapLocationListener, 0);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "setLocationListener");
        }
    }

    public void setLocationOption(AMapLocationClientOption aMapLocationClientOption) {
        try {
            a((int) PointerIconCompat.TYPE_ZOOM_IN, (Object) aMapLocationClientOption.clone(), 0);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "setLocationOption");
        }
    }

    public void startAssistantLocation() {
        try {
            a((int) PointerIconCompat.TYPE_TEXT, (Object) null, 0);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "startAssistantLocation");
        }
    }

    public void startAssistantLocation(WebView webView) {
        if (this.F == null) {
            this.F = new h(this.z, webView);
        }
        this.F.a();
    }

    public void startLocation() {
        try {
            a((int) PointerIconCompat.TYPE_HELP, (Object) null, 0);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "startLocation");
        }
    }

    public void stopAssistantLocation() {
        try {
            if (this.F != null) {
                this.F.b();
                this.F = null;
            }
            a((int) PointerIconCompat.TYPE_VERTICAL_TEXT, (Object) null, 0);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "stopAssistantLocation");
        }
    }

    public void stopLocation() {
        try {
            a(1004, (Object) null, 0);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "stopLocation");
        }
    }

    public void unRegisterLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            a(1005, (Object) aMapLocationListener, 0);
        } catch (Throwable th) {
            di.a(th, "AmapLocationManager", "unRegisterLocationListener");
        }
    }
}
