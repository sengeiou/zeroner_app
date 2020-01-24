package com.iwown.data_link.fatigue;

public class FatigueShowData {
    private String data_from;
    private String fatigues;
    private long time;

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public String getFatigues() {
        return this.fatigues;
    }

    public void setFatigues(String fatigues2) {
        this.fatigues = fatigues2;
    }

    public String toString() {
        return "FatigueShowData{time=" + this.time + ", data_from='" + this.data_from + '\'' + ", fatigues='" + this.fatigues + '\'' + '}';
    }
}
