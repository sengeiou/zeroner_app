package com.iwown.sport_module.ui.repository;

import android.content.Context;

public interface DataSource {
    public static final int NetDataMaxTimeMs = 300000;

    public interface DataCallBack<T> {
        void onResult(T t);
    }

    public interface DataCallBack1<T> {
        void onNoData(T t);

        void onResult(T t);
    }

    Context getContext();
}
