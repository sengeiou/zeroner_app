package com.tapadoo.alerter;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tapadoo.android.R;

public class Alert extends FrameLayout implements OnClickListener, AnimationListener, DismissCallbacks {
    private static final int CLEAN_UP_DELAY_MILLIS = 100;
    private static final long DISPLAY_TIME_IN_SECONDS = 3000;
    private long duration = DISPLAY_TIME_IN_SECONDS;
    private boolean enableIconPulse = true;
    private boolean enableInfiniteDuration;
    private boolean enableProgress;
    /* access modifiers changed from: private */
    public FrameLayout flBackground;
    private FrameLayout flClickShield;
    private ImageView ivIcon;
    private boolean marginSet;
    /* access modifiers changed from: private */
    public OnHideAlertListener onHideListener;
    private OnShowAlertListener onShowListener;
    /* access modifiers changed from: private */
    public ProgressBar pbProgress;
    private ViewGroup rlContainer;
    private Runnable runningAnimation;
    private Animation slideInAnimation;
    private Animation slideOutAnimation;
    private TextView tvText;
    private TextView tvTitle;
    private boolean vibrationEnabled = true;

    public Alert(@NonNull Context context) {
        super(context, null, R.attr.alertStyle);
        initView();
    }

    public Alert(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.attr.alertStyle);
        initView();
    }

    public Alert(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.alerter_alert_view, this);
        setHapticFeedbackEnabled(true);
        this.flBackground = (FrameLayout) findViewById(R.id.flAlertBackground);
        this.flClickShield = (FrameLayout) findViewById(R.id.flClickShield);
        this.ivIcon = (ImageView) findViewById(R.id.ivIcon);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvText = (TextView) findViewById(R.id.tvText);
        this.rlContainer = (ViewGroup) findViewById(R.id.rlContainer);
        this.pbProgress = (ProgressBar) findViewById(R.id.pbProgress);
        this.flBackground.setOnClickListener(this);
        this.slideInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alerter_slide_in_from_top);
        this.slideOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alerter_slide_out_to_top);
        this.slideInAnimation.setAnimationListener(this);
        setAnimation(this.slideInAnimation);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!this.marginSet) {
            this.marginSet = true;
            ((MarginLayoutParams) getLayoutParams()).topMargin = getContext().getResources().getDimensionPixelSize(R.dimen.alerter_alert_negative_margin_top);
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.slideInAnimation.setAnimationListener(null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return super.onTouchEvent(event);
    }

    public void onClick(View v) {
        hide();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.flBackground.setOnClickListener(listener);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(visibility);
        }
    }

    public void onAnimationStart(Animation animation) {
        if (!isInEditMode()) {
            if (this.vibrationEnabled) {
                performHapticFeedback(1);
            }
            setVisibility(0);
        }
    }

    public void onAnimationEnd(Animation animation) {
        if (this.enableIconPulse && this.ivIcon.getVisibility() == 0) {
            try {
                this.ivIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alerter_pulse));
            } catch (Exception ex) {
                Log.e(getClass().getSimpleName(), Log.getStackTraceString(ex));
            }
        }
        if (this.onShowListener != null) {
            this.onShowListener.onShow();
        }
        startHideAnimation();
    }

    @TargetApi(11)
    private void startHideAnimation() {
        if (!this.enableInfiniteDuration) {
            this.runningAnimation = new Runnable() {
                public void run() {
                    Alert.this.hide();
                }
            };
            postDelayed(this.runningAnimation, this.duration);
        }
        if (this.enableProgress && VERSION.SDK_INT >= 11) {
            ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{0, 100});
            valueAnimator.setDuration(this.duration);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    Alert.this.pbProgress.setProgress(((Integer) animation.getAnimatedValue()).intValue());
                }
            });
            valueAnimator.start();
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void hide() {
        try {
            this.slideOutAnimation.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    Alert.this.flBackground.setOnClickListener(null);
                    Alert.this.flBackground.setClickable(false);
                }

                public void onAnimationEnd(Animation animation) {
                    Alert.this.removeFromParent();
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            startAnimation(this.slideOutAnimation);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), Log.getStackTraceString(ex));
        }
    }

    /* access modifiers changed from: private */
    public void removeFromParent() {
        postDelayed(new Runnable() {
            public void run() {
                try {
                    if (Alert.this.getParent() == null) {
                        Log.e(getClass().getSimpleName(), "getParent() returning Null");
                        return;
                    }
                    try {
                        ((ViewGroup) Alert.this.getParent()).removeView(Alert.this);
                        if (Alert.this.onHideListener != null) {
                            Alert.this.onHideListener.onHide();
                        }
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Cannot remove from parent layout");
                    }
                } catch (Exception ex) {
                    Log.e(getClass().getSimpleName(), Log.getStackTraceString(ex));
                }
            }
        }, 100);
    }

    public void setAlertBackgroundColor(@ColorInt int color) {
        this.flBackground.setBackgroundColor(color);
    }

    public void setAlertBackgroundResource(@DrawableRes int resource) {
        this.flBackground.setBackgroundResource(resource);
    }

    public void setAlertBackgroundDrawable(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            this.flBackground.setBackground(drawable);
        } else {
            this.flBackground.setBackgroundDrawable(drawable);
        }
    }

    public void setTitle(@StringRes int titleId) {
        setTitle(getContext().getString(titleId));
    }

    public void setText(@StringRes int textId) {
        setText(getContext().getString(textId));
    }

    public int getContentGravity() {
        return ((LayoutParams) this.rlContainer.getLayoutParams()).gravity;
    }

    public void setContentGravity(int contentGravity) {
        ((LayoutParams) this.rlContainer.getLayoutParams()).gravity = contentGravity;
        this.rlContainer.requestLayout();
    }

    public void disableOutsideTouch() {
        this.flClickShield.setClickable(true);
    }

    public FrameLayout getAlertBackground() {
        return this.flBackground;
    }

    public TextView getTitle() {
        return this.tvTitle;
    }

    public void setTitle(@NonNull String title) {
        if (!TextUtils.isEmpty(title)) {
            this.tvTitle.setVisibility(0);
            this.tvTitle.setText(title);
        }
    }

    public void setTitleAppearance(@StyleRes int textAppearance) {
        if (VERSION.SDK_INT >= 23) {
            this.tvTitle.setTextAppearance(textAppearance);
        } else {
            this.tvTitle.setTextAppearance(this.tvTitle.getContext(), textAppearance);
        }
    }

    public void setTitleTypeface(@NonNull Typeface typeface) {
        this.tvTitle.setTypeface(typeface);
    }

    public void setTextTypeface(@NonNull Typeface typeface) {
        this.tvText.setTypeface(typeface);
    }

    public TextView getText() {
        return this.tvText;
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.tvText.setVisibility(0);
            this.tvText.setText(text);
        }
    }

    public void setTextAppearance(@StyleRes int textAppearance) {
        if (VERSION.SDK_INT >= 23) {
            this.tvText.setTextAppearance(textAppearance);
        } else {
            this.tvText.setTextAppearance(this.tvText.getContext(), textAppearance);
        }
    }

    public ImageView getIcon() {
        return this.ivIcon;
    }

    public void setIcon(@DrawableRes int iconId) {
        this.ivIcon.setImageDrawable(VectorDrawableCompat.create(getContext().getResources(), iconId, null));
    }

    public void setIcon(@NonNull Bitmap bitmap) {
        this.ivIcon.setImageBitmap(bitmap);
    }

    public void setIcon(@NonNull Drawable drawable) {
        this.ivIcon.setImageDrawable(drawable);
    }

    public void showIcon(boolean showIcon) {
        this.ivIcon.setVisibility(showIcon ? 0 : 8);
    }

    public void enableSwipeToDismiss() {
        this.flBackground.setOnTouchListener(new SwipeDismissTouchListener(this.flBackground, null, this));
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration2) {
        this.duration = duration2;
    }

    public void pulseIcon(boolean shouldPulse) {
        this.enableIconPulse = shouldPulse;
    }

    public void setEnableInfiniteDuration(boolean enableInfiniteDuration2) {
        this.enableInfiniteDuration = enableInfiniteDuration2;
    }

    public void setEnableProgress(boolean enableProgress2) {
        this.enableProgress = enableProgress2;
    }

    public void setOnShowListener(@NonNull OnShowAlertListener listener) {
        this.onShowListener = listener;
    }

    public void setOnHideListener(@NonNull OnHideAlertListener listener) {
        this.onHideListener = listener;
    }

    public void setVibrationEnabled(boolean vibrationEnabled2) {
        this.vibrationEnabled = vibrationEnabled2;
    }

    public boolean canDismiss(Object token) {
        return true;
    }

    public void onDismiss(View view, Object token) {
        this.flClickShield.removeView(this.flBackground);
    }

    public void onTouch(View view, boolean touch) {
        if (touch) {
            removeCallbacks(this.runningAnimation);
        } else {
            startHideAnimation();
        }
    }
}
