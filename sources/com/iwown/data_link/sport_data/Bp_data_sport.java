package com.iwown.data_link.sport_data;

public class Bp_data_sport {
    private long bpTime;
    private String dataFrom;
    private int dbp;
    private int isupload;
    private int sbp;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getDataFrom() {
        return this.dataFrom;
    }

    public void setDataFrom(String dataFrom2) {
        this.dataFrom = dataFrom2;
    }

    public int getSbp() {
        return this.sbp;
    }

    public void setSbp(int sbp2) {
        this.sbp = sbp2;
    }

    public int getDbp() {
        return this.dbp;
    }

    public void setDbp(int dbp2) {
        this.dbp = dbp2;
    }

    public long getBpTime() {
        return this.bpTime;
    }

    public void setBpTime(long bpTime2) {
        this.bpTime = bpTime2;
    }

    public int getIsupload() {
        return this.isupload;
    }

    public void setIsupload(int isupload2) {
        this.isupload = isupload2;
    }
}
