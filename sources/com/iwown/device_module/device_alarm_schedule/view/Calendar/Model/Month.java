package com.iwown.device_module.device_alarm_schedule.view.Calendar.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Month implements Parcelable {
    public static final Creator<Month> CREATOR = new Creator<Month>() {
        public Month createFromParcel(Parcel in) {
            return new Month(in);
        }

        public Month[] newArray(int size) {
            return new Month[size];
        }
    };
    private static final int DAYS_IN_WEEK = 7;
    private final int mDay;
    private int mDelta;
    private boolean mIsMonthOfToday;
    private final int mMonth;
    private List<MonthDay> mMonthDayList;
    private int mTotalDays;
    private int mTotalWeeks;
    private final int mYear;

    public Month(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt());
    }

    public Month(int year, int month, int day) {
        this.mMonthDayList = new ArrayList();
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        addMonthDay(year, month, day);
    }

    public Month(MonthDay day) {
        this.mMonthDayList = new ArrayList();
        this.mYear = day.getmYear();
        this.mMonth = day.getmMonth();
        this.mDay = day.getmDay();
        addMonthDay(this.mYear, this.mMonth, this.mDay);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mYear);
        dest.writeInt(this.mMonth);
        dest.writeInt(this.mDay);
    }

    private Calendar generateWorkingCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        this.mIsMonthOfToday = isMonthOfToday(calendar);
        this.mTotalDays = calendar.getActualMaximum(5);
        calendar.set(year, month, 1);
        this.mDelta = calendar.get(7) - 1;
        calendar.add(5, -this.mDelta);
        this.mTotalWeeks = 6;
        return calendar;
    }

    private void addMonthDay(int year, int month, int day) {
        Calendar calendar = generateWorkingCalendar(year, month, day);
        for (int i = 0; i < this.mTotalWeeks; i++) {
            for (int j = 0; j < 7; j++) {
                MonthDay monthDay = new MonthDay(calendar);
                int currentDays = (i * 7) + j;
                monthDay.setCheckable(currentDays >= this.mDelta && currentDays < this.mTotalDays + this.mDelta);
                if (currentDays < this.mDelta) {
                    monthDay.setDayFlag(1);
                } else if (currentDays >= this.mTotalDays + this.mDelta) {
                    monthDay.setDayFlag(2);
                }
                this.mMonthDayList.add(monthDay);
                calendar.add(5, 1);
            }
        }
    }

    private boolean isMonthOfToday(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        if (calendar.get(1) == today.get(1) && calendar.get(2) == today.get(2)) {
            return true;
        }
        return false;
    }

    public int getmYear() {
        return this.mYear;
    }

    public int getmMonth() {
        return this.mMonth;
    }

    public int getmDay() {
        return this.mDay;
    }

    public int getmTotalDays() {
        return this.mTotalDays;
    }

    public int getWeeksInMonth() {
        return this.mTotalWeeks;
    }

    public MonthDay getMonthDay(int index) {
        if (this.mMonthDayList.size() <= index) {
            return null;
        }
        return (MonthDay) this.mMonthDayList.get(index);
    }

    public MonthDay getMonthDay(int xIndex, int yIndex) {
        return getMonthDay((xIndex * 7) + yIndex);
    }

    public int getYear() {
        return this.mYear;
    }

    public int getMonth() {
        return this.mMonth;
    }

    public boolean isMonthOfToday() {
        return this.mIsMonthOfToday;
    }

    public int getIndexOfDayInCurMonth(int day) {
        MonthDay lastMonthday = null;
        int index = -1;
        for (int i = 0; i < this.mMonthDayList.size(); i++) {
            MonthDay monthDay = (MonthDay) this.mMonthDayList.get(i);
            if (monthDay.getDayFlag() == 0) {
                lastMonthday = monthDay;
                index = i;
            }
            if (monthDay.isCheckable() && monthDay.getCalendar().get(5) == day) {
                return i;
            }
        }
        if (lastMonthday == null || day < lastMonthday.getmDay()) {
            return -1;
        }
        return index;
    }

    public int getIndexOfToday() {
        if (!this.mIsMonthOfToday) {
            return -1;
        }
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        return getIndexOfDayInCurMonth(today.get(5));
    }
}
