package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_ecg_view_data extends DataSupport {
    private int _uploaded;
    private String ai_result;
    private String dataArray;
    private String data_from;
    private String date;
    private int heartrate;
    private String note;
    private long uid;
    private long unixTime;
    private String url;

    public String getAi_result() {
        return this.ai_result;
    }

    public void setAi_result(String ai_result2) {
        this.ai_result = ai_result2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public long getUnixTime() {
        return this.unixTime;
    }

    public void setUnixTime(long unixTime2) {
        this.unixTime = unixTime2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public int getHeartrate() {
        return this.heartrate;
    }

    public void setHeartrate(int heartrate2) {
        this.heartrate = heartrate2;
    }

    public int get_uploaded() {
        return this._uploaded;
    }

    public void set_uploaded(int _uploaded2) {
        this._uploaded = _uploaded2;
    }

    public String getDataArray() {
        return this.dataArray;
    }

    public void setDataArray(String dataArray2) {
        this.dataArray = dataArray2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note2) {
        this.note = note2;
    }
}
