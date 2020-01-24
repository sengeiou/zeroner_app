package com.iwown.ble_module.proto.model;

import java.util.Calendar;

public class ProtoBufParent {
    protected int hisDataCase;
    protected int hisDataType;

    public int[] parseTime(long timeStamp, int timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((1000 * timeStamp) - ((long) ((timeZone * 3600) * 1000)));
        return new int[]{calendar.get(1), calendar.get(2) + 1, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13)};
    }
}
