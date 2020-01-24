package com.iwown.ble_module.zg_ble.test;

import android.content.Context;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;

public class ZG_Order_Handler {
    public static void initOrderSend(Context context) {
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getFirmwareInformation()));
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getHardwareFeatures()));
        BleDataOrderHandler.getInstance().setStride(context, 55, 85);
    }

    public byte[] getDetailWalk(int day) {
        return new byte[]{-119, ByteUtil.hexToBytes(String.valueOf(day) + "1")[0], -127, 1, 0};
    }

    public byte[] getDetailSport(int day) {
        return new byte[]{-119, ByteUtil.hexToBytes(String.valueOf(day) + "4")[0], -127, 1, 0};
    }

    public byte[] getDetailSleep(int day) {
        return new byte[]{-119, ByteUtil.hexToBytes(String.valueOf(day) + "3")[0], -127, 1, 0};
    }
}
