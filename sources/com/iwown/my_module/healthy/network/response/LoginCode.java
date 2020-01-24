package com.iwown.my_module.healthy.network.response;

import com.iwown.data_link.base.RetCode;

public class LoginCode extends RetCode {
    private int newbie;
    private String register_date;
    private long uid;

    public int getNewbie() {
        return this.newbie;
    }

    public void setNewbie(int newbie2) {
        this.newbie = newbie2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getRegister_date() {
        return this.register_date;
    }

    public void setRegister_date(String register_date2) {
        this.register_date = register_date2;
    }
}
