package com.iwown.my_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.Scroller;
import com.iwown.my_module.R;
import com.iwown.my_module.widget.TosAdapterView.AdapterContextMenuInfo;

public class TosGallery extends TosAbsSpinner implements OnGestureListener, OnDoubleTapListener {
    public static final int HORIZONTAL = 1;
    private static final int SCROLL_TO_FLING_UNCERTAINTY_TIMEOUT = 250;
    public static final String TAG = TosGallery.class.getName();
    public static final int VERTICAL = 2;
    private static final boolean localLOGV = false;
    /* access modifiers changed from: private */
    public int mAnimationDuration;
    private AdapterContextMenuInfo mContextMenuInfo;
    private Runnable mDisableSuppressSelectionChangedRunnable;
    /* access modifiers changed from: private */
    public int mDownTouchPosition;
    private View mDownTouchView;
    private int mFirstChildOffset;
    private FlingRunnable mFlingRunnable;
    private GestureDetector mGestureDetector;
    private int mGravity;
    private boolean mIsDisableScroll;
    private boolean mIsFirstScroll;
    private boolean mIsScrollCycle;
    private boolean mIsScrollCycleTemp;
    private boolean mIsSlotCenter;
    private int mLeftMost;
    private OnEndFlingListener mOnEndFlingListener;
    private int mOrientation;
    private boolean mReceivedInvokeKeyDown;
    private int mRightMost;
    private int mScrollBarBottomMargin;
    private int mScrollBarSize;
    /* access modifiers changed from: private */
    public boolean mScrolling;
    private View mSelectedChild;
    private boolean mShouldCallbackDuringFling;
    private boolean mShouldCallbackOnUnselectedItemClick;
    /* access modifiers changed from: private */
    public boolean mShouldStopFling;
    private int mSpacing;
    /* access modifiers changed from: private */
    public boolean mSuppressSelectionChanged;
    private float mUnselectedAlpha;
    private float mVelocityRatio;

    private class FlingRunnable implements Runnable {
        private int mLastFlingX;
        private int mLastFlingY;
        /* access modifiers changed from: private */
        public Scroller mScroller;

        public FlingRunnable() {
            this.mScroller = new Scroller(TosGallery.this.getContext());
        }

        private void startCommon() {
            TosGallery.this.removeCallbacks(this);
        }

