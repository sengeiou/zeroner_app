package com.iwown.device_module.device_long_sit.zg;

import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.device_module.common.utils.ContextUtil;

public class ZgLongSit {
    public static void writeSedentaryIfLunchBreak(int startHour, int endHour) {
        BleDataOrderHandler.getInstance().setLongSitAlarm(ContextUtil.app, 2, startHour, endHour);
    }

    public static void writeSedentaryAccordingApi(int startHour, int endHour, boolean alarm_status) {
        BleDataOrderHandler.getInstance().setLongSitAlarm(ContextUtil.app, alarm_status ? 1 : 0, startHour, endHour);
    }
}
