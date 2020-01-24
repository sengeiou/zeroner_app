package coms.mediatek.wearable;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

class h extends Linker {
    private OutputStream a;
    /* access modifiers changed from: private */
    public BluetoothSocket b;
    /* access modifiers changed from: private */
    public BluetoothSocket c;
    /* access modifiers changed from: private */
    public Thread k;
    private Thread l;
    private final Runnable m = new Runnable() {
        public void run() {
            InputStream inputStream;
            Log.d("[wearable]SPPLinker", "SPPReadThread begin");
            try {
                inputStream = h.this.c.getInputStream();
            } catch (Exception e) {
                Log.e("[wearable]SPPLinker", "SPPReadThread getInputStream fail: " + e.getMessage());
                inputStream = null;
            }
            while (inputStream != null) {
                try {
                    byte[] bArr = new byte[5120];
                    int read = inputStream.read(bArr);
                    Log.d("[wearable]SPPLinker", "SPPReadThread read length=" + read);
                    if (read > 0) {
                        k.b("[wearable]SPPLinker", "SPPReadThread.read data=" + new String(bArr));
                        h.this.f.a(bArr, read);
                    }
                } catch (Exception e2) {
                    ThrowableExtension.printStackTrace(e2);
                    Log.e("[wearable]SPPLinker", "SPPReadThread.read Exception" + e2.getMessage());
                    h.this.q();
                    h.this.f();
                }
            }
            Log.d("[wearable]SPPLinker", "SPPReadThread end");
        }
    };
    private final Runnable n = new Runnable() {
        public void run() {
            Log.d("[wearable]SPPLinker", "SPPClientThread begin");
            try {
                h.this.b = h.this.n().createRfcommSocketToServiceRecord(UUID.fromString(WearableConfig.a()));
                try {
                    if (h.this.g.isDiscovering()) {
                        h.this.g.cancelDiscovery();
                    }
                    Log.d("[wearable]SPPLinker", "SPPClientThread connect begin");
                    h.this.c(2);
                    h.this.b.connect();
                    Log.d("[wearable]SPPLinker", "SPPClientThread.connect end");
                    synchronized (h.this) {
                        h.this.k = null;
                    }
                    h.this.a(h.this.b, h.this.n());
                    Log.d("[wearable]SPPLinker", "SPPClientThread end");
                } catch (Exception e) {
                    Log.e("[wearable]SPPLinker", "SPPClientThread.connect fail: " + e.getMessage());
                    h.this.g();
                    try {
                        if (h.this.b != null) {
                            h.this.b.close();
                        }
                    } catch (Exception e2) {
                        Log.e("[wearable]SPPLinker", "SPPClientThread.connect close fail: " + e2.getMessage());
                    }
                }
            } catch (IOException e3) {
                Log.e("[wearable]SPPLinker", "SPPClientThread create socket IOException" + e3.getMessage());
            }
        }
    };
    /* access modifiers changed from: private */
    public final Handler o = new Handler();
    /* access modifiers changed from: private */
    public int p = 0;
    /* access modifiers changed from: private */
    public int q = 5;
    /* access modifiers changed from: private */
    public final Runnable r = new Runnable() {
        public void run() {
            if (h.this.p < h.this.q) {
                h.this.p = h.this.p + 1;
                Log.d("[wearable]SPPLinker", "mScanRunnable scan mScanTime=" + h.this.p);
                h.this.o.removeCallbacks(h.this.r);
                h.this.o.postDelayed(h.this.r, 13000);
                boolean z = h.this.e.getSharedPreferences("linker", 0).getBoolean("isSPPReconnect", false);
                Log.d("[wearable]SPPLinker", "runAutoConnectTask isSPPReconnect=" + z);
                if (!z) {
                    h.this.o.removeCallbacks(h.this.r);
                    Log.d("[wearable]SPPLinker", "runAutoConnectTask return");
                    return;
                }
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.bluetooth.device.action.FOUND");
                h.this.e.registerReceiver(h.this.s, intentFilter, null, null);
                if (h.this.g != null && h.this.g.isDiscovering()) {
                    h.this.g.cancelDiscovery();
                }
                h.this.g.startDiscovery();
                return;
            }
            Log.d("[wearable]SPPLinker", "mScanRunnable stop mScanTime=" + h.this.p);
            if (h.this.g != null && h.this.g.isDiscovering()) {
                Log.d("[wearable]SPPLinker", "mScanRunnable cancelDiscovery");
                h.this.g.cancelDiscovery();
            }
            h.this.o.removeCallbacks(h.this.r);
            h.this.u();
        }
    };
    /* access modifiers changed from: private */
    public final BroadcastReceiver s = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d("[wearable]SPPLinker", "[mSPPReceiver] intent=" + intent.toString());
            if ("android.bluetooth.device.action.FOUND".equals(intent.getAction())) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                SharedPreferences sharedPreferences = h.this.e.getSharedPreferences("linker", 0);
                boolean z = sharedPreferences.getBoolean("isSPPReconnect", false);
                String string = sharedPreferences.getString("reconnectSPPAddress", "");
                Log.d("[wearable]SPPLinker", "[mSPPReceiver] isReconnect=" + z + " preAddress=" + string + " device=" + (bluetoothDevice != null ? bluetoothDevice.getAddress() : MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
                if (!z) {
                    h.this.s();
                }
                if (z && bluetoothDevice != null && string.equals(bluetoothDevice.getAddress())) {
                    Log.d("[wearable]SPPLinker", "[mSPPReceiver] auto-connect " + bluetoothDevice.getAddress());
                    h.this.s();
                    h.this.r();
                    h.this.b(bluetoothDevice);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public Timer t;

    /* access modifiers changed from: private */
    public synchronized void a(BluetoothSocket bluetoothSocket, BluetoothDevice bluetoothDevice) {
        Log.d("[wearable]SPPLinker", "[connected], socket=" + bluetoothSocket + " device=" + bluetoothDevice);
        Log.d("[wearable]SPPLinker", "mclient=" + this.b + " mread=" + this.c);
        if (this.k != null) {
            b(1);
            this.k = null;
        }
        if (this.l != null) {
            b(2);
            this.l = null;
        }
        this.c = bluetoothSocket;
        this.l = new Thread(this.m);
        this.l.start();
        this.d = bluetoothDevice;
        try {
            this.a = bluetoothSocket.getOutputStream();
            c(3);
            Log.d("[wearable]SPPLinker", "[connected] write SPP TAG");
            a("MTKSPPForMMI".getBytes());
            a(LoadJniFunction.a().a(2, "REQV"));
            v();
        } catch (Exception e) {
            Log.e("[wearable]SPPLinker", "[connected] getOutput fail: " + e.getMessage());
            g();
        }
        return;
    }

    private void b(int i) {
        if (i == 1) {
            try {
                if (this.b != null) {
                    Log.d("[wearable]SPPLinker", "cancelThread mClientSocket.close");
                    this.b.close();
                    this.b = null;
                }
            } catch (IOException e) {
                Log.e("[wearable]SPPLinker", "SPPCancelCallback.cancel client fail: " + e.getMessage());
            }
        } else if (i == 2) {
            try {
                synchronized (this.c) {
                    if (this.c != null) {
                        Log.d("[wearable]SPPLinker", "cancelThread mReadSocket.close begin " + this.c.isConnected());
                        this.c.close();
                        Log.d("[wearable]SPPLinker", "cancelThread mReadSocket.close end " + this.c.isConnected());
                        this.c = null;
                    }
                }
            } catch (IOException e2) {
                Log.e("[wearable]SPPLinker", "SPPCancelCallback.cancel read failed: " + e2.getMessage());
            }
        } else {
            Log.e("[wearable]SPPLinker", "SPPCancelCallback.cancel invaild thread");
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        Log.d("[wearable]SPPLinker", "[connectionLost] begin");
        e();
        c(5);
        if (this.k != null) {
            b(1);
            this.k = null;
        }
        if (this.l != null) {
            b(2);
            this.l = null;
        }
        c(0);
        if (i()) {
            t();
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        Log.d("[wearable]SPPLinker", "[connectFailed] begin");
        c(4);
    }

    private void h() {
        if (WearableManager.getInstance().getWorkingMode() != 0) {
            Log.d("[wearable]SPPLinker", "autoSPPReconnect return");
            return;
        }
        SharedPreferences sharedPreferences = this.e.getSharedPreferences("linker", 0);
        boolean z = sharedPreferences.getBoolean("isSPPReconnect", false);
        String string = sharedPreferences.getString("reconnectSPPAddress", "");
        Log.d("[wearable]SPPLinker", "autoSPPReconnect isReconnect=" + z + " address=" + string);
        if (z && BluetoothAdapter.checkBluetoothAddress(string)) {
            BluetoothDevice remoteDevice = this.g.getRemoteDevice(string);
            Log.d("[wearable]SPPLinker", "autoSPPReconnect name=" + remoteDevice.getName());
            b(remoteDevice);
        }
    }

    private boolean i() {
        boolean z = this.e.getSharedPreferences("linker", 0).getBoolean("isSPPReconnect", false);
        Log.d("[wearable]SPPLinker", "isSPPReconnect isReconnect=" + z);
        return z;
    }

    /* access modifiers changed from: private */
    public void r() {
        Log.d("[wearable]SPPLinker", "disableSPPReconnect");
        Editor edit = this.e.getSharedPreferences("linker", 0).edit();
        edit.putBoolean("isSPPReconnect", false);
        edit.commit();
    }

    /* access modifiers changed from: private */
    public void s() {
        Log.d("[wearable]SPPLinker", "cancelSPPAutoConnectTask");
        if (this.g != null && this.g.isDiscovering()) {
            this.g.cancelDiscovery();
        }
        if (this.o != null) {
            this.o.removeCallbacks(this.r);
        }
        u();
    }

    private void t() {
        u();
        Log.d("[wearable]SPPLinker", "runAutoConnectTask start " + WearableConfig.d());
        this.p = 1;
        this.q = ((WearableConfig.d() * 1000) / 13000) + 1;
        this.o.removeCallbacks(this.r);
        this.o.postDelayed(this.r, 3000);
    }

    /* access modifiers changed from: private */
    public void u() {
        try {
            Log.d("[wearable]SPPLinker", "unregisterSPPReconnectReceiver start");
            this.e.unregisterReceiver(this.s);
        } catch (Exception e) {
            Log.d("[wearable]SPPLinker", "unregisterSPPReconnectReceiver exception");
        }
    }

    private void v() {
        Log.d("[wearable]SPPLinker", "runHandShakeTask");
        AnonymousClass5 r0 = new TimerTask() {
            public void run() {
                Log.d("[wearable]SPPLinker", "HandShakeTask start " + h.this.h + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + h.this.i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + h.this.j());
                if (h.this.h && !h.this.i) {
                    cancel();
                    h.this.t = null;
                    h.this.c();
                    Intent intent = new Intent("com.mtk.shake_hand_fail");
                    if (h.this.e != null) {
                        h.this.e.sendBroadcast(intent);
                    }
                }
            }
        };
        if (this.t != null) {
            this.t.cancel();
            this.t = null;
        }
        this.t = new Timer();
        this.t.schedule(r0, 10000);
    }

    public void a() {
        Log.d("[wearable]SPPLinker", "close begin");
        if (this.k != null) {
            b(1);
            this.k = null;
        }
        if (this.l != null) {
            b(2);
            this.l = null;
        }
        c(0);
        this.d = null;
        this.a = null;
    }

    public void a(e eVar, boolean z, Context context) {
        Log.d("[wearable]SPPLinker", "init begin");
        super.a(eVar, z, context);
        if (this.g == null || !this.g.isEnabled()) {
            Log.d("[wearable]SPPLinker", "Linker init fail");
        } else {
            h();
        }
    }

    public void a(byte[] bArr) {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    if (this.a != null) {
                        this.a.write(bArr);
                        k.b("[wearable]SPPLinker", "write data=" + new String(bArr));
                        this.j.getData(null, bArr.length);
                    }
                    k.a("[wearable]SPPLinker", "Write data size=" + bArr.length);
                    return;
                }
            } catch (Exception e) {
                Log.e("[wearable]SPPLinker", "Write IOException: " + e.getMessage());
                return;
            }
        }
        Log.e("[wearable]SPPLinker", "write return, error data");
    }

    /* access modifiers changed from: protected */
    public boolean a(int i) {
        Log.e("[wearable]SPPLinker", "requestConnectionPriority fail in SPP Mode");
        return false;
    }

    /* access modifiers changed from: protected */
    public void b() {
        Log.d("[wearable]SPPLinker", "connectRemote begin");
        if (j() != 2 && j() != 3) {
            if (this.l != null) {
                b(2);
                this.l = null;
            }
            if (j() == 2 && this.k != null) {
                b(1);
            }
            this.k = new Thread(this.n);
            this.k.start();
            r();
        }
    }

    public void b(boolean z) {
        Log.d("[wearable]SPPLinker", "scan begin");
        if (this.g == null || !this.g.isEnabled()) {
            Log.d("[wearable]SPPLinker", "scan fail, BT is off");
        } else if (z) {
            if (this.g.isDiscovering()) {
                this.g.cancelDiscovery();
            }
            this.g.startDiscovery();
        } else {
            this.g.cancelDiscovery();
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        Log.d("[wearable]SPPLinker", "disconnect begin");
        if (this.k != null) {
            b(1);
            this.k = null;
        }
        if (this.l != null) {
            b(2);
            this.l = null;
        }
        this.d = null;
    }

    /* access modifiers changed from: protected */
    public void d() {
        Log.d("[wearable]SPPLinker", "[reInit] begin");
        if (this.k != null) {
            b(1);
            this.k = null;
        }
        if (this.l != null) {
            b(2);
            this.l = null;
        }
        h();
    }

    public void e() {
        Log.d("[wearable]SPPLinker", "cancelHandShakeTimer");
        if (this.t != null) {
            this.t.cancel();
            this.t = null;
        }
    }
}
