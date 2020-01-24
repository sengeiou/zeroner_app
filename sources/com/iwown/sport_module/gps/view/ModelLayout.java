package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.iwown.lib_common.DensityUtil;

public class ModelLayout extends LinearLayout {
    private int surfaceQuadrangleBgColor = -1;

    public ModelLayout(Context context) {
        super(context);
    }

    public ModelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ModelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(this.surfaceQuadrangleBgColor);
        p.setStyle(Style.FILL);
        p.setAntiAlias(true);
        int end = DensityUtil.dip2px(getContext(), 70.0f);
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0.0f, (float) end);
        path.lineTo((float) getMeasuredWidth(), (float) end);
        path.close();
        canvas.drawPath(path, p);
        canvas.drawRect(0.0f, (float) end, (float) getMeasuredWidth(), (float) getMeasuredHeight(), p);
    }

    public void changeSurfaceQuadrangleBg(int color) {
        this.surfaceQuadrangleBgColor = color;
        invalidate();
    }
}
