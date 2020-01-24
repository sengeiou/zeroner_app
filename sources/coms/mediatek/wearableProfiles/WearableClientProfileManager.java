package coms.mediatek.wearableProfiles;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class WearableClientProfileManager {
    private static WearableClientProfileManager a;
    private HandlerThread b;
    private ArrayList<WearableClientProfile> c = new ArrayList<>();
    private BluetoothGatt d;

    private WearableClientProfileManager() {
        if (this.b == null) {
            this.b = new HandlerThread("ClientProfileHandlerThread");
            this.b.start();
        }
    }

    private void a(int i, b bVar) {
        Log.d("[W-Client]WearableClientProfileManager", "callbackType=" + i);
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            WearableClientProfile wearableClientProfile = (WearableClientProfile) it.next();
            if (wearableClientProfile.preCheckNeedSendMessage(i, bVar)) {
                if (wearableClientProfile.checkCallbackRunningInBluetoothThread()) {
                    wearableClientProfile.processCallback(i, bVar);
                } else {
                    Handler msgHandler = wearableClientProfile.getMsgHandler();
                    if (msgHandler != null) {
                        msgHandler.obtainMessage(i, bVar).sendToTarget();
                    } else {
                        Log.e("[W-Client]WearableClientProfileManager", "get handler is null");
                    }
                }
            }
        }
    }

    public static WearableClientProfileManager getWearableClientProfileManager() {
        if (a == null) {
            a = new WearableClientProfileManager();
        }
        return a;
    }

    public void dispatchCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchCharacteristicChanged characteristic=" + (bluetoothGattCharacteristic == null ? null : bluetoothGattCharacteristic.getUuid()));
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.a(bluetoothGattCharacteristic);
        a(2001, bVar);
    }

    public void dispatchCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchCharacteristicRead status=" + i + " characteristic=" + (bluetoothGattCharacteristic == null ? null : bluetoothGattCharacteristic.getUuid()));
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.a(bluetoothGattCharacteristic);
        bVar.a(i);
        a(WearableClientProfile.MSG_CHARAC_READ, bVar);
    }

    public void dispatchCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchCharacteristicWrite status=" + i + ", characteristic=" + (bluetoothGattCharacteristic == null ? null : bluetoothGattCharacteristic.getUuid()));
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.a(bluetoothGattCharacteristic);
        bVar.a(i);
        a(WearableClientProfile.MSG_CHARAC_WRITE, bVar);
    }

    public void dispatchConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchConnectionStateChange status=" + i + " newState=" + i2);
        if (i == 0) {
            if (i2 == 0) {
                this.d = null;
                Iterator it = this.c.iterator();
                while (it.hasNext()) {
                    ((WearableClientProfile) it.next()).setBluetoothGatt(null);
                }
            } else if (i2 == 2) {
                this.d = bluetoothGatt;
                if (this.d == null) {
                    Log.e("[W-Client]WearableClientProfileManager", "dispatchConnectionStateChange, gatt is null");
                }
                Iterator it2 = this.c.iterator();
                while (it2.hasNext()) {
                    ((WearableClientProfile) it2.next()).setBluetoothGatt(this.d);
                }
            }
        }
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.a(i);
        bVar.b(i2);
        a(1001, bVar);
    }

    public void dispatchDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchDescriptorRead status=" + i + " descriptor=" + (bluetoothGattDescriptor == null ? null : bluetoothGattDescriptor.getUuid()));
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.a(bluetoothGattDescriptor);
        bVar.a(i);
        a(WearableClientProfile.MSG_DESCRIPTOR_READ, bVar);
    }

    public void dispatchDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchDescriptorWrite status=" + i + " descriptor=" + (bluetoothGattDescriptor == null ? null : bluetoothGattDescriptor.getUuid()));
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.a(bluetoothGattDescriptor);
        bVar.a(i);
        a(2012, bVar);
    }

    public void dispatchReadRemoteRssi(BluetoothGatt bluetoothGatt, int i, int i2) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchReadRemoteRssi status=" + i2 + " rssi=" + i);
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.c(i);
        bVar.a(i2);
        a(3001, bVar);
    }

    public void dispatchReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchReadRemoteRssi status=" + i);
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.a(i);
        a(WearableClientProfile.MSG_RELIABLE_WRITE_COMPLETED, bVar);
    }

    public void dispatchServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
        Log.d("[W-Client]WearableClientProfileManager", "dispatchServicesDiscovered status=" + i);
        b bVar = new b();
        bVar.a(bluetoothGatt);
        bVar.a(i);
        a(1002, bVar);
    }

    public void registerWearableClientProfile(WearableClientProfile wearableClientProfile, Looper looper) {
        Log.d("[W-Client]WearableClientProfileManager", "registerWearableClientProfile");
        if (wearableClientProfile != null) {
            Log.d("[W-Client]WearableClientProfileManager", "registerWearableClientProfile set BluetoothGatt=" + this.d);
            wearableClientProfile.setBluetoothGatt(this.d);
            this.c.add(wearableClientProfile);
            if (looper == null) {
                wearableClientProfile.initMessageHandler(this.b.getLooper());
            } else {
                wearableClientProfile.initMessageHandler(looper);
            }
        }
    }

    public void unRegisterWearableClientProfile(WearableClientProfile wearableClientProfile) {
        if (wearableClientProfile != null) {
            wearableClientProfile.setBluetoothGatt(null);
            wearableClientProfile.uninitMsgHandler();
            this.c.remove(wearableClientProfile);
        }
    }
}
