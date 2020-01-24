package com.iwown.my_module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int i = 0;
        while (i < ((ViewGroup) getChildAt(0)).getChildCount()) {
            try {
                CustomView child = (CustomView) ((ViewGroup) getChildAt(0)).getChildAt(i);
                if (child.isLastTouch) {
                    child.onTouchEvent(ev);
                    return true;
                }
                i++;
            } catch (ClassCastException e) {
            }
        }
        return super.onTouchEvent(ev);
    }
}
