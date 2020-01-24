package com.iwown.device_module.common.network.callback;

public interface MyCallback<T> {
    void onFail(Throwable th);

    void onSuccess(T t);
}
