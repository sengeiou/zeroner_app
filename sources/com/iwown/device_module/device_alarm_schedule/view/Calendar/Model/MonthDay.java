package com.iwown.device_module.device_alarm_schedule.view.Calendar.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.Calendar;

public final class MonthDay implements Parcelable {
    public static final Creator<MonthDay> CREATOR = new Creator<MonthDay>() {
        public MonthDay createFromParcel(Parcel source) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(source.readLong());
            return new MonthDay(calendar);
        }

        public MonthDay[] newArray(int size) {
            return new MonthDay[size];
        }
    };
    public static final int NEXT_MONTH_DAY = 2;
    public static final int PREV_MONTH_DAY = 1;
    private Calendar mCalendar;
    private int mDay;
    private int mDayFlag;
    private int mDayOfWeek;
    private boolean mIsCheckable = true;
    private boolean mIsFirstDay;
    private boolean mIsHoliday;
    private boolean mIsToday;
    private boolean mIsWeekend;
    private Lunar mLunar;
    private String mLunarDay;
    private int mMonth;
    private int mYear;

    public MonthDay(Calendar calendar) {
        boolean z;
        String holiday;
        boolean z2 = false;
        copy(calendar);
        this.mDay = this.mCalendar.get(5);
        int dayOfWeek = this.mCalendar.get(7);
        this.mDayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 1 || dayOfWeek == 7) {
            z = true;
        } else {
            z = false;
        }
        this.mIsWeekend = z;
        this.mIsToday = isToday(this.mCalendar);
        String lunarDay = this.mLunar.getLunarDay();
        String lunarHoliday = this.mLunar.getLunarHoliday();
        String solarHoliday = this.mLunar.getSolarHolidy();
        if (TextUtils.isEmpty(lunarHoliday)) {
            holiday = solarHoliday;
        } else {
            holiday = lunarHoliday;
        }
        String solarTerm = this.mLunar.getSolarTerm();
        if (!TextUtils.isEmpty(holiday)) {
            lunarDay = holiday;
        } else if (!TextUtils.isEmpty(solarTerm)) {
            lunarDay = solarTerm;
        }
        this.mLunarDay = lunarDay;
        if (!TextUtils.isEmpty(holiday) || !TextUtils.isEmpty(solarTerm)) {
            z2 = true;
        }
        this.mIsHoliday = z2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mCalendar.getTimeInMillis());
    }

    private void copy(Calendar calendar) {
        this.mCalendar = Calendar.getInstance();
        this.mLunar = Lunar.newInstance();
        this.mCalendar.setTimeInMillis(calendar.getTimeInMillis());
        this.mLunar.setTimeInMillis(calendar.getTimeInMillis());
        this.mYear = calendar.get(1);
        this.mMonth = calendar.get(2);
        this.mDay = calendar.get(5);
    }

    private boolean isToday(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        if (calendar.get(1) == today.get(1) && calendar.get(2) == today.get(2) && calendar.get(5) == today.get(5)) {
            return true;
        }
        return false;
    }

    public int getmDay() {
        return this.mDay;
    }

    public int getmYear() {
        return this.mYear;
    }

    public int getmMonth() {
        return this.mMonth;
    }

    public int getmDayOfWeek() {
        return this.mDayOfWeek;
    }

    public String getSolarDay() {
        return Integer.toString(this.mDay);
    }

    public String getLunarDay() {
        return this.mLunarDay;
    }

    public boolean isWeekend() {
        return this.mIsWeekend;
    }

    public boolean isHoliday() {
        return this.mIsHoliday;
    }

    public boolean isFirstDay() {
        boolean z;
        if (this.mCalendar.get(5) == 1) {
            z = true;
        } else {
            z = false;
        }
        this.mIsFirstDay = z;
        return this.mIsFirstDay && this.mIsCheckable;
    }

    public void setCheckable(boolean checkable) {
        this.mIsCheckable = checkable;
    }

    public boolean isCheckable() {
        return this.mIsCheckable;
    }

    public void setDayFlag(int flag) {
        this.mDayFlag = flag;
    }

    public int getDayFlag() {
        return this.mDayFlag;
    }

    public boolean isToday() {
        return this.mIsToday;
    }

    public Calendar getCalendar() {
        return this.mCalendar;
    }

    public Lunar getLunar() {
        return this.mLunar;
    }
}
