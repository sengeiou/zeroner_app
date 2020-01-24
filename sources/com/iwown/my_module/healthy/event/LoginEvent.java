package com.iwown.my_module.healthy.event;

public class LoginEvent {
    private int loginType;
    private String openid;
    private String token;

    public LoginEvent(int loginType2, String token2, String openid2) {
        this.loginType = loginType2;
        this.token = token2;
        this.openid = openid2;
    }

    public int getLoginType() {
        return this.loginType;
    }

    public void setLoginType(int loginType2) {
        this.loginType = loginType2;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid2) {
        this.openid = openid2;
    }
}
