package com.iwown.sport_module.device_data;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AmapRecyclerview extends RecyclerView {
    private int mIndex;
    private LayoutManager mLayoutManager;
    private boolean needNoIntercept = false;

    public void setNeedNoIntercept(boolean needNoIntercept2) {
        this.needNoIntercept = needNoIntercept2;
    }

    public AmapRecyclerview(Context context) {
        super(context);
    }

    public AmapRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initEvent();
    }

    private void initEvent() {
        addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public AmapRecyclerview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }

    public boolean isScrolling() {
        return getScrollState() == 0;
    }
}
