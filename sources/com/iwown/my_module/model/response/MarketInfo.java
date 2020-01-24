package com.iwown.my_module.model.response;

public class MarketInfo {
    private String ad_msg;
    private String ad_url;
    private int app;
    private String fb_sub;
    private String twt_sub;
    private String ytb_sub;

    public int getApp() {
        return this.app;
    }

    public void setApp(int app2) {
        this.app = app2;
    }

    public String getFb_sub() {
        return this.fb_sub;
    }

    public void setFb_sub(String fb_sub2) {
        this.fb_sub = fb_sub2;
    }

    public String getYtb_sub() {
        return this.ytb_sub;
    }

    public void setYtb_sub(String ytb_sub2) {
        this.ytb_sub = ytb_sub2;
    }

    public String getTwt_sub() {
        return this.twt_sub;
    }

    public void setTwt_sub(String twt_sub2) {
        this.twt_sub = twt_sub2;
    }

    public String getAd_url() {
        return this.ad_url;
    }

    public void setAd_url(String ad_url2) {
        this.ad_url = ad_url2;
    }

    public String getAd_msg() {
        return this.ad_msg;
    }

    public void setAd_msg(String ad_msg2) {
        this.ad_msg = ad_msg2;
    }
}
