package com.alibaba.fastjson.util;

import java.lang.reflect.Type;

public class IdentityHashMap<V> {
    private final Entry<V>[] buckets;
    private final int indexMask;

    protected static final class Entry<V> {
        public final int hashCode;
        public final Type key;
        public final Entry<V> next;
        public V value;

        public Entry(Type key2, V value2, int hash, Entry<V> next2) {
            this.key = key2;
            this.value = value2;
            this.next = next2;
            this.hashCode = hash;
        }
    }

    public IdentityHashMap(int tableSize) {
        this.indexMask = tableSize - 1;
        this.buckets = new Entry[tableSize];
    }

    public final V get(Type key) {
        for (Entry<V> entry = this.buckets[System.identityHashCode(key) & this.indexMask]; entry != null; entry = entry.next) {
            if (key == entry.key) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean put(Type key, V value) {
        int hash = System.identityHashCode(key);
        int bucket = hash & this.indexMask;
        for (Entry<V> entry = this.buckets[bucket]; entry != null; entry = entry.next) {
            if (key == entry.key) {
                entry.value = value;
                return true;
            }
        }
        this.buckets[bucket] = new Entry<>(key, value, hash, this.buckets[bucket]);
        return false;
    }

    public Class findClass(String keyString) {
        for (Entry<V> entry : this.buckets) {
            if (entry != null) {
                for (Entry<V> entry2 = entry; entry2 != null; entry2 = entry2.next) {
                    Type key = entry.key;
                    if (key instanceof Class) {
                        Class clazz = (Class) key;
                        if (clazz.getName().equals(keyString)) {
                            return clazz;
                        }
                    }
                }
                continue;
            }
        }
        return null;
    }
}
