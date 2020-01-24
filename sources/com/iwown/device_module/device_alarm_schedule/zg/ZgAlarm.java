package com.iwown.device_module.device_alarm_schedule.zg;

import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.socks.library.KLog;
import org.litepal.crud.DataSupport;

public class ZgAlarm {
    public static final String TAG = "ZG_alarm";

    public static void closeAlarm(int id) {
        if (BluetoothOperation.isConnected()) {
            ZGBaseUtils.updateAlarmAndSchedule(ContextUtil.app);
            KLog.d("closeAlarm");
        }
    }

    public static void writeAlarm(int id, int weekRepeat, int hour, int minute, String text) {
        if (BluetoothOperation.isConnected()) {
            TB_Alarmstatue first = (TB_Alarmstatue) DataSupport.where("UID=? AND Ac_Idx=?", PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid) + "", String.valueOf(id)).findFirst(TB_Alarmstatue.class);
            if (first != null) {
                KLog.e("更新知格类中 mode  number " + first);
                if (!(ZGBaseUtils.alarm_number1 == -1 || ZGBaseUtils.alarm_mode1 == -1)) {
                    first.setZg_number(ZGBaseUtils.alarm_number1);
                    first.setZg_mode(ZGBaseUtils.alarm_mode1);
                }
                first.save();
                ZGBaseUtils.setAlarmScheduleModeNumber(-1, -1);
            }
            ZGBaseUtils.updateAlarmAndSchedule(ContextUtil.app);
            KLog.d(TAG, "writeAlarm Iwown");
        }
    }
}
