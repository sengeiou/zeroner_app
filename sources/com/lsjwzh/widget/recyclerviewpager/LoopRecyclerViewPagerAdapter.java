package com.lsjwzh.widget.recyclerviewpager;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.ViewHolderDelegate;

public class LoopRecyclerViewPagerAdapter<VH extends ViewHolder> extends RecyclerViewPagerAdapter<VH> {
    public LoopRecyclerViewPagerAdapter(RecyclerViewPager viewPager, Adapter<VH> adapter) {
        super(viewPager, adapter);
    }

    public int getActualItemCount() {
        return super.getItemCount();
    }

    public int getActualItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public long getActualItemId(int position) {
        return super.getItemId(position);
    }

    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(getActualPosition(position));
    }

    public long getItemId(int position) {
        return super.getItemId(getActualPosition(position));
    }

    public void onBindViewHolder(VH holder, int position) {
        super.onBindViewHolder(holder, getActualPosition(position));
        ViewHolderDelegate.setPosition(holder, position);
    }

    public int getActualPosition(int position) {
        int actualPosition = position;
        if (position >= getActualItemCount()) {
            return position % getActualItemCount();
        }
        return actualPosition;
    }
}
