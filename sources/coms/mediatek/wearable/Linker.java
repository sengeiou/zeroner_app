package coms.mediatek.wearable;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

class Linker implements a {
    /* access modifiers changed from: private */
    public int a;
    private BluetoothDevice b;
    private final Handler c = new Handler() {
        public void handleMessage(Message message) {
            Log.d("[wearable]Linker", "[mLinkerHandler] handleMessage, msg.what=" + message.what);
            switch (message.what) {
                case 0:
                    if (Linker.this.g != null && Linker.this.g.isEnabled() && Linker.this.a != 3) {
                        if ((Linker.this instanceof h) || (Linker.this instanceof d)) {
                            Linker.this.b();
                            return;
                        }
                        Message obtainMessage = obtainMessage(0);
                        removeMessages(0);
                        sendMessageDelayed(obtainMessage, 500);
                        return;
                    }
                    return;
                case 1:
                    Linker.this.c();
                    return;
                case 2:
                    Linker.this.f();
                    return;
                case 3:
                    Linker.this.c(4);
                    Linker.this.g();
                    return;
                default:
                    return;
            }
        }
    };
    protected BluetoothDevice d;
    protected Context e;
    protected e f;
    protected final BluetoothAdapter g = BluetoothAdapter.getDefaultAdapter();
    protected boolean h = true;
    protected boolean i = false;
    protected final DataBuffer j = new DataBuffer();
    /* access modifiers changed from: private */
    public int k;
    /* access modifiers changed from: private */
    public int l;
    /* access modifiers changed from: private */
    public String m;

    protected class DataBuffer {
        private byte[] b;
        private int c;
        private int d;
        private boolean e;
        private int f;

        protected DataBuffer() {
        }

        public void changeDataBuffer(int i) {
            if (Linker.this instanceof d) {
                k.a("[wearable]DataBuffer", "changeDataBuffer=" + i + " mMaxSize=" + this.f);
                if (this.c != this.d || this.e) {
                    k.a("[wearable]DataBuffer", "changeDataBuffer error");
                } else if (i > this.f) {
                    this.b = new byte[(i + 100)];
                    k.a("[wearable]DataBuffer", "changeDataBuffer mBuffer=" + this.b.length);
                    this.f = i;
                    this.c = 0;
                    this.d = 0;
                } else {
                    k.a("[wearable]DataBuffer", "needn't changeDataBuffer");
                }
            }
        }

        public void clear() {
            k.a("[wearable]DataBuffer", "[clear]");
            this.c = 0;
            this.d = 0;
            this.e = false;
            Linker.this.l = 0;
            Linker.this.k = 0;
        }

        public int getData(byte[] bArr, int i) {
            if (Linker.this instanceof d) {
                if (bArr == null) {
                    k.a("[wearable]DataBuffer", "[getData] data == null");
                    return 0;
                } else if (this.c != this.d || this.e) {
                    int dataLength = getDataLength();
                    if (i > dataLength) {
                        k.a("[wearable]DataBuffer", "[getData] length > dataLen=" + dataLength);
                        if (this.d > this.c) {
                            System.arraycopy(this.b, this.c, bArr, 0, dataLength);
                        } else if (this.d < this.c) {
                            int i2 = this.f - this.c;
                            System.arraycopy(this.b, this.c, bArr, 0, i2);
                            System.arraycopy(this.b, 0, bArr, i2, this.d);
                        } else {
                            System.arraycopy(this.b, 0, bArr, 0, this.f);
                        }
                        this.c = this.d;
                        i = dataLength;
                    } else {
                        if (this.c + i > this.f) {
                            int i3 = this.f - this.c;
                            System.arraycopy(this.b, this.c, bArr, 0, i3);
                            System.arraycopy(this.b, 0, bArr, i3, i - i3);
                        } else {
                            System.arraycopy(this.b, this.c, bArr, 0, i);
                        }
                        this.c = (this.c + i) % this.f;
                    }
                    if (this.c == this.d) {
                        this.e = false;
                        k.a("[wearable]DataBuffer", "[getData] mIsFull=false");
                    }
                    k.a("[wearable]DataBuffer", "[getData] success resulteLenth=" + i);
                } else {
                    k.a("[wearable]DataBuffer", "[getData] buffer is empty");
                    return 0;
                }
            }
            if (Linker.this.k <= 0) {
                return i;
            }
            Linker.this.l = Linker.this.l + i;
            k.a("[wearable]DataBuffer", "SentDataProgess " + Linker.this.m + " =" + Linker.this.l + "/" + Linker.this.k);
            Linker.this.f.a(((float) Linker.this.l) / ((float) Linker.this.k), Linker.this.m);
            if (Linker.this.l < Linker.this.k) {
                return i;
            }
            Linker.this.l = 0;
            Linker.this.k = 0;
            Linker.this.m = "";
            return i;
        }

