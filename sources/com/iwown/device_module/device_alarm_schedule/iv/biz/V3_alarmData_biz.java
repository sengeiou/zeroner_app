package com.iwown.device_module.device_alarm_schedule.iv.biz;

import android.content.Context;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.utils.ContextUtil;
import com.socks.library.KLog;

public class V3_alarmData_biz {
    public static String TAG = "V3_alarmData_biz";
    private Context context;

    public V3_alarmData_biz(Context context2) {
        this.context = context2;
    }

    public static void closeAlarm(int id) {
        if (BluetoothOperation.isConnected()) {
            ZeronerSendBluetoothCmdImpl.getInstance().closeAlarm(id, ContextUtil.app);
            KLog.d(TAG, "closeAlarm");
        }
    }

    public static void writeAlarm(int id, int weekRepeat, int hour, int minute, String text) {
        if (BluetoothOperation.isConnected()) {
            ZeronerSendBluetoothCmdImpl.getInstance().writeAlarmClock(ContextUtil.app, id, weekRepeat, hour, minute, text);
            KLog.d(TAG, "writeAlarm Iwown");
        }
    }

    private byte int2byte(int integer) {
        return (byte) (integer & 255);
    }
}
