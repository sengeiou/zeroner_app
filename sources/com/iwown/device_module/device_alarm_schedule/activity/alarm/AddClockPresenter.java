package com.iwown.device_module.device_alarm_schedule.activity.alarm;

import android.support.v4.app.NotificationCompat;
import com.iwown.device_module.R;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockContract.ClockPresenter;
import com.iwown.device_module.device_alarm_schedule.activity.alarm.AddClockContract.ClockView;
import com.iwown.device_module.device_alarm_schedule.common.AlarmManager;
import com.socks.library.KLog;
import java.util.List;

public class AddClockPresenter implements ClockPresenter {
    public static String KEY_ADD_OR_EDIT = "add_or_edit";
    public static String KEY_CLOCK_ID = "clock_id";
    public static String KEY_REPEAT = "repeat";
    public static int REQUEST_ADD_CLOCK = 292;
    public static int REQUEST_EDIT_CLOCK = 293;
    public static int RESULT_CLOCK_DELETE = 88;
    public static int STEP_CLOCK_ADD = 4;
    public static int STEP_CLOCK_EDIT = 5;
    public static int TO_ADD = 2;
    public static int TO_EDIT = 3;
    private String[] daysOfWeek;
    ClockView view;

    public AddClockPresenter(ClockView view2) {
        this.view = view2;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }

    public void addAlarm(int id, int weekRepeat, int hour, int minute, String item, String remind, int shakeMode, int shakeNum) {
        KLog.d(NotificationCompat.CATEGORY_ALARM, "add alarm UID" + String.valueOf(ContextUtil.getUID()));
        AlarmManager.addAlarm(String.valueOf(ContextUtil.getUID()), id, weekRepeat, hour, minute, item, remind, shakeMode, shakeNum);
    }

    public void editAlarm(int id, int weekReapt, int hour, int minute, String item, String remind, boolean isOpen, int shakeMode, int shakeNum) {
        AlarmManager.editAlarm(String.valueOf(ContextUtil.getUID()), id, weekReapt, hour, minute, item, remind, isOpen, shakeMode, shakeNum);
    }

    public void deleteAlarm(int id) {
        AlarmManager.deleteAlarm(String.valueOf(ContextUtil.getUID()), id);
    }

    public void openAlarm(int id) {
        AlarmManager.openAlarm(String.valueOf(ContextUtil.getUID()), id);
    }

    public void closeAlarm(int id) {
        AlarmManager.closeAlarm(String.valueOf(ContextUtil.getUID()), id);
    }

    public int isAddAlarm(int maxNum) {
        return AlarmManager.isAddAlarm(String.valueOf(ContextUtil.getUID()), maxNum);
    }

    public List<TB_Alarmstatue> getAllAlarmData() {
        return AlarmManager.getAllAlarmData();
    }

    public String getWeekRepeatStr(byte weakRepeat) {
        this.daysOfWeek = ContextUtil.app.getResources().getStringArray(R.array.device_module_day_of_week);
        StringBuilder sb = new StringBuilder();
        if (weakRepeat == -1 || weakRepeat == Byte.MAX_VALUE) {
            sb.append(ContextUtil.app.getString(R.string.device_module_every_day));
            return sb.toString();
        }
        if ((weakRepeat & 64) != 0) {
            sb.append(this.daysOfWeek[0]);
            sb.append(",");
        }
        if ((weakRepeat & 32) != 0) {
            sb.append(this.daysOfWeek[1]);
            sb.append(",");
        }
        if ((weakRepeat & 16) != 0) {
            sb.append(this.daysOfWeek[2]);
            sb.append(",");
        }
        if ((weakRepeat & 8) != 0) {
            sb.append(this.daysOfWeek[3]);
            sb.append(",");
        }
        if ((weakRepeat & 4) != 0) {
            sb.append(this.daysOfWeek[4]);
            sb.append(",");
        }
        if ((weakRepeat & 2) != 0) {
            sb.append(this.daysOfWeek[5]);
            sb.append(",");
        }
        if ((weakRepeat & 1) != 0) {
            sb.append(this.daysOfWeek[6]);
            sb.append(",");
        }
        if (sb.length() == 0) {
            return "";
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
