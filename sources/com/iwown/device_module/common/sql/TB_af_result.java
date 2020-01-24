package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_af_result extends DataSupport {
    private String Af_times;
    private String Test_times;
    private int af_ai_result;
    private int confidence;
    private String data_From;
    private String record_date;
    private long time;
    private long uid;

    public String getTest_times() {
        return this.Test_times;
    }

    public void setTest_times(String test_times) {
        this.Test_times = test_times;
    }

    public String getAf_times() {
        return this.Af_times;
    }

    public void setAf_times(String af_times) {
        this.Af_times = af_times;
    }

    public String getData_From() {
        return this.data_From;
    }

    public void setData_From(String data_From2) {
        this.data_From = data_From2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getRecord_date() {
        return this.record_date;
    }

    public void setRecord_date(String record_date2) {
        this.record_date = record_date2;
    }

    public int getAf_ai_result() {
        return this.af_ai_result;
    }

    public void setAf_ai_result(int af_ai_result2) {
        this.af_ai_result = af_ai_result2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public int getConfidence() {
        return this.confidence;
    }

    public void setConfidence(int confidence2) {
        this.confidence = confidence2;
    }
}
