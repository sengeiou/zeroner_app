package com.youzan.sdk.event;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.View;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.youzan.sdk.web.bridge.Event;

public abstract class AbsChooserEvent implements Event {
    public abstract void call(View view, Intent intent, int i) throws ActivityNotFoundException;

    public String subscribe() {
        return EventAPI.EVENT_FILE_CHOOSER;
    }

    public final void call(View view, String data) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        try {
            call(view, intent, Integer.valueOf(data).intValue());
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
