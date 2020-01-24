package com.iwown.my_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.iwown.my_module.R;
import com.iwown.my_module.utility.StatusbarHelper;

public class ErrorTipTextView extends LinearLayout {
    private static int DURATION_IN = 0;
    private static int DURATION_OUT = 0;
    /* access modifiers changed from: private */
    public static int DURATION_STOP = 0;
    private static final String TAG = "ErrorTipTextView";
    private TranslateAnimation mErrorTipInAnim;
    private LinearLayout mErrorTipLayout;
    /* access modifiers changed from: private */
    public TranslateAnimation mErrorTipOutAnim;
    private TextView mErrorTipTv;
    private int mHeight;
    /* access modifiers changed from: private */
    public boolean mIsErrorTipAnimating = false;
    /* access modifiers changed from: private */
    public OnDisplayEndListener mOnDisplayEndListener;
    private Vibrator mVibrator;
    private int mWidth;

    public interface OnDisplayEndListener {
        void onDisplayEnd();
    }

    public ErrorTipTextView(Context context) {
        super(context);
    }

    public void setDisplayEndListener(OnDisplayEndListener onDisplayEndListener) {
        this.mOnDisplayEndListener = onDisplayEndListener;
    }

    public ErrorTipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
        initEvent(context, attrs);
    }

    private void initEvent(Context context, AttributeSet attrs) {
        this.mErrorTipInAnim.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                ErrorTipTextView.this.mIsErrorTipAnimating = true;
            }

            public void onAnimationEnd(Animation animation) {
                ErrorTipTextView.this.mErrorTipOutAnim.setStartOffset((long) ErrorTipTextView.DURATION_STOP);
                ErrorTipTextView.this.startAnimation(ErrorTipTextView.this.mErrorTipOutAnim);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mErrorTipOutAnim.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                ErrorTipTextView.this.mIsErrorTipAnimating = true;
            }

            public void onAnimationEnd(Animation animation) {
                ErrorTipTextView.this.setVisibility(8);
                ErrorTipTextView.this.mIsErrorTipAnimating = false;
                if (ErrorTipTextView.this.mOnDisplayEndListener != null) {
                    ErrorTipTextView.this.mOnDisplayEndListener.onDisplayEnd();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.my_module_tip_text_layout, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.tip_text);
        DURATION_IN = a.getInt(R.styleable.tip_text_in_time, 100);
        DURATION_OUT = a.getInt(R.styleable.tip_text_out_time, 100);
        DURATION_STOP = a.getInt(R.styleable.tip_text_stop_time, 2000);
        a.recycle();
        this.mErrorTipTv = (TextView) findViewById(R.id.error_tip);
        this.mErrorTipLayout = (LinearLayout) findViewById(R.id.error_tip_layout);
        this.mErrorTipInAnim = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        this.mErrorTipInAnim.setDuration((long) DURATION_IN);
        this.mErrorTipInAnim.setFillAfter(true);
        this.mErrorTipOutAnim = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        this.mErrorTipOutAnim.setDuration((long) DURATION_OUT);
        setAnimation(this.mErrorTipInAnim);
        setAnimation(this.mErrorTipOutAnim);
    }

    public void startAnim() {
        if (!this.mIsErrorTipAnimating) {
            setVisibility(0);
            startAnimation(this.mErrorTipInAnim);
            this.mVibrator.vibrate(300);
        }
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
        int statusBarHeight = StatusbarHelper.getStatusBarHeight();
        if (lines > 1) {
            setTranslationY((float) ((-statusBarHeight) - 20));
        } else {
            setTranslationY((float) (-statusBarHeight));
        }
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
        }
    }

    public void setViewBackground(int resId) {
        this.mErrorTipLayout.setBackground(getResources().getDrawable(resId));
    }
}
