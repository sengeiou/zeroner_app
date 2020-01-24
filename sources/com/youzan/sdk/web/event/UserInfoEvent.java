package com.youzan.sdk.web.event;

import com.youzan.sdk.event.EventAPI;
import com.youzan.sdk.web.bridge.IBridgeEnv;

@Deprecated
public abstract class UserInfoEvent extends a {
    public abstract void call(IBridgeEnv iBridgeEnv);

    public String subscribe() {
        return EventAPI.EVENT_AUTHENTICATION;
    }

    public final void call(IBridgeEnv env, String data) {
        call(env);
    }
}