        public int getDataLength() {
            int i = this.d > this.c ? this.d - this.c : this.d < this.c ? (this.d + this.f) - this.c : this.e ? this.f : 0;
            k.a("[wearable]DataBuffer", "[getDataLength] " + i + " f=" + this.c + " r=" + this.d + " mIsFull=" + this.e);
            return i;
        }

        public void init(int i) {
            this.f = i;
            this.b = new byte[i];
            this.c = 0;
            this.d = 0;
            this.e = false;
        }

        public boolean setData(byte[] bArr) {
            if (bArr == null) {
                k.a("[wearable]DataBuffer", "[setData] data == null");
                return false;
            } else if (getDataLength() + bArr.length > this.f) {
                k.a("[wearable]DataBuffer", "[setData] too many data, " + this.f + " < " + bArr.length);
                return false;
            } else if (this.c != this.d || !this.e) {
                if (this.d + bArr.length > this.f) {
                    int i = this.f - this.d;
                    System.arraycopy(bArr, 0, this.b, this.d, i);
                    System.arraycopy(bArr, i, this.b, 0, bArr.length - i);
                } else {
                    System.arraycopy(bArr, 0, this.b, this.d, bArr.length);
                }
                this.d = (this.d + bArr.length) % this.f;
                k.a("[wearable]DataBuffer", "[setData] success data.length=" + bArr.length + " f=" + this.c + " r=" + this.d);
                if (this.c == this.d) {
                    this.e = true;
                    k.a("[wearable]DataBuffer", "[setData] mIsFull=true;");
                }
                return true;
            } else {
                k.a("[wearable]DataBuffer", "[setData] buffer is full");
                return false;
            }
        }
    }

