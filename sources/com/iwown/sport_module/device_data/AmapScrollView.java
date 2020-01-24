package com.iwown.sport_module.device_data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import com.socks.library.KLog;

public class AmapScrollView extends ScrollView {
    private OnScrollListener listener;
    private int mScrollY = 0;
    private int mapHeight = 0;
    private boolean rcCanScroll;

    public interface OnScrollListener {
        void onScroll(int i);
    }

    public void setOnScrollListener(OnScrollListener listener2) {
        this.listener = listener2;
    }

    public AmapScrollView(Context context) {
        super(context);
    }

    public AmapScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AmapScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        this.mScrollY = t;
        if (this.listener != null) {
            this.listener.onScroll(t);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent e) throws IllegalArgumentException {
        KLog.e("refreshMap: event :" + e.getY() + " -- " + this.mScrollY + " -- " + this.rcCanScroll + " mapheight: " + this.mapHeight);
        if (this.rcCanScroll || e.getActionMasked() > 1) {
            return false;
        }
        if (this.mScrollY <= this.mapHeight && this.mScrollY != 0) {
            return true;
        }
        if (this.mScrollY != 0 || e.getY() < ((float) this.mapHeight)) {
            return false;
        }
        return true;
    }

    public int getmScrollY() {
        return this.mScrollY;
    }

    public void setMapHeight(int mapHeight2) {
        this.mapHeight = mapHeight2;
    }

    public void setRcCanScroll(boolean canScroll) {
        this.rcCanScroll = canScroll;
    }

    /* access modifiers changed from: protected */
    public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
