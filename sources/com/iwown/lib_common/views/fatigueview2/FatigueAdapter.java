package com.iwown.lib_common.views.fatigueview2;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iwown.lib_common.R;
import java.util.List;

public class FatigueAdapter extends BaseQuickAdapter<FatigueDataBean2, BaseViewHolder> {
    private String tag;

    public FatigueAdapter(@Nullable List<FatigueDataBean2> data) {
        super(R.layout.lib_common_item_fatigue, data);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, FatigueDataBean2 item) {
        TextView tv_tag = (TextView) helper.getView(R.id.tv_tag);
        if (TextUtils.equals(item.tag, this.tag)) {
            tv_tag.setTextColor(tv_tag.getResources().getColor(R.color.white));
        } else {
            tv_tag.setTextColor(tv_tag.getResources().getColor(R.color.fatigue_history_date_color0));
        }
        ((FatigueLineView) helper.getView(R.id.flv_fatigue)).setFatigueDataBean(item);
        if (item.max_value == -1 && item.min_value == -1) {
            tv_tag.setVisibility(4);
            return;
        }
        tv_tag.setVisibility(0);
        tv_tag.setText(item.tag);
    }

    public void setSelectDate(String tag2) {
        this.tag = tag2;
    }
}
