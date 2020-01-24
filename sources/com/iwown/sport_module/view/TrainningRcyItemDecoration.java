package com.iwown.sport_module.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class TrainningRcyItemDecoration extends ItemDecoration {
    private int space_pix = 0;

    public TrainningRcyItemDecoration(int space_pix2) {
        this.space_pix = space_pix2;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getLayoutManager().getPosition(view) != parent.getLayoutManager().getItemCount() - 1) {
            outRect.right = this.space_pix;
        }
    }
}
