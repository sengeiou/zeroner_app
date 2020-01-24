package com.iwown.sport_module.activity.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.sport_module.R;
import java.util.List;

public class RunAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int Chart = 2;
    public static final int Diagrams = 3;
    public static final int Heart_Rate = 1;
    public static final int Map = 0;
    public static final int Sports = 4;

    public RunAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.sport_module_run_acty_map_healthy_data_item);
        addItemType(1, R.layout.sport_module_run_acty_hr_item);
        addItemType(2, R.layout.sport_module_run_acty_chart_item);
        addItemType(3, R.layout.sport_module_run_acty_diagrams_item);
        addItemType(4, R.layout.sport_module_r1_view_item);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, MultiItemEntity item) {
    }
}
