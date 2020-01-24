package com.iwown.data_link.af;

public class AfResultBean {
    private int af;
    private int confidence;
    private int retCode;

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public int getAf() {
        return this.af;
    }

    public void setAf(int af2) {
        this.af = af2;
    }

    public int getConfidence() {
        return this.confidence;
    }

    public void setConfidence(int confidence2) {
        this.confidence = confidence2;
    }

    public String toString() {
        return "AfResultBean{retCode=" + this.retCode + ", af=" + this.af + ", confidence=" + this.confidence + '}';
    }
}
