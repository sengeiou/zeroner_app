package com.iwown.sport_module.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.SportInitUtils;

public class MyTextView extends TextView {
    private int font_style = 0;

    public MyTextView(Context context) {
        super(context);
        setTypeface(SportInitUtils.mDincond_bold_font);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sport_module_MyTextView);
        this.font_style = a.getInt(R.styleable.sport_module_MyTextView_sport_module_font_style, 0);
        a.recycle();
        switch (this.font_style) {
            case 0:
                setTypeface(SportInitUtils.mDincond_bold_font);
                return;
            case 1:
                setTypeface(SportInitUtils.mFujiboli_font);
                return;
            default:
                return;
        }
    }
}
