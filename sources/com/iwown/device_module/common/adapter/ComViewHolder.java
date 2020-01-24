package com.iwown.device_module.common.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public abstract class ComViewHolder extends ViewHolder implements OnClickListener, OnLongClickListener {
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int i, View view);
    }

    public ComViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        init(itemView);
    }

    public void init(View view) {
    }

    public void onClick(View v) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(getLayoutPosition(), v);
        }
    }

    public void onItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public boolean onLongClick(View v) {
        return false;
    }
}
