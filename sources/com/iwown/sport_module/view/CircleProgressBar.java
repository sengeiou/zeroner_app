package com.iwown.sport_module.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;

public class CircleProgressBar extends View {
    private int TOTAL_WITH = 100;
    private TextPaint cicleNumtextPaint;
    private int circle_num_color = ViewCompat.MEASURED_STATE_MASK;
    private ValueAnimator mArcAnim;
    private Paint mBottomCirclePaint;
    private int mCircle_thick;
    private int mComplete_color;
    private Context mContext;
    private Typeface mDincond_bold_font;
    private int mHeight;
    private double mNumber_size_in_circle;
    private double mPersent_char_size;
    /* access modifiers changed from: private */
    public float mProgress;
    private int mProgress0_color;
    private RectF mRectF;
    private SweepGradient mSweepGradient;
    private Paint mTopShaderCircle;
    private int mWidth;

    public CircleProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.TOTAL_WITH = DensityUtil.dip2px(context, 50.0f);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sport_module_CircleProgressBar);
        this.mProgress0_color = a.getColor(R.styleable.sport_module_CircleProgressBar_sport_module_progress0_color1, -16776961);
        this.mComplete_color = a.getColor(R.styleable.sport_module_CircleProgressBar_sport_module_complete_color1, -16711936);
        this.mCircle_thick = a.getDimensionPixelSize(R.styleable.sport_module_CircleProgressBar_sport_module_circle_thick, 10);
        a.recycle();
        this.mDincond_bold_font = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "DINPRO-MEDIUM.ttf");
        this.cicleNumtextPaint = new TextPaint();
        this.cicleNumtextPaint.setAntiAlias(true);
        this.cicleNumtextPaint.setDither(true);
        this.cicleNumtextPaint.setTypeface(this.mDincond_bold_font);
        this.cicleNumtextPaint.setTextAlign(Align.CENTER);
        this.cicleNumtextPaint.setColor(this.circle_num_color);
        this.mBottomCirclePaint = new Paint();
        this.mBottomCirclePaint.setAntiAlias(true);
        this.mBottomCirclePaint.setDither(true);
        this.mBottomCirclePaint.setStyle(Style.STROKE);
        this.mBottomCirclePaint.setStrokeWidth((float) this.mCircle_thick);
        this.mBottomCirclePaint.setColor(this.mProgress0_color);
        this.mTopShaderCircle = new Paint();
        this.mTopShaderCircle.setAntiAlias(true);
        this.mTopShaderCircle.setDither(true);
        this.mTopShaderCircle.setStyle(Style.STROKE);
        this.mTopShaderCircle.setStrokeWidth((float) this.mCircle_thick);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = 0;
        this.mHeight = 0;
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        if (modeW == Integer.MIN_VALUE) {
            this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (modeW == 1073741824) {
            this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (modeW == 0) {
            this.mWidth = this.TOTAL_WITH;
        }
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        if (modeH == Integer.MIN_VALUE) {
            this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == 1073741824) {
            this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == 0) {
            this.mHeight = this.TOTAL_WITH;
        }
        int min = Math.min(this.mHeight, this.mWidth);
        this.mWidth = min;
        this.mHeight = min;
        this.mSweepGradient = new SweepGradient(((float) this.mWidth) / 2.0f, ((float) this.mHeight) / 2.0f, new int[]{-1, this.mComplete_color}, null);
        this.mRectF = new RectF(((float) this.mCircle_thick) / 2.0f, ((float) this.mCircle_thick) / 2.0f, ((float) this.mWidth) - (((float) this.mCircle_thick) / 2.0f), ((float) this.mHeight) - (((float) this.mCircle_thick) / 2.0f));
        this.mNumber_size_in_circle = (double) (((float) this.mWidth) * 0.33333334f);
        this.mPersent_char_size = this.mNumber_size_in_circle * 0.6666666666666666d;
        this.cicleNumtextPaint.setTextSize((float) this.mNumber_size_in_circle);
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBottomCir(canvas);
        drawArc(canvas);
        drawTextInCircle(canvas);
    }

    private void drawTextInCircle(Canvas canvas) {
        this.cicleNumtextPaint.setTextSize((float) this.mNumber_size_in_circle);
        int[] wh = getTextWidthAndHigh(this.cicleNumtextPaint, ((int) (this.mProgress * 100.0f)) + "");
        this.cicleNumtextPaint.setTextSize((float) this.mPersent_char_size);
        int[] percentWH = getTextWidthAndHigh(this.cicleNumtextPaint, "%");
        this.cicleNumtextPaint.setTextSize((float) this.mNumber_size_in_circle);
        canvas.drawText(((int) (this.mProgress * 100.0f)) + "", (float) (((double) (((float) this.mWidth) / 2.0f)) - (((double) percentWH[0]) / 2.0d)), (float) (((double) (((float) this.mHeight) / 2.0f)) + (((double) wh[1]) / 2.0d)), this.cicleNumtextPaint);
        this.cicleNumtextPaint.setTextSize((float) this.mPersent_char_size);
        canvas.drawText("%", (float) (((double) (((float) this.mWidth) / 2.0f)) + (((double) wh[0]) / 2.0d) + 5.0d), (float) (((double) (((float) this.mHeight) / 2.0f)) + (((double) wh[1]) / 2.0d)), this.cicleNumtextPaint);
    }

    private void drawBottomCir(Canvas canvas) {
        canvas.drawOval(this.mRectF, this.mBottomCirclePaint);
    }

    private void drawArc(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.mRectF, 0.0f, this.mProgress * 360.0f, false, this.mTopShaderCircle);
    }

    public void startProgressAnim(float progress) {
        startArcAnim(progress);
    }

    private void startArcAnim(float progress) {
        this.mArcAnim = ValueAnimator.ofFloat(new float[]{0.0f, progress});
        this.mArcAnim.setDuration(1000);
        this.mArcAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mArcAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animator) {
                CircleProgressBar.this.mProgress = ((Float) animator.getAnimatedValue()).floatValue();
                CircleProgressBar.this.postInvalidate();
            }
        });
        this.mArcAnim.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });
        this.mArcAnim.start();
    }

    public void setProgress0_color(int progress0_color) {
        this.mProgress0_color = progress0_color;
        this.mBottomCirclePaint.setColor(this.mProgress0_color);
    }

    public void setComplete_color(int complete_color) {
        this.mComplete_color = complete_color;
        this.mTopShaderCircle.setColor(this.mComplete_color);
    }

    private int getTextWidth(Paint paint, String str) {
        int w = 0;
        float trueW = 0.0f;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                w += (int) Math.ceil((double) widths[j]);
                trueW += widths[j];
            }
        }
        Log.e("licl", "trueW: " + trueW);
        return w;
    }

    private int[] getTextWidthAndHigh(Paint textPaint, String text) {
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        return new int[]{rect.width(), rect.height()};
    }

    public void setCircle_num_color(int circle_num_color2) {
        this.circle_num_color = circle_num_color2;
        this.cicleNumtextPaint.setColor(circle_num_color2);
    }
}
