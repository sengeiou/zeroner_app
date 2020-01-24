package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_f1_index extends DataSupport {
    private String data_from;
    private String date;
    private int end_seq;
    private int has_file;
    private int has_up;
    private long id;
    private int ok;
    private int start_seq;
    private long time;
    private String type;
    private long uid;

    public long getId() {
        return this.id;
    }

    public void setId(long id2) {
        this.id = id2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public int getOk() {
        return this.ok;
    }

    public void setOk(int ok2) {
        this.ok = ok2;
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

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public int getStart_seq() {
        return this.start_seq;
    }

    public void setStart_seq(int start_seq2) {
        this.start_seq = start_seq2;
    }

    public int getEnd_seq() {
        return this.end_seq;
    }

    public void setEnd_seq(int end_seq2) {
        this.end_seq = end_seq2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public int getHas_file() {
        return this.has_file;
    }

    public void setHas_file(int has_file2) {
        this.has_file = has_file2;
    }

    public int getHas_up() {
        return this.has_up;
    }

    public void setHas_up(int has_up2) {
        this.has_up = has_up2;
    }
}
