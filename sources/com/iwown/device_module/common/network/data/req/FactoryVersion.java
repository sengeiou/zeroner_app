package com.iwown.device_module.common.network.data.req;

public class FactoryVersion {
    private String cmd_b;
    private String cmd_c;
    private String mac_address;
    private String name;
    private long uid;

    public String getMac_address() {
        return this.mac_address;
    }

    public void setMac_address(String mac_address2) {
        this.mac_address = mac_address2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getCmd_b() {
        return this.cmd_b;
    }

    public void setCmd_b(String cmd_b2) {
        this.cmd_b = cmd_b2;
    }

    public String getCmd_c() {
        return this.cmd_c;
    }

    public void setCmd_c(String cmd_c2) {
        this.cmd_c = cmd_c2;
    }
}
