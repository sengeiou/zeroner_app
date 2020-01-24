package com.iwown.sport_module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.socks.library.KLog;
import java.util.Hashtable;

public class AutoNextLineLinearlayout extends LinearLayout {
    private boolean isMulityLine;
    int mBottom;
    int mLeft;
    int mRight;
    int mTop;
    Hashtable map = new Hashtable();

    private class Position {
        int bottom;
        int left;
        int right;
        int top;

        private Position() {
        }
    }

    public AutoNextLineLinearlayout(Context context) {
        super(context);
    }

    public AutoNextLineLinearlayout(Context context, int horizontalSpacing, int verticalSpacing) {
        super(context);
    }

    public AutoNextLineLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mCount = getChildCount();
        int mX = 0;
        int mY = 0;
        this.mLeft = 0;
        this.mRight = 0;
        this.mTop = 5;
        this.mBottom = 0;
        int j = 0;
        for (int i = 0; i < mCount; i++) {
            View child = getChildAt(i);
            child.measure(0, 0);
            int childw = child.getMeasuredWidth();
            int childh = child.getMeasuredHeight();
            mX += childw;
            Position position = new Position();
            this.mLeft = getPosition(i - j, i);
            this.mRight = this.mLeft + child.getMeasuredWidth();
            if (mX > mWidth) {
                mX = childw;
                int mY2 = mY + childh;
                j = i;
                this.mLeft = 0;
                this.mRight = this.mLeft + child.getMeasuredWidth();
                this.mTop = mY2 + 5;
                this.isMulityLine = true;
            }
            this.mBottom = this.mTop + child.getMeasuredHeight();
            mY = this.mTop;
            position.left = this.mLeft;
            position.top = this.mTop + 3;
            position.right = this.mRight;
            position.bottom = this.mBottom;
            this.map.put(child, position);
        }
        if (this.isMulityLine) {
            setMeasuredDimension(mWidth, this.mBottom);
            return;
        }
        KLog.e("----------------------");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(0, 0);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!this.isMulityLine) {
            super.onLayout(changed, l, t, r, b);
            return;
        }
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Position pos = (Position) this.map.get(child);
            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);
            } else {
                Log.i("MyLayout", "error");
            }
        }
    }

    public int getPosition(int IndexInRow, int childIndex) {
        if (IndexInRow > 0) {
            return getPosition(IndexInRow - 1, childIndex - 1) + getChildAt(childIndex - 1).getMeasuredWidth() + 8;
        }
        return getPaddingLeft();
    }
}
