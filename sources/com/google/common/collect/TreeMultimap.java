package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public class TreeMultimap<K, V> extends AbstractSortedKeySortedSetMultimap<K, V> {
    @GwtIncompatible("not needed in emulated source")
    private static final long serialVersionUID = 0;
    private transient Comparator<? super K> keyComparator;
    private transient Comparator<? super V> valueComparator;

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(Object x0, Object x1) {
        return super.containsEntry(x0, x1);
    }

    public /* bridge */ /* synthetic */ boolean containsKey(Object x0) {
        return super.containsKey(x0);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object x0) {
        return super.containsValue(x0);
    }

    public /* bridge */ /* synthetic */ Set entries() {
        return super.entries();
    }

    public /* bridge */ /* synthetic */ boolean equals(Object x0) {
        return super.equals(x0);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    public /* bridge */ /* synthetic */ boolean put(Object x0, Object x1) {
        return super.put(x0, x1);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Multimap x0) {
        return super.putAll(x0);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Object x0, Iterable x1) {
        return super.putAll(x0, x1);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object x0, Object x1) {
        return super.remove(x0, x1);
    }

    public /* bridge */ /* synthetic */ SortedSet removeAll(Object x0) {
        return super.removeAll(x0);
    }

    public /* bridge */ /* synthetic */ SortedSet replaceValues(Object x0, Iterable x1) {
        return super.replaceValues(x0, x1);
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create() {
        return new TreeMultimap<>(Ordering.natural(), Ordering.natural());
    }

    public static <K, V> TreeMultimap<K, V> create(Comparator<? super K> keyComparator2, Comparator<? super V> valueComparator2) {
        return new TreeMultimap<>((Comparator) Preconditions.checkNotNull(keyComparator2), (Comparator) Preconditions.checkNotNull(valueComparator2));
    }

    public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new TreeMultimap<>(Ordering.natural(), Ordering.natural(), multimap);
    }

    TreeMultimap(Comparator<? super K> keyComparator2, Comparator<? super V> valueComparator2) {
        super(new TreeMap(keyComparator2));
        this.keyComparator = keyComparator2;
        this.valueComparator = valueComparator2;
    }

    private TreeMultimap(Comparator<? super K> keyComparator2, Comparator<? super V> valueComparator2, Multimap<? extends K, ? extends V> multimap) {
        this(keyComparator2, valueComparator2);
        putAll(multimap);
    }

    /* access modifiers changed from: 0000 */
    public SortedSet<V> createCollection() {
        return new TreeSet(this.valueComparator);
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> createCollection(@Nullable K key) {
        if (key == null) {
            keyComparator().compare(key, key);
        }
        return super.createCollection(key);
    }

    public Comparator<? super K> keyComparator() {
        return this.keyComparator;
    }

    public Comparator<? super V> valueComparator() {
        return this.valueComparator;
    }

    /* access modifiers changed from: 0000 */
    public SortedMap<K, Collection<V>> backingMap() {
        return super.backingMap();
    }

    public SortedSet<V> get(@Nullable K key) {
        return super.get((Object) key);
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> unmodifiableCollectionSubclass(Collection<V> collection) {
        return Collections.unmodifiableSortedSet((SortedSet) collection);
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> wrapCollection(K key, Collection<V> collection) {
        return new WrappedSortedSet(key, (SortedSet) collection, null);
    }

    public SortedSet<K> keySet() {
        return super.keySet();
    }

    /* access modifiers changed from: 0000 */
    public SortedSet<K> createKeySet() {
        return new SortedKeySet(backingMap());
    }

    public SortedMap<K, Collection<V>> asMap() {
        return super.asMap();
    }

    /* access modifiers changed from: 0000 */
    public SortedMap<K, Collection<V>> createAsMap() {
        return new SortedAsMap(backingMap());
    }

    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(keyComparator());
        stream.writeObject(valueComparator());
        Serialization.writeMultimap(this, stream);
    }

    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.keyComparator = (Comparator) Preconditions.checkNotNull((Comparator) stream.readObject());
        this.valueComparator = (Comparator) Preconditions.checkNotNull((Comparator) stream.readObject());
        setMap(new TreeMap(this.keyComparator));
        Serialization.populateMultimap(this, stream);
    }
}
