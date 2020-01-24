package com.iwown.device_module.device_alarm_schedule.activity.alarm;

import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import java.util.List;

public class AddClockContract {

    public interface ClockPresenter extends BasePresenter {
        void addAlarm(int i, int i2, int i3, int i4, String str, String str2, int i5, int i6);

        void closeAlarm(int i);

        void deleteAlarm(int i);

        void editAlarm(int i, int i2, int i3, int i4, String str, String str2, boolean z, int i5, int i6);

        List<TB_Alarmstatue> getAllAlarmData();

        int isAddAlarm(int i);

        void openAlarm(int i);
    }

    public interface ClockView extends BaseView<ClockPresenter> {
    }
}
