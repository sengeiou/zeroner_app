package com.iwown.ble_module.proto.ble;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.google.common.primitives.UnsignedBytes;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.iwown.utils.HexUtil;
import com.iwown.ble_module.proto.cmd.ProtoBufReceiverCmd;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.utils.Util;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.Arrays;

public class ProtoBaseReceiver extends BroadcastReceiver {
    private static ProtoBaseReceiver receiver;
    private Context context;
    private IDataReceiveHandler mIDataReceiveHandler;

    private ProtoBaseReceiver(Context context2, IDataReceiveHandler iDataReceiveHandler) {
        LocalBroadcastManager.getInstance(context2).registerReceiver(this, BleService.getIntentFilter());
        this.mIDataReceiveHandler = iDataReceiveHandler;
    }

    public static ProtoBaseReceiver getInstance(Context context2, IDataReceiveHandler iDataReceiveHandler) {
        if (receiver == null) {
            synchronized (ProtoBaseReceiver.class) {
                if (receiver == null) {
                    receiver = new ProtoBaseReceiver(context2, iDataReceiveHandler);
                }
            }
        }
        return receiver;
    }

    public void onReceive(Context context2, Intent intent) {
        this.context = context2;
        String action = intent.getAction();
        if (BleService.BLE_GATT_CONNECTED.equals(action)) {
            connectStatue(true);
        } else if (BleService.BLE_GATT_DISCONNECTED.equals(action)) {
            connectStatue(false);
        } else if (BleService.BLE_CHARACTERISTIC_DISCOVERED.equals(action)) {
            if (intent.getBooleanExtra("CONNECTED", false)) {
                onCharacteristicWriteData();
            }
        } else if (BleService.BLE_DEVICE_FOUND.equals(action)) {
            Bundle extras = intent.getExtras();
            BluetoothDevice device = (BluetoothDevice) extras.getParcelable("DEVICE");
            byte[] scanRecord = extras.getByteArray("SCAN_RECORD");
            int rssi = extras.getInt("RSSI");
            if (device != null) {
                String name = device.getName();
                if (TextUtils.isEmpty(name)) {
                    if (scanRecord == null) {
                        scanRecord = new byte[0];
                    }
                    name = Util.isDevNameNULl(scanRecord);
                }
                WristBand wristBand = new WristBand(name, device.getAddress(), rssi);
                onScanResult(wristBand);
            }
        } else if (BleService.BLE_CHARACTERISTIC_CHANGED.equals(action)) {
            byte[] data = intent.getByteArrayExtra("VALUE");
            if (data == null) {
                KLog.e(" data  is null ");
            } else if (data == null || data.length > 3) {
                int dataType = ((data[7] & UnsignedBytes.MAX_VALUE) << 8) | (data[6] & 255);
                byte[] bytes = Arrays.copyOfRange(data, 8, data.length);
                String result = "";
                switch (dataType) {
                    case 0:
                        result = JsonTool.toJson(ProtoBufReceiverCmd.parse00DeviceInfo(bytes));
                        break;
                    case 1:
                        result = ProtoBufReceiverCmd.parse01PeerInfo(bytes);
                        break;
                    case 2:
                        result = ProtoBufReceiverCmd.parse02MsgNotify(bytes);
                        break;
                    case 3:
                        result = ProtoBufReceiverCmd.parse03Weather(bytes);
                        break;
                    case 4:
                        result = ProtoBufReceiverCmd.parse04Alarm(bytes);
                        break;
                    case 5:
                        result = ProtoBufReceiverCmd.parse05Sedentariness(bytes);
                        break;
                    case 6:
                        ProtoBufReceiverCmd.parse06DeviceConf(bytes);
                        break;
                    case 7:
                        result = ProtoBufReceiverCmd.parse07Calendar(bytes);
                        break;
                    case 8:
                        result = ProtoBufReceiverCmd.parse08MotorConf(bytes);
                        break;
                    case 9:
                        result = ProtoBufReceiverCmd.parse09DataInfo(bytes);
                        break;
                    case 112:
                        result = JsonTool.toJson(ProtoBufReceiverCmd.parse70RealInfo(bytes));
                        break;
                    case 128:
                        result = ProtoBufReceiverCmd.parse80HisData(bytes);
                        break;
                    case Opcodes.ADD_INT /*144*/:
                        result = ProtoBufReceiverCmd.parse90FileDescUpdate(bytes);
                        break;
                    case 65535:
                        result = ProtoBufReceiverCmd.parseFFConnectParmas(bytes);
                        break;
                }
                if (!TextUtils.isEmpty(result)) {
                    onDataArrived(4, dataType, result);
                }
            } else {
                KLog.e(" data error  length is lower " + HexUtil.formatHexString(data));
            }
        } else if (BleService.BLE_NO_CALLBACK.equals(action)) {
            connectStatue(false);
        } else if (BleService.BLE_SERVICE_DISCOVERED.equals(action)) {
            onDiscoverService(intent.getStringExtra("SERVICE_UUID"));
        } else if (BleService.BLE_CHARACTERISTIC_DISCOVERED.equals(action)) {
            onDiscoverCharacter(intent.getStringExtra("CHARACTER_UUID"));
        } else if (BleService.BLE_CHARACTERISTIC_WRITE.equals(action)) {
            onCommonSend(intent.getByteArrayExtra("DATA"));
        } else if (BleService.BLE_CONNECT_ERROR_257.equals(action)) {
            onBluetoothError();
        } else if (BleService.BLE_CHARACTERISTIC_CHANGED.equals(action)) {
            onCharacteristicChange(intent.getStringExtra("ADDRESS"));
        }
    }

    /* access modifiers changed from: protected */
    public void onScanResult(WristBand dev) {
        if (this.mIDataReceiveHandler != null) {
            this.mIDataReceiveHandler.onScanResult(dev);
        }
    }

    /* access modifiers changed from: protected */
    public void onCharacteristicWriteData() {
        this.mIDataReceiveHandler.onBluetoothInit();
    }

    /* access modifiers changed from: protected */
    public void connectStatue(boolean isConnect) {
        this.mIDataReceiveHandler.connectStatue(isConnect);
    }

    /* access modifiers changed from: protected */
    public void onCommonSend(byte[] data) {
        this.mIDataReceiveHandler.onCommonSend(data);
    }

    /* access modifiers changed from: protected */
    public void onDiscoverService(String serviceUUID) {
        this.mIDataReceiveHandler.onDiscoverService(serviceUUID);
    }

    /* access modifiers changed from: protected */
    public void onDiscoverCharacter(String characterUUID) {
        this.mIDataReceiveHandler.onDiscoverCharacter(characterUUID);
    }

    /* access modifiers changed from: protected */
    public void onCharacteristicChange(String address) {
        this.mIDataReceiveHandler.onCharacteristicChange(address);
    }

    /* access modifiers changed from: protected */
    public void onBluetoothError() {
        this.mIDataReceiveHandler.onBluetoothError();
    }

    /* access modifiers changed from: protected */
    public void onDataArrived(int ble_sdk_type, int dataType, String data) {
        if (this.mIDataReceiveHandler == null) {
            KLog.e("mIDataReceiveHandler is null");
        } else {
            this.mIDataReceiveHandler.onDataArrived(ble_sdk_type, dataType, data);
        }
    }
}
