package com.iwown.ble_module.mtk_ble.leprofiles.fmpclient;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.util.Log;
import com.iwown.ble_module.mtk_ble.leprofiles.BleGattUuid.Char;
import com.iwown.ble_module.mtk_ble.leprofiles.BleGattUuid.Service;
import coms.mediatek.wearableProfiles.GattRequestManager;
import coms.mediatek.wearableProfiles.WearableClientProfile;
import coms.mediatek.wearableProfiles.WearableClientProfileRegister;
import java.util.TreeSet;
import java.util.UUID;

public class FmpGattClient {
    private static final String TAG = "FmpGattClient";
    private static FmpGattClient sInstance = null;
    private int ALERT_LEVEL_OFFSET;
    /* access modifiers changed from: private */
    public boolean DBG;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic mAlertLevelChar;
    /* access modifiers changed from: private */
    public BluetoothGatt mGatt;
    private GattCallbackImpl mGattCallbackImpl;

    private class GattCallbackImpl extends WearableClientProfile {
        private String TAG;

        private GattCallbackImpl() {
            this.TAG = FmpGattClient.TAG;
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onConnectionStateChange, status = " + status + ", newState = " + newState + ", gatt = " + gatt);
            }
            if (2 == newState) {
                FmpGattClient.this.mGatt = gatt;
                Log.i(this.TAG, "connect success");
            } else if (newState == 0) {
                FmpClientStatusRegister.getInstance().setFindMeStatus(0);
                FmpGattClient.this.mGatt = null;
                FmpGattClient.this.mAlertLevelChar = null;
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onServicesDiscovered");
            }
            BluetoothGattService alertService = gatt.getService(Service.IMMEDIATE_ALERT);
            if (alertService != null) {
                FmpGattClient.this.mAlertLevelChar = alertService.getCharacteristic(Char.ALERT_LEVEL);
            } else {
                Log.e(this.TAG, "not support IMMEDIATE_ALERT service");
            }
            if (FmpGattClient.this.mAlertLevelChar == null) {
                Log.e(this.TAG, "not support Immediate_alert, alert level");
                FmpClientStatusRegister.getInstance().setFindMeStatus(0);
            } else {
                FmpClientStatusRegister.getInstance().setFindMeStatus(1);
            }
            BluetoothGattCharacteristic alerStatusChar = null;
            if (alertService != null) {
                alerStatusChar = alertService.getCharacteristic(Char.ALERT_STATUS);
            }
            if (alerStatusChar != null) {
                gatt.setCharacteristicNotification(alerStatusChar, true);
            } else {
                Log.e(this.TAG, "not support ALERT_STATUS");
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onCharacteristicChanged: ");
            }
            if (characteristic != null) {
                Log.d(this.TAG, "onCharacteristicChanged ID = " + characteristic.getUuid());
                if (Char.ALERT_STATUS.equals(characteristic.getUuid())) {
                    FmpGattClient.this.onRemoteStopAlert();
                }
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onCharacteristicRead: ");
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onCharacteristicWrite: ");
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onDescriptorRead:");
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onDescriptorWrite: ");
            }
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onReadRemoteRssi: ");
            }
        }

        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            if (FmpGattClient.this.DBG) {
                Log.d(this.TAG, "onReliableWriteCompleted: ");
            }
        }
    }

    public static FmpGattClient getInstance() {
        if (sInstance == null) {
            sInstance = new FmpGattClient();
        }
        return sInstance;
    }

    private FmpGattClient() {
        this.DBG = true;
        this.ALERT_LEVEL_OFFSET = 0;
        this.mAlertLevelChar = null;
        this.mGattCallbackImpl = null;
        this.mGatt = null;
        this.mGattCallbackImpl = new GattCallbackImpl();
        TreeSet<UUID> uuidSet = new TreeSet<>();
        uuidSet.add(Char.ALERT_LEVEL);
        uuidSet.add(Char.ALERT_STATUS);
        this.mGattCallbackImpl.addUuids(uuidSet);
        WearableClientProfileRegister.registerWearableClientProfile(this.mGattCallbackImpl, null);
        Log.d(TAG, "init finished");
    }

    public void findTarget(int level) {
        if (this.mGatt == null) {
            Log.e(TAG, "findTarget::mGatt is null,return");
        } else if (this.mAlertLevelChar != null) {
            FmpClientStatusRegister register = FmpClientStatusRegister.getInstance();
            if (level == 0) {
                register.setFindMeStatus(1);
            } else {
                register.setFindMeStatus(2);
            }
            this.mAlertLevelChar.setValue(level, 17, this.ALERT_LEVEL_OFFSET);
            GattRequestManager.getInstance().writeCharacteristic(this.mGatt, this.mAlertLevelChar);
        } else {
            Log.e(TAG, "findTarget, mAlertLevelChar == null");
        }
    }

    public void onRemoteStopAlert() {
        FmpClientStatusRegister register = FmpClientStatusRegister.getInstance();
        if (register.getFindMeStatus() != 0) {
            register.setFindMeStatus(1);
        }
    }
}
