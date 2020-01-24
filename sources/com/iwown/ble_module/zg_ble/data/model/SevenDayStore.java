package com.iwown.ble_module.zg_ble.data.model;

import java.util.ArrayList;
import java.util.List;

public class SevenDayStore {
    public List<EveryDayInfo> storeDateObject = new ArrayList();
    public int totalDays;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("total days:%d ", new Object[]{Integer.valueOf(this.totalDays)}));
        for (int i = 0; i < this.totalDays; i++) {
            sb.append(((EveryDayInfo) this.storeDateObject.get(i)).toString());
        }
        return sb.toString();
    }
}
