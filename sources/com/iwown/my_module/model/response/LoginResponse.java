package com.iwown.my_module.model.response;

public class LoginResponse {
    private String register_date;
    private int retCode;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public String getRegister_date() {
        return this.register_date;
    }

    public void setRegister_date(String register_date2) {
        this.register_date = register_date2;
    }
}
