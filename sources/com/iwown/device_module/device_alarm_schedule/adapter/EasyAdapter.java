package com.iwown.device_module.device_alarm_schedule.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.List;

public abstract class EasyAdapter<VH extends ViewHolder> extends Adapter<VH> implements OnClickListener {
    private int maxSelectedCount = -1;
    private List<Integer> multiSelected = new ArrayList();
    private OnItemClickListener onItemClickListener;
    private OnItemMultiSelectListener onItemMultiSelectListener;
    private OnItemSingleSelectListener onItemSingleSelectListener;
    private SelectMode selectMode;
    private int singleSelected = 0;

    public interface OnItemClickListener {
        void onClicked(int i);
    }

    public interface OnItemMultiSelectListener {
        void onSelected(Operation operation, int i, boolean z);
    }

    public interface OnItemSingleSelectListener {
        void onSelected(int i, boolean z);
    }

    public enum Operation {
        ORDINARY,
        ALL_SELECTED,
        REVERSE_SELECTED,
        ALL_CANCEL,
        SET_MAX_COUNT
    }

    public enum SelectMode {
        CLICK,
        SINGLE_SELECT,
        MULTI_SELECT
    }

    public abstract void whenBindViewHolder(VH vh, int i);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public void setOnItemSingleSelectListener(OnItemSingleSelectListener onItemSingleSelectListener2) {
        this.onItemSingleSelectListener = onItemSingleSelectListener2;
    }

    public void setOnItemMultiSelectListener(OnItemMultiSelectListener onItemMultiSelectListener2) {
        this.onItemMultiSelectListener = onItemMultiSelectListener2;
    }

    public void onBindViewHolder(VH holder, int position) {
        whenBindViewHolder(holder, position);
        holder.itemView.setTag(Integer.valueOf(position));
        holder.itemView.setOnClickListener(this);
        if (this.selectMode == SelectMode.CLICK) {
            holder.itemView.setSelected(false);
        } else if (this.selectMode == SelectMode.SINGLE_SELECT) {
            if (this.singleSelected == position) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);
            }
        } else if (this.selectMode != SelectMode.MULTI_SELECT) {
        } else {
            if (this.multiSelected.contains(Integer.valueOf(position))) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);
            }
        }
    }

    public void onClick(View v) {
        int itemPosition = ((Integer) v.getTag()).intValue();
        if (this.selectMode == SelectMode.CLICK) {
            if (this.onItemClickListener != null) {
                this.onItemClickListener.onClicked(itemPosition);
            }
        } else if (this.selectMode == SelectMode.SINGLE_SELECT) {
            if (this.onItemSingleSelectListener != null) {
                if (this.singleSelected == itemPosition) {
                    this.onItemSingleSelectListener.onSelected(itemPosition, false);
                } else {
                    this.singleSelected = itemPosition;
                    this.onItemSingleSelectListener.onSelected(itemPosition, true);
                }
            }
            notifyDataSetChanged();
        } else if (this.selectMode == SelectMode.MULTI_SELECT) {
            if (this.maxSelectedCount <= 0 || this.multiSelected.size() < this.maxSelectedCount) {
                if (this.multiSelected.contains(Integer.valueOf(itemPosition))) {
                    this.multiSelected.remove(Integer.valueOf(itemPosition));
                    if (this.onItemMultiSelectListener != null) {
                        this.onItemMultiSelectListener.onSelected(Operation.ORDINARY, itemPosition, false);
                    }
                } else {
                    this.multiSelected.add(Integer.valueOf(itemPosition));
                    if (this.onItemMultiSelectListener != null) {
                        this.onItemMultiSelectListener.onSelected(Operation.ORDINARY, itemPosition, true);
                    }
                }
            } else if (this.multiSelected.size() == this.maxSelectedCount && this.multiSelected.contains(Integer.valueOf(itemPosition))) {
                this.multiSelected.remove(Integer.valueOf(itemPosition));
                if (this.onItemMultiSelectListener != null) {
                    this.onItemMultiSelectListener.onSelected(Operation.ORDINARY, itemPosition, false);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void setSelectMode(SelectMode selectMode2) {
        this.selectMode = selectMode2;
        notifyDataSetChanged();
    }

    public SelectMode getSelectMode() {
        return this.selectMode;
    }

    public void setSelected(int... itemPositions) {
        this.multiSelected.clear();
        if (this.selectMode == SelectMode.SINGLE_SELECT) {
            this.singleSelected = itemPositions[0];
            if (this.onItemSingleSelectListener != null) {
                this.onItemSingleSelectListener.onSelected(this.singleSelected, true);
            }
        } else {
            for (int itemPosition : itemPositions) {
                this.multiSelected.add(Integer.valueOf(itemPosition));
                if (this.onItemMultiSelectListener != null) {
                    this.onItemMultiSelectListener.onSelected(Operation.ORDINARY, itemPosition, true);
                }
            }
        }
        notifyDataSetChanged();
    }

    public int getSingleSelected() {
        return this.singleSelected;
    }

    public void clearSelected() {
        if (this.selectMode == SelectMode.MULTI_SELECT) {
            this.multiSelected.clear();
            if (this.onItemMultiSelectListener != null) {
                this.onItemMultiSelectListener.onSelected(Operation.ALL_CANCEL, -1, false);
            }
            notifyDataSetChanged();
        }
    }

    public int getSingleSelectedPosition() {
        return this.singleSelected;
    }

    public List<Integer> getMultiSelectedPosition() {
        return this.multiSelected;
    }

    public void setMaxSelectedCount(int maxSelectedCount2) {
        if (maxSelectedCount2 < this.multiSelected.size()) {
            this.multiSelected.clear();
        }
        this.maxSelectedCount = maxSelectedCount2;
        if (this.onItemMultiSelectListener != null) {
            this.onItemMultiSelectListener.onSelected(Operation.SET_MAX_COUNT, -1, false);
        }
        notifyDataSetChanged();
    }

    public int getMaxSelectedCount() {
        return this.maxSelectedCount;
    }

    public void selectAll() {
        if (this.maxSelectedCount <= 0) {
            this.multiSelected.clear();
            for (int i = 0; i < getItemCount(); i++) {
                this.multiSelected.add(Integer.valueOf(i));
            }
            if (this.onItemMultiSelectListener != null) {
                this.onItemMultiSelectListener.onSelected(Operation.ALL_SELECTED, -1, false);
            }
            notifyDataSetChanged();
        }
    }

    public void reverseSelected() {
        if (this.maxSelectedCount <= 0) {
            if (this.onItemMultiSelectListener != null) {
                this.onItemMultiSelectListener.onSelected(Operation.REVERSE_SELECTED, -1, false);
            }
            for (int i = 0; i < getItemCount(); i++) {
                if (this.multiSelected.contains(Integer.valueOf(i))) {
                    this.multiSelected.remove(Integer.valueOf(i));
                } else {
                    this.multiSelected.add(Integer.valueOf(i));
                }
            }
            notifyDataSetChanged();
        }
    }

    public boolean isSelected(int position) {
        if (this.selectMode == SelectMode.SINGLE_SELECT) {
            if (position == this.singleSelected) {
                return true;
            }
            return false;
        } else if (this.selectMode == SelectMode.MULTI_SELECT) {
            return this.multiSelected.contains(Integer.valueOf(position));
        } else {
            return false;
        }
    }
}
