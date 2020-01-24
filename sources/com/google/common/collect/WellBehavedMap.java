package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@GwtCompatible
final class WellBehavedMap<K, V> extends ForwardingMap<K, V> {
    private final Map<K, V> delegate;
    private Set<Entry<K, V>> entrySet;

    private final class EntrySet extends EntrySet<K, V> {
        private EntrySet() {
        }

        /* access modifiers changed from: 0000 */
        public Map<K, V> map() {
            return WellBehavedMap.this;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new TransformedIterator<K, Entry<K, V>>(WellBehavedMap.this.keySet().iterator()) {
                /* access modifiers changed from: 0000 */
                public Entry<K, V> transform(final K key) {
                    return new AbstractMapEntry<K, V>() {
                        public K getKey() {
                            return key;
                        }

                        public V getValue() {
                            return WellBehavedMap.this.get(key);
                        }

                        public V setValue(V value) {
                            return WellBehavedMap.this.put(key, value);
                        }
                    };
                }
            };
        }
    }

    private WellBehavedMap(Map<K, V> delegate2) {
        this.delegate = delegate2;
    }

    static <K, V> WellBehavedMap<K, V> wrap(Map<K, V> delegate2) {
        return new WellBehavedMap<>(delegate2);
    }

    /* access modifiers changed from: protected */
    public Map<K, V> delegate() {
        return this.delegate;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        EntrySet entrySet2 = new EntrySet();
        this.entrySet = entrySet2;
        return entrySet2;
    }
}
