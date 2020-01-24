package com.google.common.cache;

import com.alibaba.android.arouter.utils.Consts;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache.SimpleStatsCounter;
import com.google.common.cache.AbstractCache.StatsCounter;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import kotlin.jvm.internal.LongCompanionObject;

@GwtCompatible(emulated = true)
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final Queue<? extends Object> DISCARDING_QUEUE = new AbstractQueue<Object>() {
        public boolean offer(Object o) {
            return true;
        }

        public Object peek() {
            return null;
        }

        public Object poll() {
            return null;
        }

        public int size() {
            return 0;
        }

        public Iterator<Object> iterator() {
            return Iterators.emptyIterator();
        }
    };
    static final int DRAIN_MAX = 16;
    static final int DRAIN_THRESHOLD = 63;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>() {
        public Object get() {
            return null;
        }

        public int getWeight() {
            return 0;
        }

        public ReferenceEntry<Object, Object> getEntry() {
            return null;
        }

        public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> referenceQueue, @Nullable Object value, ReferenceEntry<Object, Object> referenceEntry) {
            return this;
        }

        public boolean isLoading() {
            return false;
        }

        public boolean isActive() {
            return false;
        }

        public Object waitForValue() {
            return null;
        }

        public void notifyNewValue(Object newValue) {
        }
    };
    static final Logger logger = Logger.getLogger(LocalCache.class.getName());
    static final ListeningExecutorService sameThreadExecutor = MoreExecutors.sameThreadExecutor();
    final int concurrencyLevel;
    @Nullable
    final CacheLoader<? super K, V> defaultLoader;
    final EntryFactory entryFactory;
    Set<Entry<K, V>> entrySet;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final StatsCounter globalStatsCounter;
    final Equivalence<Object> keyEquivalence;
    Set<K> keySet;
    final Strength keyStrength;
    final long maxWeight;
    final long refreshNanos;
    final RemovalListener<K, V> removalListener;
    final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    final Ticker ticker;
    final Equivalence<Object> valueEquivalence;
    final Strength valueStrength;
    Collection<V> values;
    final Weigher<K, V> weigher;

    static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final LocalCache<K, V> localCache;

        LocalManualCache(CacheBuilder<? super K, ? super V> builder) {
            this(new LocalCache<>(builder, null));
        }

        private LocalManualCache(LocalCache<K, V> localCache2) {
            this.localCache = localCache2;
        }

        @Nullable
        public V getIfPresent(Object key) {
            return this.localCache.getIfPresent(key);
        }

        public V get(K key, final Callable<? extends V> valueLoader) throws ExecutionException {
            Preconditions.checkNotNull(valueLoader);
            return this.localCache.get(key, new CacheLoader<Object, V>() {
                public V load(Object key) throws Exception {
                    return valueLoader.call();
                }
            });
        }

        public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
            return this.localCache.getAllPresent(keys);
        }

        public void put(K key, V value) {
            this.localCache.put(key, value);
        }

        public void putAll(Map<? extends K, ? extends V> m) {
            this.localCache.putAll(m);
        }

        public void invalidate(Object key) {
            Preconditions.checkNotNull(key);
            this.localCache.remove(key);
        }

        public void invalidateAll(Iterable<?> keys) {
            this.localCache.invalidateAll(keys);
        }

        public void invalidateAll() {
            this.localCache.clear();
        }

        public long size() {
            return this.localCache.longSize();
        }

        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        public CacheStats stats() {
            SimpleStatsCounter aggregator = new SimpleStatsCounter();
            aggregator.incrementBy(this.localCache.globalStatsCounter);
            for (Segment<K, V> segment : this.localCache.segments) {
                aggregator.incrementBy(segment.statsCounter);
            }
            return aggregator.snapshot();
        }

        public void cleanUp() {
            this.localCache.cleanUp();
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new ManualSerializationProxy(this.localCache);
        }
    }

    interface ReferenceEntry<K, V> {
        long getAccessTime();

        int getHash();

        @Nullable
        K getKey();

        @Nullable
        ReferenceEntry<K, V> getNext();

        ReferenceEntry<K, V> getNextInAccessQueue();

        ReferenceEntry<K, V> getNextInWriteQueue();

        ReferenceEntry<K, V> getPreviousInAccessQueue();

        ReferenceEntry<K, V> getPreviousInWriteQueue();

        ValueReference<K, V> getValueReference();

        long getWriteTime();

        void setAccessTime(long j);

        void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry);

        void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry);

        void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry);

        void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry);

        void setValueReference(ValueReference<K, V> valueReference);

        void setWriteTime(long j);
    }

    static class Segment<K, V> extends ReentrantLock {
        @GuardedBy("Segment.this")
        final Queue<ReferenceEntry<K, V>> accessQueue;
        volatile int count;
        final ReferenceQueue<K> keyReferenceQueue;
        final LocalCache<K, V> map;
        final long maxSegmentWeight;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final StatsCounter statsCounter;
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        int threshold;
        @GuardedBy("Segment.this")
        int totalWeight;
        final ReferenceQueue<V> valueReferenceQueue;
        @GuardedBy("Segment.this")
        final Queue<ReferenceEntry<K, V>> writeQueue;

        Segment(LocalCache<K, V> map2, int initialCapacity, long maxSegmentWeight2, StatsCounter statsCounter2) {
            ReferenceQueue<V> referenceQueue = null;
            this.map = map2;
            this.maxSegmentWeight = maxSegmentWeight2;
            this.statsCounter = (StatsCounter) Preconditions.checkNotNull(statsCounter2);
            initTable(newEntryArray(initialCapacity));
            this.keyReferenceQueue = map2.usesKeyReferences() ? new ReferenceQueue<>() : null;
            if (map2.usesValueReferences()) {
                referenceQueue = new ReferenceQueue<>();
            }
            this.valueReferenceQueue = referenceQueue;
            this.recencyQueue = map2.usesAccessQueue() ? new ConcurrentLinkedQueue<>() : LocalCache.discardingQueue();
            this.writeQueue = map2.usesWriteQueue() ? new WriteQueue<>() : LocalCache.discardingQueue();
            this.accessQueue = map2.usesAccessQueue() ? new AccessQueue<>() : LocalCache.discardingQueue();
        }

        /* access modifiers changed from: 0000 */
        public AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int size) {
            return new AtomicReferenceArray<>(size);
        }

        /* access modifiers changed from: 0000 */
        public void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> newTable) {
            this.threshold = (newTable.length() * 3) / 4;
            if (!this.map.customWeigher() && ((long) this.threshold) == this.maxSegmentWeight) {
                this.threshold++;
            }
            this.table = newTable;
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(key), hash, next);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
            if (original.getKey() == null) {
                return null;
            }
            ValueReference<K, V> valueReference = original.getValueReference();
            V value = valueReference.get();
            if (value == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
            newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
            return newEntry;
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void setValue(ReferenceEntry<K, V> entry, K key, V value, long now) {
            ValueReference<K, V> previous = entry.getValueReference();
            int weight = this.map.weigher.weigh(key, value);
            Preconditions.checkState(weight >= 0, "Weights must be non-negative");
            entry.setValueReference(this.map.valueStrength.referenceValue(this, entry, value, weight));
            recordWrite(entry, weight, now);
            previous.notifyNewValue(value);
        }

        /* access modifiers changed from: 0000 */
        public V get(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
            V lockedGetOrLoad;
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(loader);
            try {
                if (this.count != 0) {
                    ReferenceEntry<K, V> e = getEntry(key, hash);
                    if (e != null) {
                        long now = this.map.ticker.read();
                        V value = getLiveValue(e, now);
                        if (value != null) {
                            recordRead(e, now);
                            this.statsCounter.recordHits(1);
                            lockedGetOrLoad = scheduleRefresh(e, key, hash, value, now, loader);
                            postReadCleanup();
                        } else {
                            ValueReference<K, V> valueReference = e.getValueReference();
                            if (valueReference.isLoading()) {
                                lockedGetOrLoad = waitForLoadingValue(e, key, valueReference);
                                postReadCleanup();
                            }
                        }
                        return lockedGetOrLoad;
                    }
                }
                lockedGetOrLoad = lockedGetOrLoad(key, hash, loader);
                postReadCleanup();
                return lockedGetOrLoad;
            } catch (ExecutionException ee) {
                Throwable cause = ee.getCause();
                if (cause instanceof Error) {
                    throw new ExecutionError((Error) cause);
                } else if (cause instanceof RuntimeException) {
                    throw new UncheckedExecutionException(cause);
                } else {
                    throw ee;
                }
            } catch (Throwable th) {
                postReadCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0067, code lost:
            if (r16.isLoading() == false) goto L_0x00a6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0069, code lost:
            r4 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            r15 = r16.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00aa, code lost:
            if (r15 != null) goto L_0x00dc;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ac, code lost:
            enqueueNotification(r6, r22, r16, com.google.common.cache.RemovalCause.COLLECTED);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b9, code lost:
            r20.writeQueue.remove(r5);
            r20.accessQueue.remove(r5);
            r20.count = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e8, code lost:
            if (r20.map.isExpired(r5, r12) == false) goto L_0x00f8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ea, code lost:
            enqueueNotification(r6, r22, r16, com.google.common.cache.RemovalCause.EXPIRED);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f8, code lost:
            recordLockedRead(r5, r12);
            r20.statsCounter.recordHits(1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0108, code lost:
            unlock();
            postWriteCleanup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
            return r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x005f, code lost:
            r16 = r5.getValueReference();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V lockedGetOrLoad(K r21, int r22, com.google.common.cache.CacheLoader<? super K, V> r23) throws java.util.concurrent.ExecutionException {
            /*
                r20 = this;
                r16 = 0
                r9 = 0
                r4 = 1
                r20.lock()
                r0 = r20
                com.google.common.cache.LocalCache<K, V> r0 = r0.map     // Catch:{ all -> 0x00d4 }
                r17 = r0
                r0 = r17
                com.google.common.base.Ticker r0 = r0.ticker     // Catch:{ all -> 0x00d4 }
                r17 = r0
                long r12 = r17.read()     // Catch:{ all -> 0x00d4 }
                r0 = r20
                r0.preWriteCleanup(r12)     // Catch:{ all -> 0x00d4 }
                r0 = r20
                int r0 = r0.count     // Catch:{ all -> 0x00d4 }
                r17 = r0
                int r11 = r17 + -1
                r0 = r20
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.LocalCache$ReferenceEntry<K, V>> r14 = r0.table     // Catch:{ all -> 0x00d4 }
                int r17 = r14.length()     // Catch:{ all -> 0x00d4 }
                int r17 = r17 + -1
                r8 = r22 & r17
                java.lang.Object r7 = r14.get(r8)     // Catch:{ all -> 0x00d4 }
                com.google.common.cache.LocalCache$ReferenceEntry r7 = (com.google.common.cache.LocalCache.ReferenceEntry) r7     // Catch:{ all -> 0x00d4 }
                r5 = r7
            L_0x0037:
                if (r5 == 0) goto L_0x006a
                java.lang.Object r6 = r5.getKey()     // Catch:{ all -> 0x00d4 }
                int r17 = r5.getHash()     // Catch:{ all -> 0x00d4 }
                r0 = r17
                r1 = r22
                if (r0 != r1) goto L_0x010f
                if (r6 == 0) goto L_0x010f
                r0 = r20
                com.google.common.cache.LocalCache<K, V> r0 = r0.map     // Catch:{ all -> 0x00d4 }
                r17 = r0
                r0 = r17
                com.google.common.base.Equivalence<java.lang.Object> r0 = r0.keyEquivalence     // Catch:{ all -> 0x00d4 }
                r17 = r0
                r0 = r17
                r1 = r21
                boolean r17 = r0.equivalent(r1, r6)     // Catch:{ all -> 0x00d4 }
                if (r17 == 0) goto L_0x010f
                com.google.common.cache.LocalCache$ValueReference r16 = r5.getValueReference()     // Catch:{ all -> 0x00d4 }
                boolean r17 = r16.isLoading()     // Catch:{ all -> 0x00d4 }
                if (r17 == 0) goto L_0x00a6
                r4 = 0
            L_0x006a:
                if (r4 == 0) goto L_0x0084
                com.google.common.cache.LocalCache$LoadingValueReference r10 = new com.google.common.cache.LocalCache$LoadingValueReference     // Catch:{ all -> 0x00d4 }
                r10.<init>()     // Catch:{ all -> 0x00d4 }
                if (r5 != 0) goto L_0x0115
                r0 = r20
                r1 = r21
                r2 = r22
                com.google.common.cache.LocalCache$ReferenceEntry r5 = r0.newEntry(r1, r2, r7)     // Catch:{ all -> 0x0137 }
                r5.setValueReference(r10)     // Catch:{ all -> 0x0137 }
                r14.set(r8, r5)     // Catch:{ all -> 0x0137 }
                r9 = r10
            L_0x0084:
                r20.unlock()
                r20.postWriteCleanup()
                if (r4 == 0) goto L_0x012b
                monitor-enter(r5)     // Catch:{ all -> 0x011e }
                r0 = r20
                r1 = r21
                r2 = r22
                r3 = r23
                java.lang.Object r15 = r0.loadSync(r1, r2, r9, r3)     // Catch:{ all -> 0x011b }
                monitor-exit(r5)     // Catch:{ all -> 0x011b }
                r0 = r20
                com.google.common.cache.AbstractCache$StatsCounter r0 = r0.statsCounter
                r17 = r0
                r18 = 1
                r17.recordMisses(r18)
            L_0x00a5:
                return r15
            L_0x00a6:
                java.lang.Object r15 = r16.get()     // Catch:{ all -> 0x00d4 }
                if (r15 != 0) goto L_0x00dc
                com.google.common.cache.RemovalCause r17 = com.google.common.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00d4 }
                r0 = r20
                r1 = r22
                r2 = r16
                r3 = r17
                r0.enqueueNotification(r6, r1, r2, r3)     // Catch:{ all -> 0x00d4 }
            L_0x00b9:
                r0 = r20
                java.util.Queue<com.google.common.cache.LocalCache$ReferenceEntry<K, V>> r0 = r0.writeQueue     // Catch:{ all -> 0x00d4 }
                r17 = r0
                r0 = r17
                r0.remove(r5)     // Catch:{ all -> 0x00d4 }
                r0 = r20
                java.util.Queue<com.google.common.cache.LocalCache$ReferenceEntry<K, V>> r0 = r0.accessQueue     // Catch:{ all -> 0x00d4 }
                r17 = r0
                r0 = r17
                r0.remove(r5)     // Catch:{ all -> 0x00d4 }
                r0 = r20
                r0.count = r11     // Catch:{ all -> 0x00d4 }
                goto L_0x006a
            L_0x00d4:
                r17 = move-exception
            L_0x00d5:
                r20.unlock()
                r20.postWriteCleanup()
                throw r17
            L_0x00dc:
                r0 = r20
                com.google.common.cache.LocalCache<K, V> r0 = r0.map     // Catch:{ all -> 0x00d4 }
                r17 = r0
                r0 = r17
                boolean r17 = r0.isExpired(r5, r12)     // Catch:{ all -> 0x00d4 }
                if (r17 == 0) goto L_0x00f8
                com.google.common.cache.RemovalCause r17 = com.google.common.cache.RemovalCause.EXPIRED     // Catch:{ all -> 0x00d4 }
                r0 = r20
                r1 = r22
                r2 = r16
                r3 = r17
                r0.enqueueNotification(r6, r1, r2, r3)     // Catch:{ all -> 0x00d4 }
                goto L_0x00b9
            L_0x00f8:
                r0 = r20
                r0.recordLockedRead(r5, r12)     // Catch:{ all -> 0x00d4 }
                r0 = r20
                com.google.common.cache.AbstractCache$StatsCounter r0 = r0.statsCounter     // Catch:{ all -> 0x00d4 }
                r17 = r0
                r18 = 1
                r17.recordHits(r18)     // Catch:{ all -> 0x00d4 }
                r20.unlock()
                r20.postWriteCleanup()
                goto L_0x00a5
            L_0x010f:
                com.google.common.cache.LocalCache$ReferenceEntry r5 = r5.getNext()     // Catch:{ all -> 0x00d4 }
                goto L_0x0037
            L_0x0115:
                r5.setValueReference(r10)     // Catch:{ all -> 0x0137 }
                r9 = r10
                goto L_0x0084
            L_0x011b:
                r17 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x011b }
                throw r17     // Catch:{ all -> 0x011e }
            L_0x011e:
                r17 = move-exception
                r0 = r20
                com.google.common.cache.AbstractCache$StatsCounter r0 = r0.statsCounter
                r18 = r0
                r19 = 1
                r18.recordMisses(r19)
                throw r17
            L_0x012b:
                r0 = r20
                r1 = r21
                r2 = r16
                java.lang.Object r15 = r0.waitForLoadingValue(r5, r1, r2)
                goto L_0x00a5
            L_0x0137:
                r17 = move-exception
                r9 = r10
                goto L_0x00d5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.lockedGetOrLoad(java.lang.Object, int, com.google.common.cache.CacheLoader):java.lang.Object");
        }

        /* access modifiers changed from: 0000 */
        public V waitForLoadingValue(ReferenceEntry<K, V> e, K key, ValueReference<K, V> valueReference) throws ExecutionException {
            if (!valueReference.isLoading()) {
                throw new AssertionError();
            }
            Preconditions.checkState(!Thread.holdsLock(e), "Recursive load of: %s", key);
            try {
                V value = valueReference.waitForValue();
                if (value == null) {
                    throw new InvalidCacheLoadException("CacheLoader returned null for key " + key + Consts.DOT);
                }
                recordRead(e, this.map.ticker.read());
                return value;
            } finally {
                this.statsCounter.recordMisses(1);
            }
        }

        /* access modifiers changed from: 0000 */
        public V loadSync(K key, int hash, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) throws ExecutionException {
            return getAndRecordStats(key, hash, loadingValueReference, loadingValueReference.loadFuture(key, loader));
        }

        /* access modifiers changed from: 0000 */
        public ListenableFuture<V> loadAsync(K key, int hash, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) {
            final ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture(key, loader);
            final K k = key;
            final int i = hash;
            final LoadingValueReference<K, V> loadingValueReference2 = loadingValueReference;
            loadingFuture.addListener(new Runnable() {
                public void run() {
                    try {
                        Segment.this.getAndRecordStats(k, i, loadingValueReference2, loadingFuture);
                    } catch (Throwable t) {
                        LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", t);
                        loadingValueReference2.setException(t);
                    }
                }
            }, LocalCache.sameThreadExecutor);
            return loadingFuture;
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: 0000 */
        public V getAndRecordStats(K key, int hash, LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> newValue) throws ExecutionException {
            V value = null;
            try {
                value = Uninterruptibles.getUninterruptibly(newValue);
                if (value == null) {
                    throw new InvalidCacheLoadException("CacheLoader returned null for key " + key + Consts.DOT);
                }
                this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
                storeLoadedValue(key, hash, loadingValueReference, value);
                if (value == null) {
                    this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                    removeLoadingValue(key, hash, loadingValueReference);
                }
                return value;
            } catch (Throwable th) {
                if (value == null) {
                    this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                    removeLoadingValue(key, hash, loadingValueReference);
                }
                throw th;
            }
        }

        /* access modifiers changed from: 0000 */
        public V scheduleRefresh(ReferenceEntry<K, V> entry, K key, int hash, V oldValue, long now, CacheLoader<? super K, V> loader) {
            if (this.map.refreshes() && now - entry.getWriteTime() > this.map.refreshNanos && !entry.getValueReference().isLoading()) {
                V newValue = refresh(key, hash, loader, true);
                if (newValue != null) {
                    return newValue;
                }
            }
            return oldValue;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V refresh(K key, int hash, CacheLoader<? super K, V> loader, boolean checkTime) {
            V v = null;
            LoadingValueReference<K, V> loadingValueReference = insertLoadingValueReference(key, hash, checkTime);
            if (loadingValueReference == null) {
                return v;
            }
            ListenableFuture<V> result = loadAsync(key, hash, loadingValueReference, loader);
            if (!result.isDone()) {
                return v;
            }
            try {
                return Uninterruptibles.getUninterruptibly(result);
            } catch (Throwable th) {
                return v;
            }
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public LoadingValueReference<K, V> insertLoadingValueReference(K key, int hash, boolean checkTime) {
            lock();
            try {
                long now = this.map.ticker.read();
                preWriteCleanup(now);
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else {
                        ValueReference<K, V> valueReference = e.getValueReference();
                        if (valueReference.isLoading() || (checkTime && now - e.getWriteTime() < this.map.refreshNanos)) {
                            return null;
                        }
                        this.modCount++;
                        LoadingValueReference<K, V> loadingValueReference = new LoadingValueReference<>(valueReference);
                        e.setValueReference(loadingValueReference);
                        unlock();
                        postWriteCleanup();
                        return loadingValueReference;
                    }
                }
                this.modCount++;
                LoadingValueReference<K, V> loadingValueReference2 = new LoadingValueReference<>();
                ReferenceEntry<K, V> e2 = newEntry(key, hash, first);
                e2.setValueReference(loadingValueReference2);
                table2.set(index, e2);
                unlock();
                postWriteCleanup();
                return loadingValueReference2;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        public void tryDrainReferenceQueues() {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                drainValueReferenceQueue();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void drainKeyReferenceQueue() {
            int i = 0;
            do {
                Reference<? extends K> ref = this.keyReferenceQueue.poll();
                if (ref != null) {
                    this.map.reclaimKey((ReferenceEntry) ref);
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void drainValueReferenceQueue() {
            int i = 0;
            do {
                Reference<? extends V> ref = this.valueReferenceQueue.poll();
                if (ref != null) {
                    this.map.reclaimValue((ValueReference) ref);
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        /* access modifiers changed from: 0000 */
        public void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                clearValueReferenceQueue();
            }
        }

        /* access modifiers changed from: 0000 */
        public void clearKeyReferenceQueue() {
            do {
            } while (this.keyReferenceQueue.poll() != null);
        }

        /* access modifiers changed from: 0000 */
        public void clearValueReferenceQueue() {
            do {
            } while (this.valueReferenceQueue.poll() != null);
        }

        /* access modifiers changed from: 0000 */
        public void recordRead(ReferenceEntry<K, V> entry, long now) {
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            this.recencyQueue.add(entry);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void recordLockedRead(ReferenceEntry<K, V> entry, long now) {
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            this.accessQueue.add(entry);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void recordWrite(ReferenceEntry<K, V> entry, int weight, long now) {
            drainRecencyQueue();
            this.totalWeight += weight;
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            if (this.map.recordsWrite()) {
                entry.setWriteTime(now);
            }
            this.accessQueue.add(entry);
            this.writeQueue.add(entry);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void drainRecencyQueue() {
            while (true) {
                ReferenceEntry<K, V> e = (ReferenceEntry) this.recencyQueue.poll();
                if (e == null) {
                    return;
                }
                if (this.accessQueue.contains(e)) {
                    this.accessQueue.add(e);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void tryExpireEntries(long now) {
            if (tryLock()) {
                try {
                    expireEntries(now);
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void expireEntries(long now) {
            ReferenceEntry<K, V> e;
            ReferenceEntry<K, V> e2;
            drainRecencyQueue();
            do {
                e = (ReferenceEntry) this.writeQueue.peek();
                if (e == null || !this.map.isExpired(e, now)) {
                    do {
                        e2 = (ReferenceEntry) this.accessQueue.peek();
                        if (e2 == null || !this.map.isExpired(e2, now)) {
                            return;
                        }
                    } while (removeEntry(e2, e2.getHash(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (removeEntry(e, e.getHash(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void enqueueNotification(ReferenceEntry<K, V> entry, RemovalCause cause) {
            enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference(), cause);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void enqueueNotification(@Nullable K key, int hash, ValueReference<K, V> valueReference, RemovalCause cause) {
            this.totalWeight -= valueReference.getWeight();
            if (cause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
                this.map.removalNotificationQueue.offer(new RemovalNotification<>(key, valueReference.get(), cause));
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void evictEntries() {
            if (this.map.evictsBySize()) {
                drainRecencyQueue();
                while (((long) this.totalWeight) > this.maxSegmentWeight) {
                    ReferenceEntry<K, V> e = getNextEvictable();
                    if (!removeEntry(e, e.getHash(), RemovalCause.SIZE)) {
                        throw new AssertionError();
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public ReferenceEntry<K, V> getNextEvictable() {
            for (ReferenceEntry<K, V> e : this.accessQueue) {
                if (e.getValueReference().getWeight() > 0) {
                    return e;
                }
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public ReferenceEntry<K, V> getFirst(int hash) {
            AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
            return (ReferenceEntry) table2.get((table2.length() - 1) & hash);
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public ReferenceEntry<K, V> getEntry(Object key, int hash) {
            for (ReferenceEntry<K, V> e = getFirst(hash); e != null; e = e.getNext()) {
                if (e.getHash() == hash) {
                    K entryKey = e.getKey();
                    if (entryKey == null) {
                        tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                        return e;
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public ReferenceEntry<K, V> getLiveEntry(Object key, int hash, long now) {
            ReferenceEntry<K, V> e = getEntry(key, hash);
            if (e == null) {
                return null;
            }
            if (!this.map.isExpired(e, now)) {
                return e;
            }
            tryExpireEntries(now);
            return null;
        }

        /* access modifiers changed from: 0000 */
        public V getLiveValue(ReferenceEntry<K, V> entry, long now) {
            if (entry.getKey() == null) {
                tryDrainReferenceQueues();
                return null;
            }
            V value = entry.getValueReference().get();
            if (value == null) {
                tryDrainReferenceQueues();
                return null;
            } else if (!this.map.isExpired(entry, now)) {
                return value;
            } else {
                tryExpireEntries(now);
                return null;
            }
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V get(Object key, int hash) {
            V v = null;
            try {
                if (this.count != 0) {
                    long now = this.map.ticker.read();
                    ReferenceEntry<K, V> e = getLiveEntry(key, hash, now);
                    if (e != null) {
                        V value = e.getValueReference().get();
                        if (value != null) {
                            recordRead(e, now);
                            v = scheduleRefresh(e, e.getKey(), hash, value, now, this.map.defaultLoader);
                            postReadCleanup();
                        } else {
                            tryDrainReferenceQueues();
                        }
                    }
                    return v;
                }
                postReadCleanup();
                return v;
            } finally {
                postReadCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean containsKey(Object key, int hash) {
            boolean z = false;
            try {
                if (this.count != 0) {
                    ReferenceEntry<K, V> e = getLiveEntry(key, hash, this.map.ticker.read());
                    if (e != null) {
                        if (e.getValueReference().get() != null) {
                            z = true;
                        }
                        postReadCleanup();
                    }
                } else {
                    postReadCleanup();
                }
                return z;
            } finally {
                postReadCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        @VisibleForTesting
        public boolean containsValue(Object value) {
            try {
                if (this.count != 0) {
                    long now = this.map.ticker.read();
                    AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                    int length = table2.length();
                    for (int i = 0; i < length; i++) {
                        for (ReferenceEntry<K, V> e = (ReferenceEntry) table2.get(i); e != null; e = e.getNext()) {
                            V entryValue = getLiveValue(e, now);
                            if (entryValue != null && this.map.valueEquivalence.equivalent(value, entryValue)) {
                                return true;
                            }
                        }
                    }
                }
                postReadCleanup();
                return false;
            } finally {
                postReadCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V put(K key, int hash, V value, boolean onlyIfAbsent) {
            int newCount;
            lock();
            try {
                long now = this.map.ticker.read();
                preWriteCleanup(now);
                if (this.count + 1 > this.threshold) {
                    expand();
                    int newCount2 = this.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else {
                        ValueReference<K, V> valueReference = e.getValueReference();
                        V entryValue = valueReference.get();
                        if (entryValue == null) {
                            this.modCount++;
                            if (valueReference.isActive()) {
                                enqueueNotification(key, hash, valueReference, RemovalCause.COLLECTED);
                                setValue(e, key, value, now);
                                newCount = this.count;
                            } else {
                                setValue(e, key, value, now);
                                newCount = this.count + 1;
                            }
                            this.count = newCount;
                            evictEntries();
                            return null;
                        } else if (onlyIfAbsent) {
                            recordLockedRead(e, now);
                            unlock();
                            postWriteCleanup();
                            return entryValue;
                        } else {
                            this.modCount++;
                            enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                            setValue(e, key, value, now);
                            evictEntries();
                            unlock();
                            postWriteCleanup();
                            return entryValue;
                        }
                    }
                }
                this.modCount++;
                ReferenceEntry<K, V> newEntry = newEntry(key, hash, first);
                setValue(newEntry, key, value, now);
                table2.set(index, newEntry);
                this.count++;
                evictEntries();
                unlock();
                postWriteCleanup();
                return null;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> oldTable = this.table;
            int oldCapacity = oldTable.length();
            if (oldCapacity < 1073741824) {
                int newCount = this.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> newTable = newEntryArray(oldCapacity << 1);
                this.threshold = (newTable.length() * 3) / 4;
                int newMask = newTable.length() - 1;
                for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
                    ReferenceEntry<K, V> head = (ReferenceEntry) oldTable.get(oldIndex);
                    if (head != null) {
                        ReferenceEntry<K, V> next = head.getNext();
                        int headIndex = head.getHash() & newMask;
                        if (next == null) {
                            newTable.set(headIndex, head);
                        } else {
                            ReferenceEntry<K, V> tail = head;
                            int tailIndex = headIndex;
                            for (ReferenceEntry<K, V> e = next; e != null; e = e.getNext()) {
                                int newIndex = e.getHash() & newMask;
                                if (newIndex != tailIndex) {
                                    tailIndex = newIndex;
                                    tail = e;
                                }
                            }
                            newTable.set(tailIndex, tail);
                            for (ReferenceEntry<K, V> e2 = head; e2 != tail; e2 = e2.getNext()) {
                                int newIndex2 = e2.getHash() & newMask;
                                ReferenceEntry<K, V> newFirst = copyEntry(e2, (ReferenceEntry) newTable.get(newIndex2));
                                if (newFirst != null) {
                                    newTable.set(newIndex2, newFirst);
                                } else {
                                    removeCollectedEntry(e2);
                                    newCount--;
                                }
                            }
                        }
                    }
                }
                this.table = newTable;
                this.count = newCount;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean replace(K key, int hash, V oldValue, V newValue) {
            lock();
            try {
                long now = this.map.ticker.read();
                preWriteCleanup(now);
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else {
                        ValueReference<K, V> valueReference = e.getValueReference();
                        V entryValue = valueReference.get();
                        if (entryValue == null) {
                            if (valueReference.isActive()) {
                                int i = this.count - 1;
                                this.modCount++;
                                int newCount = this.count - 1;
                                table2.set(index, removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED));
                                this.count = newCount;
                            }
                            return false;
                        } else if (this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
                            this.modCount++;
                            enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                            setValue(e, key, newValue, now);
                            evictEntries();
                            unlock();
                            postWriteCleanup();
                            return true;
                        } else {
                            recordLockedRead(e, now);
                            unlock();
                            postWriteCleanup();
                            return false;
                        }
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V replace(K key, int hash, V newValue) {
            lock();
            try {
                long now = this.map.ticker.read();
                preWriteCleanup(now);
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else {
                        ValueReference<K, V> valueReference = e.getValueReference();
                        V entryValue = valueReference.get();
                        if (entryValue == null) {
                            if (valueReference.isActive()) {
                                int i = this.count - 1;
                                this.modCount++;
                                int newCount = this.count - 1;
                                table2.set(index, removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED));
                                this.count = newCount;
                            }
                            return null;
                        }
                        this.modCount++;
                        enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                        setValue(e, key, newValue, now);
                        evictEntries();
                        unlock();
                        postWriteCleanup();
                        return entryValue;
                    }
                }
                unlock();
                postWriteCleanup();
                return null;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V remove(Object key, int hash) {
            RemovalCause cause;
            lock();
            try {
                preWriteCleanup(this.map.ticker.read());
                int i = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else {
                        ValueReference<K, V> valueReference = e.getValueReference();
                        V entryValue = valueReference.get();
                        if (entryValue != null) {
                            cause = RemovalCause.EXPLICIT;
                        } else if (valueReference.isActive()) {
                            cause = RemovalCause.COLLECTED;
                        } else {
                            unlock();
                            postWriteCleanup();
                            return null;
                        }
                        this.modCount++;
                        int newCount = this.count - 1;
                        table2.set(index, removeValueFromChain(first, e, entryKey, hash, valueReference, cause));
                        this.count = newCount;
                        return entryValue;
                    }
                }
                unlock();
                postWriteCleanup();
                return null;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean storeLoadedValue(K key, int hash, LoadingValueReference<K, V> oldValueReference, V newValue) {
            lock();
            try {
                long now = this.map.ticker.read();
                preWriteCleanup(now);
                int newCount = this.count + 1;
                if (newCount > this.threshold) {
                    expand();
                    newCount = this.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else {
                        ValueReference<K, V> valueReference = e.getValueReference();
                        V entryValue = valueReference.get();
                        if (oldValueReference == valueReference || (entryValue == null && valueReference != LocalCache.UNSET)) {
                            this.modCount++;
                            if (oldValueReference.isActive()) {
                                enqueueNotification(key, hash, oldValueReference, entryValue == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED);
                                newCount--;
                            }
                            setValue(e, key, newValue, now);
                            this.count = newCount;
                            evictEntries();
                            return true;
                        }
                        WeightedStrongValueReference weightedStrongValueReference = new WeightedStrongValueReference(newValue, 0);
                        enqueueNotification(key, hash, weightedStrongValueReference, RemovalCause.REPLACED);
                        unlock();
                        postWriteCleanup();
                        return false;
                    }
                }
                this.modCount++;
                ReferenceEntry<K, V> newEntry = newEntry(key, hash, first);
                setValue(newEntry, key, newValue, now);
                table2.set(index, newEntry);
                this.count = newCount;
                evictEntries();
                unlock();
                postWriteCleanup();
                return true;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean remove(Object key, int hash, Object value) {
            RemovalCause cause;
            lock();
            try {
                preWriteCleanup(this.map.ticker.read());
                int i = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else {
                        ValueReference<K, V> valueReference = e.getValueReference();
                        V entryValue = valueReference.get();
                        if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                            cause = RemovalCause.EXPLICIT;
                        } else {
                            if (entryValue == null) {
                                if (valueReference.isActive()) {
                                    cause = RemovalCause.COLLECTED;
                                }
                            }
                            unlock();
                            postWriteCleanup();
                            return false;
                        }
                        this.modCount++;
                        int newCount = this.count - 1;
                        table2.set(index, removeValueFromChain(first, e, entryKey, hash, valueReference, cause));
                        this.count = newCount;
                        return cause == RemovalCause.EXPLICIT;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        public void clear() {
            if (this.count != 0) {
                lock();
                try {
                    AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                    for (int i = 0; i < table2.length(); i++) {
                        for (ReferenceEntry<K, V> e = (ReferenceEntry) table2.get(i); e != null; e = e.getNext()) {
                            if (e.getValueReference().isActive()) {
                                enqueueNotification(e, RemovalCause.EXPLICIT);
                            }
                        }
                    }
                    for (int i2 = 0; i2 < table2.length(); i2++) {
                        table2.set(i2, null);
                    }
                    clearReferenceQueues();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                } finally {
                    unlock();
                    postWriteCleanup();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        @Nullable
        public ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> first, ReferenceEntry<K, V> entry, @Nullable K key, int hash, ValueReference<K, V> valueReference, RemovalCause cause) {
            enqueueNotification(key, hash, valueReference, cause);
            this.writeQueue.remove(entry);
            this.accessQueue.remove(entry);
            if (!valueReference.isLoading()) {
                return removeEntryFromChain(first, entry);
            }
            valueReference.notifyNewValue(null);
            return first;
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        @Nullable
        public ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> first, ReferenceEntry<K, V> entry) {
            int newCount = this.count;
            ReferenceEntry<K, V> newFirst = entry.getNext();
            for (ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
                ReferenceEntry<K, V> next = copyEntry(e, newFirst);
                if (next != null) {
                    newFirst = next;
                } else {
                    removeCollectedEntry(e);
                    newCount--;
                }
            }
            this.count = newCount;
            return newFirst;
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void removeCollectedEntry(ReferenceEntry<K, V> entry) {
            enqueueNotification(entry, RemovalCause.COLLECTED);
            this.writeQueue.remove(entry);
            this.accessQueue.remove(entry);
        }

        /* access modifiers changed from: 0000 */
        public boolean reclaimKey(ReferenceEntry<K, V> entry, int hash) {
            lock();
            try {
                int i = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                for (ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
                    if (e == entry) {
                        this.modCount++;
                        int newCount = this.count - 1;
                        table2.set(index, removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference(), RemovalCause.COLLECTED));
                        this.count = newCount;
                        return true;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean reclaimValue(K key, int hash, ValueReference<K, V> valueReference) {
            boolean z = false;
            lock();
            try {
                int i = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (true) {
                    if (e != null) {
                        K entryKey = e.getKey();
                        if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                            e = e.getNext();
                        } else if (e.getValueReference() == valueReference) {
                            this.modCount++;
                            int newCount = this.count - 1;
                            table2.set(index, removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED));
                            this.count = newCount;
                            z = true;
                        } else {
                            unlock();
                            if (!isHeldByCurrentThread()) {
                                postWriteCleanup();
                            }
                        }
                    } else {
                        unlock();
                        if (!isHeldByCurrentThread()) {
                            postWriteCleanup();
                        }
                    }
                }
                return z;
            } finally {
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean removeLoadingValue(K key, int hash, LoadingValueReference<K, V> valueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = hash & (table2.length() - 1);
                ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else if (e.getValueReference() == valueReference) {
                        if (valueReference.isActive()) {
                            e.setValueReference(valueReference.getOldValue());
                        } else {
                            table2.set(index, removeEntryFromChain(first, e));
                        }
                        return true;
                    } else {
                        unlock();
                        postWriteCleanup();
                        return false;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public boolean removeEntry(ReferenceEntry<K, V> entry, int hash, RemovalCause cause) {
            int i = this.count - 1;
            AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
            int index = hash & (table2.length() - 1);
            ReferenceEntry<K, V> first = (ReferenceEntry) table2.get(index);
            for (ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
                if (e == entry) {
                    this.modCount++;
                    int newCount = this.count - 1;
                    table2.set(index, removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference(), cause));
                    this.count = newCount;
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                cleanUp();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public void preWriteCleanup(long now) {
            runLockedCleanup(now);
        }

        /* access modifiers changed from: 0000 */
        public void postWriteCleanup() {
            runUnlockedCleanup();
        }

        /* access modifiers changed from: 0000 */
        public void cleanUp() {
            runLockedCleanup(this.map.ticker.read());
            runUnlockedCleanup();
        }

        /* access modifiers changed from: 0000 */
        public void runLockedCleanup(long now) {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                    expireEntries(now);
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void runUnlockedCleanup() {
            if (!isHeldByCurrentThread()) {
                this.map.processPendingNotifications();
            }
        }
    }

    interface ValueReference<K, V> {
        ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @Nullable V v, ReferenceEntry<K, V> referenceEntry);

        @Nullable
        V get();

        @Nullable
        ReferenceEntry<K, V> getEntry();

        int getWeight();

        boolean isActive();

        boolean isLoading();

        void notifyNewValue(@Nullable V v);

        V waitForValue() throws ExecutionException;
    }

    abstract class AbstractCacheSet<T> extends AbstractSet<T> {
        final ConcurrentMap<?, ?> map;

        AbstractCacheSet(ConcurrentMap<?, ?> map2) {
            this.map = map2;
        }

        public int size() {
            return this.map.size();
        }

        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        public void clear() {
            this.map.clear();
        }
    }

    static abstract class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V> {
        AbstractReferenceEntry() {
        }

        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        public int getHash() {
            throw new UnsupportedOperationException();
        }

        public K getKey() {
            throw new UnsupportedOperationException();
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long time) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setWriteTime(long time) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }
    }

    static final class AccessQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>() {
            ReferenceEntry<K, V> nextAccess = this;
            ReferenceEntry<K, V> previousAccess = this;

            public long getAccessTime() {
                return LongCompanionObject.MAX_VALUE;
            }

            public void setAccessTime(long time) {
            }

            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.nextAccess;
            }

            public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
                this.nextAccess = next;
            }

            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.previousAccess;
            }

            public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
                this.previousAccess = previous;
            }
        };

        AccessQueue() {
        }

        public boolean offer(ReferenceEntry<K, V> entry) {
            LocalCache.connectAccessOrder(entry.getPreviousInAccessQueue(), entry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), entry);
            LocalCache.connectAccessOrder(entry, this.head);
            return true;
        }

        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
            if (next == this.head) {
                return null;
            }
            return next;
        }

        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
            if (next == this.head) {
                return null;
            }
            remove(next);
            return next;
        }

        public boolean remove(Object o) {
            ReferenceEntry<K, V> e = (ReferenceEntry) o;
            ReferenceEntry<K, V> previous = e.getPreviousInAccessQueue();
            ReferenceEntry<K, V> next = e.getNextInAccessQueue();
            LocalCache.connectAccessOrder(previous, next);
            LocalCache.nullifyAccessOrder(e);
            return next != NullEntry.INSTANCE;
        }

        public boolean contains(Object o) {
            return ((ReferenceEntry) o).getNextInAccessQueue() != NullEntry.INSTANCE;
        }

        public boolean isEmpty() {
            return this.head.getNextInAccessQueue() == this.head;
        }

        public int size() {
            int size = 0;
            for (ReferenceEntry<K, V> e = this.head.getNextInAccessQueue(); e != this.head; e = e.getNextInAccessQueue()) {
                size++;
            }
            return size;
        }

        public void clear() {
            ReferenceEntry<K, V> e = this.head.getNextInAccessQueue();
            while (e != this.head) {
                ReferenceEntry<K, V> next = e.getNextInAccessQueue();
                LocalCache.nullifyAccessOrder(e);
                e = next;
            }
            this.head.setNextInAccessQueue(this.head);
            this.head.setPreviousInAccessQueue(this.head);
        }

        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) {
                /* access modifiers changed from: protected */
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> previous) {
                    ReferenceEntry<K, V> next = previous.getNextInAccessQueue();
                    if (next == AccessQueue.this.head) {
                        return null;
                    }
                    return next;
                }
            };
        }
    }

    enum EntryFactory {
        STRONG {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
                return new StrongEntry(key, hash, next);
            }
        },
        STRONG_ACCESS {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
                return new StrongAccessEntry(key, hash, next);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                copyAccessEntry(original, newEntry);
                return newEntry;
            }
        },
        STRONG_WRITE {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
                return new StrongWriteEntry(key, hash, next);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                copyWriteEntry(original, newEntry);
                return newEntry;
            }
        },
        STRONG_ACCESS_WRITE {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
                return new StrongAccessWriteEntry(key, hash, next);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                copyAccessEntry(original, newEntry);
                copyWriteEntry(original, newEntry);
                return newEntry;
            }
        },
        WEAK {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
                return new WeakEntry(segment.keyReferenceQueue, key, hash, next);
            }
        },
        WEAK_ACCESS {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
                return new WeakAccessEntry(segment.keyReferenceQueue, key, hash, next);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                copyAccessEntry(original, newEntry);
                return newEntry;
            }
        },
        WEAK_WRITE {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
                return new WeakWriteEntry(segment.keyReferenceQueue, key, hash, next);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                copyWriteEntry(original, newEntry);
                return newEntry;
            }
        },
        WEAK_ACCESS_WRITE {
            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, key, hash, next);
            }

            /* access modifiers changed from: 0000 */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                copyAccessEntry(original, newEntry);
                copyWriteEntry(original, newEntry);
                return newEntry;
            }
        };
        
        static final int ACCESS_MASK = 1;
        static final int WEAK_MASK = 4;
        static final int WRITE_MASK = 2;
        static final EntryFactory[] factories = null;

        /* access modifiers changed from: 0000 */
        public abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @Nullable ReferenceEntry<K, V> referenceEntry);

        static {
            factories = new EntryFactory[]{STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE};
        }

        static EntryFactory getFactory(Strength keyStrength, boolean usesAccessQueue, boolean usesWriteQueue) {
            char c;
            int i = 0;
            if (keyStrength == Strength.WEAK) {
                c = 4;
            } else {
                c = 0;
            }
            int i2 = (usesAccessQueue ? (char) 1 : 0) | c;
            if (usesWriteQueue) {
                i = 2;
            }
            return factories[i2 | i];
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
            return newEntry(segment, original.getKey(), original.getHash(), newNext);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public <K, V> void copyAccessEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newEntry) {
            newEntry.setAccessTime(original.getAccessTime());
            LocalCache.connectAccessOrder(original.getPreviousInAccessQueue(), newEntry);
            LocalCache.connectAccessOrder(newEntry, original.getNextInAccessQueue());
            LocalCache.nullifyAccessOrder(original);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("Segment.this")
        public <K, V> void copyWriteEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newEntry) {
            newEntry.setWriteTime(original.getWriteTime());
            LocalCache.connectWriteOrder(original.getPreviousInWriteQueue(), newEntry);
            LocalCache.connectWriteOrder(newEntry, original.getNextInWriteQueue());
            LocalCache.nullifyWriteOrder(original);
        }
    }

    final class EntryIterator extends HashIterator<Entry<K, V>> {
        EntryIterator() {
            super();
        }

        public Entry<K, V> next() {
            return nextEntry();
        }
    }

    final class EntrySet extends AbstractCacheSet<Entry<K, V>> {
        EntrySet(ConcurrentMap<?, ?> map) {
            super(map);
        }

        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry<?, ?> e = (Entry) o;
            Object key = e.getKey();
            if (key == null) {
                return false;
            }
            V v = LocalCache.this.get(key);
            if (v == null || !LocalCache.this.valueEquivalence.equivalent(e.getValue(), v)) {
                return false;
            }
            return true;
        }

        public boolean remove(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry<?, ?> e = (Entry) o;
            Object key = e.getKey();
            if (key == null || !LocalCache.this.remove(key, e.getValue())) {
                return false;
            }
            return true;
        }
    }

    abstract class HashIterator<T> implements Iterator<T> {
        Segment<K, V> currentSegment;
        AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;
        WriteThroughEntry lastReturned;
        ReferenceEntry<K, V> nextEntry;
        WriteThroughEntry nextExternal;
        int nextSegmentIndex;
        int nextTableIndex = -1;

        public abstract T next();

        HashIterator() {
            this.nextSegmentIndex = LocalCache.this.segments.length - 1;
            advance();
        }

        /* access modifiers changed from: 0000 */
        public final void advance() {
            this.nextExternal = null;
            if (!nextInChain() && !nextInTable()) {
                while (this.nextSegmentIndex >= 0) {
                    Segment<K, V>[] segmentArr = LocalCache.this.segments;
                    int i = this.nextSegmentIndex;
                    this.nextSegmentIndex = i - 1;
                    this.currentSegment = segmentArr[i];
                    if (this.currentSegment.count != 0) {
                        this.currentTable = this.currentSegment.table;
                        this.nextTableIndex = this.currentTable.length() - 1;
                        if (nextInTable()) {
                            return;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean nextInChain() {
            if (this.nextEntry != null) {
                this.nextEntry = this.nextEntry.getNext();
                while (this.nextEntry != null) {
                    if (advanceTo(this.nextEntry)) {
                        return true;
                    }
                    this.nextEntry = this.nextEntry.getNext();
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean nextInTable() {
            while (this.nextTableIndex >= 0) {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.currentTable;
                int i = this.nextTableIndex;
                this.nextTableIndex = i - 1;
                ReferenceEntry<K, V> referenceEntry = (ReferenceEntry) atomicReferenceArray.get(i);
                this.nextEntry = referenceEntry;
                if (referenceEntry != null && (advanceTo(this.nextEntry) || nextInChain())) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean advanceTo(ReferenceEntry<K, V> entry) {
            try {
                long now = LocalCache.this.ticker.read();
                K key = entry.getKey();
                V value = LocalCache.this.getLiveValue(entry, now);
                if (value != null) {
                    this.nextExternal = new WriteThroughEntry<>(key, value);
                    return true;
                }
                this.currentSegment.postReadCleanup();
                return false;
            } finally {
                this.currentSegment.postReadCleanup();
            }
        }

        public boolean hasNext() {
            return this.nextExternal != null;
        }

        /* access modifiers changed from: 0000 */
        public WriteThroughEntry nextEntry() {
            if (this.nextExternal == null) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.nextExternal;
            advance();
            return this.lastReturned;
        }

        public void remove() {
            Preconditions.checkState(this.lastReturned != null);
            LocalCache.this.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }

    final class KeyIterator extends HashIterator<K> {
        KeyIterator() {
            super();
        }

        public K next() {
            return nextEntry().getKey();
        }
    }

    final class KeySet extends AbstractCacheSet<K> {
        KeySet(ConcurrentMap<?, ?> map) {
            super(map);
        }

        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        public boolean contains(Object o) {
            return this.map.containsKey(o);
        }

        public boolean remove(Object o) {
            return this.map.remove(o) != null;
        }
    }

    static final class LoadingSerializationProxy<K, V> extends ManualSerializationProxy<K, V> implements LoadingCache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        transient LoadingCache<K, V> autoDelegate;

        LoadingSerializationProxy(LocalCache<K, V> cache) {
            super(cache);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            this.autoDelegate = recreateCacheBuilder().build(this.loader);
        }

        public V get(K key) throws ExecutionException {
            return this.autoDelegate.get(key);
        }

        public V getUnchecked(K key) {
            return this.autoDelegate.getUnchecked(key);
        }

        public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
            return this.autoDelegate.getAll(keys);
        }

        public final V apply(K key) {
            return this.autoDelegate.apply(key);
        }

        public void refresh(K key) {
            this.autoDelegate.refresh(key);
        }

        private Object readResolve() {
            return this.autoDelegate;
        }
    }

    static class LoadingValueReference<K, V> implements ValueReference<K, V> {
        final SettableFuture<V> futureValue;
        volatile ValueReference<K, V> oldValue;
        final Stopwatch stopwatch;

        public LoadingValueReference() {
            this(LocalCache.unset());
        }

        public LoadingValueReference(ValueReference<K, V> oldValue2) {
            this.futureValue = SettableFuture.create();
            this.stopwatch = Stopwatch.createUnstarted();
            this.oldValue = oldValue2;
        }

        public boolean isLoading() {
            return true;
        }

        public boolean isActive() {
            return this.oldValue.isActive();
        }

        public int getWeight() {
            return this.oldValue.getWeight();
        }

        public boolean set(@Nullable V newValue) {
            return this.futureValue.set(newValue);
        }

        public boolean setException(Throwable t) {
            return this.futureValue.setException(t);
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable t) {
            return Futures.immediateFailedFuture(t);
        }

        public void notifyNewValue(@Nullable V newValue) {
            if (newValue != null) {
                set(newValue);
            } else {
                this.oldValue = LocalCache.unset();
            }
        }

        public ListenableFuture<V> loadFuture(K key, CacheLoader<? super K, V> loader) {
            this.stopwatch.start();
            V previousValue = this.oldValue.get();
            if (previousValue == null) {
                try {
                    V newValue = loader.load(key);
                    if (set(newValue)) {
                        return this.futureValue;
                    }
                    return Futures.immediateFuture(newValue);
                } catch (Throwable t) {
                    if (t instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                    }
                    return setException(t) ? this.futureValue : fullyFailedFuture(t);
                }
            } else {
                ListenableFuture<V> newValue2 = loader.reload(key, previousValue);
                if (newValue2 == null) {
                    return Futures.immediateFuture(null);
                }
                return Futures.transform(newValue2, (Function<? super I, ? extends O>) new Function<V, V>() {
                    public V apply(V newValue) {
                        LoadingValueReference.this.set(newValue);
                        return newValue;
                    }
                });
            }
        }

        public long elapsedNanos() {
            return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }

        public V waitForValue() throws ExecutionException {
            return Uninterruptibles.getUninterruptibly(this.futureValue);
        }

        public V get() {
            return this.oldValue.get();
        }

        public ValueReference<K, V> getOldValue() {
            return this.oldValue;
        }

        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @Nullable V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }
    }

    static class LocalLoadingCache<K, V> extends LocalManualCache<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;

        LocalLoadingCache(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader) {
            super();
        }

        public V get(K key) throws ExecutionException {
            return this.localCache.getOrLoad(key);
        }

        public V getUnchecked(K key) {
            try {
                return get(key);
            } catch (ExecutionException e) {
                throw new UncheckedExecutionException(e.getCause());
            }
        }

        public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
            return this.localCache.getAll(keys);
        }

        public void refresh(K key) {
            this.localCache.refresh(key);
        }

        public final V apply(K key) {
            return getUnchecked(key);
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new LoadingSerializationProxy(this.localCache);
        }
    }

    static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        final int concurrencyLevel;
        transient Cache<K, V> delegate;
        final long expireAfterAccessNanos;
        final long expireAfterWriteNanos;
        final Equivalence<Object> keyEquivalence;
        final Strength keyStrength;
        final CacheLoader<? super K, V> loader;
        final long maxWeight;
        final RemovalListener<? super K, ? super V> removalListener;
        final Ticker ticker;
        final Equivalence<Object> valueEquivalence;
        final Strength valueStrength;
        final Weigher<K, V> weigher;

        ManualSerializationProxy(LocalCache<K, V> cache) {
            this(cache.keyStrength, cache.valueStrength, cache.keyEquivalence, cache.valueEquivalence, cache.expireAfterWriteNanos, cache.expireAfterAccessNanos, cache.maxWeight, cache.weigher, cache.concurrencyLevel, cache.removalListener, cache.ticker, cache.defaultLoader);
        }

        private ManualSerializationProxy(Strength keyStrength2, Strength valueStrength2, Equivalence<Object> keyEquivalence2, Equivalence<Object> valueEquivalence2, long expireAfterWriteNanos2, long expireAfterAccessNanos2, long maxWeight2, Weigher<K, V> weigher2, int concurrencyLevel2, RemovalListener<? super K, ? super V> removalListener2, Ticker ticker2, CacheLoader<? super K, V> loader2) {
            this.keyStrength = keyStrength2;
            this.valueStrength = valueStrength2;
            this.keyEquivalence = keyEquivalence2;
            this.valueEquivalence = valueEquivalence2;
            this.expireAfterWriteNanos = expireAfterWriteNanos2;
            this.expireAfterAccessNanos = expireAfterAccessNanos2;
            this.maxWeight = maxWeight2;
            this.weigher = weigher2;
            this.concurrencyLevel = concurrencyLevel2;
            this.removalListener = removalListener2;
            if (ticker2 == Ticker.systemTicker() || ticker2 == CacheBuilder.NULL_TICKER) {
                ticker2 = null;
            }
            this.ticker = ticker2;
            this.loader = loader2;
        }

        /* access modifiers changed from: 0000 */
        public CacheBuilder<K, V> recreateCacheBuilder() {
            CacheBuilder<K, V> builder = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            builder.strictParsing = false;
            if (this.expireAfterWriteNanos > 0) {
                builder.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
            }
            if (this.expireAfterAccessNanos > 0) {
                builder.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
            }
            if (this.weigher != OneWeigher.INSTANCE) {
                builder.weigher(this.weigher);
                if (this.maxWeight != -1) {
                    builder.maximumWeight(this.maxWeight);
                }
            } else if (this.maxWeight != -1) {
                builder.maximumSize(this.maxWeight);
            }
            if (this.ticker != null) {
                builder.ticker(this.ticker);
            }
            return builder;
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            this.delegate = recreateCacheBuilder().build();
        }

        private Object readResolve() {
            return this.delegate;
        }

        /* access modifiers changed from: protected */
        public Cache<K, V> delegate() {
            return this.delegate;
        }
    }

    private enum NullEntry implements ReferenceEntry<Object, Object> {
        INSTANCE;

        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        public int getHash() {
            return 0;
        }

        public Object getKey() {
            return null;
        }

        public long getAccessTime() {
            return 0;
        }

        public void setAccessTime(long time) {
        }

        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        public void setNextInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public long getWriteTime() {
            return 0;
        }

        public void setWriteTime(long time) {
        }

        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        public void setNextInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }
    }

    static class SoftValueReference<K, V> extends SoftReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        SoftValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry2) {
            super(referent, queue);
            this.entry = entry2;
        }

        public int getWeight() {
            return 1;
        }

        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        public void notifyNewValue(V v) {
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry2) {
            return new SoftValueReference(queue, value, entry2);
        }

        public boolean isLoading() {
            return false;
        }

        public boolean isActive() {
            return true;
        }

        public V waitForValue() {
            return get();
        }
    }

    enum Strength {
        STRONG {
            /* access modifiers changed from: 0000 */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V value, int weight) {
                return weight == 1 ? new StrongValueReference(value) : new WeightedStrongValueReference(value, weight);
            }

            /* access modifiers changed from: 0000 */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }
        },
        SOFT {
            /* access modifiers changed from: 0000 */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
                return weight == 1 ? new SoftValueReference(segment.valueReferenceQueue, value, entry) : new WeightedSoftValueReference(segment.valueReferenceQueue, value, entry, weight);
            }

            /* access modifiers changed from: 0000 */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        },
        WEAK {
            /* access modifiers changed from: 0000 */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
                return weight == 1 ? new WeakValueReference(segment.valueReferenceQueue, value, entry) : new WeightedWeakValueReference(segment.valueReferenceQueue, value, entry, weight);
            }

            /* access modifiers changed from: 0000 */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract Equivalence<Object> defaultEquivalence();

        /* access modifiers changed from: 0000 */
        public abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i);
    }

    static final class StrongAccessEntry<K, V> extends StrongEntry<K, V> {
        volatile long accessTime = LongCompanionObject.MAX_VALUE;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        StrongAccessEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }
    }

    static final class StrongAccessWriteEntry<K, V> extends StrongEntry<K, V> {
        volatile long accessTime = LongCompanionObject.MAX_VALUE;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = LongCompanionObject.MAX_VALUE;

        StrongAccessWriteEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static class StrongEntry<K, V> extends AbstractReferenceEntry<K, V> {
        final int hash;
        final K key;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        StrongEntry(K key2, int hash2, @Nullable ReferenceEntry<K, V> next2) {
            this.key = key2;
            this.hash = hash2;
            this.next = next2;
        }

        public K getKey() {
            return this.key;
        }

        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        public void setValueReference(ValueReference<K, V> valueReference2) {
            this.valueReference = valueReference2;
        }

        public int getHash() {
            return this.hash;
        }

        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }

    static class StrongValueReference<K, V> implements ValueReference<K, V> {
        final V referent;

        StrongValueReference(V referent2) {
            this.referent = referent2;
        }

        public V get() {
            return this.referent;
        }

        public int getWeight() {
            return 1;
        }

        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public boolean isLoading() {
            return false;
        }

        public boolean isActive() {
            return true;
        }

        public V waitForValue() {
            return get();
        }

        public void notifyNewValue(V v) {
        }
    }

    static final class StrongWriteEntry<K, V> extends StrongEntry<K, V> {
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = LongCompanionObject.MAX_VALUE;

        StrongWriteEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    final class ValueIterator extends HashIterator<V> {
        ValueIterator() {
            super();
        }

        public V next() {
            return nextEntry().getValue();
        }
    }

    final class Values extends AbstractCollection<V> {
        private final ConcurrentMap<?, ?> map;

        Values(ConcurrentMap<?, ?> map2) {
            this.map = map2;
        }

        public int size() {
            return this.map.size();
        }

        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        public void clear() {
            this.map.clear();
        }

        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        public boolean contains(Object o) {
            return this.map.containsValue(o);
        }
    }

    static final class WeakAccessEntry<K, V> extends WeakEntry<K, V> {
        volatile long accessTime = LongCompanionObject.MAX_VALUE;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        WeakAccessEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }
    }

    static final class WeakAccessWriteEntry<K, V> extends WeakEntry<K, V> {
        volatile long accessTime = LongCompanionObject.MAX_VALUE;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = LongCompanionObject.MAX_VALUE;

        WeakAccessWriteEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        WeakEntry(ReferenceQueue<K> queue, K key, int hash2, @Nullable ReferenceEntry<K, V> next2) {
            super(key, queue);
            this.hash = hash2;
            this.next = next2;
        }

        public K getKey() {
            return get();
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long time) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setWriteTime(long time) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        public void setValueReference(ValueReference<K, V> valueReference2) {
            this.valueReference = valueReference2;
        }

        public int getHash() {
            return this.hash;
        }

        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }

    static class WeakValueReference<K, V> extends WeakReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        WeakValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry2) {
            super(referent, queue);
            this.entry = entry2;
        }

        public int getWeight() {
            return 1;
        }

        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        public void notifyNewValue(V v) {
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry2) {
            return new WeakValueReference(queue, value, entry2);
        }

        public boolean isLoading() {
            return false;
        }

        public boolean isActive() {
            return true;
        }

        public V waitForValue() {
            return get();
        }
    }

    static final class WeakWriteEntry<K, V> extends WeakEntry<K, V> {
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = LongCompanionObject.MAX_VALUE;

        WeakWriteEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static final class WeightedSoftValueReference<K, V> extends SoftValueReference<K, V> {
        final int weight;

        WeightedSoftValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry, int weight2) {
            super(queue, referent, entry);
            this.weight = weight2;
        }

        public int getWeight() {
            return this.weight;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry) {
            return new WeightedSoftValueReference(queue, value, entry, this.weight);
        }
    }

    static final class WeightedStrongValueReference<K, V> extends StrongValueReference<K, V> {
        final int weight;

        WeightedStrongValueReference(V referent, int weight2) {
            super(referent);
            this.weight = weight2;
        }

        public int getWeight() {
            return this.weight;
        }
    }

    static final class WeightedWeakValueReference<K, V> extends WeakValueReference<K, V> {
        final int weight;

        WeightedWeakValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry, int weight2) {
            super(queue, referent, entry);
            this.weight = weight2;
        }

        public int getWeight() {
            return this.weight;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry) {
            return new WeightedWeakValueReference(queue, value, entry, this.weight);
        }
    }

    static final class WriteQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>() {
            ReferenceEntry<K, V> nextWrite = this;
            ReferenceEntry<K, V> previousWrite = this;

            public long getWriteTime() {
                return LongCompanionObject.MAX_VALUE;
            }

            public void setWriteTime(long time) {
            }

            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.nextWrite;
            }

            public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
                this.nextWrite = next;
            }

            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.previousWrite;
            }

            public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
                this.previousWrite = previous;
            }
        };

        WriteQueue() {
        }

        public boolean offer(ReferenceEntry<K, V> entry) {
            LocalCache.connectWriteOrder(entry.getPreviousInWriteQueue(), entry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), entry);
            LocalCache.connectWriteOrder(entry, this.head);
            return true;
        }

        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
            if (next == this.head) {
                return null;
            }
            return next;
        }

        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
            if (next == this.head) {
                return null;
            }
            remove(next);
            return next;
        }

        public boolean remove(Object o) {
            ReferenceEntry<K, V> e = (ReferenceEntry) o;
            ReferenceEntry<K, V> previous = e.getPreviousInWriteQueue();
            ReferenceEntry<K, V> next = e.getNextInWriteQueue();
            LocalCache.connectWriteOrder(previous, next);
            LocalCache.nullifyWriteOrder(e);
            return next != NullEntry.INSTANCE;
        }

        public boolean contains(Object o) {
            return ((ReferenceEntry) o).getNextInWriteQueue() != NullEntry.INSTANCE;
        }

        public boolean isEmpty() {
            return this.head.getNextInWriteQueue() == this.head;
        }

        public int size() {
            int size = 0;
            for (ReferenceEntry<K, V> e = this.head.getNextInWriteQueue(); e != this.head; e = e.getNextInWriteQueue()) {
                size++;
            }
            return size;
        }

        public void clear() {
            ReferenceEntry<K, V> e = this.head.getNextInWriteQueue();
            while (e != this.head) {
                ReferenceEntry<K, V> next = e.getNextInWriteQueue();
                LocalCache.nullifyWriteOrder(e);
                e = next;
            }
            this.head.setNextInWriteQueue(this.head);
            this.head.setPreviousInWriteQueue(this.head);
        }

        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) {
                /* access modifiers changed from: protected */
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> previous) {
                    ReferenceEntry<K, V> next = previous.getNextInWriteQueue();
                    if (next == WriteQueue.this.head) {
                        return null;
                    }
                    return next;
                }
            };
        }
    }

    final class WriteThroughEntry implements Entry<K, V> {
        final K key;
        V value;

        WriteThroughEntry(K key2, V value2) {
            this.key = key2;
            this.value = value2;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public boolean equals(@Nullable Object object) {
            if (!(object instanceof Entry)) {
                return false;
            }
            Entry<?, ?> that = (Entry) object;
            if (!this.key.equals(that.getKey()) || !this.value.equals(that.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }

        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    LocalCache(CacheBuilder<? super K, ? super V> builder, @Nullable CacheLoader<? super K, V> loader) {
        Queue<RemovalNotification<K, V>> concurrentLinkedQueue;
        this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
        this.keyStrength = builder.getKeyStrength();
        this.valueStrength = builder.getValueStrength();
        this.keyEquivalence = builder.getKeyEquivalence();
        this.valueEquivalence = builder.getValueEquivalence();
        this.maxWeight = builder.getMaximumWeight();
        this.weigher = builder.getWeigher();
        this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
        this.refreshNanos = builder.getRefreshNanos();
        this.removalListener = builder.getRemovalListener();
        if (this.removalListener == NullListener.INSTANCE) {
            concurrentLinkedQueue = discardingQueue();
        } else {
            concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        }
        this.removalNotificationQueue = concurrentLinkedQueue;
        this.ticker = builder.getTicker(recordsTime());
        this.entryFactory = EntryFactory.getFactory(this.keyStrength, usesAccessEntries(), usesWriteEntries());
        this.globalStatsCounter = (StatsCounter) builder.getStatsCounterSupplier().get();
        this.defaultLoader = loader;
        int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
        if (evictsBySize() && !customWeigher()) {
            initialCapacity = Math.min(initialCapacity, (int) this.maxWeight);
        }
        int segmentShift2 = 0;
        int segmentCount = 1;
        while (segmentCount < this.concurrencyLevel && (!evictsBySize() || ((long) (segmentCount * 20)) <= this.maxWeight)) {
            segmentShift2++;
            segmentCount <<= 1;
        }
        this.segmentShift = 32 - segmentShift2;
        this.segmentMask = segmentCount - 1;
        this.segments = newSegmentArray(segmentCount);
        int segmentCapacity = initialCapacity / segmentCount;
        if (segmentCapacity * segmentCount < initialCapacity) {
            segmentCapacity++;
        }
        int segmentSize = 1;
        while (segmentSize < segmentCapacity) {
            segmentSize <<= 1;
        }
        if (evictsBySize()) {
            long maxSegmentWeight = (this.maxWeight / ((long) segmentCount)) + 1;
            long remainder = this.maxWeight % ((long) segmentCount);
            for (int i = 0; i < this.segments.length; i++) {
                if (((long) i) == remainder) {
                    maxSegmentWeight--;
                }
                this.segments[i] = createSegment(segmentSize, maxSegmentWeight, (StatsCounter) builder.getStatsCounterSupplier().get());
            }
            return;
        }
        for (int i2 = 0; i2 < this.segments.length; i2++) {
            this.segments[i2] = createSegment(segmentSize, -1, (StatsCounter) builder.getStatsCounterSupplier().get());
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean evictsBySize() {
        return this.maxWeight >= 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean customWeigher() {
        return this.weigher != OneWeigher.INSTANCE;
    }

    /* access modifiers changed from: 0000 */
    public boolean expires() {
        return expiresAfterWrite() || expiresAfterAccess();
    }

    /* access modifiers changed from: 0000 */
    public boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean refreshes() {
        return this.refreshNanos > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean usesAccessQueue() {
        return expiresAfterAccess() || evictsBySize();
    }

    /* access modifiers changed from: 0000 */
    public boolean usesWriteQueue() {
        return expiresAfterWrite();
    }

    /* access modifiers changed from: 0000 */
    public boolean recordsWrite() {
        return expiresAfterWrite() || refreshes();
    }

    /* access modifiers changed from: 0000 */
    public boolean recordsAccess() {
        return expiresAfterAccess();
    }

    /* access modifiers changed from: 0000 */
    public boolean recordsTime() {
        return recordsWrite() || recordsAccess();
    }

    /* access modifiers changed from: 0000 */
    public boolean usesWriteEntries() {
        return usesWriteQueue() || recordsWrite();
    }

    /* access modifiers changed from: 0000 */
    public boolean usesAccessEntries() {
        return usesAccessQueue() || recordsAccess();
    }

    /* access modifiers changed from: 0000 */
    public boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }

    /* access modifiers changed from: 0000 */
    public boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }

    static <K, V> ValueReference<K, V> unset() {
        return UNSET;
    }

    static <K, V> ReferenceEntry<K, V> nullEntry() {
        return NullEntry.INSTANCE;
    }

    static <E> Queue<E> discardingQueue() {
        return DISCARDING_QUEUE;
    }

    static int rehash(int h) {
        int h2 = h + ((h << 15) ^ -12931);
        int h3 = h2 ^ (h2 >>> 10);
        int h4 = h3 + (h3 << 3);
        int h5 = h4 ^ (h4 >>> 6);
        int h6 = h5 + (h5 << 2) + (h5 << 14);
        return (h6 >>> 16) ^ h6;
    }

    /* access modifiers changed from: 0000 */
    @GuardedBy("Segment.this")
    @VisibleForTesting
    public ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return segmentFor(hash).newEntry(key, hash, next);
    }

    /* access modifiers changed from: 0000 */
    @GuardedBy("Segment.this")
    @VisibleForTesting
    public ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return segmentFor(original.getHash()).copyEntry(original, newNext);
    }

    /* access modifiers changed from: 0000 */
    @GuardedBy("Segment.this")
    @VisibleForTesting
    public ValueReference<K, V> newValueReference(ReferenceEntry<K, V> entry, V value, int weight) {
        return this.valueStrength.referenceValue(segmentFor(entry.getHash()), entry, Preconditions.checkNotNull(value), weight);
    }

    /* access modifiers changed from: 0000 */
    public int hash(@Nullable Object key) {
        return rehash(this.keyEquivalence.hash(key));
    }

    /* access modifiers changed from: 0000 */
    public void reclaimValue(ValueReference<K, V> valueReference) {
        ReferenceEntry<K, V> entry = valueReference.getEntry();
        int hash = entry.getHash();
        segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }

    /* access modifiers changed from: 0000 */
    public void reclaimKey(ReferenceEntry<K, V> entry) {
        int hash = entry.getHash();
        segmentFor(hash).reclaimKey(entry, hash);
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public boolean isLive(ReferenceEntry<K, V> entry, long now) {
        return segmentFor(entry.getHash()).getLiveValue(entry, now) != null;
    }

    /* access modifiers changed from: 0000 */
    public Segment<K, V> segmentFor(int hash) {
        return this.segments[(hash >>> this.segmentShift) & this.segmentMask];
    }

    /* access modifiers changed from: 0000 */
    public Segment<K, V> createSegment(int initialCapacity, long maxSegmentWeight, StatsCounter statsCounter) {
        return new Segment<>(this, initialCapacity, maxSegmentWeight, statsCounter);
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public V getLiveValue(ReferenceEntry<K, V> entry, long now) {
        if (entry.getKey() == null) {
            return null;
        }
        V value = entry.getValueReference().get();
        if (value == null) {
            return null;
        }
        if (isExpired(entry, now)) {
            return null;
        }
        return value;
    }

    /* access modifiers changed from: 0000 */
    public boolean isExpired(ReferenceEntry<K, V> entry, long now) {
        Preconditions.checkNotNull(entry);
        if (expiresAfterAccess() && now - entry.getAccessTime() >= this.expireAfterAccessNanos) {
            return true;
        }
        if (!expiresAfterWrite() || now - entry.getWriteTime() < this.expireAfterWriteNanos) {
            return false;
        }
        return true;
    }

    @GuardedBy("Segment.this")
    static <K, V> void connectAccessOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
        previous.setNextInAccessQueue(next);
        next.setPreviousInAccessQueue(previous);
    }

    @GuardedBy("Segment.this")
    static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> nulled) {
        ReferenceEntry<K, V> nullEntry = nullEntry();
        nulled.setNextInAccessQueue(nullEntry);
        nulled.setPreviousInAccessQueue(nullEntry);
    }

    @GuardedBy("Segment.this")
    static <K, V> void connectWriteOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
        previous.setNextInWriteQueue(next);
        next.setPreviousInWriteQueue(previous);
    }

    @GuardedBy("Segment.this")
    static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> nulled) {
        ReferenceEntry<K, V> nullEntry = nullEntry();
        nulled.setNextInWriteQueue(nullEntry);
        nulled.setPreviousInWriteQueue(nullEntry);
    }

    /* access modifiers changed from: 0000 */
    public void processPendingNotifications() {
        while (true) {
            RemovalNotification<K, V> notification = (RemovalNotification) this.removalNotificationQueue.poll();
            if (notification != null) {
                try {
                    this.removalListener.onRemoval(notification);
                } catch (Throwable e) {
                    logger.log(Level.WARNING, "Exception thrown by removal listener", e);
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final Segment<K, V>[] newSegmentArray(int ssize) {
        return new Segment[ssize];
    }

    public void cleanUp() {
        for (Segment cleanUp : this.segments) {
            cleanUp.cleanUp();
        }
    }

    public boolean isEmpty() {
        long sum = 0;
        Segment<K, V>[] segments2 = this.segments;
        for (int i = 0; i < segments2.length; i++) {
            if (segments2[i].count != 0) {
                return false;
            }
            sum += (long) segments2[i].modCount;
        }
        if (sum != 0) {
            for (int i2 = 0; i2 < segments2.length; i2++) {
                if (segments2[i2].count != 0) {
                    return false;
                }
                sum -= (long) segments2[i2].modCount;
            }
            if (sum != 0) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public long longSize() {
        long sum = 0;
        for (Segment<K, V> segment : this.segments) {
            sum += (long) segment.count;
        }
        return sum;
    }

    public int size() {
        return Ints.saturatedCast(longSize());
    }

    @Nullable
    public V get(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).get(key, hash);
    }

    @Nullable
    public V getIfPresent(Object key) {
        int hash = hash(Preconditions.checkNotNull(key));
        V value = segmentFor(hash).get(key, hash);
        if (value == null) {
            this.globalStatsCounter.recordMisses(1);
        } else {
            this.globalStatsCounter.recordHits(1);
        }
        return value;
    }

    /* access modifiers changed from: 0000 */
    public V get(K key, CacheLoader<? super K, V> loader) throws ExecutionException {
        int hash = hash(Preconditions.checkNotNull(key));
        return segmentFor(hash).get(key, hash, loader);
    }

    /* access modifiers changed from: 0000 */
    public V getOrLoad(K key) throws ExecutionException {
        return get(key, this.defaultLoader);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
        int hits = 0;
        int misses = 0;
        Map<K, V> result = Maps.newLinkedHashMap();
        for (Object key : keys) {
            V value = get(key);
            if (value == null) {
                misses++;
            } else {
                result.put(key, value);
                hits++;
            }
        }
        this.globalStatsCounter.recordHits(hits);
        this.globalStatsCounter.recordMisses(misses);
        return ImmutableMap.copyOf(result);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
        int hits = 0;
        int misses = 0;
        Map<K, V> result = Maps.newLinkedHashMap();
        Set<K> keysToLoad = Sets.newLinkedHashSet();
        for (K key : keys) {
            V value = get(key);
            if (!result.containsKey(key)) {
                result.put(key, value);
                if (value == null) {
                    misses++;
                    keysToLoad.add(key);
                } else {
                    hits++;
                }
            }
        }
        try {
            if (!keysToLoad.isEmpty()) {
                Map<K, V> newEntries = loadAll(keysToLoad, this.defaultLoader);
                for (K key2 : keysToLoad) {
                    V value2 = newEntries.get(key2);
                    if (value2 == null) {
                        throw new InvalidCacheLoadException("loadAll failed to return a value for " + key2);
                    }
                    result.put(key2, value2);
                }
            }
        } catch (UnsupportedLoadingOperationException e) {
            for (K key3 : keysToLoad) {
                misses--;
                result.put(key3, get(key3, this.defaultLoader));
            }
        } catch (Throwable th) {
            this.globalStatsCounter.recordHits(hits);
            this.globalStatsCounter.recordMisses(misses);
            throw th;
        }
        ImmutableMap<K, V> copyOf = ImmutableMap.copyOf(result);
        this.globalStatsCounter.recordHits(hits);
        this.globalStatsCounter.recordMisses(misses);
        return copyOf;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Map<K, V> loadAll(Set<? extends K> keys, CacheLoader<? super K, V> loader) throws ExecutionException {
        Preconditions.checkNotNull(loader);
        Preconditions.checkNotNull(keys);
        Stopwatch stopwatch = Stopwatch.createStarted();
        boolean success = false;
        try {
            Map<K, V> result = loader.loadAll(keys);
            if (1 == 0) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            }
            if (result == null) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
                throw new InvalidCacheLoadException(loader + " returned null map from loadAll");
            }
            stopwatch.stop();
            boolean nullsPresent = false;
            for (Entry<K, V> entry : result.entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                if (key == null || value == null) {
                    nullsPresent = true;
                } else {
                    put(key, value);
                }
            }
            if (nullsPresent) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
                throw new InvalidCacheLoadException(loader + " returned null keys or values from loadAll");
            }
            this.globalStatsCounter.recordLoadSuccess(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            return result;
        } catch (UnsupportedLoadingOperationException e) {
            success = true;
            throw e;
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new ExecutionException(e2);
        } catch (RuntimeException e3) {
            throw new UncheckedExecutionException((Throwable) e3);
        } catch (Exception e4) {
            throw new ExecutionException(e4);
        } catch (Error e5) {
            throw new ExecutionError(e5);
        } catch (Throwable th) {
            if (!success) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public ReferenceEntry<K, V> getEntry(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).getEntry(key, hash);
    }

    /* access modifiers changed from: 0000 */
    public void refresh(K key) {
        int hash = hash(Preconditions.checkNotNull(key));
        segmentFor(hash).refresh(key, hash, this.defaultLoader, false);
    }

    public boolean containsKey(@Nullable Object key) {
        if (key == null) {
            return false;
        }
        int hash = hash(key);
        return segmentFor(hash).containsKey(key, hash);
    }

    public boolean containsValue(@Nullable Object value) {
        Segment[] arr$;
        if (value == null) {
            return false;
        }
        long now = this.ticker.read();
        Segment[] segments2 = this.segments;
        long last = -1;
        for (int i = 0; i < 3; i++) {
            long sum = 0;
            for (Segment segment : segments2) {
                int i2 = segment.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = segment.table;
                for (int j = 0; j < table.length(); j++) {
                    for (ReferenceEntry<K, V> e = (ReferenceEntry) table.get(j); e != null; e = e.getNext()) {
                        V v = segment.getLiveValue(e, now);
                        if (v != null && this.valueEquivalence.equivalent(value, v)) {
                            return true;
                        }
                    }
                }
                sum += (long) segment.modCount;
            }
            if (sum == last) {
                break;
            }
            last = sum;
        }
        return false;
    }

    public V put(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).put(key, hash, value, false);
    }

    public V putIfAbsent(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).put(key, hash, value, true);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public V remove(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).remove(key, hash);
    }

    public boolean remove(@Nullable Object key, @Nullable Object value) {
        if (key == null || value == null) {
            return false;
        }
        int hash = hash(key);
        return segmentFor(hash).remove(key, hash, value);
    }

    public boolean replace(K key, @Nullable V oldValue, V newValue) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(newValue);
        if (oldValue == null) {
            return false;
        }
        int hash = hash(key);
        return segmentFor(hash).replace(key, hash, oldValue, newValue);
    }

    public V replace(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).replace(key, hash, value);
    }

    public void clear() {
        for (Segment<K, V> segment : this.segments) {
            segment.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public void invalidateAll(Iterable<?> keys) {
        for (Object key : keys) {
            remove(key);
        }
    }

    public Set<K> keySet() {
        Set<K> ks = this.keySet;
        if (ks != null) {
            return ks;
        }
        Set<K> ks2 = new KeySet<>(this);
        this.keySet = ks2;
        return ks2;
    }

    public Collection<V> values() {
        Collection<V> vs = this.values;
        if (vs != null) {
            return vs;
        }
        Collection<V> vs2 = new Values<>(this);
        this.values = vs2;
        return vs2;
    }

    @GwtIncompatible("Not supported.")
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        Set<Entry<K, V>> es2 = new EntrySet<>(this);
        this.entrySet = es2;
        return es2;
    }
}
