package no.nordicsemi.android.dialog;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.content.Intent;
import com.iwown.ble_module.mtk_ble.leprofiles.fmpserver.FmpServerAlertService;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.math.BigInteger;
import no.nordicsemi.android.dialog.async.DeviceConnectTask;
import no.nordicsemi.android.dialog.data.Statics;

public class Callback extends BluetoothGattCallback {
    public static String TAG = "Callback";
    private BluetoothManager mBluetoothManager;
    private Context mContext;
    DeviceConnectTask task;

    public Callback(Context context, BluetoothManager bluetoothManager, DeviceConnectTask task2) {
        this.mContext = context;
        this.mBluetoothManager = bluetoothManager;
        this.task = task2;
    }

    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        KLog.i(TAG, "le onConnectionStateChange [" + newState + "]");
        if (newState == 2) {
            KLog.i(TAG, "le device connected");
            gatt.discoverServices();
        } else if (newState == 0) {
            KLog.i(TAG, "le device disconnected");
        }
        Intent intent = new Intent();
        intent.setAction(Statics.CONNECTION_STATE_UPDATE);
        intent.putExtra(FmpServerAlertService.INTENT_STATE, newState);
        this.mContext.sendBroadcast(intent);
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        KLog.i(TAG, "onServicesDiscovered");
        BluetoothGattSingleton.setGatt(gatt);
        Intent intent = new Intent();
        intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
        intent.putExtra("step", 0);
        this.mContext.sendBroadcast(intent);
    }

    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        boolean sendUpdate = true;
        int index = -1;
        int step = -1;
        if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_MANUFACTURER_NAME_STRING)) {
            index = 0;
        } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_MODEL_NUMBER_STRING)) {
            index = 1;
        } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_FIRMWARE_REVISION_STRING)) {
            index = 2;
        } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_SOFTWARE_REVISION_STRING)) {
            index = 3;
        } else if (characteristic.getUuid().equals(Statics.SPOTA_MEM_INFO_UUID)) {
            step = 5;
        } else {
            sendUpdate = false;
        }
        if (sendUpdate) {
            KLog.d(TAG, "onCharacteristicRead: " + index);
            Intent intent = new Intent();
            intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
            if (index >= 0) {
                intent.putExtra("characteristic", index);
                intent.putExtra("value", new String(characteristic.getValue()));
            } else {
                intent.putExtra("step", step);
                intent.putExtra("value", characteristic.getIntValue(20, 0));
            }
            this.mContext.sendBroadcast(intent);
        }
        super.onCharacteristicRead(gatt, characteristic, status);
    }

    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == 0) {
            int step = -1;
            if (characteristic.getUuid().equals(Statics.SPOTA_GPIO_MAP_UUID)) {
                step = 4;
            } else if (characteristic.getUuid().equals(Statics.SPOTA_PATCH_LEN_UUID)) {
                step = this.mBluetoothManager.type == 1 ? 5 : 7;
            } else if (!characteristic.getUuid().equals(Statics.SPOTA_MEM_DEV_UUID) && characteristic.getUuid().equals(Statics.SPOTA_PATCH_DATA_UUID) && this.mBluetoothManager.chunkCounter != -1) {
                this.mBluetoothManager.sendBlock();
            }
            if (step > 0) {
                Intent intent = new Intent();
                intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
                intent.putExtra("step", step);
                this.mContext.sendBroadcast(intent);
            }
        } else {
            L.file("write failed: " + status, 6);
        }
        super.onCharacteristicWrite(gatt, characteristic, status);
    }

    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        KLog.d(TAG, "onDescriptorWrite");
        if (descriptor.getCharacteristic().getUuid().equals(Statics.SPOTA_SERV_STATUS_UUID)) {
            Intent intent = new Intent();
            intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
            intent.putExtra("step", 2);
            this.mContext.sendBroadcast(intent);
        }
        this.task.publishProgess(gatt);
    }

    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        int value = new BigInteger(characteristic.getValue()).intValue();
        String stringValue = String.format("%#10x", new Object[]{Integer.valueOf(value)});
        KLog.d("changed :", stringValue);
        int step = -1;
        int error = -1;
        int memDevValue = -1;
        if (stringValue.trim().equals("0x10")) {
            step = 3;
        } else if (stringValue.trim().equals("0x2")) {
            step = this.mBluetoothManager.type == 1 ? 5 : 8;
        } else if (stringValue.trim().equals("0x3") || stringValue.trim().equals("0x1")) {
            memDevValue = value;
        } else {
            error = Integer.parseInt(stringValue.trim().replace("0x", ""));
        }
        if (step >= 0 || error >= 0 || memDevValue >= 0) {
            Intent intent = new Intent();
            intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
            intent.putExtra("step", step);
            intent.putExtra("error", error);
            intent.putExtra("memDevValue", memDevValue);
            this.mContext.sendBroadcast(intent);
        }
    }
}
