package com.iwown.sport_module.ui.sleep.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iwown.sport_module.R;
import java.util.List;

public class NumberAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private final LayoutParams layoutParams;

    public NumberAdapter(@Nullable List<String> data, int number_height) {
        super(R.layout.sport_module_item_number, data);
        this.layoutParams = new LayoutParams(-1, number_height);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, String item) {
        ((RelativeLayout) helper.getView(R.id.rl_number)).setLayoutParams(this.layoutParams);
        TextView tv_number = (TextView) helper.getView(R.id.tv_number);
        ImageView iv_delete = (ImageView) helper.getView(R.id.iv_delete);
        iv_delete.setVisibility(8);
        if (TextUtils.equals(item, "-1")) {
            iv_delete.setVisibility(0);
        } else {
            tv_number.setText(item);
        }
    }
}
