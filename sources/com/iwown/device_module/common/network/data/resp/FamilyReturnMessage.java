package com.iwown.device_module.common.network.data.resp;

public class FamilyReturnMessage extends RetCode {
    private long familyUid;

    public long getFamilyUid() {
        return this.familyUid;
    }

    public void setFamilyUid(long familyUid2) {
        this.familyUid = familyUid2;
    }
}
