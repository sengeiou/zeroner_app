package com.iwown.sport_module.sql;

import org.litepal.crud.DataSupport;

public class TB_ad_url extends DataSupport {
    private String adImgUrl;
    private String adOneUrl;
    private long adTime;
    private long start_time;

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time2) {
        this.start_time = start_time2;
    }

    public String getAdOneUrl() {
        return this.adOneUrl;
    }

    public void setAdOneUrl(String adOneUrl2) {
        this.adOneUrl = adOneUrl2;
    }

    public String getAdImgUrl() {
        return this.adImgUrl;
    }

    public void setAdImgUrl(String adImgUrl2) {
        this.adImgUrl = adImgUrl2;
    }

    public long getAdTime() {
        return this.adTime;
    }

    public void setAdTime(long adTime2) {
        this.adTime = adTime2;
    }
}
