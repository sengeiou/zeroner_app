package com.iwown.device_module.device_firmware_upgrade.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.iwown.device_module.R;

public class AbsProgressBar extends View {
    protected int backgroundColor;
    protected Context context;
    protected float height;
    protected Paint paint;
    protected int progress;
    protected int textColor;
    protected float width;

    public AbsProgressBar(Context context2) {
        super(context2);
        this.context = context2;
        initPaint();
    }

    public AbsProgressBar(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        this.context = context2;
        init(context2, attrs);
        initPaint();
    }

    public AbsProgressBar(Context context2, AttributeSet attrs, int defStyleAttr) {
        super(context2, attrs, defStyleAttr);
        this.context = context2;
        init(context2, attrs);
        initPaint();
    }

    private void init(Context context2, AttributeSet attrs) {
        TypedArray a = context2.obtainStyledAttributes(attrs, R.styleable.device_module_absProgressBar);
        this.progress = a.getInteger(R.styleable.device_module_absProgressBar_device_module_progress, 0);
        this.backgroundColor = a.getColor(R.styleable.device_module_absProgressBar_device_module_backgroundColor, -723724);
        this.textColor = a.getColor(R.styleable.device_module_absProgressBar_device_module_textColor, -1);
        setProgress(this.progress);
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void initPaint() {
        if (this.paint == null) {
            this.paint = new Paint();
        } else {
            this.paint.reset();
        }
        this.paint.setAntiAlias(true);
    }

    /* access modifiers changed from: protected */
    public void getDimension() {
        this.width = (float) getWidth();
        this.height = (float) getHeight();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getDimension();
        int saveCount = canvas.save();
        drawProgress(canvas);
        drawText(canvas);
        canvas.restoreToCount(saveCount);
    }

    public void drawProgress(Canvas canvas) {
    }

    public void drawText(Canvas canvas) {
    }

    public float dip2px(float dpValue) {
        return (dpValue * this.context.getResources().getDisplayMetrics().density) + 0.5f;
    }

    public void setProgress(int progress2) {
        this.progress = progress2;
        invalidate();
    }

    public int getProgress() {
        return this.progress;
    }

    public void setBackgroundColor(int backgroundColor2) {
        this.backgroundColor = backgroundColor2;
    }

    public void setTextColor(int textColor2) {
        this.textColor = textColor2;
    }
}
