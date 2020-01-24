package com.iwown.device_module.device_alarm_schedule.eventbus;

import com.iwown.device_module.common.sql.TB_schedulestatue;

public class ScheduleEvent {
    public static int TO_ADD = 0;
    public static int TO_DELETE = 2;
    public static int TO_EDIT = 1;
    private TB_schedulestatue mSchedulestatue;
    private int operate_type;

    public ScheduleEvent(TB_schedulestatue schedulestatue, int operate_type2) {
        this.mSchedulestatue = schedulestatue;
        this.operate_type = operate_type2;
    }

    public TB_schedulestatue getSchedulestatue() {
        return this.mSchedulestatue;
    }

    public void setSchedulestatue(TB_schedulestatue schedulestatue) {
        this.mSchedulestatue = schedulestatue;
    }

    public int getOperate_type() {
        return this.operate_type;
    }

    public void setOperate_type(int operate_type2) {
        this.operate_type = operate_type2;
    }
}
