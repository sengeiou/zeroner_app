package com.youzan.sdk.event;

import android.view.View;
import com.youzan.sdk.web.bridge.Event;

public abstract class AbsStateEvent implements Event {
    public abstract void call(View view);

    public String subscribe() {
        return EventAPI.EVENT_PAGE_READY;
    }

    public final void call(View view, String data) {
        call(view);
    }
}
