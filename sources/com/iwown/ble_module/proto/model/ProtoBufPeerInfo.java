package com.iwown.ble_module.proto.model;

public class ProtoBufPeerInfo {
    private boolean hash_bpcali_conf;
    private boolean support_date_time;
    private boolean support_gnss_conf;
    private boolean support_goal_conf;
    private boolean support_hr_alarm_conf;
    private boolean support_peer_status;
    private boolean support_peer_type;
    private boolean support_user_conf;

    public boolean isSupport_peer_type() {
        return this.support_peer_type;
    }

    public void setSupport_peer_type(boolean support_peer_type2) {
        this.support_peer_type = support_peer_type2;
    }

    public boolean isSupport_peer_status() {
        return this.support_peer_status;
    }

    public void setSupport_peer_status(boolean support_peer_status2) {
        this.support_peer_status = support_peer_status2;
    }

    public boolean isSupport_date_time() {
        return this.support_date_time;
    }

    public void setSupport_date_time(boolean support_date_time2) {
        this.support_date_time = support_date_time2;
    }

    public boolean isSupport_gnss_conf() {
        return this.support_gnss_conf;
    }

    public void setSupport_gnss_conf(boolean support_gnss_conf2) {
        this.support_gnss_conf = support_gnss_conf2;
    }

    public boolean isSupport_hr_alarm_conf() {
        return this.support_hr_alarm_conf;
    }

    public void setSupport_hr_alarm_conf(boolean support_hr_alarm_conf2) {
        this.support_hr_alarm_conf = support_hr_alarm_conf2;
    }

    public boolean isSupport_user_conf() {
        return this.support_user_conf;
    }

    public void setSupport_user_conf(boolean support_user_conf2) {
        this.support_user_conf = support_user_conf2;
    }

    public boolean isSupport_goal_conf() {
        return this.support_goal_conf;
    }

    public void setSupport_goal_conf(boolean support_goal_conf2) {
        this.support_goal_conf = support_goal_conf2;
    }

    public boolean isHash_bpcali_conf() {
        return this.hash_bpcali_conf;
    }

    public void setHash_bpcali_conf(boolean hash_bpcali_conf2) {
        this.hash_bpcali_conf = hash_bpcali_conf2;
    }
}
