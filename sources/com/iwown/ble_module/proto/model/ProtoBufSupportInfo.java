package com.iwown.ble_module.proto.model;

public class ProtoBufSupportInfo {
    private boolean support_ecg;
    private boolean support_gnss;
    private boolean support_health;
    private boolean support_ppg;
    private boolean support_rri;

    public boolean isSupport_health() {
        return this.support_health;
    }

    public void setSupport_health(boolean support_health2) {
        this.support_health = support_health2;
    }

    public boolean isSupport_gnss() {
        return this.support_gnss;
    }

    public void setSupport_gnss(boolean support_gnss2) {
        this.support_gnss = support_gnss2;
    }

    public boolean isSupport_ecg() {
        return this.support_ecg;
    }

    public void setSupport_ecg(boolean support_ecg2) {
        this.support_ecg = support_ecg2;
    }

    public boolean isSupport_ppg() {
        return this.support_ppg;
    }

    public void setSupport_ppg(boolean support_ppg2) {
        this.support_ppg = support_ppg2;
    }

    public boolean isSupport_rri() {
        return this.support_rri;
    }

    public void setSupport_rri(boolean support_rri2) {
        this.support_rri = support_rri2;
    }
}
