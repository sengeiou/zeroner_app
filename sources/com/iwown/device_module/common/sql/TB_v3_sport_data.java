package com.iwown.device_module.common.sql;

import android.content.Context;
import android.util.Log;
import com.iwown.ble_module.iwown.bean.ZeronerDetailSportData;
import com.iwown.ble_module.utils.Util;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.Detail_data;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import java.io.Serializable;
import java.util.Calendar;
import org.litepal.crud.DataSupport;

public class TB_v3_sport_data extends DataSupport implements Serializable {
    private int _uploaded;
    private double calorie;
    private int complete_progress;
    private String data_from;
    private int day;
    private String detail_data;
    private int end_time;
    private long end_uxtime;
    private int month;
    private int reserved;
    private int sport_type;
    private int start_time;
    private long start_uxtime;
    private long uid;
    private int week;
    private int year;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public double getCalorie() {
        return this.calorie;
    }

    public void setCalorie(double calorie2) {
        this.calorie = calorie2;
    }

    public int getComplete_progress() {
        return this.complete_progress;
    }

    public void setComplete_progress(int complete_progress2) {
        this.complete_progress = complete_progress2;
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

    public String getDetail_data() {
        return this.detail_data;
    }

    public void setDetail_data(String detail_data2) {
        this.detail_data = detail_data2;
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

    public long getEnd_uxtime() {
        return this.end_uxtime;
    }

    public void setEnd_uxtime(long end_uxtime2) {
        this.end_uxtime = end_uxtime2;
    }

    public long getStart_uxtime() {
        return this.start_uxtime;
    }

    public void setStart_uxtime(long start_uxtime2) {
        this.start_uxtime = start_uxtime2;
    }

    public static TB_v3_sport_data parse(ZeronerDetailSportData datas, Context context, int goal) {
        TB_v3_sport_data nd = new TB_v3_sport_data();
        int year2 = datas.getYear();
        nd.setYear(year2);
        int month2 = datas.getMonth();
        nd.setMonth(month2);
        int day2 = datas.getDay();
        nd.setDay(day2);
        if (nd.getYear() - 2000 == 255 && nd.getMonth() - 1 == 255 && nd.getDay() - 1 == 255) {
            nd.setYear(255);
            nd.setMonth(255);
            nd.setDay(255);
        }
        Calendar c = Calendar.getInstance();
        c.set(year2, month2 - 1, day2);
        nd.setWeek(c.get(3));
        nd.setData_from(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
        nd.setUid(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
        nd.set_uploaded(0);
        nd.setSport_type(datas.getSport_type());
        nd.setStart_time(datas.getStartMin());
        nd.setEnd_time(datas.getEndMin());
        if (nd.getEnd_time() - nd.getStart_time() < 0) {
            nd.setEnd_uxtime(Util.date2TimeStamp(year2, month2, day2 + 1, nd.getEnd_time() / 60, nd.getEnd_time() % 60));
        } else {
            nd.setEnd_uxtime(Util.date2TimeStamp(year2, month2, day2, nd.getEnd_time() / 60, nd.getEnd_time() % 60));
        }
        nd.setStart_uxtime(Util.date2TimeStamp(year2, month2, day2, nd.getStart_time() / 60, nd.getStart_time() % 60));
        float a1 = datas.getCalories();
        Log.d("testcal", "卡路里is: " + a1 + "");
        nd.setCalorie((double) a1);
        Detail_data d = new Detail_data();
        d.setStep(datas.getSteps());
        d.setDistance(datas.getDistance());
        d.setActivity(datas.getActivity());
        d.setCount(datas.getOtherCount());
        nd.setDetail_data(JsonUtils.toJson(d));
        return nd;
    }

    public boolean isLive() {
        if (this.year == 255 && this.month == 255 && this.day == 255) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (obj == null || !(obj instanceof TB_v3_sport_data)) {
            return false;
        }
        if (this.uid == ((TB_v3_sport_data) obj).uid && ((TB_v3_sport_data) obj).getData_from() != null && ((TB_v3_sport_data) obj).getData_from().equals(this.data_from) && getStart_uxtime() == ((TB_v3_sport_data) obj).getStart_uxtime() && this.sport_type == ((TB_v3_sport_data) obj).getSport_type()) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }
}
