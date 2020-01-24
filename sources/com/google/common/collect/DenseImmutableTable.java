package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Table.Cell;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class DenseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
    /* access modifiers changed from: private */
    public final int[] columnCounts = new int[this.columnKeyToIndex.size()];
    /* access modifiers changed from: private */
    public final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableMap<C, Map<R, V>> columnMap;
    private final int[] iterationOrderColumn;
    private final int[] iterationOrderRow;
    /* access modifiers changed from: private */
    public final int[] rowCounts = new int[this.rowKeyToIndex.size()];
    /* access modifiers changed from: private */
    public final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableMap<R, Map<C, V>> rowMap;
    /* access modifiers changed from: private */
    public final V[][] values;

    private static abstract class ImmutableArrayMap<K, V> extends ImmutableMap<K, V> {
        private final int size;

        /* access modifiers changed from: 0000 */
        @Nullable
        public abstract V getValue(int i);

        /* access modifiers changed from: 0000 */
        public abstract ImmutableMap<K, Integer> keyToIndex();

        ImmutableArrayMap(int size2) {
            this.size = size2;
        }

        private boolean isFull() {
            return this.size == keyToIndex().size();
        }

        /* access modifiers changed from: 0000 */
        public K getKey(int index) {
            return keyToIndex().keySet().asList().get(index);
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSet<K> createKeySet() {
            return isFull() ? keyToIndex().keySet() : super.createKeySet();
        }

        public int size() {
            return this.size;
        }

        public V get(@Nullable Object key) {
            Integer keyIndex = (Integer) keyToIndex().get(key);
            if (keyIndex == null) {
                return null;
            }
            return getValue(keyIndex.intValue());
        }

        /* access modifiers changed from: 0000 */
        public ImmutableSet<Entry<K, V>> createEntrySet() {
            return new ImmutableMapEntrySet<K, V>() {
                /* access modifiers changed from: 0000 */
                public ImmutableMap<K, V> map() {
                    return ImmutableArrayMap.this;
                }

                public UnmodifiableIterator<Entry<K, V>> iterator() {
                    return new AbstractIterator<Entry<K, V>>() {
                        private int index = -1;
                        private final int maxIndex = ImmutableArrayMap.this.keyToIndex().size();

                        /* access modifiers changed from: protected */
                        public Entry<K, V> computeNext() {
                            this.index++;
                            while (this.index < this.maxIndex) {
                                V value = ImmutableArrayMap.this.getValue(this.index);
                                if (value != null) {
                                    return Maps.immutableEntry(ImmutableArrayMap.this.getKey(this.index), value);
                                }
                                this.index++;
                            }
                            return (Entry) endOfData();
                        }
                    };
                }
            };
        }
    }

    private final class Column extends ImmutableArrayMap<R, V> {
        private final int columnIndex;

        Column(int columnIndex2) {
            super(DenseImmutableTable.this.columnCounts[columnIndex2]);
            this.columnIndex = columnIndex2;
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<R, Integer> keyToIndex() {
            return DenseImmutableTable.this.rowKeyToIndex;
        }

        /* access modifiers changed from: 0000 */
        public V getValue(int keyIndex) {
            return DenseImmutableTable.this.values[keyIndex][this.columnIndex];
        }

        /* access modifiers changed from: 0000 */
        public boolean isPartialView() {
            return true;
        }
    }

    private final class ColumnMap extends ImmutableArrayMap<C, Map<R, V>> {
        private ColumnMap() {
            super(DenseImmutableTable.this.columnCounts.length);
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<C, Integer> keyToIndex() {
            return DenseImmutableTable.this.columnKeyToIndex;
        }

        /* access modifiers changed from: 0000 */
        public Map<R, V> getValue(int keyIndex) {
            return new Column(keyIndex);
        }

        /* access modifiers changed from: 0000 */
        public boolean isPartialView() {
            return false;
        }
    }

    private final class Row extends ImmutableArrayMap<C, V> {
        private final int rowIndex;

        Row(int rowIndex2) {
            super(DenseImmutableTable.this.rowCounts[rowIndex2]);
            this.rowIndex = rowIndex2;
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<C, Integer> keyToIndex() {
            return DenseImmutableTable.this.columnKeyToIndex;
        }

        /* access modifiers changed from: 0000 */
        public V getValue(int keyIndex) {
            return DenseImmutableTable.this.values[this.rowIndex][keyIndex];
        }

        /* access modifiers changed from: 0000 */
        public boolean isPartialView() {
            return true;
        }
    }

    private final class RowMap extends ImmutableArrayMap<R, Map<C, V>> {
        private RowMap() {
            super(DenseImmutableTable.this.rowCounts.length);
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<R, Integer> keyToIndex() {
            return DenseImmutableTable.this.rowKeyToIndex;
        }

        /* access modifiers changed from: 0000 */
        public Map<C, V> getValue(int keyIndex) {
            return new Row(keyIndex);
        }

        /* access modifiers changed from: 0000 */
        public boolean isPartialView() {
            return false;
        }
    }

    private static <E> ImmutableMap<E, Integer> makeIndex(ImmutableSet<E> set) {
        Builder<E, Integer> indexBuilder = ImmutableMap.builder();
        int i = 0;
        Iterator i$ = set.iterator();
        while (i$.hasNext()) {
            indexBuilder.put(i$.next(), Integer.valueOf(i));
            i++;
        }
        return indexBuilder.build();
    }

    DenseImmutableTable(ImmutableList<Cell<R, C, V>> cellList, ImmutableSet<R> rowSpace, ImmutableSet<C> columnSpace) {
        this.values = (Object[][]) Array.newInstance(Object.class, new int[]{rowSpace.size(), columnSpace.size()});
        this.rowKeyToIndex = makeIndex(rowSpace);
        this.columnKeyToIndex = makeIndex(columnSpace);
        int[] iterationOrderRow2 = new int[cellList.size()];
        int[] iterationOrderColumn2 = new int[cellList.size()];
        for (int i = 0; i < cellList.size(); i++) {
            Cell<R, C, V> cell = (Cell) cellList.get(i);
            R rowKey = cell.getRowKey();
            C columnKey = cell.getColumnKey();
            int rowIndex = ((Integer) this.rowKeyToIndex.get(rowKey)).intValue();
            int columnIndex = ((Integer) this.columnKeyToIndex.get(columnKey)).intValue();
            Preconditions.checkArgument(this.values[rowIndex][columnIndex] == null, "duplicate key: (%s, %s)", rowKey, columnKey);
            this.values[rowIndex][columnIndex] = cell.getValue();
            int[] iArr = this.rowCounts;
            iArr[rowIndex] = iArr[rowIndex] + 1;
            int[] iArr2 = this.columnCounts;
            iArr2[columnIndex] = iArr2[columnIndex] + 1;
            iterationOrderRow2[i] = rowIndex;
            iterationOrderColumn2[i] = columnIndex;
        }
        this.iterationOrderRow = iterationOrderRow2;
        this.iterationOrderColumn = iterationOrderColumn2;
        this.rowMap = new RowMap();
        this.columnMap = new ColumnMap();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return this.columnMap;
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return this.rowMap;
    }

    public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
        Integer rowIndex = (Integer) this.rowKeyToIndex.get(rowKey);
        Integer columnIndex = (Integer) this.columnKeyToIndex.get(columnKey);
        if (rowIndex == null || columnIndex == null) {
            return null;
        }
        return this.values[rowIndex.intValue()][columnIndex.intValue()];
    }

    public int size() {
        return this.iterationOrderRow.length;
    }

    /* access modifiers changed from: 0000 */
    public Cell<R, C, V> getCell(int index) {
        int rowIndex = this.iterationOrderRow[index];
        int columnIndex = this.iterationOrderColumn[index];
        return cellOf(rowKeySet().asList().get(rowIndex), columnKeySet().asList().get(columnIndex), this.values[rowIndex][columnIndex]);
    }

    /* access modifiers changed from: 0000 */
    public V getValue(int index) {
        return this.values[this.iterationOrderRow[index]][this.iterationOrderColumn[index]];
    }
}
