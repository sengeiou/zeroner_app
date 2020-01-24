package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.SportInitUtils;
import com.iwown.sport_module.device_data.MapUtil;
import com.iwown.sport_module.pojo.R1DataBean;
import com.iwown.sport_module.view.WithUnitText;
import com.iwown.sport_module.view.run.DPointChartView;

public class RunR1SportItem extends LinearLayout {
    DPointChartView dlcv_pace;
    TextView mTv_pace_avg;
    TextView mTv_pace_fastest;
    TextView mTv_rate_avg_value;
    TextView mTv_rate_max_value;
    DPointChartView r1_amplitude_chart;
    DPointChartView r1_earth_chart;
    TextView ri_touchdown_txt;
    WithUnitText ri_touchdown_unit;
    RelativeLayout rl_earth_time;
    RelativeLayout rl_rate;
    RelativeLayout rl_sky_time;
    RelativeLayout rl_vertical_time;
    DPointChartView step_rate;

    public RunR1SportItem(Context context) {
        super(context);
        initView(context);
    }

    public RunR1SportItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RunR1SportItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_r1_view_item, this);
        this.dlcv_pace = (DPointChartView) findViewById(R.id.dlcv_pace);
        this.step_rate = (DPointChartView) findViewById(R.id.step_rate);
        this.r1_amplitude_chart = (DPointChartView) findViewById(R.id.r1_amplitude_chart);
        this.r1_earth_chart = (DPointChartView) findViewById(R.id.r1_earth_chart);
        this.mTv_pace_fastest = (TextView) findViewById(R.id.tv_fastest_value);
        this.mTv_pace_avg = (TextView) findViewById(R.id.tv_pace_avg_value);
        this.mTv_pace_fastest.setTypeface(SportInitUtils.mDincond_bold_font);
        this.mTv_pace_avg.setTypeface(SportInitUtils.mDincond_bold_font);
        this.mTv_rate_max_value = (TextView) findViewById(R.id.tv_max_value);
        this.mTv_rate_avg_value = (TextView) findViewById(R.id.tv_rate_avg_value);
        this.rl_rate = (RelativeLayout) findViewById(R.id.rl_rate);
        this.rl_earth_time = (RelativeLayout) findViewById(R.id.rl_earth_time);
        this.rl_sky_time = (RelativeLayout) findViewById(R.id.rl_sky_time);
        this.rl_vertical_time = (RelativeLayout) findViewById(R.id.rl_vertical_time);
        this.ri_touchdown_txt = (TextView) findViewById(R.id.ri_touchdown_txt);
        this.ri_touchdown_unit = (WithUnitText) findViewById(R.id.ri_touchdown_unit);
        this.dlcv_pace.setReverse(true);
        this.dlcv_pace.setShowYText(true);
        this.dlcv_pace.setShowCorner(true);
        this.dlcv_pace.setShowmins(true);
        this.dlcv_pace.setShowYText(true);
        this.r1_amplitude_chart.setShowYText(true);
        this.r1_earth_chart.setShowYText(true);
        this.step_rate.setShowYText(true);
    }

    private void setSportText(RelativeLayout rl, String titleStr, String valueStr, String... valueAdd) {
        StringBuilder builder = new StringBuilder();
        TextView title = (TextView) rl.findViewById(R.id.title);
        WithUnitText value = (WithUnitText) rl.findViewById(R.id.value);
        for (String append : valueAdd) {
            builder.append(append);
        }
        title.setText(titleStr);
        value.setNumTv(valueStr);
        value.setUnitTv(builder.toString());
    }

    public void refreshR1SportView(final R1DataBean r1DataBean) {
        if (r1DataBean != null) {
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.mTv_pace_fastest, this.mTv_pace_avg, this.mTv_rate_avg_value, this.mTv_rate_max_value);
            this.dlcv_pace.post(new Runnable() {
                public void run() {
                    RunR1SportItem.this.dlcv_pace.setMaxRealYValue((int) r1DataBean.getSpeed_max());
                    RunR1SportItem.this.dlcv_pace.setDatas(r1DataBean.getSpeedLists());
                }
            });
            this.r1_amplitude_chart.post(new Runnable() {
                public void run() {
                    RunR1SportItem.this.r1_amplitude_chart.setMaxRealYValue(r1DataBean.getMax_vertical());
                    RunR1SportItem.this.r1_amplitude_chart.setDatas(r1DataBean.getVerticalLists(), 6.7f, 8.3f, 10.1f, 11.8f, true);
                }
            });
            this.r1_earth_chart.post(new Runnable() {
                public void run() {
                    RunR1SportItem.this.r1_earth_chart.setMaxRealYValue(r1DataBean.getMax_earth_time());
                    RunR1SportItem.this.r1_earth_chart.setDatas(r1DataBean.getEarthTimeLists(), 208.0f, 240.0f, 273.0f, 305.0f, true);
                }
            });
            this.step_rate.post(new Runnable() {
                public void run() {
                    RunR1SportItem.this.step_rate.setMaxRealYValue(r1DataBean.getMaxRate());
                    RunR1SportItem.this.step_rate.setDatas(r1DataBean.getStepRateLists(), 183.0f, 173.0f, 164.0f, 153.0f, false);
                }
            });
            this.mTv_pace_fastest.setText(MapUtil.number2mins(r1DataBean.getSpeed_min()));
            this.mTv_pace_avg.setText(MapUtil.number2mins(r1DataBean.getSpeed_avg()));
            this.mTv_rate_avg_value.setText(r1DataBean.getRate_avg());
            this.mTv_rate_max_value.setText(String.valueOf(r1DataBean.getMaxRate()));
            setSportText(this.rl_rate, getContext().getString(R.string.sport_module_avg_step_rate), r1DataBean.getRate_avg(), getContext().getString(R.string.sport_module_avg_step));
            setSportText(this.rl_earth_time, getContext().getString(R.string.sport_module_avg_earth_time), r1DataBean.getEarth_time_avg(), getContext().getString(R.string.sport_module_Running_time));
            setSportText(this.rl_sky_time, getContext().getString(R.string.sport_module_avg_sky_time), r1DataBean.getSky_time_avg(), getContext().getString(R.string.sport_module_Running_time));
            setSportText(this.rl_vertical_time, getContext().getString(R.string.sport_module_avg_vertical_angle), r1DataBean.getVertical_avg(), getContext().getString(R.string.sport_module_unit_cm));
            this.ri_touchdown_txt.setText(getContext().getString(R.string.sport_module_avg_earth_avg));
            this.ri_touchdown_unit.setNumTv(r1DataBean.getEarth_balance());
            this.ri_touchdown_unit.setUnitTv("");
        }
    }
}
