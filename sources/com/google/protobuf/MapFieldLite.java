package com.google.protobuf;

import com.google.protobuf.Internal.EnumLite;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class MapFieldLite<K, V> extends LinkedHashMap<K, V> {
    private static final MapFieldLite EMPTY_MAP_FIELD = new MapFieldLite();
    private boolean isMutable = true;

    private MapFieldLite() {
    }

    private MapFieldLite(Map<K, V> mapData) {
        super(mapData);
    }

    static {
        EMPTY_MAP_FIELD.makeImmutable();
    }

    public static <K, V> MapFieldLite<K, V> emptyMapField() {
        return EMPTY_MAP_FIELD;
    }

    public void mergeFrom(MapFieldLite<K, V> other) {
        ensureMutable();
        if (!other.isEmpty()) {
            putAll(other);
        }
    }

    public Set<Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    public void clear() {
        ensureMutable();
        super.clear();
    }

    public V put(K key, V value) {
        ensureMutable();
        Internal.checkNotNull(key);
        Internal.checkNotNull(value);
        return super.put(key, value);
    }

    public V put(Entry<K, V> entry) {
        return put(entry.getKey(), entry.getValue());
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        ensureMutable();
        checkForNullKeysAndValues(m);
        super.putAll(m);
    }

    public V remove(Object key) {
        ensureMutable();
        return super.remove(key);
    }

    private static void checkForNullKeysAndValues(Map<?, ?> m) {
        for (Object key : m.keySet()) {
            Internal.checkNotNull(key);
            Internal.checkNotNull(m.get(key));
        }
    }

    private static boolean equals(Object a, Object b) {
        if (!(a instanceof byte[]) || !(b instanceof byte[])) {
            return a.equals(b);
        }
        return Arrays.equals((byte[]) a, (byte[]) b);
    }

    static <K, V> boolean equals(Map<K, V> a, Map<K, V> b) {
        if (a == b) {
            return true;
        }
        if (a.size() != b.size()) {
            return false;
        }
        for (Entry<K, V> entry : a.entrySet()) {
            if (!b.containsKey(entry.getKey())) {
                return false;
            }
            if (!equals(entry.getValue(), b.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        return (object instanceof Map) && equals((Map<K, V>) this, (Map) object);
    }

    private static int calculateHashCodeForObject(Object a) {
        if (a instanceof byte[]) {
            return Internal.hashCode((byte[]) (byte[]) a);
        }
        if (!(a instanceof EnumLite)) {
            return a.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    static <K, V> int calculateHashCodeForMap(Map<K, V> a) {
        int result = 0;
        for (Entry<K, V> entry : a.entrySet()) {
            result += calculateHashCodeForObject(entry.getKey()) ^ calculateHashCodeForObject(entry.getValue());
        }
        return result;
    }

    public int hashCode() {
        return calculateHashCodeForMap(this);
    }

    private static Object copy(Object object) {
        if (!(object instanceof byte[])) {
            return object;
        }
        byte[] data = (byte[]) object;
        return Arrays.copyOf(data, data.length);
    }

    static <K, V> Map<K, V> copy(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : map.entrySet()) {
            result.put(entry.getKey(), copy(entry.getValue()));
        }
        return result;
    }

    public MapFieldLite<K, V> mutableCopy() {
        return isEmpty() ? new MapFieldLite<>() : new MapFieldLite<>(this);
    }

    public void makeImmutable() {
        this.isMutable = false;
    }

    public boolean isMutable() {
        return this.isMutable;
    }

    private void ensureMutable() {
        if (!isMutable()) {
            throw new UnsupportedOperationException();
        }
    }
}
