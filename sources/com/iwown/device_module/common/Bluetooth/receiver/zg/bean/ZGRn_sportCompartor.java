package com.iwown.device_module.common.Bluetooth.receiver.zg.bean;

import com.iwown.device_module.common.sql.TB_v3_sport_data;
import java.util.Comparator;

public class ZGRn_sportCompartor implements Comparator<TB_v3_sport_data> {
    public int compare(TB_v3_sport_data o1, TB_v3_sport_data o2) {
        return ((long) o1.getStart_time()) - ((long) o2.getEnd_time()) > 0 ? 1 : -1;
    }
}
