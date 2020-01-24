package com.iwown.healthy.network.data;

public class RetCodeResponse {
    private int is_online = -2;
    private String message;
    private int retCode = -1;

    public int getIs_online() {
        return this.is_online;
    }

    public void setIs_online(int is_online2) {
        this.is_online = is_online2;
    }

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
