package com.iwown.ble_module.mtk_ble;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MtkBaseReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (MtkBroadcastSender.ZERONER_CONNECTED_WITH_STATE_CODE.equalsIgnoreCase(action)) {
            onConnectState(intent.getBooleanExtra("CONNECTED", false), intent.getIntExtra(MtkBroadcastSender.EXTRA_CONNECT_STATUS, 0), intent.getIntExtra(MtkBroadcastSender.EXTRA_CONNECT_NEW_STATE, 0));
        } else if ("com.zeroner.sdk.ble.service_discovered".equalsIgnoreCase(action)) {
            onServiceDiscovered(intent.getStringExtra(MtkBroadcastSender.EXTRA_SERVICE_UUID), intent.getIntExtra(MtkBroadcastSender.EXTRA_SERVICE_DISCOVERED_STATE, 0));
        } else if (MtkBroadcastSender.ZERONER_CAN_WRITE_DATA_TO_DEV.equalsIgnoreCase(action)) {
            onCanWriteData2Dev();
        } else if (MtkBroadcastSender.ZERONER_START_CONNECT.equalsIgnoreCase(action)) {
            onPreConnect();
        } else if (MtkBroadcastSender.ZERONER_NO_CALLBACK.equalsIgnoreCase(action)) {
            noCallback();
        } else if (MtkBroadcastSender.ZERONER_DATA_RESULT.equalsIgnoreCase(action)) {
            onDataArrive(intent.getIntExtra(MtkBroadcastSender.EXTRA_DATA_TYPE, 0), intent.getStringExtra(MtkBroadcastSender.EXTRA_RESULT_PARSED));
        } else if (MtkBroadcastSender.ZERONER_CHARACTERISTIC_FOUND.equalsIgnoreCase(action)) {
            onCharacteristicFound(intent.getStringExtra(MtkBroadcastSender.EXTRA_CHARACTERISTIC_UUID));
        } else if (MtkBroadcastSender.ZERONER_ON_DATA_WRITE.equalsIgnoreCase(action)) {
            onDataWrite(intent.getByteArrayExtra(MtkBroadcastSender.EXTRA_DATA_WRITED));
        } else if (MtkBroadcastSender.ZERONER_ERROR.equalsIgnoreCase(action)) {
            onError(intent.getIntExtra(MtkBroadcastSender.EXTRA_ERROR, -1));
        }
    }

    public void onError(int errorCode) {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.onBluetoothError(errorCode);
        }
    }

    public void onConnectState(boolean isConnect, int status, int newState) {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.connectStatue(isConnect);
            if (status == 133) {
                receiveHandler.onBluetoothError(MTKBleError.BLE_CONNECT_ERROR_133);
            } else if (status == 257) {
                receiveHandler.onBluetoothError(MTKBleError.BLE_CONNECT_ERROR_257);
            }
        }
    }

    public void onServiceDiscovered(String uuid, int state) {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.onDiscoverService(uuid);
        }
    }

    public void onCanWriteData2Dev() {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.onBluetoothInit();
        }
    }

    public void onPreConnect() {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.onPreConnect();
        }
    }

    public void noCallback() {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.onNoCallback();
        }
    }

    public void onDataArrive(int dataType, String result) {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.onDataArrived(MTKBle.SDK_TYPE, dataType, result);
        }
    }

    public void onCharacteristicFound(String uuid) {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.onDiscoverCharacter(uuid);
        }
    }

    public void onDataWrite(byte[] data) {
        IDataReceiveHandler receiveHandler = MTKBle.getInstance().getDataReceiveHandler();
        if (receiveHandler != null) {
            receiveHandler.onCommonSend(data);
        }
    }
}
