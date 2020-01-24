package com.iwown.lib_common.views.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyViewHolder {
    private View mCurrentView;
    private int mPosition;
    private SparseArray<View> mViews = new SparseArray<>();

    public MyViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mCurrentView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mCurrentView.setTag(this);
    }

    public static MyViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new MyViewHolder(context, parent, layoutId, position);
        }
        MyViewHolder holder = (MyViewHolder) convertView.getTag();
        holder.mPosition = position;
        return holder;
    }

    public <T extends View> T getView(int viewId) {
        View view = (View) this.mViews.get(viewId);
        if (view != null) {
            return view;
        }
        View view2 = this.mCurrentView.findViewById(viewId);
        this.mViews.put(viewId, view2);
        return view2;
    }

    public View getConvertView() {
        return this.mCurrentView;
    }
}