        public void startUsingVelocity(int initialVelocity) {
            if (initialVelocity != 0) {
                startCommon();
                if (TosGallery.this.isOrientationVertical()) {
                    int initialY = initialVelocity < 0 ? Integer.MAX_VALUE : 0;
                    this.mLastFlingY = initialY;
                    this.mScroller.fling(0, initialY, 0, initialVelocity, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
                    TosGallery.this.post(this);
                    return;
                }
                int initialX = initialVelocity < 0 ? Integer.MAX_VALUE : 0;
                this.mLastFlingX = initialX;
                this.mScroller.fling(initialX, 0, initialVelocity, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
                TosGallery.this.post(this);
            }
        }

        public void startUsingDistance(int distance) {
            if (distance != 0) {
                if (TosGallery.this.isOrientationVertical()) {
                    startCommon();
                    TosGallery.this.mScrolling = true;
                    this.mLastFlingY = 0;
                    this.mScroller.startScroll(0, 0, 0, -distance, TosGallery.this.mAnimationDuration);
                    TosGallery.this.post(this);
                    return;
                }
                startCommon();
                TosGallery.this.mScrolling = true;
                this.mLastFlingX = 0;
                this.mScroller.startScroll(0, 0, -distance, 0, TosGallery.this.mAnimationDuration);
                TosGallery.this.post(this);
            }
        }

        public void stop(boolean scrollIntoSlots) {
            TosGallery.this.removeCallbacks(this);
            endFling(scrollIntoSlots);
        }

        /* access modifiers changed from: private */
        public void endFling(boolean scrollIntoSlots) {
            TosGallery.this.mScrolling = false;
            this.mScroller.forceFinished(true);
            if (scrollIntoSlots) {
                TosGallery.this.scrollIntoSlots();
            }
            TosGallery.this.onEndFling();
        }

        public void run() {
            int delta;
            if (TosGallery.this.isOrientationVertical()) {
                runVertical();
            } else if (TosGallery.this.mItemCount == 0) {
                endFling(true);
            } else {
                TosGallery.this.mShouldStopFling = false;
                Scroller scroller = this.mScroller;
                boolean more = scroller.computeScrollOffset();
                int x = scroller.getCurrX();
                int delta2 = this.mLastFlingX - x;
                if (delta2 > 0) {
                    TosGallery.this.mDownTouchPosition = TosGallery.this.mFirstPosition;
                    delta = Math.min(((TosGallery.this.getWidth() - TosGallery.this.getPaddingLeft()) - TosGallery.this.getPaddingRight()) - 1, delta2);
                } else {
                    TosGallery.this.mDownTouchPosition = TosGallery.this.mFirstPosition + (TosGallery.this.getChildCount() - 1);
                    delta = Math.max(-(((TosGallery.this.getWidth() - TosGallery.this.getPaddingRight()) - TosGallery.this.getPaddingLeft()) - 1), delta2);
                }
                TosGallery.this.trackMotionScroll(delta);
                if (!more || TosGallery.this.mShouldStopFling) {
                    endFling(true);
                    return;
                }
                this.mLastFlingX = x;
                TosGallery.this.post(this);
            }
        }

        public void runVertical() {
            int delta;
            if (TosGallery.this.mItemCount == 0) {
                endFling(true);
                return;
            }
            TosGallery.this.mShouldStopFling = false;
            Scroller scroller = this.mScroller;
            boolean more = scroller.computeScrollOffset();
            int y = scroller.getCurrY();
            int delta2 = this.mLastFlingY - y;
            if (delta2 > 0) {
                TosGallery.this.mDownTouchPosition = TosGallery.this.mFirstPosition;
                delta = Math.min(((TosGallery.this.getHeight() - TosGallery.this.getPaddingTop()) - TosGallery.this.getPaddingBottom()) - 1, delta2);
            } else {
                TosGallery.this.mDownTouchPosition = TosGallery.this.mFirstPosition + (TosGallery.this.getChildCount() - 1);
                delta = Math.max(-(((TosGallery.this.getHeight() - TosGallery.this.getPaddingBottom()) - TosGallery.this.getPaddingTop()) - 1), delta2);
            }
            TosGallery.this.trackMotionScrollVertical(delta);
            if (!more || TosGallery.this.mShouldStopFling) {
                endFling(true);
                return;
            }
            this.mLastFlingY = y;
            TosGallery.this.post(this);
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public interface OnEndFlingListener {
        void onEndFling(TosGallery tosGallery);
    }

    public TosGallery(Context context) {
        this(context, null);
    }

    public TosGallery(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.galleryStyle);
    }

    public TosGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSpacing = 0;
        this.mAnimationDuration = 400;
        this.mFlingRunnable = new FlingRunnable();
        this.mDisableSuppressSelectionChangedRunnable = new Runnable() {
            public void run() {
                TosGallery.this.mSuppressSelectionChanged = false;
                TosGallery.this.selectionChanged();
            }
        };
        this.mShouldCallbackDuringFling = true;
        this.mShouldCallbackOnUnselectedItemClick = true;
        this.mIsDisableScroll = false;
        this.mScrolling = false;
        this.mFirstChildOffset = 0;
        this.mScrollBarBottomMargin = 0;
        this.mScrollBarSize = 5;
        this.mVelocityRatio = 1.0f;
        this.mIsScrollCycle = false;
        this.mIsScrollCycleTemp = true;
        this.mIsSlotCenter = false;
        this.mOrientation = 1;
        this.mOnEndFlingListener = null;
        this.mGestureDetector = new GestureDetector(context, this);
        this.mGestureDetector.setIsLongpressEnabled(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Gallery, defStyle, 0);
        int index = a.getInt(R.styleable.Gallery_gravity, -1);
        if (index >= 0) {
            setGravity(index);
        }
        int animationDuration = a.getInt(R.styleable.Gallery_animationDuration, -1);
        if (animationDuration > 0) {
            setAnimationDuration(animationDuration);
        }
        setSpacing(a.getDimensionPixelOffset(R.styleable.Gallery_spacing, 0));
        setUnselectedAlpha(a.getFloat(R.styleable.Gallery_unselectedAlpha, 0.5f));
        a.recycle();
        setChildrenDrawingOrderEnabled(true);
        setStaticTransformationsEnabled(true);
        this.mScrollBarSize = ViewConfiguration.get(context).getScaledScrollBarSize();
        if (isOrientationVertical()) {
            this.mGravity = 1;
        } else {
            this.mGravity = 16;
        }
    }

    public void setCallbackDuringFling(boolean shouldCallback) {
        this.mShouldCallbackDuringFling = shouldCallback;
    }

    public void setCallbackOnUnselectedItemClick(boolean shouldCallback) {
        this.mShouldCallbackOnUnselectedItemClick = shouldCallback;
    }

    public void setAnimationDuration(int animationDurationMillis) {
        this.mAnimationDuration = animationDurationMillis;
    }

    public void setSpacing(int spacing) {
        this.mSpacing = spacing;
    }

    public void setUnselectedAlpha(float unselectedAlpha) {
        this.mUnselectedAlpha = unselectedAlpha;
    }

    /* access modifiers changed from: protected */
    public boolean getChildStaticTransformation(View child, Transformation t) {
        t.clear();
        t.setAlpha(child == this.mSelectedChild ? 1.0f : this.mUnselectedAlpha);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDrawHorizontalScrollBar(Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        int t2 = t - this.mScrollBarBottomMargin;
        int b2 = b - this.mScrollBarBottomMargin;
        scrollBar.setBounds(l, b2 - this.mScrollBarSize, r, b2);
        scrollBar.draw(canvas);
    }

    public void invalidate(int l, int t, int r, int b) {
        super.invalidate(l, t - (this.mScrollBarSize + this.mScrollBarBottomMargin), r, b);
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollExtent() {
        boolean isFirst;
        boolean isLast = true;
        int count = getChildCount();
        if (count <= 0) {
            return 0;
        }
        int extent = count * 100;
        View view = getChildAt(0);
        int left = view.getLeft();
        int width = view.getWidth();
        if (width > 0) {
            if (this.mFirstPosition == 0) {
                isFirst = true;
            } else {
                isFirst = false;
            }
            if (!isFirst || left <= 0) {
                extent += (left * 100) / width;
            }
        }
        View view2 = getChildAt(count - 1);
        int right = view2.getRight();
        int width2 = view2.getWidth();
        if (width2 <= 0) {
            return extent;
        }
        if (this.mFirstPosition + count != this.mItemCount) {
            isLast = false;
        }
        if (!isLast || right >= getWidth()) {
            return extent - (((right - getWidth()) * 100) / width2);
        }
        return extent;
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollOffset() {
        if (this.mFirstPosition >= 0 && getChildCount() > 0) {
            View view = getChildAt(0);
            int left = view.getLeft();
            int width = view.getWidth();
            if (width > 0) {
                return Math.max(((this.mFirstPosition / 1) * 100) - ((left * 100) / width), 0);
            }
        }
        return this.mSelectedPosition;
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollRange() {
        return Math.max((((this.mItemCount + 1) - 1) / 1) * 100, 0);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mInLayout = true;
        layout(0, false);
        this.mInLayout = false;
    }

    /* access modifiers changed from: 0000 */
    public int getChildHeight(View child) {
        return child.getMeasuredHeight();
    }

    /* access modifiers changed from: 0000 */
    public void trackMotionScroll(int deltaX) {
        boolean toLeft;
        if (getChildCount() != 0) {
            if (deltaX < 0) {
                toLeft = true;
            } else {
                toLeft = false;
            }
            if (isSlotInCenter()) {
                if ((!isScrollCycle() || getChildCount() >= this.mItemCount) && getLimitedMotionScrollAmount(toLeft, deltaX) != deltaX) {
                    this.mFlingRunnable.endFling(false);
                    onFinishedMovement();
                }
                offsetChildrenLeftAndRight(deltaX);
                detachOffScreenChildren(toLeft);
                if (toLeft) {
                    fillToGalleryRight();
                } else {
                    fillToGalleryLeft();
                }
                this.mRecycler.clear();
                setSelectionToCenterChild();
                invalidate();
                return;
            }
            if (toLeft) {
                View child = getChildAt(getChildCount() - 1);
                if (child != null && ((float) child.getRight()) < getStopFlingPosition()) {
                    deltaX = 0;
                    if (this.mFlingRunnable != null) {
                        this.mFlingRunnable.stop(false);
                    }
                }
            } else {
                View child2 = getChildAt(0);
                if (child2 != null && ((float) child2.getLeft()) > ((float) getWidth()) - getStopFlingPosition()) {
                    deltaX = 0;
                    if (this.mFlingRunnable != null) {
                        this.mFlingRunnable.stop(false);
                    }
                }
            }
            offsetChildrenLeftAndRight(deltaX);
            detachOffScreenChildren(toLeft);
            if (toLeft) {
                fillToGalleryRight();
            } else {
                fillToGalleryLeft();
            }
            this.mRecycler.clear();
            setSelectionToCenterChild();
            awakenScrollBars();
            invalidate();
        }
    }

    /* access modifiers changed from: 0000 */
    public void trackMotionScrollVertical(int deltaY) {
        boolean toTop;
        if (getChildCount() != 0) {
            if (deltaY < 0) {
                toTop = true;
            } else {
                toTop = false;
            }
            if (isSlotInCenter()) {
                if ((!isScrollCycle() || getChildCount() >= this.mItemCount) && getLimitedMotionScrollAmount(toTop, deltaY) != deltaY) {
                    this.mFlingRunnable.endFling(false);
                    onFinishedMovement();
                }
                offsetChildrenTopAndBottom(deltaY);
                detachOffScreenChildrenVertical(toTop);
                if (toTop) {
                    fillToGalleryBottom();
                } else {
                    fillToGalleryTop();
                }
                this.mRecycler.clear();
                setSelectionToCenterChildVertical();
                invalidate();
                return;
            }
            if (toTop) {
                View child = getChildAt(getChildCount() - 1);
                if (child != null && ((float) child.getRight()) < getStopFlingPosition()) {
                    deltaY = 0;
                    if (this.mFlingRunnable != null) {
                        this.mFlingRunnable.stop(false);
                    }
                }
            } else {
                View child2 = getChildAt(0);
                if (child2 != null && ((float) child2.getLeft()) > ((float) getWidth()) - getStopFlingPosition()) {
                    deltaY = 0;
                    if (this.mFlingRunnable != null) {
                        this.mFlingRunnable.stop(false);
                    }
                }
            }
            offsetChildrenTopAndBottom(deltaY);
            detachOffScreenChildrenVertical(toTop);
            if (toTop) {
                fillToGalleryBottom();
            } else {
                fillToGalleryTop();
            }
            this.mRecycler.clear();
            setSelectionToCenterChild();
            awakenScrollBars();
            invalidate();
        }
    }

    /* access modifiers changed from: 0000 */
    public int getLimitedMotionScrollAmount(boolean motionToLeft, int deltaX) {
        int extremeItemPosition;
        if (motionToLeft) {
            extremeItemPosition = this.mItemCount - 1;
        } else {
            extremeItemPosition = 0;
        }
        View extremeChild = getChildAt(extremeItemPosition - this.mFirstPosition);
        if (extremeChild == null) {
            return deltaX;
        }
        int extremeChildCenter = getCenterOfView(extremeChild);
        int galleryCenter = getCenterOfGallery();
        if (motionToLeft) {
            if (extremeChildCenter <= galleryCenter) {
                return 0;
            }
        } else if (extremeChildCenter >= galleryCenter) {
            return 0;
        }
        int centerDifference = galleryCenter - extremeChildCenter;
        if (motionToLeft) {
            return Math.max(centerDifference, deltaX);
        }
        return Math.min(centerDifference, deltaX);
    }

    private void offsetChildrenLeftAndRight(int offset) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).offsetLeftAndRight(offset);
        }
    }

    private void offsetChildrenTopAndBottom(int offset) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).offsetTopAndBottom(offset);
        }
    }

