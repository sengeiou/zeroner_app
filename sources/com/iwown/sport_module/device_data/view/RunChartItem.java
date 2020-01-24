package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.activity.adapter.RunBaseDataAdapter;
import com.iwown.sport_module.pojo.DataFragmentBean;
import java.util.ArrayList;
import java.util.List;

public class RunChartItem extends LinearLayout {
    private boolean isMertric = true;
    private List<DataFragmentBean> mDataFragmentBeans = new ArrayList();
    private LinearLayoutManager mPaceChartLinearLayoutManager;
    private RecyclerView mPaceChartRcy;
    private RunBaseDataAdapter mPaceChartRcyAdapter;
    private RunBaseDataAdapter mRunBaseDataAdapter;

    public RunChartItem(Context context) {
        super(context);
        initView(context);
    }

    public RunChartItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RunChartItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public RunChartItem(Context context, boolean isMertric2) {
        super(context);
        this.isMertric = isMertric2;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_run_acty_chart_item, this);
        if (!this.isMertric) {
            TextView pace_unit = (TextView) findViewById(R.id.tv_pace_unit);
            TextView speed_unit = (TextView) findViewById(R.id.tv_speed_unit);
            ((TextView) findViewById(R.id.tv_distance_unit)).setText(R.string.sport_module_distance_unit_mi);
            pace_unit.setText(R.string.sport_module_unit_min_per_mi);
            speed_unit.setText(R.string.sport_module_unit_mi_per_h);
        }
        findViewById(R.id.total_chart_cl).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        this.mPaceChartRcy = (RecyclerView) findViewById(R.id.rlv_datas);
        this.mPaceChartRcy.setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        this.mPaceChartLinearLayoutManager = new LinearLayoutManager(getContext());
        this.mPaceChartRcyAdapter = new RunBaseDataAdapter(this.mDataFragmentBeans, getContext());
        this.mPaceChartRcy.setLayoutManager(this.mPaceChartLinearLayoutManager);
        this.mPaceChartRcy.setAdapter(this.mPaceChartRcyAdapter);
    }

    public void refreshChartView(List<DataFragmentBean> mDataFragmentBeans2) {
        if (mDataFragmentBeans2 != null && mDataFragmentBeans2.size() > 0) {
            this.mDataFragmentBeans.clear();
            this.mDataFragmentBeans.addAll(mDataFragmentBeans2);
            if (this.mPaceChartRcyAdapter != null) {
                this.mPaceChartRcyAdapter.notifyDataSetChanged();
            }
        }
    }
}
