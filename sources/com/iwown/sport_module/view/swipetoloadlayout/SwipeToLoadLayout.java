package com.iwown.sport_module.view.swipetoloadlayout;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.OverScroller;
import android.widget.Scroller;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.iwown.sport_module.R;
import com.socks.library.KLog;

public class SwipeToLoadLayout extends ViewGroup implements NestedScrollingParent {
    private static final int DEFAULT_DEFAULT_TO_LOADING_MORE_SCROLLING_DURATION = 300;
    private static final int DEFAULT_DEFAULT_TO_REFRESHING_SCROLLING_DURATION = 500;
    private static final float DEFAULT_DRAG_RATIO = 0.5f;
    private static final int DEFAULT_LOAD_MORE_COMPLETE_DELAY_DURATION = 300;
    private static final int DEFAULT_LOAD_MORE_COMPLETE_TO_DEFAULT_SCROLLING_DURATION = 300;
    private static final int DEFAULT_REFRESH_COMPLETE_DELAY_DURATION = 300;
    private static final int DEFAULT_REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION = 500;
    private static final int DEFAULT_RELEASE_TO_LOADING_MORE_SCROLLING_DURATION = 200;
    private static final int DEFAULT_RELEASE_TO_REFRESHING_SCROLLING_DURATION = 200;
    private static final int DEFAULT_SWIPING_TO_LOAD_MORE_TO_DEFAULT_SCROLLING_DURATION = 200;
    private static final int DEFAULT_SWIPING_TO_REFRESH_TO_DEFAULT_SCROLLING_DURATION = 200;
    private static final int INVALID_COORDINATE = -1;
    private static final int INVALID_POINTER = -1;
    /* access modifiers changed from: private */
    public static final String TAG = SwipeToLoadLayout.class.getSimpleName();
    /* access modifiers changed from: private */
    public boolean canHeadScrollWithParentNow;
    private int mActivePointerId;
    private boolean mAutoLoading;
    private AutoScroller mAutoScroller;
    private boolean mDebug;
    private int mDefaultToLoadingMoreScrollingDuration;
    private int mDefaultToRefreshingScrollingDuration;
    private float mDragRatio;
    private int mFooterHeight;
    private int mFooterOffset;
    /* access modifiers changed from: private */
    public View mFooterView;
    private boolean mHasFooterView;
    private boolean mHasHeaderView;
    private int mHeaderHeight;
    private int mHeaderOffset;
    /* access modifiers changed from: private */
    public View mHeaderView;
    private float mInitDownX;
    private float mInitDownY;
    private float mLastX;
    private float mLastY;
    LoadMoreCallback mLoadMoreCallback;
    private int mLoadMoreCompleteDelayDuration;
    private int mLoadMoreCompleteToDefaultScrollingDuration;
    private boolean mLoadMoreEnabled;
    private float mLoadMoreFinalDragOffset;
    /* access modifiers changed from: private */
    public OnLoadMoreListener mLoadMoreListener;
    private float mLoadMoreTriggerOffset;
    ValueAnimator mOffsetAnimator;
    private OverScroller mOverScroller;
    private NestedScrollingParentHelper mParentHelper;
    RefreshCallback mRefreshCallback;
    private int mRefreshCompleteDelayDuration;
    private int mRefreshCompleteToDefaultScrollingDuration;
    private boolean mRefreshEnabled;
    private float mRefreshFinalDragOffset;
    /* access modifiers changed from: private */
    public OnRefreshListener mRefreshListener;
    private float mRefreshTriggerOffset;
    private int mReleaseToLoadMoreToLoadingMoreScrollingDuration;
    private int mReleaseToRefreshToRefreshingScrollingDuration;
    /* access modifiers changed from: private */
    public int mStatus;
    private int mStyle;
    private int mSwipingToLoadMoreToDefaultScrollingDuration;
    private int mSwipingToRefreshToDefaultScrollingDuration;
    private int mTargetOffset;
    /* access modifiers changed from: private */
    public View mTargetView;
    private final int mTouchSlop;
    private boolean needHeadScrollWithParent;
    private SparseArray<View> otherviews;

    private class AutoScroller implements Runnable {
        private boolean mAbort = false;
        private boolean mRunning = false;
        private Scroller mScroller;
        private int mmLastY;

        public AutoScroller() {
            this.mScroller = new Scroller(SwipeToLoadLayout.this.getContext());
        }

        public void run() {
            boolean finish = !this.mScroller.computeScrollOffset() || this.mScroller.isFinished();
            int currY = this.mScroller.getCurrY();
            int yDiff = currY - this.mmLastY;
            if (finish) {
                finish();
                return;
            }
            this.mmLastY = currY;
            SwipeToLoadLayout.this.autoScroll((float) yDiff);
            SwipeToLoadLayout.this.post(this);
        }

        private void finish() {
            this.mmLastY = 0;
            this.mRunning = false;
            SwipeToLoadLayout.this.removeCallbacks(this);
            if (!this.mAbort) {
                SwipeToLoadLayout.this.autoScrollFinished();
            }
        }

        public void abortIfRunning() {
            if (this.mRunning) {
                if (!this.mScroller.isFinished()) {
                    this.mAbort = true;
                    this.mScroller.forceFinished(true);
                }
                finish();
                this.mAbort = false;
            }
        }

        /* access modifiers changed from: private */
        public void autoScroll(int yScrolled, int duration) {
            SwipeToLoadLayout.this.removeCallbacks(this);
            this.mmLastY = 0;
            if (!this.mScroller.isFinished()) {
                this.mScroller.forceFinished(true);
            }
            this.mScroller.startScroll(0, 0, 0, yScrolled, duration);
            SwipeToLoadLayout.this.post(this);
            this.mRunning = true;
        }
    }

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

    abstract class LoadMoreCallback implements SwipeTrigger, SwipeLoadMoreTrigger {
        LoadMoreCallback() {
        }
    }

    abstract class RefreshCallback implements SwipeTrigger, SwipeRefreshTrigger {
        RefreshCallback() {
        }
    }

