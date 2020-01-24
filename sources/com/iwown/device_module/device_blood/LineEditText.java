package com.iwown.device_module.device_blood;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class LineEditText extends AppCompatEditText {
    private Paint paint = new Paint();

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint.setStyle(Style.STROKE);
        this.paint.setColor(-3355444);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0.0f, (float) (getHeight() - 2), (float) (getWidth() - 2), (float) (getHeight() - 2), this.paint);
    }
}
