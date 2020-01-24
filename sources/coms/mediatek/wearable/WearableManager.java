package coms.mediatek.wearable;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;

public class WearableManager {
    public static final int DEFAULT_VERSION = 340;
    public static final String LIB_VERSION = "1.2.0";
    public static final int MODE_DOGP = 1;
    public static final int MODE_SPP = 0;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECT_FAIL = 4;
    public static final int STATE_CONNECT_LOST = 5;
    public static final int STATE_DISCONNECTING = 6;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    public static final int VERSION_32 = 210;
    public static final int VERSION_330 = 330;
    public static final int VERSION_331 = 331;
    public static final int VERSION_340 = 340;
    public static final int VERSION_35 = 310;
    public static final int VERSION_38 = 320;
    private static final l f = new l();
    private static final l g = new l();
    private static WearableManager i;
    private static final Object o = new Object();
    private static boolean p = true;
    int a = 0;
    final a b = new a() {
        public void a(int i) {
            WearableManager.this.l = i;
        }
    };
    private Linker c;
    private final f d = new f();
    private int e = 0;
    private Context h;
    private final ArrayList<WearableListener> j = new ArrayList<>();
    private boolean k = false;
    /* access modifiers changed from: private */
    public int l;
    private int m = 0;
    private int n = 340;

    interface a {
        void a(int i);
    }

    private WearableManager() {
    }

    private boolean a(Boolean bool, Context context, String str, int i2) {
        Boolean valueOf = Boolean.valueOf(true);
        String str2 = "";
        Log.d("[wearable]WearbleManager", "init, configResID " + i2);
        if (this.k) {
            Log.e("[wearable]WearbleManager", "init fail, cannot reinit");
            return false;
        } else if (context == null || VERSION.SDK_INT < 14 || i2 == 0) {
            Log.e("[wearable]WearbleManager", "init fail. init OK, appContext = null, Android API Level < 14, configResID == 0");
            return false;
        } else {
            if (VERSION.SDK_INT < 18 || !context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
                this.m = 3;
            }
            this.e = context.getSharedPreferences("linker", 0).getInt("linker_mode", 0);
            WearableConfig.init(context, i2);
            if (this.e == 0) {
                this.c = new h();
                f.a(context, this.c);
            } else {
                this.c = new d();
                g.a(context, this.c);
            }
            this.h = context;
            this.c.a(this.d, valueOf.booleanValue(), context);
            g.a(context);
            Log.d("[wearable]WearbleManager", "init done");
            this.k = true;
            return true;
        }
    }

    private void c() {
        Log.d("[wearable]WearbleManager", "disableGATTReconnect begin");
        Editor edit = this.h.getSharedPreferences("linker", 0).edit();
        edit.putBoolean("isReconnect", false);
        edit.commit();
    }

