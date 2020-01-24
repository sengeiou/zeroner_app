package com.github.mikephil.charting.formatter;

import com.alibaba.android.arouter.utils.Consts;
import java.text.DecimalFormat;

public class DefaultAxisValueFormatter extends ValueFormatter {
    protected int digits;
    protected DecimalFormat mFormat;

    public DefaultAxisValueFormatter(int digits2) {
        this.digits = digits2;
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits2; i++) {
            if (i == 0) {
                b.append(Consts.DOT);
            }
            b.append("0");
        }
        this.mFormat = new DecimalFormat("###,###,###,##0" + b.toString());
    }

    public String getFormattedValue(float value) {
        return this.mFormat.format((double) value);
    }

    public int getDecimalDigits() {
        return this.digits;
    }
}
