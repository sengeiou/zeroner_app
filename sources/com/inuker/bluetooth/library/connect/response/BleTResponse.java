package com.inuker.bluetooth.library.connect.response;

public interface BleTResponse<T> {
    void onResponse(int i, T t);
}
