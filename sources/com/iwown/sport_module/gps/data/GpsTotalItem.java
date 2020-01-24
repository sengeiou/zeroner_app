package com.iwown.sport_module.gps.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class GpsTotalItem implements MultiItemEntity {
    private int activity_count;
    private long all_time;
    private int data_type;
    private int itemType;
    private int month;
    private float total_calorie;
    private float total_distance;
    private int year;

    public GpsTotalItem(int itemType2) {
        this.itemType = itemType2;
    }

    public void setItemType(int itemType2) {
        this.itemType = itemType2;
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

    public float getTotal_distance() {
        return this.total_distance;
    }

    public void setTotal_distance(float total_distance2) {
        this.total_distance = total_distance2;
    }

    public int getActivity_count() {
        return this.activity_count;
    }

    public void setActivity_count(int activity_count2) {
        this.activity_count = activity_count2;
    }

    public int getItemType() {
        return 0;
    }

    public float getTotal_calorie() {
        return this.total_calorie;
    }

    public void setTotal_calorie(float total_calorie2) {
        this.total_calorie = total_calorie2;
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public long getAll_time() {
        return this.all_time;
    }

    public void setAll_time(long all_time2) {
        this.all_time = all_time2;
    }
}
