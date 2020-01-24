package com.iwown.my_module.healthy.network.request;

public class QQWxSend {
    private String openid;
    private String token;

    public QQWxSend() {
    }

    public QQWxSend(String token2, String openid2) {
        this.token = token2;
        this.openid = openid2;
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
