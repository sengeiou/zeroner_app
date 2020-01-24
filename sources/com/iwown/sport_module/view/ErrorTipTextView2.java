package com.iwown.sport_module.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.util.WindowUtil;

public class ErrorTipTextView2 extends LinearLayout {
    /* access modifiers changed from: private */
    public static int DURATION_IN = 0;
    /* access modifiers changed from: private */
    public static int DURATION_OUT = 0;
    /* access modifiers changed from: private */
    public static int DURATION_STOP = 0;
    public static final int ERROR = 2;
    public static final int OK = 0;
    private static final String TAG = "ErrorTipTextView";
    public static final int WARNING = 1;
    /* access modifiers changed from: private */
    public boolean isIning = false;
    /* access modifiers changed from: private */
    public boolean isOuting = false;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public ValueAnimator mErrorTipInAnim;
    private LinearLayout mErrorTipLayout;
    /* access modifiers changed from: private */
    public ValueAnimator mErrorTipOutAnim;
    private TextView mErrorTipTv;
    private int mHeight;
    /* access modifiers changed from: private */
    public boolean mIsErrorTipAnimating = false;
    /* access modifiers changed from: private */
    public OnDisplayEndListener mOnDisplayEndListener;
    /* access modifiers changed from: private */
    public OnTipClickedListener mOnTipClickedListener;
    private ImageView mTipIcon;
    private Vibrator mVibrator;
    private int mWidth;
    /* access modifiers changed from: private */
    public boolean needAutoAnim = false;
    private int tip_level = 1;

    public interface OnDisplayEndListener {
        void onDisplayEnd();
    }

    public interface OnTipClickedListener {
        void onTipClicked();
    }

    public boolean isErrorTipAnimating() {
        return this.mIsErrorTipAnimating;
    }

    public boolean isIning() {
        return this.isIning;
    }

    public void setIning(boolean ining) {
        this.isIning = ining;
    }

    public boolean isOuting() {
        return this.isOuting;
    }

    public void setOuting(boolean outing) {
        this.isOuting = outing;
    }

    public int getTip_level() {
        return this.tip_level;
    }

    public void setOnTipClickedListener(OnTipClickedListener onTipClickedListener) {
        this.mOnTipClickedListener = onTipClickedListener;
    }

    public ErrorTipTextView2(Context context) {
        super(context);
    }

    public void setDisplayEndListener(OnDisplayEndListener onDisplayEndListener) {
        this.mOnDisplayEndListener = onDisplayEndListener;
    }

