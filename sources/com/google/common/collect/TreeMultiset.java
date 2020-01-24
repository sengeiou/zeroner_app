package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class TreeMultiset<E> extends AbstractSortedMultiset<E> implements Serializable {
    @GwtIncompatible("not needed in emulated source")
    private static final long serialVersionUID = 1;
    /* access modifiers changed from: private */
    public final transient AvlNode<E> header;
    /* access modifiers changed from: private */
    public final transient GeneralRange<E> range;
    private final transient Reference<AvlNode<E>> rootReference;

    private static final class AvlNode<E> extends AbstractEntry<E> {
        /* access modifiers changed from: private */
        public int distinctElements;
        /* access modifiers changed from: private */
        @Nullable
        public final E elem;
        /* access modifiers changed from: private */
        public int elemCount;
        private int height;
        /* access modifiers changed from: private */
        public AvlNode<E> left;
        /* access modifiers changed from: private */
        public AvlNode<E> pred;
        /* access modifiers changed from: private */
        public AvlNode<E> right;
        /* access modifiers changed from: private */
        public AvlNode<E> succ;
        /* access modifiers changed from: private */
        public long totalCount;

        AvlNode(@Nullable E elem2, int elemCount2) {
            Preconditions.checkArgument(elemCount2 > 0);
            this.elem = elem2;
            this.elemCount = elemCount2;
            this.totalCount = (long) elemCount2;
            this.distinctElements = 1;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        public int count(Comparator<? super E> comparator, E e) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                if (this.left == null) {
                    return 0;
                }
                return this.left.count(comparator, e);
            } else if (cmp <= 0) {
                return this.elemCount;
            } else {
                if (this.right != null) {
                    return this.right.count(comparator, e);
                }
                return 0;
            }
        }

        private AvlNode<E> addRightChild(E e, int count) {
            this.right = new AvlNode<>(e, count);
            TreeMultiset.successor(this, this.right, this.succ);
            this.height = Math.max(2, this.height);
            this.distinctElements++;
            this.totalCount += (long) count;
            return this;
        }

        private AvlNode<E> addLeftChild(E e, int count) {
            this.left = new AvlNode<>(e, count);
            TreeMultiset.successor(this.pred, this.left, this);
            this.height = Math.max(2, this.height);
            this.distinctElements++;
            this.totalCount += (long) count;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public AvlNode<E> add(Comparator<? super E> comparator, @Nullable E e, int count, int[] result) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> initLeft = this.left;
                if (initLeft == null) {
                    result[0] = 0;
                    return addLeftChild(e, count);
                }
                int initHeight = initLeft.height;
                this.left = initLeft.add(comparator, e, count, result);
                if (result[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) count;
                if (this.left.height != initHeight) {
                    return rebalance();
                }
                return this;
            } else if (cmp > 0) {
                AvlNode<E> initRight = this.right;
                if (initRight == null) {
                    result[0] = 0;
                    return addRightChild(e, count);
                }
                int initHeight2 = initRight.height;
                this.right = initRight.add(comparator, e, count, result);
                if (result[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) count;
                if (this.right.height != initHeight2) {
                    return rebalance();
                }
                return this;
            } else {
                result[0] = this.elemCount;
                Preconditions.checkArgument(((long) this.elemCount) + ((long) count) <= 2147483647L);
                this.elemCount += count;
                this.totalCount += (long) count;
                return this;
            }
        }

        /* access modifiers changed from: 0000 */
        public AvlNode<E> remove(Comparator<? super E> comparator, @Nullable E e, int count, int[] result) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> initLeft = this.left;
                if (initLeft == null) {
                    result[0] = 0;
                    return this;
                }
                this.left = initLeft.remove(comparator, e, count, result);
                if (result[0] > 0) {
                    if (count >= result[0]) {
                        this.distinctElements--;
                        this.totalCount -= (long) result[0];
                    } else {
                        this.totalCount -= (long) count;
                    }
                }
                if (result[0] != 0) {
                    return rebalance();
                }
                return this;
            } else if (cmp > 0) {
                AvlNode<E> initRight = this.right;
                if (initRight == null) {
                    result[0] = 0;
                    return this;
                }
                this.right = initRight.remove(comparator, e, count, result);
                if (result[0] > 0) {
                    if (count >= result[0]) {
                        this.distinctElements--;
                        this.totalCount -= (long) result[0];
                    } else {
                        this.totalCount -= (long) count;
                    }
                }
                return rebalance();
            } else {
                result[0] = this.elemCount;
                if (count >= this.elemCount) {
                    return deleteMe();
                }
                this.elemCount -= count;
                this.totalCount -= (long) count;
                return this;
            }
        }

        /* access modifiers changed from: 0000 */
        public AvlNode<E> setCount(Comparator<? super E> comparator, @Nullable E e, int count, int[] result) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> initLeft = this.left;
                if (initLeft == null) {
                    result[0] = 0;
                    return count > 0 ? addLeftChild(e, count) : this;
                }
                this.left = initLeft.setCount(comparator, e, count, result);
                if (count == 0 && result[0] != 0) {
                    this.distinctElements--;
                } else if (count > 0 && result[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) (count - result[0]);
                return rebalance();
            } else if (cmp > 0) {
                AvlNode<E> initRight = this.right;
                if (initRight == null) {
                    result[0] = 0;
                    if (count > 0) {
                        return addRightChild(e, count);
                    }
                    return this;
                }
                this.right = initRight.setCount(comparator, e, count, result);
                if (count == 0 && result[0] != 0) {
                    this.distinctElements--;
                } else if (count > 0 && result[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) (count - result[0]);
                return rebalance();
            } else {
                result[0] = this.elemCount;
                if (count == 0) {
                    return deleteMe();
                }
                this.totalCount += (long) (count - this.elemCount);
                this.elemCount = count;
                return this;
            }
        }

        /* access modifiers changed from: 0000 */
        public AvlNode<E> setCount(Comparator<? super E> comparator, @Nullable E e, int expectedCount, int newCount, int[] result) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                AvlNode<E> initLeft = this.left;
                if (initLeft == null) {
                    result[0] = 0;
                    if (expectedCount != 0 || newCount <= 0) {
                        return this;
                    }
                    return addLeftChild(e, newCount);
                }
                this.left = initLeft.setCount(comparator, e, expectedCount, newCount, result);
                if (result[0] == expectedCount) {
                    if (newCount == 0 && result[0] != 0) {
                        this.distinctElements--;
                    } else if (newCount > 0 && result[0] == 0) {
                        this.distinctElements++;
                    }
                    this.totalCount += (long) (newCount - result[0]);
                }
                return rebalance();
            } else if (cmp > 0) {
                AvlNode<E> initRight = this.right;
                if (initRight == null) {
                    result[0] = 0;
                    if (expectedCount != 0 || newCount <= 0) {
                        return this;
                    }
                    return addRightChild(e, newCount);
                }
                this.right = initRight.setCount(comparator, e, expectedCount, newCount, result);
                if (result[0] == expectedCount) {
                    if (newCount == 0 && result[0] != 0) {
                        this.distinctElements--;
                    } else if (newCount > 0 && result[0] == 0) {
                        this.distinctElements++;
                    }
                    this.totalCount += (long) (newCount - result[0]);
                }
                return rebalance();
            } else {
                result[0] = this.elemCount;
                if (expectedCount != this.elemCount) {
                    return this;
                }
                if (newCount == 0) {
                    return deleteMe();
                }
                this.totalCount += (long) (newCount - this.elemCount);
                this.elemCount = newCount;
                return this;
            }
        }

        private AvlNode<E> deleteMe() {
            int oldElemCount = this.elemCount;
            this.elemCount = 0;
            TreeMultiset.successor(this.pred, this.succ);
            if (this.left == null) {
                return this.right;
            }
            if (this.right == null) {
                return this.left;
            }
            if (this.left.height >= this.right.height) {
                AvlNode<E> newTop = this.pred;
                newTop.left = this.left.removeMax(newTop);
                newTop.right = this.right;
                newTop.distinctElements = this.distinctElements - 1;
                newTop.totalCount = this.totalCount - ((long) oldElemCount);
                return newTop.rebalance();
            }
            AvlNode<E> newTop2 = this.succ;
            newTop2.right = this.right.removeMin(newTop2);
            newTop2.left = this.left;
            newTop2.distinctElements = this.distinctElements - 1;
            newTop2.totalCount = this.totalCount - ((long) oldElemCount);
            return newTop2.rebalance();
        }

        private AvlNode<E> removeMin(AvlNode<E> node) {
            if (this.left == null) {
                return this.right;
            }
            this.left = this.left.removeMin(node);
            this.distinctElements--;
            this.totalCount -= (long) node.elemCount;
            return rebalance();
        }

        private AvlNode<E> removeMax(AvlNode<E> node) {
            if (this.right == null) {
                return this.left;
            }
            this.right = this.right.removeMax(node);
            this.distinctElements--;
            this.totalCount -= (long) node.elemCount;
            return rebalance();
        }

        private void recomputeMultiset() {
            this.distinctElements = TreeMultiset.distinctElements(this.left) + 1 + TreeMultiset.distinctElements(this.right);
            this.totalCount = ((long) this.elemCount) + totalCount(this.left) + totalCount(this.right);
        }

        private void recomputeHeight() {
            this.height = Math.max(height(this.left), height(this.right)) + 1;
        }

        private void recompute() {
            recomputeMultiset();
            recomputeHeight();
        }

        private AvlNode<E> rebalance() {
            switch (balanceFactor()) {
                case -2:
                    if (this.right.balanceFactor() > 0) {
                        this.right = this.right.rotateRight();
                    }
                    return rotateLeft();
                case 2:
                    if (this.left.balanceFactor() < 0) {
                        this.left = this.left.rotateLeft();
                    }
                    return rotateRight();
                default:
                    recomputeHeight();
                    return this;
            }
        }

        private int balanceFactor() {
            return height(this.left) - height(this.right);
        }

        private AvlNode<E> rotateLeft() {
            Preconditions.checkState(this.right != null);
            AvlNode<E> newTop = this.right;
            this.right = newTop.left;
            newTop.left = this;
            newTop.totalCount = this.totalCount;
            newTop.distinctElements = this.distinctElements;
            recompute();
            newTop.recomputeHeight();
            return newTop;
        }

        private AvlNode<E> rotateRight() {
            Preconditions.checkState(this.left != null);
            AvlNode<E> newTop = this.left;
            this.left = newTop.right;
            newTop.right = this;
            newTop.totalCount = this.totalCount;
            newTop.distinctElements = this.distinctElements;
            recompute();
            newTop.recomputeHeight();
            return newTop;
        }

        private static long totalCount(@Nullable AvlNode<?> node) {
            if (node == null) {
                return 0;
            }
            return node.totalCount;
        }

        private static int height(@Nullable AvlNode<?> node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        /* access modifiers changed from: private */
        @Nullable
        public AvlNode<E> ceiling(Comparator<? super E> comparator, E e) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp < 0) {
                if (this.left == null) {
                    return this;
                }
                return (AvlNode) Objects.firstNonNull(this.left.ceiling(comparator, e), this);
            } else if (cmp == 0) {
                return this;
            } else {
                return this.right == null ? null : this.right.ceiling(comparator, e);
            }
        }

        /* access modifiers changed from: private */
        @Nullable
        public AvlNode<E> floor(Comparator<? super E> comparator, E e) {
            int cmp = comparator.compare(e, this.elem);
            if (cmp > 0) {
                if (this.right == null) {
                    return this;
                }
                return (AvlNode) Objects.firstNonNull(this.right.floor(comparator, e), this);
            } else if (cmp == 0) {
                return this;
            } else {
                return this.left == null ? null : this.left.floor(comparator, e);
            }
        }

        public E getElement() {
            return this.elem;
        }

        public int getCount() {
            return this.elemCount;
        }

        public String toString() {
            return Multisets.immutableEntry(getElement(), getCount()).toString();
        }
    }

    private static final class Reference<T> {
        @Nullable
        private T value;

        private Reference() {
        }

        @Nullable
        public T get() {
            return this.value;
        }

        public void checkAndSet(@Nullable T expected, T newValue) {
            if (this.value != expected) {
                throw new ConcurrentModificationException();
            }
            this.value = newValue;
        }
    }

    private enum Aggregate {
        SIZE {
            /* access modifiers changed from: 0000 */
            public int nodeAggregate(AvlNode<?> node) {
                return node.elemCount;
            }

            /* access modifiers changed from: 0000 */
            public long treeAggregate(@Nullable AvlNode<?> root) {
                if (root == null) {
                    return 0;
                }
                return root.totalCount;
            }
        },
        DISTINCT {
            /* access modifiers changed from: 0000 */
            public int nodeAggregate(AvlNode<?> avlNode) {
                return 1;
            }

            /* access modifiers changed from: 0000 */
            public long treeAggregate(@Nullable AvlNode<?> root) {
                if (root == null) {
                    return 0;
                }
                return (long) root.distinctElements;
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract int nodeAggregate(AvlNode<?> avlNode);

        /* access modifiers changed from: 0000 */
        public abstract long treeAggregate(@Nullable AvlNode<?> avlNode);
    }

    public /* bridge */ /* synthetic */ boolean add(Object x0) {
        return super.add(x0);
    }

    public /* bridge */ /* synthetic */ boolean addAll(Collection x0) {
        return super.addAll(x0);
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ Comparator comparator() {
        return super.comparator();
    }

    public /* bridge */ /* synthetic */ boolean contains(Object x0) {
        return super.contains(x0);
    }

    public /* bridge */ /* synthetic */ SortedMultiset descendingMultiset() {
        return super.descendingMultiset();
    }

    public /* bridge */ /* synthetic */ SortedSet elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ boolean equals(Object x0) {
        return super.equals(x0);
    }

    public /* bridge */ /* synthetic */ Entry firstEntry() {
        return super.firstEntry();
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    public /* bridge */ /* synthetic */ Entry lastEntry() {
        return super.lastEntry();
    }

    public /* bridge */ /* synthetic */ Entry pollFirstEntry() {
        return super.pollFirstEntry();
    }

    public /* bridge */ /* synthetic */ Entry pollLastEntry() {
        return super.pollLastEntry();
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

    public /* bridge */ /* synthetic */ SortedMultiset subMultiset(Object x0, BoundType x1, Object x2, BoundType x3) {
        return super.subMultiset(x0, x1, x2, x3);
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset<>(Ordering.natural());
    }

    public static <E> TreeMultiset<E> create(@Nullable Comparator<? super E> comparator) {
        return comparator == null ? new TreeMultiset<>(Ordering.natural()) : new TreeMultiset<>(comparator);
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> elements) {
        TreeMultiset<E> multiset = create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }

    TreeMultiset(Reference<AvlNode<E>> rootReference2, GeneralRange<E> range2, AvlNode<E> endLink) {
        super(range2.comparator());
        this.rootReference = rootReference2;
        this.range = range2;
        this.header = endLink;
    }

    TreeMultiset(Comparator<? super E> comparator) {
        super(comparator);
        this.range = GeneralRange.all(comparator);
        this.header = new AvlNode<>(null, 1);
        successor(this.header, this.header);
        this.rootReference = new Reference<>();
    }

    private long aggregateForEntries(Aggregate aggr) {
        AvlNode<E> root = (AvlNode) this.rootReference.get();
        long total = aggr.treeAggregate(root);
        if (this.range.hasLowerBound()) {
            total -= aggregateBelowRange(aggr, root);
        }
        if (this.range.hasUpperBound()) {
            return total - aggregateAboveRange(aggr, root);
        }
        return total;
    }

    private long aggregateBelowRange(Aggregate aggr, @Nullable AvlNode<E> node) {
        if (node == null) {
            return 0;
        }
        int cmp = comparator().compare(this.range.getLowerEndpoint(), node.elem);
        if (cmp < 0) {
            return aggregateBelowRange(aggr, node.left);
        }
        if (cmp != 0) {
            return aggr.treeAggregate(node.left) + ((long) aggr.nodeAggregate(node)) + aggregateBelowRange(aggr, node.right);
        }
        switch (this.range.getLowerBoundType()) {
            case OPEN:
                return ((long) aggr.nodeAggregate(node)) + aggr.treeAggregate(node.left);
            case CLOSED:
                return aggr.treeAggregate(node.left);
            default:
                throw new AssertionError();
        }
    }

    private long aggregateAboveRange(Aggregate aggr, @Nullable AvlNode<E> node) {
        if (node == null) {
            return 0;
        }
        int cmp = comparator().compare(this.range.getUpperEndpoint(), node.elem);
        if (cmp > 0) {
            return aggregateAboveRange(aggr, node.right);
        }
        if (cmp != 0) {
            return aggr.treeAggregate(node.right) + ((long) aggr.nodeAggregate(node)) + aggregateAboveRange(aggr, node.left);
        }
        switch (this.range.getUpperBoundType()) {
            case OPEN:
                return ((long) aggr.nodeAggregate(node)) + aggr.treeAggregate(node.right);
            case CLOSED:
                return aggr.treeAggregate(node.right);
            default:
                throw new AssertionError();
        }
    }

    public int size() {
        return Ints.saturatedCast(aggregateForEntries(Aggregate.SIZE));
    }

    /* access modifiers changed from: 0000 */
    public int distinctElements() {
        return Ints.saturatedCast(aggregateForEntries(Aggregate.DISTINCT));
    }

    public int count(@Nullable Object element) {
        Object obj = element;
        try {
            AvlNode<E> root = (AvlNode) this.rootReference.get();
            if (!this.range.contains(obj) || root == null) {
                return 0;
            }
            return root.count(comparator(), obj);
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }

    public int add(@Nullable E element, int occurrences) {
        CollectPreconditions.checkNonnegative(occurrences, "occurrences");
        if (occurrences == 0) {
            return count(element);
        }
        Preconditions.checkArgument(this.range.contains(element));
        AvlNode<E> root = (AvlNode) this.rootReference.get();
        if (root == null) {
            comparator().compare(element, element);
            AvlNode<E> newRoot = new AvlNode<>(element, occurrences);
            successor(this.header, newRoot, this.header);
            this.rootReference.checkAndSet(root, newRoot);
            return 0;
        }
        int[] result = new int[1];
        this.rootReference.checkAndSet(root, root.add(comparator(), element, occurrences, result));
        return result[0];
    }

    public int remove(@Nullable Object element, int occurrences) {
        CollectPreconditions.checkNonnegative(occurrences, "occurrences");
        if (occurrences == 0) {
            return count(element);
        }
        AvlNode<E> root = (AvlNode) this.rootReference.get();
        int[] result = new int[1];
        Object obj = element;
        try {
            if (!this.range.contains(obj) || root == null) {
                return 0;
            }
            this.rootReference.checkAndSet(root, root.remove(comparator(), obj, occurrences, result));
            return result[0];
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }

    public int setCount(@Nullable E element, int count) {
        boolean z = true;
        CollectPreconditions.checkNonnegative(count, "count");
        if (!this.range.contains(element)) {
            if (count != 0) {
                z = false;
            }
            Preconditions.checkArgument(z);
            return 0;
        }
        AvlNode<E> root = (AvlNode) this.rootReference.get();
        if (root != null) {
            int[] result = new int[1];
            this.rootReference.checkAndSet(root, root.setCount(comparator(), element, count, result));
            return result[0];
        } else if (count <= 0) {
            return 0;
        } else {
            add(element, count);
            return 0;
        }
    }

    public boolean setCount(@Nullable E element, int oldCount, int newCount) {
        CollectPreconditions.checkNonnegative(newCount, "newCount");
        CollectPreconditions.checkNonnegative(oldCount, "oldCount");
        Preconditions.checkArgument(this.range.contains(element));
        AvlNode<E> root = (AvlNode) this.rootReference.get();
        if (root != null) {
            int[] result = new int[1];
            this.rootReference.checkAndSet(root, root.setCount(comparator(), element, oldCount, newCount, result));
            return result[0] == oldCount;
        } else if (oldCount != 0) {
            return false;
        } else {
            if (newCount <= 0) {
                return true;
            }
            add(element, newCount);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public Entry<E> wrapEntry(final AvlNode<E> baseEntry) {
        return new AbstractEntry<E>() {
            public E getElement() {
                return baseEntry.getElement();
            }

            public int getCount() {
                int result = baseEntry.getCount();
                if (result == 0) {
                    return TreeMultiset.this.count(getElement());
                }
                return result;
            }
        };
    }

    /* access modifiers changed from: private */
    @Nullable
    public AvlNode<E> firstNode() {
        AvlNode<E> node;
        if (((AvlNode) this.rootReference.get()) == null) {
            return null;
        }
        if (this.range.hasLowerBound()) {
            E endpoint = this.range.getLowerEndpoint();
            node = ((AvlNode) this.rootReference.get()).ceiling(comparator(), endpoint);
            if (node == null) {
                return null;
            }
            if (this.range.getLowerBoundType() == BoundType.OPEN && comparator().compare(endpoint, node.getElement()) == 0) {
                node = node.succ;
            }
        } else {
            node = this.header.succ;
        }
        if (node == this.header || !this.range.contains(node.getElement())) {
            node = null;
        }
        return node;
    }

    /* access modifiers changed from: private */
    @Nullable
    public AvlNode<E> lastNode() {
        AvlNode<E> node;
        if (((AvlNode) this.rootReference.get()) == null) {
            return null;
        }
        if (this.range.hasUpperBound()) {
            E endpoint = this.range.getUpperEndpoint();
            node = ((AvlNode) this.rootReference.get()).floor(comparator(), endpoint);
            if (node == null) {
                return null;
            }
            if (this.range.getUpperBoundType() == BoundType.OPEN && comparator().compare(endpoint, node.getElement()) == 0) {
                node = node.pred;
            }
        } else {
            node = this.header.pred;
        }
        if (node == this.header || !this.range.contains(node.getElement())) {
            node = null;
        }
        return node;
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<E>> entryIterator() {
        return new Iterator<Entry<E>>() {
            AvlNode<E> current = TreeMultiset.this.firstNode();
            Entry<E> prevEntry;

            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooHigh(this.current.getElement())) {
                    return true;
                }
                this.current = null;
                return false;
            }

            public Entry<E> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Entry<E> result = TreeMultiset.this.wrapEntry(this.current);
                this.prevEntry = result;
                if (this.current.succ == TreeMultiset.this.header) {
                    this.current = null;
                } else {
                    this.current = this.current.succ;
                }
                return result;
            }

            public void remove() {
                boolean z;
                if (this.prevEntry != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.checkRemove(z);
                TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<E>> descendingEntryIterator() {
        return new Iterator<Entry<E>>() {
            AvlNode<E> current = TreeMultiset.this.lastNode();
            Entry<E> prevEntry = null;

            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooLow(this.current.getElement())) {
                    return true;
                }
                this.current = null;
                return false;
            }

            public Entry<E> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Entry<E> result = TreeMultiset.this.wrapEntry(this.current);
                this.prevEntry = result;
                if (this.current.pred == TreeMultiset.this.header) {
                    this.current = null;
                } else {
                    this.current = this.current.pred;
                }
                return result;
            }

            public void remove() {
                boolean z;
                if (this.prevEntry != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.checkRemove(z);
                TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
        };
    }

    public SortedMultiset<E> headMultiset(@Nullable E upperBound, BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.upTo(comparator(), upperBound, boundType)), this.header);
    }

    public SortedMultiset<E> tailMultiset(@Nullable E lowerBound, BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.downTo(comparator(), lowerBound, boundType)), this.header);
    }

    static int distinctElements(@Nullable AvlNode<?> node) {
        if (node == null) {
            return 0;
        }
        return node.distinctElements;
    }

    /* access modifiers changed from: private */
    public static <T> void successor(AvlNode<T> a, AvlNode<T> b) {
        a.succ = b;
        b.pred = a;
    }

    /* access modifiers changed from: private */
    public static <T> void successor(AvlNode<T> a, AvlNode<T> b, AvlNode<T> c) {
        successor(a, b);
        successor(b, c);
    }

    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(elementSet().comparator());
        Serialization.writeMultiset(this, stream);
    }

    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        Comparator<? super E> comparator = (Comparator) stream.readObject();
        Serialization.getFieldSetter(AbstractSortedMultiset.class, "comparator").set(this, (Object) comparator);
        Serialization.getFieldSetter(TreeMultiset.class, "range").set(this, (Object) GeneralRange.all(comparator));
        Serialization.getFieldSetter(TreeMultiset.class, "rootReference").set(this, (Object) new Reference());
        AvlNode<E> header2 = new AvlNode<>(null, 1);
        Serialization.getFieldSetter(TreeMultiset.class, "header").set(this, (Object) header2);
        successor(header2, header2);
        Serialization.populateMultiset(this, stream);
    }
}
