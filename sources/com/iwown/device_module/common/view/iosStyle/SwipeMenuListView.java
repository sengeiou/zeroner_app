package com.iwown.device_module.common.view.iosStyle;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuLayout.OnMenuStateChangeListener;

public class SwipeMenuListView extends ListView {
    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;
    private int MAX_X = 3;
    private int MAX_Y = 5;
    private boolean isFristOpen;
    private Interpolator mCloseInterpolator;
    private float mDownX;
    private float mDownY;
    /* access modifiers changed from: private */
    public SwipeMenuCreator mMenuCreator;
    /* access modifiers changed from: private */
    public OnMenuItemClickListener mOnMenuItemClickListener;
    private Interpolator mOpenInterpolator;
    private int mTouchPosition;
    private int mTouchState;
    /* access modifiers changed from: private */
    public SwipeMenuLayout mTouchView;
    private OpenOrCloseListener onOpenOrCloseListener;

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int i, SwipeMenu swipeMenu, int i2);
    }

    public interface OpenOrCloseListener {
        void isOpen(boolean z);
    }

    public SwipeMenuListView(Context context) {
        super(context);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.MAX_X = dp2px(this.MAX_X);
        this.MAX_Y = dp2px(this.MAX_Y);
        this.mTouchState = 0;
    }

    public void setOnMenuStateChangeListener(OnMenuStateChangeListener listener) {
        SwipeMenuLayout.mOnMenuStateChangeListener = listener;
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(new SwipeMenuAdapter(getContext(), adapter) {
            public void createMenu(SwipeMenu menu) {
                if (SwipeMenuListView.this.mMenuCreator != null) {
                    SwipeMenuListView.this.mMenuCreator.create(menu);
                }
            }

            public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
                if (SwipeMenuListView.this.mOnMenuItemClickListener != null) {
                    SwipeMenuListView.this.mOnMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, index);
                }
                if (SwipeMenuListView.this.mTouchView != null) {
                    SwipeMenuListView.this.mTouchView.smoothCloseMenu();
                }
            }
        });
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        this.mCloseInterpolator = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        this.mOpenInterpolator = interpolator;
    }

    public Interpolator getOpenInterpolator() {
        return this.mOpenInterpolator;
    }

    public Interpolator getCloseInterpolator() {
        return this.mCloseInterpolator;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int actionMasked = MotionEventCompat.getActionMasked(ev);
        switch (ev.getAction()) {
            case 0:
                if (this.mTouchView != null) {
                    this.isFristOpen = this.mTouchView.isOpen();
                }
                if (this.onOpenOrCloseListener != null) {
                    this.onOpenOrCloseListener.isOpen(this.isFristOpen);
                }
                this.mDownX = ev.getX();
                this.mDownY = ev.getY();
                this.mTouchState = 0;
                if (this.mTouchView == null || !this.mTouchView.isOpen()) {
                    this.mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
                    View view = getChildAt(this.mTouchPosition - getFirstVisiblePosition());
                    if (view instanceof SwipeMenuLayout) {
                        this.mTouchView = (SwipeMenuLayout) view;
                    }
                    if (this.mTouchView != null) {
                        this.mTouchView.onSwipe(ev);
                        break;
                    }
                } else {
                    this.mTouchView.smoothCloseMenu();
                    return false;
                }
                break;
            case 1:
                if (this.mTouchState == 1) {
                    if (this.mTouchView != null) {
                        this.mTouchView.onSwipe(ev);
                    }
                    ev.setAction(3);
                    super.onTouchEvent(ev);
                    return true;
                }
                break;
            case 2:
                float dy = Math.abs(ev.getY() - this.mDownY);
                float dx = Math.abs(ev.getX() - this.mDownX);
                if (this.mTouchState != 1) {
                    if (this.mTouchState != 1 && Math.abs(dy) > ((float) this.MAX_Y)) {
                        this.mTouchState = 2;
                    }
                    if (this.mTouchState != 2 && dx > ((float) this.MAX_X)) {
                        this.mTouchState = 1;
                        break;
                    }
                } else {
                    if (this.mTouchView != null) {
                        this.mTouchView.onSwipe(ev);
                    }
                    getSelector().setState(new int[]{0});
                    ev.setAction(3);
                    super.onTouchEvent(ev);
                    return true;
                }
        }
        return super.onTouchEvent(ev);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, getContext().getResources().getDisplayMetrics());
    }

    public void setMenuCreator(SwipeMenuCreator menuCreator) {
        this.mMenuCreator = menuCreator;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnOpenOrCloseListener(OpenOrCloseListener onOpenOrCloseListener2) {
        this.onOpenOrCloseListener = onOpenOrCloseListener2;
    }
}
