package com.iwown.device_module.device_alarm_schedule.pb;

import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.lib_common.date.DateUtil;

public class PbScheduleUtil {
    public static void add(TB_schedulestatue data) {
        int y = data.getYear();
        int m = data.getMonth();
        int d = data.getDay();
        int h = data.getHour();
        int min = data.getMinute();
        String text = data.getText();
        DateUtil dateUtil = new DateUtil(y, m, d, h, min);
        BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().addCalendar((int) dateUtil.getUnixTimestamp(), (int) dateUtil.getUnixTimestamp(), text));
    }

    public static void delete(TB_schedulestatue data) {
        DateUtil dateUtil = new DateUtil(data.getYear(), data.getMonth(), data.getDay(), data.getHour(), data.getMinute());
        BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().removeCalendar((int) dateUtil.getUnixTimestamp(), (int) dateUtil.getUnixTimestamp()));
    }

    public static void edit(TB_schedulestatue srcData, TB_schedulestatue dstData) {
        delete(srcData);
        add(dstData);
    }
}
