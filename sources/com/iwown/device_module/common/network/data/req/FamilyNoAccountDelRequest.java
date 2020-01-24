package com.iwown.device_module.common.network.data.req;

import java.io.Serializable;

public class FamilyNoAccountDelRequest implements Serializable {
    private long familyUid;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getFamilyUid() {
        return this.familyUid;
    }

    public void setFamilyUid(long familyUid2) {
        this.familyUid = familyUid2;
    }
}
