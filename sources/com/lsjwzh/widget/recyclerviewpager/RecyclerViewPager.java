package com.lsjwzh.widget.recyclerviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.SmoothScroller.Action;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPager extends RecyclerView {
    public static final boolean DEBUG = false;
    View mCurView;
    int mFirstTopWhenDragging;
    int mFisrtLeftWhenDragging;
    private float mFlingFactor;
    private boolean mHasCalledOnPageChanged;
    int mMaxLeftWhenDragging;
    int mMaxTopWhenDragging;
    int mMinLeftWhenDragging;
    int mMinTopWhenDragging;
    boolean mNeedAdjust;
    /* access modifiers changed from: private */
    public List<OnPageChangedListener> mOnPageChangedListeners;
    /* access modifiers changed from: private */
    public int mPositionBeforeScroll;
    private int mPositionOnTouchDown;
    private boolean mSinglePageFling;
    /* access modifiers changed from: private */
    public int mSmoothScrollTargetPosition;
    private float mTouchSpan;
    private float mTriggerOffset;
    private RecyclerViewPagerAdapter<?> mViewPagerAdapter;
    private boolean reverseLayout;

    public interface OnPageChangedListener {
        void OnPageChanged(int i, int i2);
    }

    public RecyclerViewPager(Context context) {
        this(context, null);
    }

    public RecyclerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mTriggerOffset = 0.25f;
        this.mFlingFactor = 0.15f;
        this.mSmoothScrollTargetPosition = -1;
        this.mPositionBeforeScroll = -1;
        this.mMaxLeftWhenDragging = Integer.MIN_VALUE;
        this.mMinLeftWhenDragging = Integer.MAX_VALUE;
        this.mMaxTopWhenDragging = Integer.MIN_VALUE;
        this.mMinTopWhenDragging = Integer.MAX_VALUE;
        this.mPositionOnTouchDown = -1;
        this.mHasCalledOnPageChanged = true;
        this.reverseLayout = false;
        initAttrs(context, attrs, defStyle);
        setNestedScrollingEnabled(false);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewPager, defStyle, 0);
        this.mFlingFactor = a.getFloat(R.styleable.RecyclerViewPager_rvp_flingFactor, 0.15f);
        this.mTriggerOffset = a.getFloat(R.styleable.RecyclerViewPager_rvp_triggerOffset, 0.25f);
        this.mSinglePageFling = a.getBoolean(R.styleable.RecyclerViewPager_rvp_singlePageFling, this.mSinglePageFling);
        a.recycle();
    }

    public void setFlingFactor(float flingFactor) {
        this.mFlingFactor = flingFactor;
    }

    public float getFlingFactor() {
        return this.mFlingFactor;
    }

    public void setTriggerOffset(float triggerOffset) {
        this.mTriggerOffset = triggerOffset;
    }

    public float getTriggerOffset() {
        return this.mTriggerOffset;
    }

    public void setSinglePageFling(boolean singlePageFling) {
        this.mSinglePageFling = singlePageFling;
    }

    public boolean isSinglePageFling() {
        return this.mSinglePageFling;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        try {
            Field fLayoutState = state.getClass().getDeclaredField("mLayoutState");
            fLayoutState.setAccessible(true);
            Object layoutState = fLayoutState.get(state);
            Field fAnchorOffset = layoutState.getClass().getDeclaredField("mAnchorOffset");
            Field fAnchorPosition = layoutState.getClass().getDeclaredField("mAnchorPosition");
            fAnchorPosition.setAccessible(true);
            fAnchorOffset.setAccessible(true);
            if (fAnchorOffset.getInt(layoutState) > 0) {
                fAnchorPosition.set(layoutState, Integer.valueOf(fAnchorPosition.getInt(layoutState) - 1));
            } else if (fAnchorOffset.getInt(layoutState) < 0) {
                fAnchorPosition.set(layoutState, Integer.valueOf(fAnchorPosition.getInt(layoutState) + 1));
            }
            fAnchorOffset.setInt(layoutState, 0);
        } catch (Throwable e) {
            ThrowableExtension.printStackTrace(e);
        }
        super.onRestoreInstanceState(state);
    }

    public void setAdapter(Adapter adapter) {
        this.mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
        super.setAdapter(this.mViewPagerAdapter);
    }

    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        this.mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
        super.swapAdapter(this.mViewPagerAdapter, removeAndRecycleExistingViews);
    }

    public Adapter getAdapter() {
        if (this.mViewPagerAdapter != null) {
            return this.mViewPagerAdapter.mAdapter;
        }
        return null;
    }

    public RecyclerViewPagerAdapter getWrapperAdapter() {
        return this.mViewPagerAdapter;
    }

    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof LinearLayoutManager) {
            this.reverseLayout = ((LinearLayoutManager) layout).getReverseLayout();
        }
    }

    public boolean fling(int velocityX, int velocityY) {
        boolean flinging = super.fling((int) (((float) velocityX) * this.mFlingFactor), (int) (((float) velocityY) * this.mFlingFactor));
        if (flinging) {
            if (getLayoutManager().canScrollHorizontally()) {
                adjustPositionX(velocityX);
            } else {
                adjustPositionY(velocityY);
            }
        }
        return flinging;
    }

    public void smoothScrollToPosition(int position) {
        this.mPositionBeforeScroll = getCurrentPosition();
        this.mSmoothScrollTargetPosition = position;
        if (getLayoutManager() == null || !(getLayoutManager() instanceof LinearLayoutManager)) {
            super.smoothScrollToPosition(position);
            return;
        }
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(getContext()) {
            public PointF computeScrollVectorForPosition(int targetPosition) {
                if (getLayoutManager() == null) {
                    return null;
                }
                return ((LinearLayoutManager) getLayoutManager()).computeScrollVectorForPosition(targetPosition);
            }

            /* access modifiers changed from: protected */
            public void onTargetFound(View targetView, State state, Action action) {
                int dx;
                int dy;
                if (getLayoutManager() != null) {
                    int dx2 = calculateDxToMakeVisible(targetView, getHorizontalSnapPreference());
                    int dy2 = calculateDyToMakeVisible(targetView, getVerticalSnapPreference());
                    if (dx2 > 0) {
                        dx = dx2 - getLayoutManager().getLeftDecorationWidth(targetView);
                    } else {
                        dx = dx2 + getLayoutManager().getRightDecorationWidth(targetView);
                    }
                    if (dy2 > 0) {
                        dy = dy2 - getLayoutManager().getTopDecorationHeight(targetView);
                    } else {
                        dy = dy2 + getLayoutManager().getBottomDecorationHeight(targetView);
                    }
                    int time = calculateTimeForDeceleration((int) Math.sqrt((double) ((dx * dx) + (dy * dy))));
                    if (time > 0) {
                        action.update(-dx, -dy, time, this.mDecelerateInterpolator);
                    }
                }
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        if (position != -1) {
            getLayoutManager().startSmoothScroll(linearSmoothScroller);
        }
    }

    public void scrollToPosition(int position) {
        this.mPositionBeforeScroll = getCurrentPosition();
        this.mSmoothScrollTargetPosition = position;
        super.scrollToPosition(position);
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (VERSION.SDK_INT < 16) {
                    RecyclerViewPager.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    RecyclerViewPager.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                if (RecyclerViewPager.this.mSmoothScrollTargetPosition >= 0 && RecyclerViewPager.this.mSmoothScrollTargetPosition < RecyclerViewPager.this.getItemCount() && RecyclerViewPager.this.mOnPageChangedListeners != null) {
                    for (OnPageChangedListener onPageChangedListener : RecyclerViewPager.this.mOnPageChangedListeners) {
                        if (onPageChangedListener != null) {
                            onPageChangedListener.OnPageChanged(RecyclerViewPager.this.mPositionBeforeScroll, RecyclerViewPager.this.getCurrentPosition());
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public int getItemCount() {
        if (this.mViewPagerAdapter == null) {
            return 0;
        }
        return this.mViewPagerAdapter.getItemCount();
    }

    public int getCurrentPosition() {
        int curPosition;
        if (getLayoutManager().canScrollHorizontally()) {
            curPosition = ViewUtils.getCenterXChildPosition(this);
        } else {
            curPosition = ViewUtils.getCenterYChildPosition(this);
        }
        if (curPosition < 0) {
            return this.mSmoothScrollTargetPosition;
        }
        return curPosition;
    }

    /* access modifiers changed from: protected */
    public void adjustPositionX(int velocityX) {
        if (this.reverseLayout) {
            velocityX *= -1;
        }
        if (getChildCount() > 0) {
            int curPosition = ViewUtils.getCenterXChildPosition(this);
            int flingCount = getFlingCount(velocityX, (getWidth() - getPaddingLeft()) - getPaddingRight());
            int targetPosition = curPosition + flingCount;
            if (this.mSinglePageFling) {
                int flingCount2 = Math.max(-1, Math.min(1, flingCount));
                if (flingCount2 == 0) {
                    targetPosition = curPosition;
                } else {
                    targetPosition = this.mPositionOnTouchDown + flingCount2;
                }
            }
            int targetPosition2 = Math.min(Math.max(targetPosition, 0), getItemCount() - 1);
            if (targetPosition2 == curPosition && (!this.mSinglePageFling || this.mPositionOnTouchDown == curPosition)) {
                View centerXChild = ViewUtils.getCenterXChild(this);
                if (centerXChild != null) {
                    if (this.mTouchSpan > ((float) centerXChild.getWidth()) * this.mTriggerOffset * this.mTriggerOffset && targetPosition2 != 0) {
                        targetPosition2 = !this.reverseLayout ? targetPosition2 - 1 : targetPosition2 + 1;
                    } else if (this.mTouchSpan < ((float) centerXChild.getWidth()) * (-this.mTriggerOffset) && targetPosition2 != getItemCount() - 1) {
                        targetPosition2 = !this.reverseLayout ? targetPosition2 + 1 : targetPosition2 - 1;
                    }
                }
            }
            smoothScrollToPosition(safeTargetPosition(targetPosition2, getItemCount()));
        }
    }

    public void addOnPageChangedListener(OnPageChangedListener listener) {
        if (this.mOnPageChangedListeners == null) {
            this.mOnPageChangedListeners = new ArrayList();
        }
        this.mOnPageChangedListeners.add(listener);
    }

    public void removeOnPageChangedListener(OnPageChangedListener listener) {
        if (this.mOnPageChangedListeners != null) {
            this.mOnPageChangedListeners.remove(listener);
        }
    }

    public void clearOnPageChangedListeners() {
        if (this.mOnPageChangedListeners != null) {
            this.mOnPageChangedListeners.clear();
        }
    }

    /* access modifiers changed from: protected */
    public void adjustPositionY(int velocityY) {
        if (this.reverseLayout) {
            velocityY *= -1;
        }
        if (getChildCount() > 0) {
            int curPosition = ViewUtils.getCenterYChildPosition(this);
            int flingCount = getFlingCount(velocityY, (getHeight() - getPaddingTop()) - getPaddingBottom());
            int targetPosition = curPosition + flingCount;
            if (this.mSinglePageFling) {
                int flingCount2 = Math.max(-1, Math.min(1, flingCount));
                if (flingCount2 == 0) {
                    targetPosition = curPosition;
                } else {
                    targetPosition = this.mPositionOnTouchDown + flingCount2;
                }
            }
            int targetPosition2 = Math.min(Math.max(targetPosition, 0), getItemCount() - 1);
            if (targetPosition2 == curPosition && (!this.mSinglePageFling || this.mPositionOnTouchDown == curPosition)) {
                View centerYChild = ViewUtils.getCenterYChild(this);
                if (centerYChild != null) {
                    if (this.mTouchSpan > ((float) centerYChild.getHeight()) * this.mTriggerOffset && targetPosition2 != 0) {
                        targetPosition2 = !this.reverseLayout ? targetPosition2 - 1 : targetPosition2 + 1;
                    } else if (this.mTouchSpan < ((float) centerYChild.getHeight()) * (-this.mTriggerOffset) && targetPosition2 != getItemCount() - 1) {
                        targetPosition2 = !this.reverseLayout ? targetPosition2 + 1 : targetPosition2 - 1;
                    }
                }
            }
            smoothScrollToPosition(safeTargetPosition(targetPosition2, getItemCount()));
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0 && getLayoutManager() != null) {
            this.mPositionOnTouchDown = getLayoutManager().canScrollHorizontally() ? ViewUtils.getCenterXChildPosition(this) : ViewUtils.getCenterYChildPosition(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == 2 && this.mCurView != null) {
            this.mMaxLeftWhenDragging = Math.max(this.mCurView.getLeft(), this.mMaxLeftWhenDragging);
            this.mMaxTopWhenDragging = Math.max(this.mCurView.getTop(), this.mMaxTopWhenDragging);
            this.mMinLeftWhenDragging = Math.min(this.mCurView.getLeft(), this.mMinLeftWhenDragging);
            this.mMinTopWhenDragging = Math.min(this.mCurView.getTop(), this.mMinTopWhenDragging);
        }
        return super.onTouchEvent(e);
    }

    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == 1) {
            this.mNeedAdjust = true;
            this.mCurView = getLayoutManager().canScrollHorizontally() ? ViewUtils.getCenterXChild(this) : ViewUtils.getCenterYChild(this);
            if (this.mCurView != null) {
                if (this.mHasCalledOnPageChanged) {
                    this.mPositionBeforeScroll = getChildLayoutPosition(this.mCurView);
                    this.mHasCalledOnPageChanged = false;
                }
                this.mFisrtLeftWhenDragging = this.mCurView.getLeft();
                this.mFirstTopWhenDragging = this.mCurView.getTop();
            } else {
                this.mPositionBeforeScroll = -1;
            }
            this.mTouchSpan = 0.0f;
        } else if (state == 2) {
            this.mNeedAdjust = false;
            if (this.mCurView == null) {
                this.mTouchSpan = 0.0f;
            } else if (getLayoutManager().canScrollHorizontally()) {
                this.mTouchSpan = (float) (this.mCurView.getLeft() - this.mFisrtLeftWhenDragging);
            } else {
                this.mTouchSpan = (float) (this.mCurView.getTop() - this.mFirstTopWhenDragging);
            }
            this.mCurView = null;
        } else if (state == 0) {
            if (this.mNeedAdjust) {
                int targetPosition = getLayoutManager().canScrollHorizontally() ? ViewUtils.getCenterXChildPosition(this) : ViewUtils.getCenterYChildPosition(this);
                if (this.mCurView != null) {
                    targetPosition = getChildAdapterPosition(this.mCurView);
                    if (getLayoutManager().canScrollHorizontally()) {
                        int spanX = this.mCurView.getLeft() - this.mFisrtLeftWhenDragging;
                        if (((float) spanX) > ((float) this.mCurView.getWidth()) * this.mTriggerOffset && this.mCurView.getLeft() >= this.mMaxLeftWhenDragging) {
                            targetPosition = !this.reverseLayout ? targetPosition - 1 : targetPosition + 1;
                        } else if (((float) spanX) < ((float) this.mCurView.getWidth()) * (-this.mTriggerOffset) && this.mCurView.getLeft() <= this.mMinLeftWhenDragging) {
                            targetPosition = !this.reverseLayout ? targetPosition + 1 : targetPosition - 1;
                        }
                    } else {
                        int spanY = this.mCurView.getTop() - this.mFirstTopWhenDragging;
                        if (((float) spanY) > ((float) this.mCurView.getHeight()) * this.mTriggerOffset && this.mCurView.getTop() >= this.mMaxTopWhenDragging) {
                            targetPosition = !this.reverseLayout ? targetPosition - 1 : targetPosition + 1;
                        } else if (((float) spanY) < ((float) this.mCurView.getHeight()) * (-this.mTriggerOffset) && this.mCurView.getTop() <= this.mMinTopWhenDragging) {
                            targetPosition = !this.reverseLayout ? targetPosition + 1 : targetPosition - 1;
                        }
                    }
                }
                smoothScrollToPosition(safeTargetPosition(targetPosition, getItemCount()));
                this.mCurView = null;
            } else if (this.mSmoothScrollTargetPosition != this.mPositionBeforeScroll) {
                if (this.mOnPageChangedListeners != null) {
                    for (OnPageChangedListener onPageChangedListener : this.mOnPageChangedListeners) {
                        if (onPageChangedListener != null) {
                            onPageChangedListener.OnPageChanged(this.mPositionBeforeScroll, this.mSmoothScrollTargetPosition);
                        }
                    }
                }
                this.mHasCalledOnPageChanged = true;
                this.mPositionBeforeScroll = this.mSmoothScrollTargetPosition;
            }
            this.mMaxLeftWhenDragging = Integer.MIN_VALUE;
            this.mMinLeftWhenDragging = Integer.MAX_VALUE;
            this.mMaxTopWhenDragging = Integer.MIN_VALUE;
            this.mMinTopWhenDragging = Integer.MAX_VALUE;
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public RecyclerViewPagerAdapter ensureRecyclerViewPagerAdapter(Adapter adapter) {
        return adapter instanceof RecyclerViewPagerAdapter ? (RecyclerViewPagerAdapter) adapter : new RecyclerViewPagerAdapter(this, adapter);
    }

    private int getFlingCount(int velocity, int cellSize) {
        if (velocity == 0) {
            return 0;
        }
        int sign = velocity > 0 ? 1 : -1;
        return (int) (((double) sign) * Math.ceil((double) (((((float) (velocity * sign)) * this.mFlingFactor) / ((float) cellSize)) - this.mTriggerOffset)));
    }

    private int safeTargetPosition(int position, int count) {
        if (position < 0) {
            return 0;
        }
        if (position >= count) {
            return count - 1;
        }
        return position;
    }
}