    private boolean e() {
        if (!(this instanceof d) || WearableManager.getInstance().getWorkingMode() != 1) {
            return (this instanceof h) && WearableManager.getInstance().getWorkingMode() == 0;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void f() {
        boolean a2 = b.a(this);
        Log.d("[wearable]Linker", "startConnectReLEScann success=" + a2);
        if (!a2) {
            c(4);
            g();
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        Log.d("[wearable]Linker", "cancelConnectScan");
        this.c.removeMessages(2);
        this.c.removeMessages(3);
        b.a();
    }

    public void a() {
    }

    public void a(int i2, String str) {
        this.k = this.j.getDataLength() + i2;
        this.l = 0;
        this.m = str;
        k.a("[wearable]Linker", "mSentSize=" + this.k + " mSessionTag=" + this.m);
    }

    public void a(BluetoothDevice bluetoothDevice) {
        Log.d("[wearable]Linker", "[onDeviceFound] device begin");
        if (this instanceof d) {
            Log.d("[wearable]Linker", "[onDeviceFound] GATTLinker return");
        } else {
            this.f.a(bluetoothDevice, 0, null);
        }
    }

    public void a(BluetoothDevice bluetoothDevice, int i2) {
        Log.d("[wearable]Linker", "[onProfileConnect] device begin");
        if (bluetoothDevice == null) {
            Log.d("[wearable]Linker", "[onProfileConnect] return");
        } else if (this.a != 2 && this.a != 3 && !(this instanceof d)) {
            int i3 = 3000;
            if (i2 == 1) {
                i3 = 20000;
            } else if (i2 == 2) {
                i3 = 1000;
            }
            c(bluetoothDevice);
            Message obtainMessage = this.c.obtainMessage();
            obtainMessage.what = 0;
            obtainMessage.obj = bluetoothDevice;
            this.c.removeMessages(0);
            this.c.sendMessageDelayed(obtainMessage, (long) i3);
        }
    }

    public void a(e eVar, boolean z, Context context) {
        Log.d("[wearable]Linker", "init begin");
        this.a = 0;
        this.h = z;
        this.e = context;
        if (!this.h) {
            this.i = true;
        }
        this.f = eVar;
    }

    public void a(boolean z) {
        Log.d("[wearable]Linker", "[onBTSwitch] on=" + z);
        if (z) {
            d();
        } else {
            a();
        }
    }

    public void a(byte[] bArr) {
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void b() {
    }

    public void b(BluetoothDevice bluetoothDevice) {
        Log.d("[wearable]Linker", "connect begin");
        c(bluetoothDevice);
        if (this.a == 2 || this.a == 3 || this.a == 6 || this.g == null || !this.g.isEnabled()) {
            Log.d("[wearable]Linker", "connect return");
        } else if (VERSION.SDK_INT < 23 || !(this instanceof d) || WearableManager.getInstance().getWorkingMode() != 1 || bluetoothDevice.getType() != 0) {
            Message obtainMessage = this.c.obtainMessage();
            obtainMessage.what = 0;
            obtainMessage.obj = bluetoothDevice;
            this.c.removeMessages(0);
            this.c.sendMessageDelayed(obtainMessage, 500);
        } else {
            Log.d("[wearable]Linker", "connect return for Unknown Device: " + bluetoothDevice.getAddress());
            this.c.sendEmptyMessageDelayed(2, 1000);
            this.c.sendEmptyMessageDelayed(3, 60000);
            c(2);
        }
    }

    public void b(boolean z) {
    }

    /* access modifiers changed from: protected */
    public void c() {
    }

    public void c(int i2) {
        Log.d("[wearable]Linker", "oldState=" + this.a + " newState=" + i2);
        if (i2 == 3) {
            this.c.removeMessages(0);
        }
        if (this.a != i2) {
            int i3 = this.a;
            this.a = i2;
            if (e()) {
                this.f.a(i3, i2);
            }
        } else if (e() && this.a == 0) {
            this.f.a(this.a, this.a);
        }
    }

    public void c(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            Log.d("[wearable]Linker", "[setConnectDevice] device == null");
            this.c.removeMessages(0);
        } else if (VERSION.SDK_INT < 18) {
            Log.d("[wearable]Linker", "[setConnectDevice] <18 " + bluetoothDevice.getAddress());
        } else {
            Log.d("[wearable]Linker", "[setConnectDevice] " + bluetoothDevice.getAddress() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + bluetoothDevice.getType());
        }
        this.b = bluetoothDevice;
        if (e()) {
            this.f.a(bluetoothDevice);
        }
    }

    public void c(boolean z) {
        this.i = z;
        Log.d("[wearable]Linker", "setHandShakeDone, support auto-reconnect " + WearableConfig.i());
        if (z && (this instanceof d)) {
            if (WearableConfig.i()) {
                d(true);
            }
            ((d) this).e();
        }
        if (z && (this instanceof h)) {
            if (WearableConfig.i()) {
                Log.d("[wearable]Linker", "set SPP Reconnect");
                SharedPreferences sharedPreferences = this.e.getSharedPreferences("linker", 0);
                boolean z2 = sharedPreferences.getBoolean("isSPPReconnect", false);
                Log.d("[wearable]Linker", "setHandShakeDone " + z2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + sharedPreferences.getString("reconnectSPPAddress", ""));
                String str = "";
                if (this.d != null) {
                    str = this.d.getAddress();
                    Log.d("[wearable]Linker", "setHandShakeDone mConnectedDevice " + str);
                } else if (this.b != null) {
                    str = this.b.getAddress();
                    Log.d("[wearable]Linker", "setHandShakeDone mConnectDevice " + str);
                }
                Editor edit = sharedPreferences.edit();
                edit.putBoolean("isSPPReconnect", true);
                edit.putString("reconnectSPPAddress", str);
                edit.commit();
            }
            ((h) this).e();
        }
    }

    /* access modifiers changed from: protected */
    public void d() {
    }

    public void d(int i2) {
        Log.d("[wearable]Linker", "setWorkingState: " + i2);
        if (e()) {
            this.f.a(i2);
        }
    }

    public void d(BluetoothDevice bluetoothDevice) {
        Log.d("[wearable]Linker", "mConnectScanCallback device=" + bluetoothDevice);
        if (this.b == null || this.b.getType() != 0) {
            Log.d("[wearable]Linker", "mConnectScanCallback return " + this.b);
            this.a = 0;
            g();
            b(this.b);
            return;
        }
        String address = this.b.getAddress();
        Log.d("[wearable]Linker", "mConnectScanCallback Addr=" + bluetoothDevice.getAddress() + " for " + address);
        if (address.equals(bluetoothDevice.getAddress())) {
            int type = bluetoothDevice.getType();
            Log.d("[wearable]Linker", "mConnectScanCallback start connect type=" + type);
            if (type == 2 || type == 3) {
                this.a = 0;
                g();
                b(bluetoothDevice);
                return;
            }
            c(4);
            g();
        }
    }

    /* access modifiers changed from: protected */
    public void d(boolean z) {
        Log.d("[wearable]Linker", "updateReconnectInfo enable=" + z);
        SharedPreferences sharedPreferences = this.e.getSharedPreferences("linker", 0);
        boolean z2 = sharedPreferences.getBoolean("isReconnect", false);
        Log.d("[wearable]Linker", "updateReconnectInfo isReconnect=" + z2 + " preAddress=" + sharedPreferences.getString("reconnectAddress", ""));
        String str = "";
        if (this.d != null) {
            str = this.d.getAddress();
            Log.d("[wearable]Linker", "updateReconnectInfo mConnectedDevice address=" + this.d.getAddress() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.d.getType());
        } else if (this.b != null) {
            str = this.b.getAddress();
            Log.d("[wearable]Linker", "updateReconnectInfo mConnectDevice address=" + this.b.getAddress() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.b.getType());
        }
        if (z == z2) {
            Log.d("[wearable]Linker", "updateReconnectInfo enable=isReconnect");
        }
        Editor edit = sharedPreferences.edit();
        edit.putBoolean("isReconnect", z);
        edit.putString("reconnectAddress", str);
        edit.commit();
    }

    public void e(int i2) {
        this.j.changeDataBuffer(i2);
    }

    public int j() {
        return this.a;
    }

    public boolean k() {
        return this.c.hasMessages(0);
    }

    public void l() {
        Log.d("[wearable]Linker", "disconnect begin");
        if (this.a == 6) {
            Log.d("[wearable]Linker", "disconnect return");
            return;
        }
        Message obtainMessage = this.c.obtainMessage();
        obtainMessage.what = 1;
        this.c.removeMessages(1);
        this.c.sendMessage(obtainMessage);
    }

    public BluetoothDevice m() {
        return this.d;
    }

    public BluetoothDevice n() {
        return this.b;
    }

    public boolean o() {
        return this.g != null && this.g.isEnabled() && this.a == 3;
    }

    public boolean p() {
        return !this.h || this.i;
    }

    /* access modifiers changed from: protected */
    public void q() {
        Log.d("[wearable]Linker", "disableReconnect begin");
        Editor edit = this.e.getSharedPreferences("linker", 0).edit();
        edit.putBoolean("isReconnect", false);
        edit.commit();
    }
}
