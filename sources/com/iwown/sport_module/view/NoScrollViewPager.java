package com.iwown.sport_module.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    private float beforeX;
    private float beforeY;
    private boolean justScrollRight = false;
    private boolean justScrollleft = false;
    private boolean noScroll = true;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll2) {
        this.noScroll = noScroll2;
    }

    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!this.justScrollRight && !this.justScrollleft) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case 0:
                this.beforeX = ev.getX();
                this.beforeY = ev.getY();
                break;
            case 2:
                float motionValue = ev.getX() - this.beforeX;
                float motionValue_Y = ev.getY() - this.beforeY;
                if (this.justScrollRight && motionValue < 0.0f && ((double) Math.abs(motionValue_Y / motionValue)) < 1.8d) {
                    return true;
                }
                if (!this.justScrollleft || motionValue <= 0.0f || ((double) Math.abs(motionValue_Y / motionValue)) >= 1.8d) {
                    this.beforeX = ev.getX();
                    this.beforeY = ev.getY();
                    break;
                } else {
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent arg0) {
        if (this.noScroll) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }

    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (this.noScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    public void openNoScroll() {
        this.noScroll = true;
        this.justScrollleft = false;
        this.justScrollRight = false;
    }

    public void openJustLeftScroll() {
        this.noScroll = false;
        this.justScrollleft = true;
        this.justScrollRight = false;
    }

    public void openJustRightScroll() {
        this.noScroll = false;
        this.justScrollleft = false;
        this.justScrollRight = true;
    }

    public void openNoLimitScroll() {
        this.noScroll = false;
        this.justScrollleft = false;
        this.justScrollRight = false;
    }
}
