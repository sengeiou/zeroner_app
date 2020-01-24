package kotlin;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00012\u00060\u0003j\u0002`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00028\u0001¢\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\tJ\u000e\u0010\r\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\tJ.\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0005\u001a\u00028\u00002\b\b\u0002\u0010\u0006\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u000b\u0010\t¨\u0006\u0018"}, d2 = {"Lkotlin/Pair;", "A", "B", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "first", "second", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getSecond", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
/* compiled from: Tuples.kt */
public final class Pair<A, B> implements Serializable {
    private final A first;
    private final B second;

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object, code=A, for r2v0, types: [java.lang.Object] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object, code=B, for r3v0, types: [java.lang.Object] */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* bridge */ /* synthetic */ kotlin.Pair copy$default(kotlin.Pair r1, A r2, B r3, int r4, java.lang.Object r5) {
        /*
            r0 = r4 & 1
            if (r0 == 0) goto L_0x0006
            A r2 = r1.first
        L_0x0006:
            r0 = r4 & 2
            if (r0 == 0) goto L_0x000c
            B r3 = r1.second
        L_0x000c:
            kotlin.Pair r0 = r1.copy(r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.Pair.copy$default(kotlin.Pair, java.lang.Object, java.lang.Object, int, java.lang.Object):kotlin.Pair");
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    @NotNull
    public final Pair<A, B> copy(A first2, B second2) {
        return new Pair<>(first2, second2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001a, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r2.second, (java.lang.Object) r3.second) != false) goto L_0x001c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r2 == r3) goto L_0x001c
            boolean r0 = r3 instanceof kotlin.Pair
            if (r0 == 0) goto L_0x001e
            kotlin.Pair r3 = (kotlin.Pair) r3
            A r0 = r2.first
            A r1 = r3.first
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 == 0) goto L_0x001e
            B r0 = r2.second
            B r1 = r3.second
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 == 0) goto L_0x001e
        L_0x001c:
            r0 = 1
        L_0x001d:
            return r0
        L_0x001e:
            r0 = 0
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.Pair.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = 0;
        A a = this.first;
        int hashCode = (a != null ? a.hashCode() : 0) * 31;
        B b = this.second;
        if (b != null) {
            i = b.hashCode();
        }
        return hashCode + i;
    }

    public Pair(A first2, B second2) {
        this.first = first2;
        this.second = second2;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    @NotNull
    public String toString() {
        return '(' + this.first + ", " + this.second + ')';
    }
}
