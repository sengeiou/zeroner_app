package com.iwown.sport_module.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.socks.library.KLog;

public class Item2TopRecyclerview extends RecyclerView {
    /* access modifiers changed from: private */
    public int mIndex;
    /* access modifiers changed from: private */
    public LayoutManager mLayoutManager;
    /* access modifiers changed from: private */
    public boolean move = false;
    private boolean needNoIntercept = false;

    public void setNeedNoIntercept(boolean needNoIntercept2) {
        this.needNoIntercept = needNoIntercept2;
    }

    public Item2TopRecyclerview(Context context) {
        super(context);
    }

    public Item2TopRecyclerview(Context context, @Nullable AttributeSet attrs) {
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
                if (Item2TopRecyclerview.this.move && (Item2TopRecyclerview.this.mLayoutManager instanceof LinearLayoutManager)) {
                    Item2TopRecyclerview.this.move = false;
                    int n = Item2TopRecyclerview.this.mIndex - ((LinearLayoutManager) Item2TopRecyclerview.this.mLayoutManager).findFirstVisibleItemPosition();
                    if (n >= 0 && n < Item2TopRecyclerview.this.getChildCount()) {
                        Item2TopRecyclerview.this.scrollBy(0, Item2TopRecyclerview.this.getChildAt(n).getTop());
                    }
                }
            }
        });
    }

    public Item2TopRecyclerview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void moveToPosition(int n) {
        this.mLayoutManager = getLayoutManager();
        if (getLayoutManager() instanceof LinearLayoutManager) {
            int firstItem = ((LinearLayoutManager) this.mLayoutManager).findFirstVisibleItemPosition();
            int lastItem = ((LinearLayoutManager) this.mLayoutManager).findLastVisibleItemPosition();
            this.mIndex = n;
            System.out.println(firstItem + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + lastItem);
            if (n <= firstItem) {
                scrollToPosition(n);
            } else if (n <= lastItem) {
                int top = getChildAt(n - firstItem).getTop();
                KLog.e("no2855 top is " + top);
                scrollBy(0, top);
            } else {
                scrollToPosition(n);
                this.move = true;
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (this.needNoIntercept) {
            return false;
        }
        return super.onInterceptTouchEvent(e);
    }

    public boolean isScrolling() {
        return getScrollState() == 0;
    }
}
