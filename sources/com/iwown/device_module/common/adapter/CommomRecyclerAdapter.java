package com.iwown.device_module.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public abstract class CommomRecyclerAdapter<T> extends Adapter<BaseViewHolder> {
    private Context context;
    private int count = -1;
    private int layoutId;
    private List<T> list;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;

    public class BaseViewHolder extends ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public View getItemView() {
            return this.itemView;
        }

        public View getView(int viewId) {
            return this.itemView.findViewById(viewId);
        }

        public BaseViewHolder setText(int viewId, String text) {
            ((TextView) this.itemView.findViewById(viewId)).setText(text);
            return this;
        }

        public BaseViewHolder setImageRes(int viewId, int resId) {
            ((ImageView) this.itemView.findViewById(viewId)).setImageResource(resId);
            return this;
        }

        public BaseViewHolder setImageUrl(int viewId, String url) {
            return this;
        }

        public BaseViewHolder setVisible(int viewId, boolean isVisible) {
            this.itemView.findViewById(viewId).setVisibility(isVisible ? 0 : 8);
            return this;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);

        void onItemLongClick(View view, int i);
    }

    /* access modifiers changed from: protected */
    public abstract void fillData(BaseViewHolder baseViewHolder, int i, T t);

    public CommomRecyclerAdapter(Context context2, List<T> list2, int layoutId2) {
        this.context = context2;
        this.list = list2;
        this.layoutId = layoutId2;
    }

    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<>(LayoutInflater.from(this.context).inflate(this.layoutId, parent, false));
    }

    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (CommomRecyclerAdapter.this.onItemClickListener != null) {
                    CommomRecyclerAdapter.this.onItemClickListener.onItemClick(v, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (CommomRecyclerAdapter.this.onItemClickListener != null) {
                    CommomRecyclerAdapter.this.onItemClickListener.onItemLongClick(v, position);
                }
                return true;
            }
        });
        fillData(holder, position, this.list.get(position));
    }

    public int getItemCount() {
        if (this.count != -1) {
            return this.count;
        }
        return this.list.size();
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }
}
