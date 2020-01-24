package com.iwown.device_module.common.view.flowlayout;

import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TagAdapter<T> {
    private HashSet<Integer> mCheckedPosList = new HashSet<>();
    private OnDataChangedListener mOnDataChangedListener;
    private List<T> mTagDatas;

    interface OnDataChangedListener {
        void onChanged();
    }

    public abstract View getView(FlowLayout flowLayout, int i, T t);

    public TagAdapter(List<T> datas) {
        this.mTagDatas = datas;
    }

    public TagAdapter(T[] datas) {
        this.mTagDatas = new ArrayList(Arrays.asList(datas));
    }

    /* access modifiers changed from: 0000 */
    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(Integer.valueOf(pos));
        }
        setSelectedList(set);
    }

    public void setSelectedList(Set<Integer> set) {
        this.mCheckedPosList.clear();
        if (set != null) {
            this.mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    /* access modifiers changed from: 0000 */
    public HashSet<Integer> getPreCheckedList() {
        return this.mCheckedPosList;
    }

    public int getCount() {
        if (this.mTagDatas == null) {
            return 0;
        }
        return this.mTagDatas.size();
    }

    public void notifyDataChanged() {
        if (this.mOnDataChangedListener != null) {
            this.mOnDataChangedListener.onChanged();
        }
    }

    public T getItem(int position) {
        return this.mTagDatas.get(position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }
}
