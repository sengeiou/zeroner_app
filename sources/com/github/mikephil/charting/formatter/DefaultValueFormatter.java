package com.github.mikephil.charting.formatter;

import com.alibaba.android.arouter.utils.Consts;
import java.text.DecimalFormat;

public class DefaultValueFormatter extends ValueFormatter {
    protected int mDecimalDigits;
    protected DecimalFormat mFormat;

    public DefaultValueFormatter(int digits) {
        setup(digits);
    }

    public void setup(int digits) {
        this.mDecimalDigits = digits;
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits; i++) {
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
        return this.mDecimalDigits;
    }
}
