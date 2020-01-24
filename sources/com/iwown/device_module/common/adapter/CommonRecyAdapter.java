package com.iwown.device_module.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.iwown.device_module.common.adapter.ComViewHolder.OnItemClickListener;
import java.util.List;

public abstract class CommonRecyAdapter<T> extends Adapter<ViewHolder> {
    public static final int RECYLER_FOOTER_TYPE = 2;
    public static final int RECYLER_HEAD_TYPE = 1;
    public static final int RECYLER_ITEM_TYPE = 0;
    protected int footerlayoutid;
    protected int headerlayoutid;
    protected Context mContext;
    protected List<T> mDataList;
    protected LayoutInflater mInflater;
    protected final int mItemLayoutId;
    private final boolean on;
    private OnItemClickListener onItemClickListener;

    /* access modifiers changed from: protected */
    public abstract ComViewHolder setComViewHolder(View view, int i);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public CommonRecyAdapter(Context context, List<T> dataList, int layoutId) {
        this(context, dataList, layoutId, 0);
    }

    public CommonRecyAdapter(Context context, List<T> dataList, int layoutId, int headerlayoutid2) {
        this(context, dataList, layoutId, headerlayoutid2, 0);
    }

    public CommonRecyAdapter(Context context, List<T> dataList, int layoutId, int headerlayoutid2, int footerlayoutid2) {
        this(context, dataList, layoutId, headerlayoutid2, footerlayoutid2, false);
    }

    public CommonRecyAdapter(Context context, List<T> dataList, int layoutId, int headerlayoutid2, int footerlayoutid2, boolean isneedM) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mItemLayoutId = layoutId;
        this.headerlayoutid = headerlayoutid2;
        this.footerlayoutid = footerlayoutid2;
        this.mInflater = LayoutInflater.from(context);
        this.on = isneedM;
    }

    public int getItemViewType(int position) {
        if (this.headerlayoutid != 0 && position == 0) {
            return 1;
        }
        if (this.footerlayoutid == 0 || position != getItemCount() - 1) {
            return 0;
        }
        return 2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.headerlayoutid != 0 && viewType == 1) {
            return setComViewHolder(this.mInflater.inflate(this.headerlayoutid, parent, false), viewType);
        }
        if (this.footerlayoutid != 0 && viewType == 2) {
            return setComViewHolder(this.mInflater.inflate(this.footerlayoutid, parent, false), viewType);
        }
        ComViewHolder viewHolder = setComViewHolder(this.mInflater.inflate(this.mItemLayoutId, parent, false), viewType);
        viewHolder.onItemClickListener(this.onItemClickListener);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (this.headerlayoutid != 0) {
            if (position == 0) {
                onBindHeader(holder);
            } else if (this.footerlayoutid == 0 || position != getItemCount() - 1) {
                onBindItem(holder, position - 1, this.mDataList.get(position - 1));
            } else {
                onBindFooter(holder);
            }
        } else if (this.footerlayoutid == 0 || position != getItemCount() - 1) {
            onBindItem(holder, position, this.mDataList.get(position));
        } else {
            onBindFooter(holder);
        }
    }

    private void onBindFooter(ViewHolder holder) {
    }

    public void onBindHeader(ViewHolder holder) {
    }

    public void onBindItem(ViewHolder holder, int position, T t) {
    }

    public int getItemCount() {
        int size = this.mDataList.size();
        if (this.headerlayoutid != 0) {
            size++;
        }
        if (this.footerlayoutid != 0) {
            return size + 1;
        }
        return size;
    }
}
