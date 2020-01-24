package com.iwown.data_link.ecg;

public class EcgDownLoadNet {
    String Ai_result;
    String Data_from;
    int Hr;
    String Note;
    String Start_time;
    long Uid;
    String Url;

    public String getAi_result() {
        return this.Ai_result;
    }

    public void setAi_result(String ai_result) {
        this.Ai_result = ai_result;
    }

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

    public String getData_from() {
        return this.Data_from;
    }

    public void setData_from(String data_from) {
        this.Data_from = data_from;
    }

    public int getHr() {
        return this.Hr;
    }

    public void setHr(int hr) {
        this.Hr = hr;
    }

    public String getUrl() {
        return this.Url;
    }

    public void setUrl(String url) {
        this.Url = url;
    }

    public String getNote() {
        return this.Note;
    }

    public void setNote(String note) {
        this.Note = note;
    }
}
