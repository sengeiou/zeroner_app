package com.iwown.my_module.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomView extends RelativeLayout {
    static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    static final String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
    boolean animation = false;
    int beforeBackground;
    final int disabledBackgroundColor = Color.parseColor("#E2E2E2");
    public boolean isLastTouch = false;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            setBackgroundColor(this.beforeBackground);
        } else {
            setBackgroundColor(this.disabledBackgroundColor);
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onAnimationStart() {
        super.onAnimationStart();
        this.animation = true;
    }

    /* access modifiers changed from: protected */
    public void onAnimationEnd() {
        super.onAnimationEnd();
        this.animation = false;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.animation) {
            invalidate();
        }
    }
}