    /* access modifiers changed from: protected */
    public int getCenterOfGallery() {
        if (isOrientationVertical()) {
            return (((getHeight() - getPaddingTop()) - getPaddingBottom()) / 2) + getPaddingTop();
        }
        return (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
    }

    private float getStopFlingPosition() {
        if (isOrientationVertical()) {
            return (((float) ((getHeight() - getPaddingTop()) - getPaddingBottom())) * onStopFlingPosRatio()) + ((float) getPaddingTop());
        }
        return (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * onStopFlingPosRatio()) + ((float) getPaddingLeft());
    }

    /* access modifiers changed from: protected */
    public float onStopFlingPosRatio() {
        return 0.0f;
    }

    private int getCenterOfView(View view) {
        if (isOrientationVertical()) {
            return view.getTop() + (view.getHeight() / 2);
        }
        return view.getLeft() + (view.getWidth() / 2);
    }

    private void detachOffScreenChildren(boolean toLeft) {
        int numChildren = getChildCount();
        int firstPosition = this.mFirstPosition;
        int start = 0;
        int count = 0;
        if (toLeft) {
            int galleryLeft = getPaddingLeft();
            for (int i = 0; i < numChildren; i++) {
                View child = getChildAt(i);
                if (child.getRight() >= galleryLeft) {
                    break;
                }
                count++;
                this.mRecycler.put(firstPosition + i, child);
            }
            if (count == numChildren) {
                count--;
            }
        } else {
            int galleryRight = getWidth() - getPaddingRight();
            for (int i2 = numChildren - 1; i2 >= 0; i2--) {
                View child2 = getChildAt(i2);
                if (child2.getLeft() <= galleryRight) {
                    break;
                }
                start = i2;
                count++;
                this.mRecycler.put(firstPosition + i2, child2);
            }
            if (start == 0) {
                start++;
            }
        }
        detachViewsFromParent(start, count);
        if (toLeft) {
            this.mFirstPosition += count;
            if (isScrollCycle()) {
                this.mFirstPosition %= this.mItemCount;
            }
        }
    }

    private void detachOffScreenChildrenVertical(boolean toTop) {
        int numChildren = getChildCount();
        int firstPosition = this.mFirstPosition;
        int start = 0;
        int count = 0;
        if (toTop) {
            int galleryTop = getPaddingTop();
            for (int i = 0; i < numChildren; i++) {
                View child = getChildAt(i);
                if (child.getBottom() >= galleryTop) {
                    break;
                }
                count++;
                this.mRecycler.put(firstPosition + i, child);
            }
            if (count == numChildren) {
                count--;
            }
        } else {
            int galleryBottom = getHeight() - getPaddingBottom();
            for (int i2 = numChildren - 1; i2 >= 0; i2--) {
                View child2 = getChildAt(i2);
                if (child2.getTop() <= galleryBottom) {
                    break;
                }
                start = i2;
                count++;
                this.mRecycler.put(firstPosition + i2, child2);
            }
            if (start == 0) {
                start++;
            }
        }
        detachViewsFromParent(start, count);
        if (toTop) {
            this.mFirstPosition += count;
            if (isScrollCycle()) {
                this.mFirstPosition %= this.mItemCount;
            }
        }
    }

    public void scrollIntoSlots() {
        if (isOrientationVertical()) {
            scrollIntoSlotsVertical();
        } else if (isSlotInCenter()) {
            if (getChildCount() != 0 && this.mSelectedChild != null) {
                int scrollAmount = getCenterOfGallery() - getCenterOfView(this.mSelectedChild);
                if (scrollAmount != 0) {
                    this.mFlingRunnable.startUsingDistance(scrollAmount);
                } else {
                    onFinishedMovement();
                }
            }
        } else if (getChildCount() != 0) {
            int scrollAmount2 = 0;
            if (this.mFirstPosition == 0) {
                View child = getChildAt(0);
                if (child.getLeft() >= 0) {
                    scrollAmount2 = getPaddingLeft() - child.getLeft();
                } else {
                    View lastChild = getChildAt(getChildCount() - 1);
                    if (lastChild.getRight() - child.getLeft() < getRight() - getPaddingRight()) {
                        scrollAmount2 = getPaddingLeft() - this.mFirstChildOffset;
                    } else if (lastChild.getRight() < getRight() - getPaddingRight()) {
                        scrollAmount2 = (getWidth() - getPaddingRight()) - lastChild.getRight();
                    }
                }
            } else if (this.mFirstPosition + getChildCount() == this.mItemCount) {
                View child2 = getChildAt(getChildCount() - 1);
                if (child2.getRight() < getRight() - getPaddingRight()) {
                    scrollAmount2 = (getWidth() - getPaddingRight()) - child2.getRight();
                }
            }
            if (scrollAmount2 != 0) {
                this.mFlingRunnable.startUsingDistance(scrollAmount2);
            } else {
                onFinishedMovement();
            }
        }
    }

    private void scrollIntoSlotsVertical() {
        if (isSlotInCenter()) {
            if (getChildCount() != 0 && this.mSelectedChild != null) {
                int scrollAmount = getCenterOfGallery() - getCenterOfView(this.mSelectedChild);
                if (scrollAmount != 0) {
                    this.mFlingRunnable.startUsingDistance(scrollAmount);
                } else {
                    onFinishedMovement();
                }
            }
        } else if (getChildCount() != 0) {
            int scrollAmount2 = 0;
            if (this.mFirstPosition == 0) {
                View child = getChildAt(0);
                if (child.getTop() >= 0) {
                    scrollAmount2 = getPaddingTop() - child.getTop();
                } else {
                    View lastChild = getChildAt(getChildCount() - 1);
                    if (lastChild.getBottom() - child.getTop() < getBottom() - getPaddingBottom()) {
                        scrollAmount2 = getPaddingLeft() - this.mFirstChildOffset;
                    } else if (lastChild.getBottom() < getBottom() - getPaddingBottom()) {
                        scrollAmount2 = (getHeight() - getPaddingBottom()) - lastChild.getBottom();
                    }
                }
            } else if (this.mFirstPosition + getChildCount() == this.mItemCount) {
                View child2 = getChildAt(getChildCount() - 1);
                if (child2.getBottom() < getBottom() - getPaddingBottom()) {
                    scrollAmount2 = (getHeight() - getPaddingBottom()) - child2.getBottom();
                }
            }
            if (scrollAmount2 != 0) {
                this.mFlingRunnable.startUsingDistance(scrollAmount2);
            } else {
                onFinishedMovement();
            }
        }
    }

    private void onFinishedMovement() {
        if (this.mSuppressSelectionChanged) {
            this.mSuppressSelectionChanged = false;
            super.selectionChanged();
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void selectionChanged() {
        if (!this.mSuppressSelectionChanged) {
            super.selectionChanged();
        }
    }

    private void setSelectionToCenterChild() {
        View selView = this.mSelectedChild;
        if (this.mSelectedChild != null) {
            int galleryCenter = getCenterOfGallery();
            if (selView.getLeft() > galleryCenter || selView.getRight() < galleryCenter) {
                int closestEdgeDistance = Integer.MAX_VALUE;
                int newSelectedChildIndex = 0;
                int i = getChildCount() - 1;
                while (true) {
                    if (i < 0) {
                        break;
                    }
                    View child = getChildAt(i);
                    if (child.getLeft() <= galleryCenter && child.getRight() >= galleryCenter) {
                        newSelectedChildIndex = i;
                        break;
                    }
                    int childClosestEdgeDistance = Math.min(Math.abs(child.getLeft() - galleryCenter), Math.abs(child.getRight() - galleryCenter));
                    if (childClosestEdgeDistance < closestEdgeDistance) {
                        closestEdgeDistance = childClosestEdgeDistance;
                        newSelectedChildIndex = i;
                    }
                    i--;
                }
                int newPos = this.mFirstPosition + newSelectedChildIndex;
                if (isScrollCycle()) {
                    newPos %= this.mItemCount;
                }
                if (newPos != this.mSelectedPosition) {
                    setSelectedPositionInt(newPos);
                    setNextSelectedPositionInt(newPos);
                    checkSelectionChanged();
                }
            }
        }
    }

    private void setSelectionToCenterChildVertical() {
        View selView = this.mSelectedChild;
        if (this.mSelectedChild != null) {
            int galleryCenter = getCenterOfGallery();
            if (selView == null || selView.getTop() > galleryCenter || selView.getBottom() < galleryCenter) {
                int closestEdgeDistance = Integer.MAX_VALUE;
                int newSelectedChildIndex = 0;
                int i = getChildCount() - 1;
                while (true) {
                    if (i < 0) {
                        break;
                    }
                    View child = getChildAt(i);
                    if (child.getTop() <= galleryCenter && child.getBottom() >= galleryCenter) {
                        newSelectedChildIndex = i;
                        break;
                    }
                    int childClosestEdgeDistance = Math.min(Math.abs(child.getTop() - galleryCenter), Math.abs(child.getBottom() - galleryCenter));
                    if (childClosestEdgeDistance < closestEdgeDistance) {
                        closestEdgeDistance = childClosestEdgeDistance;
                        newSelectedChildIndex = i;
                    }
                    i--;
                }
                int newPos = this.mFirstPosition + newSelectedChildIndex;
                if (isScrollCycle()) {
                    newPos %= this.mItemCount;
                }
                if (newPos != this.mSelectedPosition) {
                    setSelectedPositionInt(newPos);
                    setNextSelectedPositionInt(newPos);
                    checkSelectionChanged();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void layout(int delta, boolean animate) {
        boolean z = true;
        if (isOrientationVertical()) {
            layoutVertical(delta, animate);
            return;
        }
        int childrenLeft = this.mSpinnerPadding.left + this.mFirstChildOffset;
        if (this.mDataChanged) {
            handleDataChanged();
        }
        if (this.mItemCount == 0) {
            resetList();
            return;
        }
        if (this.mNextSelectedPosition >= 0) {
            setSelectedPositionInt(this.mNextSelectedPosition);
        }
        recycleAllViews();
        detachAllViewsFromParent();
        this.mRightMost = 0;
        this.mLeftMost = 0;
        this.mFirstPosition = this.mSelectedPosition;
        View sel = makeAndAddView(this.mFirstPosition, 0, 0, true);
        int selectedOffset = childrenLeft + this.mSpacing;
        if (isSlotInCenter()) {
            selectedOffset = (((((getRight() - getLeft()) - this.mSpinnerPadding.left) - this.mSpinnerPadding.right) / 2) + childrenLeft) - (sel.getWidth() / 2);
        }
        sel.offsetLeftAndRight(selectedOffset);
        fillToGalleryRight();
        fillToGalleryLeft();
        this.mRecycler.clear();
        invalidate();
        this.mDataChanged = false;
        this.mNeedSync = false;
        setNextSelectedPositionInt(this.mSelectedPosition);
        updateSelectedItemMetadata();
        if (getChildCount() >= this.mItemCount) {
            z = false;
        }
        this.mIsScrollCycleTemp = z;
    }

    /* access modifiers changed from: 0000 */
    public void layoutVertical(int delta, boolean animate) {
        boolean z = true;
        int childrenTop = this.mSpinnerPadding.top + this.mFirstChildOffset;
        if (this.mDataChanged) {
            handleDataChanged();
        }
        if (this.mItemCount == 0) {
            resetList();
            return;
        }
        if (this.mNextSelectedPosition >= 0) {
            setSelectedPositionInt(this.mNextSelectedPosition);
        }
        recycleAllViews();
        detachAllViewsFromParent();
        this.mRightMost = 0;
        this.mLeftMost = 0;
        this.mFirstPosition = this.mSelectedPosition;
        View sel = makeAndAddViewVertical(this.mFirstPosition, 0, 0, true);
        int selectedOffset = childrenTop + this.mSpacing;
        if (isSlotInCenter()) {
            selectedOffset = (((((getBottom() - getTop()) - this.mSpinnerPadding.top) - this.mSpinnerPadding.bottom) / 2) + childrenTop) - (sel.getHeight() / 2);
        }
        sel.offsetTopAndBottom(selectedOffset);
        fillToGalleryBottom();
        fillToGalleryTop();
        this.mRecycler.clear();
        invalidate();
        this.mDataChanged = false;
        this.mNeedSync = false;
        setNextSelectedPositionInt(this.mSelectedPosition);
        updateSelectedItemMetadata();
        if (getChildCount() >= this.mItemCount) {
            z = false;
        }
        this.mIsScrollCycleTemp = z;
    }

    private void fillToGalleryLeft() {
        int curRightEdge;
        int curPosition;
        if (isScrollCycle()) {
            fillToGalleryLeftCycle();
            return;
        }
        int itemSpacing = this.mSpacing;
        int galleryLeft = getPaddingLeft();
        View prevIterationView = getChildAt(0);
        if (prevIterationView != null) {
            curPosition = this.mFirstPosition - 1;
            curRightEdge = prevIterationView.getLeft() - itemSpacing;
        } else {
            curPosition = 0;
            curRightEdge = (getRight() - getLeft()) - getPaddingRight();
            this.mShouldStopFling = true;
        }
        while (curRightEdge > galleryLeft && curPosition >= 0) {
            View prevIterationView2 = makeAndAddView(curPosition, curPosition - this.mSelectedPosition, curRightEdge, false);
            this.mFirstPosition = curPosition;
            curRightEdge = prevIterationView2.getLeft() - itemSpacing;
            curPosition--;
        }
    }

    private void fillToGalleryTop() {
        int curRightEdge;
        int curPosition;
        if (isScrollCycle()) {
            fillToGalleryTopCycle();
            return;
        }
        int itemSpacing = this.mSpacing;
        int galleryTop = getPaddingTop();
        View prevIterationView = getChildAt(0);
        if (prevIterationView != null) {
            curPosition = this.mFirstPosition - 1;
            curRightEdge = prevIterationView.getTop() - itemSpacing;
        } else {
            curPosition = 0;
            curRightEdge = (getBottom() - getTop()) - getPaddingBottom();
            this.mShouldStopFling = true;
        }
        while (curRightEdge > galleryTop && curPosition >= 0) {
            View prevIterationView2 = makeAndAddViewVertical(curPosition, curPosition - this.mSelectedPosition, curRightEdge, false);
            this.mFirstPosition = curPosition;
            curRightEdge = prevIterationView2.getTop() - itemSpacing;
            curPosition--;
        }
    }

    private void fillToGalleryRight() {
        int curPosition;
        int curLeftEdge;
        if (isScrollCycle()) {
            fillToGalleryRightCycle();
            return;
        }
        int itemSpacing = this.mSpacing;
        int galleryRight = (getRight() - getLeft()) - getPaddingRight();
        int numChildren = getChildCount();
        int numItems = this.mItemCount;
        View prevIterationView = getChildAt(numChildren - 1);
        if (prevIterationView != null) {
            curPosition = this.mFirstPosition + numChildren;
            curLeftEdge = prevIterationView.getRight() + itemSpacing;
        } else {
            curPosition = this.mItemCount - 1;
            this.mFirstPosition = curPosition;
            curLeftEdge = getPaddingLeft();
            this.mShouldStopFling = true;
        }
        while (curLeftEdge < galleryRight && curPosition < numItems) {
            curLeftEdge = makeAndAddView(curPosition, curPosition - this.mSelectedPosition, curLeftEdge, true).getRight() + itemSpacing;
            curPosition++;
        }
    }

    private void fillToGalleryBottom() {
        int curPosition;
        int curLeftEdge;
        if (isScrollCycle()) {
            fillToGalleryBottomCycle();
            return;
        }
        int itemSpacing = this.mSpacing;
        int galleryRight = (getBottom() - getTop()) - getPaddingRight();
        int numChildren = getChildCount();
        int numItems = this.mItemCount;
        View prevIterationView = getChildAt(numChildren - 1);
        if (prevIterationView != null) {
            curPosition = this.mFirstPosition + numChildren;
            curLeftEdge = prevIterationView.getBottom() + itemSpacing;
        } else {
            curPosition = this.mItemCount - 1;
            this.mFirstPosition = curPosition;
            curLeftEdge = getPaddingTop();
            this.mShouldStopFling = true;
        }
        while (curLeftEdge < galleryRight && curPosition < numItems) {
            curLeftEdge = makeAndAddViewVertical(curPosition, curPosition - this.mSelectedPosition, curLeftEdge, true).getBottom() + itemSpacing;
            curPosition++;
        }
    }

    private View makeAndAddView(int position, int offset, int x, boolean fromLeft) {
        if (!this.mDataChanged) {
            View child = this.mRecycler.get(position);
            if (child != null) {
                int childLeft = child.getLeft();
                this.mRightMost = Math.max(this.mRightMost, child.getMeasuredWidth() + childLeft);
                this.mLeftMost = Math.min(this.mLeftMost, childLeft);
                setUpChild(child, offset, x, fromLeft);
                return child;
            }
        }
        View child2 = this.mAdapter.getView(position, null, this);
        setUpChild(child2, offset, x, fromLeft);
        return child2;
    }

    private View makeAndAddViewVertical(int position, int offset, int y, boolean fromTop) {
        if (!this.mDataChanged) {
            View child = this.mRecycler.get(position);
            if (child != null) {
                int childTop = child.getTop();
                this.mRightMost = Math.max(this.mRightMost, child.getMeasuredHeight() + childTop);
                this.mLeftMost = Math.min(this.mLeftMost, childTop);
                setUpChildVertical(child, offset, y, fromTop);
                return child;
            }
        }
        View child2 = this.mAdapter.getView(position, null, this);
        setUpChildVertical(child2, offset, y, fromTop);
        return child2;
    }

    private void setUpChild(View child, int offset, int x, boolean fromLeft) {
        int childLeft;
        int childRight;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (lp == null) {
            lp = (LayoutParams) generateDefaultLayoutParams();
        }
        addViewInLayout(child, fromLeft ? -1 : 0, lp);
        child.setSelected(offset == 0);
        child.measure(ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, lp.width), ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, lp.height));
        int childTop = calculateTop(child, true);
        int childBottom = childTop + child.getMeasuredHeight();
        int width = child.getMeasuredWidth();
        if (fromLeft) {
            childLeft = x;
            childRight = childLeft + width;
        } else {
            childLeft = x - width;
            childRight = x;
        }
        child.layout(childLeft, childTop, childRight, childBottom);
    }

    private void setUpChildVertical(View child, int offset, int y, boolean fromTop) {
        int childTop;
        int childBottom;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (lp == null) {
            lp = (LayoutParams) generateDefaultLayoutParams();
        }
        addViewInLayout(child, fromTop ? -1 : 0, lp);
        child.setSelected(offset == 0);
        child.measure(ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, lp.width), ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, lp.height));
        int childLeft = calculateLeft(child, true);
        int childRight = childLeft + child.getMeasuredWidth();
        int height = child.getMeasuredHeight();
        if (fromTop) {
            childTop = y;
            childBottom = childTop + height;
        } else {
            childTop = y - height;
            childBottom = y;
        }
        child.layout(childLeft, childTop, childRight, childBottom);
    }

    private int calculateTop(View child, boolean duringLayout) {
        int childHeight;
        int myHeight = duringLayout ? getMeasuredHeight() : getHeight();
        if (duringLayout) {
            childHeight = child.getMeasuredHeight();
        } else {
            childHeight = child.getHeight();
        }
        switch (this.mGravity) {
            case 16:
                return this.mSpinnerPadding.top + ((((myHeight - this.mSpinnerPadding.bottom) - this.mSpinnerPadding.top) - childHeight) / 2);
            case 48:
                return this.mSpinnerPadding.top;
            case 80:
                return (myHeight - this.mSpinnerPadding.bottom) - childHeight;
            default:
                return 0;
        }
    }

    private int calculateLeft(View child, boolean duringLayout) {
        int childWidth;
        int myWidth = duringLayout ? getMeasuredWidth() : getWidth();
        if (duringLayout) {
            childWidth = child.getMeasuredWidth();
        } else {
            childWidth = child.getWidth();
        }
        switch (this.mGravity) {
            case 1:
                return this.mSpinnerPadding.left + ((((myWidth - this.mSpinnerPadding.right) - this.mSpinnerPadding.left) - childWidth) / 2);
            case 3:
                return this.mSpinnerPadding.left;
            case 5:
                return (myWidth - this.mSpinnerPadding.right) - childWidth;
            default:
                return 0;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean retValue = this.mGestureDetector.onTouchEvent(event);
        int action = event.getAction();
        if (action == 1) {
            onUp();
        } else if (action == 3) {
            onCancel();
        }
        return retValue;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        if (this.mDownTouchPosition < 0) {
            return false;
        }
        if (isScrollCycle()) {
            this.mDownTouchPosition %= getCount();
        }
        if (isSlotInCenter()) {
            scrollToChild(this.mDownTouchPosition - this.mFirstPosition);
        }
        performItemSelect(this.mDownTouchPosition);
        if (this.mShouldCallbackOnUnselectedItemClick || this.mDownTouchPosition == this.mSelectedPosition) {
            performItemClick(this.mDownTouchView, this.mDownTouchPosition, this.mAdapter.getItemId(this.mDownTouchPosition));
        }
        return true;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!shouldDisableScroll()) {
            if (!this.mShouldCallbackDuringFling) {
                removeCallbacks(this.mDisableSuppressSelectionChangedRunnable);
                if (!this.mSuppressSelectionChanged) {
                    this.mSuppressSelectionChanged = true;
                }
            }
            if (isOrientationVertical()) {
                this.mFlingRunnable.startUsingVelocity((int) (-(velocityY * getVelocityRatio())));
            } else {
                this.mFlingRunnable.startUsingVelocity((int) (-(velocityX * getVelocityRatio())));
            }
        }
        return true;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!shouldDisableScroll()) {
            this.mScrolling = true;
            getParent().requestDisallowInterceptTouchEvent(true);
            if (!this.mShouldCallbackDuringFling) {
                if (this.mIsFirstScroll) {
                    if (!this.mSuppressSelectionChanged) {
                        this.mSuppressSelectionChanged = true;
                    }
                    postDelayed(this.mDisableSuppressSelectionChangedRunnable, 250);
                }
            } else if (this.mSuppressSelectionChanged) {
                this.mSuppressSelectionChanged = false;
            }
            if (isOrientationVertical()) {
                trackMotionScrollVertical(((int) distanceY) * -1);
            } else {
                trackMotionScroll(((int) distanceX) * -1);
            }
            this.mIsFirstScroll = false;
        }
        return true;
    }

    public boolean onDown(MotionEvent e) {
        this.mFlingRunnable.stop(false);
        this.mDownTouchPosition = pointToPosition((int) e.getX(), (int) e.getY());
        if (this.mDownTouchPosition >= 0) {
            this.mDownTouchView = getChildAt(this.mDownTouchPosition - this.mFirstPosition);
            this.mDownTouchView.setPressed(true);
        }
        this.mIsFirstScroll = true;
        return true;
    }

    /* access modifiers changed from: protected */
    public void onUp() {
        if (this.mFlingRunnable.mScroller.isFinished()) {
            scrollIntoSlots();
        }
        dispatchUnpress();
    }

    /* access modifiers changed from: 0000 */
    public void onCancel() {
        onUp();
    }

    public void onLongPress(MotionEvent e) {
        if (this.mDownTouchPosition >= 0) {
            performHapticFeedback(0);
            dispatchLongPress(this.mDownTouchView, this.mDownTouchPosition, getItemIdAtPosition(this.mDownTouchPosition));
        }
    }

    public void onShowPress(MotionEvent e) {
    }

    private void dispatchPress(View child) {
        if (child != null) {
            child.setPressed(true);
        }
        setPressed(true);
    }

    /* access modifiers changed from: protected */
    public void dispatchUnpress() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            getChildAt(i).setPressed(false);
        }
        setPressed(false);
    }

