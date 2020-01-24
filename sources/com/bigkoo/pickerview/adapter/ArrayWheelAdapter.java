package com.bigkoo.pickerview.adapter;

import java.util.List;

public class ArrayWheelAdapter<T> implements WheelAdapter {
    public static final int DEFAULT_LENGTH = 4;
    private List<T> items;
    private int length;

    public ArrayWheelAdapter(List<T> items2, int length2) {
        this.items = items2;
        this.length = length2;
    }

    public ArrayWheelAdapter(List<T> items2) {
        this(items2, 4);
    }

    public Object getItem(int index) {
        if (index < 0 || index >= this.items.size()) {
            return "";
        }
        return this.items.get(index);
    }

    public int getItemsCount() {
        return this.items.size();
    }

    public int indexOf(Object o) {
        return this.items.indexOf(o);
    }
}
