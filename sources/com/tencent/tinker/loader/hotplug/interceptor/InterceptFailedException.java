package com.tencent.tinker.loader.hotplug.interceptor;

public class InterceptFailedException extends Exception {
    public InterceptFailedException(Throwable thr) {
        super(thr);
    }
}
