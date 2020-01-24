package com.iwown.my_module.healthy.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.iwown.my_module.R;

public class ClearableEditText extends AppCompatEditText {
    private static final int DRAWABLE_BOTTOM = 3;
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_TOP = 1;
    private Drawable mClearDrawable;

    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mClearDrawable = getResources().getDrawable(R.mipmap.bbs_edit_clear);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                Drawable drawable = getCompoundDrawables()[2];
                if (drawable != null && event.getX() <= ((float) (getWidth() - getPaddingRight())) && event.getX() >= ((float) ((getWidth() - getPaddingRight()) - drawable.getBounds().width()))) {
                    setText("");
                    break;
                }
        }
        return super.onTouchEvent(event);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], visible ? this.mClearDrawable : null, getCompoundDrawables()[3]);
    }
}
