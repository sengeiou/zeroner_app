package com.iwown.data_link.ecg;

public class EcgDataAiResult {
    String ai_result;
    String data_from;
    String start_time;
    long uid;

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

    public String getStart_time() {
        return this.start_time;
    }

    public void setStart_time(String start_time2) {
        this.start_time = start_time2;
    }

    public String getAi_result() {
        return this.ai_result;
    }

    public void setAi_result(String ai_result2) {
        this.ai_result = ai_result2;
    }
}
