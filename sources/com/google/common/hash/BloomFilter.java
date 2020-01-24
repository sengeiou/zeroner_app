package com.google.common.hash;

import com.github.mikephil.charting.utils.Utils;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import javax.annotation.Nullable;

@Beta
public final class BloomFilter<T> implements Predicate<T>, Serializable {
    private static final Strategy DEFAULT_STRATEGY = getDefaultStrategyFromSystemProperty();
    @VisibleForTesting
    static final String USE_MITZ32_PROPERTY = "com.google.common.hash.BloomFilter.useMitz32";
    /* access modifiers changed from: private */
    public final BitArray bits;
    /* access modifiers changed from: private */
    public final Funnel<T> funnel;
    /* access modifiers changed from: private */
    public final int numHashFunctions;
    /* access modifiers changed from: private */
    public final Strategy strategy;

    private static class SerialForm<T> implements Serializable {
        private static final long serialVersionUID = 1;
        final long[] data;
        final Funnel<T> funnel;
        final int numHashFunctions;
        final Strategy strategy;

        SerialForm(BloomFilter<T> bf) {
            this.data = bf.bits.data;
            this.numHashFunctions = bf.numHashFunctions;
            this.funnel = bf.funnel;
            this.strategy = bf.strategy;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return new BloomFilter(new BitArray(this.data), this.numHashFunctions, this.funnel, this.strategy);
        }
    }

    interface Strategy extends Serializable {
        <T> boolean mightContain(T t, Funnel<? super T> funnel, int i, BitArray bitArray);

        int ordinal();

        <T> boolean put(T t, Funnel<? super T> funnel, int i, BitArray bitArray);
    }

