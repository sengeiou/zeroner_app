package com.iwown.sport_module.view.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SwipeRefreshHeaderLayout extends FrameLayout implements SwipeRefreshTrigger, SwipeTrigger {
    public SwipeRefreshHeaderLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onRefresh() {
    }

    public void onPrepare() {
    }

    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    public void onRelease() {
    }

    public void onComplete() {
    }

    public void onReset() {
    }
}
