package com.iwown.sport_module.sql;

import org.litepal.crud.DataSupport;

public class TB_DevSupportsByName extends DataSupport {
    private int dev_type;
    private String name_key = "";
    private int supports;

    public String getName_key() {
        return this.name_key;
    }

    public void setName_key(String name_key2) {
        this.name_key = name_key2;
    }

    public int getSupports() {
        return this.supports;
    }

    public void setSupports(int supports2) {
        this.supports = supports2;
    }

    public int getDev_type() {
        return this.dev_type;
    }

    public void setDev_type(int dev_type2) {
        this.dev_type = dev_type2;
    }
}
