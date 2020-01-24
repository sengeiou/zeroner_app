package com.iwown.ble_module.iwown.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.iwown.ble_module.iwown.bean.DeviceTime;
import com.iwown.ble_module.iwown.bean.FMdeviceInfo;
import com.iwown.ble_module.iwown.bean.IWBleParams;
import com.iwown.ble_module.iwown.bean.IWDevSetting;
import com.iwown.ble_module.iwown.bean.IWHeartWarning;
import com.iwown.ble_module.iwown.bean.IWUserInfo;
import com.iwown.ble_module.iwown.bean.KeyModel;
import com.iwown.ble_module.iwown.bean.Power;
import com.iwown.ble_module.iwown.bean.QuietModeInfo;
import com.iwown.ble_module.iwown.bean.SedentaryInfo;
import com.iwown.ble_module.iwown.bean.SportGoal;
import com.iwown.ble_module.iwown.bean.SportType;
import com.iwown.ble_module.iwown.bean.StoredDataInfoTotal;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.iwown.bean.Zeroner28Base;
import com.iwown.ble_module.iwown.bean.ZeronerDetailSportParse;
import com.iwown.ble_module.iwown.bean.ZeronerSleepParse;
import com.iwown.ble_module.iwown.bean.ZeronerTotalsportParse;
import com.iwown.ble_module.iwown.bluetooth.BleService;
import com.iwown.ble_module.iwown.cmd.ZeronerAlarmClockScheduleHandler;
import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.iwown.utils.HexUtil;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.utils.Util;
import com.socks.library.KLog;

public class IWBaseBleReceiver extends BroadcastReceiver {
    private static IWBaseBleReceiver instance;
    protected Context context;
    public Handler mHandler = new Handler(Looper.getMainLooper());
    private IDataReceiveHandler mIDataReceiveHandler;
    private long pre01time;

    private IWBaseBleReceiver(Context context2, IDataReceiveHandler iDataReceiveHandler) {
        LocalBroadcastManager.getInstance(context2).registerReceiver(this, BleService.getIntentFilter());
        this.mIDataReceiveHandler = iDataReceiveHandler;
    }

    public static IWBaseBleReceiver getInstance(Context context2, IDataReceiveHandler iDataReceiveHandler) {
        if (instance == null) {
            instance = new IWBaseBleReceiver(context2, iDataReceiveHandler);
        }
        return instance;
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
                byte dataType = data[2];
                String result = ByteUtil.bytesToString1(data);
                switch (dataType) {
                    case 0:
                        FMdeviceInfo fMdeviceInfo = new FMdeviceInfo();
                        fMdeviceInfo.parseData(data);
                        result = JsonTool.toJson(fMdeviceInfo);
                        break;
                    case 1:
                        Power power = new Power();
                        power.parseData(data);
                        result = JsonTool.toJson(power);
                        break;
                    case 6:
                        QuietModeInfo quietModeInfo = new QuietModeInfo();
                        quietModeInfo.parseData(data);
                        result = JsonTool.toJson(quietModeInfo);
                        break;
                    case 8:
                        StoredDataInfoTotal storedDataInfoTotal = new StoredDataInfoTotal();
                        storedDataInfoTotal.parseData(data);
                        result = JsonTool.toJson(storedDataInfoTotal);
                        break;
                    case 17:
                        DeviceTime deviceTime = new DeviceTime();
                        deviceTime.parseData(data);
                        result = JsonTool.toJson(deviceTime);
                        break;
                    case 19:
                        IWBleParams iwBleParams = new IWBleParams();
                        iwBleParams.parseData(data);
                        result = JsonTool.toJson(iwBleParams);
                        break;
                    case 21:
                        result = ZeronerAlarmClockScheduleHandler.parseAlarmClock(data);
                        break;
                    case 23:
                        SedentaryInfo sedentaryInfo = new SedentaryInfo();
                        sedentaryInfo.parseData(data);
                        result = JsonTool.toJson(sedentaryInfo);
                        break;
                    case 25:
                        IWDevSetting iwDevSetting = new IWDevSetting();
                        iwDevSetting.parseData(data);
                        result = JsonTool.toJson(iwDevSetting);
                        break;
                    case 26:
                        SportType sportType = new SportType();
                        sportType.parseData(data);
                        result = JsonTool.toJson(sportType);
                        break;
                    case 28:
                        SportGoal sportGoal = new SportGoal();
                        sportGoal.parseData(data);
                        result = JsonTool.toJson(sportGoal);
                        break;
                    case 29:
                        result = ZeronerAlarmClockScheduleHandler.parseSetScheduleResponse(data);
                        break;
                    case 30:
                        result = ZeronerAlarmClockScheduleHandler.parseSchedule(data);
                        break;
                    case 33:
                        IWUserInfo iwUserInfo = new IWUserInfo();
                        iwUserInfo.parseData(data);
                        result = JsonTool.toJson(iwUserInfo);
                        break;
                    case 36:
                        IWHeartWarning iwHeartWarning = new IWHeartWarning();
                        iwHeartWarning.parseData(data);
                        result = JsonTool.toJson(iwHeartWarning);
                        break;
                    case 40:
                        if (data[9] != 0) {
                            if (data[9] != -1) {
                                result = ZeronerDetailSportParse.parse(data);
                                break;
                            } else {
                                result = Zeroner28Base.parseBase(data);
                                break;
                            }
                        } else {
                            result = ZeronerSleepParse.parse(data);
                            break;
                        }
                    case 41:
                        result = ZeronerTotalsportParse.parse(data);
                        break;
                    case 64:
                        KeyModel keyModel = new KeyModel();
                        keyModel.parseData(data);
                        result = JsonTool.toJson(keyModel);
                        break;
                    case 81:
                        result = ZeronerHeartHandler.parseDetail(data);
                        break;
                    case 83:
                        result = ZeronerHeartHandler.parseHour(data);
                        break;
                }
                KLog.e("result " + result);
                if (this.mIDataReceiveHandler != null) {
                    this.mIDataReceiveHandler.onDataArrived(1, dataType, result);
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
