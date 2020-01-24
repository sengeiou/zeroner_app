package com.iwown.device_module.common.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.LinearInterpolator;
import com.google.common.primitives.Ints;
import java.text.DecimalFormat;

public class HorizontalProgressBar extends View {
    private int bgColor = -1972760;
    private Paint bgPaint;
    /* access modifiers changed from: private */
    public float currentProgress;
    private int duration = 1000;
    private float lastProgress = 0.0f;
    private int mHeight;
    private float mProgress;
    private int mViewHeight;
    /* access modifiers changed from: private */
    public int mWidth;
    /* access modifiers changed from: private */
    public float moveDis;
    private Path path = new Path();
    private ValueAnimator progressAnimator;
    private int progressColor = -627950;
    /* access modifiers changed from: private */
    public ProgressListener progressListener;
    private int progressMarginTop;
    private Paint progressPaint;
    private int progressPaintWidth;
    private RectF rectF = new RectF();
    private int roundRectRadius;
    private int startDelay = 500;
    private Paint textPaint;
    private int textPaintSize;
    private Rect textRect = new Rect();
    /* access modifiers changed from: private */
    public String textString = "0";
    private int tipHeight;
    private Paint tipPaint;
    private int tipPaintWidth;
    /* access modifiers changed from: private */
    public int tipWidth;
    private int triangleHeight;

    public interface ProgressListener {
        void currentProgressListener(float f);
    }

    public HorizontalProgressBar(Context context) {
        super(context);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initPaint();
    }

    private void init() {
        this.progressPaintWidth = dp2px(4);
        this.tipHeight = dp2px(15);
        this.tipWidth = dp2px(30);
        this.tipPaintWidth = dp2px(1);
        this.triangleHeight = dp2px(3);
        this.roundRectRadius = dp2px(2);
        this.textPaintSize = sp2px(10);
        this.progressMarginTop = dp2px(8);
        this.mViewHeight = this.tipHeight + this.tipPaintWidth + this.triangleHeight + this.progressPaintWidth + this.progressMarginTop;
    }

    private void initPaint() {
        this.bgPaint = getPaint(this.progressPaintWidth, this.bgColor, Style.STROKE);
        this.progressPaint = getPaint(this.progressPaintWidth, this.progressColor, Style.STROKE);
        this.tipPaint = getPaint(this.tipPaintWidth, this.progressColor, Style.FILL);
        initTextPaint();
    }

    private void initTextPaint() {
        this.textPaint = new Paint(1);
        this.textPaint.setTextSize((float) this.textPaintSize);
        this.textPaint.setColor(-1);
        this.textPaint.setTextAlign(Align.CENTER);
        this.textPaint.setAntiAlias(true);
    }

