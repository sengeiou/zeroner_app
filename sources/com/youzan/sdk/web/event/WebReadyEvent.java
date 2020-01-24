package com.youzan.sdk.web.event;

import com.youzan.sdk.event.EventAPI;
import com.youzan.sdk.web.bridge.IBridgeEnv;

@Deprecated
public abstract class WebReadyEvent extends a {
    public abstract void call(IBridgeEnv iBridgeEnv, String str);

    public String subscribe() {
        return EventAPI.EVENT_PAGE_READY;
    }
}
