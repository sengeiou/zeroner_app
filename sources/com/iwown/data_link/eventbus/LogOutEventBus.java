package com.iwown.data_link.eventbus;

import org.greenrobot.eventbus.EventBus;

public class LogOutEventBus {
    public static void userLogOut() {
        EventBus.getDefault().post(new LogOutEventBus());
    }
}
