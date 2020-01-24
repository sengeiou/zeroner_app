package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMap<K, V> extends ForwardingObject implements Map<K, V> {

    @Beta
    protected abstract class StandardEntrySet extends EntrySet<K, V> {
        public StandardEntrySet() {
        }

        /* access modifiers changed from: 0000 */
        public Map<K, V> map() {
            return ForwardingMap.this;
        }
    }

    @Beta
    protected class StandardKeySet extends KeySet<K, V> {
        public StandardKeySet() {
            super(ForwardingMap.this);
        }
    }

    @Beta
    protected class StandardValues extends Values<K, V> {
        public StandardValues() {
            super(ForwardingMap.this);
        }
    }

    /* access modifiers changed from: protected */
    public abstract Map<K, V> delegate();

    protected ForwardingMap() {
    }

    public int size() {
        return delegate().size();
    }

    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    public V remove(Object object) {
        return delegate().remove(object);
    }

    public void clear() {
        delegate().clear();
    }

    public boolean containsKey(@Nullable Object key) {
        return delegate().containsKey(key);
    }

    public boolean containsValue(@Nullable Object value) {
        return delegate().containsValue(value);
    }

    public V get(@Nullable Object key) {
        return delegate().get(key);
    }

    public V put(K key, V value) {
        return delegate().put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        delegate().putAll(map);
    }

    public Set<K> keySet() {
        return delegate().keySet();
    }

    public Collection<V> values() {
        return delegate().values();
    }

    public Set<Entry<K, V>> entrySet() {
        return delegate().entrySet();
    }

    public boolean equals(@Nullable Object object) {
        return object == this || delegate().equals(object);
    }

    public int hashCode() {
        return delegate().hashCode();
    }

    /* access modifiers changed from: protected */
    public void standardPutAll(Map<? extends K, ? extends V> map) {
        Maps.putAllImpl(this, map);
    }

    /* access modifiers changed from: protected */
    @Beta
    public V standardRemove(@Nullable Object key) {
        Iterator<Entry<K, V>> entryIterator = entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<K, V> entry = (Entry) entryIterator.next();
            if (Objects.equal(entry.getKey(), key)) {
                V value = entry.getValue();
                entryIterator.remove();
                return value;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void standardClear() {
        Iterators.clear(entrySet().iterator());
    }

    /* access modifiers changed from: protected */
    @Beta
    public boolean standardContainsKey(@Nullable Object key) {
        return Maps.containsKeyImpl(this, key);
    }

    /* access modifiers changed from: protected */
    public boolean standardContainsValue(@Nullable Object value) {
        return Maps.containsValueImpl(this, value);
    }

    /* access modifiers changed from: protected */
    public boolean standardIsEmpty() {
        return !entrySet().iterator().hasNext();
    }

    /* access modifiers changed from: protected */
    public boolean standardEquals(@Nullable Object object) {
        return Maps.equalsImpl(this, object);
    }

    /* access modifiers changed from: protected */
    public int standardHashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    /* access modifiers changed from: protected */
    public String standardToString() {
        return Maps.toStringImpl(this);
    }
}
