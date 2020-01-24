package com.iwown.sport_module.ui.af.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.af.bean.AfDecription;
import java.util.List;

public class AfDescriptionAdapter extends BaseQuickAdapter<AfDecription, BaseViewHolder> {
    private List<AfDecription> data;

    public AfDescriptionAdapter(@Nullable List<AfDecription> data2) {
        super(R.layout.sport_module_adpter_recyclerview_item, data2);
        this.data = data2;
    }

    public int getItemCount() {
        return this.data.size() + getHeaderLayoutCount();
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, AfDecription item) {
        helper.setImageResource(R.id.iv_chart_view, item.getImgRes());
        helper.setText(R.id.tv_chart_description, item.getDespText());
    }
}
