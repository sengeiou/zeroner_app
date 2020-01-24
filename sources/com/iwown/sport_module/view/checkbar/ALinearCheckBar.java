package com.iwown.sport_module.view.checkbar;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.iwown.sport_module.view.checkbar.CheckbleItemContainer.OnCheckedChangedListener;

public class ALinearCheckBar extends LinearLayout implements OnCheckedChangedListener {
    private AChecKBarAdapter mAdapter;
    /* access modifiers changed from: private */
    public SparseArray<CheckbleItemContainer> mContainerSparseArray = new SparseArray<>();
    private Context mContext;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;

    public interface OnCheckedChangeListener {
        void onCheckChanged(int i, boolean z);
    }

    public interface OnItemClickListener {
        void onItemClick(int i, View view);
    }

    public void onCheckedChanged(CheckbleItemContainer container, boolean isChecked) {
        if (this.mOnCheckedChangeListener != null) {
            this.mOnCheckedChangeListener.onCheckChanged(this.mContainerSparseArray.indexOfValue(container), isChecked);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public ALinearCheckBar(Context context) {
        super(context);
        this.mContext = context;
    }

    public ALinearCheckBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public ALinearCheckBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setAdapter(AChecKBarAdapter adapter) {
        this.mAdapter = adapter;
        if (adapter != null) {
            for (int i = 0; i < adapter.getData().size(); i++) {
                CheckbleItemContainer view = (CheckbleItemContainer) adapter.getItemView();
                this.mAdapter.bindCheckRes(view, adapter.getData().get(i));
                addView(view);
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                layoutParams.gravity = 17;
                if (getOrientation() == 1) {
                    layoutParams.width = -1;
                    layoutParams.height = 0;
                    layoutParams.weight = 1.0f;
                } else {
                    layoutParams.height = -1;
                    layoutParams.width = 0;
                    layoutParams.weight = 1.0f;
                }
                view.setLayoutParams(layoutParams);
                view.setClickable(true);
                this.mContainerSparseArray.put(i, view);
                if (i == this.mAdapter.getInit_checke_pos()) {
                    view.setChecked(true);
                } else {
                    view.setChecked(false);
                }
                view.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (ALinearCheckBar.this.mOnItemClickListener != null) {
                            ALinearCheckBar.this.mOnItemClickListener.onItemClick(ALinearCheckBar.this.mContainerSparseArray.indexOfValue((CheckbleItemContainer) view), view);
                        }
                        if (!((CheckbleItemContainer) view).isChecked()) {
                            for (int j = 0; j < ALinearCheckBar.this.mContainerSparseArray.size(); j++) {
                                ((CheckbleItemContainer) ALinearCheckBar.this.mContainerSparseArray.valueAt(j)).setChecked(false);
                            }
                            ((CheckbleItemContainer) view).setChecked(true);
                        }
                    }
                });
                view.setOnCheckedChangedListener(this);
            }
        }
    }

    public void setCheck(@IntRange(from = 0) int position) {
        if (position < this.mAdapter.getData().size()) {
            View view = (View) this.mContainerSparseArray.valueAt(position);
            if (!((CheckbleItemContainer) view).isChecked()) {
                for (int j = 0; j < this.mContainerSparseArray.size(); j++) {
                    ((CheckbleItemContainer) this.mContainerSparseArray.valueAt(j)).setChecked(false);
                }
                ((CheckbleItemContainer) view).setChecked(true);
            }
        }
    }
}