    private Paint getPaint(int strokeWidth, int color, Style style) {
        Paint paint = new Paint(1);
        paint.setStrokeWidth((float) strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Cap.ROUND);
        paint.setStyle(style);
        return paint;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(MeasureSpec.getMode(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec)), measureHeight(MeasureSpec.getMode(heightMeasureSpec), MeasureSpec.getSize(heightMeasureSpec)));
    }

    private int measureWidth(int mode, int width) {
        switch (mode) {
            case Ints.MAX_POWER_OF_TWO /*1073741824*/:
                this.mWidth = width;
                break;
        }
        return this.mWidth;
    }

    private int measureHeight(int mode, int height) {
        switch (mode) {
            case Integer.MIN_VALUE:
            case 0:
                this.mHeight = this.mViewHeight;
                break;
            case Ints.MAX_POWER_OF_TWO /*1073741824*/:
                this.mHeight = height;
                break;
        }
        return this.mHeight;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine((float) getPaddingLeft(), (float) (this.tipHeight + this.progressMarginTop), (float) getWidth(), (float) (this.tipHeight + this.progressMarginTop), this.bgPaint);
        canvas.drawLine((float) getPaddingLeft(), (float) (this.tipHeight + this.progressMarginTop), this.currentProgress, (float) (this.tipHeight + this.progressMarginTop), this.progressPaint);
        drawTipView(canvas);
        drawText(canvas, this.textString);
    }

    private void drawTipView(Canvas canvas) {
        drawRoundRect(canvas);
        drawTriangle(canvas);
    }

    private void drawRoundRect(Canvas canvas) {
        this.rectF.set(this.moveDis, 0.0f, ((float) this.tipWidth) + this.moveDis, (float) this.tipHeight);
        canvas.drawRoundRect(this.rectF, (float) this.roundRectRadius, (float) this.roundRectRadius, this.tipPaint);
    }

    private void drawTriangle(Canvas canvas) {
        this.path.moveTo(((float) ((this.tipWidth / 2) - this.triangleHeight)) + this.moveDis, (float) this.tipHeight);
        this.path.lineTo(((float) (this.tipWidth / 2)) + this.moveDis, (float) (this.tipHeight + this.triangleHeight));
        this.path.lineTo(((float) ((this.tipWidth / 2) + this.triangleHeight)) + this.moveDis, (float) this.tipHeight);
        canvas.drawPath(this.path, this.tipPaint);
        this.path.reset();
    }

    private void drawText(Canvas canvas, String textString2) {
        this.textRect.left = (int) this.moveDis;
        this.textRect.top = 0;
        this.textRect.right = (int) (((float) this.tipWidth) + this.moveDis);
        this.textRect.bottom = this.tipHeight;
        FontMetricsInt fontMetrics = this.textPaint.getFontMetricsInt();
        canvas.drawText(textString2 + "%", (float) this.textRect.centerX(), (float) ((((this.textRect.bottom + this.textRect.top) - fontMetrics.bottom) - fontMetrics.top) / 2), this.textPaint);
    }

    private void initAnimation() {
        this.progressAnimator = ValueAnimator.ofFloat(new float[]{this.lastProgress, this.mProgress});
        this.progressAnimator.setDuration((long) this.duration);
        this.progressAnimator.setStartDelay((long) this.startDelay);
        this.progressAnimator.setInterpolator(new LinearInterpolator());
        this.progressAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                HorizontalProgressBar.this.textString = HorizontalProgressBar.formatNum(HorizontalProgressBar.format2Int((double) value));
                HorizontalProgressBar.this.currentProgress = (((float) HorizontalProgressBar.this.mWidth) * value) / 100.0f;
                if (HorizontalProgressBar.this.progressListener != null) {
                    HorizontalProgressBar.this.progressListener.currentProgressListener(value);
                }
                if (HorizontalProgressBar.this.currentProgress >= ((float) (HorizontalProgressBar.this.tipWidth / 2)) && HorizontalProgressBar.this.currentProgress <= ((float) (HorizontalProgressBar.this.mWidth - (HorizontalProgressBar.this.tipWidth / 2)))) {
                    HorizontalProgressBar.this.moveDis = HorizontalProgressBar.this.currentProgress - ((float) (HorizontalProgressBar.this.tipWidth / 2));
                }
                HorizontalProgressBar.this.invalidate();
            }
        });
        this.lastProgress = this.mProgress;
        this.progressAnimator.start();
    }

    public void initProgress() {
        this.mProgress = 0.0f;
        this.lastProgress = 0.0f;
    }

    public HorizontalProgressBar setProgressWithAnimation(float progress) {
        this.mProgress = progress;
        initAnimation();
        return this;
    }

    public HorizontalProgressBar setCurrentProgress(float progress) {
        this.mProgress = progress;
        this.currentProgress = (((float) this.mWidth) * progress) / 100.0f;
        this.textString = formatNum(format2Int((double) progress));
        invalidate();
        return this;
    }

    public void startProgressAnimation() {
        if (this.progressAnimator != null && !this.progressAnimator.isRunning() && !this.progressAnimator.isStarted()) {
            this.progressAnimator.start();
        }
    }

    @RequiresApi(api = 19)
    public void pauseProgressAnimation() {
        if (this.progressAnimator != null) {
            this.progressAnimator.pause();
        }
    }

    @RequiresApi(api = 19)
    public void resumeProgressAnimation() {
        if (this.progressAnimator != null) {
            this.progressAnimator.resume();
        }
    }

    public void stopProgressAnimation() {
        if (this.progressAnimator != null) {
            this.progressAnimator.end();
        }
    }

    public HorizontalProgressBar setProgressListener(ProgressListener listener) {
        this.progressListener = listener;
        return this;
    }

    public static String formatNumTwo(double money) {
        return new DecimalFormat("0.00").format(money);
    }

    public static String formatNum(int money) {
        return new DecimalFormat("0").format((long) money);
    }

    /* access modifiers changed from: protected */
    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(1, (float) dpVal, getResources().getDisplayMetrics());
    }

    /* access modifiers changed from: protected */
    public int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(2, (float) spVal, getResources().getDisplayMetrics());
    }

    public static int format2Int(double i) {
        return (int) i;
    }
}
