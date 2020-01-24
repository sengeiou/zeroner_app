package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class RegularImmutableSortedMap<K, V> extends ImmutableSortedMap<K, V> {
    private final transient RegularImmutableSortedSet<K> keySet;
    /* access modifiers changed from: private */
    public final transient ImmutableList<V> valueList;

    private class EntrySet extends ImmutableMapEntrySet<K, V> {
        private EntrySet() {
        }

        public UnmodifiableIterator<Entry<K, V>> iterator() {
            return asList().iterator();
        }

        /* access modifiers changed from: 0000 */
        public ImmutableList<Entry<K, V>> createAsList() {
            return new ImmutableAsList<Entry<K, V>>() {
                private final ImmutableList<K> keyList = RegularImmutableSortedMap.this.keySet().asList();

                public Entry<K, V> get(int index) {
                    return Maps.immutableEntry(this.keyList.get(index), RegularImmutableSortedMap.this.valueList.get(index));
                }

                /* access modifiers changed from: 0000 */
                public ImmutableCollection<Entry<K, V>> delegateCollection() {
                    return EntrySet.this;
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<K, V> map() {
            return RegularImmutableSortedMap.this;
        }
    }

    RegularImmutableSortedMap(RegularImmutableSortedSet<K> keySet2, ImmutableList<V> valueList2) {
        this.keySet = keySet2;
        this.valueList = valueList2;
    }

    RegularImmutableSortedMap(RegularImmutableSortedSet<K> keySet2, ImmutableList<V> valueList2, ImmutableSortedMap<K, V> descendingMap) {
        super(descendingMap);
        this.keySet = keySet2;
        this.valueList = valueList2;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSet<Entry<K, V>> createEntrySet() {
        return new EntrySet();
    }

    public ImmutableSortedSet<K> keySet() {
        return this.keySet;
    }

    public ImmutableCollection<V> values() {
        return this.valueList;
    }

    public V get(@Nullable Object key) {
        int index = this.keySet.indexOf(key);
        if (index == -1) {
            return null;
        }
        return this.valueList.get(index);
    }

    private ImmutableSortedMap<K, V> getSubMap(int fromIndex, int toIndex) {
        if (fromIndex == 0 && toIndex == size()) {
            return this;
        }
        if (fromIndex == toIndex) {
            return emptyMap(comparator());
        }
        return from(this.keySet.getSubSet(fromIndex, toIndex), this.valueList.subList(fromIndex, toIndex));
    }

    public ImmutableSortedMap<K, V> headMap(K toKey, boolean inclusive) {
        return getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(toKey), inclusive));
    }

    public ImmutableSortedMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(fromKey), inclusive), size());
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedMap<K, V> createDescendingMap() {
        return new RegularImmutableSortedMap((RegularImmutableSortedSet) this.keySet.descendingSet(), this.valueList.reverse(), this);
    }
}
