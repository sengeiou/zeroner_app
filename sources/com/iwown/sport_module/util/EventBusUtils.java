package com.iwown.sport_module.util;

import com.iwown.sport_module.pojo.AllDataUploadEventBus;
import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
    public static void sendAllDataUploadEventBus(AllDataUploadEventBus allDataUploadEventBus) {
        EventBus.getDefault().post(allDataUploadEventBus);
    }
}
