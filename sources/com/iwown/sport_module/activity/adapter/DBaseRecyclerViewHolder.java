package com.iwown.sport_module.activity.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.iwown.sport_module.activity.adapter.DBaseRecyclerViewAdapter.OnClickItemListsner;
import com.socks.library.KLog;

public abstract class DBaseRecyclerViewHolder<T> extends ViewHolder implements OnClickListener {
    public DBaseRecyclerViewAdapter<T> dBaseRecyclerViewAdapter;
    public DRecyclerViewAdapter mDRecyclerViewAdapter;
    OnClickItemListsner onClickItemListsner;

    public abstract void setData(T t, int i);

    public OnClickItemListsner getOnClickItemListsner() {
        return this.onClickItemListsner;
    }

    public DBaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public DBaseRecyclerViewHolder(View itemView, DBaseRecyclerViewAdapter<T> dBaseRecyclerViewAdapter2) {
        super(itemView);
        this.dBaseRecyclerViewAdapter = dBaseRecyclerViewAdapter2;
        this.mDRecyclerViewAdapter = dBaseRecyclerViewAdapter2.getmDRecyclerViewAdapter();
        this.onClickItemListsner = dBaseRecyclerViewAdapter2.getOnClickItemListsner();
    }

    public DBaseRecyclerViewHolder(ViewGroup parent, @LayoutRes int res, DBaseRecyclerViewAdapter<T> dBaseRecyclerViewAdapter2) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
        this.mDRecyclerViewAdapter = dBaseRecyclerViewAdapter2.getmDRecyclerViewAdapter();
        this.dBaseRecyclerViewAdapter = dBaseRecyclerViewAdapter2;
        this.onClickItemListsner = dBaseRecyclerViewAdapter2.getOnClickItemListsner();
        this.itemView.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public <T extends View> T $(@IdRes int id) {
        return this.itemView.findViewById(id);
    }

    public int getAdapterItemPosition() {
        int oldPosition = getAdapterPosition();
        KLog.e("oldPosition " + oldPosition);
        if (this.dBaseRecyclerViewAdapter == null) {
            return oldPosition;
        }
        if (this.dBaseRecyclerViewAdapter.isHeader(oldPosition) || this.dBaseRecyclerViewAdapter.isFooter(oldPosition)) {
            return -1;
        }
        return oldPosition - this.dBaseRecyclerViewAdapter.getHeaderViewsCount();
    }

    public void onClick(View v) {
        if (this.onClickItemListsner != null) {
            this.onClickItemListsner.onClick(getAdapterItemPosition());
        }
    }
}
