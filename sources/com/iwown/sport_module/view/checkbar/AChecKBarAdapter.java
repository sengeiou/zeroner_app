package com.iwown.sport_module.view.checkbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public abstract class AChecKBarAdapter<T> {
    private static final int INIT_NONE = -1;
    private List<T> data = new ArrayList();
    private int init_checke_pos = -1;
    private int layout_res;
    private Context mContext;
    private LayoutInflater mInflater;

    public abstract void bindCheckRes(View view, T t);

    public AChecKBarAdapter(Context context, int itemLayoutRes, List<T> data2, int initCheckedPos) {
        this.mContext = context;
        this.layout_res = itemLayoutRes;
        this.data = data2;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.init_checke_pos = initCheckedPos;
    }

    public View getItemView() {
        return this.mInflater.inflate(this.layout_res, null);
    }

    public List<T> getData() {
        return this.data;
    }

    public int getInit_checke_pos() {
        return this.init_checke_pos;
    }
}
