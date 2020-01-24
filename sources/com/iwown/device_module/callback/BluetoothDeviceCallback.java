package com.iwown.device_module.callback;

import android.app.Application;
import android.content.Context;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.iwown.receiver.IWBaseBleReceiver;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.proto.ble.ProtoBaseReceiver;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.IDataReceiveHandler;
import org.jetbrains.annotations.NotNull;

public class BluetoothDeviceCallback {
    private static Context context;
    public static BluetoothDeviceCallback instance;

    private BluetoothDeviceCallback(Context context2) {
        context = context2;
    }

    public static BluetoothDeviceCallback getInstance(Context context2) {
        if (instance == null) {
            instance = new BluetoothDeviceCallback(context2);
        }
        return instance;
    }

    public static void init(Application app, @NotNull final IBluetoothListener listener) {
        BleHandler.getInstance().init(app, new IDataReceiveHandler() {
            public void onDataArrived(int ble_sdk_type, int dataType, String data) {
                listener.onDataArrived(ble_sdk_type, dataType, data);
            }

            public void onScanResult(WristBand dev) {
            }

            public void onBluetoothInit() {
                listener.onBluetoothInit();
            }

            public void connectStatue(boolean isConnect) {
                listener.connectStatue(isConnect);
            }

            public void onDiscoverService(String serviceUUID) {
                listener.onDiscoverService(serviceUUID);
            }

            public void onDiscoverCharacter(String characterUUID) {
                listener.onDiscoverCharacter(characterUUID);
            }

            public void onCommonSend(byte[] data) {
                listener.onCommonSend(data);
            }

            public void onCharacteristicChange(String address) {
                listener.onCharacteristicChange(address);
            }

            public void onBluetoothError() {
                listener.onBluetoothError();
            }
        });
        IWBaseBleReceiver.getInstance(app, new com.iwown.ble_module.iwown.receiver.IDataReceiveHandler() {
            public void onDataArrived(int ble_sdk_type, int dataType, String data) {
                listener.onDataArrived(ble_sdk_type, dataType, data);
            }

            public void onScanResult(WristBand dev) {
                listener.onScanResult(dev);
            }

            public void onBluetoothInit() {
                listener.onBluetoothInit();
            }

            public void connectStatue(boolean isConnect) {
                listener.connectStatue(isConnect);
            }

            public void onDiscoverService(String serviceUUID) {
                listener.onDiscoverService(serviceUUID);
            }

            public void onDiscoverCharacter(String characterUUID) {
                listener.onDiscoverCharacter(characterUUID);
            }

            public void onCommonSend(byte[] data) {
                listener.onCommonSend(data);
            }

            public void onCharacteristicChange(String address) {
                listener.onCharacteristicChange(address);
            }

            public void onBluetoothError() {
                listener.onBluetoothError();
            }
        });
        MTKBle.getInstance(app).setDataReceiveHandler(new com.iwown.ble_module.mtk_ble.IDataReceiveHandler() {
            public void onDataArrived(int ble_sdk_type, int dataType, String data) {
                listener.onDataArrived(ble_sdk_type, dataType, data);
            }

            public void onScanResult(WristBand dev) {
                listener.onScanResult(dev);
            }

            public void onBluetoothInit() {
                listener.onBluetoothInit();
            }

            public void connectStatue(boolean isConnect) {
                listener.connectStatue(isConnect);
            }

            public void onDiscoverService(String serviceUUID) {
                listener.onDiscoverService(serviceUUID);
            }

            public void onDiscoverCharacter(String characterUUID) {
                listener.onDiscoverCharacter(characterUUID);
            }

            public void onCommonSend(byte[] data) {
                listener.onCommonSend(data);
            }

            public void onCharacteristicChange(String address) {
                listener.onDiscoverCharacter(address);
            }

            public void onBluetoothError(int errorCode) {
                listener.onBluetoothError();
            }

            public void onNoCallback() {
                listener.noCallBack();
            }

            public void onPreConnect() {
                listener.onPreConnect();
            }
        });
        ProtoBaseReceiver.getInstance(context, new com.iwown.ble_module.proto.ble.IDataReceiveHandler() {
            public void onDataArrived(int ble_sdk_type, int dataType, String data) {
                listener.onDataArrived(ble_sdk_type, dataType, data);
            }

            public void onScanResult(WristBand dev) {
                listener.onScanResult(dev);
            }

            public void onBluetoothInit() {
                listener.onBluetoothInit();
            }

            public void connectStatue(boolean isConnect) {
                listener.connectStatue(isConnect);
            }

            public void onDiscoverService(String serviceUUID) {
                listener.onDiscoverService(serviceUUID);
            }

            public void onDiscoverCharacter(String characterUUID) {
                listener.onDiscoverCharacter(characterUUID);
            }

            public void onCommonSend(byte[] data) {
                listener.onCommonSend(data);
            }

            public void onCharacteristicChange(String address) {
                listener.onCharacteristicChange(address);
            }

            public void onBluetoothError() {
                listener.onBluetoothError();
            }
        });
    }
}
