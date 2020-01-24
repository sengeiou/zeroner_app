package com.iwown.sport_module.net.callback;

public interface MyCallback<T> {
    void onFail(Throwable th);

    void onSuccess(T t);
}
