package com.iwown.sport_module.ui.sleep;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

public abstract class RecyclerOnPagerListener extends OnScrollListener {
    LinearLayoutManager layoutManager;
    public int position;

    public abstract void callBack(int i);

    public RecyclerOnPagerListener(LinearLayoutManager layoutManager2) {
        this.layoutManager = layoutManager2;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        int firstCompletelyVisibleItemPosition = this.layoutManager.findFirstCompletelyVisibleItemPosition();
        if (newState == 0 && firstCompletelyVisibleItemPosition != -1 && firstCompletelyVisibleItemPosition != this.position) {
            callBack(firstCompletelyVisibleItemPosition);
            this.position = firstCompletelyVisibleItemPosition;
        }
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dx == 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (this.position != firstVisibleItemPosition) {
                callBack(firstVisibleItemPosition);
                this.position = firstVisibleItemPosition;
            }
        }
    }
}
