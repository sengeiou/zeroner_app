package com.iwown.device_module.device_alarm_schedule.common;

import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_alarm_schedule.iv.biz.V3_alarmData_biz;
import com.iwown.device_module.device_alarm_schedule.mtk.Mtkalarm;
import com.iwown.device_module.device_alarm_schedule.zg.ZgAlarm;
import com.socks.library.KLog;

public class AlarmCommandUtil {
    public static void writeAlarm(int id, int weekRepeat, int hour, int minute, String text) {
        if (!BluetoothOperation.isConnected()) {
            KLog.i("device_disconnect");
        } else if (BluetoothOperation.isIv()) {
            V3_alarmData_biz.writeAlarm(id, weekRepeat, hour, minute, text);
        } else if (BluetoothOperation.isZg()) {
            ZgAlarm.writeAlarm(id, weekRepeat, hour, minute, text);
        } else if (BluetoothOperation.isMtk()) {
            Mtkalarm.writeAlarm(id, weekRepeat, hour, minute, text);
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().addAlarm(id, true, weekRepeat, hour, minute, text));
        }
    }

    public static void closeAlarm(int id) {
        if (!BluetoothOperation.isConnected()) {
            KLog.i("device_disconnect");
            return;
        }
        if (BluetoothOperation.isIv()) {
            V3_alarmData_biz.closeAlarm(id);
        } else if (BluetoothOperation.isZg()) {
            ZgAlarm.closeAlarm(id);
        } else if (BluetoothOperation.isMtk()) {
            Mtkalarm.closeAlarm(id);
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().removeAlarm(id));
        }
        KLog.d("closeAlarm");
    }
}
