package com.tencent.tinker.loader.hotplug;

public class UnsupportedEnvironmentException extends UnsupportedOperationException {
    public UnsupportedEnvironmentException(String msg) {
        super(msg);
    }

    public UnsupportedEnvironmentException(Throwable thr) {
        super(thr);
    }
}
