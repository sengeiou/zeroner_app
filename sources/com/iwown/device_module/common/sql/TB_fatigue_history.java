package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_fatigue_history extends DataSupport {
    private String data_from;
    private String date;
    private String detail;
    private long time;
    private long uid;
    private int upload;

    public int getUpload() {
        return this.upload;
    }

    public void setUpload(int upload2) {
        this.upload = upload2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail2) {
        this.detail = detail2;
    }

    public String toString() {
        return "TB_fatigue_history{uid=" + this.uid + ", date='" + this.date + '\'' + ", data_from='" + this.data_from + '\'' + ", detail='" + this.detail + '\'' + ", time=" + this.time + ", upload=" + this.upload + '}';
    }
}
