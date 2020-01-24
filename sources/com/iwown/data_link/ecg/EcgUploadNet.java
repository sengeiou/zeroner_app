package com.iwown.data_link.ecg;

public class EcgUploadNet {
    String Data_from;
    int Hr;
    String Note;
    String Start_time;
    long Uid;
    String Url;

    public long getUid() {
        return this.Uid;
    }

    public void setUid(long uid) {
        this.Uid = uid;
    }

    public String getStart_time() {
        return this.Start_time;
    }

    public void setStart_time(String start_time) {
        this.Start_time = start_time;
    }

    public int getHr() {
        return this.Hr;
    }

    public void setHr(int hr) {
        this.Hr = hr;
    }

    public String getNote() {
        return this.Note;
    }

    public void setNote(String note) {
        this.Note = note;
    }

    public String getData_from() {
        return this.Data_from;
    }

    public void setData_from(String data_from) {
        this.Data_from = data_from;
    }

    public String getUrl() {
        return this.Url;
    }

    public void setUrl(String url) {
        this.Url = url;
    }
}
