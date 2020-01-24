package com.iwown.device_module.device_alarm_schedule;

import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_alarm_schedule.common.ScheduleManager;

public class AlarmScheduleOperation {
    private static AlarmScheduleOperation instance;

    private AlarmScheduleOperation() {
    }

    public static AlarmScheduleOperation getInstance() {
        if (instance == null) {
            instance = new AlarmScheduleOperation();
        }
        return instance;
    }

    public void initSdk() {
        ScheduleManager.initData(ContextUtil.app);
    }
}
