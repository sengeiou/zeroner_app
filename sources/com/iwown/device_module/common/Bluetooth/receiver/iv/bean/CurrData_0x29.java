package com.iwown.device_module.common.Bluetooth.receiver.iv.bean;

import android.annotation.SuppressLint;
import android.content.Context;
import com.iwown.ble_module.iwown.bean.TotalSportData;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.utils.PrefUtil;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@SuppressLint({"ParcelCreator"})
public class CurrData_0x29 extends Result {
    private static int[] indexTypes = {2, 3, 4, 5, 6};
    private static final long serialVersionUID = 1;
    private static int[] suportTypes = {128, 129, Opcodes.INT_TO_FLOAT, Opcodes.INT_TO_DOUBLE, Opcodes.LONG_TO_INT, Opcodes.LONG_TO_FLOAT, Opcodes.LONG_TO_DOUBLE, 135, Opcodes.FLOAT_TO_LONG, Opcodes.FLOAT_TO_DOUBLE, Opcodes.DOUBLE_TO_INT, Opcodes.DOUBLE_TO_LONG, Opcodes.DOUBLE_TO_FLOAT, Opcodes.INT_TO_CHAR, Opcodes.INT_TO_SHORT, Opcodes.ADD_INT, Opcodes.SUB_INT};
    private int activityTime;
    private int count;
    private int day;
    private int endTime;
    private int month;
    private String sportCalories;
    private String sportDistances;
    private String sportSteps;
    private int sportType;
    private int startTime;
    private String totalCalories;
    private String totalSteps;
    private long uid;
    private int year;

    public CurrData_0x29() {
    }

    public CurrData_0x29(String totalSteps2, String totalCalories2) {
        this.totalSteps = totalSteps2;
        this.totalCalories = totalCalories2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public static CurrData_0x29 parse(TotalSportData sportData, Context context) {
        CurrData_0x29 nd = new CurrData_0x29();
        DecimalFormat decimalFormat = new DecimalFormat(".0", new DecimalFormatSymbols(Locale.ENGLISH));
        nd.setYear(sportData.getYear());
        nd.setMonth(sportData.getMonth());
        nd.setDay(sportData.getDay());
        nd.setUid(PrefUtil.getLong(context, UserAction.User_Uid));
        nd.setSportType(sportData.getSport_type());
        if (nd.getYear() - 2000 == 255 && nd.getMonth() - 1 == 255 && nd.getDay() - 1 == 255) {
            nd.setYear(255);
            nd.setMonth(255);
            nd.setDay(255);
            nd.setSportCalories(sportData.getCalories() + "");
            nd.setSportSteps(sportData.getSteps() + "");
            nd.setSportDistances(decimalFormat.format((double) sportData.getDistance()));
        } else {
            nd.setSportCalories(sportData.getCalories() + "");
            if (sportData.getSport_type() == 1) {
                nd.setSportSteps(sportData.getSteps() + "");
                nd.setSportDistances(decimalFormat.format((double) sportData.getDistance()));
            }
        }
        return nd;
    }

    public boolean isLive() {
        if (this.year == 255 && this.month == 255 && this.day == 255) {
            return true;
        }
        return false;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int startTime2) {
        this.startTime = startTime2;
    }

    public int getActivityTime() {
        return this.activityTime;
    }

    public void setActivityTime(int activityTime2) {
        this.activityTime = activityTime2;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int endTime2) {
        this.endTime = endTime2;
    }

    public String getTotalSteps() {
        return this.totalSteps;
    }

    public void setTotalSteps(String totalSteps2) {
        this.totalSteps = totalSteps2;
    }

    public String getTotalCalories() {
        return this.totalCalories;
    }

    public void setTotalCalories(String totalCalories2) {
        this.totalCalories = totalCalories2;
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

    public int getSportType() {
        return this.sportType;
    }

    public void setSportType(int sportType2) {
        this.sportType = sportType2;
    }

    public String getSportSteps() {
        return this.sportSteps;
    }

    public void setSportSteps(String sportSteps2) {
        this.sportSteps = sportSteps2;
    }

    public String getSportDistances() {
        return this.sportDistances;
    }

    public void setSportDistances(String sportDistances2) {
        this.sportDistances = sportDistances2;
    }

    public String getSportCalories() {
        return this.sportCalories;
    }

    public void setSportCalories(String sportCalories2) {
        this.sportCalories = sportCalories2;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }
}
