package com.iwown.sport_module.ui.sleep;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class AntGridView extends GridView {
    private boolean isShow;

    public AntGridView(Context context) {
        super(context);
    }

    public AntGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AntGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }

    public void setLineShow(boolean isShow2) {
        this.isShow = isShow2;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.isShow) {
            View localView1 = getChildAt(0);
            if (localView1 != null) {
                int column = getWidth() / localView1.getWidth();
                int childCount = getChildCount();
                Paint localPaint = new Paint();
                localPaint.setStyle(Style.STROKE);
                localPaint.setColor(Color.parseColor("#3A496F"));
                localPaint.setStrokeWidth(1.0f);
                for (int i = 0; i < childCount; i++) {
                    View cellView = getChildAt(i);
                    if ((i + 1) % column == 0) {
                        canvas.drawLine((float) cellView.getLeft(), (float) cellView.getBottom(), (float) cellView.getRight(), (float) cellView.getBottom(), localPaint);
                    } else if (i + 1 > childCount - (childCount % column)) {
                        canvas.drawLine((float) cellView.getRight(), (float) cellView.getTop(), (float) cellView.getRight(), (float) cellView.getBottom(), localPaint);
                    } else {
                        canvas.drawLine((float) cellView.getRight(), (float) cellView.getTop(), (float) cellView.getRight(), (float) cellView.getBottom(), localPaint);
                        canvas.drawLine((float) cellView.getLeft(), (float) cellView.getBottom(), (float) cellView.getRight(), (float) cellView.getBottom(), localPaint);
                    }
                }
                if (childCount % column != 0) {
                    for (int j = 0; j < column - (childCount % column); j++) {
                        View lastView = getChildAt(childCount - 1);
                        canvas.drawLine((float) (lastView.getRight() + (lastView.getWidth() * j)), (float) lastView.getTop(), (float) (lastView.getRight() + (lastView.getWidth() * j)), (float) lastView.getBottom(), localPaint);
                    }
                }
            }
        }
    }
}
