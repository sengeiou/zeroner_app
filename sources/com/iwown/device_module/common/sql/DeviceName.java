package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class DeviceName extends DataSupport {
    private String alias;
    private int batch_number;
    private String broadcast_name;
    private String model;

    public String getModel() {
        return this.model;
    }

    public void setModel(String model2) {
        this.model = model2;
    }

    public String getBroadcast_name() {
        return this.broadcast_name;
    }

    public void setBroadcast_name(String broadcast_name2) {
        this.broadcast_name = broadcast_name2;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias2) {
        this.alias = alias2;
    }

    public int getBatch_number() {
        return this.batch_number;
    }

    public void setBatch_number(int batch_number2) {
        this.batch_number = batch_number2;
    }
}
