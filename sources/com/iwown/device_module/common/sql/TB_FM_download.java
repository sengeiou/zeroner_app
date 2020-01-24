package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_FM_download extends DataSupport {
    private String mac;
    private int model;
    private long time;
    private long uid;
    private String url;

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getModel() {
        return this.model;
    }

    public void setModel(int model2) {
        this.model = model2;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac2) {
        this.mac = mac2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }
}
