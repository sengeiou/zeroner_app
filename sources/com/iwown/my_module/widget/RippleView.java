package com.iwown.my_module.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import com.iwown.my_module.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

@SuppressLint({"ClickableViewAccessibility"})
public class RippleView extends Button {
    private float mAlphaFactor;
    private boolean mAnimationIsCancel;
    private float mDensity;
    private float mDownX;
    private float mDownY;
    private boolean mHover;
    /* access modifiers changed from: private */
    public boolean mIsAnimating;
    private float mMaxRadius;
    private Paint mPaint;
    private Path mPath;
    private RadialGradient mRadialGradient;
    private float mRadius;
    private ObjectAnimator mRadiusAnimator;
    private Rect mRect;
    private int mRippleColor;

    private int dp(int dp) {
        return (int) ((((float) dp) * this.mDensity) + 0.5f);
    }

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIsAnimating = false;
        this.mHover = true;
        this.mPath = new Path();
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        this.mRippleColor = a.getColor(R.styleable.RippleView_rippleColor, this.mRippleColor);
        this.mAlphaFactor = a.getFloat(R.styleable.RippleView_alphaFactor, this.mAlphaFactor);
        this.mHover = a.getBoolean(R.styleable.RippleView_hover, this.mHover);
        a.recycle();
    }

    public void init() {
        this.mDensity = getContext().getResources().getDisplayMetrics().density;
        this.mPaint = new Paint(1);
        this.mPaint.setAlpha(100);
        setRippleColor(ViewCompat.MEASURED_STATE_MASK, 0.2f);
    }

    public void setRippleColor(int rippleColor, float alphaFactor) {
        this.mRippleColor = rippleColor;
        this.mAlphaFactor = alphaFactor;
    }

    public void setHover(boolean enabled) {
        this.mHover = enabled;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mMaxRadius = (float) Math.sqrt((double) ((w * w) + (h * h)));
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean z = false;
        Log.d("TouchEvent", String.valueOf(event.getActionMasked()));
        Log.d("mIsAnimating", String.valueOf(this.mIsAnimating));
        Log.d("mAnimationIsCancel", String.valueOf(this.mAnimationIsCancel));
        boolean superResult = super.onTouchEvent(event);
        if (event.getActionMasked() == 0 && isEnabled() && this.mHover) {
            this.mRect = new Rect(getLeft(), getTop(), getRight(), getBottom());
            this.mAnimationIsCancel = false;
            this.mDownX = event.getX();
            this.mDownY = event.getY();
            this.mRadiusAnimator = ObjectAnimator.ofFloat((Object) this, "radius", 0.0f, (float) dp(50)).setDuration(400);
            this.mRadiusAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            this.mRadiusAnimator.start();
            if (!superResult) {
                return true;
            }
        } else if (event.getActionMasked() == 2 && isEnabled() && this.mHover) {
            this.mDownX = event.getX();
            this.mDownY = event.getY();
            if (!this.mRect.contains(getLeft() + ((int) event.getX()), getTop() + ((int) event.getY()))) {
                z = true;
            }
            this.mAnimationIsCancel = z;
            if (z) {
                setRadius(0.0f);
            } else {
                setRadius((float) dp(50));
            }
            if (!superResult) {
                return true;
            }
        } else if (event.getActionMasked() == 1 && !this.mAnimationIsCancel && isEnabled()) {
            this.mDownX = event.getX();
            this.mDownY = event.getY();
            float targetRadius = Math.max((float) Math.sqrt((double) ((this.mDownX * this.mDownX) + (this.mDownY * this.mDownY))), this.mMaxRadius);
            if (this.mIsAnimating) {
                this.mRadiusAnimator.cancel();
            }
            this.mRadiusAnimator = ObjectAnimator.ofFloat((Object) this, "radius", (float) dp(50), targetRadius);
            this.mRadiusAnimator.setDuration(500);
            this.mRadiusAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            this.mRadiusAnimator.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    RippleView.this.mIsAnimating = true;
                }

                public void onAnimationEnd(Animator animator) {
                    RippleView.this.setRadius(0.0f);
                    ViewHelper.setAlpha(RippleView.this, 1.0f);
                    RippleView.this.mIsAnimating = false;
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            this.mRadiusAnimator.start();
            if (!superResult) {
                return true;
            }
        }
        return superResult;
    }

    public int adjustAlpha(int color, float factor) {
        return Color.argb(Math.round(((float) Color.alpha(color)) * factor), Color.red(color), Color.green(color), Color.blue(color));
    }

    public void setRadius(float radius) {
        this.mRadius = radius;
        if (this.mRadius > 0.0f) {
            this.mRadialGradient = new RadialGradient(this.mDownX, this.mDownY, this.mRadius, adjustAlpha(this.mRippleColor, this.mAlphaFactor), this.mRippleColor, TileMode.MIRROR);
            this.mPaint.setShader(this.mRadialGradient);
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            canvas.save(2);
            this.mPath.reset();
            this.mPath.addCircle(this.mDownX, this.mDownY, this.mRadius, Direction.CW);
            canvas.clipPath(this.mPath);
            canvas.restore();
            canvas.drawCircle(this.mDownX, this.mDownY, this.mRadius, this.mPaint);
        }
    }
}
