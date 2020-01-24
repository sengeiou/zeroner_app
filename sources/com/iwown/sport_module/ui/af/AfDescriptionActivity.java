package com.iwown.sport_module.ui.af;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.af.adapter.AfDescriptionAdapter;
import com.iwown.sport_module.ui.af.bean.AfDecription;
import java.util.ArrayList;
import java.util.List;

public class AfDescriptionActivity extends BaseActivity {
    private AfDescriptionAdapter adapter;
    private List<AfDecription> afDescriptionList;
    private int[] imgRes = {R.drawable.af_icon0, R.drawable.af_icon1, R.drawable.af_icon2, R.drawable.af_icon3, R.drawable.af_icon4, R.drawable.af_icon5, R.drawable.af_icon6, R.drawable.af_icon7, R.drawable.af_icon8, R.drawable.af_icon9, R.drawable.af_icon10, R.drawable.af_icon11};
    private int[] textRes = {R.string.af_result_0, R.string.af_result_1, R.string.af_result_2, R.string.af_result_3, R.string.af_result_4, R.string.af_result_5, R.string.af_result_6, R.string.af_result_7, R.string.af_result_8, R.string.af_result_9, R.string.af_result_10, R.string.af_result_11};

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_af_description);
        initView();
        initData();
    }

    private void initData() {
        List<AfDecription> list = new ArrayList<>();
        for (int i = 0; i < this.imgRes.length; i++) {
            AfDecription afDecription = new AfDecription();
            afDecription.setImgRes(this.imgRes[i]);
            afDecription.setDespText(this.textRes[i]);
            list.add(afDecription);
        }
        refreshData(list);
    }

    private void refreshData(List<AfDecription> list) {
        this.afDescriptionList.clear();
        this.afDescriptionList.addAll(list);
        this.adapter.notifyDataSetChanged();
    }

    private void initView() {
        this.afDescriptionList = new ArrayList();
        setLeftBackTo();
        setTitleBarBG(getResources().getColor(R.color.heart_top));
        setTitleTextID(R.string.sport_module_af_description_title);
        RecyclerView rl_description = (RecyclerView) findViewById(R.id.rl_chart_description);
        rl_description.setLayoutManager(new GridLayoutManager(this, 2));
        this.adapter = new AfDescriptionAdapter(this.afDescriptionList);
        rl_description.setAdapter(this.adapter);
        this.adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.sport_module_activity_af_head, null));
    }
}
