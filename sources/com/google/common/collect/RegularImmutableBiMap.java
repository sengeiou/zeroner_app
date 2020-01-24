package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
    static final double MAX_LOAD_FACTOR = 1.2d;
    /* access modifiers changed from: private */
    public final transient ImmutableMapEntry<K, V>[] entries;
    /* access modifiers changed from: private */
    public final transient int hashCode;
    private transient ImmutableBiMap<V, K> inverse;
    private final transient ImmutableMapEntry<K, V>[] keyTable;
    /* access modifiers changed from: private */
    public final transient int mask;
    /* access modifiers changed from: private */
    public final transient ImmutableMapEntry<K, V>[] valueTable;

    private static final class NonTerminalBiMapEntry<K, V> extends ImmutableMapEntry<K, V> {
        @Nullable
        private final ImmutableMapEntry<K, V> nextInKeyBucket;
        @Nullable
        private final ImmutableMapEntry<K, V> nextInValueBucket;

        NonTerminalBiMapEntry(K key, V value, @Nullable ImmutableMapEntry<K, V> nextInKeyBucket2, @Nullable ImmutableMapEntry<K, V> nextInValueBucket2) {
            super(key, value);
            this.nextInKeyBucket = nextInKeyBucket2;
            this.nextInValueBucket = nextInValueBucket2;
        }

        NonTerminalBiMapEntry(ImmutableMapEntry<K, V> contents, @Nullable ImmutableMapEntry<K, V> nextInKeyBucket2, @Nullable ImmutableMapEntry<K, V> nextInValueBucket2) {
            super(contents);
            this.nextInKeyBucket = nextInKeyBucket2;
            this.nextInValueBucket = nextInValueBucket2;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public ImmutableMapEntry<K, V> getNextInKeyBucket() {
            return this.nextInKeyBucket;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public ImmutableMapEntry<K, V> getNextInValueBucket() {
            return this.nextInValueBucket;
        }
    }

    private final class Inverse extends ImmutableBiMap<V, K> {

        final class InverseEntrySet extends ImmutableMapEntrySet<V, K> {
            InverseEntrySet() {
            }

            /* access modifiers changed from: 0000 */
            public ImmutableMap<V, K> map() {
                return Inverse.this;
            }

            /* access modifiers changed from: 0000 */
            public boolean isHashCodeFast() {
                return true;
            }

            public int hashCode() {
                return RegularImmutableBiMap.this.hashCode;
            }

            public UnmodifiableIterator<Entry<V, K>> iterator() {
                return asList().iterator();
            }

            /* access modifiers changed from: 0000 */
            public ImmutableList<Entry<V, K>> createAsList() {
                return new ImmutableAsList<Entry<V, K>>() {
                    public Entry<V, K> get(int index) {
                        Entry<K, V> entry = RegularImmutableBiMap.this.entries[index];
                        return Maps.immutableEntry(entry.getValue(), entry.getKey());
                    }

                    /* access modifiers changed from: 0000 */
                    public ImmutableCollection<Entry<V, K>> delegateCollection() {
                        return InverseEntrySet.this;
                    }
                };
            }
        }

        private Inverse() {
        }

        public int size() {
            return inverse().size();
        }

        public ImmutableBiMap<K, V> inverse() {
            return RegularImmutableBiMap.this;
        }

        public K get(@Nullable Object value) {
            if (value == null) {
                return null;
            }
            for (ImmutableMapEntry<K, V> entry = RegularImmutableBiMap.this.valueTable[Hashing.smear(value.hashCode()) & RegularImmutableBiMap.this.mask]; entry != null; entry = entry.getNextInValueBucket()) {
                if (value.equals(entry.getValue())) {
                    return entry.getKey();
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSet<Entry<V, K>> createEntrySet() {
            return new InverseEntrySet();
        }

        /* access modifiers changed from: 0000 */
        public boolean isPartialView() {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new InverseSerializedForm(RegularImmutableBiMap.this);
        }
    }

    private static class InverseSerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        private final ImmutableBiMap<K, V> forward;

        InverseSerializedForm(ImmutableBiMap<K, V> forward2) {
            this.forward = forward2;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.forward.inverse();
        }
    }

    RegularImmutableBiMap(TerminalEntry<?, ?>... entriesToAdd) {
        this(entriesToAdd.length, entriesToAdd);
    }

    /* JADX WARNING: type inference failed for: r21v0, types: [com.google.common.collect.ImmutableMapEntry[]] */
    /* JADX WARNING: type inference failed for: r14v0, types: [com.google.common.collect.ImmutableMapEntry] */
    /* JADX WARNING: type inference failed for: r10v0 */
    /* JADX WARNING: type inference failed for: r10v1, types: [java.util.Map$Entry, com.google.common.collect.ImmutableMapEntry] */
    /* JADX WARNING: type inference failed for: r15v0, types: [com.google.common.collect.ImmutableMapEntry] */
    /* JADX WARNING: type inference failed for: r19v0 */
    /* JADX WARNING: type inference failed for: r19v1, types: [com.google.common.collect.ImmutableMapEntry] */
    /* JADX WARNING: type inference failed for: r13v0, types: [com.google.common.collect.RegularImmutableBiMap$NonTerminalBiMapEntry] */
    /* JADX WARNING: type inference failed for: r13v1 */
    /* JADX WARNING: type inference failed for: r13v2 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.util.Map$Entry] */
    /* JADX WARNING: type inference failed for: r19v2, types: [com.google.common.collect.ImmutableMapEntry] */
    /* JADX WARNING: type inference failed for: r10v2, types: [com.google.common.collect.ImmutableMapEntry] */
    /* JADX WARNING: type inference failed for: r13v3 */
    /* JADX WARNING: type inference failed for: r19v3 */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    RegularImmutableBiMap(int r25, com.google.common.collect.ImmutableMapEntry.TerminalEntry<?, ?>[] r26) {
        /*
            r24 = this;
            r24.<init>()
            r22 = 4608083138725491507(0x3ff3333333333333, double:1.2)
            r0 = r25
            r1 = r22
            int r16 = com.google.common.collect.Hashing.closedTableSize(r0, r1)
            int r22 = r16 + -1
            r0 = r22
            r1 = r24
            r1.mask = r0
            com.google.common.collect.ImmutableMapEntry[] r12 = createEntryArray(r16)
            com.google.common.collect.ImmutableMapEntry[] r21 = createEntryArray(r16)
            com.google.common.collect.ImmutableMapEntry[] r4 = createEntryArray(r25)
            r6 = 0
            r7 = 0
        L_0x0026:
            r0 = r25
            if (r7 >= r0) goto L_0x00bc
            r5 = r26[r7]
            java.lang.Object r8 = r5.getKey()
            java.lang.Object r17 = r5.getValue()
            int r11 = r8.hashCode()
            int r20 = r17.hashCode()
            int r22 = com.google.common.collect.Hashing.smear(r11)
            r0 = r24
            int r0 = r0.mask
            r23 = r0
            r9 = r22 & r23
            int r22 = com.google.common.collect.Hashing.smear(r20)
            r0 = r24
            int r0 = r0.mask
            r23 = r0
            r18 = r22 & r23
            r14 = r12[r9]
            r10 = r14
        L_0x0057:
            if (r10 == 0) goto L_0x0079
            java.lang.Object r22 = r10.getKey()
            r0 = r22
            boolean r22 = r8.equals(r0)
            if (r22 != 0) goto L_0x0076
            r22 = 1
        L_0x0067:
            java.lang.String r23 = "key"
            r0 = r22
            r1 = r23
            checkNoConflict(r0, r1, r5, r10)
            com.google.common.collect.ImmutableMapEntry r10 = r10.getNextInKeyBucket()
            goto L_0x0057
        L_0x0076:
            r22 = 0
            goto L_0x0067
        L_0x0079:
            r15 = r21[r18]
            r19 = r15
        L_0x007d:
            if (r19 == 0) goto L_0x00a3
            java.lang.Object r22 = r19.getValue()
            r0 = r17
            r1 = r22
            boolean r22 = r0.equals(r1)
            if (r22 != 0) goto L_0x00a0
            r22 = 1
        L_0x008f:
            java.lang.String r23 = "value"
            r0 = r22
            r1 = r23
            r2 = r19
            checkNoConflict(r0, r1, r5, r2)
            com.google.common.collect.ImmutableMapEntry r19 = r19.getNextInValueBucket()
            goto L_0x007d
        L_0x00a0:
            r22 = 0
            goto L_0x008f
        L_0x00a3:
            if (r14 != 0) goto L_0x00b6
            if (r15 != 0) goto L_0x00b6
            r13 = r5
        L_0x00a8:
            r12[r9] = r13
            r21[r18] = r13
            r4[r7] = r13
            r22 = r11 ^ r20
            int r6 = r6 + r22
            int r7 = r7 + 1
            goto L_0x0026
        L_0x00b6:
            com.google.common.collect.RegularImmutableBiMap$NonTerminalBiMapEntry r13 = new com.google.common.collect.RegularImmutableBiMap$NonTerminalBiMapEntry
            r13.<init>(r5, r14, r15)
            goto L_0x00a8
        L_0x00bc:
            r0 = r24
            r0.keyTable = r12
            r0 = r21
            r1 = r24
            r1.valueTable = r0
            r0 = r24
            r0.entries = r4
            r0 = r24
            r0.hashCode = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableBiMap.<init>(int, com.google.common.collect.ImmutableMapEntry$TerminalEntry[]):void");
    }

    RegularImmutableBiMap(Entry<?, ?>[] entriesToAdd) {
        int n = entriesToAdd.length;
        int tableSize = Hashing.closedTableSize(n, MAX_LOAD_FACTOR);
        this.mask = tableSize - 1;
        ImmutableMapEntry<K, V>[] keyTable2 = createEntryArray(tableSize);
        ImmutableMapEntry<K, V>[] valueTable2 = createEntryArray(tableSize);
        ImmutableMapEntry<K, V>[] entries2 = createEntryArray(n);
        int hashCode2 = 0;
        for (int i = 0; i < n; i++) {
            Entry<K, V> entry = entriesToAdd[i];
            K key = entry.getKey();
            V value = entry.getValue();
            CollectPreconditions.checkEntryNotNull(key, value);
            int keyHash = key.hashCode();
            int valueHash = value.hashCode();
            int keyBucket = Hashing.smear(keyHash) & this.mask;
            int valueBucket = Hashing.smear(valueHash) & this.mask;
            ImmutableMapEntry<K, V> nextInKeyBucket = keyTable2[keyBucket];
            for (ImmutableMapEntry<K, V> keyEntry = nextInKeyBucket; keyEntry != null; keyEntry = keyEntry.getNextInKeyBucket()) {
                checkNoConflict(!key.equals(keyEntry.getKey()), "key", entry, keyEntry);
            }
            ImmutableMapEntry<K, V> nextInValueBucket = valueTable2[valueBucket];
            for (ImmutableMapEntry<K, V> valueEntry = nextInValueBucket; valueEntry != null; valueEntry = valueEntry.getNextInValueBucket()) {
                checkNoConflict(!value.equals(valueEntry.getValue()), "value", entry, valueEntry);
            }
            ImmutableMapEntry<K, V> newEntry = (nextInKeyBucket == null && nextInValueBucket == null) ? new TerminalEntry<>(key, value) : new NonTerminalBiMapEntry<>(key, value, nextInKeyBucket, nextInValueBucket);
            keyTable2[keyBucket] = newEntry;
            valueTable2[valueBucket] = newEntry;
            entries2[i] = newEntry;
            hashCode2 += keyHash ^ valueHash;
        }
        this.keyTable = keyTable2;
        this.valueTable = valueTable2;
        this.entries = entries2;
        this.hashCode = hashCode2;
    }

    private static <K, V> ImmutableMapEntry<K, V>[] createEntryArray(int length) {
        return new ImmutableMapEntry[length];
    }

    @Nullable
    public V get(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        for (ImmutableMapEntry<K, V> entry = this.keyTable[Hashing.smear(key.hashCode()) & this.mask]; entry != null; entry = entry.getNextInKeyBucket()) {
            if (key.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSet<Entry<K, V>> createEntrySet() {
        return new ImmutableMapEntrySet<K, V>() {
            /* access modifiers changed from: 0000 */
            public ImmutableMap<K, V> map() {
                return RegularImmutableBiMap.this;
            }

            public UnmodifiableIterator<Entry<K, V>> iterator() {
                return asList().iterator();
            }

            /* access modifiers changed from: 0000 */
            public ImmutableList<Entry<K, V>> createAsList() {
                return new RegularImmutableAsList((ImmutableCollection<E>) this, (Object[]) RegularImmutableBiMap.this.entries);
            }

            /* access modifiers changed from: 0000 */
            public boolean isHashCodeFast() {
                return true;
            }

            public int hashCode() {
                return RegularImmutableBiMap.this.hashCode;
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public boolean isPartialView() {
        return false;
    }

    public int size() {
        return this.entries.length;
    }

    public ImmutableBiMap<V, K> inverse() {
        ImmutableBiMap<V, K> result = this.inverse;
        if (result != null) {
            return result;
        }
        Inverse inverse2 = new Inverse();
        this.inverse = inverse2;
        return inverse2;
    }
}
