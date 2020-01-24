package com.iwown.lib_common.views.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private int layoutId;
    private Context mContext;
    private List<T> mDatas;
    private LayoutInflater mLayoutInflater;

    public abstract void convert(MyViewHolder myViewHolder, T t, int i);

    public MyBaseAdapter(List<T> mDatas2, Context mContext2, int layoutId2) {
        this.mDatas = mDatas2;
        this.mContext = mContext2;
        this.mLayoutInflater = LayoutInflater.from(mContext2);
        this.layoutId = layoutId2;
    }

    public int getCount() {
        return this.mDatas.size();
    }

    public T getItem(int position) {
        return this.mDatas.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = MyViewHolder.get(this.mContext, convertView, parent, this.layoutId, position);
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }
}
