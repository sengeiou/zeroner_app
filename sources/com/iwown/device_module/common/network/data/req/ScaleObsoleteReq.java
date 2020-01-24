package com.iwown.device_module.common.network.data.req;

public class ScaleObsoleteReq {
    private long uid;
    private long[] weightIdList;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long[] getWeightid() {
        return this.weightIdList;
    }

    public void setWeightid(long[] weightid) {
        this.weightIdList = weightid;
    }
}
