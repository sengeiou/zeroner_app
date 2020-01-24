package com.iwown.device_module.common.sql;

import android.support.annotation.NonNull;
import org.litepal.crud.DataSupport;

public class TB_rri_index_table extends DataSupport implements Comparable<TB_rri_index_table> {
    private String dataFrom;
    private String data_ymd;
    private String date;
    private int end_seq;
    private int start_seq;
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

    public String getData_ymd() {
        return this.data_ymd;
    }

    public void setData_ymd(String data_ymd2) {
        this.data_ymd = data_ymd2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public int compareTo(@NonNull TB_rri_index_table tb_rri_index_table) {
        if (this.start_seq > tb_rri_index_table.getStart_seq()) {
            return 1;
        }
        return -1;
    }
}