    public void dispatchSetSelected(boolean selected) {
    }

    /* access modifiers changed from: protected */
    public void dispatchSetPressed(boolean pressed) {
        if (this.mSelectedChild != null) {
            this.mSelectedChild.setPressed(pressed);
        }
    }

    /* access modifiers changed from: protected */
    public ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public boolean showContextMenuForChild(View originalView) {
        int longPressPosition = getPositionForView(originalView);
        if (longPressPosition < 0) {
            return false;
        }
        return dispatchLongPress(originalView, longPressPosition, this.mAdapter.getItemId(longPressPosition));
    }

    public boolean showContextMenu() {
        if (!isPressed() || this.mSelectedPosition < 0) {
            return false;
        }
        return dispatchLongPress(getChildAt(this.mSelectedPosition - this.mFirstPosition), this.mSelectedPosition, this.mSelectedRowId);
    }

    private boolean dispatchLongPress(View view, int position, long id) {
        boolean handled = false;
        if (this.mOnItemLongClickListener != null) {
            handled = this.mOnItemLongClickListener.onItemLongClick(this, this.mDownTouchView, this.mDownTouchPosition, id);
        }
        if (!handled) {
            this.mContextMenuInfo = new AdapterContextMenuInfo(view, position, id);
            handled = super.showContextMenuForChild(this);
        }
        if (handled) {
            performHapticFeedback(0);
        }
        return handled;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return event.dispatch(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 21:
                if (!movePrevious()) {
                    return true;
                }
                playSoundEffect(1);
                return true;
            case 22:
                if (!moveNext()) {
                    return true;
                }
                playSoundEffect(3);
                return true;
            case 23:
            case 66:
                this.mReceivedInvokeKeyDown = true;
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 23:
            case 66:
                if (this.mReceivedInvokeKeyDown && this.mItemCount > 0) {
                    dispatchPress(this.mSelectedChild);
                    postDelayed(new Runnable() {
                        public void run() {
                            TosGallery.this.dispatchUnpress();
                        }
                    }, (long) ViewConfiguration.getPressedStateDuration());
                    performItemClick(getChildAt(this.mSelectedPosition - this.mFirstPosition), this.mSelectedPosition, this.mAdapter.getItemId(this.mSelectedPosition));
                }
                this.mReceivedInvokeKeyDown = false;
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean movePrevious() {
        if (this.mItemCount <= 0 || this.mSelectedPosition <= 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean moveNext() {
        if (this.mItemCount <= 0 || this.mSelectedPosition >= this.mItemCount - 1) {
            return false;
        }
        return true;
    }

    private boolean scrollToChild(int childPosition) {
        View child = getChildAt(childPosition);
        if (child == null) {
            return false;
        }
        this.mFlingRunnable.startUsingDistance(getCenterOfGallery() - getCenterOfView(child));
        return true;
    }

    /* access modifiers changed from: protected */
    public void setSelectedPositionInt(int position) {
        super.setSelectedPositionInt(position);
        updateSelectedItemMetadata();
    }

    private void updateSelectedItemMetadata() {
        View oldSelectedChild = this.mSelectedChild;
        Log(" updateSelectedItemMetadata   mSelectedPosition =  " + this.mSelectedPosition + "   mFirstPosition = " + this.mFirstPosition);
        int index = this.mSelectedPosition - this.mFirstPosition;
        if (isScrollCycle() && this.mFirstPosition > this.mSelectedPosition) {
            index = (this.mItemCount - this.mFirstPosition) + this.mSelectedPosition;
        }
        View child = getChildAt(index);
        this.mSelectedChild = child;
        if (child != null) {
            child.setSelected(true);
            child.setFocusable(true);
            if (hasFocus()) {
                child.requestFocus();
            }
            if (oldSelectedChild != null) {
                oldSelectedChild.setSelected(false);
                oldSelectedChild.setFocusable(false);
            }
        }
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            this.mGravity = gravity;
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int childCount, int i) {
        int selectedIndex = this.mSelectedPosition - this.mFirstPosition;
        if (selectedIndex < 0) {
            return i;
        }
        if (i == childCount - 1) {
            return selectedIndex;
        }
        if (i >= selectedIndex) {
            return i + 1;
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus && this.mSelectedChild != null) {
            this.mSelectedChild.requestFocus(direction);
        }
    }

    private boolean performItemSelect(int childPosition) {
        if (childPosition == this.mSelectedPosition) {
            return false;
        }
        setSelectedPositionInt(childPosition);
        setNextSelectedPositionInt(childPosition);
        checkSelectionChanged();
        return true;
    }

    public void setFirstChildOffset(int firstChildOffset) {
        this.mFirstChildOffset = firstChildOffset;
    }

    public void setFirstPosition(int firstPosition) {
        this.mFirstPosition = firstPosition;
    }

    public void setSlotInCenter(boolean isSlotCenter) {
        this.mIsSlotCenter = isSlotCenter;
    }

    public boolean isSlotInCenter() {
        return this.mIsSlotCenter;
    }

    /* access modifiers changed from: private */
    public boolean isOrientationVertical() {
        return this.mOrientation == 2;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public void setOnEndFlingListener(OnEndFlingListener listener) {
        this.mOnEndFlingListener = listener;
    }

    public void setDisableScroll(boolean disableScroll) {
        this.mIsDisableScroll = disableScroll;
    }

    public void setScrollBarBottomMargin(int scrollBarBottomMargin) {
        this.mScrollBarBottomMargin = scrollBarBottomMargin;
    }

    public void setScrollBarSize(int scrollBarSize) {
        this.mScrollBarSize = scrollBarSize;
    }

    public int getFirstPosition() {
        return this.mFirstPosition;
    }

    public int getSpacing() {
        return this.mSpacing;
    }

    public boolean isScrolling() {
        return this.mScrolling;
    }

    public int scrollGalleryItems(boolean toLeft) {
        int offset;
        int i = 0;
        if (getChildCount() == 0) {
            return 0;
        }
        if (toLeft) {
            View child = getChildAt(getChildCount() - 1);
            offset = Math.max((child.getRight() - getRight()) + getPaddingRight(), 0);
            if (offset == 0 && this.mFirstPosition + getChildCount() != this.mItemCount) {
                if (child != null) {
                    i = child.getWidth();
                }
                offset += i;
            }
        } else {
            View child2 = getChildAt(0);
            offset = Math.min(child2.getLeft() - getPaddingLeft(), 0);
            if (offset == 0 && this.mFirstPosition != 0) {
                if (child2 != null) {
                    i = child2.getWidth();
                }
                offset -= i;
            }
        }
        if (!(offset == 0 || this.mFlingRunnable == null)) {
            this.mFlingRunnable.startUsingDistance(offset * -1);
        }
        return offset * -1;
    }

    public int scrollGalleryItems(int offset) {
        if (this.mFlingRunnable != null) {
            this.mFlingRunnable.startUsingDistance(offset * -1);
        }
        return offset * -1;
    }

    public int getItemIndexFromPoint(Point pt) {
        int nChildCount = getChildCount();
        int nIndex = -1;
        Rect rc = new Rect();
        getDrawingRect(rc);
        if (rc.contains(pt.x, pt.y)) {
            int i = 0;
            while (true) {
                if (i >= nChildCount) {
                    break;
                }
                getChildAt(i).getHitRect(rc);
                if (rc.contains(pt.x, pt.y)) {
                    nIndex = i;
                    break;
                }
                i++;
            }
        }
        if (nIndex >= 0) {
            return this.mFirstPosition + nIndex;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void onEndFling() {
        if (this.mOnEndFlingListener != null) {
            this.mOnEndFlingListener.onEndFling(this);
        }
    }

    public float getVelocityRatio() {
        return this.mVelocityRatio;
    }

    public void setVelocityRatio(float velocityRatio) {
        this.mVelocityRatio = velocityRatio;
        if (this.mVelocityRatio < 0.5f) {
            this.mVelocityRatio = 0.5f;
        } else if (this.mVelocityRatio > 1.5f) {
            this.mVelocityRatio = 1.5f;
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldDisableScroll() {
        if (!this.mIsDisableScroll || getChildCount() < this.mItemCount) {
            return false;
        }
        View child = getChildAt(0);
        if (child != null && child.getLeft() < getLeft()) {
            return false;
        }
        View child2 = getChildAt(getChildCount() - 1);
        if (child2 == null || child2.getRight() <= getRight()) {
            return true;
        }
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        if (1 != e.getAction() || this.mDownTouchPosition < 0) {
            return false;
        }
        if (!this.mShouldCallbackOnUnselectedItemClick && this.mDownTouchPosition != this.mSelectedPosition) {
            return true;
        }
        performItemDoubleClick(this.mDownTouchView, this.mDownTouchPosition, this.mAdapter.getItemId(this.mDownTouchPosition));
        return true;
    }

    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    public boolean isPointInChild(float x, float y) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (x >= ((float) child.getLeft()) && x <= ((float) child.getRight()) && y >= ((float) child.getTop()) && y <= ((float) child.getBottom())) {
                return true;
            }
        }
        return false;
    }

    public void setScrollCycle(boolean scrollCycle) {
        this.mIsScrollCycle = scrollCycle;
    }

    public boolean isScrollCycle() {
        return this.mIsScrollCycle && this.mIsScrollCycleTemp;
    }

    /* access modifiers changed from: protected */
    public void Log(String msg) {
    }

    private void fillToGalleryLeftCycle() {
        int curRightEdge;
        int curPosition;
        int itemSpacing = this.mSpacing;
        int galleryLeft = getPaddingLeft();
        View prevIterationView = getChildAt(0);
        if (prevIterationView != null) {
            curPosition = this.mFirstPosition - 1;
            curRightEdge = prevIterationView.getLeft() - itemSpacing;
        } else {
            curPosition = 0;
            curRightEdge = (getRight() - getLeft()) - getPaddingRight();
            this.mShouldStopFling = true;
        }
        while (curRightEdge > galleryLeft && curPosition >= 0) {
            View prevIterationView2 = makeAndAddView(curPosition, curPosition - this.mSelectedPosition, curRightEdge, false);
            this.mFirstPosition = curPosition;
            curRightEdge = prevIterationView2.getLeft() - itemSpacing;
            curPosition--;
        }
        int curPosition2 = this.mItemCount - 1;
        while (curRightEdge > galleryLeft && getChildCount() < this.mItemCount) {
            View prevIterationView3 = makeAndAddView(curPosition2, curPosition2 - this.mSelectedPosition, curRightEdge, false);
            this.mFirstPosition = curPosition2;
            curRightEdge = prevIterationView3.getLeft() - itemSpacing;
            curPosition2--;
        }
    }

    private void fillToGalleryTopCycle() {
        int curRightEdge;
        int curPosition;
        int itemSpacing = this.mSpacing;
        int galleryTop = getPaddingTop();
        View prevIterationView = getChildAt(0);
        if (prevIterationView != null) {
            curPosition = this.mFirstPosition - 1;
            curRightEdge = prevIterationView.getTop() - itemSpacing;
        } else {
            curPosition = 0;
            curRightEdge = (getBottom() - getTop()) - getPaddingBottom();
            this.mShouldStopFling = true;
        }
        while (curRightEdge > galleryTop && curPosition >= 0) {
            View prevIterationView2 = makeAndAddViewVertical(curPosition, curPosition - this.mSelectedPosition, curRightEdge, false);
            this.mFirstPosition = curPosition;
            curRightEdge = prevIterationView2.getTop() - itemSpacing;
            curPosition--;
        }
        int curPosition2 = this.mItemCount - 1;
        while (curRightEdge > galleryTop && getChildCount() < this.mItemCount) {
            View prevIterationView3 = makeAndAddViewVertical(curPosition2, curPosition2 - this.mSelectedPosition, curRightEdge, false);
            this.mFirstPosition = curPosition2;
            curRightEdge = prevIterationView3.getTop() - itemSpacing;
            curPosition2--;
        }
    }

    private void fillToGalleryRightCycle() {
        int curPosition;
        int curLeftEdge;
        int itemSpacing = this.mSpacing;
        int galleryRight = (getRight() - getLeft()) - getPaddingRight();
        int numChildren = getChildCount();
        int numItems = this.mItemCount;
        View prevIterationView = getChildAt(numChildren - 1);
        Log("  fillToGalleryRightCycle mFirstPosition = " + this.mFirstPosition);
        if (prevIterationView != null) {
            curPosition = this.mFirstPosition + numChildren;
            curLeftEdge = prevIterationView.getRight() + itemSpacing;
        } else {
            curPosition = this.mItemCount - 1;
            this.mFirstPosition = curPosition;
            curLeftEdge = getPaddingLeft();
            this.mShouldStopFling = true;
        }
        while (curLeftEdge < galleryRight && curPosition < numItems) {
            curLeftEdge = makeAndAddView(curPosition, curPosition - this.mSelectedPosition, curLeftEdge, true).getRight() + itemSpacing;
            curPosition++;
        }
        int curPosition2 = curPosition % numItems;
        while (curLeftEdge <= galleryRight && getChildCount() < this.mItemCount) {
            curLeftEdge = makeAndAddView(curPosition2, curPosition2 - this.mSelectedPosition, curLeftEdge, true).getRight() + itemSpacing;
            curPosition2++;
        }
    }

    private void fillToGalleryBottomCycle() {
        int curPosition;
        int curLeftEdge;
        int itemSpacing = this.mSpacing;
        int galleryBottom = (getBottom() - getTop()) - getPaddingBottom();
        int numChildren = getChildCount();
        int numItems = this.mItemCount;
        View prevIterationView = getChildAt(numChildren - 1);
        Log("  fillToGalleryRightCycle mFirstPosition = " + this.mFirstPosition);
        if (prevIterationView != null) {
            curPosition = this.mFirstPosition + numChildren;
            curLeftEdge = prevIterationView.getBottom() + itemSpacing;
        } else {
            curPosition = this.mItemCount - 1;
            this.mFirstPosition = curPosition;
            curLeftEdge = getPaddingTop();
            this.mShouldStopFling = true;
        }
        while (curLeftEdge < galleryBottom && curPosition < numItems) {
            curLeftEdge = makeAndAddViewVertical(curPosition, curPosition - this.mSelectedPosition, curLeftEdge, true).getBottom() + itemSpacing;
            curPosition++;
        }
        int curPosition2 = curPosition % numItems;
        while (curLeftEdge <= galleryBottom && getChildCount() < this.mItemCount) {
            curLeftEdge = makeAndAddViewVertical(curPosition2, curPosition2 - this.mSelectedPosition, curLeftEdge, true).getBottom() + itemSpacing;
            curPosition2++;
        }
    }
}
