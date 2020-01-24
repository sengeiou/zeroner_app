package com.iwown.device_module.device_alarm_schedule.mtk;

import android.content.Context;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.utils.ContextUtil;
import com.socks.library.KLog;

public class Mtkalarm {
    private String TAG = "MtkAlarm";
    private Context mContext;
    private byte readAlarmHeader;
    private byte writeAlarmHeader;

    public Mtkalarm(Context contex) {
        this.mContext = contex;
    }

    public static void closeAlarm(int id) {
        if (BluetoothOperation.isConnected()) {
            MtkCmdAssembler.getInstance().closeAlarm(id, ContextUtil.app);
            KLog.d("closeMtkAlarm");
        }
    }

    public static void writeAlarm(int id, int weekRepeat, int hour, int minute, String text) {
        if (BluetoothOperation.isConnected()) {
            MtkCmdAssembler.getInstance().writeAlarmClock(ContextUtil.app, id, weekRepeat, hour, minute, text);
            KLog.d("mtk_alarm ");
        }
    }

    private byte int2byte(int integer) {
        return (byte) (integer & 255);
    }
}
