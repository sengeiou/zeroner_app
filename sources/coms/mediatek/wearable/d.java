package coms.mediatek.wearable;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import coms.mediatek.wearableProfiles.GattListener;
import coms.mediatek.wearableProfiles.GattRequestManager;
import coms.mediatek.wearableProfiles.WearableClientProfileManager;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

class d extends Linker {
    public static UUID a = UUID.fromString("000018A0-0000-1000-8000-00805F9B34FB");
    public static UUID b = UUID.fromString("00002AA0-0000-1000-8000-00805F9B34FB");
    public static UUID c = UUID.fromString("00002AA1-0000-1000-8000-00805F9B34FB");
    /* access modifiers changed from: private */
    public static int l = 20;
    /* access modifiers changed from: private */
    public static boolean m = true;
    /* access modifiers changed from: private */
    public Timer A;
    /* access modifiers changed from: private */
    public BluetoothGatt k;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic n;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic o;
    /* access modifiers changed from: private */
    public boolean p = false;
    /* access modifiers changed from: private */
    public int q = 0;
    /* access modifiers changed from: private */
    public a r = null;
    private LeScanCallback s = new LeScanCallback() {
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            if (bluetoothDevice != null) {
                k.a("[wearable]GATTLinker", "onLeScan=" + bluetoothDevice.getName() + " addr=" + bluetoothDevice.getAddress() + " type=" + bluetoothDevice.getType());
                if (bluetoothDevice.getType() != 1) {
                    d.this.f.a(bluetoothDevice, i, bArr);
                }
            }
        }
    };
    private final GattListener t = new GattListener() {
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            if (d.this.n != null && bluetoothGattCharacteristic.getUuid() == d.this.n.getUuid()) {
                k.a("[wearable]GATTLinker", "[onCharacteristicChanged] mReadState=" + d.this.q + " mIsWriting=" + d.this.p);
                if (d.this.q != 0 || d.this.p) {
                    d.this.q = 2;
                } else {
                    d.this.q = 1;
                    GattRequestManager.getInstance().readCharacteristic(d.this.k, bluetoothGattCharacteristic);
                }
            }
            WearableClientProfileManager.getWearableClientProfileManager().dispatchCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i == 0 && d.this.n != null && bluetoothGattCharacteristic.getUuid() == d.this.n.getUuid() && WearableManager.getInstance().getWorkingMode() == 1) {
                if (d.this.q != 2) {
                    d.this.q = 0;
                }
                byte[] value = bluetoothGattCharacteristic.getValue();
                k.a("[wearable]GATTLinker", "[GATT read] length is " + value.length);
                d.this.f.a(value, value.length);
                d.this.r();
            }
            WearableClientProfileManager.getWearableClientProfileManager().dispatchCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i == 143 || i == 129 || i == 133) {
                Log.d("[wearable]GATTLinker", "[GATT sent] CONNECTION_CONGESTED or status=" + i);
            }
            if ((i == 0 || i == 143 || i == 129 || i == 133) && d.this.o != null && bluetoothGattCharacteristic.getUuid() == d.this.o.getUuid()) {
                k.a("[wearable]GATTLinker", "[GATT sent] GATT_SUCCESS");
                synchronized (d.this) {
                    d.this.p = false;
                    d.this.r();
                }
            }
            WearableClientProfileManager.getWearableClientProfileManager().dispatchCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            boolean z;
            k.a("[wearable]GATTLinker", "GATT onConnectionStateChange callback.");
            d.this.r.removeMessages(103);
            if (d.this.y != null) {
                d.this.y.cancel();
                d.this.y = null;
            }
            if (i2 == 2) {
                k.a("[wearable]GATTLinker", "GATT Connected, mBluetoothGatt=" + d.this.k);
                Log.d("[wearable]GATTLinker", "GATT Connected, sIsMTK=" + d.m);
                if (VERSION.SDK_INT < 18 || VERSION.SDK_INT > 20) {
                    if (VERSION.SDK_INT > 20) {
                        if (VERSION.SDK_INT <= 22 && d.m) {
                            k.a("[wearable]GATTLinker", "MTK Phone for L, only 20bytes MTU");
                            d.l = 20;
                            z = true;
                        } else if (WearableConfig.k()) {
                            k.a("[wearable]GATTLinker", "RequestMTU Black List");
                            d.l = 20;
                            z = true;
                        } else {
                            int c = WearableConfig.c() + 3;
                            boolean requestMtu = (d.this.k != null ? d.this.k : bluetoothGatt).requestMtu(c);
                            k.a("[wearable]GATTLinker", "requestMtu mtu=" + c + " success=" + requestMtu);
                            if (requestMtu) {
                                z = false;
                            } else {
                                d.l = 20;
                            }
                        }
                    }
                    z = true;
                } else {
                    d.l = WearableConfig.b();
                    k.a("[wearable]GATTLinker", "for KK, only 20bytes MTU");
                    z = true;
                }
                if (z) {
                    d.this.a(bluetoothGatt);
                }
            } else if (i2 == 0) {
                if (d.this.y != null) {
                    d.this.y.cancel();
                    d.this.y = null;
                }
                if (d.this.z != null) {
                    d.this.z.cancel();
                    d.this.z = null;
                }
                if (d.this.A != null) {
                    d.this.A.cancel();
                    d.this.A = null;
                }
                Log.d("[wearable]GATTLinker", "GATT Disconnected.");
                if (d.this.k != null) {
                    d.this.k.close();
                    d.this.k = null;
                }
                if (d.this.v()) {
                    d.this.x();
                }
                d.this.t();
                d.this.c(5);
                d.this.q = 0;
                GattRequestManager.getInstance().clearAllRequests();
            }
            WearableClientProfileManager.getWearableClientProfileManager().dispatchConnectionStateChange(bluetoothGatt, i, i2);
        }

        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            WearableClientProfileManager.getWearableClientProfileManager().dispatchDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            WearableClientProfileManager.getWearableClientProfileManager().dispatchDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            Log.d("[wearable]GATTLinker", "onMtuChanged mtu=" + i + " status=" + i2);
            if (i2 == 0) {
                d.l = i - 3;
            } else {
                d.l = 20;
            }
            if (WearableConfig.k()) {
                k.a("[wearable]GATTLinker", "onMtuChanged, RequestMTU Black List");
                d.l = 20;
            }
            if (d.this.k == null) {
                d.this.k = bluetoothGatt;
            }
            if (d.this.r.hasMessages(103)) {
                Log.d("[wearable]GATTLinker", "onMtuChanged, remove MSG_DISCOVER_ACTION, startDiscoverServices");
                d.this.r.removeMessages(103);
                d.this.a(bluetoothGatt);
                return;
            }
            String str = Build.MANUFACTURER;
            Log.d("[wearable]GATTLinker", "onMtuChanged, Manufacturer=" + str);
            if (str == null || !str.toLowerCase().contains("samsung")) {
                d.this.r.sendEmptyMessageDelayed(103, 200);
                return;
            }
            Log.d("[wearable]GATTLinker", "onMtuChanged, send MSG_DISCOVER_ACTION 2000");
            d.this.r.sendEmptyMessageDelayed(103, 2000);
        }

        public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i, int i2) {
            WearableClientProfileManager.getWearableClientProfileManager().dispatchReadRemoteRssi(bluetoothGatt, i, i2);
        }

        public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i) {
            WearableClientProfileManager.getWearableClientProfileManager().dispatchReliableWriteCompleted(bluetoothGatt, i);
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            Log.d("[wearable]GATTLinker", "onServicesDiscovered received: " + i);
            if (d.this.z != null) {
                d.this.z.cancel();
                d.this.z = null;
            }
            if (i == 0) {
                Log.d("[wearable]GATTLinker", "Service Discovered");
                if (d.this.k != null) {
                    for (BluetoothGattService bluetoothGattService : d.this.k.getServices()) {
                        Log.d("[wearable]GATTLinker", "[handleGattService][Fit] service " + bluetoothGattService.getUuid().toString());
                        for (BluetoothGattCharacteristic uuid : bluetoothGattService.getCharacteristics()) {
                            Log.d("[wearable]GATTLinker", "[handleGattService][Fit] service character " + uuid.getUuid().toString());
                        }
                    }
                }
                if (WearableManager.getInstance().getWorkingMode() != 1) {
                    Log.d("[wearable]GATTLinker", "onServicesDiscovered STATE_CONNECTED");
                    d.this.c(3);
                } else if (d.this.s()) {
                    d.this.A();
                    Log.d("[wearable]GATTLinker", "handleGattService return true");
                } else {
                    Log.d("[wearable]GATTLinker", "handleGattService return false");
                    try {
                        if (d.this.k != null) {
                            d.this.k.disconnect();
                        }
                        d.this.c(5);
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                    d.this.d = null;
                    d.this.t();
                }
            }
            WearableClientProfileManager.getWearableClientProfileManager().dispatchServicesDiscovered(bluetoothGatt, i);
        }
    };
    /* access modifiers changed from: private */
    public Runnable u = new Runnable() {
        public void run() {
            Log.d("[wearable]GATTLinker", "mDisRunnable begin " + d.this.j.getDataLength());
            if (d.this.j.getDataLength() != 0) {
                j.a().b();
                d.this.c(6);
                d.this.r.removeCallbacks(d.this.u);
                d.this.r.postDelayed(d.this.u, 1000);
                return;
            }
            try {
                if (d.this.k != null) {
                    d.this.k.disconnect();
                }
                d.this.c(5);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            d.this.d = null;
            d.this.t();
        }
    };
    /* access modifiers changed from: private */
    public Timer v = new Timer(true);
    /* access modifiers changed from: private */
    public Runnable w = new Runnable() {
        public void run() {
            Log.d("[wearable]GATTLinker", "TimerTask stopLeScan");
            d.this.g.stopLeScan(d.this.x);
        }
    };
    /* access modifiers changed from: private */
    public LeScanCallback x = new LeScanCallback() {
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            SharedPreferences sharedPreferences = d.this.e.getSharedPreferences("linker", 0);
            boolean z = sharedPreferences.getBoolean("isReconnect", false);
            String string = sharedPreferences.getString("reconnectAddress", "");
            Log.d("[wearable]GATTLinker", "mAutoScanCallback isReconnect=" + z + " preAddress=" + string);
            if (!z) {
                d.this.w();
            }
            if (z && bluetoothDevice != null && string.equals(bluetoothDevice.getAddress())) {
                Log.d("[wearable]GATTLinker", "mAutoScanCallback auto-connect " + bluetoothDevice.getAddress());
                d.this.w();
                d.this.q();
                d.this.b(bluetoothDevice);
            }
        }
    };
    /* access modifiers changed from: private */
    public Timer y;
    /* access modifiers changed from: private */
    public Timer z;

    private final class a extends Handler {
        private a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 101:
                    k.a("[wearable]GATTLinker", "[GATT handleMessage] MSG_WRITE_ACTION");
                    if (d.this.p || d.this.q == 1) {
                        k.a("[wearable]GATTLinker", "[GATT handleMessage] return, " + d.this.p);
                        return;
                    }
                    d.this.p = true;
                    int dataLength = d.this.j.getDataLength();
                    if (dataLength >= d.l) {
                        dataLength = d.l;
                    }
                    byte[] bArr = new byte[dataLength];
                    int data = d.this.j.getData(bArr, dataLength);
                    if (dataLength == 0 || data != dataLength) {
                        k.a("[wearable]GATTLinker", "[GATT Sent] error, len " + data + " send_length " + dataLength);
                        d.this.p = false;
                        d.this.d(0);
                        return;
                    }
                    d.this.o.setValue(bArr);
                    if (d.this.k != null) {
                        k.a("[wearable]GATTLinker", "[GATT Sent] buffer len: " + data);
                        GattRequestManager.getInstance().writeCharacteristic(d.this.k, d.this.o);
                        return;
                    }
                    k.a("[wearable]GATTLinker", "[GATT Sent] error mBluetoothGatt==null");
                    return;
                case 103:
                    k.a("[wearable]GATTLinker", "[GATT handleMessage] MSG_DISCOVER_ACTION");
                    d.this.a(d.this.k);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void A() {
        Log.d("[wearable]GATTLinker", "runHandShakeTask");
        AnonymousClass9 r0 = new TimerTask() {
            public void run() {
                Log.d("[wearable]GATTLinker", "HandShakeTask start " + d.this.h + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + d.this.i);
                if (d.this.h && !d.this.i) {
                    cancel();
                    d.this.A = null;
                    if (d.this.k != null) {
                        d.this.k.disconnect();
                    }
                    Intent intent = new Intent("com.mtk.shake_hand_fail");
                    if (d.this.e != null) {
                        d.this.e.sendBroadcast(intent);
                    }
                }
            }
        };
        if (this.A != null) {
            this.A.cancel();
            this.A = null;
        }
        this.A = new Timer();
        this.A.schedule(r0, 10000);
    }

    private void B() {
        Method method = null;
        if (0 == 0) {
            try {
                method = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
            } catch (Exception e) {
                k.c("[wearable]GATTLinker", "reflect SystemProperties fail: " + e.toString());
                m = true;
                return;
            }
        }
        String str = (String) method.invoke(null, new Object[]{"ro.mediatek.platform"});
        k.a("[wearable]GATTLinker", "SystemProperties platform=" + str);
        m = str != null && str.length() > 0;
        k.a("[wearable]GATTLinker", "SystemProperties sIsMTK=" + m);
    }

    /* access modifiers changed from: private */
    public void a(BluetoothGatt bluetoothGatt) {
        Log.d("[wearable]GATTLinker", "GATT startDiscoverServices " + this.k);
        if (this.k == null) {
            this.k = bluetoothGatt;
        }
        z();
        if (this.k != null) {
            if (this.k.discoverServices()) {
                return;
            }
        } else if (bluetoothGatt.discoverServices()) {
            return;
        }
        Log.d("[wearable]GATTLinker", "discoverService fail.");
        c();
    }

    /* access modifiers changed from: private */
    public void r() {
        k.a("[wearable]GATTLinker", "makeNextAction mReadState=" + this.q + " mIsWriting=" + this.p);
        if (this.q == 2) {
            k.a("[wearable]GATTLinker", "makeNextAction, need to read");
            this.q = 1;
            GattRequestManager.getInstance().readCharacteristic(this.k, this.n);
        } else if (this.j.getDataLength() > 0) {
            k.a("[wearable]GATTLinker", "makeNextAction, need to write");
            this.r.sendMessage(this.r.obtainMessage(101));
        } else {
            k.a("[wearable]GATTLinker", "makeNextAction, LINKER_IDLE");
            d(0);
        }
    }

    /* access modifiers changed from: private */
    public boolean s() {
        Log.d("[wearable]GATTLinker", "handleGattService begin");
        if (this.k == null) {
            Log.d("[wearable]GATTLinker", "handleGattService return");
            return false;
        }
        boolean z2 = false;
        for (BluetoothGattService bluetoothGattService : this.k.getServices()) {
            String uuid = bluetoothGattService.getUuid().toString();
            List<BluetoothGattCharacteristic> characteristics = bluetoothGattService.getCharacteristics();
            if (uuid.equals(a.toString())) {
                for (BluetoothGattCharacteristic bluetoothGattCharacteristic : characteristics) {
                    String uuid2 = bluetoothGattCharacteristic.getUuid().toString();
                    if (uuid2.equals(c.toString())) {
                        this.o = bluetoothGattCharacteristic;
                        Log.d("[wearable]GATTLinker", "[handleGattService] write_length " + l + ", SDK Level " + VERSION.SDK_INT + ", Chip " + Build.BOARD + ", MTK " + m);
                        if (l != 20) {
                            this.o.setWriteType(2);
                        } else if (VERSION.SDK_INT > 19 || !m) {
                            this.o.setWriteType(2);
                        } else {
                            this.o.setWriteType(1);
                        }
                        k.a("[wearable]GATTLinker", "[handleGattService] STATE_CONNECTED write_type=" + this.o.getWriteType());
                        c(3);
                        this.d = n();
                        if (this.d != null) {
                            k.a("[wearable]GATTLinker", "handleGattService STATE_CONNECTED device=" + this.d.getAddress());
                        }
                        a(LoadJniFunction.a().a(2, "REQV"));
                    } else if (uuid2.equals(b.toString())) {
                        this.n = bluetoothGattCharacteristic;
                        k.a("[wearable]GATTLinker", "[handleGattService] Read Notification " + this.k.setCharacteristicNotification(this.n, true));
                        k.a("[wearable]GATTLinker", "[handleGattSevice] mReadState=" + this.q + " mIsWriting=" + this.p);
                        if (this.q != 0 || this.p) {
                            this.q = 2;
                        } else {
                            this.q = 1;
                            GattRequestManager.getInstance().readCharacteristic(this.k, this.n);
                        }
                    }
                }
                z2 = true;
            }
        }
        return z2;
    }

    /* access modifiers changed from: private */
    public void t() {
        Log.d("[wearable]GATTLinker", "clear");
        this.p = false;
        this.j.clear();
        d(0);
    }

    private void u() {
        if (WearableManager.getInstance().getWorkingMode() != 1) {
            Log.d("[wearable]GATTLinker", "autoReconnect return");
            return;
        }
        SharedPreferences sharedPreferences = this.e.getSharedPreferences("linker", 0);
        boolean z2 = sharedPreferences.getBoolean("isReconnect", false);
        String string = sharedPreferences.getString("reconnectAddress", "");
        Log.d("[wearable]GATTLinker", "autoReconnect isReconnect=" + z2 + " address=" + string);
        if (z2 && BluetoothAdapter.checkBluetoothAddress(string)) {
            BluetoothDevice remoteDevice = this.g.getRemoteDevice(string);
            Log.d("[wearable]GATTLinker", "autoReconnect name=" + remoteDevice.getName());
            b(remoteDevice);
        }
    }

    /* access modifiers changed from: private */
    public boolean v() {
        boolean z2 = this.e.getSharedPreferences("linker", 0).getBoolean("isReconnect", false);
        Log.d("[wearable]GATTLinker", "isGattReconnect isReconnect=" + z2);
        return z2;
    }

    /* access modifiers changed from: private */
    public void w() {
        Log.d("[wearable]GATTLinker", "cancelAutoConnectTask");
        if (this.v != null) {
            this.v.cancel();
            this.v = null;
        }
        if (this.g != null) {
            this.g.stopLeScan(this.x);
        }
        if (this.r != null) {
            this.r.removeCallbacks(this.w);
        }
    }

    /* access modifiers changed from: private */
    public void x() {
        Log.d("[wearable]GATTLinker", "runAutoConnectTask");
        AnonymousClass5 r0 = new TimerTask() {
            public void run() {
                Log.d("[wearable]GATTLinker", "TimerTask start " + WearableConfig.d());
                cancel();
                d.this.v = null;
                d.this.r.removeCallbacks(d.this.w);
                boolean z = d.this.e.getSharedPreferences("linker", 0).getBoolean("isReconnect", false);
                Log.d("[wearable]GATTLinker", "runAutoConnectTask isReconnect=" + z);
                if (!z) {
                    Log.d("[wearable]GATTLinker", "runAutoConnectTask return");
                    return;
                }
                d.this.r.postDelayed(d.this.w, (long) (WearableConfig.d() * 1000));
                d.this.g.startLeScan(d.this.x);
            }
        };
        if (this.v != null) {
            this.v.cancel();
            this.v = null;
        }
        this.v = new Timer();
        this.v.schedule(r0, 3000);
    }

    private void y() {
        Log.d("[wearable]GATTLinker", "runCallbackTask");
        AnonymousClass7 r0 = new TimerTask() {
            public void run() {
                Log.d("[wearable]GATTLinker", "CallbackTask start");
                cancel();
                d.this.y = null;
                if (d.this.k != null) {
                    d.this.k.close();
                    d.this.k = null;
                }
                d.this.t();
                d.this.c(5);
                d.this.q = 0;
                GattRequestManager.getInstance().clearAllRequests();
            }
        };
        if (this.y != null) {
            this.y.cancel();
            this.y = null;
        }
        this.y = new Timer();
        this.y.schedule(r0, 22000);
    }

    private void z() {
        Log.d("[wearable]GATTLinker", "runServiceCallbackTask");
        AnonymousClass8 r0 = new TimerTask() {
            public void run() {
                Log.d("[wearable]GATTLinker", "ServiceCallbackTask start");
                cancel();
                d.this.z = null;
                if (d.this.k != null) {
                    d.this.k.close();
                    d.this.k = null;
                }
                d.this.t();
                d.this.c(5);
                d.this.q = 0;
                GattRequestManager.getInstance().clearAllRequests();
            }
        };
        if (this.z != null) {
            this.z.cancel();
            this.z = null;
        }
        this.z = new Timer();
        this.z.schedule(r0, 20000);
    }

    public void a() {
        Log.d("[wearable]GATTLinker", "close begin");
        if (this.r != null) {
            this.r.removeCallbacksAndMessages(null);
            Looper looper = this.r.getLooper();
            if (looper != null) {
                looper.quit();
            }
        }
        this.d = null;
        c(0);
        t();
        w();
    }

    public void a(e eVar, boolean z2, Context context) {
        Log.d("[wearable]GATTLinker", "init begin");
        B();
        this.j.init(204800);
        super.a(eVar, z2, context);
        HandlerThread handlerThread = new HandlerThread("GATTLinkerTHread");
        handlerThread.start();
        this.r = new a(handlerThread.getLooper());
        GattRequestManager.getInstance().registerListener(this.t);
        if (this.g == null || !this.g.isEnabled()) {
            Log.d("[wearable]GATTLinker", "Linker init fail");
        } else {
            u();
        }
    }

    public void a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            Log.d("[wearable]GATTLinker", "write, error data");
            return;
        }
        synchronized (this) {
            this.j.setData(bArr);
            d(1);
            k.a("[wearable]GATTLinker", "write, mIsWriting=" + this.p + " connect=" + j() + " mReadState=" + this.q);
            if (!this.p && j() == 3 && this.q == 0) {
                this.r.sendMessage(this.r.obtainMessage(101));
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i) {
        int j = j();
        Log.d("[wearable]GATTLinker", "requestConnectionPriority GATT Mode, connect=" + j);
        if (j != 3 || !this.i || this.k == null) {
            Log.e("[wearable]GATTLinker", "requestConnectionPriority unknown error");
            return false;
        }
        boolean requestConnectionPriority = this.k.requestConnectionPriority(i);
        Log.d("[wearable]GATTLinker", "requestConnectionPriority GATT res=" + requestConnectionPriority);
        return requestConnectionPriority;
    }

    /* access modifiers changed from: protected */
    public void b() {
        Log.d("[wearable]GATTLinker", "doConnect begin");
        if (j() == 2 || j() == 3) {
            Log.d("[wearable]GATTLinker", "doConnect return");
            return;
        }
        if (this.k != null) {
            this.k.close();
            this.k = null;
        }
        w();
        c(2);
        BluetoothGattCallback gattCallback = GattRequestManager.getInstance().getGattCallback();
        y();
        if (VERSION.SDK_INT >= 23) {
            Log.d("[wearable]GATTLinker", "connectGATT (TRANSPORT_LE) start-call");
            this.k = n().connectGatt(this.e, false, gattCallback, 2);
            Log.d("[wearable]GATTLinker", "connectGATT (TRANSPORT_LE) start-call done");
        } else {
            this.k = n().connectGatt(this.e, false, gattCallback);
        }
        if (!(this.k == null || this.k.getDevice() == null)) {
            Log.d("[wearable]GATTLinker", "doConnect device=" + this.k.getDevice().getName());
        }
        if (this.k == null) {
            Log.d("[wearable]GATTLinker", "doConnect device (null mBluetoothGatt)=" + n().getName());
            c(n());
            return;
        }
        c(this.k.getDevice());
    }

    public void b(boolean z2) {
        if (this.g == null || !this.g.isEnabled()) {
            Log.d("[wearable]GATTLinker", "scan fail, BT is off");
        } else if (z2) {
            this.g.stopLeScan(this.s);
            Log.d("[wearable]GATTLinker", "scan success " + this.g.startLeScan(this.s));
        } else {
            this.g.stopLeScan(this.s);
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        Log.d("[wearable]GATTLinker", "doDisConnect begin");
        if (this.d == null && WearableManager.getInstance().getWorkingMode() == 1) {
            Log.d("[wearable]GATTLinker", "doDisConnect return");
            return;
        }
        this.r.removeCallbacks(this.u);
        this.r.postDelayed(this.u, 10);
    }

    /* access modifiers changed from: protected */
    public void d() {
        Log.d("[wearable]GATTLinker", "[reInit] begin");
        B();
        HandlerThread handlerThread = new HandlerThread("GATTLinkerTHread");
        handlerThread.start();
        this.r = new a(handlerThread.getLooper());
        c(0);
        t();
        u();
    }

    public void e() {
        Log.d("[wearable]GATTLinker", "cancelHandShakeTimer");
        if (this.A != null) {
            this.A.cancel();
            this.A = null;
        }
    }

    public int f() {
        return l + 3;
    }

    public int g() {
        if (this.o != null) {
            return this.o.getWriteType();
        }
        return 0;
    }
}
