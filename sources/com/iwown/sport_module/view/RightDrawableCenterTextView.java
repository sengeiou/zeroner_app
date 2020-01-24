package com.iwown.sport_module.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class RightDrawableCenterTextView extends AppCompatTextView {
    private static final String TAG = "RightDrawableCenterTextView";
    private float bodyWidth;
    private Drawable[] drawables;
    private float textWidth;

    public RightDrawableCenterTextView(Context context) {
        super(context);
        init();
    }

    public RightDrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RightDrawableCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.drawables = getCompoundDrawables();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.textWidth = getPaint().measureText(getText().toString());
        Drawable drawableRight = this.drawables[2];
        int totalWidth = getWidth();
        if (drawableRight != null) {
            this.bodyWidth = this.textWidth + ((float) drawableRight.getIntrinsicWidth()) + ((float) getCompoundDrawablePadding());
            setPadding(0, 0, (int) (((float) totalWidth) - this.bodyWidth), 0);
        }
    }

    public void setText(String text) {
        if (!text.equals(getText().toString())) {
            super.setText(text);
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.translate((((float) getWidth()) - this.bodyWidth) / 2.0f, 0.0f);
        super.onDraw(canvas);
    }
}
