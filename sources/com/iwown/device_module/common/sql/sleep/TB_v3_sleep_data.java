package com.iwown.device_module.common.sql.sleep;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.iwown.ble_module.iwown.bean.ZeronerSleepData;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import java.util.Calendar;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class TB_v3_sleep_data extends DataSupport {
    private int _uploaded;
    private int activity;
    private int all_end;
    private int all_start;
    private String data_from;
    private int day;
    private int end_time;
    @Column(ignore = true)
    public int index;
    private int month;
    private int reserved;
    private int sleep_type;
    private int start_time;
    private long uid;
    private int week;
    private int year;

    public int getAll_start() {
        return this.all_start;
    }

    public void setAll_start(int all_start2) {
        this.all_start = all_start2;
    }

    public int getAll_end() {
        return this.all_end;
    }

    public void setAll_end(int all_end2) {
        this.all_end = all_end2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getSleep_type() {
        return this.sleep_type;
    }

    public void setSleep_type(int sleep_type2) {
        this.sleep_type = sleep_type2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
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

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week2) {
        this.week = week2;
    }

    public int getStart_time() {
        return this.start_time;
    }

    public void setStart_time(int start_time2) {
        this.start_time = start_time2;
    }

    public int getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(int end_time2) {
        this.end_time = end_time2;
    }

    public int getActivity() {
        return this.activity;
    }

    public void setActivity(int activity2) {
        this.activity = activity2;
    }

    public int get_uploaded() {
        return this._uploaded;
    }

    public void set_uploaded(int _uploaded2) {
        this._uploaded = _uploaded2;
    }

    public int getReserved() {
        return this.reserved;
    }

    public void setReserved(int reserved2) {
        this.reserved = reserved2;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public static TB_v3_sleep_data parse(ZeronerSleepData data, Context context) {
        TB_v3_sleep_data ne = new TB_v3_sleep_data();
        ne.setIndex(data.getIndex());
        int year2 = data.getYear();
        ne.setYear(year2);
        int month2 = data.getMonth();
        ne.setMonth(month2);
        int day2 = data.getDay();
        ne.setDay(day2);
        if (ne.getYear() - 2000 == 255 && ne.getMonth() - 1 == 255 && ne.getDay() - 1 == 255) {
            ne.setYear(255);
            ne.setMonth(255);
            ne.setDay(255);
        }
        Calendar c = Calendar.getInstance();
        c.set(year2, month2 - 1, day2);
        ne.setWeek(c.get(3));
        ne.setStart_time(data.getStart());
        ne.setEnd_time(data.getEnd());
        ne.setActivity(data.getTimes());
        ne.setSleep_type(data.getType());
        try {
            ne.setAll_start(data.getSleep_enter());
            ne.setAll_end(data.getSleep_exit());
        } catch (Exception e) {
        }
        ne.setUid(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
        ne.set_uploaded(0);
        String device = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        if (TextUtils.isEmpty(device)) {
            device = "server";
        }
        ne.setData_from(device);
        return ne;
    }

    public String toJson() {
        return new Gson().toJson((Object) this);
    }

    public String toString() {
        return "TB_v3_sleep_data{uid=" + this.uid + ", sleep_type=" + this.sleep_type + ", data_from='" + this.data_from + '\'' + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", week=" + this.week + ", start_time=" + this.start_time + ", end_time=" + this.end_time + ", activity=" + this.activity + ", _uploaded=" + this._uploaded + ", reserved=" + this.reserved + ", index=" + this.index + '}';
    }
}