    private BloomFilter(BitArray bits2, int numHashFunctions2, Funnel<T> funnel2, Strategy strategy2) {
        boolean z;
        Preconditions.checkArgument(numHashFunctions2 > 0, "numHashFunctions (%s) must be > 0", Integer.valueOf(numHashFunctions2));
        if (numHashFunctions2 <= 255) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "numHashFunctions (%s) must be <= 255", Integer.valueOf(numHashFunctions2));
        this.bits = (BitArray) Preconditions.checkNotNull(bits2);
        this.numHashFunctions = numHashFunctions2;
        this.funnel = (Funnel) Preconditions.checkNotNull(funnel2);
        this.strategy = (Strategy) Preconditions.checkNotNull(strategy2);
    }

    public BloomFilter<T> copy() {
        return new BloomFilter<>(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
    }

    public boolean mightContain(T object) {
        return this.strategy.mightContain(object, this.funnel, this.numHashFunctions, this.bits);
    }

    @Deprecated
    public boolean apply(T input) {
        return mightContain(input);
    }

    public boolean put(T object) {
        return this.strategy.put(object, this.funnel, this.numHashFunctions, this.bits);
    }

    public double expectedFpp() {
        return Math.pow(((double) this.bits.bitCount()) / ((double) bitSize()), (double) this.numHashFunctions);
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public long bitSize() {
        return this.bits.bitSize();
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.common.hash.BloomFilter<T>, code=com.google.common.hash.BloomFilter, for r5v0, types: [com.google.common.hash.BloomFilter<T>, com.google.common.hash.BloomFilter, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isCompatible(com.google.common.hash.BloomFilter r5) {
        /*
            r4 = this;
            com.google.common.base.Preconditions.checkNotNull(r5)
            if (r4 == r5) goto L_0x002d
            int r0 = r4.numHashFunctions
            int r1 = r5.numHashFunctions
            if (r0 != r1) goto L_0x002d
            long r0 = r4.bitSize()
            long r2 = r5.bitSize()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L_0x002d
            com.google.common.hash.BloomFilter$Strategy r0 = r4.strategy
            com.google.common.hash.BloomFilter$Strategy r1 = r5.strategy
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002d
            com.google.common.hash.Funnel<T> r0 = r4.funnel
            com.google.common.hash.Funnel<T> r1 = r5.funnel
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002d
            r0 = 1
        L_0x002c:
            return r0
        L_0x002d:
            r0 = 0
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.BloomFilter.isCompatible(com.google.common.hash.BloomFilter):boolean");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.common.hash.BloomFilter<T>, code=com.google.common.hash.BloomFilter, for r10v0, types: [com.google.common.hash.BloomFilter<T>, com.google.common.hash.BloomFilter, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void putAll(com.google.common.hash.BloomFilter r10) {
        /*
            r9 = this;
            r8 = 2
            r1 = 1
            r2 = 0
            com.google.common.base.Preconditions.checkNotNull(r10)
            if (r9 == r10) goto L_0x008f
            r0 = r1
        L_0x0009:
            java.lang.String r3 = "Cannot combine a BloomFilter with itself."
            com.google.common.base.Preconditions.checkArgument(r0, r3)
            int r0 = r9.numHashFunctions
            int r3 = r10.numHashFunctions
            if (r0 != r3) goto L_0x0092
            r0 = r1
        L_0x0016:
            java.lang.String r3 = "BloomFilters must have the same number of hash functions (%s != %s)"
            java.lang.Object[] r4 = new java.lang.Object[r8]
            int r5 = r9.numHashFunctions
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r2] = r5
            int r5 = r10.numHashFunctions
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r1] = r5
            com.google.common.base.Preconditions.checkArgument(r0, r3, r4)
            long r4 = r9.bitSize()
            long r6 = r10.bitSize()
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 != 0) goto L_0x0094
            r0 = r1
        L_0x003b:
            java.lang.String r3 = "BloomFilters must have the same size underlying bit arrays (%s != %s)"
            java.lang.Object[] r4 = new java.lang.Object[r8]
            long r6 = r9.bitSize()
            java.lang.Long r5 = java.lang.Long.valueOf(r6)
            r4[r2] = r5
            long r6 = r10.bitSize()
            java.lang.Long r5 = java.lang.Long.valueOf(r6)
            r4[r1] = r5
            com.google.common.base.Preconditions.checkArgument(r0, r3, r4)
            com.google.common.hash.BloomFilter$Strategy r0 = r9.strategy
            com.google.common.hash.BloomFilter$Strategy r3 = r10.strategy
            boolean r0 = r0.equals(r3)
            java.lang.String r3 = "BloomFilters must have equal strategies (%s != %s)"
            java.lang.Object[] r4 = new java.lang.Object[r8]
            com.google.common.hash.BloomFilter$Strategy r5 = r9.strategy
            r4[r2] = r5
            com.google.common.hash.BloomFilter$Strategy r5 = r10.strategy
            r4[r1] = r5
            com.google.common.base.Preconditions.checkArgument(r0, r3, r4)
            com.google.common.hash.Funnel<T> r0 = r9.funnel
            com.google.common.hash.Funnel<T> r3 = r10.funnel
            boolean r0 = r0.equals(r3)
            java.lang.String r3 = "BloomFilters must have equal funnels (%s != %s)"
            java.lang.Object[] r4 = new java.lang.Object[r8]
            com.google.common.hash.Funnel<T> r5 = r9.funnel
            r4[r2] = r5
            com.google.common.hash.Funnel<T> r2 = r10.funnel
            r4[r1] = r2
            com.google.common.base.Preconditions.checkArgument(r0, r3, r4)
            com.google.common.hash.BloomFilterStrategies$BitArray r0 = r9.bits
            com.google.common.hash.BloomFilterStrategies$BitArray r1 = r10.bits
            r0.putAll(r1)
            return
        L_0x008f:
            r0 = r2
            goto L_0x0009
        L_0x0092:
            r0 = r2
            goto L_0x0016
        L_0x0094:
            r0 = r2
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.BloomFilter.putAll(com.google.common.hash.BloomFilter):void");
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof BloomFilter)) {
            return false;
        }
        BloomFilter<?> that = (BloomFilter) object;
        if (this.numHashFunctions != that.numHashFunctions || !this.funnel.equals(that.funnel) || !this.bits.equals(that.bits) || !this.strategy.equals(that.strategy)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.numHashFunctions), this.funnel, this.strategy, this.bits);
    }

    @VisibleForTesting
    static Strategy getDefaultStrategyFromSystemProperty() {
        return Boolean.parseBoolean(System.getProperty(USE_MITZ32_PROPERTY)) ? BloomFilterStrategies.MURMUR128_MITZ_32 : BloomFilterStrategies.MURMUR128_MITZ_64;
    }

    public static <T> BloomFilter<T> create(Funnel<T> funnel2, int expectedInsertions, double fpp) {
        return create(funnel2, expectedInsertions, fpp, DEFAULT_STRATEGY);
    }

    @VisibleForTesting
    static <T> BloomFilter<T> create(Funnel<T> funnel2, int expectedInsertions, double fpp, Strategy strategy2) {
        boolean z;
        boolean z2;
        boolean z3;
        Preconditions.checkNotNull(funnel2);
        if (expectedInsertions >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Expected insertions (%s) must be >= 0", Integer.valueOf(expectedInsertions));
        if (fpp > Utils.DOUBLE_EPSILON) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "False positive probability (%s) must be > 0.0", Double.valueOf(fpp));
        if (fpp < 1.0d) {
            z3 = true;
        } else {
            z3 = false;
        }
        Preconditions.checkArgument(z3, "False positive probability (%s) must be < 1.0", Double.valueOf(fpp));
        Preconditions.checkNotNull(strategy2);
        if (expectedInsertions == 0) {
            expectedInsertions = 1;
        }
        long numBits = optimalNumOfBits((long) expectedInsertions, fpp);
        try {
            return new BloomFilter<>(new BitArray(numBits), optimalNumOfHashFunctions((long) expectedInsertions, numBits), funnel2, strategy2);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Could not create BloomFilter of " + numBits + " bits", e);
        }
    }

    public static <T> BloomFilter<T> create(Funnel<T> funnel2, int expectedInsertions) {
        return create(funnel2, expectedInsertions, 0.03d);
    }

    @VisibleForTesting
    static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round(((double) (m / n)) * Math.log(2.0d)));
    }

    @VisibleForTesting
    static long optimalNumOfBits(long n, double p) {
        if (p == Utils.DOUBLE_EPSILON) {
            p = Double.MIN_VALUE;
        }
        return (long) ((((double) (-n)) * Math.log(p)) / (Math.log(2.0d) * Math.log(2.0d)));
    }

    private Object writeReplace() {
        return new SerialForm(this);
    }
}
