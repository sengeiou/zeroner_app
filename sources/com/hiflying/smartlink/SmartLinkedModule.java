package com.hiflying.smartlink;

import java.io.Serializable;

public class SmartLinkedModule implements Serializable {
    private static final long serialVersionUID = 833195854008521358L;
    private String id;
    private String ip;
    private String mac;

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac2) {
        this.mac = mac2;
    }

    public String getModuleIP() {
        return this.ip;
    }

    public void setModuleIP(String moduleIP) {
        this.ip = moduleIP;
    }

    public void setMid(String string) {
        this.id = string;
    }

    public String getMid() {
        return this.id;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip2) {
        this.ip = ip2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String toString() {
        return "SmartLinkedModule [mac=" + this.mac + ", ip=" + this.ip + ", id=" + this.id + "]";
    }
}
