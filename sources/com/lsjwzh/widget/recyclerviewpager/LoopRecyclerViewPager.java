package com.lsjwzh.widget.recyclerviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.util.Log;

public class LoopRecyclerViewPager extends RecyclerViewPager {
    public LoopRecyclerViewPager(Context context) {
        this(context, null);
    }

    public LoopRecyclerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopRecyclerViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        super.scrollToPosition(getMiddlePosition());
    }

    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        super.swapAdapter(adapter, removeAndRecycleExistingViews);
        super.scrollToPosition(getMiddlePosition());
    }

    /* access modifiers changed from: protected */
    @NonNull
    public RecyclerViewPagerAdapter ensureRecyclerViewPagerAdapter(Adapter adapter) {
        return adapter instanceof LoopRecyclerViewPagerAdapter ? (LoopRecyclerViewPagerAdapter) adapter : new LoopRecyclerViewPagerAdapter(this, adapter);
    }

    public void smoothScrollToPosition(int position) {
        int transformedPosition = transformInnerPositionIfNeed(position);
        super.smoothScrollToPosition(transformedPosition);
        Log.e("test", "transformedPosition:" + transformedPosition);
    }

    public void scrollToPosition(int position) {
        super.scrollToPosition(transformInnerPositionIfNeed(position));
    }

    public int getActualCurrentPosition() {
        return transformToActualPosition(getCurrentPosition());
    }

    public int transformToActualPosition(int position) {
        return position % getActualItemCountFromAdapter();
    }

    private int getActualItemCountFromAdapter() {
        return ((LoopRecyclerViewPagerAdapter) getWrapperAdapter()).getActualItemCount();
    }

    private int transformInnerPositionIfNeed(int position) {
        int actualItemCount = getActualItemCountFromAdapter();
        int actualCurrentPosition = getCurrentPosition() % actualItemCount;
        int bakPosition1 = (getCurrentPosition() - actualCurrentPosition) + (position % actualItemCount);
        int bakPosition2 = ((getCurrentPosition() - actualCurrentPosition) - actualItemCount) + (position % actualItemCount);
        int bakPosition3 = (getCurrentPosition() - actualCurrentPosition) + actualItemCount + (position % actualItemCount);
        Log.e("test", bakPosition1 + "/" + bakPosition2 + "/" + bakPosition3 + "/" + getCurrentPosition());
        if (Math.abs(bakPosition1 - getCurrentPosition()) > Math.abs(bakPosition2 - getCurrentPosition())) {
            return Math.abs(bakPosition2 - getCurrentPosition()) > Math.abs(bakPosition3 - getCurrentPosition()) ? bakPosition3 : bakPosition2;
        }
        if (Math.abs(bakPosition1 - getCurrentPosition()) <= Math.abs(bakPosition3 - getCurrentPosition())) {
            return bakPosition1;
        }
        return bakPosition3;
    }

    private int getMiddlePosition() {
        int actualItemCount = getActualItemCountFromAdapter();
        if (actualItemCount <= 0 || 1073741823 % actualItemCount == 0) {
            return 1073741823;
        }
        return 1073741823 - (1073741823 % actualItemCount);
    }
}
