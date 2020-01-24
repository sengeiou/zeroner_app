package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableMultiset<E> extends ImmutableCollection<E> implements Multiset<E> {
    private static final ImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset(ImmutableMap.of(), 0);
    private transient ImmutableSet<Entry<E>> entrySet;

    public static class Builder<E> extends com.google.common.collect.ImmutableCollection.Builder<E> {
        final Multiset<E> contents;

        public Builder() {
            this(LinkedHashMultiset.create());
        }

        Builder(Multiset<E> contents2) {
            this.contents = contents2;
        }

        public Builder<E> add(E element) {
            this.contents.add(Preconditions.checkNotNull(element));
            return this;
        }

        public Builder<E> addCopies(E element, int occurrences) {
            this.contents.add(Preconditions.checkNotNull(element), occurrences);
            return this;
        }

        public Builder<E> setCount(E element, int count) {
            this.contents.setCount(Preconditions.checkNotNull(element), count);
            return this;
        }

        public Builder<E> add(E... elements) {
            super.add(elements);
            return this;
        }

        public Builder<E> addAll(Iterable<? extends E> elements) {
            if (elements instanceof Multiset) {
                for (Entry<? extends E> entry : Multisets.cast(elements).entrySet()) {
                    addCopies(entry.getElement(), entry.getCount());
                }
            } else {
                super.addAll(elements);
            }
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> elements) {
            super.addAll(elements);
            return this;
        }

        public ImmutableMultiset<E> build() {
            return ImmutableMultiset.copyOf((Iterable<? extends E>) this.contents);
        }
    }

    private final class EntrySet extends ImmutableSet<Entry<E>> {
        private static final long serialVersionUID = 0;

        private EntrySet() {
        }

        /* access modifiers changed from: 0000 */
        public boolean isPartialView() {
            return ImmutableMultiset.this.isPartialView();
        }

        public UnmodifiableIterator<Entry<E>> iterator() {
            return asList().iterator();
        }

        /* access modifiers changed from: 0000 */
        public ImmutableList<Entry<E>> createAsList() {
            return new ImmutableAsList<Entry<E>>() {
                public Entry<E> get(int index) {
                    return ImmutableMultiset.this.getEntry(index);
                }

                /* access modifiers changed from: 0000 */
                public ImmutableCollection<Entry<E>> delegateCollection() {
                    return EntrySet.this;
                }
            };
        }

        public int size() {
            return ImmutableMultiset.this.elementSet().size();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry<?> entry = (Entry) o;
            if (entry.getCount() > 0 && ImmutableMultiset.this.count(entry.getElement()) == entry.getCount()) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ImmutableMultiset.this.hashCode();
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new EntrySetSerializedForm(ImmutableMultiset.this);
        }
    }

    static class EntrySetSerializedForm<E> implements Serializable {
        final ImmutableMultiset<E> multiset;

        EntrySetSerializedForm(ImmutableMultiset<E> multiset2) {
            this.multiset = multiset2;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.multiset.entrySet();
        }
    }

    private static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final int[] counts;
        final Object[] elements;

        SerializedForm(Multiset<?> multiset) {
            int distinct = multiset.entrySet().size();
            this.elements = new Object[distinct];
            this.counts = new int[distinct];
            int i = 0;
            for (Entry<?> entry : multiset.entrySet()) {
                this.elements[i] = entry.getElement();
                this.counts[i] = entry.getCount();
                i++;
            }
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            LinkedHashMultiset<Object> multiset = LinkedHashMultiset.create(this.elements.length);
            for (int i = 0; i < this.elements.length; i++) {
                multiset.add(this.elements[i], this.counts[i]);
            }
            return ImmutableMultiset.copyOf((Iterable<? extends E>) multiset);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract Entry<E> getEntry(int i);

    public static <E> ImmutableMultiset<E> of() {
        return EMPTY;
    }

    public static <E> ImmutableMultiset<E> of(E element) {
        return copyOfInternal((E[]) new Object[]{element});
    }

    public static <E> ImmutableMultiset<E> of(E e1, E e2) {
        return copyOfInternal((E[]) new Object[]{e1, e2});
    }

    public static <E> ImmutableMultiset<E> of(E e1, E e2, E e3) {
        return copyOfInternal((E[]) new Object[]{e1, e2, e3});
    }

    public static <E> ImmutableMultiset<E> of(E e1, E e2, E e3, E e4) {
        return copyOfInternal((E[]) new Object[]{e1, e2, e3, e4});
    }

    public static <E> ImmutableMultiset<E> of(E e1, E e2, E e3, E e4, E e5) {
        return copyOfInternal((E[]) new Object[]{e1, e2, e3, e4, e5});
    }

    public static <E> ImmutableMultiset<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E... others) {
        return new Builder().add((Object) e1).add((Object) e2).add((Object) e3).add((Object) e4).add((Object) e5).add((Object) e6).add((Object[]) others).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(E[] elements) {
        return copyOf((Iterable<? extends E>) Arrays.asList(elements));
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> elements) {
        if (elements instanceof ImmutableMultiset) {
            ImmutableMultiset<E> result = (ImmutableMultiset) elements;
            if (!result.isPartialView()) {
                return result;
            }
        }
        return copyOfInternal(elements instanceof Multiset ? Multisets.cast(elements) : LinkedHashMultiset.create(elements));
    }

    private static <E> ImmutableMultiset<E> copyOfInternal(E... elements) {
        return copyOf((Iterable<? extends E>) Arrays.asList(elements));
    }

    private static <E> ImmutableMultiset<E> copyOfInternal(Multiset<? extends E> multiset) {
        return copyFromEntries(multiset.entrySet());
    }

    static <E> ImmutableMultiset<E> copyFromEntries(Collection<? extends Entry<? extends E>> entries) {
        long size = 0;
        com.google.common.collect.ImmutableMap.Builder<E, Integer> builder = ImmutableMap.builder();
        for (Entry<? extends E> entry : entries) {
            int count = entry.getCount();
            if (count > 0) {
                builder.put(entry.getElement(), Integer.valueOf(count));
                size += (long) count;
            }
        }
        if (size == 0) {
            return of();
        }
        return new RegularImmutableMultiset(builder.build(), Ints.saturatedCast(size));
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> elements) {
        Multiset<E> multiset = LinkedHashMultiset.create();
        Iterators.addAll(multiset, elements);
        return copyOfInternal(multiset);
    }

    ImmutableMultiset() {
    }

    public UnmodifiableIterator<E> iterator() {
        final Iterator<Entry<E>> entryIterator = entrySet().iterator();
        return new UnmodifiableIterator<E>() {
            E element;
            int remaining;

            public boolean hasNext() {
                return this.remaining > 0 || entryIterator.hasNext();
            }

            public E next() {
                if (this.remaining <= 0) {
                    Entry<E> entry = (Entry) entryIterator.next();
                    this.element = entry.getElement();
                    this.remaining = entry.getCount();
                }
                this.remaining--;
                return this.element;
            }
        };
    }

    public boolean contains(@Nullable Object object) {
        return count(object) > 0;
    }

    public boolean containsAll(Collection<?> targets) {
        return elementSet().containsAll(targets);
    }

    @Deprecated
    public final int add(E e, int occurrences) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final int remove(Object element, int occurrences) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final int setCount(E e, int count) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean setCount(E e, int oldCount, int newCount) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible("not present in emulated superclass")
    public int copyIntoArray(Object[] dst, int offset) {
        Iterator i$ = entrySet().iterator();
        while (i$.hasNext()) {
            Entry<E> entry = (Entry) i$.next();
            Arrays.fill(dst, offset, entry.getCount() + offset, entry.getElement());
            offset += entry.getCount();
        }
        return offset;
    }

    public boolean equals(@Nullable Object object) {
        return Multisets.equalsImpl(this, object);
    }

    public int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    public String toString() {
        return entrySet().toString();
    }

    public ImmutableSet<Entry<E>> entrySet() {
        ImmutableSet<Entry<E>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        ImmutableSet<Entry<E>> es2 = createEntrySet();
        this.entrySet = es2;
        return es2;
    }

    private final ImmutableSet<Entry<E>> createEntrySet() {
        return isEmpty() ? ImmutableSet.of() : new EntrySet();
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }
}