    private void c(int i2) {
        Log.d("[wearable]WearbleManager", "notifySwitchMode size=" + this.j.size());
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 < this.j.size()) {
                ((WearableListener) this.j.get(i4)).onModeSwitch(i2);
                i3 = i4 + 1;
            } else {
                return;
            }
        }
    }

    private void d() {
        Log.d("[wearable]WearbleManager", "disableSPPReconnect begin");
        Editor edit = this.h.getSharedPreferences("linker", 0).edit();
        edit.putBoolean("isSPPReconnect", false);
        edit.commit();
    }

    public static synchronized WearableManager getInstance() {
        WearableManager wearableManager;
        synchronized (WearableManager.class) {
            if (i == null) {
                i = new WearableManager();
            }
            wearableManager = i;
        }
        return wearableManager;
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2, String str) {
        Log.d("[wearable]WearbleManager", "onDataSent percent=" + f2 + "  sessionTag=" + str);
        c.a().a(f2, str);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        this.a = i2;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, int i3) {
        int i4 = 0;
        Log.d("[wearable]WearbleManager", "[onConnectChange] oldState=" + i2 + " newState=" + i3);
        if (i3 == 4 || i3 == 5) {
            Log.d("[wearable]WearbleManager", "[onConnectChange] ReadDataParser clearBuffer ");
            g.a().b();
            a(false);
        }
        if (i3 != 3 || this.c.p()) {
            while (true) {
                int i5 = i4;
                if (i5 >= this.j.size()) {
                    break;
                }
                ((WearableListener) this.j.get(i5)).onConnectChange(i2, i3);
                i4 = i5 + 1;
            }
            c.a().a(i3);
            b();
            if (i3 == 3) {
                p = true;
                return;
            }
            return;
        }
        Log.d("[wearable]WearbleManager", "[onConnectChange] STATE_CONNECTED !mLinker.isHandShakeDone return");
    }

    /* access modifiers changed from: 0000 */
    public void a(BluetoothDevice bluetoothDevice) {
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < this.j.size()) {
                ((WearableListener) this.j.get(i3)).onDeviceChange(bluetoothDevice);
                i2 = i3 + 1;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 < this.j.size()) {
                ((WearableListener) this.j.get(i4)).onDeviceScan(bluetoothDevice);
                i3 = i4 + 1;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(i iVar) {
        Log.d("[wearable]WearbleManager", "[runSession] " + iVar);
        int i2 = 0;
        for (int i3 = 0; i3 < iVar.e(); i3++) {
            i2 += iVar.a(i3).length;
        }
        if (iVar.b()) {
            this.c.a(i2, iVar.d());
        }
        this.c.e(i2);
        if (this.c instanceof h) {
            for (int i4 = 0; i4 < iVar.e(); i4++) {
                this.c.a(iVar.a(i4));
            }
            return;
        }
        byte[] bArr = new byte[i2];
        int i5 = 0;
        for (int i6 = 0; i6 < iVar.e(); i6++) {
            int length = iVar.a(i6).length;
            System.arraycopy(iVar.a(i6), 0, bArr, i5, length);
            i5 += length;
        }
        this.c.a(bArr);
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        Log.d("[wearable]WearbleManager", "setHandShakeDone handShake=" + z);
        this.c.c(z);
        if (isAvailable()) {
            a(2, 3);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(byte[] bArr, int i2) {
        g.a().a(bArr, i2);
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        if (!isAvailable() || this.e != 0 || this.n <= 340 || !WearableConfig.j()) {
            return true;
        }
        try {
            Log.d("[wearable]WearbleManager", "[SPP-ACK] waitAckForSPP start");
            long currentTimeMillis = System.currentTimeMillis();
            synchronized (o) {
                if (p) {
                    Log.d("[wearable]WearbleManager", "[SPP-ACK] waitAckForSPP start-wait");
                    o.wait(10000);
                }
                Log.d("[wearable]WearbleManager", "[SPP-ACK] waitAckForSPP end");
            }
            p = true;
            if (System.currentTimeMillis() - currentTimeMillis < 10000) {
                return true;
            }
            Log.e("[wearable]WearbleManager", "[SPP-ACK] waitAckForSPP timeout");
            this.h.sendBroadcast(new Intent("Wearable.SPP_ACK.TIMEOUT"));
            return false;
        } catch (Exception e2) {
            Log.e("[wearable]WearbleManager", "[SPP-ACK] waitAckForSPP, Exception " + e2.getMessage());
            return true;
        }
    }

    public void addController(Controller controller) {
        c.a.add(controller);
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        Log.d("[wearable]WearbleManager", "[SPP-ACK] notifyAckLock");
        try {
            synchronized (o) {
                o.notifyAll();
                p = false;
            }
        } catch (Exception e2) {
            Log.e("[wearable]WearbleManager", "[SPP-ACK] notifyAckLock, Exception " + e2.getMessage());
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2) {
        Log.d("[wearable]WearbleManager", "setRemoteVersion version " + i2);
        this.n = i2;
    }

    public void clearSendList(String str) {
        j.a().a(str);
    }

    public void connect() {
        Log.d("[wearable]WearbleManager", "connect");
        BluetoothDevice remoteDevice = getRemoteDevice();
        if (remoteDevice == null) {
            Log.d("[wearable]WearbleManager", "connect fail, device == null");
        } else {
            this.c.b(remoteDevice);
        }
    }

    public void destroy() {
        Log.d("[wearable]WearbleManager", "destroy");
        c();
        d();
        this.a = 0;
        this.c.a();
        f.a();
        g.a();
        this.k = false;
    }

    public void disconnect() {
        Log.d("[wearable]WearbleManager", "disconnect");
        c();
        d();
        this.a = 0;
        this.c.l();
    }

    public int getConnectState() {
        return this.c.j();
    }

    public HashSet<Controller> getControllers() {
        return c.a;
    }

    public int getGATTMTU() {
        if (this.e == 0 || !isAvailable()) {
            Log.e("[wearable]WearbleManager", "getGATTMTU fail");
            return 0;
        }
        Log.d("[wearable]WearbleManager", "getGATTMTU mWorkMode=" + this.e + " isAvailable=" + isAvailable());
        if (!(this.c instanceof d)) {
            return 0;
        }
        int f2 = ((d) this.c).f();
        Log.d("[wearable]WearbleManager", "getGATTMTU mtu=" + f2);
        return f2;
    }

    public int getGATTWriteType() {
        if (this.e == 0 || !isAvailable()) {
            Log.e("[wearable]WearbleManager", "getGATTWriteType fail");
            return 0;
        }
        Log.d("[wearable]WearbleManager", "getGATTWriteType mWorkMode=" + this.e + " isAvailable=" + isAvailable());
        if (!(this.c instanceof d)) {
            return 0;
        }
        int g2 = ((d) this.c).g();
        Log.d("[wearable]WearbleManager", "getGATTWriteType " + g2);
        return g2;
    }

    public BluetoothDevice getLERemoteDevice() {
        return getRemoteDevice();
    }

    public BluetoothDevice getRemoteDevice() {
        return isAvailable() ? this.c.m() : this.c.n();
    }

    public int getRemoteDeviceVersion() {
        return this.n;
    }

    public int getWorkingMode() {
        return this.e;
    }

    public boolean init(Boolean bool, Context context, String str) {
        return a(bool, context, str, 0);
    }

    public boolean init(Boolean bool, Context context, String str, int i2) {
        return a(bool, context, str, i2);
    }

    public boolean isAvailable() {
        return this.c.o() && this.c.p();
    }

    public boolean isConnecting() {
        return getConnectState() == 2 || (getConnectState() == 3 && !this.c.p());
    }

    public boolean isReConnecting() {
        return this.c.k();
    }

    public void registerWearableListener(WearableListener wearableListener) {
        if (!this.j.contains(wearableListener)) {
            this.j.add(wearableListener);
            Log.d("[wearable]WearbleManager", "registerWearableListener num=" + this.j.size());
        }
    }

    public void removeController(Controller controller) {
        c.a.remove(controller);
    }

    public boolean requestConnectionPriority(int i2) {
        if (VERSION.SDK_INT < 21 || this.e == 0) {
            Log.e("[wearable]WearbleManager", "requestConnectionPriority fail, SDK 21 or SPP mode");
            return false;
        }
        Log.d("[wearable]WearbleManager", "requestConnectionPriority mWorkMode=" + this.e + " priority=" + i2);
        if (i2 == 0 || i2 == 1 || i2 == 2) {
            return this.c.a(i2);
        }
        Log.d("[wearable]WearbleManager", "requestConnectionPriority fail, error priority");
        return false;
    }

    public void scanDevice(boolean z) {
        Log.d("[wearable]WearbleManager", "[scanDevice] enable=" + z);
        this.c.b(z);
    }

    public void setRemoteDevice(BluetoothDevice bluetoothDevice) {
        if (isConnecting() || isAvailable()) {
            Log.d("[wearable]WearbleManager", "setRemoteDevice return");
        } else {
            this.c.c(bluetoothDevice);
        }
    }

    public void switchMode() {
        Log.d("[wearable]WearbleManager", "[switchMode] before WorkMode=" + this.e);
        if (this.m == 3) {
            c(-1);
            Log.d("[wearable]WearbleManager", "[switchMode] android.os.Build.VERSION.SDK_INT < 18");
        } else if (isConnecting() || getConnectState() == 3 || this.l != 0 || getConnectState() == 6) {
            c(-1);
            Log.d("[wearable]WearbleManager", "[SwitchMode] connecting/have seesion");
        } else {
            c();
            d();
            if (this.e == 0) {
                this.c.a();
                this.c = new d();
                this.c.a(this.d, true, this.h);
                this.e = 1;
                c(1);
                f.a();
                g.a(this.h, this.c);
                this.c.c((BluetoothDevice) null);
            } else if (this.e == 1) {
                this.c.a();
                this.c = new h();
                this.c.a(this.d, true, this.h);
                this.e = 0;
                c(0);
                g.a();
                f.a(this.h, this.c);
                this.c.c((BluetoothDevice) null);
            }
            Log.d("[wearable]WearbleManager", "[switchMode] after WorkMode=" + this.e);
            this.a = 0;
            Editor edit = this.h.getSharedPreferences("linker", 0).edit();
            edit.putInt("linker_mode", this.e);
            edit.commit();
        }
    }

    public void unregisterWearableListener(WearableListener wearableListener) {
        this.j.remove(wearableListener);
    }
}
