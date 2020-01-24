package com.iwown.my_module.healthy.network.request;

public class PhoneFindPasswordSend {
    private String password;
    private long phone;

    public long getPhone() {
        return this.phone;
    }

    public void setPhone(long phone2) {
        this.phone = phone2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }
}
