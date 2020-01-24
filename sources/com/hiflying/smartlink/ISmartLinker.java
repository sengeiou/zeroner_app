package com.hiflying.smartlink;

import android.content.Context;

public interface ISmartLinker {
    public static final int DEFAULT_TIMEOUT_PERIOD = 60000;
    public static final int V3 = 3;
    public static final int V7 = 7;

    boolean isSmartLinking();

    void setOnSmartLinkListener(OnSmartLinkListener onSmartLinkListener);

    void setTimeoutPeriod(int i);

    void start(Context context, String str, String... strArr) throws Exception;

    void stop();
}
