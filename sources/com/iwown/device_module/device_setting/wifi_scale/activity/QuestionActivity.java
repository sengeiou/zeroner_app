package com.iwown.device_module.device_setting.wifi_scale.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_setting.wifi_scale.bean.QuestionBean;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.views.utils.MyBaseAdapter;
import com.iwown.lib_common.views.utils.MyViewHolder;
import com.socks.library.KLog;
import java.util.Iterator;
import java.util.List;

public class QuestionActivity extends DeviceModuleBaseActivity {
    public static final String KEY_TITLE = "key_title";
    private String key_title;
    ListView lvDatas;

    class MyAdapter extends MyBaseAdapter<QuestionBean> {
        public MyAdapter(List<QuestionBean> mDatas, Context mContext, int layoutId) {
            super(mDatas, mContext, layoutId);
        }

        public void convert(MyViewHolder holder, QuestionBean questionBean, int position) {
            ((TextView) holder.getView(R.id.tv_title)).setText("Q:" + questionBean.getTitle());
            LinearLayout ll_contents = (LinearLayout) holder.getView(R.id.ll_datas);
            ll_contents.removeAllViews();
            for (String content : questionBean.getContents()) {
                TextView textView = new TextView(holder.getConvertView().getContext());
                textView.setText(content);
                textView.setTextColor(ContextCompat.getColor(holder.getConvertView().getContext(), R.color.device_module_white));
                textView.setPadding(DensityUtil.dip2px(textView.getContext(), 10.0f), DensityUtil.dip2px(textView.getContext(), 8.0f), DensityUtil.dip2px(textView.getContext(), 10.0f), DensityUtil.dip2px(textView.getContext(), 8.0f));
                ll_contents.addView(textView);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_question);
        setTitleText(getString(R.string.device_module_scale_wifi_setting_8));
        setLeftBackTo();
        this.key_title = getIntent().getStringExtra("key_title");
        this.lvDatas = (ListView) findViewById(R.id.lv_datas);
        initData();
    }

    private void initData() {
        String fromAssets = S2WifiUtils.getFromAssets(this, "s2_question.txt");
        KLog.e("no2855 字符：" + fromAssets);
        List<QuestionBean> questions = JsonUtils.getListJson(fromAssets, QuestionBean.class);
        int index = 0;
        if (!TextUtils.isEmpty(this.key_title)) {
            Iterator it = questions.iterator();
            while (it.hasNext() && !((QuestionBean) it.next()).getTitle().contains(this.key_title)) {
                index++;
            }
        }
        this.lvDatas.setAdapter(new MyAdapter(questions, this, R.layout.device_module_item_s2_questions));
        KLog.e(index + "");
        this.lvDatas.setSelection(index);
    }
}
