package coms.mediatek.wearableProfiles;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.UUID;

public class WearableClientProfile {
    public static final int MSG_CHARAC_CHANGED = 2001;
    public static final int MSG_CHARAC_READ = 2002;
    public static final int MSG_CHARAC_WRITE = 2003;
    public static final int MSG_CONNECTION_STATE_CHANGE = 1001;
    public static final int MSG_DESCRIPTOR_READ = 2011;
    public static final int MSG_DESCRIPTOR_WRITE = 2012;
    public static final int MSG_RELIABLE_WRITE_COMPLETED = 4001;
    public static final int MSG_RSSI_READ = 3001;
    public static final int MSG_SERVICES_DISCOVERED = 1002;
    private TreeSet<UUID> a = new TreeSet<>();
    private boolean b = false;
    private boolean c = false;
    private Handler d = null;
    private BluetoothGatt e = null;
    private boolean f = false;

    public final void addUuids(TreeSet<UUID> treeSet) {
        this.a.addAll(treeSet);
    }

    public final boolean checkCallbackRunningInBluetoothThread() {
        return this.f;
    }

    public final void enableRssi(boolean z) {
        this.b = z;
    }

    public final BluetoothGatt getBluetoothGatt() {
        return this.e;
    }

    public final Handler getMsgHandler() {
        return this.d;
    }

    public final void initMessageHandler(Looper looper) {
        this.d = new Handler(looper) {
            public void handleMessage(Message message) {
                Log.d("[W-Client]WearableClientProfile", "type=" + message.what);
                WearableClientProfile.this.processCallback(message.what, (b) message.obj);
            }
        };
    }

    public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
    }

    public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
    }

    public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
    }

    public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
    }

    public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
    }

    public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i, int i2) {
    }

    public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i) {
    }

    public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
    }

    public final boolean preCheckNeedSendMessage(int i, b bVar) {
        switch (i) {
            case 1001:
            case 1002:
                return true;
            case 2001:
            case MSG_CHARAC_READ /*2002*/:
            case MSG_CHARAC_WRITE /*2003*/:
                BluetoothGattCharacteristic b2 = bVar.b();
                if (b2 == null) {
                    return false;
                }
                UUID uuid = b2.getUuid();
                if (uuid == null) {
                    return false;
                }
                Iterator it = this.a.iterator();
                while (it.hasNext()) {
                    if (uuid.equals((UUID) it.next())) {
                        return true;
                    }
                }
                return false;
            case MSG_DESCRIPTOR_READ /*2011*/:
            case 2012:
                BluetoothGattDescriptor c2 = bVar.c();
                if (c2 == null) {
                    return false;
                }
                UUID uuid2 = c2.getUuid();
                if (uuid2 == null) {
                    return false;
                }
                Iterator it2 = this.a.iterator();
                while (it2.hasNext()) {
                    if (uuid2.equals((UUID) it2.next())) {
                        return true;
                    }
                }
                return false;
            case 3001:
                return this.b;
            case MSG_RELIABLE_WRITE_COMPLETED /*4001*/:
                return this.c;
            default:
                return false;
        }
    }

    public final void processCallback(int i, b bVar) {
        BluetoothGatt a2 = bVar.a();
        BluetoothGattCharacteristic b2 = bVar.b();
        BluetoothGattDescriptor c2 = bVar.c();
        int d2 = bVar.d();
        int e2 = bVar.e();
        int f2 = bVar.f();
        switch (i) {
            case 1001:
                onConnectionStateChange(a2, d2, e2);
                return;
            case 1002:
                onServicesDiscovered(a2, d2);
                return;
            case 2001:
                onCharacteristicChanged(a2, b2);
                return;
            case MSG_CHARAC_READ /*2002*/:
                onCharacteristicRead(a2, b2, d2);
                return;
            case MSG_CHARAC_WRITE /*2003*/:
                onCharacteristicWrite(a2, b2, d2);
                return;
            case MSG_DESCRIPTOR_READ /*2011*/:
                onDescriptorRead(a2, c2, d2);
                return;
            case 2012:
                onDescriptorWrite(a2, c2, d2);
                return;
            case 3001:
                onReadRemoteRssi(a2, f2, d2);
                return;
            case MSG_RELIABLE_WRITE_COMPLETED /*4001*/:
                onReliableWriteCompleted(a2, d2);
                return;
            default:
                return;
        }
    }

    public final void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
        this.e = bluetoothGatt;
    }

    public final void setCallbackRunningInBluetoothThread(boolean z) {
        this.f = z;
    }

    public final void uninitMsgHandler() {
        this.d = null;
    }
}
