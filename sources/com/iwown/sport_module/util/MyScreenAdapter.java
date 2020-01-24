package com.iwown.sport_module.util;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public abstract class MyScreenAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    public MyScreenAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    public MyScreenAdapter(@Nullable List data) {
        super(data);
    }

    public MyScreenAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void startConvert(K k, T t) {
        convert(k, t);
    }
}
