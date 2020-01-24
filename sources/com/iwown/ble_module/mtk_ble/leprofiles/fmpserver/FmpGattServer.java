package com.iwown.ble_module.mtk_ble.leprofiles.fmpserver;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.util.Log;
import com.iwown.ble_module.mtk_ble.leprofiles.BleGattUuid.Char;
import com.iwown.ble_module.mtk_ble.leprofiles.BleGattUuid.Service;
import coms.mediatek.wearable.leprofiles.LeServer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FmpGattServer implements LeServer {
    private static final int ALERT_LEVEL_OFFSET = 0;
    private static final boolean DBG = true;
    private static final String TAG = "FmpGattServer";
    private static final boolean VDBG = true;
    private static FmpGattServer sInstance = null;
    /* access modifiers changed from: private */
    public FmpServerAlerter mAlerter;
    /* access modifiers changed from: private */
    public BluetoothGattServer mBluetoothGattServer;
    private Context mContext;
    private final BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            if (characteristic != null) {
                if (!Service.IMMEDIATE_ALERT.equals(characteristic.getService().getUuid())) {
                    return;
                }
            }
            byte[] data = characteristic.getValue();
            Log.d(FmpGattServer.TAG, "onCharacteristicReadRequest - incoming request: " + device.getName());
            Log.d(FmpGattServer.TAG, "onCharacteristicReadRequest -        requestId: " + requestId);
            Log.d(FmpGattServer.TAG, "onCharacteristicReadRequest -           offset: " + offset);
            Log.d(FmpGattServer.TAG, "onCharacteristicReadRequest -             uuid: " + characteristic.getUuid().toString());
            if (FmpGattServer.this.mBluetoothGattServer != null) {
                FmpGattServer.this.mBluetoothGattServer.sendResponse(device, requestId, 0, offset, Arrays.copyOfRange(data, offset, data.length));
            }
        }

        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            byte[] newValue;
            if (characteristic != null) {
                if (!Service.IMMEDIATE_ALERT.equals(characteristic.getService().getUuid())) {
                    return;
                }
            }
            byte[] oldValue = characteristic.getValue();
            Log.d(FmpGattServer.TAG, "onCharacteristicWriteRequest - offset:" + offset + " value.length:" + value.length + " preparedWrite:" + preparedWrite + " responseNeeded:" + responseNeeded);
            if (oldValue == null || oldValue.length < value.length + offset) {
                newValue = new byte[(value.length + offset)];
                if (oldValue != null) {
                    System.arraycopy(oldValue, 0, newValue, 0, oldValue.length);
                }
                System.arraycopy(value, 0, newValue, offset, value.length);
            } else {
                newValue = new byte[(value.length + offset)];
                System.arraycopy(oldValue, 0, newValue, 0, offset);
                System.arraycopy(value, 0, newValue, offset, value.length);
            }
            Log.v(FmpGattServer.TAG, "onCharacteristicWriteRequest- preparedWrite:" + preparedWrite);
            if (preparedWrite) {
                Log.v(FmpGattServer.TAG, "onCharacteristicWriteRequest - preparedWrite write\n");
            } else {
                Log.v(FmpGattServer.TAG, "onCharacteristicWriteRequest - a normal write\n");
                if (characteristic != null) {
                    characteristic.setValue(newValue);
                    Integer level = characteristic.getIntValue(17, 0);
                    if (level != null) {
                        Log.d(FmpGattServer.TAG, "level = " + level.intValue() + ", mAlerter = " + FmpGattServer.this.mAlerter);
                        FmpGattServer.this.mAlerter.alert(level.intValue());
                    }
                }
            }
            Log.v(FmpGattServer.TAG, "onCharacteristicWriteRequest- responseNeeded:" + responseNeeded);
            if (responseNeeded && FmpGattServer.this.mBluetoothGattServer != null) {
                FmpGattServer.this.mBluetoothGattServer.sendResponse(device, requestId, 0, offset, value);
            }
        }

        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            if (newState == 0) {
                FmpGattServer.this.mAlerter.alert(0);
            }
        }

        public void onServiceAdded(int status, BluetoothGattService service) {
        }

        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
        }

        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
        }

        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
        }
    };

    public static FmpGattServer getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FmpGattServer(context);
        }
        return sInstance;
    }

    private FmpGattServer(Context context) {
        this.mContext = context;
        this.mAlerter = makeAlerter();
    }

    public void setCustomizedAlerter(FmpServerAlerter alerter) {
        this.mAlerter = alerter;
    }

    public List<BluetoothGattService> getHardCodeProfileServices() {
        List<BluetoothGattService> listGattService = new ArrayList<>();
        BluetoothGattService alertService = new BluetoothGattService(Service.IMMEDIATE_ALERT, 0);
        BluetoothGattCharacteristic alertLevelChar = new BluetoothGattCharacteristic(Char.ALERT_LEVEL, 4, 16);
        alertLevelChar.setValue(0, 17, 0);
        alertService.addCharacteristic(alertLevelChar);
        listGattService.add(alertService);
        return listGattService;
    }

    public BluetoothGattServerCallback getGattServerCallback() {
        return this.mGattServerCallback;
    }

    public void setBluetoothGattServer(BluetoothGattServer server) {
        this.mBluetoothGattServer = server;
    }

    private DefaultAlerter makeAlerter() {
        Log.d(TAG, "makeAlerter: alerterType");
        return new DefaultAlerter(this.mContext);
    }
}
