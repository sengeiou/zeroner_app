package com.iwown.device_module.common.view.wheelView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.iwown.device_module.R;

public class WheelViewPro extends TosGallery {
    private static final int[] SHADOWS_COLORS = {528420, 528420, 528420};
    private GradientDrawable mBottomShadow = null;
    private Rect mSelectorBound = new Rect();
    private Drawable mSelectorDrawable = null;
    private GradientDrawable mTopShadow = null;

    public WheelViewPro(Context context) {
        super(context);
        initialize(context, null);
    }

    public WheelViewPro(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public WheelViewPro(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        setVerticalScrollBarEnabled(false);
        setSlotInCenter(true);
        setOrientation(2);
        setGravity(1);
        setUnselectedAlpha(1.0f);
        setWillNotDraw(false);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.device_module_gallery_bg_drawble);
        this.mSelectorDrawable = getContext().getResources().getDrawable(a.getResourceId(R.styleable.device_module_gallery_bg_drawble_device_module_selectedBackground, -1));
        a.recycle();
        setSoundEffectsEnabled(false);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCenterRect(canvas);
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
