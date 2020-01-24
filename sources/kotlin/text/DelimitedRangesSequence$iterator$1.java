package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000'\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\t\u0010\u001a\u001a\u00020\u001bH\u0002J\t\u0010\u001c\u001a\u00020\u0002H\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001c\u0010\r\u001a\u0004\u0018\u00010\u0002X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0007\"\u0004\b\u0014\u0010\tR\u001a\u0010\u0015\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0007\"\u0004\b\u0017\u0010\t¨\u0006\u001d"}, d2 = {"kotlin/text/DelimitedRangesSequence$iterator$1", "", "Lkotlin/ranges/IntRange;", "(Lkotlin/text/DelimitedRangesSequence;)V", "counter", "", "getCounter", "()I", "setCounter", "(I)V", "currentStartIndex", "getCurrentStartIndex", "setCurrentStartIndex", "nextItem", "getNextItem", "()Lkotlin/ranges/IntRange;", "setNextItem", "(Lkotlin/ranges/IntRange;)V", "nextSearchIndex", "getNextSearchIndex", "setNextSearchIndex", "nextState", "getNextState", "setNextState", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
/* compiled from: Strings.kt */
public final class DelimitedRangesSequence$iterator$1 implements Iterator<IntRange>, KMappedMarker {
    private int counter;
    private int currentStartIndex;
    @Nullable
    private IntRange nextItem;
    private int nextSearchIndex;
    private int nextState = -1;
    final /* synthetic */ DelimitedRangesSequence this$0;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    DelimitedRangesSequence$iterator$1(DelimitedRangesSequence $outer) {
        this.this$0 = $outer;
        this.currentStartIndex = RangesKt.coerceIn($outer.startIndex, 0, $outer.input.length());
        this.nextSearchIndex = this.currentStartIndex;
    }

    public final int getNextState() {
        return this.nextState;
    }

    public final void setNextState(int i) {
        this.nextState = i;
    }

    public final int getCurrentStartIndex() {
        return this.currentStartIndex;
    }

    public final void setCurrentStartIndex(int i) {
        this.currentStartIndex = i;
    }

    public final int getNextSearchIndex() {
        return this.nextSearchIndex;
    }

    public final void setNextSearchIndex(int i) {
        this.nextSearchIndex = i;
    }

    @Nullable
    public final IntRange getNextItem() {
        return this.nextItem;
    }

    public final void setNextItem(@Nullable IntRange intRange) {
        this.nextItem = intRange;
    }

    public final int getCounter() {
        return this.counter;
    }

    public final void setCounter(int i) {
        this.counter = i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0025, code lost:
        if (r8.counter < r8.this$0.limit) goto L_0x0027;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void calcNext() {
        /*
            r8 = this;
            r3 = 1
            r4 = 0
            r7 = -1
            int r2 = r8.nextSearchIndex
            if (r2 >= 0) goto L_0x000f
            r8.nextState = r4
            r2 = 0
            kotlin.ranges.IntRange r2 = (kotlin.ranges.IntRange) r2
            r8.nextItem = r2
        L_0x000e:
            return
        L_0x000f:
            kotlin.text.DelimitedRangesSequence r2 = r8.this$0
            int r2 = r2.limit
            if (r2 <= 0) goto L_0x0027
            int r2 = r8.counter
            int r2 = r2 + 1
            r8.counter = r2
            int r2 = r8.counter
            kotlin.text.DelimitedRangesSequence r5 = r8.this$0
            int r5 = r5.limit
            if (r2 >= r5) goto L_0x0035
        L_0x0027:
            int r2 = r8.nextSearchIndex
            kotlin.text.DelimitedRangesSequence r5 = r8.this$0
            java.lang.CharSequence r5 = r5.input
            int r5 = r5.length()
            if (r2 <= r5) goto L_0x004d
        L_0x0035:
            int r2 = r8.currentStartIndex
            kotlin.ranges.IntRange r4 = new kotlin.ranges.IntRange
            kotlin.text.DelimitedRangesSequence r5 = r8.this$0
            java.lang.CharSequence r5 = r5.input
            int r5 = kotlin.text.StringsKt.getLastIndex(r5)
            r4.<init>(r2, r5)
            r8.nextItem = r4
            r8.nextSearchIndex = r7
        L_0x004a:
            r8.nextState = r3
            goto L_0x000e
        L_0x004d:
            kotlin.text.DelimitedRangesSequence r2 = r8.this$0
            kotlin.jvm.functions.Function2 r2 = r2.getNextMatch
            kotlin.text.DelimitedRangesSequence r5 = r8.this$0
            java.lang.CharSequence r5 = r5.input
            int r6 = r8.nextSearchIndex
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            java.lang.Object r1 = r2.invoke(r5, r6)
            kotlin.Pair r1 = (kotlin.Pair) r1
            if (r1 != 0) goto L_0x007d
            int r2 = r8.currentStartIndex
            kotlin.ranges.IntRange r4 = new kotlin.ranges.IntRange
            kotlin.text.DelimitedRangesSequence r5 = r8.this$0
            java.lang.CharSequence r5 = r5.input
            int r5 = kotlin.text.StringsKt.getLastIndex(r5)
            r4.<init>(r2, r5)
            r8.nextItem = r4
            r8.nextSearchIndex = r7
            goto L_0x004a
        L_0x007d:
            java.lang.Object r2 = r1.component1()
            java.lang.Number r2 = (java.lang.Number) r2
            int r5 = r2.intValue()
            java.lang.Object r2 = r1.component2()
            java.lang.Number r2 = (java.lang.Number) r2
            int r0 = r2.intValue()
            int r2 = r8.currentStartIndex
            kotlin.ranges.IntRange r6 = new kotlin.ranges.IntRange
            int r7 = r5 + -1
            r6.<init>(r2, r7)
            r8.nextItem = r6
            int r2 = r5 + r0
            r8.currentStartIndex = r2
            int r5 = r8.currentStartIndex
            if (r0 != 0) goto L_0x00a9
            r2 = r3
        L_0x00a5:
            int r2 = r2 + r5
            r8.nextSearchIndex = r2
            goto L_0x004a
        L_0x00a9:
            r2 = r4
            goto L_0x00a5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.DelimitedRangesSequence$iterator$1.calcNext():void");
    }

    @NotNull
    public IntRange next() {
        if (this.nextState == -1) {
            calcNext();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        IntRange result = this.nextItem;
        if (result == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.ranges.IntRange");
        }
        this.nextItem = null;
        this.nextState = -1;
        return result;
    }

    public boolean hasNext() {
        if (this.nextState == -1) {
            calcNext();
        }
        if (this.nextState == 1) {
            return true;
        }
        return false;
    }
}
