package com.chad.library.adapter.base.callback;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.View;
import com.chad.library.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;

public class ItemDragAndSwipeCallback extends Callback {
    BaseItemDraggableAdapter mAdapter;
    int mDragMoveFlags = 15;
    float mMoveThreshold = 0.1f;
    int mSwipeMoveFlags = 32;
    float mSwipeThreshold = 0.7f;

    public ItemDragAndSwipeCallback(BaseItemDraggableAdapter adapter) {
        this.mAdapter = adapter;
    }

    public boolean isLongPressDragEnabled() {
        return false;
    }

    public boolean isItemViewSwipeEnabled() {
        return this.mAdapter.isItemSwipeEnable();
    }

    public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
        if (actionState == 2 && !isViewCreateByAdapter(viewHolder)) {
            this.mAdapter.onItemDragStart(viewHolder);
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_dragging_support, Boolean.valueOf(true));
        } else if (actionState == 1 && !isViewCreateByAdapter(viewHolder)) {
            this.mAdapter.onItemSwipeStart(viewHolder);
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_swiping_support, Boolean.valueOf(true));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (!isViewCreateByAdapter(viewHolder)) {
            if (viewHolder.itemView.getTag(R.id.BaseQuickAdapter_dragging_support) != null && ((Boolean) viewHolder.itemView.getTag(R.id.BaseQuickAdapter_dragging_support)).booleanValue()) {
                this.mAdapter.onItemDragEnd(viewHolder);
                viewHolder.itemView.setTag(R.id.BaseQuickAdapter_dragging_support, Boolean.valueOf(false));
            }
            if (viewHolder.itemView.getTag(R.id.BaseQuickAdapter_swiping_support) != null && ((Boolean) viewHolder.itemView.getTag(R.id.BaseQuickAdapter_swiping_support)).booleanValue()) {
                this.mAdapter.onItemSwipeClear(viewHolder);
                viewHolder.itemView.setTag(R.id.BaseQuickAdapter_swiping_support, Boolean.valueOf(false));
            }
        }
    }

    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        if (isViewCreateByAdapter(viewHolder)) {
            return makeMovementFlags(0, 0);
        }
        return makeMovementFlags(this.mDragMoveFlags, this.mSwipeMoveFlags);
    }

    public boolean onMove(RecyclerView recyclerView, ViewHolder source, ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        return true;
    }

    public void onMoved(RecyclerView recyclerView, ViewHolder source, int fromPos, ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, source, fromPos, target, toPos, x, y);
        this.mAdapter.onItemDragMoving(source, target);
    }

    public void onSwiped(ViewHolder viewHolder, int direction) {
        if (!isViewCreateByAdapter(viewHolder)) {
            this.mAdapter.onItemSwiped(viewHolder);
        }
    }

    public float getMoveThreshold(ViewHolder viewHolder) {
        return this.mMoveThreshold;
    }

    public float getSwipeThreshold(ViewHolder viewHolder) {
        return this.mSwipeThreshold;
    }

    public void setSwipeThreshold(float swipeThreshold) {
        this.mSwipeThreshold = swipeThreshold;
    }

    public void setMoveThreshold(float moveThreshold) {
        this.mMoveThreshold = moveThreshold;
    }

    public void setDragMoveFlags(int dragMoveFlags) {
        this.mDragMoveFlags = dragMoveFlags;
    }

    public void setSwipeMoveFlags(int swipeMoveFlags) {
        this.mSwipeMoveFlags = swipeMoveFlags;
    }

    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == 1 && !isViewCreateByAdapter(viewHolder)) {
            View itemView = viewHolder.itemView;
            c.save();
            if (dX > 0.0f) {
                c.clipRect((float) itemView.getLeft(), (float) itemView.getTop(), ((float) itemView.getLeft()) + dX, (float) itemView.getBottom());
                c.translate((float) itemView.getLeft(), (float) itemView.getTop());
            } else {
                c.clipRect(((float) itemView.getRight()) + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.translate(((float) itemView.getRight()) + dX, (float) itemView.getTop());
            }
            this.mAdapter.onItemSwiping(c, viewHolder, dX, dY, isCurrentlyActive);
            c.restore();
        }
    }

    private boolean isViewCreateByAdapter(ViewHolder viewHolder) {
        int type = viewHolder.getItemViewType();
        BaseItemDraggableAdapter baseItemDraggableAdapter = this.mAdapter;
        if (type != 273) {
            BaseItemDraggableAdapter baseItemDraggableAdapter2 = this.mAdapter;
            if (type != 546) {
                BaseItemDraggableAdapter baseItemDraggableAdapter3 = this.mAdapter;
                if (type != 819) {
                    BaseItemDraggableAdapter baseItemDraggableAdapter4 = this.mAdapter;
                    if (type != 1365) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
