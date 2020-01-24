package com.chad.library.adapter.base;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.List;

public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    public static final int TYPE_NOT_FOUND = -404;
    private SparseArray<Integer> layouts;

    public BaseMultiItemQuickAdapter(List<T> data) {
        super(data);
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        Object item = this.mData.get(position);
        if (item instanceof MultiItemEntity) {
            return ((MultiItemEntity) item).getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    /* access modifiers changed from: protected */
    public void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(DEFAULT_VIEW_TYPE, layoutResId);
    }

    /* access modifiers changed from: protected */
    public K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return ((Integer) this.layouts.get(viewType, Integer.valueOf(TYPE_NOT_FOUND))).intValue();
    }

    /* access modifiers changed from: protected */
    public void addItemType(int type, @LayoutRes int layoutResId) {
        if (this.layouts == null) {
            this.layouts = new SparseArray<>();
        }
        this.layouts.put(type, Integer.valueOf(layoutResId));
    }
}
