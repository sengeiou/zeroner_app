package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_mtk_statue extends DataSupport {
    private String data_from;
    private long date;
    private int day;
    private int has_file;
    private int has_tb;
    private int has_up;
    private int month;
    private int type;
    private long uid;
    private int year;

    public long getId() {
        return getBaseObjId();
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public int getHas_file() {
        return this.has_file;
    }

    public void setHas_file(int has_file2) {
        this.has_file = has_file2;
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

    public int getHas_up() {
        return this.has_up;
    }

    public void setHas_up(int has_up2) {
        this.has_up = has_up2;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date2) {
        this.date = date2;
    }

    public int getHas_tb() {
        return this.has_tb;
    }

    public void setHas_tb(int has_tb2) {
        this.has_tb = has_tb2;
    }
}
