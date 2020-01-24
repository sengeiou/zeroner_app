package com.chad.library.adapter.base.util;

import android.support.annotation.LayoutRes;
import android.util.SparseIntArray;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import java.util.List;

public abstract class MultiTypeDelegate<T> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    private boolean autoMode;
    private SparseIntArray layouts;
    private boolean selfMode;

    /* access modifiers changed from: protected */
    public abstract int getItemType(T t);

    public MultiTypeDelegate(SparseIntArray layouts2) {
        this.layouts = layouts2;
    }

    public MultiTypeDelegate() {
    }

    public final int getDefItemViewType(List<T> data, int position) {
        T item = data.get(position);
        return item != null ? getItemType(item) : DEFAULT_VIEW_TYPE;
    }

    public final int getLayoutId(int viewType) {
        return this.layouts.get(viewType, BaseMultiItemQuickAdapter.TYPE_NOT_FOUND);
    }

    private void addItemType(int type, @LayoutRes int layoutResId) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(type, layoutResId);
    }

    public MultiTypeDelegate registerItemTypeAutoIncrease(@LayoutRes int... layoutResIds) {
        this.autoMode = true;
        checkMode(this.selfMode);
        for (int i = 0; i < layoutResIds.length; i++) {
            addItemType(i, layoutResIds[i]);
        }
        return this;
    }

    public MultiTypeDelegate registerItemType(int type, @LayoutRes int layoutResId) {
        this.selfMode = true;
        checkMode(this.autoMode);
        addItemType(type, layoutResId);
        return this;
    }

    private void checkMode(boolean mode) {
        if (mode) {
            throw new RuntimeException("Don't mess two register mode");
        }
    }
}
