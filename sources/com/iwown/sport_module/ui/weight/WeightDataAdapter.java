package com.iwown.sport_module.ui.weight;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iwown.sport_module.R;
import com.iwown.sport_module.util.MyScreenAdapter;
import java.text.NumberFormat;
import java.util.List;

public class WeightDataAdapter extends MyScreenAdapter<WeightDetailDataBean, BaseViewHolder> implements OnClickListener {
    CallBack_Dialog callBack_dialog;
    private final NumberFormat nf = NumberFormat.getNumberInstance();

    public interface CallBack_Dialog {
        void onResult(String str, String str2);
    }

    public void setCallBack_dialog(CallBack_Dialog callBack_dialog2) {
        this.callBack_dialog = callBack_dialog2;
    }

    public WeightDataAdapter(@Nullable List<WeightDetailDataBean> data) {
        super(R.layout.sport_module_item_weight_data, data);
        this.nf.setMaximumFractionDigits(1);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder helper, WeightDetailDataBean item) {
        TextView tv_tag = (TextView) helper.getView(R.id.tv_tag);
        TextView tv_value = (TextView) helper.getView(R.id.tv_value);
        tv_tag.setCompoundDrawablesWithIntrinsicBounds(item.icon, 0, 0, 0);
        tv_tag.setText(item.title);
        tv_value.setText(this.nf.format((double) item.value) + "");
        View iv_question = helper.getView(R.id.iv_question);
        iv_question.setTag(R.id.first_id, item);
        iv_question.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.iv_question) {
            WeightDetailDataBean weightDetailDataBean = (WeightDetailDataBean) view.getTag(R.id.first_id);
            if (this.callBack_dialog != null) {
                this.callBack_dialog.onResult(weightDetailDataBean.text_msg, weightDetailDataBean.title);
            }
        }
    }
}
