package com.iwown.device_module.common.sql.heart;

import org.litepal.crud.DataSupport;

public class TB_HeartDataStatus extends DataSupport {
    private String data_from;
    private String date;
    private long uid;
    private long unix_time;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getUnix_time() {
        return this.unix_time;
    }

    public void setUnix_time(long unix_time2) {
        this.unix_time = unix_time2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }
}
