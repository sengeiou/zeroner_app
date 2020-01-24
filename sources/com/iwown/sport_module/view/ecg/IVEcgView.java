package com.iwown.sport_module.view.ecg;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.iwown.lib_common.DensityUtil;

public class IVEcgView extends View {
    Context context;
    protected int mBackgroundColor = Color.parseColor("#00ffffff");
    protected int mGridColor = Color.parseColor("#24ffffff");
    protected int mGridWidth = 100;
    protected int mHeight;
    protected Paint mPaint;
    protected Paint mPath;
    protected int mSGridColor = Color.parseColor("#24ffffff");
    protected int mSGridWidth = 20;
    protected int mWidth;

    public IVEcgView(Context context2) {
        super(context2);
        this.context = context2;
    }

    public IVEcgView(Context context2, @Nullable AttributeSet attrs) {
        super(context2, attrs);
        this.context = context2;
        init(context2);
    }

    public IVEcgView(Context context2, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context2, attrs, defStyleAttr);
        this.context = context2;
        init(context2);
    }

    @TargetApi(21)
    public IVEcgView(Context context2, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context2, attrs, defStyleAttr, defStyleRes);
        this.context = context2;
        init(context2);
    }

    private void init(Context context2) {
        Log.i("------------------", "" + this.mSGridWidth + "===" + this.mGridWidth);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mWidth = w;
        this.mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initBackground(canvas);
    }

    private void initBackground(Canvas canvas) {
        this.mPaint = new Paint();
        this.mPath = new Paint();
        canvas.drawColor(this.mBackgroundColor);
        this.mGridWidth = DensityUtil.dip2px(this.context, 50.0f);
        this.mSGridWidth = DensityUtil.dip2px(this.context, 10.0f);
        int vSNum = this.mWidth / this.mSGridWidth;
        int hSNum = this.mHeight / this.mSGridWidth;
        this.mPaint.setColor(this.mSGridColor);
        this.mPaint.setStrokeWidth(2.0f);
        for (int i = 0; i < vSNum + 1; i++) {
            canvas.drawLine((float) (this.mSGridWidth * i), 0.0f, (float) (this.mSGridWidth * i), (float) this.mHeight, this.mPaint);
        }
        for (int i2 = 0; i2 < hSNum + 1; i2++) {
            canvas.drawLine(0.0f, (float) (this.mSGridWidth * i2), (float) this.mWidth, (float) (this.mSGridWidth * i2), this.mPaint);
        }
        int vNum = this.mWidth / this.mGridWidth;
        int hNum = this.mHeight / this.mGridWidth;
        this.mPaint.setColor(this.mGridColor);
        this.mPaint.setStrokeWidth(4.0f);
        for (int i3 = 0; i3 < vNum + 1; i3++) {
            canvas.drawLine((float) (this.mGridWidth * i3), 0.0f, (float) (this.mGridWidth * i3), (float) this.mHeight, this.mPaint);
        }
        for (int i4 = 0; i4 < hNum + 1; i4++) {
            canvas.drawLine(0.0f, (float) (this.mGridWidth * i4), (float) this.mWidth, (float) (this.mGridWidth * i4), this.mPaint);
        }
    }
}
