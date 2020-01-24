package com.iwown.data_link.blood;

import java.util.List;

public class bpCoverageDown1 {
    private List<bpCoverageDown2> BPDataIndex;
    private long Uid;

    public long getUid() {
        return this.Uid;
    }

    public void setUid(long uid) {
        this.Uid = uid;
    }

    public List<bpCoverageDown2> getBPDataIndex() {
        return this.BPDataIndex;
    }

    public void setBPDataIndex(List<bpCoverageDown2> BPDataIndex2) {
        this.BPDataIndex = BPDataIndex2;
    }
}
