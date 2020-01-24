package com.bigkoo.pickerview.adapter;

public class NumericWheelAdapter implements WheelAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;
    private int maxValue;
    private int minValue;

    public NumericWheelAdapter() {
        this(0, 9);
    }

    public NumericWheelAdapter(int minValue2, int maxValue2) {
        this.minValue = minValue2;
        this.maxValue = maxValue2;
    }

    public Object getItem(int index) {
        if (index < 0 || index >= getItemsCount()) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(this.minValue + index);
    }

    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }

    public int indexOf(Object o) {
        try {
            return ((Integer) o).intValue() - this.minValue;
        } catch (Exception e) {
            return -1;
        }
    }
}
