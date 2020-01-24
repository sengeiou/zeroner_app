package com.mob.commons.logcollector;

import android.content.Context;
import android.os.Message;
import android.util.Base64;
import com.mob.tools.MobLog;
import com.mob.tools.SSDKHandlerThread;
import com.mob.tools.log.NLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.FileLocker;
import com.mob.tools.utils.Hashon;
import io.reactivex.annotations.SchedulerSupport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

/* compiled from: LogsManager */
public class c extends SSDKHandlerThread {
    private static c a;
    private static String b = "http://api.exc.mob.com:80";
    private HashMap<String, Integer> c;
    private Context d;
    private DeviceHelper e;
    private NetworkHelper f = new NetworkHelper();
    private d g;
    private File h;
    private FileLocker i;

    private c(Context context) {
        this.d = context.getApplicationContext();
        this.e = DeviceHelper.getInstance(context);
        this.g = d.a(context);
        this.c = new HashMap<>();
        this.i = new FileLocker();
        this.h = new File(context.getFilesDir(), ".lock");
        if (!this.h.exists()) {
            try {
                this.h.createNewFile();
            } catch (Exception e2) {
                MobLog.getInstance().w(e2);
            }
        }
        NLog.setContext(context);
        startThread();
    }

    public Context a() {
        return this.d;
    }

    public static synchronized c a(Context context) {
        c cVar;
        synchronized (c.class) {
            if (a == null) {
                a = new c(context);
            }
            cVar = a;
        }
        return cVar;
    }

    public void a(int i2, String str, String str2) {
        Message message = new Message();
        message.what = 100;
        message.arg1 = i2;
        message.obj = new Object[]{str, str2};
        this.handler.sendMessage(message);
    }

    public void a(int i2, int i3, String str, String str2, String str3) {
        Message message = new Message();
        message.what = 101;
        message.arg1 = i2;
        message.arg2 = i3;
        message.obj = new Object[]{str, str2, str3};
        this.handler.sendMessage(message);
    }

    private void a(Message message) {
        this.handler.sendMessageDelayed(message, 1000);
    }

