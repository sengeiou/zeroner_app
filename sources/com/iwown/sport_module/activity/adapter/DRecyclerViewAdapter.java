package com.iwown.sport_module.activity.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

public class DRecyclerViewAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER_VIEW = -2147483647;
    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    public static final String Tag = DRecyclerViewAdapter.class.getName();
    private int index = 0;
    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        public void onChanged() {
            super.onChanged();
            DRecyclerViewAdapter.this.notifyDataSetChanged();
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            DRecyclerViewAdapter.this.notifyItemRangeChanged(DRecyclerViewAdapter.this.getHeadSize() + positionStart, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            DRecyclerViewAdapter.this.notifyItemRangeInserted(DRecyclerViewAdapter.this.getHeadSize() + positionStart, itemCount);
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            DRecyclerViewAdapter.this.notifyItemRangeRemoved(DRecyclerViewAdapter.this.getHeadSize() + positionStart, itemCount);
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = DRecyclerViewAdapter.this.getHeadSize();
            DRecyclerViewAdapter.this.notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }
    };
    private List<View> mFootViews = new ArrayList();
    private List<View> mHeadViews = new ArrayList();
    private Adapter<ViewHolder> mInnerAdapter;
    private List<View> mRandomViews = new ArrayList();
    private SparseArray<Integer> mRandomViews_position = new SparseArray<>();

    public static class DRecyclerViewHolder extends DBaseRecyclerViewHolder<Object> {
        public DRecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(Object data, int position) {
        }
    }

    public DRecyclerViewAdapter(DBaseRecyclerViewAdapter adapter) {
        setAdapter(adapter);
    }

    public void setAdapter(DBaseRecyclerViewAdapter myAdapter) {
        if (myAdapter == null || (myAdapter instanceof Adapter)) {
            if (this.mInnerAdapter != null) {
                notifyItemRangeRemoved(getHeadSize(), this.mInnerAdapter.getItemCount());
                this.mInnerAdapter.unregisterAdapterDataObserver(this.mDataObserver);
            }
            this.mInnerAdapter = myAdapter;
            myAdapter.setDRecyclerViewAdapter(this);
            this.mInnerAdapter.registerAdapterDataObserver(this.mDataObserver);
            notifyItemRangeInserted(getHeadSize(), this.mInnerAdapter.getItemCount());
            return;
        }
        throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
    }

    public void addHeadView(View view) {
        this.mHeadViews.add(view);
    }

    public void addFootView(View view) {
        this.mFootViews.add(view);
        notifyDataSetChanged();
    }

    public void addRandomView(View view, int posistion) {
        this.mRandomViews_position.append(posistion, Integer.valueOf(this.index));
        this.index++;
        this.mRandomViews.add(view);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < Integer.MIN_VALUE + getHeadSize()) {
            return new DRecyclerViewHolder((View) this.mHeadViews.get(viewType - Integer.MIN_VALUE));
        }
        if (viewType < TYPE_FOOTER_VIEW || viewType >= 1073741823) {
            return this.mInnerAdapter.onCreateViewHolder(parent, viewType - 1073741823);
        }
        return new DRecyclerViewHolder((View) this.mFootViews.get(viewType - TYPE_FOOTER_VIEW));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        int headerViewsCountCount = getHeadSize();
        if (position < headerViewsCountCount || position >= this.mInnerAdapter.getItemCount() + headerViewsCountCount) {
            LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
                return;
            }
            return;
        }
        this.mInnerAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
    }

    public int getItemCount() {
        return this.mHeadViews.size() + this.mInnerAdapter.getItemCount() + this.mFootViews.size();
    }

    public int getItemViewType(int position) {
        int innerCount = this.mInnerAdapter.getItemCount();
        int headerViewsCountCount = getHeadSize();
        if (position < headerViewsCountCount) {
            return Integer.MIN_VALUE + position;
        }
        if (headerViewsCountCount > position || position >= headerViewsCountCount + innerCount) {
            return ((TYPE_FOOTER_VIEW + position) - headerViewsCountCount) - innerCount;
        }
        int innerItemViewType = this.mInnerAdapter.getItemViewType(position - headerViewsCountCount);
        if (innerItemViewType < 1073741823) {
            return innerItemViewType + 1073741823;
        }
        throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < Integer.MAX_VALUE / 2");
    }

    public int getFootSize() {
        return this.mFootViews.size();
    }

    public int getHeadSize() {
        return this.mHeadViews.size();
    }

    public boolean isHeader(int position) {
        return position < this.mHeadViews.size();
    }

    public boolean isFooter(int position) {
        return position >= this.mHeadViews.size() + this.mInnerAdapter.getItemCount();
    }

    public boolean isRandom(int position) {
        Log.e("isRandom", position + "  " + this.mRandomViews_position.get(position - this.mHeadViews.size()));
        return this.mRandomViews_position.get(position - this.mHeadViews.size()) != null;
    }
}
