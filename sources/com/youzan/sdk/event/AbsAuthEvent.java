package com.youzan.sdk.event;

import android.view.View;
import com.youzan.sdk.web.bridge.Event;

public abstract class AbsAuthEvent implements Event {
    public abstract void call(View view, boolean z);

    public String subscribe() {
        return EventAPI.EVENT_AUTHENTICATION;
    }

    public final void call(View view, String data) {
        call(view, EventAPI.SIGN_NEED_LOGIN.equals(data));
    }
}
