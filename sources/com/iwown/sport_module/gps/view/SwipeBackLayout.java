package com.iwown.sport_module.gps.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;
import com.iwown.sport_module.R;
import java.util.LinkedList;
import java.util.List;

public class SwipeBackLayout extends FrameLayout {
    private static final String TAG = SwipeBackLayout.class.getSimpleName();
    private int downX;
    private int downY;
    private boolean isFinish;
    private boolean isSilding;
    private Activity mActivity;
    private View mContentView;
    private Scroller mScroller;
    private Drawable mShadowDrawable;
    private int mTouchSlop;
    private List<ViewPager> mViewPagers;
    private int tempX;
    private int viewWidth;

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mViewPagers = new LinkedList();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScroller = new Scroller(context);
        this.mShadowDrawable = getResources().getDrawable(R.mipmap.shadow_left);
    }

    public void attachToActivity(Activity activity) {
        this.mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{16842836});
        int resourceId = a.getResourceId(0, 0);
        a.recycle();
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(0);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    private void setContentView(View decorChild) {
        this.mContentView = (View) decorChild.getParent();
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ViewPager mViewPager = getTouchViewPager(this.mViewPagers, ev);
        Log.i(TAG, "mViewPager = " + mViewPager);
        if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case 0:
                int rawX = (int) ev.getRawX();
                this.tempX = rawX;
                this.downX = rawX;
                this.downY = (int) ev.getRawY();
                break;
            case 2:
                if (((int) ev.getRawX()) - this.downX > this.mTouchSlop && Math.abs(((int) ev.getRawY()) - this.downY) < this.mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                this.isSilding = false;
                if (this.mContentView.getScrollX() > (-this.viewWidth) / 2) {
                    scrollOrigin();
                    this.isFinish = false;
                    break;
                } else {
                    this.isFinish = true;
                    scrollRight();
                    break;
                }
            case 2:
                int moveX = (int) event.getRawX();
                int deltaX = this.tempX - moveX;
                this.tempX = moveX;
                if (moveX - this.downX > this.mTouchSlop && Math.abs(((int) event.getRawY()) - this.downY) < this.mTouchSlop) {
                    this.isSilding = true;
                }
                if (moveX - this.downX >= 0 && this.isSilding) {
                    this.mContentView.scrollBy(deltaX, 0);
                    break;
                }
        }
        return true;
    }

    private void getAlLViewPager(List<ViewPager> mViewPagers2, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewPager) {
                mViewPagers2.add((ViewPager) child);
            } else if (child instanceof ViewGroup) {
                getAlLViewPager(mViewPagers2, (ViewGroup) child);
            }
        }
    }

    private ViewPager getTouchViewPager(List<ViewPager> mViewPagers2, MotionEvent ev) {
        if (mViewPagers2 == null || mViewPagers2.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ViewPager v : mViewPagers2) {
            v.getHitRect(mRect);
            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.viewWidth = getWidth();
            getAlLViewPager(this.mViewPagers, this);
            Log.i(TAG, "ViewPager size = " + this.mViewPagers.size());
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mShadowDrawable != null && this.mContentView != null) {
            int left = this.mContentView.getLeft() - this.mShadowDrawable.getIntrinsicWidth();
            int right = left + this.mShadowDrawable.getIntrinsicWidth();
            this.mShadowDrawable.setBounds(left, this.mContentView.getTop(), right, this.mContentView.getBottom());
            this.mShadowDrawable.draw(canvas);
        }
    }

    private void scrollRight() {
        int delta = this.viewWidth + this.mContentView.getScrollX();
        this.mScroller.startScroll(this.mContentView.getScrollX(), 0, (-delta) + 1, 0, Math.abs(delta));
        postInvalidate();
    }

    private void scrollOrigin() {
        int delta = this.mContentView.getScrollX();
        this.mScroller.startScroll(this.mContentView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            this.mContentView.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            postInvalidate();
            if (this.mScroller.isFinished() && this.isFinish) {
                this.mActivity.finish();
            }
        }
    }
}
