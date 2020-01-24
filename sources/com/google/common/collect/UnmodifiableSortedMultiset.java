package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset.Entry;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;

@GwtCompatible(emulated = true)
final class UnmodifiableSortedMultiset<E> extends UnmodifiableMultiset<E> implements SortedMultiset<E> {
    private static final long serialVersionUID = 0;
    private transient UnmodifiableSortedMultiset<E> descendingMultiset;

    UnmodifiableSortedMultiset(SortedMultiset<E> delegate) {
        super(delegate);
    }

    /* access modifiers changed from: protected */
    public SortedMultiset<E> delegate() {
        return (SortedMultiset) super.delegate();
    }

    public Comparator<? super E> comparator() {
        return delegate().comparator();
    }

    /* access modifiers changed from: 0000 */
    public SortedSet<E> createElementSet() {
        return Collections.unmodifiableSortedSet(delegate().elementSet());
    }

    public SortedSet<E> elementSet() {
        return (SortedSet) super.elementSet();
    }

    public SortedMultiset<E> descendingMultiset() {
        UnmodifiableSortedMultiset<E> result = this.descendingMultiset;
        if (result != null) {
            return result;
        }
        UnmodifiableSortedMultiset<E> result2 = new UnmodifiableSortedMultiset<>(delegate().descendingMultiset());
        result2.descendingMultiset = this;
        this.descendingMultiset = result2;
        return result2;
    }

    public Entry<E> firstEntry() {
        return delegate().firstEntry();
    }

    public Entry<E> lastEntry() {
        return delegate().lastEntry();
    }

    public Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    public Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public SortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().headMultiset(upperBound, boundType));
    }

    public SortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().subMultiset(lowerBound, lowerBoundType, upperBound, upperBoundType));
    }

    public SortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().tailMultiset(lowerBound, boundType));
    }
}
