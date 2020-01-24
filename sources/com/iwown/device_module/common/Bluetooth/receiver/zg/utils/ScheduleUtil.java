package com.iwown.device_module.common.Bluetooth.receiver.zg.utils;

import com.iwown.device_module.R;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class ScheduleUtil {
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
    public static int shakeMode;
    public static int shakeNum;
    public static TB_schedulestatue tbScheduleStatue = new TB_schedulestatue();

    public static String getScheduleDate(int year, int month, int day) {
        String text;
        String text2 = year + HelpFormatter.DEFAULT_OPT_PREFIX;
        if (month > 9) {
            text = text2 + month + HelpFormatter.DEFAULT_OPT_PREFIX;
        } else {
            text = text2 + "0" + month + HelpFormatter.DEFAULT_OPT_PREFIX;
        }
        if (day > 9) {
            return text + day;
        }
        return text + "0" + day;
    }

    public static String getCalendarTitle(int year, int month) {
        String text = year + HelpFormatter.DEFAULT_OPT_PREFIX;
        if (month > 9) {
            return text + month;
        }
        return text + "0" + month;
    }

    public static String getStringDate(int year, int month, int day) {
        String text;
        String text2 = year + HelpFormatter.DEFAULT_OPT_PREFIX;
        if (month > 9) {
            text = text2 + month + HelpFormatter.DEFAULT_OPT_PREFIX;
        } else {
            text = text2 + "0" + month + HelpFormatter.DEFAULT_OPT_PREFIX;
        }
        if (day > 9) {
            return text + day;
        }
        return text + "0" + day;
    }

    public static String getStringTime(int hour, int minute) {
        String strHour;
        String strMinute;
        if (hour < 10) {
            strHour = "0" + hour;
        } else {
            strHour = "" + hour;
        }
        if (minute < 10) {
            strMinute = ":0" + minute;
        } else {
            strMinute = ":" + minute;
        }
        return strHour + strMinute;
    }

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
        if ((byteWeek[week] & ((byte) weekRepeat)) > 0) {
            return true;
        }
        return false;
    }

    public static byte getWeekByte(int week, boolean isRepeat) {
        byte resultWeek = byteWeek[week];
        if (isRepeat) {
            return (byte) (resultWeek | 128);
        }
        return (byte) (resultWeek & Byte.MAX_VALUE);
    }

    public static String getStringAlarmWeek(int week) {
        String text;
        byte bWeek = (byte) week;
        if ((bWeek & 128) == 0) {
            text = ContextUtil.app.getString(R.string.device_module_schedule_only_once);
        } else {
            text = ContextUtil.app.getString(R.string.device_module_repeat);
        }
        byte bWeek2 = (byte) (bWeek & Byte.MAX_VALUE);
        if (bWeek2 == Byte.MAX_VALUE) {
            return text + ContextUtil.app.getString(R.string.device_module_schedule_every_day);
        }
        if ((bWeek2 & 1) > 0) {
            text = text + ContextUtil.app.getString(R.string.device_module_schedule_sun);
        }
        if ((bWeek2 & 64) > 0) {
            text = text + ContextUtil.app.getString(R.string.device_module_schedule_mon);
        }
        if ((bWeek2 & 32) > 0) {
            text = text + ContextUtil.app.getString(R.string.device_module_schedule_tue);
        }
        if ((bWeek2 & 16) > 0) {
            text = text + ContextUtil.app.getString(R.string.device_module_schedule_wes);
        }
        if ((bWeek2 & 8) > 0) {
            text = text + ContextUtil.app.getString(R.string.device_module_schedule_thur);
        }
        if ((bWeek2 & 4) > 0) {
            text = text + ContextUtil.app.getString(R.string.device_module_schedule_fir);
        }
        if ((bWeek2 & 2) > 0) {
            text = text + ContextUtil.app.getString(R.string.device_module_schedule_sat);
        }
        return text;
    }

    public static boolean isChangSchedule(String UID, int year, int month, int day, int hour, int minute) {
        String times = getTBTimesString(hour, minute);
        if (DataSupport.where("UID = ? AND dates = ? AND times = ?", UID, getTBDatesString(year, month, day), times).count(TB_schedulestatue.class) == 0) {
            return true;
        }
        return false;
    }

    public static void packScheduleData(int id, int year, int month, int day, int hour, int minute, String item, String remind, int shakeMode2, int shakeNum2) {
        dataID = id;
        dataYear = year;
        dataMonth = month;
        dataDay = day;
        dataHour = hour;
        dataMinute = minute;
        dataItem = item;
        dataRemind = remind;
        shakeNum = shakeNum2;
        shakeMode = shakeMode2;
    }

    public static void packIDData(int id) {
        dataID = id;
    }

    public static void packAlarmData(int id, byte byteWeek2, int hour, int minute, String item, String remind, boolean isOpen, int shakeMode2, int shakeNum2) {
        dataID = id;
        dataByteWeek = byteWeek2;
        dataHour = hour;
        dataMinute = minute;
        dataItem = item;
        dataRemind = remind;
        dataIsOpen = isOpen;
        shakeMode = shakeMode2;
        shakeNum = shakeNum2;
    }

    public static void packScheduleTBData(TB_schedulestatue data) {
        tbScheduleStatue = data;
    }

    public static List<TB_Alarmstatue> getAllAlarmData(String UID) {
        new ArrayList();
        return DataSupport.where("UID=?", UID).find(TB_Alarmstatue.class);
    }
}
