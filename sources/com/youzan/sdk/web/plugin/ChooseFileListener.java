package com.youzan.sdk.web.plugin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.Keep;

@Keep
public interface ChooseFileListener {
    void onChooseFile(Intent intent, int i) throws ActivityNotFoundException;
}
