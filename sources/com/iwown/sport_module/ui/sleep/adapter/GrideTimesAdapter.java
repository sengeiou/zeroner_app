package com.iwown.sport_module.ui.sleep.adapter;

import android.content.Context;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.lib_common.views.utils.MyBaseAdapter;
import com.iwown.lib_common.views.utils.MyViewHolder;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.sleep.data.SleepBean;
import java.util.List;

public class GrideTimesAdapter extends MyBaseAdapter<SleepBean> {
    public GrideTimesAdapter(List<SleepBean> mDatas, Context mContext, int layoutId) {
        super(mDatas, mContext, layoutId);
    }

    public void convert(MyViewHolder holder, SleepBean sleepBean, int position) {
        TextView tv_title = (TextView) holder.getView(R.id.tv_title);
        TextView tv_hour = (TextView) holder.getView(R.id.tv_hour);
        TextView tv_min = (TextView) holder.getView(R.id.tv_min);
        int hour = sleepBean.time / 60;
        int min = sleepBean.time % 60;
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_hour, tv_min);
        tv_title.setText(sleepBean.title + "");
        tv_hour.setText(hour + "");
        tv_min.setText(min + "");
    }
}
