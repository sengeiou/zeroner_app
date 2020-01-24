package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_64_index_table extends DataSupport {
    private String data_from;
    private String data_ymd;
    private String date;
    private int flag;
    private long id;
    private int seq_end;
    private int seq_start;
    private int sync_seq;
    private long uid;
    private long unixTime;

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

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag2) {
        this.flag = flag2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public int getSeq_start() {
        return this.seq_start;
    }

    public void setSeq_start(int seq_start2) {
        this.seq_start = seq_start2;
    }

    public int getSeq_end() {
        return this.seq_end;
    }

    public void setSeq_end(int seq_end2) {
        this.seq_end = seq_end2;
    }

    public String getData_ymd() {
        return this.data_ymd;
    }

    public void setData_ymd(String data_ymd2) {
        this.data_ymd = data_ymd2;
    }

    public int getSync_seq() {
        return this.sync_seq;
    }

    public void setSync_seq(int sync_seq2) {
        this.sync_seq = sync_seq2;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id2) {
        this.id = id2;
    }
}
