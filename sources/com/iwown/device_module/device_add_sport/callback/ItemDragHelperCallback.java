package com.iwown.device_module.device_add_sport.callback;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

public class ItemDragHelperCallback extends Callback {
    private final ItemTouchHelperAdapter mAdapter;

    public interface ItemTouchHelperAdapter {
        void onItemDismiss(int i);

        void onItemMove(int i, int i2);
    }

    public ItemDragHelperCallback(ItemTouchHelperAdapter mAdapter2) {
        this.mAdapter = mAdapter2;
    }

    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        return makeMovementFlags(15, 0);
    }

    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        this.mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    public void onSwiped(ViewHolder viewHolder, int direction) {
        this.mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    public boolean isLongPressDragEnabled() {
        return true;
    }

    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == 1) {
            viewHolder.itemView.setAlpha(1.0f - (Math.abs(dX) / ((float) viewHolder.itemView.getWidth())));
            viewHolder.itemView.setTranslationX(dX);
            return;
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
