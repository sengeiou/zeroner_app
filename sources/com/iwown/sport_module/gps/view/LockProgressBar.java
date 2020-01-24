package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class LockProgressBar extends View {
    /* access modifiers changed from: private */
    public onAnimEnd animListener;
    /* access modifiers changed from: private */
    public boolean isCut;
    /* access modifiers changed from: private */
    public boolean isRunning = false;
    private int mColor;
    private Paint mPaint;
    /* access modifiers changed from: private */
    public float pregss;
    private RectF rectF;
    private int roundColor;
    private Paint roundPaint;
    private Thread thread;

    public interface onAnimEnd {
        void animEndListener(boolean z);
    }

    public LockProgressBar(Context context) {
        super(context);
        initPaint();
    }

    public LockProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LockProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public LockProgressBar(Context context, int color, int color2) {
        super(context);
        this.mColor = color;
        this.roundColor = color2;
        initPaint();
    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setColor(this.mColor);
        this.mPaint.setStrokeWidth(10.0f);
        this.mPaint.setStrokeCap(Cap.ROUND);
        this.roundPaint = new Paint();
        this.roundPaint.setAntiAlias(true);
        this.roundPaint.setStyle(Style.STROKE);
        this.roundPaint.setColor(this.roundColor);
        this.roundPaint.setStrokeWidth(7.0f);
        this.roundPaint.setStrokeCap(Cap.ROUND);
        this.rectF = new RectF();
        this.pregss = 0.0f;
        this.isCut = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.rectF != null) {
            this.rectF.left = 10.0f;
            this.rectF.top = 10.0f;
            this.rectF.right = (float) (getMeasuredWidth() - 10);
            this.rectF.bottom = (float) (getMeasuredHeight() - 10);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.rectF != null && this.rectF.right > 0.0f) {
            canvas.drawArc(this.rectF, 360.0f, 360.0f, false, this.roundPaint);
            canvas.drawArc(this.rectF, -90.0f, this.pregss, false, this.mPaint);
        }
    }

    public void startAnim() {
        setVisibility(0);
        this.isRunning = true;
        this.isCut = false;
        this.pregss = 0.0f;
        if (this.thread == null || !this.thread.isAlive()) {
            this.thread = new Thread(new Runnable() {
                public void run() {
                    while (LockProgressBar.this.isRunning) {
                        try {
                            if (LockProgressBar.this.isCut) {
                                LockProgressBar.this.pregss = LockProgressBar.this.pregss - 10.8f;
                            } else {
                                LockProgressBar.this.pregss = LockProgressBar.this.pregss + 10.8f;
                            }
                            if (LockProgressBar.this.pregss <= 0.0f) {
                                LockProgressBar.this.isCut = false;
                                LockProgressBar.this.pregss = 0.0f;
                                LockProgressBar.this.isRunning = false;
                                LockProgressBar.this.animListener.animEndListener(false);
                            }
                            if (LockProgressBar.this.pregss >= 360.0f) {
                                LockProgressBar.this.isCut = true;
                                LockProgressBar.this.pregss = 360.0f;
                                LockProgressBar.this.isRunning = false;
                                LockProgressBar.this.animListener.animEndListener(true);
                            }
                            LockProgressBar.this.postInvalidate();
                            Thread.sleep(30);
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                            return;
                        }
                    }
                }
            });
            this.thread.start();
        }
    }

    public void setCut(boolean isCut2) {
        this.isCut = isCut2;
    }

    public void setAnimListener(onAnimEnd animListener2) {
        this.animListener = animListener2;
    }

    public void setPaintColor(int color1, int color2) {
        this.mColor = color1;
        this.roundColor = color2;
        if (this.mPaint != null) {
            this.mPaint.setColor(this.mColor);
            this.roundPaint.setColor(this.roundColor);
        }
    }
}
