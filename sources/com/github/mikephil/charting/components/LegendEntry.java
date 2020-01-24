package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.utils.ColorTemplate;

public class LegendEntry {
    public LegendForm form = LegendForm.DEFAULT;
    public int formColor = ColorTemplate.COLOR_NONE;
    public DashPathEffect formLineDashEffect = null;
    public float formLineWidth = Float.NaN;
    public float formSize = Float.NaN;
    public String label;

    public LegendEntry() {
    }

    public LegendEntry(String label2, LegendForm form2, float formSize2, float formLineWidth2, DashPathEffect formLineDashEffect2, int formColor2) {
        this.label = label2;
        this.form = form2;
        this.formSize = formSize2;
        this.formLineWidth = formLineWidth2;
        this.formLineDashEffect = formLineDashEffect2;
        this.formColor = formColor2;
    }
}
