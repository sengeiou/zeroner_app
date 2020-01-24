package com.iwown.sport_module.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.iwown.lib_common.DensityUtil;

public class SportPopupWindow extends LinearLayout {
    public SportPopupWindow(Context context) {
        super(context);
    }

    public SportPopupWindow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Paint p = new Paint();
        p.setColor(-1);
        p.setAntiAlias(true);
        p.setStyle(Style.FILL);
        p.setStrokeWidth(2.0f);
        int raice = DensityUtil.dip2px(getContext(), 10.0f);
        RectF oval3 = new RectF(0.0f, (float) DensityUtil.dip2px(getContext(), 10.0f), (float) width, (float) height);
        canvas.drawRoundRect(oval3, (float) raice, (float) raice, p);
        p.setColor(385887577);
        p.setStyle(Style.STROKE);
        canvas.drawRoundRect(oval3, (float) raice, (float) raice, p);
        Path path = new Path();
        p.setColor(-16248796);
        p.setStyle(Style.FILL);
        path.moveTo((float) (width / 2), 0.0f);
        path.lineTo((float) ((width / 2) - DensityUtil.dip2px(getContext(), 6.0f)), (float) DensityUtil.dip2px(getContext(), 12.0f));
        path.lineTo((float) ((width / 2) + DensityUtil.dip2px(getContext(), 6.0f)), (float) DensityUtil.dip2px(getContext(), 12.0f));
        path.close();
        canvas.drawPath(path, p);
    }

    public void reshLin() {
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
