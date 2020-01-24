package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* compiled from: ApsManager */
public final class e {
    static boolean g = false;
    /* access modifiers changed from: private */
    public i A = null;
    private boolean B = true;
    private String C = "";
    private final int D = 5000;
    private String E = "jsonp1";
    String a = null;
    b b = null;
    AMapLocation c = null;
    a d = null;
    Context e = null;
    co f = null;
    HashMap<Messenger, Long> h = new HashMap<>();
    Cdo i = null;
    long j = 0;
    long k = 0;
    String l = null;
    AMapLocationClientOption m = null;
    AMapLocationClientOption n = new AMapLocationClientOption();
    ServerSocket o = null;
    boolean p = false;
    Socket q = null;
    boolean r = false;
    c s = null;
    private boolean t = false;
    private boolean u = false;
    private long v = 0;
    private long w = 0;
    private AMapLocationServer x = null;
    private long y = 0;
    private int z = 0;

    /* compiled from: ApsManager */
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: Removed duplicated region for block: B:23:0x005b A[SYNTHETIC, Splitter:B:23:0x005b] */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0066 A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0071 A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x0085 A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0099 A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00a4 A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x00af A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x00ba A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x00c5 A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x00d1 A[Catch:{ Throwable -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x00d8 A[Catch:{ Throwable -> 0x0050 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r6) {
            /*
                r5 = this;
                r2 = 0
                android.os.Bundle r1 = r6.getData()     // Catch:{ Throwable -> 0x003c }
                android.os.Messenger r2 = r6.replyTo     // Catch:{ Throwable -> 0x00df }
                if (r1 == 0) goto L_0x0047
                boolean r0 = r1.isEmpty()     // Catch:{ Throwable -> 0x00df }
                if (r0 != 0) goto L_0x0047
                java.lang.String r0 = "c"
                java.lang.String r0 = r1.getString(r0)     // Catch:{ Throwable -> 0x00df }
                com.loc.e r3 = com.loc.e.this     // Catch:{ Throwable -> 0x00df }
                boolean r0 = r3.a(r0)     // Catch:{ Throwable -> 0x00df }
                if (r0 != 0) goto L_0x0047
                int r0 = r6.what     // Catch:{ Throwable -> 0x00df }
                r3 = 1
                if (r0 != r3) goto L_0x003b
                r0 = 0
                r3 = 2102(0x836, float:2.946E-42)
                com.loc.Cdo.a(r0, r3)     // Catch:{ Throwable -> 0x00df }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x00df }
                java.lang.String r0 = "invalid handlder scode!!!#1002"
                com.autonavi.aps.amapapi.model.AMapLocationServer r0 = com.loc.e.a(10, r0)     // Catch:{ Throwable -> 0x00df }
                com.loc.e r3 = com.loc.e.this     // Catch:{ Throwable -> 0x00df }
                java.lang.String r4 = r0.l()     // Catch:{ Throwable -> 0x00df }
                r3.a(r2, r0, r4, 0)     // Catch:{ Throwable -> 0x00df }
            L_0x003b:
                return
            L_0x003c:
                r0 = move-exception
                r1 = r2
            L_0x003e:
                java.lang.String r3 = "ApsServiceCore"
                java.lang.String r4 = "ActionHandler handlerMessage"
                com.loc.di.a(r0, r3, r4)     // Catch:{ Throwable -> 0x0050 }
            L_0x0047:
                int r0 = r6.what     // Catch:{ Throwable -> 0x0050 }
                switch(r0) {
                    case 0: goto L_0x005b;
                    case 1: goto L_0x0066;
                    case 2: goto L_0x0071;
                    case 3: goto L_0x0085;
                    case 4: goto L_0x0099;
                    case 5: goto L_0x00a4;
                    case 6: goto L_0x004c;
                    case 7: goto L_0x00af;
                    case 8: goto L_0x004c;
                    case 9: goto L_0x00ba;
                    case 10: goto L_0x00c5;
                    case 11: goto L_0x00d1;
                    case 12: goto L_0x00d8;
                    default: goto L_0x004c;
                }     // Catch:{ Throwable -> 0x0050 }
            L_0x004c:
                super.handleMessage(r6)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x003b
            L_0x0050:
                r0 = move-exception
                java.lang.String r1 = "actionHandler"
                java.lang.String r2 = "handleMessage"
                com.loc.di.a(r0, r1, r2)
                goto L_0x003b
            L_0x005b:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                com.loc.e.a(r0, r2, r1)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x0066:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                com.loc.e.b(r0, r2, r1)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x0071:
                if (r1 == 0) goto L_0x003b
                boolean r0 = r1.isEmpty()     // Catch:{ Throwable -> 0x0050 }
                if (r0 != 0) goto L_0x003b
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r1 = 0
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.c()     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x0085:
                if (r1 == 0) goto L_0x003b
                boolean r0 = r1.isEmpty()     // Catch:{ Throwable -> 0x0050 }
                if (r0 != 0) goto L_0x003b
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r1 = 0
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.d()     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x0099:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                com.loc.e.a(r0)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x00a4:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                com.loc.e.b(r0)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x00af:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                com.loc.e.c(r0)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x00ba:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                com.loc.e.a(r0, r2)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x00c5:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.a(r1)     // Catch:{ Throwable -> 0x0050 }
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.a(r2, r1)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x00d1:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.e()     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x00d8:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0050 }
                r0.h.remove(r2)     // Catch:{ Throwable -> 0x0050 }
                goto L_0x004c
            L_0x00df:
                r0 = move-exception
                goto L_0x003e
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.e.a.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: ApsManager */
    class b extends HandlerThread {
        public b(String str) {
            super(str);
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onLooperPrepared() {
            /*
                r3 = this;
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x001b }
                com.loc.i r1 = new com.loc.i     // Catch:{ Throwable -> 0x001b }
                com.loc.e r2 = com.loc.e.this     // Catch:{ Throwable -> 0x001b }
                android.content.Context r2 = r2.e     // Catch:{ Throwable -> 0x001b }
                r1.<init>(r2)     // Catch:{ Throwable -> 0x001b }
                r0.A = r1     // Catch:{ Throwable -> 0x001b }
            L_0x000e:
                com.loc.e r0 = com.loc.e.this     // Catch:{ Throwable -> 0x0026 }
                com.loc.co r1 = new com.loc.co     // Catch:{ Throwable -> 0x0026 }
                r1.<init>()     // Catch:{ Throwable -> 0x0026 }
                r0.f = r1     // Catch:{ Throwable -> 0x0026 }
                super.onLooperPrepared()     // Catch:{ Throwable -> 0x0026 }
            L_0x001a:
                return
            L_0x001b:
                r0 = move-exception
                java.lang.String r1 = "APSManager$ActionThread"
                java.lang.String r2 = "init 2"
                com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x0026 }
                goto L_0x000e
            L_0x0026:
                r0 = move-exception
                java.lang.String r1 = "APSManager$ActionThread"
                java.lang.String r2 = "onLooperPrepared"
                com.loc.di.a(r0, r1, r2)
                goto L_0x001a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.e.b.onLooperPrepared():void");
        }

        public final void run() {
            try {
                super.run();
            } catch (Throwable th) {
                di.a(th, "APSManager$ActionThread", "run");
            }
        }
    }

    /* compiled from: ApsManager */
    class c extends Thread {
        c() {
        }

        public final void run() {
            try {
                if (!e.this.p) {
                    e.this.p = true;
                    e.this.o = new ServerSocket(43689);
                }
                while (e.this.p && e.this.o != null) {
                    e.this.q = e.this.o.accept();
                    e.a(e.this, e.this.q);
                }
            } catch (Throwable th) {
                di.a(th, "ApsServiceCore", "run");
            }
            super.run();
        }
    }

    public e(Context context) {
        this.e = context;
    }

    /* access modifiers changed from: private */
    public static AMapLocationServer a(int i2, String str) {
        try {
            AMapLocationServer aMapLocationServer = new AMapLocationServer("");
            aMapLocationServer.setErrorCode(i2);
            aMapLocationServer.setLocationDetail(str);
            return aMapLocationServer;
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "newInstanceAMapLoc");
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void a(Bundle bundle) {
        try {
            if (!this.t) {
                di.a(this.e);
                if (bundle != null) {
                    this.n = di.a(bundle.getBundle("optBundle"));
                }
                this.f.a(this.e);
                this.f.a();
                a(this.n);
                this.f.b();
                this.t = true;
                this.B = true;
                this.C = "";
            }
        } catch (Throwable th) {
            this.B = false;
            this.C = th.getMessage();
            di.a(th, "ApsServiceCore", "init");
        }
    }

    private void a(Messenger messenger) {
        try {
            co coVar = this.f;
            co.b(this.e);
            if (dh.k()) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("ngpsAble", dh.m());
                a(messenger, 7, bundle);
                dh.l();
            }
            if (dh.u()) {
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("installMockApp", true);
                a(messenger, 9, bundle2);
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "initAuth");
        }
    }

    private static void a(Messenger messenger, int i2, Bundle bundle) {
        if (messenger != null) {
            try {
                Message obtain = Message.obtain();
                obtain.setData(bundle);
                obtain.what = i2;
                messenger.send(obtain);
            } catch (Throwable th) {
                di.a(th, "ApsServiceCore", "sendMessage");
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(Messenger messenger, AMapLocation aMapLocation, String str, long j2) {
        Bundle bundle = new Bundle();
        bundle.setClassLoader(AMapLocation.class.getClassLoader());
        bundle.putParcelable("loc", aMapLocation);
        bundle.putString("nb", str);
        bundle.putLong("netUseTime", j2);
        this.h.put(messenger, Long.valueOf(dr.c()));
        a(messenger, 1, bundle);
    }

    private void a(AMapLocationClientOption aMapLocationClientOption) {
        try {
            if (this.f != null) {
                this.f.a(aMapLocationClientOption);
            }
            if (aMapLocationClientOption != null) {
                g = aMapLocationClientOption.isKillProcess();
                if (!(this.m == null || (aMapLocationClientOption.isOffset() == this.m.isOffset() && aMapLocationClientOption.isNeedAddress() == this.m.isNeedAddress() && aMapLocationClientOption.isLocationCacheEnable() == this.m.isLocationCacheEnable() && this.m.getGeoLanguage() == aMapLocationClientOption.getGeoLanguage()))) {
                    this.w = 0;
                    this.c = null;
                }
                this.m = aMapLocationClientOption;
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "setExtra");
        }
    }

    static /* synthetic */ void a(e eVar) {
        try {
            if (eVar.z < dh.b()) {
                eVar.z++;
                eVar.f.e();
                eVar.d.sendEmptyMessageDelayed(4, 2000);
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "doGpsFusion");
        }
    }

    static /* synthetic */ void a(e eVar, Messenger messenger) {
        try {
            eVar.a(messenger);
            dh.e(eVar.e);
            try {
                co coVar = eVar.f;
                Context context = eVar.e;
                coVar.h();
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            di.a(th2, "ApsServiceCore", "doCallOtherSer");
        }
    }

    static /* synthetic */ void a(e eVar, Messenger messenger, Bundle bundle) {
        if (bundle != null) {
            try {
                if (!bundle.isEmpty() && !eVar.u) {
                    eVar.u = true;
                    eVar.a(messenger);
                    dh.e(eVar.e);
                    try {
                        co coVar = eVar.f;
                        Context context = eVar.e;
                        coVar.g();
                    } catch (Throwable th) {
                    }
                    eVar.g();
                    if (dh.a(eVar.y) && "1".equals(bundle.getString("isCacheLoc"))) {
                        eVar.y = dr.c();
                        eVar.f.e();
                    }
                    eVar.i();
                }
            } catch (Throwable th2) {
                di.a(th2, "ApsServiceCore", "doInitAuth");
            }
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:92:0x0130=Splitter:B:92:0x0130, B:75:0x0101=Splitter:B:75:0x0101, B:33:0x0063=Splitter:B:33:0x0063, B:64:0x00e8=Splitter:B:64:0x00e8} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.loc.e r6, java.net.Socket r7) {
        /*
            r2 = 0
            if (r7 != 0) goto L_0x0004
        L_0x0003:
            return
        L_0x0004:
            int r3 = com.loc.di.f     // Catch:{ Throwable -> 0x0035 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x006f, all -> 0x00f4 }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x006f, all -> 0x00f4 }
            java.io.InputStream r4 = r7.getInputStream()     // Catch:{ Throwable -> 0x006f, all -> 0x00f4 }
            java.lang.String r5 = "UTF-8"
            r0.<init>(r4, r5)     // Catch:{ Throwable -> 0x006f, all -> 0x00f4 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x006f, all -> 0x00f4 }
            r6.a(r1)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r0 = r6.h()     // Catch:{ Throwable -> 0x013e }
            com.loc.di.f = r3     // Catch:{ Throwable -> 0x0035 }
            r6.c(r0)     // Catch:{ Throwable -> 0x0040 }
            r1.close()     // Catch:{ Throwable -> 0x002a }
            r7.close()     // Catch:{ Throwable -> 0x002a }
            goto L_0x0003
        L_0x002a:
            r0 = move-exception
            java.lang.String r1 = "ApsServiceCore"
            java.lang.String r2 = "invokeSocketLocation part3"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0003
        L_0x0035:
            r0 = move-exception
            java.lang.String r1 = "ApsServiceCore"
            java.lang.String r2 = "invokeSocketLocation part4"
            com.loc.di.a(r0, r1, r2)
            goto L_0x0003
        L_0x0040:
            r0 = move-exception
            java.lang.String r2 = "ApsServiceCore"
            java.lang.String r3 = "invokeSocketLocation part2"
            com.loc.di.a(r0, r2, r3)     // Catch:{ all -> 0x005c }
            r1.close()     // Catch:{ Throwable -> 0x0051 }
            r7.close()     // Catch:{ Throwable -> 0x0051 }
            goto L_0x0003
        L_0x0051:
            r0 = move-exception
            java.lang.String r1 = "ApsServiceCore"
            java.lang.String r2 = "invokeSocketLocation part3"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0003
        L_0x005c:
            r0 = move-exception
            r1.close()     // Catch:{ Throwable -> 0x0064 }
            r7.close()     // Catch:{ Throwable -> 0x0064 }
        L_0x0063:
            throw r0     // Catch:{ Throwable -> 0x0035 }
        L_0x0064:
            r1 = move-exception
            java.lang.String r2 = "ApsServiceCore"
            java.lang.String r3 = "invokeSocketLocation part3"
            com.loc.di.a(r1, r2, r3)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0063
        L_0x006f:
            r0 = move-exception
            r1 = r2
        L_0x0071:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x013c }
            r4.<init>()     // Catch:{ all -> 0x013c }
            java.lang.String r5 = r6.E     // Catch:{ all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.String r5 = "&&"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.String r5 = r6.E     // Catch:{ all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.String r5 = "({'package':'"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.String r5 = r6.a     // Catch:{ all -> 0x013c }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.String r5 = "','error_code':1,'error':'params error'})"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x013c }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x013c }
            java.lang.String r4 = "ApsServiceCore"
            java.lang.String r5 = "invokeSocketLocation"
            com.loc.di.a(r0, r4, r5)     // Catch:{ all -> 0x013c }
            com.loc.di.f = r3     // Catch:{ Throwable -> 0x0035 }
            r6.c(r2)     // Catch:{ Throwable -> 0x00c3 }
            r1.close()     // Catch:{ Throwable -> 0x00b7 }
            r7.close()     // Catch:{ Throwable -> 0x00b7 }
            goto L_0x0003
        L_0x00b7:
            r0 = move-exception
            java.lang.String r1 = "ApsServiceCore"
            java.lang.String r2 = "invokeSocketLocation part3"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0003
        L_0x00c3:
            r0 = move-exception
            java.lang.String r2 = "ApsServiceCore"
            java.lang.String r3 = "invokeSocketLocation part2"
            com.loc.di.a(r0, r2, r3)     // Catch:{ all -> 0x00e1 }
            r1.close()     // Catch:{ Throwable -> 0x00d5 }
            r7.close()     // Catch:{ Throwable -> 0x00d5 }
            goto L_0x0003
        L_0x00d5:
            r0 = move-exception
            java.lang.String r1 = "ApsServiceCore"
            java.lang.String r2 = "invokeSocketLocation part3"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0003
        L_0x00e1:
            r0 = move-exception
            r1.close()     // Catch:{ Throwable -> 0x00e9 }
            r7.close()     // Catch:{ Throwable -> 0x00e9 }
        L_0x00e8:
            throw r0     // Catch:{ Throwable -> 0x0035 }
        L_0x00e9:
            r1 = move-exception
            java.lang.String r2 = "ApsServiceCore"
            java.lang.String r3 = "invokeSocketLocation part3"
            com.loc.di.a(r1, r2, r3)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x00e8
        L_0x00f4:
            r0 = move-exception
            r1 = r2
        L_0x00f6:
            com.loc.di.f = r3     // Catch:{ Throwable -> 0x0035 }
            r6.c(r2)     // Catch:{ Throwable -> 0x010d }
            r1.close()     // Catch:{ Throwable -> 0x0102 }
            r7.close()     // Catch:{ Throwable -> 0x0102 }
        L_0x0101:
            throw r0     // Catch:{ Throwable -> 0x0035 }
        L_0x0102:
            r1 = move-exception
            java.lang.String r2 = "ApsServiceCore"
            java.lang.String r3 = "invokeSocketLocation part3"
            com.loc.di.a(r1, r2, r3)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0101
        L_0x010d:
            r2 = move-exception
            java.lang.String r3 = "ApsServiceCore"
            java.lang.String r4 = "invokeSocketLocation part2"
            com.loc.di.a(r2, r3, r4)     // Catch:{ all -> 0x0129 }
            r1.close()     // Catch:{ Throwable -> 0x011e }
            r7.close()     // Catch:{ Throwable -> 0x011e }
            goto L_0x0101
        L_0x011e:
            r1 = move-exception
            java.lang.String r2 = "ApsServiceCore"
            java.lang.String r3 = "invokeSocketLocation part3"
            com.loc.di.a(r1, r2, r3)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0101
        L_0x0129:
            r0 = move-exception
            r1.close()     // Catch:{ Throwable -> 0x0131 }
            r7.close()     // Catch:{ Throwable -> 0x0131 }
        L_0x0130:
            throw r0     // Catch:{ Throwable -> 0x0035 }
        L_0x0131:
            r1 = move-exception
            java.lang.String r2 = "ApsServiceCore"
            java.lang.String r3 = "invokeSocketLocation part3"
            com.loc.di.a(r1, r2, r3)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0130
        L_0x013c:
            r0 = move-exception
            goto L_0x00f6
        L_0x013e:
            r0 = move-exception
            goto L_0x0071
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.e.a(com.loc.e, java.net.Socket):void");
    }

    private void a(BufferedReader bufferedReader) throws Exception {
        String readLine = bufferedReader.readLine();
        int i2 = com.tencent.bugly.BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
        if (readLine != null && readLine.length() > 0) {
            String[] split = readLine.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            if (split != null && split.length > 1) {
                String[] split2 = split[1].split("\\?");
                if (split2 != null && split2.length > 1) {
                    String[] split3 = split2[1].split("&");
                    if (split3 != null && split3.length > 0) {
                        int i3 = 30000;
                        for (String split4 : split3) {
                            String[] split5 = split4.split("=");
                            if (split5 != null && split5.length > 1) {
                                if ("to".equals(split5[0])) {
                                    i3 = dr.h(split5[1]);
                                }
                                if ("callback".equals(split5[0])) {
                                    this.E = split5[1];
                                }
                            }
                        }
                        i2 = i3;
                    }
                }
            }
        }
        di.f = i2;
    }

    private AMapLocationClientOption b(Bundle bundle) {
        AMapLocationClientOption a2 = di.a(bundle.getBundle("optBundle"));
        a(a2);
        try {
            String string = bundle.getString("d");
            if (!TextUtils.isEmpty(string)) {
                p.a(string);
            }
        } catch (Throwable th) {
            di.a(th, "APSManager", "parseBundle");
        }
        return a2;
    }

    static /* synthetic */ void b(e eVar) {
        try {
            if (dh.e()) {
                eVar.f.e();
            } else if (!dr.e(eVar.e)) {
                eVar.f.e();
            }
            eVar.d.sendEmptyMessageDelayed(5, (long) (dh.d() * 1000));
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "doOffFusion");
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void b(com.loc.e r11, android.os.Messenger r12, android.os.Bundle r13) {
        /*
            r10 = 9
            r2 = 0
            r1 = 0
            if (r13 == 0) goto L_0x000d
            boolean r0 = r13.isEmpty()     // Catch:{ Throwable -> 0x0073 }
            if (r0 == 0) goto L_0x000e
        L_0x000d:
            return
        L_0x000e:
            com.amap.api.location.AMapLocationClientOption r6 = r11.b(r13)     // Catch:{ Throwable -> 0x0073 }
            java.util.HashMap<android.os.Messenger, java.lang.Long> r0 = r11.h     // Catch:{ Throwable -> 0x0073 }
            boolean r0 = r0.containsKey(r12)     // Catch:{ Throwable -> 0x0073 }
            if (r0 == 0) goto L_0x0038
            boolean r0 = r6.isOnceLocation()     // Catch:{ Throwable -> 0x0073 }
            if (r0 != 0) goto L_0x0038
            java.util.HashMap<android.os.Messenger, java.lang.Long> r0 = r11.h     // Catch:{ Throwable -> 0x0073 }
            java.lang.Object r0 = r0.get(r12)     // Catch:{ Throwable -> 0x0073 }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ Throwable -> 0x0073 }
            long r4 = r0.longValue()     // Catch:{ Throwable -> 0x0073 }
            long r8 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0073 }
            long r4 = r8 - r4
            r8 = 800(0x320, double:3.953E-321)
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 < 0) goto L_0x000d
        L_0x0038:
            boolean r0 = r11.B     // Catch:{ Throwable -> 0x0073 }
            if (r0 != 0) goto L_0x007e
            r0 = 9
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r2 = "init error : "
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r2 = r11.C     // Catch:{ Throwable -> 0x0073 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r2 = "#0901"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0073 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = a(r0, r1)     // Catch:{ Throwable -> 0x0073 }
            r11.x = r0     // Catch:{ Throwable -> 0x0073 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r2 = r11.x     // Catch:{ Throwable -> 0x0073 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r3 = r0.l()     // Catch:{ Throwable -> 0x0073 }
            r4 = 0
            r0 = r11
            r1 = r12
            r0.a(r1, r2, r3, r4)     // Catch:{ Throwable -> 0x0073 }
            r0 = 0
            r1 = 2091(0x82b, float:2.93E-42)
            com.loc.Cdo.a(r0, r1)     // Catch:{ Throwable -> 0x0073 }
            goto L_0x000d
        L_0x0073:
            r0 = move-exception
            java.lang.String r1 = "ApsServiceCore"
            java.lang.String r2 = "doLocation"
            com.loc.di.a(r0, r1, r2)
            goto L_0x000d
        L_0x007e:
            long r4 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0073 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            boolean r0 = com.loc.dr.a(r0)     // Catch:{ Throwable -> 0x0073 }
            if (r0 == 0) goto L_0x00e4
            long r8 = r11.w     // Catch:{ Throwable -> 0x0073 }
            long r4 = r4 - r8
            r8 = 600(0x258, double:2.964E-321)
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 >= 0) goto L_0x00e4
            com.autonavi.aps.amapapi.model.AMapLocationServer r2 = r11.x     // Catch:{ Throwable -> 0x0073 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r3 = r0.l()     // Catch:{ Throwable -> 0x0073 }
            r4 = 0
            r0 = r11
            r1 = r12
            r0.a(r1, r2, r3, r4)     // Catch:{ Throwable -> 0x0073 }
        L_0x00a2:
            r11.a(r12)     // Catch:{ Throwable -> 0x0073 }
            boolean r0 = com.loc.dh.v()     // Catch:{ Throwable -> 0x0073 }
            if (r0 == 0) goto L_0x00ae
            r11.g()     // Catch:{ Throwable -> 0x0073 }
        L_0x00ae:
            long r0 = r11.y     // Catch:{ Throwable -> 0x0073 }
            boolean r0 = com.loc.dh.a(r0)     // Catch:{ Throwable -> 0x0073 }
            if (r0 == 0) goto L_0x00df
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            if (r0 == 0) goto L_0x00df
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            int r0 = r0.getLocationType()     // Catch:{ Throwable -> 0x0073 }
            r1 = 2
            if (r0 == r1) goto L_0x00d4
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            int r0 = r0.getLocationType()     // Catch:{ Throwable -> 0x0073 }
            r1 = 4
            if (r0 == r1) goto L_0x00d4
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            int r0 = r0.getLocationType()     // Catch:{ Throwable -> 0x0073 }
            if (r0 != r10) goto L_0x00df
        L_0x00d4:
            long r0 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0073 }
            r11.y = r0     // Catch:{ Throwable -> 0x0073 }
            com.loc.co r0 = r11.f     // Catch:{ Throwable -> 0x0073 }
            r0.e()     // Catch:{ Throwable -> 0x0073 }
        L_0x00df:
            r11.i()     // Catch:{ Throwable -> 0x0073 }
            goto L_0x000d
        L_0x00e4:
            com.loc.dn r7 = new com.loc.dn     // Catch:{ Throwable -> 0x0073 }
            r7.<init>()     // Catch:{ Throwable -> 0x0073 }
            long r4 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0073 }
            r7.a(r4)     // Catch:{ Throwable -> 0x0073 }
            com.loc.co r0 = r11.f     // Catch:{ Throwable -> 0x0178 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r0.d()     // Catch:{ Throwable -> 0x0178 }
            r11.x = r0     // Catch:{ Throwable -> 0x0178 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0178 }
            int r0 = r0.getLocationType()     // Catch:{ Throwable -> 0x0178 }
            r4 = 6
            if (r0 == r4) goto L_0x010a
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0178 }
            int r0 = r0.getLocationType()     // Catch:{ Throwable -> 0x0178 }
            r4 = 5
            if (r0 != r4) goto L_0x0110
        L_0x010a:
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0178 }
            long r2 = r0.k()     // Catch:{ Throwable -> 0x0178 }
        L_0x0110:
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0178 }
            r7.a(r0)     // Catch:{ Throwable -> 0x0178 }
            com.loc.co r0 = r11.f     // Catch:{ Throwable -> 0x0178 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r4 = r11.x     // Catch:{ Throwable -> 0x0178 }
            r5 = 0
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x0178 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r0.a(r4, r5)     // Catch:{ Throwable -> 0x0178 }
            r11.x = r0     // Catch:{ Throwable -> 0x0178 }
            r4 = r2
        L_0x0123:
            long r2 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0073 }
            r7.b(r2)     // Catch:{ Throwable -> 0x0073 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            boolean r0 = com.loc.dr.a(r0)     // Catch:{ Throwable -> 0x0073 }
            if (r0 == 0) goto L_0x0138
            long r2 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0073 }
            r11.w = r2     // Catch:{ Throwable -> 0x0073 }
        L_0x0138:
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            if (r0 != 0) goto L_0x0147
            r0 = 8
            java.lang.String r2 = "loc is null#0801"
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = a(r0, r2)     // Catch:{ Throwable -> 0x0073 }
            r11.x = r0     // Catch:{ Throwable -> 0x0073 }
        L_0x0147:
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            if (r0 == 0) goto L_0x01ba
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r3 = r0.l()     // Catch:{ Throwable -> 0x0073 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r0 = r11.x     // Catch:{ Throwable -> 0x0073 }
            com.amap.api.location.AMapLocation r0 = r0.clone()     // Catch:{ Throwable -> 0x0073 }
        L_0x0157:
            boolean r1 = r6.isLocationCacheEnable()     // Catch:{ Throwable -> 0x01ae }
            if (r1 == 0) goto L_0x016b
            com.loc.i r1 = r11.A     // Catch:{ Throwable -> 0x01ae }
            if (r1 == 0) goto L_0x016b
            com.loc.i r1 = r11.A     // Catch:{ Throwable -> 0x01ae }
            long r8 = r6.getLastLocationLifeCycle()     // Catch:{ Throwable -> 0x01ae }
            com.amap.api.location.AMapLocation r0 = r1.a(r0, r3, r8)     // Catch:{ Throwable -> 0x01ae }
        L_0x016b:
            r2 = r0
        L_0x016c:
            r0 = r11
            r1 = r12
            r0.a(r1, r2, r3, r4)     // Catch:{ Throwable -> 0x0073 }
            android.content.Context r0 = r11.e     // Catch:{ Throwable -> 0x0073 }
            com.loc.Cdo.a(r0, r7)     // Catch:{ Throwable -> 0x0073 }
            goto L_0x00a2
        L_0x0178:
            r0 = move-exception
            r4 = 0
            r5 = 2081(0x821, float:2.916E-42)
            com.loc.Cdo.a(r4, r5)     // Catch:{ Throwable -> 0x0073 }
            r4 = 8
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r8 = "loc error : "
            r5.<init>(r8)     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r8 = r0.getMessage()     // Catch:{ Throwable -> 0x0073 }
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r8 = "#0801"
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0073 }
            com.autonavi.aps.amapapi.model.AMapLocationServer r4 = a(r4, r5)     // Catch:{ Throwable -> 0x0073 }
            r11.x = r4     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r4 = "ApsServiceCore"
            java.lang.String r5 = "run part2"
            com.loc.di.a(r0, r4, r5)     // Catch:{ Throwable -> 0x0073 }
            r4 = r2
            goto L_0x0123
        L_0x01ae:
            r1 = move-exception
            java.lang.String r2 = "ApsServiceCore"
            java.lang.String r6 = "fixLastLocation"
            com.loc.di.a(r1, r2, r6)     // Catch:{ Throwable -> 0x0073 }
            r2 = r0
            goto L_0x016c
        L_0x01ba:
            r0 = r1
            r3 = r1
            goto L_0x0157
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.e.b(com.loc.e, android.os.Messenger, android.os.Bundle):void");
    }

    static /* synthetic */ void c(e eVar) {
        try {
            if (dh.a(eVar.e, eVar.v)) {
                eVar.v = dr.b();
                eVar.f.e();
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "doNGps");
        }
    }

    private void c(String str) throws UnsupportedEncodingException, IOException {
        PrintStream printStream = new PrintStream(this.q.getOutputStream(), true, "UTF-8");
        printStream.println("HTTP/1.0 200 OK");
        printStream.println("Content-Length:" + str.getBytes("UTF-8").length);
        printStream.println();
        printStream.println(str);
        printStream.close();
    }

    public static void f() {
        g = false;
    }

    private void g() {
        try {
            this.d.removeMessages(4);
            if (dh.a()) {
                this.d.sendEmptyMessage(4);
            }
            this.d.removeMessages(5);
            if (dh.c() && dh.d() > 2) {
                this.d.sendEmptyMessage(5);
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "checkConfig");
        }
    }

    private String h() {
        long currentTimeMillis = System.currentTimeMillis();
        if (dr.e(this.e)) {
            return this.E + "&&" + this.E + "({'package':'" + this.a + "','error_code':36,'error':'app is background'})";
        }
        if (this.x == null || currentTimeMillis - this.x.getTime() > BootloaderScanner.TIMEOUT) {
            try {
                this.x = this.f.d();
            } catch (Throwable th) {
                di.a(th, "ApsServiceCore", "getSocketLocResult");
            }
        }
        return this.x == null ? this.E + "&&" + this.E + "({'package':'" + this.a + "','error_code':8,'error':'unknown error'})" : this.x.getErrorCode() != 0 ? this.E + "&&" + this.E + "({'package':'" + this.a + "','error_code':" + this.x.getErrorCode() + ",'error':'" + this.x.getErrorInfo() + "'})" : this.E + "&&" + this.E + "({'package':'" + this.a + "','error_code':0,'error':'','location':{'y':" + this.x.getLatitude() + ",'precision':" + this.x.getAccuracy() + ",'x':" + this.x.getLongitude() + "},'version_code':'4.7.0','version':'4.7.0'})";
    }

    private void i() {
        try {
            if (this.f != null) {
                this.f.k();
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "startColl");
        }
    }

    public final void a() {
        try {
            this.i = new Cdo();
            this.b = new b("amapLocCoreThread");
            this.b.setPriority(5);
            this.b.start();
            this.d = new a(this.b.getLooper());
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "onCreate");
        }
    }

    public final void a(Intent intent) {
        if ("true".equals(intent.getStringExtra("as")) && this.d != null) {
            this.d.sendEmptyMessageDelayed(9, 100);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Messenger messenger, Bundle bundle) {
        float f2;
        if (bundle != null) {
            try {
                if (!bundle.isEmpty() && dh.r()) {
                    double d2 = bundle.getDouble("lat");
                    double d3 = bundle.getDouble("lon");
                    b(bundle);
                    if (this.c != null) {
                        f2 = dr.a(new double[]{d2, d3, this.c.getLatitude(), this.c.getLongitude()});
                        if (f2 < ((float) (dh.s() * 3))) {
                            Bundle bundle2 = new Bundle();
                            bundle2.setClassLoader(AMapLocation.class.getClassLoader());
                            bundle2.putInt("I_MAX_GEO_DIS", dh.s() * 3);
                            bundle2.putInt("I_MIN_GEO_DIS", dh.s());
                            bundle2.putParcelable("loc", this.c);
                            a(messenger, 6, bundle2);
                        }
                    } else {
                        f2 = -1.0f;
                    }
                    if (f2 == -1.0f || f2 > ((float) dh.s())) {
                        a(bundle);
                        this.c = this.f.a(d2, d3);
                    }
                }
            } catch (Throwable th) {
                di.a(th, "ApsServiceCore", "doLocationGeo");
            }
        }
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(this.l)) {
            this.l = di.b(this.e);
        }
        return !TextUtils.isEmpty(str) && str.equals(this.l);
    }

    public final Handler b() {
        return this.d;
    }

    public final void b(Intent intent) {
        String stringExtra = intent.getStringExtra("a");
        if (!TextUtils.isEmpty(stringExtra)) {
            m.a(this.e, stringExtra);
        }
        this.a = intent.getStringExtra("b");
        l.a(this.a);
        String stringExtra2 = intent.getStringExtra("d");
        if (!TextUtils.isEmpty(stringExtra2)) {
            p.a(stringExtra2);
        }
        dh.a = intent.getBooleanExtra("f", true);
    }

    public final void c() {
        try {
            if (!this.r) {
                this.s = new c();
                this.s.start();
                this.r = true;
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "startSocket");
        }
    }

    public final void d() {
        try {
            if (this.q != null) {
                this.q.close();
            }
        } catch (Throwable th) {
            di.a(th, "ApsServiceCore", "doStopScocket 1");
        }
        try {
            if (this.o != null) {
                this.o.close();
            }
        } catch (Throwable th2) {
            di.a(th2, "ApsServiceCore", "doStopScocket 2");
        }
        try {
            if (this.s != null) {
                this.s.interrupt();
            }
        } catch (Throwable th3) {
        }
        this.s = null;
        this.o = null;
        this.p = false;
        this.r = false;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void e() {
        /*
            r8 = this;
            r4 = 0
            java.util.HashMap<android.os.Messenger, java.lang.Long> r0 = r8.h     // Catch:{ Throwable -> 0x00a7 }
            r0.clear()     // Catch:{ Throwable -> 0x00a7 }
            r0 = 0
            r8.h = r0     // Catch:{ Throwable -> 0x00a7 }
            com.loc.co r0 = r8.f     // Catch:{ Throwable -> 0x00a7 }
            if (r0 == 0) goto L_0x0015
            com.loc.co r0 = r8.f     // Catch:{ Throwable -> 0x00a7 }
            android.content.Context r0 = r8.e     // Catch:{ Throwable -> 0x00a7 }
            com.loc.co.b(r0)     // Catch:{ Throwable -> 0x00a7 }
        L_0x0015:
            com.loc.e$a r0 = r8.d     // Catch:{ Throwable -> 0x00a7 }
            if (r0 == 0) goto L_0x001f
            com.loc.e$a r0 = r8.d     // Catch:{ Throwable -> 0x00a7 }
            r1 = 0
            r0.removeCallbacksAndMessages(r1)     // Catch:{ Throwable -> 0x00a7 }
        L_0x001f:
            com.loc.e$b r0 = r8.b     // Catch:{ Throwable -> 0x00a7 }
            if (r0 == 0) goto L_0x0036
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x00a7 }
            r1 = 18
            if (r0 < r1) goto L_0x00b2
            com.loc.e$b r0 = r8.b     // Catch:{ Throwable -> 0x00a0 }
            java.lang.Class<android.os.HandlerThread> r1 = android.os.HandlerThread.class
            java.lang.String r2 = "quitSafely"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00a0 }
            com.loc.dm.a(r0, r1, r2, r3)     // Catch:{ Throwable -> 0x00a0 }
        L_0x0036:
            r0 = 0
            r8.b = r0     // Catch:{ Throwable -> 0x00a7 }
            r0 = 0
            r8.d = r0     // Catch:{ Throwable -> 0x00a7 }
            com.loc.i r0 = r8.A     // Catch:{ Throwable -> 0x00a7 }
            if (r0 == 0) goto L_0x0048
            com.loc.i r0 = r8.A     // Catch:{ Throwable -> 0x00a7 }
            r0.c()     // Catch:{ Throwable -> 0x00a7 }
            r0 = 0
            r8.A = r0     // Catch:{ Throwable -> 0x00a7 }
        L_0x0048:
            r8.d()     // Catch:{ Throwable -> 0x00a7 }
            r0 = 0
            r8.t = r0     // Catch:{ Throwable -> 0x00a7 }
            r0 = 0
            r8.u = r0     // Catch:{ Throwable -> 0x00a7 }
            com.loc.co r0 = r8.f     // Catch:{ Throwable -> 0x00a7 }
            r0.f()     // Catch:{ Throwable -> 0x00a7 }
            android.content.Context r0 = r8.e     // Catch:{ Throwable -> 0x00a7 }
            com.loc.Cdo.a(r0)     // Catch:{ Throwable -> 0x00a7 }
            com.loc.do r0 = r8.i     // Catch:{ Throwable -> 0x00a7 }
            if (r0 == 0) goto L_0x0091
            long r0 = r8.j     // Catch:{ Throwable -> 0x00a7 }
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0091
            long r0 = r8.k     // Catch:{ Throwable -> 0x00a7 }
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0091
            long r0 = com.loc.dr.c()     // Catch:{ Throwable -> 0x00a7 }
            long r2 = r8.j     // Catch:{ Throwable -> 0x00a7 }
            long r6 = r0 - r2
            com.loc.do r0 = r8.i     // Catch:{ Throwable -> 0x00a7 }
            android.content.Context r1 = r8.e     // Catch:{ Throwable -> 0x00a7 }
            int r2 = r0.c(r1)     // Catch:{ Throwable -> 0x00a7 }
            com.loc.do r0 = r8.i     // Catch:{ Throwable -> 0x00a7 }
            android.content.Context r1 = r8.e     // Catch:{ Throwable -> 0x00a7 }
            int r3 = r0.d(r1)     // Catch:{ Throwable -> 0x00a7 }
            android.content.Context r1 = r8.e     // Catch:{ Throwable -> 0x00a7 }
            long r4 = r8.k     // Catch:{ Throwable -> 0x00a7 }
            com.loc.Cdo.a(r1, r2, r3, r4, r6)     // Catch:{ Throwable -> 0x00a7 }
            com.loc.do r0 = r8.i     // Catch:{ Throwable -> 0x00a7 }
            android.content.Context r1 = r8.e     // Catch:{ Throwable -> 0x00a7 }
            r0.e(r1)     // Catch:{ Throwable -> 0x00a7 }
        L_0x0091:
            com.loc.aj.b()     // Catch:{ Throwable -> 0x00a7 }
            boolean r0 = g     // Catch:{ Throwable -> 0x00a7 }
            if (r0 == 0) goto L_0x009f
            int r0 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x00a7 }
            android.os.Process.killProcess(r0)     // Catch:{ Throwable -> 0x00a7 }
        L_0x009f:
            return
        L_0x00a0:
            r0 = move-exception
            com.loc.e$b r0 = r8.b     // Catch:{ Throwable -> 0x00a7 }
            r0.quit()     // Catch:{ Throwable -> 0x00a7 }
            goto L_0x0036
        L_0x00a7:
            r0 = move-exception
            java.lang.String r1 = "ApsServiceCore"
            java.lang.String r2 = "threadDestroy"
            com.loc.di.a(r0, r1, r2)
            goto L_0x009f
        L_0x00b2:
            com.loc.e$b r0 = r8.b     // Catch:{ Throwable -> 0x00a7 }
            r0.quit()     // Catch:{ Throwable -> 0x00a7 }
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.e.e():void");
    }
}
