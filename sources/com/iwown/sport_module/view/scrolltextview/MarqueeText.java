package com.iwown.sport_module.view.scrolltextview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MarqueeText extends AppCompatTextView {
    public MarqueeText(Context con) {
        super(con);
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isFocused() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    }
}
