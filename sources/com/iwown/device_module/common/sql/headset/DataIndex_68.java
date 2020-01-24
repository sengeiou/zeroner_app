package com.iwown.device_module.common.sql.headset;

import android.support.annotation.NonNull;
import org.litepal.crud.DataSupport;

public class DataIndex_68 extends DataSupport implements Comparable<DataIndex_68> {
    private int day;
    private String device_name;
    private int end_idx;
    private int month;
    private int processed;
    private String send_cmd;
    private int start_idx;
    private long uid;
    private int year;

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

    public int getStart_idx() {
        return this.start_idx;
    }

    public void setStart_idx(int start_idx2) {
        this.start_idx = start_idx2;
    }

    public int getEnd_idx() {
        return this.end_idx;
    }

    public void setEnd_idx(int end_idx2) {
        this.end_idx = end_idx2;
    }

    public String getSend_cmd() {
        return this.send_cmd;
    }

    public void setSend_cmd(String send_cmd2) {
        this.send_cmd = send_cmd2;
    }

    public int getProcessed() {
        return this.processed;
    }

    public void setProcessed(int processed2) {
        this.processed = processed2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getDevice_name() {
        return this.device_name;
    }

    public void setDevice_name(String device_name2) {
        this.device_name = device_name2;
    }

    public int compareTo(@NonNull DataIndex_68 obj) {
        if (this.year > obj.year) {
            return 1;
        }
        if (this.year < obj.year) {
            return -1;
        }
        if (this.month > obj.month) {
            return 1;
        }
        if (this.month < obj.month) {
            return -1;
        }
        if (this.day > obj.day) {
            return 1;
        }
        if (this.day < obj.day) {
            return -1;
        }
        return 0;
    }
}
