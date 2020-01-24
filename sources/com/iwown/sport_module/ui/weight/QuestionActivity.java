package com.iwown.sport_module.ui.weight;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.iwown.data_link.utils.AssetsUtils;
import com.iwown.lib_common.BaseActivity2;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.views.utils.MyBaseAdapter;
import com.iwown.lib_common.views.utils.MyViewHolder;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.ui.weight.bean.QuestionBean;
import com.socks.library.KLog;
import java.util.Iterator;
import java.util.List;

public class QuestionActivity extends BaseActivity2 {
    public static final String KEY_TITLE = "key_title";
    private String key_title;
    private ListView lvDatas;

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
                textView.setTextColor(-1);
                textView.setPadding(DensityUtil.dip2px(textView.getContext(), 10.0f), DensityUtil.dip2px(textView.getContext(), 8.0f), DensityUtil.dip2px(textView.getContext(), 10.0f), DensityUtil.dip2px(textView.getContext(), 8.0f));
                ll_contents.addView(textView);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_activity_question);
        setTitleText(getString(R.string.sport_module_s2_wifi_question));
        this.key_title = getIntent().getStringExtra("key_title");
        getTitleBar().setBackgroundColor(RunActivitySkin.Sport_Home_Bg_Color_Bottom);
        initView();
        initData();
    }

    private void initView() {
        setLeftBackTo();
        this.lvDatas = (ListView) findViewById(R.id.lv_datas);
    }

    private void initData() {
        List<QuestionBean> questions = JsonTool.getListJson(AssetsUtils.getFromAssets(this, "s2_question.txt"), QuestionBean.class);
        int index = 0;
        if (!TextUtils.isEmpty(this.key_title)) {
            Iterator it = questions.iterator();
            while (it.hasNext() && !((QuestionBean) it.next()).getTitle().contains(this.key_title)) {
                index++;
            }
        }
        this.lvDatas.setAdapter(new MyAdapter(questions, this, R.layout.sport_module_item_s2_questions));
        KLog.e(index + "");
        this.lvDatas.setSelection(index);
    }
}
