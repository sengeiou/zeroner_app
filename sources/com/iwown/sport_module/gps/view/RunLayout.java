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
import com.iwown.sport_module.R;

public class RunLayout extends LinearLayout {
    private int mColor = -13619152;

    public RunLayout(Context context) {
        super(context);
    }

    public RunLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public RunLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        this.mColor = context.obtainStyledAttributes(attrs, R.styleable.RunLayout).getColor(R.styleable.RunLayout_run_layout_bg, -13619152);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(this.mColor);
        p.setStyle(Style.FILL);
        p.setAntiAlias(true);
        int padTop = DensityUtil.dip2px(getContext(), 30.0f);
        int end = ((DensityUtil.dip2px(getContext(), 65.0f) - padTop) * 2) + padTop;
        Path path = new Path();
        path.moveTo(0.0f, (float) padTop);
        path.lineTo(0.0f, (float) end);
        path.lineTo((float) getMeasuredWidth(), (float) end);
        path.close();
        canvas.drawPath(path, p);
        canvas.drawRect(0.0f, (float) end, (float) getMeasuredWidth(), (float) getMeasuredHeight(), p);
    }
}
