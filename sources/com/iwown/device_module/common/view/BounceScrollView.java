package com.iwown.device_module.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class BounceScrollView extends ScrollView {
    private View inner;
    private boolean isCount = false;
    private Rect normal = new Rect();
    private float y;

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            this.inner = getChildAt(0);
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.inner != null) {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case 1:
                if (isNeedAnimation()) {
                    animation();
                    this.isCount = false;
                    return;
                }
                return;
            case 2:
                float preY = this.y;
                float nowY = e.getY();
                int deltaY = (int) (preY - nowY);
                if (!this.isCount) {
                    deltaY = 0;
                }
                this.y = nowY;
                if (isNeedMove()) {
                    if (this.normal.isEmpty()) {
                        this.normal.set(this.inner.getLeft(), this.inner.getTop(), this.inner.getRight(), this.inner.getBottom());
                    }
                    this.inner.layout(this.inner.getLeft(), this.inner.getTop() - (deltaY / 2), this.inner.getRight(), this.inner.getBottom() - (deltaY / 2));
                }
                this.isCount = true;
                return;
            default:
                return;
        }
    }

    public void animation() {
        TranslateAnimation ta = new TranslateAnimation(0.0f, 0.0f, (float) this.inner.getTop(), (float) this.normal.top);
        ta.setDuration(200);
        this.inner.startAnimation(ta);
        this.inner.layout(this.normal.left, this.normal.top, this.normal.right, this.normal.bottom);
        this.normal.setEmpty();
    }

    public boolean isNeedAnimation() {
        return !this.normal.isEmpty();
    }

    public boolean isNeedMove() {
        int offset = this.inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }
}
