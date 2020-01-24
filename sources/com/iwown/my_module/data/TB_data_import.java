package com.iwown.my_module.data;

import org.litepal.crud.DataSupport;

public class TB_data_import extends DataSupport {
    private int code;
    private int type;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }
}
