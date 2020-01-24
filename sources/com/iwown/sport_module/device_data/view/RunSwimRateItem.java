package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.pojo.SwimRateData;
import com.iwown.sport_module.view.WithUnitText;
import com.iwown.sport_module.view.run.DLineChartView;

public class RunSwimRateItem extends LinearLayout {
    /* access modifiers changed from: private */
    public int avgRate = 0;
    private boolean isMertric = true;
    /* access modifiers changed from: private */
    public DLineChartView mRate_line_view;
    /* access modifiers changed from: private */
    public WithUnitText swimRateAvg;
    /* access modifiers changed from: private */
    public WithUnitText swimRateMax;

    public RunSwimRateItem(Context context) {
        super(context);
        initView(context);
    }

    public RunSwimRateItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RunSwimRateItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public RunSwimRateItem(Context context, boolean isMertric2) {
        super(context);
        this.isMertric = isMertric2;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_run_swim_rate_item, this);
        TextView tv_pace_unit = (TextView) findViewById(R.id.tv_pace_unit);
        if (!this.isMertric) {
            tv_pace_unit.setText(R.string.sport_module_unit_min_per_mi);
        }
        View avgView = findViewById(R.id.swim_rate_avg);
        View maxView = findViewById(R.id.swim_rate_max);
        this.mRate_line_view = (DLineChartView) findViewById(R.id.dlcv_rate);
        this.mRate_line_view.setShowYText(true);
        this.mRate_line_view.setShowCorner(true);
        this.mRate_line_view.setShowmins(false);
        ((TextView) avgView.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_rate_avg));
        ((TextView) maxView.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_rate_max));
        this.swimRateAvg = (WithUnitText) avgView.findViewById(R.id.value);
        this.swimRateMax = (WithUnitText) maxView.findViewById(R.id.value);
        this.swimRateAvg.setUnitTv(context.getString(R.string.sport_swimming_rate_unit2));
        this.swimRateMax.setUnitTv(context.getString(R.string.sport_swimming_rate_unit2));
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.swimRateAvg.getNumTv(), this.swimRateMax.getNumTv());
    }

    public void refreshView(final SwimRateData swimRateData) {
        if (this.mRate_line_view != null && swimRateData != null && swimRateData.getRateDataBeans() != null) {
            this.mRate_line_view.post(new Runnable() {
                public void run() {
                    if (RunSwimRateItem.this.avgRate != 0) {
                        RunSwimRateItem.this.swimRateAvg.setNumTv(String.valueOf(RunSwimRateItem.this.avgRate));
                    } else {
                        RunSwimRateItem.this.swimRateAvg.setNumTv(String.valueOf(swimRateData.getAvg_rate()));
                    }
                    RunSwimRateItem.this.swimRateMax.setNumTv(String.valueOf(swimRateData.getMaxY_rate()));
                    RunSwimRateItem.this.mRate_line_view.setMaxRealYValue((int) (((double) swimRateData.getMaxY_rate()) * 1.2d));
                    RunSwimRateItem.this.mRate_line_view.setDatas(swimRateData.getRateDataBeans());
                }
            });
        }
    }

    public void setAvgRate(int avgRate2) {
        this.avgRate = avgRate2;
    }
}
