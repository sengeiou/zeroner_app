package com.iwown.device_module.common.view.iosStyle;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.bugly.beta.tinker.TinkerReport;

public class SwipeMenuLayout extends FrameLayout {
    private static final int CONTENT_VIEW_ID = 1;
    private static final int MENU_VIEW_ID = 2;
    private static final int STATE_CLOSE = 0;
    private static final int STATE_OPEN = 1;
    public static OnMenuStateChangeListener mOnMenuStateChangeListener;
    /* access modifiers changed from: private */
    public int MAX_VELOCITYX;
    /* access modifiers changed from: private */
    public int MIN_FLING;
    /* access modifiers changed from: private */
    public boolean isFling;
    private int mBaseX;
    private Interpolator mCloseInterpolator;
    private ScrollerCompat mCloseScroller;
    private View mContentView;
    private int mDownX;
    private GestureDetectorCompat mGestureDetector;
    private OnGestureListener mGestureListener;
    private SwipeMenuView mMenuView;
    private Interpolator mOpenInterpolator;
    private ScrollerCompat mOpenScroller;
    private int position;
    private int state;

    public interface OnMenuStateChangeListener {
        void onMenuStateChange(boolean z);
    }

    public SwipeMenuLayout(View contentView, SwipeMenuView menuView) {
        this(contentView, menuView, null, null);
    }

    public SwipeMenuLayout(View contentView, SwipeMenuView menuView, Interpolator closeInterpolator, Interpolator openInterpolator) {
        super(contentView.getContext());
        this.state = 0;
        this.MIN_FLING = dp2px(15);
        this.MAX_VELOCITYX = -dp2px(500);
        this.mCloseInterpolator = closeInterpolator;
        this.mOpenInterpolator = openInterpolator;
        this.mContentView = contentView;
        this.mMenuView = menuView;
        this.mMenuView.setLayout(this);
        init();
    }

    private SwipeMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.state = 0;
        this.MIN_FLING = dp2px(15);
        this.MAX_VELOCITYX = -dp2px(500);
    }

    private SwipeMenuLayout(Context context) {
        super(context);
        this.state = 0;
        this.MIN_FLING = dp2px(15);
        this.MAX_VELOCITYX = -dp2px(500);
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position2) {
        this.position = position2;
        this.mMenuView.setPosition(position2);
    }

    private void init() {
        this.mGestureListener = new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                SwipeMenuLayout.this.isFling = false;
                return true;
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() - e2.getX() > ((float) SwipeMenuLayout.this.MIN_FLING) && velocityX < ((float) SwipeMenuLayout.this.MAX_VELOCITYX)) {
                    SwipeMenuLayout.this.isFling = true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        };
        this.mGestureDetector = new GestureDetectorCompat(getContext(), this.mGestureListener);
        if (this.mCloseInterpolator != null) {
            this.mCloseScroller = ScrollerCompat.create(getContext(), this.mCloseInterpolator);
        } else {
            this.mCloseScroller = ScrollerCompat.create(getContext());
        }
        if (this.mOpenInterpolator != null) {
            this.mOpenScroller = ScrollerCompat.create(getContext(), this.mOpenInterpolator);
        } else {
            this.mOpenScroller = ScrollerCompat.create(getContext());
        }
        this.mContentView.setLayoutParams(new LayoutParams(-1, -2));
        if (this.mContentView.getId() < 1) {
            this.mContentView.setId(1);
        }
        this.mMenuView.setId(2);
        this.mMenuView.setVisibility(8);
        this.mMenuView.setLayoutParams(new LayoutParams(-2, -1));
        addView(this.mContentView);
        addView(this.mMenuView);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public boolean onSwipe(MotionEvent event) {
        this.mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                this.mDownX = (int) event.getX();
                this.isFling = false;
                break;
            case 1:
                if (this.isFling || ((float) this.mDownX) - event.getX() > ((float) ((this.mMenuView.getWidth() * 3) / 4))) {
                    smoothOpenMenu();
                    break;
                } else {
                    smoothCloseMenu();
                    return false;
                }
                break;
            case 2:
                int dis = (int) (((float) this.mDownX) - event.getX());
                if (this.state == 1) {
                    dis += this.mMenuView.getWidth();
                }
                swipe(dis);
                break;
        }
        return true;
    }

    public boolean isOpen() {
        return this.state == 1;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void swipe(int dis) {
        if (this.mMenuView.getVisibility() == 8) {
            this.mMenuView.setVisibility(0);
        }
        if (dis > this.mMenuView.getWidth()) {
            dis = this.mMenuView.getWidth();
        }
        if (dis < 0) {
            dis = 0;
        }
        this.mContentView.layout(-dis, this.mContentView.getTop(), this.mContentView.getWidth() - dis, getMeasuredHeight());
        this.mMenuView.layout(this.mContentView.getWidth() - dis, this.mMenuView.getTop(), (this.mContentView.getWidth() + this.mMenuView.getWidth()) - dis, this.mMenuView.getBottom());
    }

    public void computeScroll() {
        if (this.state == 1) {
            if (this.mOpenScroller.computeScrollOffset()) {
                swipe(this.mOpenScroller.getCurrX());
                postInvalidate();
            }
        } else if (this.mCloseScroller.computeScrollOffset()) {
            swipe(this.mBaseX - this.mCloseScroller.getCurrX());
            postInvalidate();
        }
    }

    public void smoothCloseMenu() {
        this.state = 0;
        this.mBaseX = -this.mContentView.getLeft();
        this.mCloseScroller.startScroll(0, 0, this.mBaseX, 0, TinkerReport.KEY_LOADED_PACKAGE_CHECK_SIGNATURE);
        postInvalidate();
        if (mOnMenuStateChangeListener != null) {
            mOnMenuStateChangeListener.onMenuStateChange(false);
        }
    }

    public void smoothOpenMenu() {
        this.state = 1;
        this.mOpenScroller.startScroll(-this.mContentView.getLeft(), 0, this.mMenuView.getWidth(), 0, TinkerReport.KEY_LOADED_PACKAGE_CHECK_SIGNATURE);
        postInvalidate();
        if (mOnMenuStateChangeListener != null) {
            mOnMenuStateChangeListener.onMenuStateChange(true);
        }
    }

    public void closeMenu() {
        if (this.state == 1) {
            this.state = 0;
            swipe(0);
            if (mOnMenuStateChangeListener != null) {
                mOnMenuStateChangeListener.onMenuStateChange(false);
            }
        }
    }

    public void openMenu() {
        if (this.state == 0) {
            this.state = 1;
            swipe(this.mMenuView.getWidth());
            if (mOnMenuStateChangeListener != null) {
                mOnMenuStateChangeListener.onMenuStateChange(true);
            }
        }
    }

    public View getContentView() {
        return this.mContentView;
    }

    public SwipeMenuView getMenuView() {
        return this.mMenuView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, getContext().getResources().getDisplayMetrics());
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mContentView.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        this.mMenuView.layout(getMeasuredWidth(), 0, getMeasuredWidth() + this.mMenuView.getMeasuredWidth(), getMeasuredHeight());
    }
}
