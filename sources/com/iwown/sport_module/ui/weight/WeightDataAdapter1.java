package com.iwown.sport_module.ui.weight;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.lib_common.views.utils.MyBaseAdapter;
import com.iwown.lib_common.views.utils.MyViewHolder;
import com.iwown.sport_module.R;
import java.text.NumberFormat;
import java.util.List;

public class WeightDataAdapter1 extends MyBaseAdapter<WeightDetailDataBean> implements OnClickListener {
    CallBack_Dialog callBack_dialog;
    private NumberFormat nf = NumberFormat.getNumberInstance();

    public interface CallBack_Dialog {
        void onResult(String str, String str2);
    }

    public WeightDataAdapter1(List<WeightDetailDataBean> mDatas, Context mContext, int layoutId) {
        super(mDatas, mContext, layoutId);
        this.nf.setMaximumFractionDigits(1);
    }

    public void convert(MyViewHolder helper, WeightDetailDataBean item, int position) {
        ProgressBar pb_progress = (ProgressBar) helper.getView(R.id.pb_progress);
        TextView tv_tag = (TextView) helper.getView(R.id.tv_tag);
        TextView tv_value = (TextView) helper.getView(R.id.tv_value);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), tv_value);
        tv_tag.setCompoundDrawablesWithIntrinsicBounds(item.icon, 0, 0, 0);
        tv_tag.setText(item.title);
        tv_value.setText(this.nf.format((double) item.value) + item.unit);
        pb_progress.setProgress((int) Math.round(item.percent * 100.0d));
        pb_progress.setProgressDrawable(item.drawable);
        View iv_question = helper.getView(R.id.iv_question);
        iv_question.setTag(R.id.first_id, item);
        iv_question.setOnClickListener(this);
    }

    public void setCallBack_dialog(CallBack_Dialog callBack_dialog2) {
        this.callBack_dialog = callBack_dialog2;
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
