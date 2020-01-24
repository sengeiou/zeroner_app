package com.iwown.my_module.model.request;

public class RegisterRequest {
    private String account;
    private String password;
    public short type;

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account2) {
        this.account = account2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public short getType() {
        return this.type;
    }

    public void setType(short type2) {
        this.type = type2;
    }
}
