package com.iwown.sport_module.ui.sleep.adapter;

import android.content.Context;
import android.widget.TextView;
import com.iwown.lib_common.views.utils.MyBaseAdapter;
import com.iwown.lib_common.views.utils.MyViewHolder;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.sleep.data.SleepBedTimeStatusBean;
import java.util.List;

public class GrideBedTimeStatusAdapter extends MyBaseAdapter<SleepBedTimeStatusBean> {
    public GrideBedTimeStatusAdapter(List mDatas, Context mContext) {
        super(mDatas, mContext, R.layout.sport_module_item_sleep_bedtime_status);
    }

    public void convert(MyViewHolder holder, SleepBedTimeStatusBean sleepBedTimeStatusBean, int position) {
        TextView tv_title = (TextView) holder.getView(R.id.tv_title);
        tv_title.setText(sleepBedTimeStatusBean.name);
        if (sleepBedTimeStatusBean.selected) {
            tv_title.setCompoundDrawablesWithIntrinsicBounds(0, sleepBedTimeStatusBean.icon1, 0, 0);
        } else {
            tv_title.setCompoundDrawablesWithIntrinsicBounds(0, sleepBedTimeStatusBean.icon0, 0, 0);
        }
    }
}
