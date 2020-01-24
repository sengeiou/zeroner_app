package com.iwown.data_link.heart.heart_sport;

public class HeartDownData1 {
    private String data_from;
    private HeartRateDetial detail;
    private long end_time;
    private long start_time;
    private long uid;

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public HeartRateDetial getDetail() {
        return this.detail;
    }

    public void setDetail(HeartRateDetial detail2) {
        this.detail = detail2;
    }

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time2) {
        this.end_time = end_time2;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time2) {
        this.start_time = start_time2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }
}
