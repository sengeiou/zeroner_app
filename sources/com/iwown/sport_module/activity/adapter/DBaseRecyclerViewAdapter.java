package com.iwown.sport_module.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;
import com.socks.library.KLog;
import java.util.List;

public abstract class DBaseRecyclerViewAdapter<T> extends Adapter<DBaseRecyclerViewHolder> {
    private Context mContext;
    private DRecyclerViewAdapter mDRecyclerViewAdapter;
    private List<T> mDatas;
    OnClickItemListsner onClickItemListsner;

    public interface OnClickItemListsner {
        void onClick(int i);
    }

    /* access modifiers changed from: protected */
    public abstract DBaseRecyclerViewHolder onCreateViewHolder1(ViewGroup viewGroup, int i);

    public List<T> getmDatas() {
        return this.mDatas;
    }

    public void setOnClickItemListsner(OnClickItemListsner onClickItemListsner2) {
        this.onClickItemListsner = onClickItemListsner2;
    }

    public OnClickItemListsner getOnClickItemListsner() {
        return this.onClickItemListsner;
    }

    public DRecyclerViewAdapter getmDRecyclerViewAdapter() {
        return this.mDRecyclerViewAdapter;
    }

    public void setDRecyclerViewAdapter(DRecyclerViewAdapter mDRecyclerViewAdapter2) {
        this.mDRecyclerViewAdapter = mDRecyclerViewAdapter2;
    }

    public DBaseRecyclerViewAdapter(List<T> mDatas2, Context mContext2) {
        this.mDatas = mDatas2;
        this.mContext = mContext2;
    }

    public DBaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder1(parent, viewType);
    }

    public void onBindViewHolder(DBaseRecyclerViewHolder holder, int position) {
        holder.setData(this.mDatas.get(position), position);
    }

    public int getItemCount() {
        return this.mDatas.size();
    }

    public boolean isHeader(int position) {
        return getHeaderViewsCount() > 0 && position < getHeaderViewsCount();
    }

    public boolean isFooter(int position) {
        KLog.e(position + "  all  " + (getItemCount() + getHeaderViewsCount()));
        return position >= getItemCount() + getHeaderViewsCount();
    }

    private int getFooterViewsCount() {
        if (this.mDRecyclerViewAdapter == null) {
            return 0;
        }
        return this.mDRecyclerViewAdapter.getFootSize();
    }

    public int getHeaderViewsCount() {
        if (this.mDRecyclerViewAdapter == null) {
            return 0;
        }
        return this.mDRecyclerViewAdapter.getHeadSize();
    }
}
