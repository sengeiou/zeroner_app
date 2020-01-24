package com.iwown.device_module.device_firmware_upgrade.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import com.iwown.device_module.R;

public class ProgressBar extends AbsProgressBar {
    private int arrowPointColor;
    protected int endFillColor;
    protected int middleFillColor;
    protected float progressWidth;
    protected int startFillColor;

    public ProgressBar(Context context) {
        super(context);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = this.context.obtainStyledAttributes(attrs, R.styleable.device_module_progressBar);
        this.startFillColor = a.getColor(R.styleable.device_module_progressBar_device_module_startFillColor, SupportMenu.CATEGORY_MASK);
        this.middleFillColor = a.getColor(R.styleable.device_module_progressBar_device_module_middleFillColor, SupportMenu.CATEGORY_MASK);
        this.endFillColor = a.getColor(R.styleable.device_module_progressBar_device_module_endFillColor, SupportMenu.CATEGORY_MASK);
        this.arrowPointColor = a.getColor(R.styleable.device_module_progressBar_device_module_arrowPointColor, -1);
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void getDimension() {
        super.getDimension();
        this.progressWidth = (float) ((((double) this.progress) / 100.0d) * ((double) this.width));
    }

    public void drawProgress(Canvas canvas) {
        if (this.progressWidth < (this.height / 2.0f) - dip2px(2.0f)) {
            this.paint.setColor(this.backgroundColor);
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, this.width, this.height), this.height / 2.0f, this.height / 2.0f, this.paint);
            this.paint.setColor(this.arrowPointColor);
            canvas.drawCircle(this.height / 2.0f, this.height / 2.0f, (this.height / 2.0f) - dip2px(2.0f), this.paint);
        } else if (this.progressWidth > (this.height / 2.0f) - dip2px(2.0f)) {
            this.paint.setColor(this.backgroundColor);
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, this.width, this.height), this.height / 2.0f, this.height / 2.0f, this.paint);
            this.paint.setShader(getShader(this.progressWidth));
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, this.progressWidth, this.height), this.height / 2.0f, this.height / 2.0f, this.paint);
            initPaint();
            this.paint.setColor(this.arrowPointColor);
            canvas.drawCircle(this.progressWidth - (this.height / 2.0f), this.height / 2.0f, (this.height / 2.0f) - dip2px(2.0f), this.paint);
        } else {
            this.paint.setShader(getShader(this.progressWidth));
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, this.progressWidth, this.height), this.height / 2.0f, this.height / 2.0f, this.paint);
            initPaint();
            this.paint.setColor(this.arrowPointColor);
            canvas.drawCircle(this.progressWidth - (this.height / 2.0f), this.height / 2.0f, (this.height / 2.0f) - dip2px(2.0f), this.paint);
        }
    }

    /* access modifiers changed from: protected */
    public Shader getShader(float width) {
        return new LinearGradient(0.0f, 0.0f, width, 0.0f, new int[]{this.startFillColor, this.middleFillColor, this.endFillColor}, new float[]{0.0f, 0.5f, 1.0f}, TileMode.MIRROR);
    }

    public void setStartFillColor(int startFillColor2) {
        this.startFillColor = startFillColor2;
    }

    public void setMiddleFillColor(int middleFillColor2) {
        this.middleFillColor = middleFillColor2;
    }

    public void setEndFillColor(int endFillColor2) {
        this.endFillColor = endFillColor2;
    }

    public void setArrowPointColor(int arrowPointColor2) {
        this.arrowPointColor = arrowPointColor2;
    }
}
