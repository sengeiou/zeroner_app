package com.iwown.sport_module.activity.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.iwown.sport_module.R;
import com.iwown.sport_module.pojo.DataFragmentBean;
import java.util.List;

public class RunBaseDataAdapter extends DBaseRecyclerViewAdapter<DataFragmentBean> {
    List<DataFragmentBean> mDataFragmentBeans = null;

    public RunBaseDataAdapter(List<DataFragmentBean> mDatas, Context mContext) {
        super(mDatas, mContext);
        this.mDataFragmentBeans = mDatas;
    }

    /* access modifiers changed from: protected */
    public DBaseRecyclerViewHolder onCreateViewHolder1(ViewGroup parent, int viewType) {
        RunBaseViewHolder runBaseViewHolder = new RunBaseViewHolder(parent, R.layout.sport_module_item_sport_run_data_view, this);
        if (!(this.mDataFragmentBeans == null || this.mDataFragmentBeans.size() == 0)) {
            runBaseViewHolder.setData_size(this.mDataFragmentBeans.size());
        }
        return runBaseViewHolder;
    }
}