    private static final class STATUS {
        private static final int STATUS_DEFAULT = 0;
        private static final int STATUS_LOADING_MORE = 3;
        private static final int STATUS_LOAD_MORE_RETURNING = 4;
        private static final int STATUS_REFRESHING = -3;
        private static final int STATUS_REFRESH_RETURNING = -4;
        private static final int STATUS_RELEASE_TO_LOAD_MORE = 2;
        private static final int STATUS_RELEASE_TO_REFRESH = -2;
        private static final int STATUS_SWIPING_TO_LOAD_MORE = 1;
        private static final int STATUS_SWIPING_TO_REFRESH = -1;

        private STATUS() {
        }

        /* access modifiers changed from: private */
        public static boolean isRefreshing(int status) {
            return status == -3;
        }

        /* access modifiers changed from: private */
        public static boolean isLoadingMore(int status) {
            return status == 3;
        }

        /* access modifiers changed from: private */
        public static boolean isReleaseToRefresh(int status) {
            return status == -2;
        }

        /* access modifiers changed from: private */
        public static boolean isReleaseToLoadMore(int status) {
            return status == 2;
        }

        /* access modifiers changed from: private */
        public static boolean isSwipingToRefresh(int status) {
            return status == -1;
        }

        /* access modifiers changed from: private */
        public static boolean isSwipingToLoadMore(int status) {
            return status == 1;
        }

        /* access modifiers changed from: private */
        public static boolean isRefreshStatus(int status) {
            return status < 0;
        }

        public static boolean isLoadMoreStatus(int status) {
            return status > 0;
        }

        /* access modifiers changed from: private */
        public static boolean isStatusDefault(int status) {
            return status == 0;
        }

        /* access modifiers changed from: private */
        public static String getStatus(int status) {
            switch (status) {
                case -4:
                    return "status_refresh_returning";
                case -3:
                    return "status_refreshing";
                case -2:
                    return "status_release_to_refresh";
                case -1:
                    return "status_swiping_to_refresh";
                case 0:
                    return "status_default";
                case 1:
                    return "status_swiping_to_load_more";
                case 2:
                    return "status_release_to_load_more";
                case 3:
                    return "status_loading_more";
                case 4:
                    return "status_load_more_returning";
                default:
                    return "status_illegal!";
            }
        }

        /* access modifiers changed from: private */
        public static void printStatus(int status) {
            Log.i(SwipeToLoadLayout.TAG, "printStatus:" + getStatus(status));
        }
    }

    public static final class STYLE {
        public static final int ABOVE = 1;
        public static final int BLEW = 2;
        public static final int CLASSIC = 0;
        public static final int SCALE = 3;
    }

    public SwipeToLoadLayout(Context context) {
        this(context, null);
    }

