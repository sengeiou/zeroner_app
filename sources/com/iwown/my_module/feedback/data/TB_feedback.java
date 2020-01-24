package com.iwown.my_module.feedback.data;

import org.litepal.crud.DataSupport;

public class TB_feedback extends DataSupport {
    private long date;
    private int id;
    private String message;
    private int msg_type;
    private long record_id;
    private long uid;
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date2) {
        this.date = date2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public int getMsg_type() {
        return this.msg_type;
    }

    public void setMsg_type(int msg_type2) {
        this.msg_type = msg_type2;
    }

    public long getRecord_id() {
        return this.record_id;
    }

    public void setRecord_id(long record_id2) {
        this.record_id = record_id2;
    }
}
