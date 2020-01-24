package com.tapadoo.alerter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;

class SwipeDismissTouchListener implements OnTouchListener {
    private final long mAnimationTime;
    /* access modifiers changed from: private */
    public final DismissCallbacks mCallbacks;
    private float mDownX;
    private float mDownY;
    private final int mMinFlingVelocity;
    private final int mSlop;
    private boolean mSwiping;
    private int mSwipingSlop;
    /* access modifiers changed from: private */
    public Object mToken;
    private float mTranslationX;
    private VelocityTracker mVelocityTracker;
    /* access modifiers changed from: private */
    public final View mView;
    private int mViewWidth = 1;

    interface DismissCallbacks {
        boolean canDismiss(Object obj);

        void onDismiss(View view, Object obj);

        void onTouch(View view, boolean z);
    }

    SwipeDismissTouchListener(View view, Object token, DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(view.getContext());
        this.mSlop = vc.getScaledTouchSlop();
        this.mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        this.mAnimationTime = (long) view.getContext().getResources().getInteger(17694720);
        this.mView = view;
        this.mToken = token;
        this.mCallbacks = callbacks;
    }

    @RequiresApi(api = 12)
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i;
        motionEvent.offsetLocation(this.mTranslationX, 0.0f);
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mView.getWidth();
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mDownX = motionEvent.getRawX();
                this.mDownY = motionEvent.getRawY();
                if (this.mCallbacks.canDismiss(this.mToken)) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                    this.mVelocityTracker.addMovement(motionEvent);
                }
                this.mCallbacks.onTouch(view, true);
                return false;
            case 1:
                if (this.mVelocityTracker != null) {
                    float deltaX = motionEvent.getRawX() - this.mDownX;
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float velocityX = this.mVelocityTracker.getXVelocity();
                    float absVelocityX = Math.abs(velocityX);
                    float absVelocityY = Math.abs(this.mVelocityTracker.getYVelocity());
                    boolean dismiss = false;
                    boolean dismissRight = false;
                    if (Math.abs(deltaX) > ((float) (this.mViewWidth / 2)) && this.mSwiping) {
                        dismiss = true;
                        dismissRight = deltaX > 0.0f;
                    } else if (((float) this.mMinFlingVelocity) <= absVelocityX && absVelocityY < absVelocityX && this.mSwiping) {
                        dismiss = ((velocityX > 0.0f ? 1 : (velocityX == 0.0f ? 0 : -1)) < 0) == ((deltaX > 0.0f ? 1 : (deltaX == 0.0f ? 0 : -1)) < 0);
                        dismissRight = this.mVelocityTracker.getXVelocity() > 0.0f;
                    }
                    if (dismiss) {
                        this.mView.animate().translationX(dismissRight ? (float) this.mViewWidth : (float) (-this.mViewWidth)).alpha(0.0f).setDuration(this.mAnimationTime).setListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animation) {
                                SwipeDismissTouchListener.this.performDismiss();
                            }
                        });
                    } else if (this.mSwiping) {
                        this.mView.animate().translationX(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener(null);
                        this.mCallbacks.onTouch(view, false);
                    }
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    this.mTranslationX = 0.0f;
                    this.mDownX = 0.0f;
                    this.mDownY = 0.0f;
                    this.mSwiping = false;
                    break;
                }
                break;
            case 2:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(motionEvent);
                    float deltaX2 = motionEvent.getRawX() - this.mDownX;
                    float deltaY = motionEvent.getRawY() - this.mDownY;
                    if (Math.abs(deltaX2) > ((float) this.mSlop) && Math.abs(deltaY) < Math.abs(deltaX2) / 2.0f) {
                        this.mSwiping = true;
                        if (deltaX2 > 0.0f) {
                            i = this.mSlop;
                        } else {
                            i = -this.mSlop;
                        }
                        this.mSwipingSlop = i;
                        this.mView.getParent().requestDisallowInterceptTouchEvent(true);
                        MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                        cancelEvent.setAction((motionEvent.getActionIndex() << 8) | 3);
                        this.mView.onTouchEvent(cancelEvent);
                        cancelEvent.recycle();
                    }
                    if (this.mSwiping) {
                        this.mTranslationX = deltaX2;
                        this.mView.setTranslationX(deltaX2 - ((float) this.mSwipingSlop));
                        this.mView.setAlpha(Math.max(0.0f, Math.min(1.0f, 1.0f - ((2.0f * Math.abs(deltaX2)) / ((float) this.mViewWidth)))));
                        return true;
                    }
                }
                break;
            case 3:
                if (this.mVelocityTracker != null) {
                    this.mView.animate().translationX(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener(null);
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    this.mTranslationX = 0.0f;
                    this.mDownX = 0.0f;
                    this.mDownY = 0.0f;
                    this.mSwiping = false;
                    break;
                }
                break;
            default:
                view.performClick();
                return false;
        }
        return false;
    }

    /* access modifiers changed from: private */
    @RequiresApi(api = 11)
    public void performDismiss() {
        final LayoutParams lp = this.mView.getLayoutParams();
        final int originalHeight = this.mView.getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{originalHeight, 1}).setDuration(this.mAnimationTime);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeDismissTouchListener.this.mCallbacks.onDismiss(SwipeDismissTouchListener.this.mView, SwipeDismissTouchListener.this.mToken);
                SwipeDismissTouchListener.this.mView.setAlpha(1.0f);
                SwipeDismissTouchListener.this.mView.setTranslationX(0.0f);
                lp.height = originalHeight;
                SwipeDismissTouchListener.this.mView.setLayoutParams(lp);
            }
        });
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                SwipeDismissTouchListener.this.mView.setLayoutParams(lp);
            }
        });
        animator.start();
    }
}
