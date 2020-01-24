package com.iwown.my_module.healthy.network.response;

public class AllUserInfoData {
    private String bind_email;
    private String bind_phone;
    private String nickname;
    private String portrait_url;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getPortrait_url() {
        return this.portrait_url;
    }

    public void setPortrait_url(String portrait_url2) {
        this.portrait_url = portrait_url2;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname2) {
        this.nickname = nickname2;
    }

    public String getBind_phone() {
        return this.bind_phone;
    }

    public void setBind_phone(String bind_phone2) {
        this.bind_phone = bind_phone2;
    }

    public String getBind_email() {
        return this.bind_email;
    }

    public void setBind_email(String bind_email2) {
        this.bind_email = bind_email2;
    }
}
