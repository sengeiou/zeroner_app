package com.iwown.sport_module.ui.af.presenter.chart;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import java.util.List;

public interface IChart {
    void onDestroy();

    void setData(List<Entry> list);

    void showChat(ScatterChart scatterChart);
}
