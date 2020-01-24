package com.iwown.sport_module.ui.sleep.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.skin_loader.SleepSkinHandler;
import com.iwown.sport_module.ui.sleep.fragment.SleepHistoryDetailBean;
import java.util.List;

public class SleepHistoryDetailsAdapter extends BaseQuickAdapter<SleepHistoryDetailBean, BaseViewHolder> {
    private final LayoutParams layoutParams;

    public SleepHistoryDetailsAdapter(@Nullable List<SleepHistoryDetailBean> data, int number_height) {
        super(R.layout.sport_module_item_sleep_history_detail, data);
        this.layoutParams = new LayoutParams(-1, number_height);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, SleepHistoryDetailBean item) {
        ((ConstraintLayout) helper.getView(R.id.cl_main)).setLayoutParams(this.layoutParams);
        ((TextView) helper.getView(R.id.tv_title)).setTextColor(SleepSkinHandler.getInstance().getSleepSkin_Type_Time_Title());
        ((TextView) helper.getView(R.id.tv_hour)).setTextColor(SleepSkinHandler.getInstance().getSleepSkin_Type_Time_Number());
        TextView tv_hour = (TextView) helper.getView(R.id.tv_hour);
        TextView tv_title = (TextView) helper.getView(R.id.tv_title);
        TextView tv_min = (TextView) helper.getView(R.id.tv_min);
        tv_min.setTextColor(SleepSkinHandler.getInstance().getSleepSkin_Type_Time_Number());
        TextView tv_hour_unit = (TextView) helper.getView(R.id.tv_hour_unit);
        tv_hour_unit.setTextColor(SleepSkinHandler.getInstance().getSleepSkin_Type_Time_Unit());
        TextView tv_min_unit = (TextView) helper.getView(R.id.tv_min_unit);
        tv_min_unit.setTextColor(SleepSkinHandler.getInstance().getSleepSkin_Type_Time_Unit());
        tv_title.setText(item.title + "");
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_hour, tv_min);
        if (!item.isHMFormat) {
            tv_hour_unit.setVisibility(8);
            tv_min.setVisibility(8);
            tv_min_unit.setVisibility(8);
            tv_hour.setText(item.valueStr);
            return;
        }
        tv_hour_unit.setVisibility(0);
        tv_min.setVisibility(0);
        tv_min_unit.setVisibility(0);
        tv_hour.setText(item.hour + "");
        tv_min.setText(item.min + "");
    }
}
