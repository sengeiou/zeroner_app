package com.hiflying.smartlink;

public interface OnSmartLinkListener {
    void onCompleted();

    void onLinked(SmartLinkedModule smartLinkedModule);

    void onTimeOut();
}
