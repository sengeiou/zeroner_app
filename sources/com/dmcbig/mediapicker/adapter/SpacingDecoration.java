package com.dmcbig.mediapicker.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class SpacingDecoration extends ItemDecoration {
    private int space;
    private int spanCount;

    public SpacingDecoration(int spanCount2, int space2) {
        this.spanCount = spanCount2;
        this.space = space2;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        outRect.left = this.space;
        outRect.bottom = this.space;
        if (parent.getChildLayoutPosition(view) % this.spanCount == 0) {
            outRect.left = 0;
        }
    }
}
