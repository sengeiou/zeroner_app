package com.iwown.my_module.feedback.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class TalkRecyclerView extends RecyclerView {
    private int mPosition;

    public TalkRecyclerView(Context context) {
        super(context);
    }

    public TalkRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TalkRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
