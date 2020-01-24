package com.iwown.my_module.healthy.network.request;

public class WxBindSend {
    private String openid;
    private String token;
    private long uid;

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid2) {
        this.openid = openid2;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }
}
