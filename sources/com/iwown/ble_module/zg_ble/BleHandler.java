package com.iwown.ble_module.zg_ble;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.os.SystemClock;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.bluetooth.BleDevice;
import com.iwown.ble_module.zg_ble.bluetooth.ZGBle;
import com.iwown.ble_module.zg_ble.callback.BleGattCallback;
import com.iwown.ble_module.zg_ble.data.IDataReceiveHandler;
import com.iwown.ble_module.zg_ble.data.ZGBaseBleReceiver;
import com.iwown.ble_module.zg_ble.exception.BleException;
import com.iwown.ble_module.zg_ble.task.AgpsBleMessage;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.ble_module.zg_ble.task.TaskHandler;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;

public class BleHandler {
    public static final int Ble_Type = 3;
    private static final int DEFAULT_MAX_MULTIPLE_DEVICE = 7;
    private static final int DEFAULT_OPERATE_TIME = 5000;
    public static final byte TYPE89_Heart_02 = 91;
    public static final byte TYPE89_Sleep_03 = 92;
    public static final byte TYPE89_Sport_04 = 93;
    public static final byte TYPE89_Total_81 = 89;
    public static final byte TYPE89_Walk_01 = 90;
    private static BleHandler instance;
    private long agpsTime = 0;
    private Application application;
    private ZGBaseBleReceiver baseBleReceiver;
    public MyBleGattCallback bleGattCallback = new MyBleGattCallback();
    private BleScanHandler bleScanHandler;
    /* access modifiers changed from: private */
    public IDataReceiveHandler iDataReceiveHandler;
    private int maxConnectCount = 7;
    private int operateTimeout = 5000;

    interface BleScanCallBack {
        void result(BluetoothDevice bluetoothDevice);
    }

    public class MyBleGattCallback extends BleGattCallback {
        public MyBleGattCallback() {
        }

        public void onStartConnect() {
        }

        public void onConnectFail(BleException exception) {
            BleHandler.this.iDataReceiveHandler.onBluetoothError();
            BleHandler.this.iDataReceiveHandler.connectStatue(false);
        }

        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
            BleHandler.this.iDataReceiveHandler.connectStatue(true);
        }

        public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
            BleHandler.this.iDataReceiveHandler.connectStatue(false);
        }
    }

    public Context getApplication() {
        return this.application;
    }

    private BleHandler() {
    }

    public static BleHandler getInstance() {
        if (instance == null) {
            instance = new BleHandler();
        }
        return instance;
    }

    public void init(Application app, IDataReceiveHandler iDataReceiveHandler2) {
        if (this.application == null && app != null) {
            this.application = app;
            this.iDataReceiveHandler = iDataReceiveHandler2;
            TaskHandler.getInstance().init();
            this.baseBleReceiver = ZGBaseBleReceiver.getInstance(iDataReceiveHandler2);
        }
    }

    public void addTaskMessage(BleMessage bleMessage) {
        if (!isSyncAgps()) {
            TaskHandler.getInstance().addTaskMessage(bleMessage);
        } else if (bleMessage instanceof AgpsBleMessage) {
            TaskHandler.getInstance().addTaskMessage(bleMessage);
        } else if (bleMessage != null && bleMessage.bytes != null) {
            String msg = JsonTool.toJson(bleMessage.bytes);
            KLog.e("no2855--> 正在写入agps，不能插入其他指令: " + msg);
            L.file("正在写入agps，不能插入指令" + msg.toString(), 1);
        }
    }

    public void addMessageFirstImmediately(BleMessage bleMessage) {
        TaskHandler.getInstance().addMessageFirstImmediately(bleMessage);
    }

    private boolean isSyncAgps() {
        return System.currentTimeMillis() - this.agpsTime < 25000;
    }

    public void setAgpsTime(long time) {
        this.agpsTime = time;
    }

    public synchronized void setSendStatusOver() {
        TaskHandler.getInstance().setSendStatusOver();
    }

    public void setSendStatusNotOver() {
        TaskHandler.getInstance().setSendStatusNotOver();
    }

    public void clearTaskHandler() {
        TaskHandler.getInstance().clearTask();
    }

    public void wirteMessege2Ble(BleMessage message, int sleepTime) {
        if (message == null) {
            KLog.e("message  is  null");
        } else if (message.bytes == null || message.bytes.size() == 0) {
            KLog.e("message type is   byte [] is null");
        } else {
            int index = 0;
            for (byte[] bytes : message.bytes) {
                TaskHandler.getInstance().setSendStatusIng();
                KLog.d("no2855=wirteMessege2Ble " + ByteUtil.bytesToString1(bytes) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + TaskHandler.getInstance().getSendStatus());
                if (index > 0) {
                    SystemClock.sleep((long) sleepTime);
                }
                if (ZGBle.getInstance() != null) {
                    ZGBle.getInstance().writeDataToWristBand(bytes);
                }
                index++;
            }
        }
    }

    public void cancelScan() {
        this.bleScanHandler.stopScanBleDevice();
    }

    public int getMaxConnectCount() {
        return this.maxConnectCount;
    }

    public int getOperateTimeout() {
        return this.operateTimeout;
    }
}
