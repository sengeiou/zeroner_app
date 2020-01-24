package com.iwown.sport_module.ui.af.presenter.chart;

import android.content.Context;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.iwown.sport_module.R;
import java.util.ArrayList;
import java.util.List;

public class ChartImpl implements IChart {
    private Context context;
    private List<Entry> entries = new ArrayList();
    private ScatterChart scatterChart;
    private ScatterDataSet scatterDataSet;

    public ChartImpl(Context context2) {
        this.context = context2;
    }

    public void showChat(ScatterChart scatterChart2) {
        this.scatterChart = scatterChart2;
        initChart();
        initLegend();
        initXAxis();
        initYAxis();
        initScatterDataSet();
    }

    private void initScatterDataSet() {
        this.scatterDataSet = new ScatterDataSet(this.entries, "房颤");
        this.scatterDataSet.setScatterShapeHoleColor(this.context.getApplicationContext().getResources().getColor(R.color.fatigue_history_top));
        this.scatterDataSet.setScatterShape(ScatterShape.CIRCLE);
        this.scatterDataSet.setScatterShapeSize(3.0f);
        this.scatterDataSet.setColor(this.context.getApplicationContext().getResources().getColor(R.color.white));
        this.scatterDataSet.setValueTextColor(this.context.getApplicationContext().getResources().getColor(R.color.transparent));
    }

    public void onDestroy() {
        this.entries.clear();
        this.scatterChart.clearAnimation();
        this.scatterChart.clear();
        this.scatterDataSet.clear();
        this.scatterDataSet = null;
        this.scatterChart = null;
        this.context = null;
    }

    public void setData(List<Entry> entries2) {
        if (entries2.size() == 0) {
            this.scatterChart.clear();
            return;
        }
        this.scatterDataSet.setValues(entries2);
        ScatterData scatterData = new ScatterData(this.scatterDataSet);
        this.scatterChart.animateX(1000);
        this.scatterChart.setData(scatterData);
        this.scatterChart.invalidate();
    }

    private void initChart() {
        this.scatterChart.setBackgroundColor(this.context.getApplicationContext().getResources().getColor(R.color.transparent));
        this.scatterChart.setNoDataText(this.context.getApplicationContext().getResources().getString(R.string.sport_module_no_data));
        this.scatterChart.setDrawGridBackground(false);
        this.scatterChart.setDrawBorders(false);
        this.scatterChart.setTouchEnabled(false);
        Description description = new Description();
        description.setEnabled(false);
        this.scatterChart.setDescription(description);
    }

    private void initLegend() {
        this.scatterChart.getLegend().setEnabled(false);
    }

    private void initXAxis() {
        XAxis xAxis = this.scatterChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setGridColor(this.context.getApplicationContext().getResources().getColor(R.color.white));
        xAxis.enableGridDashedLine(10.0f, 10.0f, 0.0f);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setAxisMaximum(2000.0f);
        xAxis.setAxisMinimum(0.0f);
        xAxis.setLabelCount(4);
        xAxis.setTextColor(this.context.getApplicationContext().getResources().getColor(R.color.white));
    }

    private void initYAxis() {
        YAxis yAxis = this.scatterChart.getAxisLeft();
        yAxis.setGridColor(this.context.getApplicationContext().getResources().getColor(R.color.white));
        yAxis.enableGridDashedLine(10.0f, 10.0f, 0.0f);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(true);
        yAxis.setAxisMaximum(2000.0f);
        yAxis.setTextColor(this.context.getApplicationContext().getResources().getColor(R.color.white));
        yAxis.setAxisMinimum(0.0f);
        yAxis.setLabelCount(4);
        YAxis yAxis1 = this.scatterChart.getAxisRight();
        yAxis1.setDrawLabels(false);
        yAxis1.setGridColor(this.context.getApplicationContext().getResources().getColor(R.color.white));
        yAxis1.setTextColor(this.context.getApplicationContext().getResources().getColor(R.color.white));
        yAxis1.enableGridDashedLine(10.0f, 10.0f, 0.0f);
        yAxis1.setDrawAxisLine(false);
        yAxis1.setAxisMaximum(2000.0f);
        yAxis1.setAxisMinimum(0.0f);
        yAxis1.setLabelCount(4);
    }
}
