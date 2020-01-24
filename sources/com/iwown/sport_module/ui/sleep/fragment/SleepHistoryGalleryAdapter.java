package com.iwown.sport_module.ui.sleep.fragment;

import android.content.Context;
import android.widget.TextView;
import com.iwown.data_link.sleep_data.SleepHistoryData;
import com.iwown.lib_common.views.utils.MyBaseAdapter;
import com.iwown.lib_common.views.utils.MyViewHolder;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.sleep.views.HistoryChartView;
import java.util.List;
import java.util.Random;

public class SleepHistoryGalleryAdapter extends MyBaseAdapter<SleepHistoryData> {
    private final Random random = new Random();
    private int selectItem = -1;

    public SleepHistoryGalleryAdapter(List<SleepHistoryData> mDatas, Context mContext) {
        super(mDatas, mContext, R.layout.sport_module_item_history_chart_view);
    }

    public void showLightSelectBG(int position) {
        if (this.selectItem != position) {
            this.selectItem = position;
            notifyDataSetChanged();
        }
    }

    public void convert(MyViewHolder holder, SleepHistoryData sleepHistoryCahrtBean, int position) {
        int wak = (sleepHistoryCahrtBean.totalTime - sleepHistoryCahrtBean.deepTime) - sleepHistoryCahrtBean.lightTime;
        if (wak < 0) {
            wak = 0;
        }
        HistoryChartView hcv_chart = (HistoryChartView) holder.getView(R.id.hcv_chart);
        TextView tv_day = (TextView) holder.getView(R.id.tv_day);
        if (this.selectItem == position) {
            hcv_chart.setColors(new int[]{R.color.sleep_color_history_deep1, R.color.sleep_color_history_light1, R.color.sleep_color_history_wake1});
            tv_day.setTextColor(tv_day.getResources().getColor(R.color.white));
        } else {
            hcv_chart.setColors(new int[]{R.color.sleep_color_history_deep0, R.color.sleep_color_history_light0, R.color.sleep_color_history_wake0});
            tv_day.setTextColor(tv_day.getResources().getColor(R.color.sleep_bg_base_color));
        }
        tv_day.setText(sleepHistoryCahrtBean.time_str + "");
        hcv_chart.setTypeValue(sleepHistoryCahrtBean.deepTime, sleepHistoryCahrtBean.lightTime, wak);
    }
}
