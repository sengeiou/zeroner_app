package com.iwown.my_module.healthy.network.response;

import com.iwown.data_link.base.RetCode;

public class RegisterCode extends RetCode {
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }
}
