package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_schedulestatue extends DataSupport {
    private String UID;
    private int dates;
    private int day;
    private int hour;
    private int id;
    private int minute;
    private int month;
    private String remind;
    private String text;
    private int times;
    private int year;
    private int zg_mode = 1;
    private int zg_number = 6;

    public int getZg_mode() {
        return this.zg_mode;
    }

    public void setZg_mode(int zg_mode2) {
        this.zg_mode = zg_mode2;
    }

    public int getZg_number() {
        return this.zg_number;
    }

    public void setZg_number(int zg_number2) {
        this.zg_number = zg_number2;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String UID2) {
        this.UID = UID2;
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

    public String getText() {
        return this.text;
    }

    public void setText(String text2) {
        this.text = text2;
    }

    public int getTimes() {
        return this.times;
    }

    public void setTimes(int times2) {
        this.times = times2;
    }

    public int getDates() {
        return this.dates;
    }

    public void setDates(int dates2) {
        this.dates = dates2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public boolean isSame(TB_schedulestatue data) {
        if (this.year == data.getYear() && this.month == data.getMonth() && this.day == data.getDay() && this.hour == data.getHour() && this.minute == data.getMinute()) {
            return true;
        }
        return false;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof TB_schedulestatue)) {
            return false;
        }
        TB_schedulestatue that = (TB_schedulestatue) o;
        if (getId() != that.getId() || getDates() != that.getDates() || getTimes() != that.getTimes() || getYear() != that.getYear() || getMonth() != that.getMonth() || getDay() != that.getDay() || getHour() != that.getHour() || getMinute() != that.getMinute()) {
            return false;
        }
        if (getUID() != null) {
            if (!getUID().equals(that.getUID())) {
                return false;
            }
        } else if (that.getUID() != null) {
            return false;
        }
        if (getRemind() != null) {
            if (!getRemind().equals(that.getRemind())) {
                return false;
            }
        } else if (that.getRemind() != null) {
            return false;
        }
        if (getText() != null) {
            z = getText().equals(that.getText());
        } else if (that.getText() != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int id2 = ((getId() * 31) + (getUID() != null ? getUID().hashCode() : 0)) * 31;
        if (getRemind() != null) {
            i = getRemind().hashCode();
        } else {
            i = 0;
        }
        int dates2 = (((((((((((((((id2 + i) * 31) + getDates()) * 31) + getTimes()) * 31) + getYear()) * 31) + getMonth()) * 31) + getDay()) * 31) + getHour()) * 31) + getMinute()) * 31;
        if (getText() != null) {
            i2 = getText().hashCode();
        }
        return dates2 + i2;
    }
}
