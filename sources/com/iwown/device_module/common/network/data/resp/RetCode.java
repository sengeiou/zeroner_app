package com.iwown.device_module.common.network.data.resp;

public class RetCode {
    private String message;
    private int retCode = -1;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }
}
