package com.iwown.data_link.fatigue;

import java.util.List;

public class FatigueSend {
    private List<FatigueNet> dailyData;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public List<FatigueNet> getDailyData() {
        return this.dailyData;
    }

    public void setDailyData(List<FatigueNet> dailyData2) {
        this.dailyData = dailyData2;
    }

    public String toString() {
        return "FatigueSend{uid=" + this.uid + ", dailyData=" + this.dailyData + '}';
    }
}
