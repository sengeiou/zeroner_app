package com.iwown.my_module.healthy.network.request;

public class PhoneSend {
    private String account;
    private String password;
    private int type;

    public PhoneSend(String account2, int type2, String password2) {
        this.account = account2;
        this.type = type2;
        this.password = password2;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account2) {
        this.account = account2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }
}
