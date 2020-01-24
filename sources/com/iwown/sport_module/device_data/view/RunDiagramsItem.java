package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.SportInitUtils;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.device_data.MapUtil;
import com.iwown.sport_module.pojo.DiagramsData;
import com.iwown.sport_module.view.run.DLineChartView;
import com.socks.library.KLog;

public class RunDiagramsItem extends LinearLayout {
    private boolean isMertric = true;
    /* access modifiers changed from: private */
    public DLineChartView mPace_line_view;
    private View mRate;
    private ConstraintLayout mRate_cl;
    /* access modifiers changed from: private */
    public DLineChartView mRate_line_view;
    private int mSportType;
    private TextView mTv_pace_avg;
    private TextView mTv_pace_fastest;
    private TextView mTv_rate_avg_value;
    private TextView mTv_rate_max_value;

    public RunDiagramsItem(Context context) {
        super(context);
        initView(context);
    }

    public RunDiagramsItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RunDiagramsItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public RunDiagramsItem(Context context, boolean isMertric2, int sportType) {
        super(context);
        this.isMertric = isMertric2;
        this.mSportType = sportType;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_run_acty_diagrams_item, this);
        TextView tv_pace_unit = (TextView) findViewById(R.id.tv_pace_unit);
        if (!this.isMertric) {
            tv_pace_unit.setText(R.string.sport_module_unit_min_per_mi);
        }
        findViewById(R.id.pace_chart_cl).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        findViewById(R.id.rate_chart_cl).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        this.mRate_cl = (ConstraintLayout) findViewById(R.id.rate_chart_cl);
        this.mTv_pace_fastest = (TextView) findViewById(R.id.tv_fastest_value);
        this.mTv_pace_avg = (TextView) findViewById(R.id.tv_pace_avg_value);
        this.mTv_rate_max_value = (TextView) findViewById(R.id.tv_max_value);
        this.mTv_rate_avg_value = (TextView) findViewById(R.id.tv_rate_avg_value);
        this.mTv_pace_fastest.setTypeface(SportInitUtils.mDincond_bold_font);
        this.mTv_pace_avg.setTypeface(SportInitUtils.mDincond_bold_font);
        this.mTv_rate_max_value.setTypeface(SportInitUtils.mDincond_bold_font);
        this.mTv_rate_avg_value.setTypeface(SportInitUtils.mDincond_bold_font);
        this.mPace_line_view = (DLineChartView) findViewById(R.id.dlcv_pace);
        this.mRate_line_view = (DLineChartView) findViewById(R.id.dlcv_rate);
        this.mRate_line_view.setShowYText(true);
        this.mRate_line_view.setShowCorner(true);
        this.mRate_line_view.setShowmins(false);
        this.mPace_line_view.setReverse(true);
        this.mPace_line_view.setShowYText(true);
        this.mPace_line_view.setShowCorner(true);
        this.mPace_line_view.setShowmins(true);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.mTv_pace_fastest, this.mTv_pace_avg, this.mTv_rate_avg_value, this.mTv_rate_max_value);
    }

    public void adjustDiagramUi(int dev_type) {
        switch (dev_type) {
            case 1:
                if (this.mSportType == 136) {
                    this.mRate_cl.setVisibility(8);
                    return;
                }
                return;
            case 2:
                if (this.mSportType == 1) {
                    this.mRate_cl.setVisibility(8);
                    return;
                }
                return;
            case 4:
                if (this.mSportType == 136) {
                    this.mRate_cl.setVisibility(8);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void refreshDiagrams(final DiagramsData mDiagramsData) {
        KLog.e("no2855refreshDiagrams111111");
        if (mDiagramsData != null && this.mRate_line_view != null && this.mPace_line_view != null) {
            KLog.e("no2855DLineChart:licl" + mDiagramsData.getPaceDataBeans().size());
            this.mRate_line_view.post(new Runnable() {
                public void run() {
                    RunDiagramsItem.this.mRate_line_view.setMaxRealYValue((int) (((double) mDiagramsData.getMaxY_rate()) * 1.2d));
                    RunDiagramsItem.this.mRate_line_view.setDatas(mDiagramsData.getRateDataBeans());
                }
            });
            this.mPace_line_view.post(new Runnable() {
                public void run() {
                    RunDiagramsItem.this.mPace_line_view.setMaxRealYValue((int) (((double) mDiagramsData.getMinY_pace()) * 1.2d));
                    RunDiagramsItem.this.mPace_line_view.setDatas(mDiagramsData.getPaceDataBeans());
                }
            });
            this.mTv_pace_fastest.setText(MapUtil.number2mins(mDiagramsData.getMaxY_pace()));
            this.mTv_rate_max_value.setText(mDiagramsData.getMaxY_rate() + "");
            KLog.e("no2855refreshDiagrams222222");
        }
    }

    public void refreshPaceRate(int time_double, String rateStr) {
        this.mTv_pace_avg.setText(getPaceStr(time_double));
        this.mTv_rate_avg_value.setText(rateStr);
    }

    private String getPaceStr(int time_double) {
        String numStr1 = String.valueOf(time_double / 60);
        return numStr1 + "'" + String.valueOf((int) ((double) (time_double - ((time_double / 60) * 60)))) + "''";
    }
}
