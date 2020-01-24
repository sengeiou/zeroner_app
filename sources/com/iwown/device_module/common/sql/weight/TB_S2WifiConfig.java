package com.iwown.device_module.common.sql.weight;

import org.litepal.crud.DataSupport;

public class TB_S2WifiConfig extends DataSupport {
    private String config_wifi_name;
    private String config_wifi_pwd;
    private String mac;
    private long uid;

    public String getConfig_wifi_name() {
        return this.config_wifi_name;
    }

    public void setConfig_wifi_name(String config_wifi_name2) {
        this.config_wifi_name = config_wifi_name2;
    }

    public String getConfig_wifi_pwd() {
        return this.config_wifi_pwd;
    }

    public void setConfig_wifi_pwd(String config_wifi_pwd2) {
        this.config_wifi_pwd = config_wifi_pwd2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac2) {
        this.mac = mac2;
    }
}