    public void b(int i2, int i3, String str, String str2, String str3) {
        a(i2, i3, str, str2, str3);
        try {
            this.handler.wait();
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: protected */
    public void onMessage(Message message) {
        switch (message.what) {
            case 100:
                b(message);
                return;
            case 101:
                c(message);
                return;
            default:
                return;
        }
    }

    private void b(Message message) {
        try {
            int i2 = message.arg1;
            Object[] objArr = (Object[]) message.obj;
            String str = (String) objArr[0];
            String str2 = (String) objArr[1];
            b(i2, str, str2);
            a(i2, str, str2, null);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(android.os.Message r17) {
        /*
            r16 = this;
            r0 = r17
            int r11 = r0.arg1     // Catch:{ Throwable -> 0x00f7 }
            r0 = r17
            java.lang.Object r2 = r0.obj     // Catch:{ Throwable -> 0x00f7 }
            java.lang.Object[] r2 = (java.lang.Object[]) r2     // Catch:{ Throwable -> 0x00f7 }
            java.lang.Object[] r2 = (java.lang.Object[]) r2     // Catch:{ Throwable -> 0x00f7 }
            r3 = 0
            r3 = r2[r3]     // Catch:{ Throwable -> 0x00f7 }
            r0 = r3
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00f7 }
            r9 = r0
            r3 = 1
            r3 = r2[r3]     // Catch:{ Throwable -> 0x00f7 }
            r0 = r3
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00f7 }
            r10 = r0
            r3 = 2
            r6 = r2[r3]     // Catch:{ Throwable -> 0x00f7 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x00f7 }
            r7 = 1
            r0 = r17
            int r2 = r0.arg2     // Catch:{ Throwable -> 0x00f7 }
            if (r2 != 0) goto L_0x006c
            r7 = 2
        L_0x0027:
            r0 = r16
            com.mob.commons.logcollector.d r2 = r0.g     // Catch:{ Throwable -> 0x00f7 }
            java.lang.String r2 = r2.f()     // Catch:{ Throwable -> 0x00f7 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x00f7 }
            if (r3 != 0) goto L_0x0075
            com.mob.tools.utils.Hashon r3 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x00f7 }
            r3.<init>()     // Catch:{ Throwable -> 0x00f7 }
            java.util.HashMap r2 = r3.fromJson(r2)     // Catch:{ Throwable -> 0x00f7 }
            java.lang.String r3 = "fakelist"
            java.lang.Object r2 = r2.get(r3)     // Catch:{ Throwable -> 0x00f7 }
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch:{ Throwable -> 0x00f7 }
            if (r2 == 0) goto L_0x0075
            int r3 = r2.size()     // Catch:{ Throwable -> 0x00f7 }
            if (r3 <= 0) goto L_0x0075
            java.util.Iterator r3 = r2.iterator()     // Catch:{ Throwable -> 0x00f7 }
        L_0x0053:
            boolean r2 = r3.hasNext()     // Catch:{ Throwable -> 0x00f7 }
            if (r2 == 0) goto L_0x0075
            java.lang.Object r2 = r3.next()     // Catch:{ Throwable -> 0x00f7 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x00f7 }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x00f7 }
            if (r4 != 0) goto L_0x0053
            boolean r2 = r6.contains(r2)     // Catch:{ Throwable -> 0x00f7 }
            if (r2 == 0) goto L_0x0053
        L_0x006b:
            return
        L_0x006c:
            r0 = r17
            int r2 = r0.arg2     // Catch:{ Throwable -> 0x00f7 }
            r3 = 2
            if (r2 != r3) goto L_0x0027
            r7 = 1
            goto L_0x0027
        L_0x0075:
            r0 = r16
            com.mob.commons.logcollector.d r2 = r0.g     // Catch:{ Throwable -> 0x00f7 }
            int r2 = r2.c()     // Catch:{ Throwable -> 0x00f7 }
            r0 = r16
            com.mob.commons.logcollector.d r3 = r0.g     // Catch:{ Throwable -> 0x00f7 }
            int r12 = r3.d()     // Catch:{ Throwable -> 0x00f7 }
            r0 = r16
            com.mob.commons.logcollector.d r3 = r0.g     // Catch:{ Throwable -> 0x00f7 }
            int r13 = r3.e()     // Catch:{ Throwable -> 0x00f7 }
            r3 = 3
            if (r3 != r7) goto L_0x0093
            r3 = -1
            if (r3 == r13) goto L_0x006b
        L_0x0093:
            r3 = 1
            if (r3 != r7) goto L_0x0099
            r3 = -1
            if (r3 == r2) goto L_0x006b
        L_0x0099:
            r3 = 2
            if (r3 != r7) goto L_0x009f
            r3 = -1
            if (r3 == r12) goto L_0x006b
        L_0x009f:
            java.lang.String r8 = com.mob.tools.utils.Data.MD5(r6)     // Catch:{ Throwable -> 0x00f7 }
            r0 = r16
            com.mob.tools.utils.FileLocker r3 = r0.i     // Catch:{ Throwable -> 0x0101 }
            r0 = r16
            java.io.File r4 = r0.h     // Catch:{ Throwable -> 0x0101 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x0101 }
            r3.setLockFile(r4)     // Catch:{ Throwable -> 0x0101 }
            r0 = r16
            com.mob.tools.utils.FileLocker r3 = r0.i     // Catch:{ Throwable -> 0x0101 }
            r4 = 0
            boolean r3 = r3.lock(r4)     // Catch:{ Throwable -> 0x0101 }
            if (r3 == 0) goto L_0x00d1
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0101 }
            r0 = r16
            com.mob.commons.logcollector.d r3 = r0.g     // Catch:{ Throwable -> 0x0101 }
            long r14 = r3.a()     // Catch:{ Throwable -> 0x0101 }
            long r4 = r4 - r14
            r0 = r16
            android.content.Context r3 = r0.d     // Catch:{ Throwable -> 0x0101 }
            com.mob.commons.logcollector.f.a(r3, r4, r6, r7, r8)     // Catch:{ Throwable -> 0x0101 }
        L_0x00d1:
            r0 = r16
            com.mob.tools.utils.FileLocker r3 = r0.i     // Catch:{ Throwable -> 0x0101 }
            r3.release()     // Catch:{ Throwable -> 0x0101 }
            r0 = r16
            java.util.HashMap<java.lang.String, java.lang.Integer> r3 = r0.c     // Catch:{ Throwable -> 0x00f7 }
            r3.remove(r8)     // Catch:{ Throwable -> 0x00f7 }
            r3 = 3
            if (r3 != r7) goto L_0x013b
            r3 = 1
            if (r3 != r13) goto L_0x013b
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ Throwable -> 0x00f7 }
            r3 = 0
            r4 = 3
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x00f7 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x00f7 }
            r0 = r16
            r0.a(r11, r9, r10, r2)     // Catch:{ Throwable -> 0x00f7 }
            goto L_0x006b
        L_0x00f7:
            r2 = move-exception
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()
            r3.w(r2)
            goto L_0x006b
        L_0x0101:
            r2 = move-exception
            r3 = r2
            r0 = r16
            java.util.HashMap<java.lang.String, java.lang.Integer> r2 = r0.c     // Catch:{ Throwable -> 0x00f7 }
            boolean r2 = r2.containsKey(r8)     // Catch:{ Throwable -> 0x00f7 }
            if (r2 == 0) goto L_0x0130
            r0 = r16
            java.util.HashMap<java.lang.String, java.lang.Integer> r2 = r0.c     // Catch:{ Throwable -> 0x00f7 }
            java.lang.Object r2 = r2.get(r8)     // Catch:{ Throwable -> 0x00f7 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Throwable -> 0x00f7 }
            int r2 = r2.intValue()     // Catch:{ Throwable -> 0x00f7 }
        L_0x011b:
            int r2 = r2 + 1
            r0 = r16
            java.util.HashMap<java.lang.String, java.lang.Integer> r4 = r0.c     // Catch:{ Throwable -> 0x00f7 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x00f7 }
            r4.put(r8, r5)     // Catch:{ Throwable -> 0x00f7 }
            r4 = 3
            if (r2 >= r4) goto L_0x0132
            r16.a(r17)     // Catch:{ Throwable -> 0x00f7 }
            goto L_0x006b
        L_0x0130:
            r2 = 0
            goto L_0x011b
        L_0x0132:
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x00f7 }
            r2.w(r3)     // Catch:{ Throwable -> 0x00f7 }
            goto L_0x006b
        L_0x013b:
            r3 = 1
            if (r3 != r7) goto L_0x0153
            r3 = 1
            if (r3 != r2) goto L_0x0153
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ Throwable -> 0x00f7 }
            r3 = 0
            r4 = 1
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x00f7 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x00f7 }
            r0 = r16
            r0.a(r11, r9, r10, r2)     // Catch:{ Throwable -> 0x00f7 }
            goto L_0x006b
        L_0x0153:
            r2 = 2
            if (r2 != r7) goto L_0x006b
            r2 = 1
            if (r2 != r12) goto L_0x006b
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ Throwable -> 0x00f7 }
            r3 = 0
            r4 = 2
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x00f7 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x00f7 }
            r0 = r16
            r0.a(r11, r9, r10, r2)     // Catch:{ Throwable -> 0x00f7 }
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.logcollector.c.c(android.os.Message):void");
    }

    private String b() {
        return b + "/errconf";
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r7, java.lang.String r8, java.lang.String r9) throws java.lang.Throwable {
        /*
            r6 = this;
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair
            java.lang.String r1 = "key"
            r0.<init>(r1, r9)
            r2.add(r0)
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair
            java.lang.String r1 = "sdk"
            r0.<init>(r1, r8)
            r2.add(r0)
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair
            java.lang.String r1 = "apppkg"
            com.mob.tools.utils.DeviceHelper r3 = r6.e
            java.lang.String r3 = r3.getPackageName()
            r0.<init>(r1, r3)
            r2.add(r0)
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair
            java.lang.String r1 = "appver"
            com.mob.tools.utils.DeviceHelper r3 = r6.e
            int r3 = r3.getAppVersion()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r0.<init>(r1, r3)
            r2.add(r0)
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair
            java.lang.String r1 = "sdkver"
            java.lang.String r3 = java.lang.String.valueOf(r7)
            r0.<init>(r1, r3)
            r2.add(r0)
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair
            java.lang.String r1 = "plat"
            com.mob.tools.utils.DeviceHelper r3 = r6.e
            int r3 = r3.getPlatformCode()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r0.<init>(r1, r3)
            r2.add(r0)
            com.mob.tools.network.NetworkHelper$NetworkTimeOut r5 = new com.mob.tools.network.NetworkHelper$NetworkTimeOut     // Catch:{ Throwable -> 0x01d5 }
            r5.<init>()     // Catch:{ Throwable -> 0x01d5 }
            r0 = 10000(0x2710, float:1.4013E-41)
            r5.readTimout = r0     // Catch:{ Throwable -> 0x01d5 }
            r0 = 10000(0x2710, float:1.4013E-41)
            r5.connectionTimeout = r0     // Catch:{ Throwable -> 0x01d5 }
            com.mob.tools.network.NetworkHelper r0 = r6.f     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r1 = r6.b()     // Catch:{ Throwable -> 0x01d5 }
            r3 = 0
            r4 = 0
            java.lang.String r0 = r0.httpPost(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x01d5 }
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r2 = "get server config == %s"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01d5 }
            r4 = 0
            r3[r4] = r0     // Catch:{ Throwable -> 0x01d5 }
            r1.i(r2, r3)     // Catch:{ Throwable -> 0x01d5 }
            com.mob.tools.utils.Hashon r1 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x01d5 }
            r1.<init>()     // Catch:{ Throwable -> 0x01d5 }
            java.util.HashMap r0 = r1.fromJson(r0)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r1 = "-200"
            java.lang.String r2 = "status"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x01d5 }
            boolean r1 = r1.equals(r2)     // Catch:{ Throwable -> 0x01d5 }
            if (r1 == 0) goto L_0x00b9
            com.mob.tools.log.NLog r0 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r1 = "error log server config response fail !!"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x01d5 }
            r0.i(r1, r2)     // Catch:{ Throwable -> 0x01d5 }
        L_0x00b8:
            return
        L_0x00b9:
            java.lang.String r1 = "result"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x01d5 }
            if (r0 == 0) goto L_0x00b8
            boolean r1 = r0 instanceof java.util.HashMap     // Catch:{ Throwable -> 0x01d5 }
            if (r1 == 0) goto L_0x00b8
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r1 = "timestamp"
            boolean r1 = r0.containsKey(r1)     // Catch:{ Throwable -> 0x01d5 }
            if (r1 == 0) goto L_0x00eb
            java.lang.String r1 = "timestamp"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ Throwable -> 0x01df }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x01df }
            long r2 = com.mob.tools.utils.R.parseLong(r1)     // Catch:{ Throwable -> 0x01df }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01df }
            long r2 = r4 - r2
            com.mob.commons.logcollector.d r1 = r6.g     // Catch:{ Throwable -> 0x01df }
            r1.a(r2)     // Catch:{ Throwable -> 0x01df }
        L_0x00eb:
            java.lang.String r1 = "1"
            java.lang.String r2 = "enable"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x01d5 }
            boolean r1 = r1.equals(r2)     // Catch:{ Throwable -> 0x01d5 }
            if (r1 == 0) goto L_0x01e9
            com.mob.commons.logcollector.d r1 = r6.g     // Catch:{ Throwable -> 0x01d5 }
            r2 = 1
            r1.a(r2)     // Catch:{ Throwable -> 0x01d5 }
        L_0x0105:
            java.lang.String r1 = "upconf"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ Throwable -> 0x01d5 }
            if (r1 == 0) goto L_0x0150
            boolean r2 = r1 instanceof java.util.HashMap     // Catch:{ Throwable -> 0x01d5 }
            if (r2 == 0) goto L_0x0150
            java.util.HashMap r1 = (java.util.HashMap) r1     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r2 = "crash"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r3 = "sdkerr"
            java.lang.Object r3 = r1.get(r3)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r4 = "apperr"
            java.lang.Object r1 = r1.get(r4)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x01d5 }
            com.mob.commons.logcollector.d r4 = r6.g     // Catch:{ Throwable -> 0x01d5 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Throwable -> 0x01d5 }
            r4.a(r2)     // Catch:{ Throwable -> 0x01d5 }
            com.mob.commons.logcollector.d r2 = r6.g     // Catch:{ Throwable -> 0x01d5 }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ Throwable -> 0x01d5 }
            r2.b(r3)     // Catch:{ Throwable -> 0x01d5 }
            com.mob.commons.logcollector.d r2 = r6.g     // Catch:{ Throwable -> 0x01d5 }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Throwable -> 0x01d5 }
            r2.c(r1)     // Catch:{ Throwable -> 0x01d5 }
        L_0x0150:
            java.lang.String r1 = "requesthost"
            boolean r1 = r0.containsKey(r1)     // Catch:{ Throwable -> 0x01d5 }
            if (r1 == 0) goto L_0x01a5
            java.lang.String r1 = "requestport"
            boolean r1 = r0.containsKey(r1)     // Catch:{ Throwable -> 0x01d5 }
            if (r1 == 0) goto L_0x01a5
            java.lang.String r1 = "requesthost"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r2 = "requestport"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x01d5 }
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x01d5 }
            if (r3 != 0) goto L_0x01a5
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x01d5 }
            if (r3 != 0) goto L_0x01a5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d5 }
            r3.<init>()     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r4 = "http://"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r3 = ":"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x01d5 }
            b = r1     // Catch:{ Throwable -> 0x01d5 }
        L_0x01a5:
            java.lang.String r1 = "filter"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x01d5 }
            if (r0 == 0) goto L_0x00b8
            boolean r1 = r0 instanceof java.util.ArrayList     // Catch:{ Throwable -> 0x01d5 }
            if (r1 == 0) goto L_0x00b8
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ Throwable -> 0x01d5 }
            int r1 = r0.size()     // Catch:{ Throwable -> 0x01d5 }
            if (r1 <= 0) goto L_0x00b8
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ Throwable -> 0x01d5 }
            r1.<init>()     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r2 = "fakelist"
            r1.put(r2, r0)     // Catch:{ Throwable -> 0x01d5 }
            com.mob.commons.logcollector.d r0 = r6.g     // Catch:{ Throwable -> 0x01d5 }
            com.mob.tools.utils.Hashon r2 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x01d5 }
            r2.<init>()     // Catch:{ Throwable -> 0x01d5 }
            java.lang.String r1 = r2.fromHashMap(r1)     // Catch:{ Throwable -> 0x01d5 }
            r0.a(r1)     // Catch:{ Throwable -> 0x01d5 }
            goto L_0x00b8
        L_0x01d5:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.d(r0)
            goto L_0x00b8
        L_0x01df:
            r1 = move-exception
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x01d5 }
            r2.i(r1)     // Catch:{ Throwable -> 0x01d5 }
            goto L_0x00eb
        L_0x01e9:
            com.mob.commons.logcollector.d r1 = r6.g     // Catch:{ Throwable -> 0x01d5 }
            r2 = 0
            r1.a(r2)     // Catch:{ Throwable -> 0x01d5 }
            goto L_0x0105
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.logcollector.c.b(int, java.lang.String, java.lang.String):void");
    }

    private String c() {
        return b + "/errlog";
    }

    private void a(int i2, String str, String str2, String[] strArr) {
        try {
            if (this.g.b()) {
                if (SchedulerSupport.NONE.equals(this.e.getDetailNetworkTypeForStatic())) {
                    throw new IllegalStateException("network is disconnected!");
                }
                ArrayList a2 = f.a(this.d, strArr);
                int i3 = 0;
                while (true) {
                    int i4 = i3;
                    if (i4 < a2.size()) {
                        e eVar = (e) a2.get(i4);
                        HashMap c2 = c(i2, str, str2);
                        c2.put("errmsg", eVar.a);
                        if (a(a(new Hashon().fromHashMap(c2)), true)) {
                            f.a(this.d, eVar.b);
                        }
                        i3 = i4 + 1;
                    } else {
                        return;
                    }
                }
            }
        } catch (Throwable th) {
            MobLog.getInstance().i(th);
        }
    }

    private HashMap<String, Object> c(int i2, String str, String str2) throws Throwable {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("key", str2);
        hashMap.put("plat", Integer.valueOf(this.e.getPlatformCode()));
        hashMap.put("sdk", str);
        hashMap.put("sdkver", Integer.valueOf(i2));
        hashMap.put("appname", this.e.getAppName());
        hashMap.put("apppkg", this.e.getPackageName());
        hashMap.put("appver", String.valueOf(this.e.getAppVersion()));
        hashMap.put("deviceid", this.e.getDeviceKey());
        hashMap.put("model", this.e.getModel());
        hashMap.put("mac", this.e.getMacAddress());
        hashMap.put("udid", this.e.getDeviceId());
        hashMap.put("sysver", String.valueOf(this.e.getOSVersionInt()));
        hashMap.put("networktype", this.e.getDetailNetworkTypeForStatic());
        return hashMap;
    }

    private String a(String str) throws Throwable {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = byteArrayInputStream.read(bArr, 0, 1024);
            if (read != -1) {
                gZIPOutputStream.write(bArr, 0, read);
            } else {
                gZIPOutputStream.flush();
                gZIPOutputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                byteArrayInputStream.close();
                return Base64.encodeToString(byteArray, 2);
            }
        }
    }

    private boolean a(String str, boolean z) throws Throwable {
        try {
            if (SchedulerSupport.NONE.equals(this.e.getDetailNetworkTypeForStatic())) {
                throw new IllegalStateException("network is disconnected!");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("m", str));
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = 10000;
            networkTimeOut.connectionTimeout = 10000;
            this.f.httpPost(c(), arrayList, null, null, networkTimeOut);
            return true;
        } catch (Throwable th) {
            MobLog.getInstance().i(th);
            return false;
        }
    }
}
