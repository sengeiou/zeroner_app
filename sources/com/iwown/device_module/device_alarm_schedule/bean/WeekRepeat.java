package com.iwown.device_module.device_alarm_schedule.bean;

public class WeekRepeat {
    private boolean check;
    private String day;
    private int repeat;

    public WeekRepeat(String day2, boolean check2) {
        this.day = day2;
        this.check = check2;
    }

    public WeekRepeat(String day2, boolean check2, int repeat2) {
        this.day = day2;
        this.check = check2;
        this.repeat = repeat2;
    }

    public int getRepeat() {
        return this.repeat;
    }

    public void setRepeat(int repeat2) {
        this.repeat = repeat2;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day2) {
        this.day = day2;
    }

    public boolean isCheck() {
        return this.check;
    }

    public void setCheck(boolean check2) {
        this.check = check2;
    }
}
