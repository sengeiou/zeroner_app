package com.iwown.device_module.common.view.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.iwown.device_module.R;
import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private static final int CENTER = 0;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final String TAG = "FlowLayout";
    private List<View> lineViews;
    protected List<List<View>> mAllViews;
    private int mGravity;
    protected List<Integer> mLineHeight;
    protected List<Integer> mLineWidth;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAllViews = new ArrayList();
        this.mLineHeight = new ArrayList();
        this.mLineWidth = new ArrayList();
        this.lineViews = new ArrayList();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.device_module_TagFlowLayout);
        this.mGravity = ta.getInt(R.styleable.device_module_TagFlowLayout_device_module_tag_gravity, -1);
        ta.recycle();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                if (lineWidth + childWidth > (sizeWidth - getPaddingLeft()) - getPaddingRight()) {
                    width = Math.max(width, lineWidth);
                    lineWidth = childWidth;
                    height += lineHeight;
                    lineHeight = childHeight;
                } else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
            } else if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        if (modeWidth != 1073741824) {
            sizeWidth = getPaddingLeft() + width + getPaddingRight();
        }
        if (modeHeight != 1073741824) {
            sizeHeight = getPaddingTop() + height + getPaddingBottom();
        }
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mAllViews.clear();
        this.mLineHeight.clear();
        this.mLineWidth.clear();
        this.lineViews.clear();
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > (width - getPaddingLeft()) - getPaddingRight()) {
                    this.mLineHeight.add(Integer.valueOf(lineHeight));
                    this.mAllViews.add(this.lineViews);
                    this.mLineWidth.add(Integer.valueOf(lineWidth));
                    lineWidth = 0;
                    lineHeight = lp.topMargin + childHeight + lp.bottomMargin;
                    this.lineViews = new ArrayList();
                }
                lineWidth += lp.leftMargin + childWidth + lp.rightMargin;
                lineHeight = Math.max(lineHeight, lp.topMargin + childHeight + lp.bottomMargin);
                this.lineViews.add(child);
            }
        }
        this.mLineHeight.add(Integer.valueOf(lineHeight));
        this.mLineWidth.add(Integer.valueOf(lineWidth));
        this.mAllViews.add(this.lineViews);
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum = this.mAllViews.size();
        for (int i2 = 0; i2 < lineNum; i2++) {
            this.lineViews = (List) this.mAllViews.get(i2);
            int lineHeight2 = ((Integer) this.mLineHeight.get(i2)).intValue();
            int currentLineWidth = ((Integer) this.mLineWidth.get(i2)).intValue();
            switch (this.mGravity) {
                case -1:
                    left = getPaddingLeft();
                    break;
                case 0:
                    left = ((width - currentLineWidth) / 2) + getPaddingLeft();
                    break;
                case 1:
                    left = (width - currentLineWidth) + getPaddingLeft();
                    break;
            }
            for (int j = 0; j < this.lineViews.size(); j++) {
                View child2 = (View) this.lineViews.get(j);
                if (child2.getVisibility() != 8) {
                    MarginLayoutParams lp2 = (MarginLayoutParams) child2.getLayoutParams();
                    int lc = left + lp2.leftMargin;
                    int tc = top + lp2.topMargin;
                    child2.layout(lc, tc, lc + child2.getMeasuredWidth(), tc + child2.getMeasuredHeight());
                    left += child2.getMeasuredWidth() + lp2.leftMargin + lp2.rightMargin;
                }
            }
            top += lineHeight2;
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
