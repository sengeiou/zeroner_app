package com.iwown.my_module.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.iwown.my_module.MyInitUtils;
import com.iwown.my_module.utility.CommonUtility;

public class RoundImageDrawable extends Drawable {
    private final Paint mPaint = new Paint();
    private RectF mRect;
    private float radioX = ((float) CommonUtility.dip2px(MyInitUtils.getInstance().getMyApplication(), 26.0f));
    private float radioY = ((float) CommonUtility.dip2px(MyInitUtils.getInstance().getMyApplication(), 26.0f));

    public RoundImageDrawable() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth((float) CommonUtility.dip2px(MyInitUtils.getInstance().getMyApplication(), 1.0f));
    }

    public void setRadioX(float radioX2) {
        this.radioX = radioX2;
    }

    public void setRadioY(float radioY2) {
        this.radioY = radioY2;
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void setStyle(Style style) {
        this.mPaint.setStyle(style);
    }

    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        this.mRect = new RectF((float) left, (float) top, (float) right, (float) bottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(this.mRect, this.radioX, this.radioY, this.mPaint);
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -2;
    }
}
