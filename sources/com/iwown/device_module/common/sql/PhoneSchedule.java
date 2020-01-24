package com.iwown.device_module.common.sql;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class PhoneSchedule extends DataSupport {
    @Column(defaultValue = "2")
    private int checked = 2;
    private String message;
    private long time;
    private long uid;

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getChecked() {
        return this.checked;
    }

    public void setChecked(int checked2) {
        this.checked = checked2;
    }
}
