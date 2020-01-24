package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class ZG_BaseInfo extends DataSupport {
    public static final String key_bleSpeed = "key_bleSpeed";
    public static final String key_data_last_day = "key_data_last_day";
    public static final String key_deviceset = "key_deviceset";
    public static final String key_devicetime = "key_devicetime";
    public static final String key_hardinfo = "key_hardinfo";
    public static final String key_last_totaldata = "key_last_totaldata";
    public static final String key_message_notification = "key_message_notification";
    public static final String key_phone_call_time = "key_phone_call_time";
    public static final String key_push_alert_time = "key_push_alert_time";
    public static final String key_sit_long_time = "key_sit_long_time";
    public static final String key_welcome_blood = "key_welcome_blood";
    public static final String key_zg_curr0x29 = "key_zg_curr0x29";
    private String content;
    private String data_form;
    private String key;
    private String uid;

    public static String getKey_hardinfo() {
        return key_hardinfo;
    }

    public String getData_form() {
        return this.data_form;
    }

    public void setData_form(String data_form2) {
        this.data_form = data_form2;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid2) {
        this.uid = uid2;
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

    /* access modifiers changed from: protected */
    public long getBaseObjId() {
        return super.getBaseObjId();
    }

    public long getId() {
        return getBaseObjId();
    }
}
