package com.iwown.sport_module.ui.fatigue;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.sport_module.R;
import java.util.List;

public class FatigueDetialAdapter extends BaseQuickAdapter<FatigueData, BaseViewHolder> {
    public FatigueDetialAdapter(@Nullable List<FatigueData> data) {
        super(R.layout.sport_module_item_gatigue_detail, data);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, FatigueData item) {
        helper.setText(R.id.tv_time, (CharSequence) item.getMeasuretime() + "");
        helper.setText(R.id.tv_value, (CharSequence) item.getValue() + "");
    }
}
