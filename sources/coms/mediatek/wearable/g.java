package coms.mediatek.wearable;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import com.alibaba.android.arouter.utils.Consts;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.common.base.Ascii;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

class g {
    private static g h;
    private static Context i;
    private static boolean j = true;
    private int a = 0;
    private int b = 0;
    private final byte[] c = new byte[51200];
    private int d = 0;
    private int e = 0;
    private int f = 1;
    private final LoadJniFunction g = LoadJniFunction.a();

    private g() {
    }

    private int a(long j2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j2);
        int timeInMillis = (int) (instance.getTimeInMillis() / 1000);
        k.a("[wearable]ReadDataParser", "[getUTCTime] UTC time=" + timeInMillis);
        return timeInMillis;
    }

    public static synchronized g a() {
        g gVar;
        synchronized (g.class) {
            if (h == null) {
                h = new g();
            }
            gVar = h;
        }
        return gVar;
    }

    public static void a(Context context) {
        i = context;
    }

    private void a(boolean z) throws IOException {
        k.a("[wearable]ReadDataParser", "[sendSyncTime] useNewFormat=" + z);
        long currentTimeMillis = System.currentTimeMillis();
        int a2 = a(currentTimeMillis);
        int b2 = b(currentTimeMillis);
        boolean is24HourFormat = DateFormat.is24HourFormat(i);
        String e2 = WearableConfig.e();
        k.a("[wearable]ReadDataParser", "[sendSyncTime] useNewFormat=" + z + " deviceManufacturer=" + e2);
        if (z) {
            String str = "bnsrv_time mtk_bnapk 0 0 " + String.valueOf(String.valueOf(a2).length() + 1 + String.valueOf(b2).length()) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
            String str2 = String.valueOf(a2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.valueOf(b2);
            byte[] a3 = this.g.a(9, str);
            byte[] bytes = str2.getBytes();
            i iVar = new i("SyncTime", false, false);
            iVar.a(a3);
            iVar.a(bytes);
            j.a().a(iVar);
            return;
        }
        String str3 = String.valueOf(a2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.valueOf(b2);
        if (WearableManager.getInstance().getRemoteDeviceVersion() >= 340) {
            str3 = String.valueOf(a2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.valueOf(b2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + WearableManager.LIB_VERSION.substring(0, WearableManager.LIB_VERSION.lastIndexOf(Consts.DOT)) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + "ANDROID";
        }
        if (e2 != null && e2.toUpperCase().contains("KCT")) {
            str3 = String.valueOf(a2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.valueOf(b2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + (is24HourFormat ? "1" : "0");
        }
        Log.d("[wearable]ReadDataParser", "[sendSyncTime] snycTime=" + str3);
        byte[] a4 = this.g.a(2, str3);
        i iVar2 = new i("SyncTime", false, false);
        iVar2.a(a4);
        j.a().a(iVar2);
    }

    private int b(long j2) {
        TimeZone timeZone = TimeZone.getDefault();
        int rawOffset = timeZone.getRawOffset();
        if (timeZone.inDaylightTime(new Date(j2))) {
            rawOffset += timeZone.getDSTSavings();
        }
        k.a("[wearable]ReadDataParser", "[getUtcTimeZone] UTC time zone=" + rawOffset);
        return rawOffset;
    }

    private void c() {
        k.a("[wearable]ReadDataParser", "[runningReadFSM] mState=" + this.e);
        while (this.d > 0) {
            try {
                switch (this.e) {
                    case 0:
                        d();
                        break;
                    case 1:
                        e();
                        break;
                    case 2:
                        f();
                        break;
                }
            } catch (Exception e2) {
                k.c("[wearable]ReadDataParser", "ReadDataParser exception " + e2.toString());
                return;
            }
        }
    }

    private void d() throws Exception {
        int i2;
        if (this.e != 0) {
            throw new Exception("[getCommandLenth] state exception");
        } else if (this.d < 8) {
            k.a("[wearable]ReadDataParser", "[getCommandLenth] reciveBufferLenth < DataConstants.NOTIFYMINIHEADERLENTH");
            throw new Exception("[getCommandLenth] reciveBufferLenth ERROR");
        } else {
            int i3 = 0;
            while (true) {
                if (i3 < this.d - 4) {
                    if (this.c[i3] == -16 && this.c[i3 + 1] == -16 && this.c[i3 + 2] == -16 && this.c[i3 + 3] == -15) {
                        Log.d("[wearable]ReadDataParser", "[getCommandLenth] Get F0F0F0F1 Success");
                        i2 = i3;
                        break;
                    }
                    i3++;
                } else {
                    i2 = -1;
                    break;
                }
            }
            Log.d("[wearable]ReadDataParser", "[getCommandLenth] cmdpos=" + i2);
            if (i2 > 0) {
                System.arraycopy(this.c, i2, this.c, 0, this.d - i2);
                this.d -= i2;
                i3 = 0;
                i2 = 0;
            }
            if (i2 != -1) {
                this.a = this.c[i3 + 7] | (this.c[i3 + 4] << Ascii.CAN) | (this.c[i3 + 5] << 16) | (this.c[i3 + 6] << 8);
                System.arraycopy(this.c, 8, this.c, 0, this.d - 8);
                this.d -= 8;
                this.e = 1;
                k.a("[wearable]ReadDataParser", "[getCommandLenth] Get cmdBufferLenth Success cmdBufferLenth is " + this.a + "reciveBufferLenth is " + this.d);
                return;
            }
            System.arraycopy(this.c, 8, this.c, 0, this.d - 8);
            this.d -= 8;
            this.e = 0;
            k.a("[wearable]ReadDataParser", "[getCommandLenth] fail, Get cmdBufferLenth Success cmdBufferLenth is " + this.a + "reciveBufferLenth is " + this.d);
        }
    }

    private void e() throws Exception {
        if (this.d < this.a) {
            k.a("[wearable]ReadDataParser", "[getCmdAndDataLenth] reciveBufferLenth < cmdBufferLenth");
            throw new Exception("[getCmdAndDataLenth] reciveBufferLenth < cmdBufferLenth");
        }
        byte[] bArr = new byte[this.a];
        System.arraycopy(this.c, 0, bArr, 0, this.a);
        System.arraycopy(this.c, this.a, this.c, 0, this.d - this.a);
        this.c[this.d - this.a] = 0;
        this.d -= this.a;
        k.a("[wearable]ReadDataParser", "[getCmdAndDataLenth] Get cmdBuffer Success cmdBufferLenth is " + this.a + "reciveBufferLenth is " + this.d);
        this.f = this.g.a(bArr, this.a);
        Log.d("[wearable]ReadDataParser", "[getCmdAndDataLenth] commandBuffer=" + new String(bArr) + " mCommandType=" + this.f);
        if (!WearableManager.getInstance().isAvailable()) {
            if (this.f == 3) {
                k.a("[wearable]ReadDataParser", "[getCmdAndDataLenth] isHandshake=true");
                WearableManager.getInstance().a(true);
            } else if (this.f == 4) {
                j = false;
                k.a("[wearable]ReadDataParser", "[getCmdAndDataLenth] Get the Version Success");
            } else {
                this.e = 0;
                throw new Exception("[getCmdAndDataLenth] hand shake flow error");
            }
        } else if (this.f == 4) {
            this.c[0] = 0;
            this.d = 0;
            this.e = 0;
            k.a("[wearable]ReadDataParser", "[getCmdAndDataLenth] CMD_4 return");
            throw new Exception("[getCmdAndDataLenth] hand shake flow error");
        }
        if (this.f == 1 || this.f == 5 || this.f == 6 || this.f == 7 || this.f == 8 || this.f == 9) {
            this.b = this.g.b(bArr, this.a);
            k.a("[wearable]ReadDataParser", "[getCmdAndDataLenth] Get dataBufferLenth Success dataBufferLenth is " + this.b);
            if (this.b == -1) {
                this.e = 0;
                throw new Exception("[getCmdAndDataLenth] CMD156789 Parse error");
            }
        } else if (this.f == 3) {
            this.b = this.g.b(bArr, this.a);
            k.a("[wearable]ReadDataParser", "[getCmdAndDataLenth] CMD_3 dataBufferLenth=" + this.b);
            if (this.b == -1) {
                this.e = 0;
                throw new Exception("[getCmdAndDataLenth] CMD_3 Parse error");
            }
        } else if (this.f == 4) {
            this.b = this.g.b(bArr, this.a);
            k.a("[wearable]ReadDataParser", "[getCmdAndDataLenth] CMD_4 dataBufferLenth=" + this.b + " / " + this.d);
            if (this.b == -1) {
                this.e = 0;
                throw new Exception("[getCmdAndDataLenth] CMD_4 Parse error");
            }
        } else {
            this.e = 0;
            throw new Exception("[getCmdAndDataLenth] CMD Parse error");
        }
        this.e = 2;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [int, byte] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f() throws java.lang.Exception {
        /*
            r8 = this;
            r7 = 1
            r0 = 0
            int r1 = r8.b
            int r2 = r8.d
            if (r1 > r2) goto L_0x0140
            int r1 = r8.b
            byte[] r1 = new byte[r1]
            byte[] r2 = r8.c
            int r3 = r8.b
            java.lang.System.arraycopy(r2, r0, r1, r0, r3)
            byte[] r2 = r8.c
            int r3 = r8.b
            byte[] r4 = r8.c
            int r5 = r8.d
            int r6 = r8.b
            int r5 = r5 - r6
            java.lang.System.arraycopy(r2, r3, r4, r0, r5)
            byte[] r2 = r8.c
            int r3 = r8.d
            int r4 = r8.b
            int r3 = r3 - r4
            r2[r3] = r0
            int r2 = r8.d
            int r3 = r8.b
            int r2 = r2 - r3
            r8.d = r2
            r8.e = r0
            r8.b = r0
            r8.a = r0
            int r2 = r8.f
            r3 = 9
            if (r2 != r3) goto L_0x00cd
            java.lang.String r2 = new java.lang.String
            r2.<init>(r1)
            java.lang.String r3 = " "
            java.lang.String[] r3 = r2.split(r3)
            java.lang.String r4 = "[wearable]ReadDataParser"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "[getData] command="
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
            r4 = r3[r7]
            java.lang.String r5 = "mtk_bnapk"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00a9
            r0 = r3[r0]
            java.lang.String r2 = "bnsrv_time"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x009f
            r0 = 1
            r8.a(r0)     // Catch:{ IOException -> 0x009a }
        L_0x007d:
            java.lang.String r0 = "[wearable]ReadDataParser"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "[getData] reciveBufferLenth is "
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r8.d
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            coms.mediatek.wearable.k.a(r0, r1)
            return
        L_0x009a:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x007d
        L_0x009f:
            coms.mediatek.wearable.c r0 = coms.mediatek.wearable.c.a()
            int r2 = r8.f
            r0.a(r2, r1)
            goto L_0x007d
        L_0x00a9:
            java.lang.String r0 = "mtkack mtkack"
            boolean r0 = r2.contains(r0)
            if (r0 == 0) goto L_0x00c3
            java.lang.String r0 = "ok"
            boolean r0 = r2.contains(r0)
            if (r0 == 0) goto L_0x00c3
            coms.mediatek.wearable.WearableManager r0 = coms.mediatek.wearable.WearableManager.getInstance()
            r0.b()
            goto L_0x007d
        L_0x00c3:
            coms.mediatek.wearable.c r0 = coms.mediatek.wearable.c.a()
            int r2 = r8.f
            r0.a(r2, r1)
            goto L_0x007d
        L_0x00cd:
            int r2 = r8.f
            r3 = 3
            if (r2 != r3) goto L_0x00f2
            java.lang.String r0 = new java.lang.String
            r0.<init>(r1)
            java.lang.String r1 = "[wearable]ReadDataParser"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "[getData] command="
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
            goto L_0x007d
        L_0x00f2:
            int r2 = r8.f
            r3 = 4
            if (r2 != r3) goto L_0x0135
            java.lang.String r2 = new java.lang.String
            r2.<init>(r1)
            java.lang.String r1 = "[wearable]ReadDataParser"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "[getData] CMD_4 version: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r1, r3)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)     // Catch:{ NumberFormatException -> 0x012a }
            int r0 = r1.intValue()     // Catch:{ NumberFormatException -> 0x012a }
        L_0x011e:
            coms.mediatek.wearable.WearableManager r1 = coms.mediatek.wearable.WearableManager.getInstance()
            r1.b(r0)
            r8.g()
            goto L_0x007d
        L_0x012a:
            r1 = move-exception
            java.lang.String r1 = "[wearable]ReadDataParser"
            java.lang.String r2 = "[getData] CMD_4 version: NumberFormatException"
            coms.mediatek.wearable.k.a(r1, r2)
            goto L_0x011e
        L_0x0135:
            coms.mediatek.wearable.c r0 = coms.mediatek.wearable.c.a()
            int r2 = r8.f
            r0.a(r2, r1)
            goto L_0x007d
        L_0x0140:
            java.lang.String r0 = "[wearable]ReadDataParser"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "[getData] getdata dataBufferLenth > reciveBufferLenth "
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r8.d
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            coms.mediatek.wearable.k.a(r0, r1)
            java.lang.Exception r0 = new java.lang.Exception
            java.lang.String r1 = "[getData] dataBufferLenth > reciveBufferLenth"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: coms.mediatek.wearable.g.f():void");
    }

    private void g() {
        if (j) {
            k.a("[wearable]ReadDataParser", "[handShakeDone] mTimer is canceled verstion is old");
            WearableManager.getInstance().a(true);
            return;
        }
        try {
            k.a("[wearable]ReadDataParser", "[handShakeDone] mTimer is canceled verstion is new");
            a(false);
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    public void a(byte[] bArr, int i2) {
        try {
            System.arraycopy(bArr, 0, this.c, this.d, i2);
        } catch (Exception e2) {
            k.c("[wearable]ReadDataParser", "ReadDataParser exception: " + e2.toString());
            Intent intent = new Intent("Wearable.ReadDataParser.Error");
            intent.putExtra("ErrorLog", "Wearable ReadDataParser Error! Please check the data format sent by device.");
            i.sendBroadcast(intent);
            b();
        }
        this.d += i2;
        c();
    }

    public void b() {
        this.b = 0;
        this.d = 0;
        this.a = 0;
        this.e = 0;
    }
}
