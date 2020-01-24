package com.alibaba.android.arouter.base;

import java.util.TreeMap;

public class UniqueKeyTreeMap<K, V> extends TreeMap<K, V> {
    private String tipText;

    public UniqueKeyTreeMap(String exceptionText) {
        this.tipText = exceptionText;
    }

    public V put(K key, V value) {
        if (!containsKey(key)) {
            return super.put(key, value);
        }
        throw new RuntimeException(String.format(this.tipText, new Object[]{key}));
    }
}
