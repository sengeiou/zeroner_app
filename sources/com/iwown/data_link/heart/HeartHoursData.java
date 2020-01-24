package com.iwown.data_link.heart;

import com.iwown.lib_common.date.DateUtil;
import java.util.ArrayList;
import java.util.List;

public class HeartHoursData {
    private String data_from;
    private String date;
    private List<Integer> detail = new ArrayList();
    private long record_date;
    private long uid;

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

    public List<Integer> getDetail() {
        return this.detail;
    }

    public void setDetail(List<Integer> detail2) {
        this.detail = detail2;
    }

    public long getRecord_date() {
        return this.record_date;
    }

    public void setRecord_date(long record_date2) {
        this.record_date = record_date2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String toString() {
        return "HeartHoursData{uid=" + this.uid + ", record_date=" + new DateUtil(this.record_date, true).getY_M_D() + ", data_from='" + this.data_from + '\'' + ", detail=" + this.detail + '}';
    }
}