    public SwipeToLoadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX INFO: finally extract failed */
    public SwipeToLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDragRatio = 0.5f;
        this.mStatus = 0;
        this.mRefreshEnabled = true;
        this.mLoadMoreEnabled = true;
        this.mStyle = 0;
        this.mSwipingToRefreshToDefaultScrollingDuration = 200;
        this.mReleaseToRefreshToRefreshingScrollingDuration = 200;
        this.mRefreshCompleteDelayDuration = 300;
        this.mRefreshCompleteToDefaultScrollingDuration = 500;
        this.mDefaultToRefreshingScrollingDuration = 500;
        this.mReleaseToLoadMoreToLoadingMoreScrollingDuration = 200;
        this.mLoadMoreCompleteDelayDuration = 300;
        this.mLoadMoreCompleteToDefaultScrollingDuration = 300;
        this.mSwipingToLoadMoreToDefaultScrollingDuration = 200;
        this.mDefaultToLoadingMoreScrollingDuration = 300;
        this.otherviews = new SparseArray<>();
        this.mRefreshCallback = new RefreshCallback() {
            public void onPrepare() {
                if (SwipeToLoadLayout.this.mHeaderView != null && (SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger) && STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
                    SwipeToLoadLayout.this.mHeaderView.setVisibility(0);
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onPrepare();
                }
            }

            public void onMove(int y, boolean isComplete, boolean automatic) {
                if (SwipeToLoadLayout.this.mHeaderView != null && (SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger) && STATUS.isRefreshStatus(SwipeToLoadLayout.this.mStatus)) {
                    if (SwipeToLoadLayout.this.mHeaderView.getVisibility() != 0) {
                        SwipeToLoadLayout.this.mHeaderView.setVisibility(0);
                    }
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onMove(y, isComplete, automatic);
                }
            }

            public void onRelease() {
                if (SwipeToLoadLayout.this.mHeaderView != null && (SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger) && STATUS.isReleaseToRefresh(SwipeToLoadLayout.this.mStatus)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onRelease();
                }
            }

            public void onRefresh() {
                if (SwipeToLoadLayout.this.mHeaderView != null && STATUS.isRefreshing(SwipeToLoadLayout.this.mStatus)) {
                    if (SwipeToLoadLayout.this.mHeaderView instanceof SwipeRefreshTrigger) {
                        ((SwipeRefreshTrigger) SwipeToLoadLayout.this.mHeaderView).onRefresh();
                    }
                    if (SwipeToLoadLayout.this.mRefreshListener != null) {
                        SwipeToLoadLayout.this.mRefreshListener.onRefresh();
                    }
                }
            }

            public void onComplete() {
                if (SwipeToLoadLayout.this.mHeaderView != null && (SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onComplete();
                    SwipeToLoadLayout.this.canHeadScrollWithParentNow = false;
                }
            }

            public void onReset() {
                if (SwipeToLoadLayout.this.mHeaderView != null && (SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger) && STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onReset();
                    SwipeToLoadLayout.this.mHeaderView.setVisibility(8);
                }
            }
        };
        this.mLoadMoreCallback = new LoadMoreCallback() {
            public void onPrepare() {
                if (SwipeToLoadLayout.this.mFooterView != null && (SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger) && STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
                    SwipeToLoadLayout.this.mFooterView.setVisibility(0);
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onPrepare();
                }
            }

            public void onMove(int y, boolean isComplete, boolean automatic) {
                if (SwipeToLoadLayout.this.mFooterView != null && (SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger) && STATUS.isLoadMoreStatus(SwipeToLoadLayout.this.mStatus)) {
                    if (SwipeToLoadLayout.this.mFooterView.getVisibility() != 0) {
                        SwipeToLoadLayout.this.mFooterView.setVisibility(0);
                    }
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onMove(y, isComplete, automatic);
                }
            }

            public void onRelease() {
                if (SwipeToLoadLayout.this.mFooterView != null && (SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger) && STATUS.isReleaseToLoadMore(SwipeToLoadLayout.this.mStatus)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onRelease();
                }
            }

            public void onLoadMore() {
                if (SwipeToLoadLayout.this.mFooterView != null && STATUS.isLoadingMore(SwipeToLoadLayout.this.mStatus)) {
                    if (SwipeToLoadLayout.this.mFooterView instanceof SwipeLoadMoreTrigger) {
                        ((SwipeLoadMoreTrigger) SwipeToLoadLayout.this.mFooterView).onLoadMore();
                    }
                    if (SwipeToLoadLayout.this.mLoadMoreListener != null) {
                        SwipeToLoadLayout.this.mLoadMoreListener.onLoadMore();
                    }
                }
            }

            public void onComplete() {
                if (SwipeToLoadLayout.this.mFooterView != null && (SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onComplete();
                }
            }

            public void onReset() {
                if (SwipeToLoadLayout.this.mFooterView != null && (SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger) && STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onReset();
                    SwipeToLoadLayout.this.mFooterView.setVisibility(8);
                }
            }
        };
        this.canHeadScrollWithParentNow = false;
        this.needHeadScrollWithParent = false;
        this.mOffsetAnimator = null;
        this.mParentHelper = new NestedScrollingParentHelper(this);
        this.mOverScroller = new OverScroller(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sport_module_SwipeToLoadLayout, defStyleAttr, 0);
        try {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_refresh_enabled) {
                    setRefreshEnabled(a.getBoolean(attr, true));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_load_more_enabled) {
                    setLoadMoreEnabled(a.getBoolean(attr, true));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_swipe_style) {
                    setSwipeStyle(a.getInt(attr, 0));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_drag_ratio) {
                    setDragRatio(a.getFloat(attr, 0.5f));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_refresh_final_drag_offset) {
                    setRefreshFinalDragOffset(a.getDimensionPixelOffset(attr, 0));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_load_more_final_drag_offset) {
                    setLoadMoreFinalDragOffset(a.getDimensionPixelOffset(attr, 0));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_refresh_trigger_offset) {
                    setRefreshTriggerOffset(a.getDimensionPixelOffset(attr, 0));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_load_more_trigger_offset) {
                    setLoadMoreTriggerOffset(a.getDimensionPixelOffset(attr, 0));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_swiping_to_refresh_to_default_scrolling_duration) {
                    setSwipingToRefreshToDefaultScrollingDuration(a.getInt(attr, 200));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_release_to_refreshing_scrolling_duration) {
                    setReleaseToRefreshingScrollingDuration(a.getInt(attr, 200));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_refresh_complete_delay_duration) {
                    setRefreshCompleteDelayDuration(a.getInt(attr, 300));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_refresh_complete_to_default_scrolling_duration) {
                    setRefreshCompleteToDefaultScrollingDuration(a.getInt(attr, 500));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_default_to_refreshing_scrolling_duration) {
                    setDefaultToRefreshingScrollingDuration(a.getInt(attr, 500));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_swiping_to_load_more_to_default_scrolling_duration) {
                    setSwipingToLoadMoreToDefaultScrollingDuration(a.getInt(attr, 200));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_release_to_loading_more_scrolling_duration) {
                    setReleaseToLoadingMoreScrollingDuration(a.getInt(attr, 200));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_load_more_complete_delay_duration) {
                    setLoadMoreCompleteDelayDuration(a.getInt(attr, 300));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_load_more_complete_to_default_scrolling_duration) {
                    setLoadMoreCompleteToDefaultScrollingDuration(a.getInt(attr, 300));
                } else if (attr == R.styleable.sport_module_SwipeToLoadLayout_sport_module_default_to_loading_more_scrolling_duration) {
                    setDefaultToLoadingMoreScrollingDuration(a.getInt(attr, 300));
                }
            }
            a.recycle();
            this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            this.mAutoScroller = new AutoScroller();
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        int childNum = getChildCount();
        if (childNum != 0) {
            this.mHeaderView = findViewById(R.id.sport_module_swipe_refresh_header);
            this.mTargetView = findViewById(R.id.sport_module_swipe_target);
            this.mFooterView = findViewById(R.id.sport_module_swipe_load_more_footer);
            if (this.mTargetView != null) {
                if (this.mHeaderView != null && (this.mHeaderView instanceof SwipeTrigger)) {
                    this.mHeaderView.setVisibility(8);
                }
                if (this.mFooterView != null && (this.mFooterView instanceof SwipeTrigger)) {
                    this.mFooterView.setVisibility(8);
                }
                for (int i = 0; i < childNum; i++) {
                    View view = getChildAt(i);
                    if (!(view == null || this.mHeaderView == view || this.mFooterView == view || this.mTargetView == view)) {
                        this.otherviews.put(view.getId(), view);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mHeaderView != null) {
            View headerView = this.mHeaderView;
            measureChildWithMargins(headerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = (MarginLayoutParams) headerView.getLayoutParams();
            this.mHeaderHeight = headerView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (this.mRefreshTriggerOffset < ((float) this.mHeaderHeight)) {
                this.mRefreshTriggerOffset = (float) this.mHeaderHeight;
            }
        }
        if (this.mTargetView != null) {
            measureChildWithMargins(this.mTargetView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        if (this.mFooterView != null) {
            View footerView = this.mFooterView;
            measureChildWithMargins(footerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp2 = (MarginLayoutParams) footerView.getLayoutParams();
            this.mFooterHeight = footerView.getMeasuredHeight() + lp2.topMargin + lp2.bottomMargin;
            if (this.mLoadMoreTriggerOffset < ((float) this.mFooterHeight)) {
                this.mLoadMoreTriggerOffset = (float) this.mFooterHeight;
            }
        }
        for (int i = 0; i < this.otherviews.size(); i++) {
            measureChildWithMargins((View) this.otherviews.valueAt(i), widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        boolean z;
        boolean z2 = true;
        layoutChildren();
        if (this.mHeaderView != null) {
            z = true;
        } else {
            z = false;
        }
        this.mHasHeaderView = z;
        if (this.mFooterView == null) {
            z2 = false;
        }
        this.mHasFooterView = z2;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case 1:
            case 3:
                onActivePointerUp();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean moved;
        boolean triggerCondition;
        switch (MotionEventCompat.getActionMasked(event)) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                float motionEventY = getMotionEventY(event, this.mActivePointerId);
                this.mLastY = motionEventY;
                this.mInitDownY = motionEventY;
                float motionEventX = getMotionEventX(event, this.mActivePointerId);
                this.mLastX = motionEventX;
                this.mInitDownX = motionEventX;
                if (STATUS.isSwipingToRefresh(this.mStatus) || STATUS.isSwipingToLoadMore(this.mStatus) || STATUS.isReleaseToRefresh(this.mStatus) || STATUS.isReleaseToLoadMore(this.mStatus)) {
                    this.mAutoScroller.abortIfRunning();
                    if (this.mDebug) {
                        Log.i(TAG, "Another finger down, abort auto scrolling, let the new finger handle");
                    }
                }
                if (STATUS.isSwipingToRefresh(this.mStatus) || STATUS.isReleaseToRefresh(this.mStatus) || STATUS.isSwipingToLoadMore(this.mStatus) || STATUS.isReleaseToLoadMore(this.mStatus)) {
                    return true;
                }
            case 1:
            case 3:
                this.mActivePointerId = -1;
                break;
            case 2:
                if (this.mActivePointerId == -1) {
                    return false;
                }
                float y = getMotionEventY(event, this.mActivePointerId);
                float x = getMotionEventX(event, this.mActivePointerId);
                float yInitDiff = y - this.mInitDownY;
                float xInitDiff = x - this.mInitDownX;
                this.mLastY = y;
                this.mLastX = x;
                if (Math.abs(yInitDiff) <= Math.abs(xInitDiff) || Math.abs(yInitDiff) <= ((float) this.mTouchSlop)) {
                    moved = false;
                } else {
                    moved = true;
                }
                if ((yInitDiff <= 0.0f || !moved || !onCheckCanRefresh()) && (yInitDiff >= 0.0f || !moved || !onCheckCanLoadMore())) {
                    triggerCondition = false;
                } else {
                    triggerCondition = true;
                }
                if (triggerCondition) {
                    return true;
                }
                break;
            case 6:
                onSecondaryPointerUp(event);
                float motionEventY2 = getMotionEventY(event, this.mActivePointerId);
                this.mLastY = motionEventY2;
                this.mInitDownY = motionEventY2;
                float motionEventX2 = getMotionEventX(event, this.mActivePointerId);
                this.mLastX = motionEventX2;
                this.mInitDownX = motionEventX2;
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                return true;
            case 1:
            case 3:
                if (this.mActivePointerId != -1) {
                    this.mActivePointerId = -1;
                    break;
                } else {
                    return false;
                }
            case 2:
                float y = getMotionEventY(event, this.mActivePointerId);
                float x = getMotionEventX(event, this.mActivePointerId);
                float yDiff = y - this.mLastY;
                float xDiff = x - this.mLastX;
                this.mLastY = y;
                this.mLastX = x;
                if (Math.abs(xDiff) > Math.abs(yDiff) && Math.abs(xDiff) > ((float) this.mTouchSlop)) {
                    return false;
                }
                if (STATUS.isStatusDefault(this.mStatus)) {
                    if (yDiff > 0.0f && onCheckCanRefresh()) {
                        this.mRefreshCallback.onPrepare();
                        setStatus(-1);
                    } else if (yDiff < 0.0f && onCheckCanLoadMore()) {
                        this.mLoadMoreCallback.onPrepare();
                        setStatus(1);
                    }
                } else if (STATUS.isRefreshStatus(this.mStatus)) {
                    if (this.mTargetOffset <= 0) {
                        setStatus(0);
                        fixCurrentStatusLayout();
                        return false;
                    }
                } else if (STATUS.isLoadMoreStatus(this.mStatus) && this.mTargetOffset >= 0) {
                    setStatus(0);
                    fixCurrentStatusLayout();
                    return false;
                }
                if (STATUS.isRefreshStatus(this.mStatus)) {
                    if (!STATUS.isSwipingToRefresh(this.mStatus) && !STATUS.isReleaseToRefresh(this.mStatus)) {
                        return true;
                    }
                    if (((float) this.mTargetOffset) >= this.mRefreshTriggerOffset) {
                        setStatus(-2);
                    } else {
                        setStatus(-1);
                    }
                    fingerScroll(yDiff);
                    return true;
                } else if (!STATUS.isLoadMoreStatus(this.mStatus)) {
                    return true;
                } else {
                    if (!STATUS.isSwipingToLoadMore(this.mStatus) && !STATUS.isReleaseToLoadMore(this.mStatus)) {
                        return true;
                    }
                    if (((float) (-this.mTargetOffset)) >= this.mLoadMoreTriggerOffset) {
                        setStatus(2);
                    } else {
                        setStatus(1);
                    }
                    fingerScroll(yDiff);
                    return true;
                }
            case 5:
                int pointerId = MotionEventCompat.getPointerId(event, MotionEventCompat.getActionIndex(event));
                if (pointerId != -1) {
                    this.mActivePointerId = pointerId;
                }
                float motionEventY = getMotionEventY(event, this.mActivePointerId);
                this.mLastY = motionEventY;
                this.mInitDownY = motionEventY;
                float motionEventX = getMotionEventX(event, this.mActivePointerId);
                this.mLastX = motionEventX;
                this.mInitDownX = motionEventX;
                break;
            case 6:
                onSecondaryPointerUp(event);
                float motionEventY2 = getMotionEventY(event, this.mActivePointerId);
                this.mLastY = motionEventY2;
                this.mInitDownY = motionEventY2;
                float motionEventX2 = getMotionEventX(event, this.mActivePointerId);
                this.mLastX = motionEventX2;
                this.mInitDownX = motionEventX2;
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setDebug(boolean debug2) {
        this.mDebug = debug2;
    }

    public boolean isRefreshEnabled() {
        return this.mRefreshEnabled;
    }

    public void setRefreshEnabled(boolean enable) {
        this.mRefreshEnabled = enable;
    }

    public boolean isLoadMoreEnabled() {
        return this.mLoadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean enable) {
        this.mLoadMoreEnabled = enable;
    }

    public boolean isRefreshing() {
        return STATUS.isRefreshing(this.mStatus);
    }

    public boolean isLoadingMore() {
        return STATUS.isLoadingMore(this.mStatus);
    }

    public void setRefreshHeaderView(View view) {
        if (view instanceof SwipeRefreshTrigger) {
            if (!(this.mHeaderView == null || this.mHeaderView == view)) {
                removeView(this.mHeaderView);
            }
            if (this.mHeaderView != view) {
                this.mHeaderView = view;
                addView(view);
                return;
            }
            return;
        }
        Log.e(TAG, "Refresh header view must be an implement of SwipeRefreshTrigger");
    }

    public void setLoadMoreFooterView(View view) {
        if (view instanceof SwipeLoadMoreTrigger) {
            if (!(this.mFooterView == null || this.mFooterView == view)) {
                removeView(this.mFooterView);
            }
            if (this.mFooterView != view) {
                this.mFooterView = view;
                addView(this.mFooterView);
                return;
            }
            return;
        }
        Log.e(TAG, "Load more footer view must be an implement of SwipeLoadTrigger");
    }

    public void setSwipeStyle(int style) {
        this.mStyle = style;
        requestLayout();
    }

    public void setDragRatio(float dragRatio) {
        this.mDragRatio = dragRatio;
    }

    public void setRefreshTriggerOffset(int offset) {
        this.mRefreshTriggerOffset = (float) offset;
    }

    public void setLoadMoreTriggerOffset(int offset) {
        this.mLoadMoreTriggerOffset = (float) offset;
    }

    public void setRefreshFinalDragOffset(int offset) {
        this.mRefreshFinalDragOffset = (float) offset;
    }

    public void setLoadMoreFinalDragOffset(int offset) {
        this.mLoadMoreFinalDragOffset = (float) offset;
    }

    public void setSwipingToRefreshToDefaultScrollingDuration(int duration) {
        this.mSwipingToRefreshToDefaultScrollingDuration = duration;
    }

    public void setReleaseToRefreshingScrollingDuration(int duration) {
        this.mReleaseToRefreshToRefreshingScrollingDuration = duration;
    }

    public void setRefreshCompleteDelayDuration(int duration) {
        this.mRefreshCompleteDelayDuration = duration;
    }

    public void setRefreshCompleteToDefaultScrollingDuration(int duration) {
        this.mRefreshCompleteToDefaultScrollingDuration = duration;
    }

    public void setDefaultToRefreshingScrollingDuration(int duration) {
        this.mDefaultToRefreshingScrollingDuration = duration;
    }

    public void setSwipingToLoadMoreToDefaultScrollingDuration(int duration) {
        this.mSwipingToLoadMoreToDefaultScrollingDuration = duration;
    }

    public void setReleaseToLoadingMoreScrollingDuration(int duration) {
        this.mReleaseToLoadMoreToLoadingMoreScrollingDuration = duration;
    }

    public void setLoadMoreCompleteDelayDuration(int duration) {
        this.mLoadMoreCompleteDelayDuration = duration;
    }

    public void setLoadMoreCompleteToDefaultScrollingDuration(int duration) {
        this.mLoadMoreCompleteToDefaultScrollingDuration = duration;
    }

    public void setDefaultToLoadingMoreScrollingDuration(int duration) {
        this.mDefaultToLoadingMoreScrollingDuration = duration;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    public void setRefreshing(boolean refreshing) {
        if (!isRefreshEnabled() || this.mHeaderView == null) {
            KLog.i("----------setRefreshing------------");
            return;
        }
        this.mAutoLoading = refreshing;
        if (refreshing) {
            if (STATUS.isStatusDefault(this.mStatus)) {
                setStatus(-1);
                scrollDefaultToRefreshing();
            }
        } else if (STATUS.isRefreshing(this.mStatus)) {
            this.mRefreshCallback.onComplete();
            postDelayed(new Runnable() {
                public void run() {
                    SwipeToLoadLayout.this.scrollRefreshingToDefault();
                }
            }, (long) this.mRefreshCompleteDelayDuration);
        }
    }

    public void setLoadingMore(boolean loadingMore) {
        if (isLoadMoreEnabled() && this.mFooterView != null) {
            this.mAutoLoading = loadingMore;
            if (loadingMore) {
                if (STATUS.isStatusDefault(this.mStatus)) {
                    setStatus(1);
                    scrollDefaultToLoadingMore();
                }
            } else if (STATUS.isLoadingMore(this.mStatus)) {
                this.mLoadMoreCallback.onComplete();
                postDelayed(new Runnable() {
                    public void run() {
                        SwipeToLoadLayout.this.scrollLoadingMoreToDefault();
                    }
                }, (long) this.mLoadMoreCompleteDelayDuration);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean canChildScrollUp() {
        boolean z = false;
        if (VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTargetView, -1);
        }
        if (this.mTargetView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) this.mTargetView;
            if (absListView.getChildCount() <= 0 || (absListView.getFirstVisiblePosition() <= 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop())) {
                return false;
            }
            return true;
        }
        if (ViewCompat.canScrollVertically(this.mTargetView, -1) || this.mTargetView.getScrollY() > 0) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public boolean canChildScrollDown() {
        boolean z = false;
        if (VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTargetView, 1);
        }
        if (this.mTargetView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) this.mTargetView;
            if (absListView.getChildCount() <= 0 || (absListView.getLastVisiblePosition() >= absListView.getChildCount() - 1 && absListView.getChildAt(absListView.getChildCount() - 1).getBottom() <= absListView.getPaddingBottom())) {
                return false;
            }
            return true;
        }
        if (ViewCompat.canScrollVertically(this.mTargetView, 1) || this.mTargetView.getScrollY() < 0) {
            z = true;
        }
        return z;
    }

    private void layoutChildren() {
        int footerBottom;
        int targetTop;
        int headerTop;
        int measuredWidth = getMeasuredWidth();
        int height = getMeasuredHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        if (this.mTargetView != null) {
            if (this.mHeaderView != null) {
                View headerView = this.mHeaderView;
                MarginLayoutParams lp = (MarginLayoutParams) headerView.getLayoutParams();
                int headerLeft = paddingLeft + lp.leftMargin;
                switch (this.mStyle) {
                    case 0:
                        headerTop = ((lp.topMargin + paddingTop) - this.mHeaderHeight) + this.mHeaderOffset;
                        break;
                    case 1:
                        headerTop = ((lp.topMargin + paddingTop) - this.mHeaderHeight) + this.mHeaderOffset;
                        break;
                    case 2:
                        headerTop = paddingTop + lp.topMargin;
                        break;
                    case 3:
                        headerTop = ((lp.topMargin + paddingTop) - (this.mHeaderHeight / 2)) + (this.mHeaderOffset / 2);
                        break;
                    default:
                        headerTop = ((lp.topMargin + paddingTop) - this.mHeaderHeight) + this.mHeaderOffset;
                        break;
                }
                headerView.layout(headerLeft, headerTop, headerLeft + headerView.getMeasuredWidth(), headerTop + headerView.getMeasuredHeight());
            }
            if (this.mTargetView != null) {
                View targetView = this.mTargetView;
                MarginLayoutParams lp2 = (MarginLayoutParams) targetView.getLayoutParams();
                int targetLeft = paddingLeft + lp2.leftMargin;
                switch (this.mStyle) {
                    case 0:
                        targetTop = lp2.topMargin + paddingTop + this.mTargetOffset;
                        break;
                    case 1:
                        targetTop = paddingTop + lp2.topMargin;
                        break;
                    case 2:
                        targetTop = lp2.topMargin + paddingTop + this.mTargetOffset;
                        break;
                    case 3:
                        targetTop = lp2.topMargin + paddingTop + this.mTargetOffset;
                        break;
                    default:
                        targetTop = lp2.topMargin + paddingTop + this.mTargetOffset;
                        break;
                }
                targetView.layout(targetLeft, targetTop, targetLeft + targetView.getMeasuredWidth(), targetTop + targetView.getMeasuredHeight());
            }
            for (int i = 0; i < this.otherviews.size(); i++) {
                View view = (View) this.otherviews.valueAt(i);
                MarginLayoutParams lp3 = (MarginLayoutParams) view.getLayoutParams();
                int targetLeft2 = paddingLeft + lp3.leftMargin;
                int targetTop2 = paddingTop + lp3.topMargin;
                view.layout(targetLeft2, targetTop2, targetLeft2 + view.getMeasuredWidth(), targetTop2 + view.getMeasuredHeight());
            }
            if (this.mFooterView != null) {
                View footerView = this.mFooterView;
                MarginLayoutParams lp4 = (MarginLayoutParams) footerView.getLayoutParams();
                int footerLeft = paddingLeft + lp4.leftMargin;
                switch (this.mStyle) {
                    case 0:
                        footerBottom = ((height - paddingBottom) - lp4.bottomMargin) + this.mFooterHeight + this.mFooterOffset;
                        break;
                    case 1:
                        footerBottom = ((height - paddingBottom) - lp4.bottomMargin) + this.mFooterHeight + this.mFooterOffset;
                        break;
                    case 2:
                        footerBottom = (height - paddingBottom) - lp4.bottomMargin;
                        break;
                    case 3:
                        footerBottom = ((height - paddingBottom) - lp4.bottomMargin) + (this.mFooterHeight / 2) + (this.mFooterOffset / 2);
                        break;
                    default:
                        footerBottom = ((height - paddingBottom) - lp4.bottomMargin) + this.mFooterHeight + this.mFooterOffset;
                        break;
                }
                footerView.layout(footerLeft, footerBottom - footerView.getMeasuredHeight(), footerLeft + footerView.getMeasuredWidth(), footerBottom);
            }
            if (this.mStyle == 0 || this.mStyle == 1) {
                if (this.mHeaderView != null) {
                    this.mHeaderView.bringToFront();
                }
                if (this.mFooterView != null) {
                    this.mFooterView.bringToFront();
                }
            } else if ((this.mStyle == 2 || this.mStyle == 3) && this.mTargetView != null) {
                this.mTargetView.bringToFront();
            }
        }
    }

    private void fixCurrentStatusLayout() {
        if (STATUS.isRefreshing(this.mStatus)) {
            this.mTargetOffset = (int) (this.mRefreshTriggerOffset + 0.5f);
            this.mHeaderOffset = this.mTargetOffset;
            this.mFooterOffset = 0;
            layoutChildren();
            invalidate();
        } else if (STATUS.isStatusDefault(this.mStatus)) {
            this.mTargetOffset = 0;
            this.mHeaderOffset = 0;
            this.mFooterOffset = 0;
            layoutChildren();
            invalidate();
        } else if (STATUS.isLoadingMore(this.mStatus)) {
            this.mTargetOffset = -((int) (this.mLoadMoreTriggerOffset + 0.5f));
            this.mHeaderOffset = 0;
            this.mFooterOffset = this.mTargetOffset;
            layoutChildren();
            invalidate();
        }
    }

    private void fingerScroll(float yDiff) {
        float yScrolled = yDiff * this.mDragRatio;
        float tmpTargetOffset = yScrolled + ((float) this.mTargetOffset);
        if ((tmpTargetOffset > 0.0f && this.mTargetOffset < 0) || (tmpTargetOffset < 0.0f && this.mTargetOffset > 0)) {
            yScrolled = (float) (-this.mTargetOffset);
        }
        if (this.mRefreshFinalDragOffset >= this.mRefreshTriggerOffset && tmpTargetOffset > this.mRefreshFinalDragOffset) {
            yScrolled = this.mRefreshFinalDragOffset - ((float) this.mTargetOffset);
        } else if (this.mLoadMoreFinalDragOffset >= this.mLoadMoreTriggerOffset && (-tmpTargetOffset) > this.mLoadMoreFinalDragOffset) {
            yScrolled = (-this.mLoadMoreFinalDragOffset) - ((float) this.mTargetOffset);
        }
        if (STATUS.isRefreshStatus(this.mStatus)) {
            this.mRefreshCallback.onMove(this.mTargetOffset, false, false);
        } else if (STATUS.isLoadMoreStatus(this.mStatus)) {
            this.mLoadMoreCallback.onMove(this.mTargetOffset, false, false);
        }
        updateScroll(yScrolled);
    }

    /* access modifiers changed from: private */
    public void autoScroll(float yScrolled) {
        if (STATUS.isSwipingToRefresh(this.mStatus)) {
            this.mRefreshCallback.onMove(this.mTargetOffset, false, true);
        } else if (STATUS.isReleaseToRefresh(this.mStatus)) {
            this.mRefreshCallback.onMove(this.mTargetOffset, false, true);
        } else if (STATUS.isRefreshing(this.mStatus)) {
            this.mRefreshCallback.onMove(this.mTargetOffset, true, true);
        } else if (STATUS.isSwipingToLoadMore(this.mStatus)) {
            this.mLoadMoreCallback.onMove(this.mTargetOffset, false, true);
        } else if (STATUS.isReleaseToLoadMore(this.mStatus)) {
            this.mLoadMoreCallback.onMove(this.mTargetOffset, false, true);
        } else if (STATUS.isLoadingMore(this.mStatus)) {
            this.mLoadMoreCallback.onMove(this.mTargetOffset, true, true);
        }
        updateScroll(yScrolled);
    }

    private void updateScroll(float yScrolled) {
        if (yScrolled != 0.0f) {
            this.mTargetOffset = (int) (((float) this.mTargetOffset) + yScrolled);
            if (STATUS.isRefreshStatus(this.mStatus)) {
                this.mHeaderOffset = this.mTargetOffset;
                this.mFooterOffset = 0;
            } else if (STATUS.isLoadMoreStatus(this.mStatus)) {
                this.mFooterOffset = this.mTargetOffset;
                this.mHeaderOffset = 0;
            }
            if (this.mDebug) {
                Log.i(TAG, "mTargetOffset = " + this.mTargetOffset);
            }
            layoutChildren();
            invalidate();
        }
    }

    private void onActivePointerUp() {
        if (STATUS.isSwipingToRefresh(this.mStatus)) {
            scrollSwipingToRefreshToDefault();
        } else if (STATUS.isSwipingToLoadMore(this.mStatus)) {
            scrollSwipingToLoadMoreToDefault();
        } else if (STATUS.isReleaseToRefresh(this.mStatus)) {
            this.mRefreshCallback.onRelease();
            scrollReleaseToRefreshToRefreshing();
        } else if (STATUS.isReleaseToLoadMore(this.mStatus)) {
            this.mLoadMoreCallback.onRelease();
            scrollReleaseToLoadMoreToLoadingMore();
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.mActivePointerId) {
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex == 0 ? 1 : 0);
        }
    }

    private void scrollDefaultToRefreshing() {
        this.mAutoScroller.autoScroll((int) (this.mRefreshTriggerOffset + 0.5f), this.mDefaultToRefreshingScrollingDuration);
    }

    private void scrollDefaultToLoadingMore() {
        this.mAutoScroller.autoScroll(-((int) (this.mLoadMoreTriggerOffset + 0.5f)), this.mDefaultToLoadingMoreScrollingDuration);
    }

    private void scrollSwipingToRefreshToDefault() {
        this.mAutoScroller.autoScroll(-this.mHeaderOffset, this.mSwipingToRefreshToDefaultScrollingDuration);
    }

    private void scrollSwipingToLoadMoreToDefault() {
        this.mAutoScroller.autoScroll(-this.mFooterOffset, this.mSwipingToLoadMoreToDefaultScrollingDuration);
    }

    private void scrollReleaseToRefreshToRefreshing() {
        this.mAutoScroller.autoScroll(this.mHeaderHeight - this.mHeaderOffset, this.mReleaseToRefreshToRefreshingScrollingDuration);
    }

    private void scrollReleaseToLoadMoreToLoadingMore() {
        this.mAutoScroller.autoScroll((-this.mFooterOffset) - this.mFooterHeight, this.mReleaseToLoadMoreToLoadingMoreScrollingDuration);
    }

    /* access modifiers changed from: private */
    public void scrollRefreshingToDefault() {
        this.mAutoScroller.autoScroll(-this.mHeaderOffset, this.mRefreshCompleteToDefaultScrollingDuration);
    }

    /* access modifiers changed from: private */
    public void scrollLoadingMoreToDefault() {
        this.mAutoScroller.autoScroll(-this.mFooterOffset, this.mLoadMoreCompleteToDefaultScrollingDuration);
    }

    /* access modifiers changed from: private */
    public void autoScrollFinished() {
        int mLastStatus = this.mStatus;
        if (STATUS.isReleaseToRefresh(this.mStatus)) {
            setStatus(-3);
            fixCurrentStatusLayout();
            this.mRefreshCallback.onRefresh();
        } else if (STATUS.isRefreshing(this.mStatus)) {
            setStatus(0);
            fixCurrentStatusLayout();
            this.mRefreshCallback.onReset();
        } else if (STATUS.isSwipingToRefresh(this.mStatus)) {
            if (this.mAutoLoading) {
                this.mAutoLoading = false;
                setStatus(-3);
                fixCurrentStatusLayout();
                this.mRefreshCallback.onRefresh();
            } else {
                setStatus(0);
                fixCurrentStatusLayout();
                this.mRefreshCallback.onReset();
            }
        } else if (!STATUS.isStatusDefault(this.mStatus)) {
            if (STATUS.isSwipingToLoadMore(this.mStatus)) {
                if (this.mAutoLoading) {
                    this.mAutoLoading = false;
                    setStatus(3);
                    fixCurrentStatusLayout();
                    this.mLoadMoreCallback.onLoadMore();
                } else {
                    setStatus(0);
                    fixCurrentStatusLayout();
                    this.mLoadMoreCallback.onReset();
                }
            } else if (STATUS.isLoadingMore(this.mStatus)) {
                setStatus(0);
                fixCurrentStatusLayout();
                this.mLoadMoreCallback.onReset();
            } else if (STATUS.isReleaseToLoadMore(this.mStatus)) {
                setStatus(3);
                fixCurrentStatusLayout();
                this.mLoadMoreCallback.onLoadMore();
            } else {
                throw new IllegalStateException("illegal state: " + STATUS.getStatus(this.mStatus));
            }
        }
        if (this.mDebug) {
            Log.i(TAG, STATUS.getStatus(mLastStatus) + " -> " + STATUS.getStatus(this.mStatus));
        }
    }

    private boolean onCheckCanRefresh() {
        return this.mRefreshEnabled && !canChildScrollUp() && this.mHasHeaderView && this.mRefreshTriggerOffset > 0.0f;
    }

    private boolean onCheckCanLoadMore() {
        return this.mLoadMoreEnabled && !canChildScrollDown() && this.mHasFooterView && this.mLoadMoreTriggerOffset > 0.0f;
    }

    private float getMotionEventY(MotionEvent event, int activePointerId) {
        int index = MotionEventCompat.findPointerIndex(event, activePointerId);
        if (index < 0) {
            return -1.0f;
        }
        return MotionEventCompat.getY(event, index);
    }

    private float getMotionEventX(MotionEvent event, int activePointId) {
        int index = MotionEventCompat.findPointerIndex(event, activePointId);
        if (index < 0) {
            return -1.0f;
        }
        return MotionEventCompat.getX(event, index);
    }

    private void setStatus(int status) {
        this.mStatus = status;
        if (this.mDebug) {
            STATUS.printStatus(status);
        }
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 2) != 0;
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        this.mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        KLog.d("onNestedScroll..." + dyConsumed);
        if (this.needHeadScrollWithParent && this.canHeadScrollWithParentNow) {
            KLog.e(Boolean.valueOf(this.mTargetView.canScrollVertically(-1)));
            this.mTargetView.postDelayed(new Runnable() {
                public void run() {
                    if (!SwipeToLoadLayout.this.mTargetView.canScrollVertically(-1)) {
                        KLog.e(Boolean.valueOf(SwipeToLoadLayout.this.mTargetView.canScrollVertically(-1)));
                        SwipeToLoadLayout.this.scrollTo(0, 0);
                    }
                }
            }, 300);
        }
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop;
        boolean showTop;
        KLog.d("onNestedPreScroll...", Integer.valueOf(dy));
        if (this.needHeadScrollWithParent && this.canHeadScrollWithParentNow) {
            if (dy <= 0 || getScrollY() >= this.mHeaderView.getBottom() || !isRefreshing()) {
                hiddenTop = false;
            } else {
                hiddenTop = true;
            }
            if (dy >= 0 || getScrollY() <= 0 || this.mTargetView.canScrollVertically(-1) || !isRefreshing()) {
                showTop = false;
            } else {
                showTop = true;
            }
            KLog.e(TAG, "sroll-Y:" + getScrollY() + "/" + this.mHeaderView.getBottom());
            KLog.e(TAG, "canScrollVertically:" + this.mTargetView.canScrollVertically(-1));
            if (hiddenTop || showTop) {
                KLog.e(TAG, "hiddenTop/showTop:" + hiddenTop + "/" + showTop);
                scrollBy(0, Math.min(dy, this.mHeaderView.getBottom()));
                consumed[1] = dy;
            }
        }
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        KLog.e("onNestedFling..." + velocityY);
        return false;
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        KLog.e("onNestedPreFling..." + velocityY);
        return false;
    }

    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    public void onStopNestedScroll(View child) {
        this.mParentHelper.onStopNestedScroll(child);
    }

    public void scrollTo(int x, int y) {
        KLog.e(TAG, "scrollTo:" + y);
        if (y < 0) {
            y = 0;
        }
        if (y > this.mHeaderView.getBottom()) {
            y = this.mHeaderView.getBottom();
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    private int computeDuration(float velocityY) {
        int distance;
        if (velocityY > 0.0f) {
            distance = Math.abs(this.mHeaderView.getBottom() - getScrollY());
        } else {
            distance = Math.abs(getScrollY());
        }
        float velocityY2 = Math.abs(velocityY);
        if (velocityY2 > 0.0f) {
            return Math.round(1000.0f * (((float) distance) / velocityY2)) * 3;
        }
        return (int) ((1.0f + (((float) distance) / ((float) getHeight()))) * 150.0f);
    }

    private void animateScroll(float velocityY, int duration, boolean consumed) {
        int currentOffset = getScrollY();
        int topHeight = this.mHeaderView.getBottom();
        if (this.mOffsetAnimator == null) {
            this.mOffsetAnimator = new ValueAnimator();
            this.mOffsetAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            this.mOffsetAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Integer) {
                        SwipeToLoadLayout.this.scrollTo(0, ((Integer) animation.getAnimatedValue()).intValue());
                    }
                }
            });
        } else {
            this.mOffsetAnimator.cancel();
        }
        this.mOffsetAnimator.setDuration((long) Math.min(duration, ServiceErrorCode.YOU_AND_ME_IS_FRIEND));
        if (velocityY >= 0.0f) {
            this.mOffsetAnimator.setIntValues(new int[]{currentOffset, topHeight});
            this.mOffsetAnimator.start();
        } else if (!consumed) {
            this.mOffsetAnimator.setIntValues(new int[]{currentOffset, 0});
            this.mOffsetAnimator.start();
        }
    }

    public void fling(int velocityY) {
        this.mOverScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, this.mHeaderView.getBottom());
        invalidate();
    }

    public void computeScroll() {
        if (this.mOverScroller.computeScrollOffset()) {
            scrollTo(0, this.mOverScroller.getCurrY());
            invalidate();
        }
    }
}
