package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {
    private static final long serialVersionUID = 1;
    /* access modifiers changed from: private */
    public final transient ConcurrentMap<E, AtomicInteger> countMap;
    private transient EntrySet entrySet;

    private class EntrySet extends EntrySet {
        private EntrySet() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public ConcurrentHashMultiset<E> multiset() {
            return ConcurrentHashMultiset.this;
        }

        public Object[] toArray() {
            return snapshot().toArray();
        }

        public <T> T[] toArray(T[] array) {
            return snapshot().toArray(array);
        }

        private List<Entry<E>> snapshot() {
            List<Entry<E>> list = Lists.newArrayListWithExpectedSize(size());
            Iterators.addAll(list, iterator());
            return list;
        }
    }

    private static class FieldSettersHolder {
        static final FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");

        private FieldSettersHolder() {
        }
    }

    public /* bridge */ /* synthetic */ boolean add(Object x0) {
        return super.add(x0);
    }

    public /* bridge */ /* synthetic */ boolean addAll(Collection x0) {
        return super.addAll(x0);
    }

    public /* bridge */ /* synthetic */ boolean contains(Object x0) {
        return super.contains(x0);
    }

    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ boolean equals(Object x0) {
        return super.equals(x0);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    public /* bridge */ /* synthetic */ boolean remove(Object x0) {
        return super.remove(x0);
    }

    public /* bridge */ /* synthetic */ boolean removeAll(Collection x0) {
        return super.removeAll(x0);
    }

    public /* bridge */ /* synthetic */ boolean retainAll(Collection x0) {
        return super.retainAll(x0);
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset<>(new ConcurrentHashMap());
    }

    public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> elements) {
        ConcurrentHashMultiset<E> multiset = create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }

    @Beta
    public static <E> ConcurrentHashMultiset<E> create(MapMaker mapMaker) {
        return new ConcurrentHashMultiset<>(mapMaker.makeMap());
    }

    @VisibleForTesting
    ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> countMap2) {
        Preconditions.checkArgument(countMap2.isEmpty());
        this.countMap = countMap2;
    }

    public int count(@Nullable Object element) {
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter == null) {
            return 0;
        }
        return existingCounter.get();
    }

    public int size() {
        long sum = 0;
        for (AtomicInteger value : this.countMap.values()) {
            sum += (long) value.get();
        }
        return Ints.saturatedCast(sum);
    }

    public Object[] toArray() {
        return snapshot().toArray();
    }

    public <T> T[] toArray(T[] array) {
        return snapshot().toArray(array);
    }

    private List<E> snapshot() {
        List<E> list = Lists.newArrayListWithExpectedSize(size());
        for (Entry<E> entry : entrySet()) {
            E element = entry.getElement();
            for (int i = entry.getCount(); i > 0; i--) {
                list.add(element);
            }
        }
        return list;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0070, code lost:
        r1 = new java.util.concurrent.atomic.AtomicInteger(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007b, code lost:
        if (r10.countMap.putIfAbsent(r11, r1) == null) goto L_0x000b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int add(E r11, int r12) {
        /*
            r10 = this;
            r6 = 1
            r7 = 0
            com.google.common.base.Preconditions.checkNotNull(r11)
            if (r12 != 0) goto L_0x000c
            int r7 = r10.count(r11)
        L_0x000b:
            return r7
        L_0x000c:
            if (r12 <= 0) goto L_0x0048
            r5 = r6
        L_0x000f:
            java.lang.String r8 = "Invalid occurrences: %s"
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.Integer r9 = java.lang.Integer.valueOf(r12)
            r6[r7] = r9
            com.google.common.base.Preconditions.checkArgument(r5, r8, r6)
        L_0x001d:
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r5 = r10.countMap
            java.lang.Object r0 = com.google.common.collect.Maps.safeGet(r5, r11)
            java.util.concurrent.atomic.AtomicInteger r0 = (java.util.concurrent.atomic.AtomicInteger) r0
            if (r0 != 0) goto L_0x0036
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r5 = r10.countMap
            java.util.concurrent.atomic.AtomicInteger r6 = new java.util.concurrent.atomic.AtomicInteger
            r6.<init>(r12)
            java.lang.Object r0 = r5.putIfAbsent(r11, r6)
            java.util.concurrent.atomic.AtomicInteger r0 = (java.util.concurrent.atomic.AtomicInteger) r0
            if (r0 == 0) goto L_0x000b
        L_0x0036:
            int r3 = r0.get()
            if (r3 == 0) goto L_0x0070
            int r2 = com.google.common.math.IntMath.checkedAdd(r3, r12)     // Catch:{ ArithmeticException -> 0x004a }
            boolean r5 = r0.compareAndSet(r3, r2)     // Catch:{ ArithmeticException -> 0x004a }
            if (r5 == 0) goto L_0x0036
            r7 = r3
            goto L_0x000b
        L_0x0048:
            r5 = r7
            goto L_0x000f
        L_0x004a:
            r4 = move-exception
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Overflow adding "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r12)
            java.lang.String r7 = " occurrences to a count of "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r3)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x0070:
            java.util.concurrent.atomic.AtomicInteger r1 = new java.util.concurrent.atomic.AtomicInteger
            r1.<init>(r12)
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r5 = r10.countMap
            java.lang.Object r5 = r5.putIfAbsent(r11, r1)
            if (r5 == 0) goto L_0x000b
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r5 = r10.countMap
            boolean r5 = r5.replace(r11, r0, r1)
            if (r5 == 0) goto L_0x001d
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.add(java.lang.Object, int):int");
    }

    public int remove(@Nullable Object element, int occurrences) {
        boolean z;
        int oldValue;
        int newValue;
        if (occurrences == 0) {
            return count(element);
        }
        if (occurrences > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Invalid occurrences: %s", Integer.valueOf(occurrences));
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter == null) {
            return 0;
        }
        do {
            oldValue = existingCounter.get();
            if (oldValue == 0) {
                return 0;
            }
            newValue = Math.max(0, oldValue - occurrences);
        } while (!existingCounter.compareAndSet(oldValue, newValue));
        if (newValue == 0) {
            this.countMap.remove(element, existingCounter);
        }
        return oldValue;
    }

    public boolean removeExactly(@Nullable Object element, int occurrences) {
        boolean z;
        int oldValue;
        int newValue;
        if (occurrences == 0) {
            return true;
        }
        if (occurrences > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Invalid occurrences: %s", Integer.valueOf(occurrences));
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter == null) {
            return false;
        }
        do {
            oldValue = existingCounter.get();
            if (oldValue < occurrences) {
                return false;
            }
            newValue = oldValue - occurrences;
        } while (!existingCounter.compareAndSet(oldValue, newValue));
        if (newValue != 0) {
            return true;
        }
        this.countMap.remove(element, existingCounter);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002f, code lost:
        if (r8 != 0) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0033, code lost:
        r1 = new java.util.concurrent.atomic.AtomicInteger(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003e, code lost:
        if (r6.countMap.putIfAbsent(r7, r1) == null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setCount(E r7, int r8) {
        /*
            r6 = this;
            r3 = 0
            com.google.common.base.Preconditions.checkNotNull(r7)
            java.lang.String r4 = "count"
            com.google.common.collect.CollectPreconditions.checkNonnegative(r8, r4)
        L_0x000a:
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r4 = r6.countMap
            java.lang.Object r0 = com.google.common.collect.Maps.safeGet(r4, r7)
            java.util.concurrent.atomic.AtomicInteger r0 = (java.util.concurrent.atomic.AtomicInteger) r0
            if (r0 != 0) goto L_0x0029
            if (r8 != 0) goto L_0x0018
            r2 = r3
        L_0x0017:
            return r2
        L_0x0018:
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r4 = r6.countMap
            java.util.concurrent.atomic.AtomicInteger r5 = new java.util.concurrent.atomic.AtomicInteger
            r5.<init>(r8)
            java.lang.Object r0 = r4.putIfAbsent(r7, r5)
            java.util.concurrent.atomic.AtomicInteger r0 = (java.util.concurrent.atomic.AtomicInteger) r0
            if (r0 != 0) goto L_0x0029
            r2 = r3
            goto L_0x0017
        L_0x0029:
            int r2 = r0.get()
            if (r2 != 0) goto L_0x004a
            if (r8 != 0) goto L_0x0033
            r2 = r3
            goto L_0x0017
        L_0x0033:
            java.util.concurrent.atomic.AtomicInteger r1 = new java.util.concurrent.atomic.AtomicInteger
            r1.<init>(r8)
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r4 = r6.countMap
            java.lang.Object r4 = r4.putIfAbsent(r7, r1)
            if (r4 == 0) goto L_0x0048
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r4 = r6.countMap
            boolean r4 = r4.replace(r7, r0, r1)
            if (r4 == 0) goto L_0x000a
        L_0x0048:
            r2 = r3
            goto L_0x0017
        L_0x004a:
            boolean r4 = r0.compareAndSet(r2, r8)
            if (r4 == 0) goto L_0x0029
            if (r8 != 0) goto L_0x0017
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r3 = r6.countMap
            r3.remove(r7, r0)
            goto L_0x0017
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.setCount(java.lang.Object, int):int");
    }

    public boolean setCount(E element, int expectedOldCount, int newCount) {
        boolean z = false;
        Preconditions.checkNotNull(element);
        CollectPreconditions.checkNonnegative(expectedOldCount, "oldCount");
        CollectPreconditions.checkNonnegative(newCount, "newCount");
        AtomicInteger existingCounter = (AtomicInteger) Maps.safeGet(this.countMap, element);
        if (existingCounter != null) {
            int oldValue = existingCounter.get();
            if (oldValue == expectedOldCount) {
                if (oldValue == 0) {
                    if (newCount == 0) {
                        this.countMap.remove(element, existingCounter);
                        return true;
                    }
                    AtomicInteger newCounter = new AtomicInteger(newCount);
                    if (this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter)) {
                        z = true;
                    }
                    return z;
                } else if (existingCounter.compareAndSet(oldValue, newCount)) {
                    if (newCount != 0) {
                        return true;
                    }
                    this.countMap.remove(element, existingCounter);
                    return true;
                }
            }
            return false;
        } else if (expectedOldCount != 0) {
            return false;
        } else {
            if (newCount == 0 || this.countMap.putIfAbsent(element, new AtomicInteger(newCount)) == null) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public Set<E> createElementSet() {
        final Set<E> delegate = this.countMap.keySet();
        return new ForwardingSet<E>() {
            /* access modifiers changed from: protected */
            public Set<E> delegate() {
                return delegate;
            }

            public boolean contains(@Nullable Object object) {
                return object != null && Collections2.safeContains(delegate, object);
            }

            public boolean containsAll(Collection<?> collection) {
                return standardContainsAll(collection);
            }

            public boolean remove(Object object) {
                return object != null && Collections2.safeRemove(delegate, object);
            }

            public boolean removeAll(Collection<?> c) {
                return standardRemoveAll(c);
            }
        };
    }

    public Set<Entry<E>> entrySet() {
        EntrySet result = this.entrySet;
        if (result != null) {
            return result;
        }
        EntrySet result2 = new EntrySet<>();
        this.entrySet = result2;
        return result2;
    }

    /* access modifiers changed from: 0000 */
    public int distinctElements() {
        return this.countMap.size();
    }

    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<E>> entryIterator() {
        final Iterator<Entry<E>> readOnlyIterator = new AbstractIterator<Entry<E>>() {
            private Iterator<Map.Entry<E, AtomicInteger>> mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();

            /* access modifiers changed from: protected */
            public Entry<E> computeNext() {
                while (this.mapEntries.hasNext()) {
                    Map.Entry<E, AtomicInteger> mapEntry = (Map.Entry) this.mapEntries.next();
                    int count = ((AtomicInteger) mapEntry.getValue()).get();
                    if (count != 0) {
                        return Multisets.immutableEntry(mapEntry.getKey(), count);
                    }
                }
                return (Entry) endOfData();
            }
        };
        return new ForwardingIterator<Entry<E>>() {
            private Entry<E> last;

            /* access modifiers changed from: protected */
            public Iterator<Entry<E>> delegate() {
                return readOnlyIterator;
            }

            public Entry<E> next() {
                this.last = (Entry) super.next();
                return this.last;
            }

            public void remove() {
                boolean z;
                if (this.last != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.checkRemove(z);
                ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
                this.last = null;
            }
        };
    }

    public void clear() {
        this.countMap.clear();
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.countMap);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, (Object) (ConcurrentMap) stream.readObject());
    }
}
