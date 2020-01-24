package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class DeviceBaseInfo extends DataSupport {
    private String content;
    private String data_form;
    private String key;
    private String uid;

    public String getData_form() {
        return this.data_form;
    }

    public void setData_form(String data_form2) {
        this.data_form = data_form2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content2) {
        this.content = content2;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid2) {
        this.uid = uid2;
    }
}
