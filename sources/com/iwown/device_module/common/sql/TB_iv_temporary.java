package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_iv_temporary extends DataSupport {
    private String data_from;
    private long end_time;
    private int has_hr;
    private long id;
    private int sport_type;
    private long start_time;
    private long uid;

    public long getId() {
        return this.id;
    }

    public void setId(long id2) {
        this.id = id2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time2) {
        this.start_time = start_time2;
    }

    public int getHas_hr() {
        return this.has_hr;
    }

    public void setHas_hr(int has_hr2) {
        this.has_hr = has_hr2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time2) {
        this.end_time = end_time2;
    }
}
