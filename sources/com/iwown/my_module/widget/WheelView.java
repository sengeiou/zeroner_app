package com.iwown.my_module.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.view.View;
import com.iwown.my_module.R;

public class WheelView extends TosGallery {
    private static final int[] SHADOWS_COLORS = {1118481, 11184810, 11184810};
    private GradientDrawable mBottomShadow = null;
    private Rect mSelectorBound = new Rect();
    private Drawable mSelectorDrawable = null;
    private GradientDrawable mTopShadow = null;

    public WheelView(Context context) {
        super(context);
        initialize(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        setVerticalScrollBarEnabled(false);
        setSlotInCenter(true);
        setOrientation(2);
        setGravity(1);
        setUnselectedAlpha(1.0f);
        setWillNotDraw(false);
        this.mSelectorDrawable = getContext().getResources().getDrawable(R.drawable.my_module_scroll_rect);
        this.mTopShadow = new GradientDrawable(Orientation.TOP_BOTTOM, SHADOWS_COLORS);
        this.mBottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP, SHADOWS_COLORS);
        setSoundEffectsEnabled(false);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCenterRect(canvas);
        drawShadows(canvas);
    }

    public void setOrientation(int orientation) {
        if (1 == orientation) {
            throw new IllegalArgumentException("The orientation must be VERTICAL");
        }
        super.setOrientation(orientation);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int galleryCenter = getCenterOfGallery();
        View v = getChildAt(0);
        int height = v != null ? v.getMeasuredHeight() : 50;
        int top = galleryCenter - (height / 2);
        this.mSelectorBound.set(getPaddingLeft(), top, getWidth() - getPaddingRight(), top + height);
    }

    /* access modifiers changed from: protected */
    public void selectionChanged() {
        super.selectionChanged();
        playSoundEffect(0);
    }

    private void drawCenterRect(Canvas canvas) {
        if (this.mSelectorDrawable != null) {
            this.mSelectorDrawable.setBounds(this.mSelectorBound);
            this.mSelectorDrawable.draw(canvas);
        }
    }

    private void drawShadows(Canvas canvas) {
        int height = (int) (2.0d * ((double) this.mSelectorBound.height()));
        this.mTopShadow.setBounds(0, 0, getWidth(), height);
        this.mTopShadow.draw(canvas);
        this.mBottomShadow.setBounds(0, getHeight() - height, getWidth(), getHeight());
        this.mBottomShadow.draw(canvas);
    }
}
