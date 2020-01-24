package com.iwown.device_module.device_alarm_schedule.activity.schedule;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import java.util.List;

public class AddScheduleContract {

    public interface AddScheduleView {
        void readScheduleError(int i, String str);

        void showDialog(boolean z);
    }

    public interface SchedulePresenter extends BasePresenter {
        void addSchedule(int i, int i2, int i3, int i4, int i5, String str, String str2, int i6, int i7);

        void addScheduleErrorListener();

        void deleteSchedule(TB_schedulestatue tB_schedulestatue);

        void editSchedule(int i, int i2, int i3, int i4, int i5, int i6, String str, String str2, int i7, int i8);

        boolean getIsSupportSchedule();

        int getMaxSetableNum();

        int getPerdaySetableNum();

        List<TB_schedulestatue> getSelectedDayScheduleData(int i, int i2, int i3);

        GoogleAccountCredential googleServiceInit();

        void initSdk();

        int isAddSchedule(int i, int i2, int i3);

        boolean isChangeDeviceSchedule(boolean z);

        void newScheduleBluetoothDataParseBiz();

        void readDeviceInfoFromTB();

        void registerReceiver();

        void unRegisterReceiver();

        void updateIsChangeDevice();
    }
}
