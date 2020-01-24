package com.iwown.lib_common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected int countSum = -1;
    protected Context mContext;
    protected List<T> mDatas;
    protected List<ViewHolder> mHolders = new ArrayList();
    protected LayoutInflater mInflater;
    protected final int mItemLayoutId;

    public abstract void convert(ViewHolder viewHolder, int i, T t);

    public CommonAdapter(Context context, List<T> mDatas2, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mDatas = mDatas2;
        this.mItemLayoutId = itemLayoutId;
    }

    public void refresh(List<T> mDatas2) {
        this.mDatas = mDatas2;
        notifyDataSetChanged();
    }

    public void deleteList(int position) {
        this.mDatas.remove(position);
        notifyDataSetChanged();
    }

    public CommonAdapter setCount(int i) {
        this.countSum = i;
        notifyDataSetChanged();
        return this;
    }

    public int getCount() {
        if (this.countSum == -1) {
            return this.mDatas.size();
        }
        return this.countSum;
    }

    public T getItem(int position) {
        if (this.countSum == -1) {
            return this.mDatas.get(position);
        }
        return this.mDatas.get(this.countSum % this.mDatas.size());
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        if (!this.mHolders.contains(viewHolder)) {
            this.mHolders.add(viewHolder);
        }
        convert(viewHolder, position, getItem(position));
        adjustConvertView(viewHolder.getConvertView());
        return viewHolder.getConvertView();
    }

    public void adjustConvertView(View convertView) {
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(this.mContext, convertView, parent, this.mItemLayoutId, position);
    }

    public List<ViewHolder> getHolders() {
        return this.mHolders;
    }
}
