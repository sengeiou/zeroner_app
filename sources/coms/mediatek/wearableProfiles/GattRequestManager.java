package coms.mediatek.wearableProfiles;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.LinkedList;

public class GattRequestManager {
    private static GattRequestManager c;
    private final int a = 0;
    /* access modifiers changed from: private */
    public LinkedList<a> b = new LinkedList<>();
    /* access modifiers changed from: private */
    public int d = 0;
    /* access modifiers changed from: private */
    public GattListener e;
    /* access modifiers changed from: private */
    public boolean f = false;
    private final BluetoothGattCallback g = new BluetoothGattCallback() {
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            Log.d("[wearable]GATTRequestManager", "onCharacteristicChanged");
            GattRequestManager.this.e.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            Log.d("[wearable]GATTRequestManager", "onCharacteristicRead" + i);
            GattRequestManager.this.d = 0;
            GattRequestManager.this.a();
            GattRequestManager.this.e.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            Log.d("[wearable]GATTRequestManager", "onCharacteristicWrite " + i);
            GattRequestManager.this.d = 0;
            GattRequestManager.this.a();
            GattRequestManager.this.e.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            Log.d("[wearable]GATTRequestManager", "onConnectionStateChange " + i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + i2);
            GattRequestManager.this.f = false;
            GattRequestManager.this.e.onConnectionStateChange(bluetoothGatt, i, i2);
            synchronized (GattRequestManager.this.b) {
                GattRequestManager.this.b.clear();
                GattRequestManager.this.d = 0;
            }
        }

        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            Log.d("[wearable]GATTRequestManager", "onDescriptorRead " + i);
            GattRequestManager.this.d = 0;
            GattRequestManager.this.a();
            GattRequestManager.this.e.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            Log.d("[wearable]GATTRequestManager", "onDescriptorWrite " + i);
            GattRequestManager.this.d = 0;
            GattRequestManager.this.a();
            GattRequestManager.this.e.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            Log.d("[wearable]GATTRequestManager", "onMtuChanged mtu=" + i + " status=" + i2);
            GattRequestManager.this.e.onMtuChanged(bluetoothGatt, i, i2);
        }

        public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i, int i2) {
            Log.d("[wearable]GATTRequestManager", "onReadRemoteRssi " + i2);
            GattRequestManager.this.e.onReadRemoteRssi(bluetoothGatt, i, i2);
        }

        public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i) {
            Log.d("[wearable]GATTRequestManager", "onReliableWriteCompleted " + i);
            GattRequestManager.this.e.onReliableWriteCompleted(bluetoothGatt, i);
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            Log.d("[wearable]GATTRequestManager", "onServicesDiscovered " + i);
            GattRequestManager.this.f = true;
            GattRequestManager.this.e.onServicesDiscovered(bluetoothGatt, i);
        }
    };

    private GattRequestManager() {
    }

    /* access modifiers changed from: private */
    public void a() {
        Log.d("[wearable]GATTRequestManager", "runRequest currState=" + this.d + " currSize=" + this.b.size());
        if (this.d != 1) {
            synchronized (this.b) {
                if (this.b.size() > 0) {
                    a aVar = (a) this.b.get(0);
                    this.d = 1;
                    aVar.a();
                    this.b.remove(0);
                }
            }
        }
    }

    private void a(a aVar) {
        if (this.f) {
            synchronized (this.b) {
                this.b.add(aVar);
            }
            Log.d("[wearable]GATTRequestManager", "addReauest currSize=" + this.b.size());
            a();
            return;
        }
        Log.e("[wearable]GATTRequestManager", "GATT connection have not initialized");
    }

    public static GattRequestManager getInstance() {
        if (c == null) {
            c = new GattRequestManager();
        }
        return c;
    }

    public void clearAllRequests() {
        synchronized (this.b) {
            this.b.clear();
        }
        this.d = 0;
        Log.d("[wearable]GATTRequestManager", "clearAllRequests currSize=" + this.b.size());
    }

    public BluetoothGattCallback getGattCallback() {
        return this.g;
    }

    public void readCharacteristic(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.d("[wearable]GATTRequestManager", "readCharacteristic");
        a(new a(bluetoothGatt, bluetoothGattCharacteristic, 1));
    }

    public void readDescriptor(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor) {
        Log.d("[wearable]GATTRequestManager", "writeCharacteristic");
        a(new a(bluetoothGatt, bluetoothGattDescriptor, 1));
    }

    public void registerListener(GattListener gattListener) {
        Log.d("[wearable]GATTRequestManager", "registerListener");
        this.e = gattListener;
    }

    public void writeCharacteristic(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.d("[wearable]GATTRequestManager", "writeCharacteristic");
        a(new a(bluetoothGatt, bluetoothGattCharacteristic, 2));
    }

    public void writeDescriptor(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor) {
        Log.d("[wearable]GATTRequestManager", "writeCharacteristic");
        a(new a(bluetoothGatt, bluetoothGattDescriptor, 2));
    }
}
