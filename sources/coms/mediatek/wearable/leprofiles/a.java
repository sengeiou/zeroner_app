package coms.mediatek.wearable.leprofiles;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.ArrayList;
import java.util.List;

class a {
    private static a l = null;
    /* access modifiers changed from: private */
    public BluetoothManager a;
    /* access modifiers changed from: private */
    public BluetoothGattServer b;
    private Thread c;
    /* access modifiers changed from: private */
    public Handler d;
    private List<BluetoothGattService> e = new ArrayList();
    /* access modifiers changed from: private */
    public List<BluetoothGattServerCallback> f = new ArrayList();
    private List<LeServer> g = new ArrayList();
    private GattServicesAddedCallback h;
    private int i = 0;
    /* access modifiers changed from: private */
    public String j = null;
    /* access modifiers changed from: private */
    public int k = 0;
    private boolean m = false;
    private Context n;
    private final BluetoothGattServerCallback o = new BluetoothGattServerCallback() {
        public void onCharacteristicReadRequest(BluetoothDevice bluetoothDevice, int i, int i2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            if (bluetoothDevice == null || !bluetoothDevice.getAddress().equals(a.this.j)) {
                Log.e("LeServerController", "onCharacteristicReadRequest, address: " + bluetoothDevice + " not equal mConnectedAddress: " + a.this.j);
            }
            if (bluetoothGattCharacteristic == null) {
                Log.e("LeServerController", "onCharacteristicReadRequest(), characteristic == null");
            } else if (bluetoothGattCharacteristic.getService() == null) {
                Log.e("LeServerController", "onCharacteristicReadRequest(), service == null");
            } else {
                Log.d("LeServerController", "onCharacteristicReadRequest - incoming request: " + bluetoothDevice.getName());
                Log.d("LeServerController", "onCharacteristicReadRequest -        requestId: " + i);
                Log.d("LeServerController", "onCharacteristicReadRequest -           offset: " + i2);
                Log.d("LeServerController", "onCharacteristicReadRequest -             uuid: " + bluetoothGattCharacteristic.getUuid().toString());
                if (a.this.f != null) {
                    for (BluetoothGattServerCallback onCharacteristicReadRequest : a.this.f) {
                        onCharacteristicReadRequest.onCharacteristicReadRequest(bluetoothDevice, i, i2, bluetoothGattCharacteristic);
                    }
                }
            }
        }

        public void onCharacteristicWriteRequest(BluetoothDevice bluetoothDevice, int i, BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z, boolean z2, int i2, byte[] bArr) {
            if (bluetoothDevice == null || !bluetoothDevice.getAddress().equals(a.this.j)) {
                Log.e("LeServerController", "onCharacteristicWriteRequest, address: " + bluetoothDevice + " not equal mConnectedAddress: " + a.this.j);
            }
            if (bluetoothGattCharacteristic == null) {
                Log.e("LeServerController", "onCharacteristicWriteRequest(), characteristic == null");
            } else if (bluetoothGattCharacteristic.getService() != null) {
                Log.d("LeServerController", "onCharacteristicWriteRequest - offset:" + i2 + " value.length:" + bArr.length + " preparedWrite:" + z + " responseNeeded:" + z2);
                if (a.this.f != null) {
                    for (BluetoothGattServerCallback onCharacteristicWriteRequest : a.this.f) {
                        onCharacteristicWriteRequest.onCharacteristicWriteRequest(bluetoothDevice, i, bluetoothGattCharacteristic, z, z2, i2, bArr);
                    }
                }
            } else {
                Log.e("LeServerController", "onCharacteristicWriteRequest(), service == null");
            }
        }

        public void onConnectionStateChange(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("LeServerController", "onConnectionStateChange- device=" + bluetoothDevice + " status=" + i + " newState=" + i2 + "mConnectedAddress=" + a.this.j);
            String str = bluetoothDevice != null ? bluetoothDevice.getAddress() : null;
            if (i2 == 2) {
                if (str != null) {
                    a.this.j = str;
                    Log.d("LeServerController", "onConnectionStateChange, address=" + str);
                } else {
                    Log.e("LeServerController", "onConnectionStateChange, address is null");
                    return;
                }
            }
            if (i2 != 2) {
                if (str == null || !str.equals(a.this.j)) {
                    Log.e("LeServerController", "onConnectionStateChange, address not equal mConnectedAddress");
                    return;
                } else {
                    Log.d("LeServerController", "onConnectionStateChange, address matched, disconnected");
                    a.this.j = null;
                }
            }
            if (a.this.f != null) {
                for (BluetoothGattServerCallback onConnectionStateChange : a.this.f) {
                    onConnectionStateChange.onConnectionStateChange(bluetoothDevice, i, i2);
                }
            }
        }

        public void onDescriptorReadRequest(BluetoothDevice bluetoothDevice, int i, int i2, BluetoothGattDescriptor bluetoothGattDescriptor) {
            if (bluetoothDevice == null || !bluetoothDevice.getAddress().equals(a.this.j)) {
                Log.e("LeServerController", "onDescriptorReadRequest, address=" + bluetoothDevice + " not equal mConnectedAddress=" + a.this.j);
            }
            if (bluetoothGattDescriptor != null) {
                BluetoothGattCharacteristic characteristic = bluetoothGattDescriptor.getCharacteristic();
                if (characteristic == null) {
                    Log.e("LeServerController", "onDescriptorReadRequest(), characteristic == null");
                } else if (characteristic.getService() != null) {
                    Log.d("LeServerController", "onDescriptorReadRequest - incoming request=" + bluetoothDevice.getName());
                    Log.d("LeServerController", "onDescriptorReadRequest -        requestId=" + i);
                    Log.d("LeServerController", "onDescriptorReadRequest -           offset=" + i2);
                    Log.d("LeServerController", "onDescriptorReadRequest -             uuid=" + bluetoothGattDescriptor.getUuid().toString());
                    if (a.this.f != null) {
                        for (BluetoothGattServerCallback onDescriptorReadRequest : a.this.f) {
                            onDescriptorReadRequest.onDescriptorReadRequest(bluetoothDevice, i, i2, bluetoothGattDescriptor);
                        }
                    }
                } else {
                    Log.e("LeServerController", "onDescriptorReadRequest(), service == null");
                }
            } else {
                Log.e("LeServerController", "onDescriptorReadRequest(), descriptor == null");
            }
        }

        public void onDescriptorWriteRequest(BluetoothDevice bluetoothDevice, int i, BluetoothGattDescriptor bluetoothGattDescriptor, boolean z, boolean z2, int i2, byte[] bArr) {
            if (bluetoothDevice == null || !bluetoothDevice.getAddress().equals(a.this.j)) {
                Log.e("LeServerController", "onDescriptorWriteRequest, address=" + bluetoothDevice + " not equal mConnectedAddress=" + a.this.j);
            }
            if (bluetoothGattDescriptor != null) {
                BluetoothGattCharacteristic characteristic = bluetoothGattDescriptor.getCharacteristic();
                if (characteristic == null) {
                    Log.e("LeServerController", "onDescriptorWriteRequest(), characteristic == null");
                } else if (characteristic.getService() != null) {
                    Log.d("LeServerController", "onDescriptorWriteRequest - offset=" + i2 + " value.length=" + bArr.length + " preparedWrite=" + z + " responseNeeded:" + z2 + ", uuid=" + bluetoothGattDescriptor.getUuid().toString());
                    if (a.this.f != null) {
                        for (BluetoothGattServerCallback onDescriptorWriteRequest : a.this.f) {
                            onDescriptorWriteRequest.onDescriptorWriteRequest(bluetoothDevice, i, bluetoothGattDescriptor, z, z2, i2, bArr);
                        }
                    }
                } else {
                    Log.e("LeServerController", "onDescriptorWriteRequest(), service == null");
                }
            } else {
                Log.e("LeServerController", "onDescriptorWriteRequest(), descriptor == null");
            }
        }

        public void onExecuteWrite(BluetoothDevice bluetoothDevice, int i, boolean z) {
            if (bluetoothDevice == null || !bluetoothDevice.getAddress().equals(a.this.j)) {
                Log.e("LeServerController", "onExecuteWrite, address=" + bluetoothDevice + " not equal mConnectedAddress=" + a.this.j);
            }
            Log.d("LeServerController", "onExecuteWrite- device=" + bluetoothDevice + " requestId=" + i + " execute=" + z);
            if (a.this.f != null) {
                for (BluetoothGattServerCallback onExecuteWrite : a.this.f) {
                    onExecuteWrite.onExecuteWrite(bluetoothDevice, i, z);
                }
            }
        }

        public void onServiceAdded(int i, BluetoothGattService bluetoothGattService) {
            Log.d("LeServerController", "onServiceAdded - status=" + i + "service=" + bluetoothGattService.getUuid());
            if (i == 0) {
                Log.d("LeServerController", "onServiceAdded - add service success");
                if (a.this.d.hasMessages(3)) {
                    a.this.d.removeMessages(3);
                }
                if (!a.this.d.hasMessages(0) && !a.this.d.hasMessages(1)) {
                    a.this.d.obtainMessage(2).sendToTarget();
                }
                for (BluetoothGattServerCallback onServiceAdded : a.this.f) {
                    onServiceAdded.onServiceAdded(i, bluetoothGattService);
                }
            }
        }
    };

