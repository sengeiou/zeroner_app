package com.youzan.sdk.event;

public interface EventAPI {
    public static final String EVENT_AUTHENTICATION = "getUserInfo";
    public static final String EVENT_FILE_CHOOSER = "showFileChooser";
    public static final String EVENT_PAGE_READY = "webReady";
    public static final String EVENT_SHARE = "returnShareData";
    public static final String SIGN_NEED_LOGIN = "{\"need_login\":true}";
}
