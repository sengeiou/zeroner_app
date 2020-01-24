package com.iwown.device_module.device_add_sport.recyledapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import com.iwown.device_module.R;
import com.iwown.device_module.device_add_sport.Listener.OnItemOnclikListener;
import com.iwown.device_module.device_add_sport.bean.AddSport;
import com.iwown.device_module.device_add_sport.callback.ItemDragHelperCallback.ItemTouchHelperAdapter;
import com.iwown.device_module.device_add_sport.util.AddSportUtil;
import java.util.ArrayList;
import java.util.Collections;

public class RecycleAdapter extends Adapter<AddSportViewHolder> implements ItemTouchHelperAdapter {
    int check;
    Context context;
    ArrayList<AddSport> mData;
    LayoutInflater mInflater;
    public OnItemOnclikListener mOnClikListener;

    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(this.mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void onItemDismiss(int position) {
        this.mData.remove(position);
        notifyItemRemoved(position);
    }

    public void setmData(ArrayList<AddSport> mData2) {
        this.mData = mData2;
    }

    public void addsmData(AddSport data) {
        this.mData.add(data);
    }

    public void deletesmData(int pos) {
        this.mData.remove(pos);
    }

    public void SetOnClickListener(OnItemOnclikListener mOnClikListener2) {
        this.mOnClikListener = mOnClikListener2;
    }

    public RecycleAdapter(ArrayList<AddSport> mData2, Context context2, int check2) {
        this.mData = mData2;
        this.context = context2;
        this.mInflater = LayoutInflater.from(context2);
        this.check = check2;
    }

    public AddSportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddSportViewHolder(this.mInflater.inflate(R.layout.device_module_add_sport_item, parent, false));
    }

    public void onBindViewHolder(AddSportViewHolder holder, final int position) {
        holder.tv_itemName.setText(((AddSport) this.mData.get(position)).getSportName());
        if (this.check == 1) {
            int id = AddSportUtil.getSporyImgOrName(2, ((AddSport) this.mData.get(position)).getType());
            if (id == -1) {
                holder.img_item_pic.setImageResource(((AddSport) this.mData.get(position)).getDrawableId());
            } else {
                holder.img_item_pic.setImageResource(id);
            }
        } else if (this.check == 2) {
            holder.img_item_pic.setImageResource(AddSportUtil.getSporyImgOrName(1, ((AddSport) this.mData.get(position)).getType()));
        } else {
            holder.img_item_pic.setImageResource(((AddSport) this.mData.get(position)).getDrawableId());
        }
        if (this.mOnClikListener != null) {
            holder.tv_itemName.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RecycleAdapter.this.mOnClikListener.OnItemClik(view, position);
                }
            });
            holder.tv_itemName.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    RecycleAdapter.this.mOnClikListener.OnItemLongClik(view, position);
                    return true;
                }
            });
            holder.img_item_pic.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RecycleAdapter.this.mOnClikListener.OnItemClik(view, position);
                }
            });
        }
    }

    public int getItemCount() {
        return this.mData.size();
    }
}
