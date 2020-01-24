package com.iwown.my_module.feedback.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TalkLeftOneLayout extends LinearLayout {
    private RecyclerView recyclerView;

    public TalkLeftOneLayout(Context context) {
        super(context);
    }

    public TalkLeftOneLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TalkLeftOneLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
