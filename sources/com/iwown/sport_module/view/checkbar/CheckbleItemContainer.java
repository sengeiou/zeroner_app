package com.iwown.sport_module.view.checkbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;

public class CheckbleItemContainer extends RelativeLayout implements Checkable {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private boolean mChecked = false;
    private OnCheckedChangedListener mOnCheckedChangedListener;

    public interface OnCheckedChangedListener {
        void onCheckedChanged(CheckbleItemContainer checkbleItemContainer, boolean z);
    }

    public void setOnCheckedChangedListener(OnCheckedChangedListener onCheckedChangedListener) {
        this.mOnCheckedChangedListener = onCheckedChangedListener;
    }

    public CheckbleItemContainer(Context context) {
        super(context);
    }

    public CheckbleItemContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckbleItemContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setChecked(boolean b) {
        if (b != this.mChecked) {
            this.mChecked = b;
            refreshDrawableState();
            if (this.mOnCheckedChangedListener != null) {
                this.mOnCheckedChangedListener.onCheckedChanged(this, b);
            }
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public void toggle() {
        setChecked(!this.mChecked);
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
