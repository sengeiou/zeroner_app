package com.iwown.my_module.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.iwown.my_module.utility.FontProvider;

public class MenuTextView extends AppCompatTextView {
    public MenuTextView(Context context) {
        super(context);
        setTypeface(FontProvider.getFont_DINPRO_MEDIUM(getContext()));
    }

    public MenuTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontProvider.getFont_DINPRO_MEDIUM(getContext()));
    }

    public MenuTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(FontProvider.getFont_DINPRO_MEDIUM(getContext()));
    }
}
