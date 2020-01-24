package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import java.text.DecimalFormat;

public class PercentFormatter extends ValueFormatter {
    public DecimalFormat mFormat;
    private PieChart pieChart;

    public PercentFormatter() {
        this.mFormat = new DecimalFormat("###,###,##0.0");
    }

    public PercentFormatter(PieChart pieChart2) {
        this();
        this.pieChart = pieChart2;
    }

    public String getFormattedValue(float value) {
        return this.mFormat.format((double) value) + " %";
    }

    public String getPieLabel(float value, PieEntry pieEntry) {
        if (this.pieChart == null || !this.pieChart.isUsePercentValuesEnabled()) {
            return this.mFormat.format((double) value);
        }
        return getFormattedValue(value);
    }
}
