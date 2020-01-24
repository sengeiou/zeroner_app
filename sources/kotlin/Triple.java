package kotlin;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u0001*\u0006\b\u0002\u0010\u0003 \u00012\u00060\u0004j\u0002`\u0005B\u001d\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0001\u0012\u0006\u0010\b\u001a\u00028\u0002¢\u0006\u0002\u0010\tJ\u000e\u0010\u000f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0010\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0011\u001a\u00028\u0002HÆ\u0003¢\u0006\u0002\u0010\u000bJ>\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00028\u00002\b\b\u0002\u0010\u0007\u001a\u00028\u00012\b\b\u0002\u0010\b\u001a\u00028\u0002HÆ\u0001¢\u0006\u0002\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\r\u0010\u000bR\u0013\u0010\b\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u001c"}, d2 = {"Lkotlin/Triple;", "A", "B", "C", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "first", "second", "third", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getSecond", "getThird", "component1", "component2", "component3", "copy", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Triple;", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
/* compiled from: Tuples.kt */
public final class Triple<A, B, C> implements Serializable {
    private final A first;
    private final B second;
    private final C third;

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object, code=A, for r2v0, types: [java.lang.Object] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object, code=B, for r3v0, types: [java.lang.Object] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object, code=C, for r4v0, types: [java.lang.Object] */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* bridge */ /* synthetic */ kotlin.Triple copy$default(kotlin.Triple r1, A r2, B r3, C r4, int r5, java.lang.Object r6) {
        /*
            r0 = r5 & 1
            if (r0 == 0) goto L_0x0006
            A r2 = r1.first
        L_0x0006:
            r0 = r5 & 2
            if (r0 == 0) goto L_0x000c
            B r3 = r1.second
        L_0x000c:
            r0 = r5 & 4
            if (r0 == 0) goto L_0x0012
            C r4 = r1.third
        L_0x0012:
            kotlin.Triple r0 = r1.copy(r2, r3, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.Triple.copy$default(kotlin.Triple, java.lang.Object, java.lang.Object, java.lang.Object, int, java.lang.Object):kotlin.Triple");
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public final C component3() {
        return this.third;
    }

    @NotNull
    public final Triple<A, B, C> copy(A first2, B second2, C third2) {
        return new Triple<>(first2, second2, third2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0024, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r2.third, (java.lang.Object) r3.third) != false) goto L_0x0026;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r2 == r3) goto L_0x0026
            boolean r0 = r3 instanceof kotlin.Triple
            if (r0 == 0) goto L_0x0028
            kotlin.Triple r3 = (kotlin.Triple) r3
            A r0 = r2.first
            A r1 = r3.first
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 == 0) goto L_0x0028
            B r0 = r2.second
            B r1 = r3.second
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 == 0) goto L_0x0028
            C r0 = r2.third
            C r1 = r3.third
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 == 0) goto L_0x0028
        L_0x0026:
            r0 = 1
        L_0x0027:
            return r0
        L_0x0028:
            r0 = 0
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.Triple.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = 0;
        A a = this.first;
        int hashCode = (a != null ? a.hashCode() : 0) * 31;
        B b = this.second;
        int hashCode2 = ((b != null ? b.hashCode() : 0) + hashCode) * 31;
        C c = this.third;
        if (c != null) {
            i = c.hashCode();
        }
        return hashCode2 + i;
    }

    public Triple(A first2, B second2, C third2) {
        this.first = first2;
        this.second = second2;
        this.third = third2;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public final C getThird() {
        return this.third;
    }

    @NotNull
    public String toString() {
        return '(' + this.first + ", " + this.second + ", " + this.third + ')';
    }
}
