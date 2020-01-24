package com.iwown.sport_module.pojo.active;

import com.iwown.lib_common.date.DateUtil;
import java.util.ArrayList;
import java.util.List;

public class SportAllData {
    private int active_time;
    private float[] caloreis;
    private int calorie;
    private String data_from;
    private int day;
    private List<SportDetailsData> detailsData;
    private double distance;
    private int month;
    private boolean real_has_data;
    private int stand_hours;
    private float[] step_value_every_h;
    private int steps;
    private int year;

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public SportAllData() {
        this.real_has_data = true;
        this.data_from = "";
        this.caloreis = new float[24];
        this.step_value_every_h = new float[24];
        this.detailsData = new ArrayList();
    }

    public SportAllData(int year2, int month2, int day2, String data_from2, boolean realHasData) {
        this.real_has_data = true;
        this.data_from = "";
        this.year = year2;
        this.month = month2;
        this.day = day2;
        this.real_has_data = realHasData;
        this.data_from = data_from2;
        this.caloreis = new float[24];
        this.step_value_every_h = new float[24];
        this.detailsData = new ArrayList();
    }

    public int getActive_time() {
        return this.active_time;
    }

    public void setActive_time(int active_time2) {
        this.active_time = active_time2;
    }

    public boolean isReal_has_data() {
        return this.real_has_data;
    }

    public void setReal_has_data(boolean real_has_data2) {
        this.real_has_data = real_has_data2;
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

    public String getDateStr() {
        return new DateUtil(this.year, this.month, this.day).getSyyyyMMddDate();
    }

    public float[] getStep_value_every_h() {
        return this.step_value_every_h;
    }

    public int getStand_hours() {
        return this.stand_hours;
    }

    public void setStand_hours(int stand_hours2) {
        this.stand_hours = stand_hours2;
    }

    public float[] getCaloreis() {
        return this.caloreis;
    }

    public void setCaloreis(float[] caloreis2) {
        this.caloreis = caloreis2;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps2) {
        this.steps = steps2;
    }

    public int getCalorie() {
        return this.calorie;
    }

    public void setCalorie(int calorie2) {
        this.calorie = calorie2;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance2) {
        this.distance = distance2;
    }

    public List<SportDetailsData> getDetailsDatas() {
        return this.detailsData;
    }

    public void setDetailsDatas(List<SportDetailsData> detailsData2) {
        this.detailsData = detailsData2;
    }
}
