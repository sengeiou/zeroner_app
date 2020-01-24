package com.iwown.device_module.device_alarm_schedule.utils;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.device_alarm_schedule.bean.ScheduleInfo;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.date.DateUtil.DateFormater;
import com.socks.library.KLog;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.litepal.crud.DataSupport;

public class AddScheduleUtil {
    public static final byte EVERY_DAY = Byte.MAX_VALUE;
    public static final byte NO_REPEAT = 0;
    public static final byte REPEAT = Byte.MIN_VALUE;
    public static final byte WEEKEND = 3;
    public static final byte WEEK_1 = 64;
    public static final byte WEEK_2 = 32;
    public static final byte WEEK_3 = 16;
    public static final byte WEEK_4 = 8;
    public static final byte WEEK_5 = 4;
    public static final byte WEEK_6 = 2;
    public static final byte WEEK_7 = 1;
    public static final byte WORK_DAY = 124;
    private static byte[] byteWeek = {1, 64, 32, 16, 8, 4, 2};
    public static byte dataByteWeek;
    public static int dataDay;
    public static int dataHour;
    public static int dataID;
    public static boolean dataIsOpen;
    public static String dataItem;
    public static int dataMinute;
    public static int dataMonth;
    public static String dataRemind;
    public static int dataYear;
    public static List<ScheduleInfo> list = new ArrayList();
    public static int shakeMode;
    public static int shakeNum;
    public static TB_schedulestatue tbScheduleStatue = new TB_schedulestatue();

    public static boolean isBeforeToday(int year, int month, int day, Calendar curCalendar) {
        int cYear = curCalendar.get(1);
        int cMonth = curCalendar.get(2) + 1;
        int cDay = curCalendar.get(5);
        if (year < cYear) {
            return true;
        }
        if (year > cYear) {
            return false;
        }
        if (year != cYear) {
            return false;
        }
        if (month > cMonth) {
            return false;
        }
        if (month < cMonth || day < cDay) {
            return true;
        }
        return false;
    }

    public static boolean isBeforeCurrentTime(int hour, int minute, Calendar curCalendar) {
        int curHour = curCalendar.get(11);
        int curMinute = curCalendar.get(12);
        if (hour < curHour) {
            return true;
        }
        if (hour != curHour || minute > curMinute) {
            return false;
        }
        return true;
    }

    public static boolean isToday(int year, int month, int day, Calendar curCalendar) {
        if (day == curCalendar.get(5) && month == curCalendar.get(2) + 1 && year == curCalendar.get(1)) {
            return true;
        }
        return false;
    }

    public static int getTBTimesInt(int hour, int minute) {
        return (hour * 100) + minute;
    }

    public static int getTBDatesInt(int year, int month, int day) {
        return ((year - 2000) * 10000) + (month * 100) + day;
    }

    public static String getTBTimesString(int hour, int minute) {
        return String.valueOf(getTBTimesInt(hour, minute));
    }

    public static String getTBDatesString(int year, int month, int day) {
        return String.valueOf(getTBDatesInt(year, month, day));
    }

    public static boolean isAtAlarmWeek(int week, int weekRepeat) {
        return (byteWeek[week] & ((byte) weekRepeat)) > 0;
    }

    public static boolean isChangSchedule(String UID, int year, int month, int day, int hour, int minute) {
        String times = getTBTimesString(hour, minute);
        if (DataSupport.where("uid = ? and dates = ? and times = ?", UID, getTBDatesString(year, month, day), times).count(TB_schedulestatue.class) == 0) {
            return true;
        }
        return false;
    }

    public static void packScheduleData(int id, int year, int month, int day, int hour, int minute, String item, String remind, int alarm_mode, int alarm_number) {
        dataID = id;
        dataYear = year;
        dataMonth = month;
        dataDay = day;
        dataHour = hour;
        dataMinute = minute;
        dataItem = item;
        dataRemind = remind;
        shakeMode = alarm_mode;
        shakeNum = alarm_number;
        ZGBaseUtils.setAlarmScheduleModeNumber(alarm_mode, alarm_number);
    }

    public static void packIDData(int id) {
        dataID = id;
    }

    public static void packAlarmData(int id, byte byteWeek2, int hour, int minute, String item, String remind, boolean isOpen, int alarm_mode, int alarm_number) {
        dataID = id;
        dataByteWeek = byteWeek2;
        dataHour = hour;
        dataMinute = minute;
        dataItem = item;
        dataRemind = remind;
        dataIsOpen = isOpen;
        shakeMode = alarm_mode;
        shakeNum = alarm_number;
        ZGBaseUtils.setAlarmScheduleModeNumber(alarm_mode, alarm_number);
    }

    public static void packScheduleTBData(TB_schedulestatue data) {
        tbScheduleStatue = data;
    }

    public static List<TB_Alarmstatue> getAllAlarmData(String UID) {
        new ArrayList();
        return DataSupport.where("UID=?", UID).find(TB_Alarmstatue.class);
    }

    public static boolean isTodayAlarm(TB_Alarmstatue data, int year, int month, int day) {
        if (TextUtils.isEmpty(data.getDate())) {
            return false;
        }
        try {
            DateUtil dateUtil = DateUtil.parse(data.getDate(), DateFormater.yyyyMMdd_HHmm);
            if (dateUtil.getYear() == year && dateUtil.getMonth() == month && dateUtil.getDay() == day) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }

    public static boolean checkAlarmExpired(TB_Alarmstatue alarmstatue) {
        if (alarmstatue.getAc_Conf() != 0) {
            return false;
        }
        try {
            if (!TextUtils.isEmpty(alarmstatue.getDate()) && DateUtil.parse(alarmstatue.getDate(), DateFormater.yyyyMMdd_HHmm).getTimestamp() <= System.currentTimeMillis()) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }

    public static boolean checkSameData(String uid, int repeat, int hour, int min) {
        KLog.i("------" + repeat + "----" + hour + "-----" + min);
        List<TB_Alarmstatue> alarms = DataSupport.where("UID=? and Ac_Conf=? and Ac_Hour=? and Ac_Minute=?", uid, repeat + "", hour + "", min + "").find(TB_Alarmstatue.class);
        if (alarms == null || alarms.size() <= 0) {
            return false;
        }
        return true;
    }
}