    private a(Context context) {
        this.n = context;
        this.a = (BluetoothManager) context.getSystemService("bluetooth");
    }

    public static a a(Context context) {
        if (l == null) {
            l = new a(context);
        }
        return l;
    }

    /* access modifiers changed from: private */
    public void c() {
        this.d = new Handler() {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        Log.d("LeServerController", "MSG_OPEN_GATT_SERVER:");
                        if (a.this.a == null) {
                            Log.e("LeServerController", "openGattServer, mBluetoothManager is null");
                            return;
                        }
                        BluetoothAdapter adapter = a.this.a.getAdapter();
                        if (adapter == null || !adapter.isEnabled()) {
                            Log.d("LeServerController", "openGattServer, BT is Off");
                            return;
                        } else if (a.this.b == null) {
                            a.this.e();
                            return;
                        } else {
                            Log.d("LeServerController", "already started services");
                            return;
                        }
                    case 1:
                    case 4:
                        Log.d("LeServerController", "MSG_CLOSE_GATT_SERVER:");
                        a.this.k = 0;
                        if (a.this.b != null) {
                            a.this.b.close();
                            a.this.b = null;
                            a.this.g();
                            if (message.what == 4) {
                                getLooper().quit();
                                return;
                            }
                            return;
                        }
                        return;
                    case 2:
                        a.this.k = a.this.k + 1;
                        a.this.d();
                        return;
                    case 3:
                        Log.e("LeServerController", "onService time out");
                        a.this.k = a.this.k + 1;
                        a.this.d();
                        return;
                    default:
                        Log.e("LeServerController", "mHanderThread, unknown message type");
                        return;
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public boolean d() {
        Log.d("LeServerController", "addService id=" + this.k);
        if (this.e != null) {
            if (this.k >= this.e.size()) {
                f();
                return true;
            }
            BluetoothGattService bluetoothGattService = (BluetoothGattService) this.e.get(this.k);
            if (bluetoothGattService == null) {
                f();
                return true;
            }
            try {
                if (this.b != null) {
                    Message obtainMessage = this.d.obtainMessage(3);
                    if (this.b.addService(bluetoothGattService)) {
                        if (!this.d.hasMessages(1) && !this.d.hasMessages(0)) {
                            this.d.sendMessageDelayed(obtainMessage, 3000);
                        }
                        return true;
                    }
                    Log.e("LeServerController", "addService return false");
                    if (!this.d.hasMessages(1) && !this.d.hasMessages(0)) {
                        this.d.sendMessage(obtainMessage);
                    }
                } else {
                    Log.e("LeServerController", "mBluetoothGattServer is null");
                }
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
                if (!this.d.hasMessages(1) && !this.d.hasMessages(0)) {
                    this.d.obtainMessage(1).sendToTarget();
                    this.d.obtainMessage(3).sendToTarget();
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean e() {
        boolean z = false;
        Log.d("LeServerController", "addFirstService()");
        this.k = 0;
        if (this.b != null) {
            Log.d("LeServerController", "already started services -- after sleep");
        } else {
            try {
                this.b = this.a.openGattServer(this.n, this.o);
                if (this.b != null) {
                    z = d();
                } else {
                    Log.e("LeServerController", "addFirstService, mBluetoothGattServer is null, times=" + this.i);
                    if (!this.d.hasMessages(1)) {
                        if (this.i >= 4) {
                            this.m = false;
                            if (this.h != null) {
                                this.h.onAllServicesChanged(this.m);
                            }
                        } else {
                            this.i++;
                            this.d.sendMessageDelayed(this.d.obtainMessage(0), 500);
                        }
                    }
                }
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
                this.b = null;
            }
            g();
        }
        return z;
    }

    private void f() {
        Log.d("LeServerController", "allServiceAddedSuccessed");
        this.m = true;
        if (this.h != null) {
            this.h.onAllServicesChanged(this.m);
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        if (this.g != null && this.g.size() > 0) {
            for (LeServer bluetoothGattServer : this.g) {
                bluetoothGattServer.setBluetoothGattServer(this.b);
            }
        }
    }

    public synchronized void a(List<LeServer> list) {
        boolean z = false;
        synchronized (this) {
            Log.d("LeServerController", "enter addLeServers()");
            if (list != null && list.size() > 0) {
                synchronized (this.e) {
                    if (this.k >= this.e.size()) {
                        z = true;
                    }
                    this.g.addAll(list);
                    for (LeServer leServer : list) {
                        this.e.addAll(leServer.getHardCodeProfileServices());
                        this.f.add(leServer.getGattServerCallback());
                    }
                }
                if (this.d == null) {
                    this.c = new Thread() {
                        public void run() {
                            Looper.prepare();
                            a.this.c();
                            a.this.a();
                            Looper.loop();
                        }
                    };
                    this.c.start();
                } else {
                    Log.d("LeServerController", "addServicesFinished=" + z);
                    if (z && !this.d.hasMessages(1) && !this.d.hasMessages(0) && !this.d.hasMessages(3) && !this.d.hasMessages(4)) {
                        this.k--;
                        this.d.obtainMessage(2).sendToTarget();
                    }
                }
            }
        }
    }

    public boolean a() {
        Log.d("LeServerController", "openGattServer start");
        if (this.d == null || this.e == null) {
            Log.e("LeServerController", "openGattServer error, mHandler == null");
        } else {
            if (this.d.hasMessages(0)) {
                this.d.removeMessages(0);
            }
            if (this.d.hasMessages(2)) {
                this.d.removeMessages(2);
            }
            if (this.d.hasMessages(3)) {
                this.d.removeMessages(3);
            }
            this.d.sendMessageDelayed(this.d.obtainMessage(0), 200);
        }
        return true;
    }

    public void b() {
        Log.d("LeServerController", "closeGattServer start");
        this.m = false;
        if (this.h != null) {
            this.h.onAllServicesChanged(this.m);
        }
        if (this.d != null) {
            if (this.d.hasMessages(1)) {
                this.d.removeMessages(1);
            }
            if (this.d.hasMessages(0)) {
                this.d.removeMessages(0);
            }
            if (this.d.hasMessages(2)) {
                this.d.removeMessages(2);
            }
            if (this.d.hasMessages(3)) {
                this.d.removeMessages(3);
            }
            this.d.obtainMessage(1).sendToTarget();
        }
    }
}
