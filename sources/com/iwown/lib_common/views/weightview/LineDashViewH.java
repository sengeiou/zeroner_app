package com.iwown.lib_common.views.weightview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class LineDashViewH extends View {
    private Paint mPaint;
    private Path mPath;

    public LineDashViewH(Context context) {
        this(context, null);
    }

    public LineDashViewH(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineDashViewH(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = 21)
    public LineDashViewH(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setColor(Color.parseColor("#535C72"));
        this.mPaint.setStrokeWidth(3.0f);
        this.mPaint.setPathEffect(new DashPathEffect(new float[]{15.0f, 10.0f}, 0.0f));
        this.mPath = new Path();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int centerX = getWidth() / 2;
        this.mPath.reset();
        this.mPath.moveTo((float) centerX, 0.0f);
        this.mPath.lineTo((float) centerX, (float) getHeight());
        canvas.drawPath(this.mPath, this.mPaint);
    }
}
