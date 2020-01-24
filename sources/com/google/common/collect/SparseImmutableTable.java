package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Table.Cell;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class SparseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
    private final ImmutableMap<C, Map<R, V>> columnMap;
    private final int[] iterationOrderColumn;
    private final int[] iterationOrderRow;
    private final ImmutableMap<R, Map<C, V>> rowMap;

    SparseImmutableTable(ImmutableList<Cell<R, C, V>> cellList, ImmutableSet<R> rowSpace, ImmutableSet<C> columnSpace) {
        HashMap newHashMap = Maps.newHashMap();
        LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
        Iterator i$ = rowSpace.iterator();
        while (i$.hasNext()) {
            R row = i$.next();
            newHashMap.put(row, Integer.valueOf(newLinkedHashMap.size()));
            newLinkedHashMap.put(row, new LinkedHashMap());
        }
        Map<C, Map<R, V>> columns = Maps.newLinkedHashMap();
        Iterator i$2 = columnSpace.iterator();
        while (i$2.hasNext()) {
            columns.put(i$2.next(), new LinkedHashMap());
        }
        int[] iterationOrderRow2 = new int[cellList.size()];
        int[] iterationOrderColumn2 = new int[cellList.size()];
        for (int i = 0; i < cellList.size(); i++) {
            Cell<R, C, V> cell = (Cell) cellList.get(i);
            R rowKey = cell.getRowKey();
            C columnKey = cell.getColumnKey();
            V value = cell.getValue();
            iterationOrderRow2[i] = ((Integer) newHashMap.get(rowKey)).intValue();
            Map<C, V> thisRow = (Map) newLinkedHashMap.get(rowKey);
            iterationOrderColumn2[i] = thisRow.size();
            V oldValue = thisRow.put(columnKey, value);
            if (oldValue != null) {
                throw new IllegalArgumentException("Duplicate value for row=" + rowKey + ", column=" + columnKey + ": " + value + ", " + oldValue);
            }
            ((Map) columns.get(columnKey)).put(rowKey, value);
        }
        this.iterationOrderRow = iterationOrderRow2;
        this.iterationOrderColumn = iterationOrderColumn2;
        Builder<R, Map<C, V>> rowBuilder = ImmutableMap.builder();
        for (Entry<R, Map<C, V>> row2 : newLinkedHashMap.entrySet()) {
            rowBuilder.put(row2.getKey(), ImmutableMap.copyOf((Map) row2.getValue()));
        }
        this.rowMap = rowBuilder.build();
        Builder<C, Map<R, V>> columnBuilder = ImmutableMap.builder();
        for (Entry<C, Map<R, V>> col : columns.entrySet()) {
            columnBuilder.put(col.getKey(), ImmutableMap.copyOf((Map) col.getValue()));
        }
        this.columnMap = columnBuilder.build();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return this.columnMap;
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return this.rowMap;
    }

    public int size() {
        return this.iterationOrderRow.length;
    }

    /* access modifiers changed from: 0000 */
    public Cell<R, C, V> getCell(int index) {
        Entry<R, Map<C, V>> rowEntry = (Entry) this.rowMap.entrySet().asList().get(this.iterationOrderRow[index]);
        ImmutableMap<C, V> row = (ImmutableMap) rowEntry.getValue();
        Entry<C, V> colEntry = (Entry) row.entrySet().asList().get(this.iterationOrderColumn[index]);
        return cellOf(rowEntry.getKey(), colEntry.getKey(), colEntry.getValue());
    }

    /* access modifiers changed from: 0000 */
    public V getValue(int index) {
        ImmutableMap<C, V> row = (ImmutableMap) this.rowMap.values().asList().get(this.iterationOrderRow[index]);
        return row.values().asList().get(this.iterationOrderColumn[index]);
    }
}
