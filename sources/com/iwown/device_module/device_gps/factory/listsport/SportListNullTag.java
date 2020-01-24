package com.iwown.device_module.device_gps.factory.listsport;

import com.iwown.data_link.data.CopySportGps;
import java.util.LinkedList;
import java.util.List;

public class SportListNullTag extends SportListAllExecutor {
    public List<CopySportGps> getCopySportGpsList(long uid, long startTime, int count, int sportType) {
        return new LinkedList<>();
    }
}
