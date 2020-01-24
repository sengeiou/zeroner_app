package com.iwown.data_link.ecg;

public class EcgDataNoteNet {
    String data_from;
    int hr;
    String note;
    String start_time;
    long uid;
    String url;

    public String getStart_time() {
        return this.start_time;
    }

    public void setStart_time(String start_time2) {
        this.start_time = start_time2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getHr() {
        return this.hr;
    }

    public void setHr(int hr2) {
        this.hr = hr2;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note2) {
        this.note = note2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }
}
