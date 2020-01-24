package com.iwown.device_module.device_gps.factory.onesport;

import com.iwown.data_link.data.CopySportGps;

public class SportNullTag extends SportAllExecutor {
    public CopySportGps getCopySportGps(long uid, long startTime, String dataFrom, int sportType) {
        return null;
    }
}
