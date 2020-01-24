package com.iwown.device_module.device_alarm_schedule.iv.bean;

import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.TB_schedulestatue;

public class RecentNotification {
    public static final int TYPE_ALARM = 2;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SCHEDULE = 1;
    private int day;
    private int hour;
    private TB_Alarmstatue mTBAlarmstatue;
    private TB_schedulestatue mTBSchedulestatue;
    private int minute;
    private int month;
    private String remind;
    private String title;
    private int type = 0;
    private int year;

    public TB_Alarmstatue getTBAlarmstatue() {
        return this.mTBAlarmstatue;
    }

    public void setTBAlarmstatue(TB_Alarmstatue TBAlarmstatue) {
        this.mTBAlarmstatue = TBAlarmstatue;
    }

    public TB_schedulestatue getTBSchedulestatue() {
        return this.mTBSchedulestatue;
    }

    public void setTBSchedulestatue(TB_schedulestatue TBSchedulestatue) {
        this.mTBSchedulestatue = TBSchedulestatue;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getRemind() {
        return this.remind;
    }

    public void setRemind(String remind2) {
        this.remind = remind2;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year2) {
        this.year = year2;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month2) {
        this.month = month2;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute2) {
        this.minute = minute2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }
}
