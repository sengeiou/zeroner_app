package com.youzan.sdk.web.bridge;

import android.view.View;

public interface Event {
    void call(View view, String str);

    String subscribe();
}
