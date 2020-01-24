package com.iwown.data_link.blood;

import java.util.List;

public class BpDownData1 {
    private List<BpDownData> Data;
    private int ReturnCode;

    public int getUid() {
        return this.ReturnCode;
    }

    public void setUid(int uid) {
        this.ReturnCode = uid;
    }

    public int getReturnCode() {
        return this.ReturnCode;
    }

    public void setReturnCode(int returnCode) {
        this.ReturnCode = returnCode;
    }

    public List<BpDownData> getData() {
        return this.Data;
    }

    public void setData(List<BpDownData> data) {
        this.Data = data;
    }

    public String toString() {
        return "BpDownData1{ReturnCode=" + this.ReturnCode + ", Data=" + this.Data + '}';
    }
}
