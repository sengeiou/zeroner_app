package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E> {
    @GwtTransient
    final Comparator<? super E> comparator;
    private transient SortedMultiset<E> descendingMultiset;

    /* access modifiers changed from: 0000 */
    public abstract Iterator<Entry<E>> descendingEntryIterator();

    AbstractSortedMultiset() {
        this(Ordering.natural());
    }

    AbstractSortedMultiset(Comparator<? super E> comparator2) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator2);
    }

    public SortedSet<E> elementSet() {
        return (SortedSet) super.elementSet();
    }

    /* access modifiers changed from: 0000 */
    public SortedSet<E> createElementSet() {
        return new ElementSet(this);
    }

    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    public Entry<E> firstEntry() {
        Iterator<Entry<E>> entryIterator = entryIterator();
        if (entryIterator.hasNext()) {
            return (Entry) entryIterator.next();
        }
        return null;
    }

    public Entry<E> lastEntry() {
        Iterator<Entry<E>> entryIterator = descendingEntryIterator();
        if (entryIterator.hasNext()) {
            return (Entry) entryIterator.next();
        }
        return null;
    }

    public Entry<E> pollFirstEntry() {
        Iterator<Entry<E>> entryIterator = entryIterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        Entry<E> result = (Entry) entryIterator.next();
        Entry<E> result2 = Multisets.immutableEntry(result.getElement(), result.getCount());
        entryIterator.remove();
        return result2;
    }

    public Entry<E> pollLastEntry() {
        Iterator<Entry<E>> entryIterator = descendingEntryIterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        Entry<E> result = (Entry) entryIterator.next();
        Entry<E> result2 = Multisets.immutableEntry(result.getElement(), result.getCount());
        entryIterator.remove();
        return result2;
    }

    public SortedMultiset<E> subMultiset(@Nullable E fromElement, BoundType fromBoundType, @Nullable E toElement, BoundType toBoundType) {
        Preconditions.checkNotNull(fromBoundType);
        Preconditions.checkNotNull(toBoundType);
        return tailMultiset(fromElement, fromBoundType).headMultiset(toElement, toBoundType);
    }

    /* access modifiers changed from: 0000 */
    public Iterator<E> descendingIterator() {
        return Multisets.iteratorImpl(descendingMultiset());
    }

    public SortedMultiset<E> descendingMultiset() {
        SortedMultiset<E> result = this.descendingMultiset;
        if (result != null) {
            return result;
        }
        SortedMultiset<E> result2 = createDescendingMultiset();
        this.descendingMultiset = result2;
        return result2;
    }

    /* access modifiers changed from: 0000 */
    public SortedMultiset<E> createDescendingMultiset() {
        return new DescendingMultiset<E>() {
            /* access modifiers changed from: 0000 */
            public SortedMultiset<E> forwardMultiset() {
                return AbstractSortedMultiset.this;
            }

            /* access modifiers changed from: 0000 */
            public Iterator<Entry<E>> entryIterator() {
                return AbstractSortedMultiset.this.descendingEntryIterator();
            }

            public Iterator<E> iterator() {
                return AbstractSortedMultiset.this.descendingIterator();
            }
        };
    }
}
