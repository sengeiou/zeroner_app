package com.iwown.data_link.blood;

import com.iwown.data_link.sport_data.ReturnCode;

public class bpPreDown1 extends ReturnCode {
    private bpPreDown2 Data;
    private int ReturnCode;

    public int getUid() {
        return this.ReturnCode;
    }

    public void setUid(int uid) {
        this.ReturnCode = uid;
    }

    public String toString() {
        return "BpDownData1{ReturnCode=" + this.ReturnCode + ", Data=" + this.Data + '}';
    }

    public void setData(bpPreDown2 data) {
        this.Data = data;
    }

    public bpPreDown2 getData() {
        return this.Data;
    }
}
