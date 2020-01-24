package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.SortedLists.KeyAbsentBehavior;
import com.google.common.collect.SortedLists.KeyPresentBehavior;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    private final transient ImmutableList<E> elements;

    RegularImmutableSortedSet(ImmutableList<E> elements2, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = elements2;
        Preconditions.checkArgument(!elements2.isEmpty());
    }

    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }

    @GwtIncompatible("NavigableSet")
    public UnmodifiableIterator<E> descendingIterator() {
        return this.elements.reverse().iterator();
    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return this.elements.size();
    }

    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        try {
            return unsafeBinarySearch(o) >= 0;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean containsAll(Collection<?> targets) {
        if (targets instanceof Multiset) {
            targets = ((Multiset) targets).elementSet();
        }
        if (!SortedIterables.hasSameComparator(comparator(), targets) || targets.size() <= 1) {
            return super.containsAll(targets);
        }
        PeekingIterator<E> thisIterator = Iterators.peekingIterator((Iterator<? extends T>) iterator());
        Iterator<?> thatIterator = targets.iterator();
        Object target = thatIterator.next();
        while (thisIterator.hasNext()) {
            try {
                int cmp = unsafeCompare(thisIterator.peek(), target);
                if (cmp < 0) {
                    thisIterator.next();
                } else if (cmp == 0) {
                    if (!thatIterator.hasNext()) {
                        return true;
                    }
                    target = thatIterator.next();
                } else if (cmp > 0) {
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            } catch (ClassCastException e2) {
                return false;
            }
        }
        return false;
    }

    private int unsafeBinarySearch(Object key) throws ClassCastException {
        return Collections.binarySearch(this.elements, key, unsafeComparator());
    }

    /* access modifiers changed from: 0000 */
    public boolean isPartialView() {
        return this.elements.isPartialView();
    }

    /* access modifiers changed from: 0000 */
    public int copyIntoArray(Object[] dst, int offset) {
        return this.elements.copyIntoArray(dst, offset);
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set<?> that = (Set) object;
        if (size() != that.size()) {
            return false;
        }
        if (!SortedIterables.hasSameComparator(this.comparator, that)) {
            return containsAll(that);
        }
        Iterator<?> otherIterator = that.iterator();
        try {
            Iterator<E> iterator = iterator();
            while (iterator.hasNext()) {
                Object element = iterator.next();
                Object otherElement = otherIterator.next();
                if (otherElement != null) {
                    if (unsafeCompare(element, otherElement) != 0) {
                    }
                }
                return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        } catch (NoSuchElementException e2) {
            return false;
        }
    }

    public E first() {
        return this.elements.get(0);
    }

    public E last() {
        return this.elements.get(size() - 1);
    }

    public E lower(E element) {
        int index = headIndex(element, false) - 1;
        if (index == -1) {
            return null;
        }
        return this.elements.get(index);
    }

    public E floor(E element) {
        int index = headIndex(element, true) - 1;
        if (index == -1) {
            return null;
        }
        return this.elements.get(index);
    }

    public E ceiling(E element) {
        int index = tailIndex(element, true);
        if (index == size()) {
            return null;
        }
        return this.elements.get(index);
    }

    public E higher(E element) {
        int index = tailIndex(element, false);
        if (index == size()) {
            return null;
        }
        return this.elements.get(index);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
        return getSubSet(0, headIndex(toElement, inclusive));
    }

    /* access modifiers changed from: 0000 */
    public int headIndex(E toElement, boolean inclusive) {
        return SortedLists.binarySearch((List<? extends E>) this.elements, Preconditions.checkNotNull(toElement), comparator(), inclusive ? KeyPresentBehavior.FIRST_AFTER : KeyPresentBehavior.FIRST_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return tailSetImpl(fromElement, fromInclusive).headSetImpl(toElement, toInclusive);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
        return getSubSet(tailIndex(fromElement, inclusive), size());
    }

    /* access modifiers changed from: 0000 */
    public int tailIndex(E fromElement, boolean inclusive) {
        return SortedLists.binarySearch((List<? extends E>) this.elements, Preconditions.checkNotNull(fromElement), comparator(), inclusive ? KeyPresentBehavior.FIRST_PRESENT : KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER);
    }

    /* access modifiers changed from: 0000 */
    public Comparator<Object> unsafeComparator() {
        return this.comparator;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> getSubSet(int newFromIndex, int newToIndex) {
        if (newFromIndex == 0 && newToIndex == size()) {
            return this;
        }
        if (newFromIndex < newToIndex) {
            return new RegularImmutableSortedSet(this.elements.subList(newFromIndex, newToIndex), this.comparator);
        }
        return emptySet(this.comparator);
    }

    /* access modifiers changed from: 0000 */
    public int indexOf(@Nullable Object target) {
        if (target == null) {
            return -1;
        }
        try {
            int position = SortedLists.binarySearch((List<? extends E>) this.elements, target, unsafeComparator(), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
            if (position < 0) {
                position = -1;
            }
            return position;
        } catch (ClassCastException e) {
            return -1;
        }
    }

    /* access modifiers changed from: 0000 */
    public ImmutableList<E> createAsList() {
        return new ImmutableSortedAsList(this, this.elements);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> createDescendingSet() {
        return new RegularImmutableSortedSet(this.elements.reverse(), Ordering.from(this.comparator).reverse());
    }
}
