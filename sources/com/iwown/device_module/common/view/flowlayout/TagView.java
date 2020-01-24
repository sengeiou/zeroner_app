package com.iwown.device_module.common.view.flowlayout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

public class TagView extends FrameLayout implements Checkable {
    private static final int[] CHECK_STATE = {16842912};
    private boolean isChecked;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private int tag_index = -1;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View view, boolean z, int i);
    }

    public int getTag_index() {
        return this.tag_index;
    }

    public void setTag_index(int tag_index2) {
        this.tag_index = tag_index2;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return getChildAt(0);
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }

    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            refreshDrawableState();
            if (this.mOnCheckedChangeListener != null) {
                this.mOnCheckedChangeListener.onCheckedChanged(this, this.isChecked, this.tag_index);
            }
        }
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void toggle() {
        setChecked(!this.isChecked);
    }
}