    public ErrorTipTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /* access modifiers changed from: private */
    public void initEvent() {
        this.mErrorTipInAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animator) {
                ErrorTipTextView2.this.setTranslationY(((Float) animator.getAnimatedValue()).floatValue());
            }
        });
        this.mErrorTipInAnim.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animator) {
                ErrorTipTextView2.this.mIsErrorTipAnimating = true;
                ErrorTipTextView2.this.isIning = true;
            }

            public void onAnimationEnd(Animator animator) {
                ErrorTipTextView2.this.isIning = false;
                if (ErrorTipTextView2.this.needAutoAnim) {
                    ErrorTipTextView2.this.outDelay((long) ErrorTipTextView2.DURATION_STOP);
                }
            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });
        this.mErrorTipOutAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animator) {
                ErrorTipTextView2.this.setTranslationY(((Float) animator.getAnimatedValue()).floatValue());
            }
        });
        this.mErrorTipOutAnim.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animator) {
                ErrorTipTextView2.this.isOuting = true;
            }

            public void onAnimationEnd(Animator animator) {
                ErrorTipTextView2.this.isOuting = false;
                ErrorTipTextView2.this.mIsErrorTipAnimating = false;
                if (ErrorTipTextView2.this.mOnDisplayEndListener != null) {
                    ErrorTipTextView2.this.mOnDisplayEndListener.onDisplayEnd();
                }
            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
        this.mContext = context;
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sport_module_tip_text_layout, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sport_module_tip_text);
        DURATION_IN = a.getInt(R.styleable.sport_module_tip_text_sport_module_in_time, 100);
        DURATION_OUT = a.getInt(R.styleable.sport_module_tip_text_sport_module_out_time, 100);
        DURATION_STOP = a.getInt(R.styleable.sport_module_tip_text_sport_module_stop_time, 2000);
        a.recycle();
        this.mErrorTipTv = (TextView) findViewById(R.id.error_tip);
        this.mErrorTipLayout = (LinearLayout) findViewById(R.id.error_tip_layout);
        setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ErrorTipTextView2.this.mOnTipClickedListener != null) {
                    ErrorTipTextView2.this.mOnTipClickedListener.onTipClicked();
                }
            }
        });
        this.mTipIcon = (ImageView) findViewById(R.id.tip_icon);
        post(new Runnable() {
            public void run() {
                ErrorTipTextView2.this.mErrorTipInAnim = ValueAnimator.ofFloat(new float[]{0.0f, (float) DensityUtil.dip2px(ErrorTipTextView2.this.mContext, 10.0f)});
                ErrorTipTextView2.this.mErrorTipInAnim.setDuration((long) ErrorTipTextView2.DURATION_IN);
                ErrorTipTextView2.this.mErrorTipOutAnim = ValueAnimator.ofFloat(new float[]{0.0f, (float) ((-ErrorTipTextView2.this.getHeight()) - DensityUtil.dip2px(ErrorTipTextView2.this.mContext, 20.0f))});
                ErrorTipTextView2.this.mErrorTipOutAnim.setDuration((long) ErrorTipTextView2.DURATION_OUT);
                ErrorTipTextView2.this.initEvent();
            }
        });
    }

    public void startAnim() {
        if (!this.mIsErrorTipAnimating) {
            setVisibility(0);
            in();
            this.mVibrator.vibrate(300);
        }
    }

    public boolean isErrorLevelNow() {
        return this.tip_level == 2;
    }

    public void showErrorTip(String text) {
        setText(text, 2);
    }

    public void showWarningTip(String text) {
        setText(text, 1);
    }

    public void showOKTip(String text) {
        setText(text, 0);
    }

    public void setText(String text, int tip_level2) {
        this.mErrorTipTv.setText(text);
        this.tip_level = tip_level2;
        switch (tip_level2) {
            case 0:
                this.mTipIcon.setBackgroundResource(R.mipmap.right_3x);
                break;
            case 1:
                this.mTipIcon.setBackgroundResource(R.mipmap.attention_3x);
                break;
            case 2:
                this.mTipIcon.setBackgroundResource(R.mipmap.wrong_3x);
                break;
        }
        post(new Runnable() {
            public void run() {
                ErrorTipTextView2.this.mErrorTipOutAnim = ValueAnimator.ofFloat(new float[]{0.0f, (float) ((-ErrorTipTextView2.this.getHeight()) - DensityUtil.dip2px(ErrorTipTextView2.this.mContext, 20.0f))});
                ErrorTipTextView2.this.mErrorTipOutAnim.setDuration((long) ErrorTipTextView2.DURATION_OUT);
                ErrorTipTextView2.this.initEvent();
            }
        });
    }

    public void setText(String text) {
        this.mErrorTipTv.setText(text);
        int lines = getLinesByText(text);
        Log.i(TAG, String.format("cal lines : %d", new Object[]{Integer.valueOf(lines)}));
        setHeightAndWidth(lines);
    }

    public void setHeightAndWidth(int lines) {
        LayoutParams layoutParams = (LayoutParams) this.mErrorTipTv.getLayoutParams();
        layoutParams.height = this.mHeight + ((lines - 1) * 40);
        layoutParams.width = this.mWidth;
        Log.i(TAG, String.format("set height:%d", new Object[]{Integer.valueOf(layoutParams.height)}));
        this.mErrorTipTv.setLayoutParams(layoutParams);
        this.mErrorTipTv.requestLayout();
        int statusBarHeight = WindowUtil.getStatusBarHeight();
    }

    public void setInitialHeightAndWidth(int heightPX, int widthPX) {
        this.mHeight = heightPX;
        this.mWidth = widthPX;
        Log.i(TAG, String.format("init height:%d", new Object[]{Integer.valueOf(heightPX)}));
        LayoutParams layoutParams = (LayoutParams) this.mErrorTipTv.getLayoutParams();
        layoutParams.height = this.mHeight;
        layoutParams.width = this.mWidth;
        this.mErrorTipTv.setLayoutParams(layoutParams);
        this.mErrorTipTv.requestLayout();
    }

    public int getLinesByText(String text) {
        Paint paint = new Paint();
        paint.setTextSize(14.0f);
        float textWidth = paint.measureText(text);
        Log.i(TAG, String.format("text width:%f, view width:%d", new Object[]{Float.valueOf(textWidth), Integer.valueOf(this.mWidth)}));
        return (int) Math.ceil((double) (textWidth / 320.0f));
    }

    public void cancelAnims() {
        if (this.mIsErrorTipAnimating) {
            this.mErrorTipInAnim.cancel();
            this.mErrorTipOutAnim.cancel();
            this.isIning = false;
            this.isOuting = false;
        }
    }

    public void setViewBackground(int resId) {
        this.mErrorTipLayout.setBackground(getResources().getDrawable(resId));
    }

    public void in() {
        this.mErrorTipInAnim.start();
        setVisibility(0);
    }

    public void out() {
        this.mErrorTipOutAnim.setStartDelay(0);
        this.mErrorTipOutAnim.start();
        setVisibility(0);
    }

    public void outDelay(long delay) {
        this.mErrorTipOutAnim.setStartDelay(delay);
        this.mErrorTipOutAnim.start();
        setVisibility(0);
    }
}
