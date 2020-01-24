package com.dinuscxj.refresh;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import com.google.common.primitives.Ints;

public class RecyclerRefreshLayout extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    private static final int DEFAULT_ANIMATE_DURATION = 300;
    private static final int DEFAULT_REFRESH_SIZE_DP = 30;
    private static final int DEFAULT_REFRESH_TARGET_OFFSET_DP = 50;
    private static final int INVALID_INDEX = -1;
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId;
    private int mAnimateToRefreshDuration;
    private Interpolator mAnimateToRefreshInterpolator;
    private final Animation mAnimateToRefreshingAnimation;
    private final Animation mAnimateToStartAnimation;
    private int mAnimateToStartDuration;
    private Interpolator mAnimateToStartInterpolator;
    private float mCurrentTouchOffsetY;
    private boolean mDispatchTargetTouchDown;
    private IDragDistanceConverter mDragDistanceConverter;
    private int mFrom;
    /* access modifiers changed from: private */
    public IRefreshStatus mIRefreshStatus;
    private float mInitialDownY;
    private float mInitialMotionY;
    private float mInitialScrollY;
    /* access modifiers changed from: private */
    public boolean mIsAnimatingToStart;
    private boolean mIsBeingDragged;
    private boolean mIsFitRefresh;
    private boolean mIsRefreshing;
    private boolean mNestedScrollInProgress;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    /* access modifiers changed from: private */
    public boolean mNotifyListener;
    /* access modifiers changed from: private */
    public OnRefreshListener mOnRefreshListener;
    private final int[] mParentOffsetInWindow;
    private final int[] mParentScrollConsumed;
    /* access modifiers changed from: private */
    public float mRefreshInitialOffset;
    /* access modifiers changed from: private */
    public RefreshStyle mRefreshStyle;
    /* access modifiers changed from: private */
    public float mRefreshTargetOffset;
    /* access modifiers changed from: private */
    public View mRefreshView;
    private int mRefreshViewIndex;
    private boolean mRefreshViewMeasured;
    private int mRefreshViewSize;
    private final AnimationListener mRefreshingListener;
    private final AnimationListener mResetListener;
    /* access modifiers changed from: private */
    public View mTarget;
    private float mTargetOrRefreshViewOffsetY;
    private float mTotalUnconsumed;
    private int mTouchSlop;
    private boolean mUsingCustomRefreshInitialOffset;
    private boolean mUsingCustomRefreshTargetOffset;

    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public enum RefreshStyle {
        NORMAL,
        PINNED,
        FLOAT
    }

    /* access modifiers changed from: private */
    public void animateToTargetOffset(float targetEnd, float currentOffset, float interpolatedTime) {
        setTargetOrRefreshViewOffsetY((int) (((float) ((int) (((float) this.mFrom) + ((targetEnd - ((float) this.mFrom)) * interpolatedTime)))) - currentOffset));
    }

    public RecyclerRefreshLayout(Context context) {
        this(context, null);
    }

    public RecyclerRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mParentScrollConsumed = new int[2];
        this.mParentOffsetInWindow = new int[2];
        this.mRefreshViewIndex = -1;
        this.mActivePointerId = -1;
        this.mAnimateToStartDuration = 300;
        this.mAnimateToRefreshDuration = 300;
        this.mUsingCustomRefreshTargetOffset = false;
        this.mUsingCustomRefreshInitialOffset = false;
        this.mRefreshViewMeasured = false;
        this.mRefreshStyle = RefreshStyle.NORMAL;
        this.mAnimateToStartInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        this.mAnimateToRefreshInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        this.mAnimateToRefreshingAnimation = new Animation() {
            /* access modifiers changed from: protected */
            public void applyTransformation(float interpolatedTime, Transformation t) {
                switch (AnonymousClass5.$SwitchMap$com$dinuscxj$refresh$RecyclerRefreshLayout$RefreshStyle[RecyclerRefreshLayout.this.mRefreshStyle.ordinal()]) {
                    case 1:
                        RecyclerRefreshLayout.this.animateToTargetOffset(RecyclerRefreshLayout.this.mRefreshTargetOffset + RecyclerRefreshLayout.this.mRefreshInitialOffset, (float) RecyclerRefreshLayout.this.mRefreshView.getTop(), interpolatedTime);
                        return;
                    default:
                        RecyclerRefreshLayout.this.animateToTargetOffset(RecyclerRefreshLayout.this.mRefreshTargetOffset, (float) RecyclerRefreshLayout.this.mTarget.getTop(), interpolatedTime);
                        return;
                }
            }
        };
        this.mAnimateToStartAnimation = new Animation() {
            /* access modifiers changed from: protected */
            public void applyTransformation(float interpolatedTime, Transformation t) {
                switch (AnonymousClass5.$SwitchMap$com$dinuscxj$refresh$RecyclerRefreshLayout$RefreshStyle[RecyclerRefreshLayout.this.mRefreshStyle.ordinal()]) {
                    case 1:
                        RecyclerRefreshLayout.this.animateToTargetOffset(RecyclerRefreshLayout.this.mRefreshInitialOffset, (float) RecyclerRefreshLayout.this.mRefreshView.getTop(), interpolatedTime);
                        return;
                    default:
                        RecyclerRefreshLayout.this.animateToTargetOffset(0.0f, (float) RecyclerRefreshLayout.this.mTarget.getTop(), interpolatedTime);
                        return;
                }
            }
        };
        this.mRefreshingListener = new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                RecyclerRefreshLayout.this.mIsAnimatingToStart = true;
                RecyclerRefreshLayout.this.mIRefreshStatus.refreshing();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (RecyclerRefreshLayout.this.mNotifyListener && RecyclerRefreshLayout.this.mOnRefreshListener != null) {
                    RecyclerRefreshLayout.this.mOnRefreshListener.onRefresh();
                }
                RecyclerRefreshLayout.this.mIsAnimatingToStart = false;
            }
        };
        this.mResetListener = new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                RecyclerRefreshLayout.this.mIsAnimatingToStart = true;
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                RecyclerRefreshLayout.this.reset();
            }
        };
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        this.mRefreshViewSize = (int) (30.0f * metrics.density);
        this.mRefreshTargetOffset = 50.0f * metrics.density;
        this.mTargetOrRefreshViewOffsetY = 0.0f;
        this.mRefreshInitialOffset = 0.0f;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        initRefreshView();
        initDragDistanceConverter();
        setNestedScrollingEnabled(true);
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        reset();
        clearAnimation();
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: private */
    public void reset() {
        setTargetOrRefreshViewToInitial();
        this.mCurrentTouchOffsetY = 0.0f;
        this.mIRefreshStatus.reset();
        this.mRefreshView.setVisibility(8);
        this.mIsRefreshing = false;
        this.mIsAnimatingToStart = false;
    }

    private void setTargetOrRefreshViewToInitial() {
        switch (this.mRefreshStyle) {
            case FLOAT:
                setTargetOrRefreshViewOffsetY((int) (this.mRefreshInitialOffset - this.mTargetOrRefreshViewOffsetY));
                return;
            default:
                setTargetOrRefreshViewOffsetY((int) (0.0f - this.mTargetOrRefreshViewOffsetY));
                return;
        }
    }

    private void initRefreshView() {
        this.mRefreshView = new RefreshView(getContext());
        this.mRefreshView.setVisibility(8);
        if (this.mRefreshView instanceof IRefreshStatus) {
            this.mIRefreshStatus = (IRefreshStatus) this.mRefreshView;
            addView(this.mRefreshView, new LayoutParams(this.mRefreshViewSize, this.mRefreshViewSize));
            return;
        }
        throw new ClassCastException("the refreshView must implement the interface IRefreshStatus");
    }

    private void initDragDistanceConverter() {
        this.mDragDistanceConverter = new MaterialDragDistanceConverter();
    }

    public void setRefreshView(View refreshView, android.view.ViewGroup.LayoutParams layoutParams) {
        if (this.mRefreshView != refreshView) {
            if (!(this.mRefreshView == null || this.mRefreshView.getParent() == null)) {
                ((ViewGroup) this.mRefreshView.getParent()).removeView(this.mRefreshView);
            }
            if (refreshView instanceof IRefreshStatus) {
                this.mIRefreshStatus = (IRefreshStatus) refreshView;
                refreshView.setVisibility(8);
                addView(refreshView, layoutParams);
                this.mRefreshView = refreshView;
                return;
            }
            throw new ClassCastException("the refreshView must implement the interface IRefreshStatus");
        }
    }

    public void setDragDistanceConverter(@NonNull IDragDistanceConverter dragDistanceConverter) {
        if (dragDistanceConverter == null) {
            throw new NullPointerException("the dragDistanceConverter can't be null");
        }
        this.mDragDistanceConverter = dragDistanceConverter;
    }

    public void setAnimateToStartInterpolator(Interpolator animateToStartInterpolator) {
        this.mAnimateToStartInterpolator = animateToStartInterpolator;
    }

    public void setAnimateToRefreshInterpolator(Interpolator animateToRefreshInterpolator) {
        this.mAnimateToRefreshInterpolator = animateToRefreshInterpolator;
    }

    public void setAnimateToStartDuration(int animateToStartDuration) {
        this.mAnimateToStartDuration = animateToStartDuration;
    }

    public void setAnimateToRefreshDuration(int animateToRefreshDuration) {
        this.mAnimateToRefreshDuration = animateToRefreshDuration;
    }

    public void setRefreshTargetOffset(float refreshTargetOffset) {
        this.mRefreshTargetOffset = refreshTargetOffset;
        this.mUsingCustomRefreshTargetOffset = true;
        requestLayout();
    }

    public void setRefreshInitialOffset(float refreshInitialOffset) {
        this.mRefreshInitialOffset = refreshInitialOffset;
        this.mUsingCustomRefreshInitialOffset = true;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int childCount, int i) {
        switch (this.mRefreshStyle) {
            case FLOAT:
                if (this.mRefreshViewIndex < 0) {
                    return i;
                }
                if (i == childCount - 1) {
                    return this.mRefreshViewIndex;
                }
                if (i >= this.mRefreshViewIndex) {
                    return i + 1;
                }
                return i;
            default:
                if (this.mRefreshViewIndex < 0) {
                    return i;
                }
                if (i == 0) {
                    return this.mRefreshViewIndex;
                }
                if (i <= this.mRefreshViewIndex) {
                    return i - 1;
                }
                return i;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean b) {
        if (VERSION.SDK_INT < 21 && (this.mTarget instanceof AbsListView)) {
            return;
        }
        if (this.mTarget == null || ViewCompat.isNestedScrollingEnabled(this.mTarget)) {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        switch (this.mRefreshStyle) {
            case FLOAT:
                if (!isEnabled() || !canChildScrollUp(this.mTarget) || this.mIsRefreshing || (nestedScrollAxes & 2) == 0) {
                    return false;
                }
                return true;
            default:
                if (!isEnabled() || !canChildScrollUp(this.mTarget) || (nestedScrollAxes & 2) == 0) {
                    return false;
                }
                return true;
        }
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(axes & 2);
        this.mTotalUnconsumed = 0.0f;
        this.mNestedScrollInProgress = true;
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy > 0 && this.mTotalUnconsumed > 0.0f) {
            if (((float) dy) > this.mTotalUnconsumed) {
                consumed[1] = dy - ((int) this.mTotalUnconsumed);
                this.mTotalUnconsumed = 0.0f;
            } else {
                this.mTotalUnconsumed -= (float) dy;
                consumed[1] = dy;
            }
            Log.i("debug", "pre scroll");
            moveSpinner(this.mTotalUnconsumed);
        }
        int[] parentConsumed = this.mParentScrollConsumed;
        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] = consumed[0] + parentConsumed[0];
            consumed[1] = consumed[1] + parentConsumed[1];
        }
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public void onStopNestedScroll(View target) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(target);
        this.mNestedScrollInProgress = false;
        if (this.mTotalUnconsumed > 0.0f) {
            finishSpinner();
            this.mTotalUnconsumed = 0.0f;
        }
        stopNestedScroll();
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, this.mParentOffsetInWindow);
        int dy = dyUnconsumed + this.mParentOffsetInWindow[1];
        if (dy < 0) {
            this.mTotalUnconsumed += (float) Math.abs(dy);
            Log.i("debug", "nested scroll");
            moveSpinner(this.mTotalUnconsumed);
        }
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int axes) {
        return this.mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    public void stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return this.mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (getChildCount() != 0) {
            ensureTarget();
            if (this.mTarget != null) {
                int width = getMeasuredWidth();
                int height = getMeasuredHeight();
                int childTop = getPaddingTop();
                int childLeft = getPaddingLeft();
                this.mTarget.layout(childLeft, childTop, childLeft + ((width - getPaddingLeft()) - getPaddingRight()), childTop + ((height - getPaddingTop()) - getPaddingBottom()));
                this.mRefreshView.layout((width / 2) - (this.mRefreshView.getMeasuredWidth() / 2), (int) this.mRefreshInitialOffset, (width / 2) + (this.mRefreshView.getMeasuredWidth() / 2), (int) (this.mRefreshInitialOffset + ((float) this.mRefreshView.getMeasuredHeight())));
            }
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTarget();
        if (this.mTarget != null) {
            measureTarget();
            measureRefreshView(widthMeasureSpec, heightMeasureSpec);
            if (!this.mRefreshViewMeasured && !this.mUsingCustomRefreshInitialOffset) {
                switch (this.mRefreshStyle) {
                    case FLOAT:
                        float f = (float) (-this.mRefreshView.getMeasuredHeight());
                        this.mRefreshInitialOffset = f;
                        this.mTargetOrRefreshViewOffsetY = f;
                        break;
                    case PINNED:
                        this.mRefreshInitialOffset = 0.0f;
                        this.mTargetOrRefreshViewOffsetY = 0.0f;
                        break;
                    default:
                        this.mTargetOrRefreshViewOffsetY = 0.0f;
                        this.mRefreshInitialOffset = (float) (-this.mRefreshView.getMeasuredHeight());
                        break;
                }
            }
            if (!this.mRefreshViewMeasured && !this.mUsingCustomRefreshTargetOffset && this.mRefreshTargetOffset < ((float) this.mRefreshView.getMeasuredHeight())) {
                this.mRefreshTargetOffset = (float) this.mRefreshView.getMeasuredHeight();
            }
            this.mRefreshViewMeasured = true;
            this.mRefreshViewIndex = -1;
            for (int index = 0; index < getChildCount(); index++) {
                if (getChildAt(index) == this.mRefreshView) {
                    this.mRefreshViewIndex = index;
                    return;
                }
            }
        }
    }

    private void measureTarget() {
        this.mTarget.measure(MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), Ints.MAX_POWER_OF_TWO));
    }

    private void measureRefreshView(int widthMeasureSpec, int heightMeasureSpec) {
        int childWidthMeasureSpec;
        int childHeightMeasureSpec;
        MarginLayoutParams lp = (MarginLayoutParams) this.mRefreshView.getLayoutParams();
        if (lp.width == -1) {
            childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, (((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - lp.leftMargin) - lp.rightMargin), Ints.MAX_POWER_OF_TWO);
        } else {
            childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin, lp.width);
        }
        if (lp.height == -1) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - lp.topMargin) - lp.bottomMargin), Ints.MAX_POWER_OF_TWO);
        } else {
            childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin, lp.height);
        }
        this.mRefreshView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 1:
            case 3:
                onStopNestedScroll(this);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        if (this.mTarget == null) {
            return false;
        }
        switch (this.mRefreshStyle) {
            case FLOAT:
                if (!isEnabled() || canChildScrollUp(this.mTarget) || this.mIsRefreshing || this.mNestedScrollInProgress) {
                    return false;
                }
            default:
                if (!isEnabled()) {
                    return false;
                }
                if (canChildScrollUp(this.mTarget) && !this.mDispatchTargetTouchDown) {
                    return false;
                }
        }
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                this.mIsBeingDragged = false;
                float initialDownY = getMotionEventY(ev, this.mActivePointerId);
                if (initialDownY != -1.0f) {
                    if (this.mAnimateToRefreshingAnimation.hasEnded() && this.mAnimateToStartAnimation.hasEnded()) {
                        this.mIsAnimatingToStart = false;
                    }
                    this.mInitialDownY = initialDownY;
                    this.mInitialScrollY = this.mTargetOrRefreshViewOffsetY;
                    this.mDispatchTargetTouchDown = false;
                    break;
                } else {
                    return false;
                }
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                break;
            case 2:
                if (this.mActivePointerId == -1) {
                    return false;
                }
                float activeMoveY = getMotionEventY(ev, this.mActivePointerId);
                if (activeMoveY != -1.0f) {
                    initDragStatus(activeMoveY);
                    break;
                } else {
                    return false;
                }
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        float overScrollY;
        ensureTarget();
        if (this.mTarget == null) {
            return false;
        }
        switch (this.mRefreshStyle) {
            case FLOAT:
                if (!isEnabled() || canChildScrollUp(this.mTarget) || this.mNestedScrollInProgress) {
                    return false;
                }
            default:
                if (!isEnabled()) {
                    return false;
                }
                if (canChildScrollUp(this.mTarget) && !this.mDispatchTargetTouchDown) {
                    return false;
                }
        }
        if (this.mRefreshStyle == RefreshStyle.FLOAT && (canChildScrollUp(this.mTarget) || this.mNestedScrollInProgress)) {
            return false;
        }
        switch (ev.getAction()) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                this.mIsBeingDragged = false;
                break;
            case 1:
            case 3:
                if (this.mActivePointerId == -1 || getMotionEventY(ev, this.mActivePointerId) == -1.0f) {
                    resetTouchEvent();
                    return false;
                } else if (this.mIsRefreshing || this.mIsAnimatingToStart) {
                    if (this.mDispatchTargetTouchDown) {
                        this.mTarget.dispatchTouchEvent(ev);
                    }
                    resetTouchEvent();
                    return false;
                } else {
                    resetTouchEvent();
                    finishSpinner();
                    return false;
                }
            case 2:
                if (this.mActivePointerId == -1) {
                    return false;
                }
                float activeMoveY = getMotionEventY(ev, this.mActivePointerId);
                if (activeMoveY != -1.0f) {
                    if (this.mIsAnimatingToStart) {
                        overScrollY = (float) getTargetOrRefreshViewTop();
                        this.mInitialMotionY = activeMoveY;
                        this.mInitialScrollY = overScrollY;
                        Log.i("debug", "animatetostart overscrolly " + overScrollY + " -- " + this.mInitialMotionY);
                    } else {
                        overScrollY = (activeMoveY - this.mInitialMotionY) + this.mInitialScrollY;
                        Log.i("debug", "overscrolly " + overScrollY + " --" + this.mInitialMotionY + " -- " + this.mInitialScrollY);
                    }
                    if (!this.mIsRefreshing) {
                        if (!this.mIsBeingDragged) {
                            initDragStatus(activeMoveY);
                            break;
                        } else if (overScrollY > 0.0f) {
                            moveSpinner(overScrollY);
                            break;
                        } else {
                            return false;
                        }
                    } else {
                        if (overScrollY <= 0.0f) {
                            if (this.mDispatchTargetTouchDown) {
                                this.mTarget.dispatchTouchEvent(ev);
                            } else {
                                MotionEvent obtain = MotionEvent.obtain(ev);
                                obtain.setAction(0);
                                this.mDispatchTargetTouchDown = true;
                                this.mTarget.dispatchTouchEvent(obtain);
                            }
                        } else if (overScrollY > 0.0f && overScrollY < this.mRefreshTargetOffset && this.mDispatchTargetTouchDown) {
                            MotionEvent obtain2 = MotionEvent.obtain(ev);
                            obtain2.setAction(3);
                            this.mDispatchTargetTouchDown = false;
                            this.mTarget.dispatchTouchEvent(obtain2);
                        }
                        Log.i("debug", "moveSpinner refreshing -- " + this.mInitialScrollY + " -- " + (activeMoveY - this.mInitialMotionY));
                        moveSpinner(overScrollY);
                        break;
                    }
                } else {
                    return false;
                }
            case 5:
                onNewerPointerDown(ev);
                break;
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        return true;
    }

    private void resetTouchEvent() {
        this.mInitialScrollY = 0.0f;
        this.mIsBeingDragged = false;
        this.mDispatchTargetTouchDown = false;
        this.mActivePointerId = -1;
    }

    public void setRefreshing(boolean refreshing) {
        if (!refreshing || this.mIsRefreshing == refreshing) {
            setRefreshing(refreshing, false);
            return;
        }
        this.mIsRefreshing = refreshing;
        this.mNotifyListener = false;
        animateToRefreshingPosition((int) this.mTargetOrRefreshViewOffsetY, this.mRefreshingListener);
    }

    private void setRefreshing(boolean refreshing, boolean notify) {
        if (this.mIsRefreshing != refreshing) {
            this.mNotifyListener = notify;
            this.mIsRefreshing = refreshing;
            if (refreshing) {
                animateToRefreshingPosition((int) this.mTargetOrRefreshViewOffsetY, this.mRefreshingListener);
            } else {
                animateOffsetToStartPosition((int) this.mTargetOrRefreshViewOffsetY, this.mResetListener);
            }
        }
    }

    private void initDragStatus(float activeMoveY) {
        float diff = activeMoveY - this.mInitialDownY;
        if (this.mIsRefreshing && (diff > ((float) this.mTouchSlop) || this.mTargetOrRefreshViewOffsetY > 0.0f)) {
            this.mIsBeingDragged = true;
            this.mInitialMotionY = this.mInitialDownY + ((float) this.mTouchSlop);
        } else if (!this.mIsBeingDragged && diff > ((float) this.mTouchSlop)) {
            this.mInitialMotionY = this.mInitialDownY + ((float) this.mTouchSlop);
            this.mIsBeingDragged = true;
        }
    }

    private void animateOffsetToStartPosition(int from, AnimationListener listener) {
        if (computeAnimateToStartDuration((float) from) <= 0) {
            listener.onAnimationStart(null);
            listener.onAnimationEnd(null);
            return;
        }
        this.mFrom = from;
        this.mAnimateToStartAnimation.reset();
        this.mAnimateToStartAnimation.setDuration((long) computeAnimateToStartDuration((float) from));
        this.mAnimateToStartAnimation.setInterpolator(this.mAnimateToStartInterpolator);
        if (listener != null) {
            this.mAnimateToStartAnimation.setAnimationListener(listener);
        }
        clearAnimation();
        startAnimation(this.mAnimateToStartAnimation);
    }

    private void animateToRefreshingPosition(int from, AnimationListener listener) {
        if (computeAnimateToRefreshingDuration((float) from) <= 0) {
            listener.onAnimationStart(null);
            listener.onAnimationEnd(null);
            return;
        }
        this.mFrom = from;
        this.mAnimateToRefreshingAnimation.reset();
        this.mAnimateToRefreshingAnimation.setDuration((long) computeAnimateToRefreshingDuration((float) from));
        this.mAnimateToRefreshingAnimation.setInterpolator(this.mAnimateToRefreshInterpolator);
        if (listener != null) {
            this.mAnimateToRefreshingAnimation.setAnimationListener(listener);
        }
        clearAnimation();
        startAnimation(this.mAnimateToRefreshingAnimation);
    }

    private int computeAnimateToRefreshingDuration(float from) {
        Log.i("debug", "from -- refreshing " + from);
        if (from < this.mRefreshInitialOffset) {
            return 0;
        }
        switch (this.mRefreshStyle) {
            case FLOAT:
                return (int) (Math.max(0.0f, Math.min(1.0f, Math.abs((from - this.mRefreshInitialOffset) - this.mRefreshTargetOffset) / this.mRefreshTargetOffset)) * ((float) this.mAnimateToRefreshDuration));
            default:
                return (int) (Math.max(0.0f, Math.min(1.0f, Math.abs(from - this.mRefreshTargetOffset) / this.mRefreshTargetOffset)) * ((float) this.mAnimateToRefreshDuration));
        }
    }

    private int computeAnimateToStartDuration(float from) {
        Log.i("debug", "from -- start " + from);
        if (from < this.mRefreshInitialOffset) {
            return 0;
        }
        switch (this.mRefreshStyle) {
            case FLOAT:
                return (int) (Math.max(0.0f, Math.min(1.0f, Math.abs(from - this.mRefreshInitialOffset) / this.mRefreshTargetOffset)) * ((float) this.mAnimateToStartDuration));
            default:
                return (int) (Math.max(0.0f, Math.min(1.0f, Math.abs(from) / this.mRefreshTargetOffset)) * ((float) this.mAnimateToStartDuration));
        }
    }

    private void moveSpinner(float targetOrRefreshViewOffsetY) {
        float convertScrollOffset;
        float refreshTargetOffset;
        this.mCurrentTouchOffsetY = targetOrRefreshViewOffsetY;
        if (!this.mIsRefreshing) {
            switch (this.mRefreshStyle) {
                case FLOAT:
                    convertScrollOffset = this.mRefreshInitialOffset + this.mDragDistanceConverter.convert(targetOrRefreshViewOffsetY, this.mRefreshTargetOffset);
                    refreshTargetOffset = this.mRefreshTargetOffset;
                    break;
                default:
                    convertScrollOffset = this.mDragDistanceConverter.convert(targetOrRefreshViewOffsetY, this.mRefreshTargetOffset);
                    refreshTargetOffset = this.mRefreshTargetOffset;
                    break;
            }
        } else {
            if (targetOrRefreshViewOffsetY > this.mRefreshTargetOffset) {
                convertScrollOffset = this.mRefreshTargetOffset;
            } else {
                convertScrollOffset = targetOrRefreshViewOffsetY;
            }
            if (convertScrollOffset < 0.0f) {
                convertScrollOffset = 0.0f;
            }
            refreshTargetOffset = this.mRefreshTargetOffset;
        }
        if (!this.mIsRefreshing) {
            if (convertScrollOffset > refreshTargetOffset && !this.mIsFitRefresh) {
                this.mIsFitRefresh = true;
                this.mIRefreshStatus.pullToRefresh();
            } else if (convertScrollOffset <= refreshTargetOffset && this.mIsFitRefresh) {
                this.mIsFitRefresh = false;
                this.mIRefreshStatus.releaseToRefresh();
            }
        }
        Log.i("debug", targetOrRefreshViewOffsetY + " -- " + refreshTargetOffset + " -- " + convertScrollOffset + " -- " + this.mTargetOrRefreshViewOffsetY + " -- " + this.mRefreshTargetOffset);
        setTargetOrRefreshViewOffsetY((int) (convertScrollOffset - this.mTargetOrRefreshViewOffsetY));
    }

    private void finishSpinner() {
        if (!this.mIsRefreshing && !this.mIsAnimatingToStart) {
            if (((float) getTargetOrRefreshViewOffset()) > this.mRefreshTargetOffset) {
                setRefreshing(true, true);
                return;
            }
            this.mIsRefreshing = false;
            animateOffsetToStartPosition((int) this.mTargetOrRefreshViewOffsetY, this.mResetListener);
        }
    }

    private void onNewerPointerDown(MotionEvent ev) {
        this.mActivePointerId = MotionEventCompat.getPointerId(ev, MotionEventCompat.getActionIndex(ev));
        this.mInitialMotionY = getMotionEventY(ev, this.mActivePointerId) - this.mCurrentTouchOffsetY;
        Log.i("debug", " onDown " + this.mInitialMotionY);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.mActivePointerId) {
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex == 0 ? 1 : 0);
        }
        this.mInitialMotionY = getMotionEventY(ev, this.mActivePointerId) - this.mCurrentTouchOffsetY;
        Log.i("debug", " onUp " + this.mInitialMotionY);
    }

    private void setTargetOrRefreshViewOffsetY(int offsetY) {
        switch (this.mRefreshStyle) {
            case FLOAT:
                this.mRefreshView.offsetTopAndBottom(offsetY);
                this.mTargetOrRefreshViewOffsetY = (float) this.mRefreshView.getTop();
                break;
            case PINNED:
                this.mTarget.offsetTopAndBottom(offsetY);
                this.mTargetOrRefreshViewOffsetY = (float) this.mTarget.getTop();
                break;
            default:
                this.mTarget.offsetTopAndBottom(offsetY);
                this.mRefreshView.offsetTopAndBottom(offsetY);
                this.mTargetOrRefreshViewOffsetY = (float) this.mTarget.getTop();
                break;
        }
        Log.i("debug", "current offset" + this.mTargetOrRefreshViewOffsetY);
        switch (this.mRefreshStyle) {
            case FLOAT:
                this.mIRefreshStatus.pullProgress(this.mTargetOrRefreshViewOffsetY, (this.mTargetOrRefreshViewOffsetY - this.mRefreshInitialOffset) / this.mRefreshTargetOffset);
                break;
            default:
                this.mIRefreshStatus.pullProgress(this.mTargetOrRefreshViewOffsetY, this.mTargetOrRefreshViewOffsetY / this.mRefreshTargetOffset);
                break;
        }
        if (this.mRefreshView.getVisibility() != 0) {
            this.mRefreshView.setVisibility(0);
        }
        invalidate();
    }

    private int getTargetOrRefreshViewTop() {
        switch (this.mRefreshStyle) {
            case FLOAT:
                return this.mRefreshView.getTop();
            default:
                return this.mTarget.getTop();
        }
    }

    private int getTargetOrRefreshViewOffset() {
        switch (this.mRefreshStyle) {
            case FLOAT:
                return (int) (((float) this.mRefreshView.getTop()) - this.mRefreshInitialOffset);
            default:
                return this.mTarget.getTop();
        }
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1.0f;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private boolean canChildScrollUp(View mTarget2) {
        if (mTarget2 == null) {
            return false;
        }
        if (VERSION.SDK_INT >= 14 || !(mTarget2 instanceof AbsListView)) {
            if (mTarget2 instanceof ViewGroup) {
                int childCount = ((ViewGroup) mTarget2).getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (canChildScrollUp(((ViewGroup) mTarget2).getChildAt(i))) {
                        return true;
                    }
                }
            }
            return ViewCompat.canScrollVertically(mTarget2, -1);
        }
        AbsListView absListView = (AbsListView) mTarget2;
        if (absListView.getChildCount() <= 0) {
            return false;
        }
        if (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()) {
            return true;
        }
        return false;
    }

    private void ensureTarget() {
        if (!isTargetValid()) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(this.mRefreshView)) {
                    this.mTarget = child;
                    return;
                }
            }
        }
    }

    private boolean isTargetValid() {
        for (int i = 0; i < getChildCount(); i++) {
            if (this.mTarget == getChildAt(i)) {
                return true;
            }
        }
        return false;
    }

    public void setRefreshStyle(@NonNull RefreshStyle refreshStyle) {
        this.mRefreshStyle = refreshStyle;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }
}
