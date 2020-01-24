package com.iwown.device_module.device_alarm_schedule.activity.schedule;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_alarm_schedule.AlarmScheduleOperation;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddScheduleContract.AddScheduleView;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddScheduleContract.SchedulePresenter;
import com.iwown.device_module.device_alarm_schedule.common.IQueueManagerResultListener;
import com.iwown.device_module.device_alarm_schedule.common.ScheduleManager;
import com.socks.library.KLog;
import com.tencent.bugly.beta.tinker.TinkerReport;
import java.util.Arrays;
import java.util.List;

public class AddSchedulePresenter implements SchedulePresenter {
    public static int Google_Schedule_Code = 999;
    public static String Google_Schedule_Code_Action = "google_schedule";
    public static String KEY_ADD_OR_EDIT = "add_or_edit";
    public static String KEY_SCH_DAY = "day";
    public static String KEY_SCH_MONTH = "month";
    public static String KEY_SCH_YEAR = "year";
    public static int PHONE_SCHEDULE = 666;
    public static int REQUEST_ADD_SCH = 294;
    public static int REQUEST_EDIT_SCH = 295;
    public static int RESULT_SCH_DELETE = TinkerReport.KEY_LOADED_INFO_CORRUPTED;
    private static final String[] SCOPES = {CalendarScopes.CALENDAR_READONLY};
    public static int STEP_SCHEDULE_ADD = 6;
    public static int STEP_SCHEDULE_EDIT = 7;
    public static final String TAG = "schedule";
    public static int TO_ADD = 2;
    public static int TO_EDIT = 3;
    public static String queue_state_bluetooth_break = "QUEUE_STATE_BLUETOOTH_BREAK";
    public static String queue_state_bluetooth_connect = "QUEUE_STATE_BLUETOOTH_CONNECT";
    public static String queue_state_readdevice_info = "QUEUE_STATE_READDEVICE_INFO";
    GoogleAccountCredential googleAccountCredential;
    /* access modifiers changed from: private */
    public AddScheduleView view;

    private class Listener implements IQueueManagerResultListener {
        private Listener() {
        }

        public void onResult(int result, int state) {
            KLog.d("QueueManagerResult");
            if (2 == result) {
                KLog.d("QueueManagerResult 蓝牙断开");
                if (state == 4) {
                    KLog.d(AddSchedulePresenter.TAG, "ReadDeviceInfoDlg");
                    AddSchedulePresenter.this.view.readScheduleError(state, AddSchedulePresenter.queue_state_readdevice_info);
                } else if (state == 1) {
                    KLog.d(AddSchedulePresenter.TAG, "蓝牙断开");
                    AddSchedulePresenter.this.view.readScheduleError(state, AddSchedulePresenter.queue_state_bluetooth_break);
                } else if (state == 0) {
                    KLog.d(AddSchedulePresenter.TAG, "蓝牙连接");
                    AddSchedulePresenter.this.view.readScheduleError(state, AddSchedulePresenter.queue_state_bluetooth_connect);
                } else {
                    KLog.d(AddSchedulePresenter.TAG, "ExceptionDlg");
                }
            } else if (3 == result) {
                KLog.d(AddSchedulePresenter.TAG, "QueueManagerResult 接收超时");
                if (state == 4) {
                    KLog.d(AddSchedulePresenter.TAG, "ReadDeviceInfoDlg");
                    AddSchedulePresenter.this.view.readScheduleError(state, AddSchedulePresenter.queue_state_readdevice_info);
                    return;
                }
                KLog.d(AddSchedulePresenter.TAG, "ExceptionDlg");
            } else if (1 == result) {
                KLog.d(AddSchedulePresenter.TAG, "QueueManagerResult 失败");
                if (state == 4) {
                    KLog.d(AddSchedulePresenter.TAG, "ReadDeviceInfoDlg");
                    AddSchedulePresenter.this.view.readScheduleError(state, AddSchedulePresenter.queue_state_readdevice_info);
                    return;
                }
                KLog.d(AddSchedulePresenter.TAG, "ExceptionDlg");
            } else if (result == 0) {
                KLog.d(AddSchedulePresenter.TAG, "QueueManagerResult 成功");
                if (state == 4) {
                    KLog.d(AddSchedulePresenter.TAG, "ReadDeviceInfoDlg");
                    AddSchedulePresenter.this.view.readScheduleError(state, AddSchedulePresenter.queue_state_readdevice_info);
                    return;
                }
                KLog.d(AddSchedulePresenter.TAG, "ExceptionDlg");
            }
        }
    }

    public AddSchedulePresenter(AddScheduleView view2) {
        this.view = view2;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }

    public void addSchedule(int year, int month, int day, int hour, int minute, String item, String remind, int shakeMode, int shakeNum) {
        ScheduleManager.getInstance().addSchedule(year, month, day, hour, minute, item, remind, shakeMode, shakeNum);
    }

    public void editSchedule(int id, int year, int month, int day, int hour, int minute, String item, String remind, int shakeMode, int shakeNum) {
        ScheduleManager.getInstance().editSchedule(id, year, month, day, hour, minute, item, remind, shakeMode, shakeNum);
    }

    public void deleteSchedule(TB_schedulestatue data) {
        ScheduleManager.getInstance().deleteSchedule(data);
    }

    public void newScheduleBluetoothDataParseBiz() {
        ScheduleManager.getInstance().newScheduleBluetoothDataParseBiz();
    }

    public void registerReceiver() {
        ScheduleManager.getInstance().registerReceiver();
    }

    public void unRegisterReceiver() {
        ScheduleManager.getInstance().unRegisterReceiver();
    }

    public void initSdk() {
        AlarmScheduleOperation.getInstance().initSdk();
    }

    public void readDeviceInfoFromTB() {
        ScheduleManager.getInstance().readDeviceInfoFromTB();
    }

    public boolean isChangeDeviceSchedule(boolean flag) {
        return ScheduleManager.getInstance().isChangeDeviceSchedule(flag);
    }

    public int isAddSchedule(int year, int month, int day) {
        return ScheduleManager.getInstance().isAddSchedule(year, month, day);
    }

    public boolean getIsSupportSchedule() {
        return ScheduleManager.getInstance().getIsSupportSchedule();
    }

    public List<TB_schedulestatue> getSelectedDayScheduleData(int year, int month, int day) {
        return ScheduleManager.getInstance().getSelectedDayScheduleData(year, month, day);
    }

    public int getMaxSetableNum() {
        return ScheduleManager.getInstance().getMaxSetableNum();
    }

    public int getPerdaySetableNum() {
        return ScheduleManager.getInstance().getPerdaySetableNum();
    }

    public void updateIsChangeDevice() {
        ScheduleManager.getInstance().updateIsChangeDeviceSchedule();
    }

    public void addScheduleErrorListener() {
        ScheduleManager.getInstance().setQueueManagerResult(new Listener());
    }

    public GoogleAccountCredential googleServiceInit() {
        if (this.googleAccountCredential != null) {
            return this.googleAccountCredential;
        }
        this.googleAccountCredential = GoogleAccountCredential.usingOAuth2(ContextUtil.app, Arrays.asList(SCOPES)).setBackOff(new ExponentialBackOff());
        return this.googleAccountCredential;
    }
}
