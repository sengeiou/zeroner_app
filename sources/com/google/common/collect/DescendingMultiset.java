package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset.Entry;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

@GwtCompatible(emulated = true)
abstract class DescendingMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
    private transient Comparator<? super E> comparator;
    private transient SortedSet<E> elementSet;
    private transient Set<Entry<E>> entrySet;

    /* access modifiers changed from: 0000 */
    public abstract Iterator<Entry<E>> entryIterator();

    /* access modifiers changed from: 0000 */
    public abstract SortedMultiset<E> forwardMultiset();

    DescendingMultiset() {
    }

    public Comparator<? super E> comparator() {
        Comparator<? super E> result = this.comparator;
        if (result != null) {
            return result;
        }
        Comparator<? super E> result2 = Ordering.from(forwardMultiset().comparator()).reverse();
        this.comparator = result2;
        return result2;
    }

    public SortedSet<E> elementSet() {
        SortedSet<E> result = this.elementSet;
        if (result != null) {
            return result;
        }
        SortedSet<E> result2 = new ElementSet<>(this);
        this.elementSet = result2;
        return result2;
    }

    public Entry<E> pollFirstEntry() {
        return forwardMultiset().pollLastEntry();
    }

    public Entry<E> pollLastEntry() {
        return forwardMultiset().pollFirstEntry();
    }

    public SortedMultiset<E> headMultiset(E toElement, BoundType boundType) {
        return forwardMultiset().tailMultiset(toElement, boundType).descendingMultiset();
    }

    public SortedMultiset<E> subMultiset(E fromElement, BoundType fromBoundType, E toElement, BoundType toBoundType) {
        return forwardMultiset().subMultiset(toElement, toBoundType, fromElement, fromBoundType).descendingMultiset();
    }

    public SortedMultiset<E> tailMultiset(E fromElement, BoundType boundType) {
        return forwardMultiset().headMultiset(fromElement, boundType).descendingMultiset();
    }

    /* access modifiers changed from: protected */
    public Multiset<E> delegate() {
        return forwardMultiset();
    }

    public SortedMultiset<E> descendingMultiset() {
        return forwardMultiset();
    }

    public Entry<E> firstEntry() {
        return forwardMultiset().lastEntry();
    }

    public Entry<E> lastEntry() {
        return forwardMultiset().firstEntry();
    }

    public Set<Entry<E>> entrySet() {
        Set<Entry<E>> result = this.entrySet;
        if (result != null) {
            return result;
        }
        Set<Entry<E>> result2 = createEntrySet();
        this.entrySet = result2;
        return result2;
    }

    /* access modifiers changed from: 0000 */
    public Set<Entry<E>> createEntrySet() {
        return new EntrySet<E>() {
            /* access modifiers changed from: 0000 */
            public Multiset<E> multiset() {
                return DescendingMultiset.this;
            }

            public Iterator<Entry<E>> iterator() {
                return DescendingMultiset.this.entryIterator();
            }

            public int size() {
                return DescendingMultiset.this.forwardMultiset().entrySet().size();
            }
        };
    }

    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    public Object[] toArray() {
        return standardToArray();
    }

    public <T> T[] toArray(T[] array) {
        return standardToArray(array);
    }

    public String toString() {
        return entrySet().toString();
    }
}
