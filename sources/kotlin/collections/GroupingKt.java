package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000L\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\u0005\u001a\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\b\u001a´\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\b¢\u0006\u0002\u0010\u0013\u001a0\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aI\u0010\u0016\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007¢\u0006\u0002\u0010\u0017\u001a¼\u0001\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0019\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001a2K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001bH\b\u001a|\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001c\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\b¢\u0006\u0002\u0010\u001d\u001aÕ\u0001\u0010\u001e\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0019\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001a2K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001bH\b¢\u0006\u0002\u0010\u001f\u001a\u0001\u0010\u001e\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001c\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\b¢\u0006\u0002\u0010 \u001aW\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\"\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\"0\u00112\u001e\u0010#\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\"0%\u0012\u0004\u0012\u0002H\u00030$H\b\u001a\u0001\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H'0\u0001\"\u0004\b\u0000\u0010'\"\b\b\u0001\u0010\u0004*\u0002H'\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H'¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H'0\u001bH\b\u001a¡\u0001\u0010(\u001a\u0002H\u0010\"\u0004\b\u0000\u0010'\"\b\b\u0001\u0010\u0004*\u0002H'\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H'0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H'¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H'0\u001bH\b¢\u0006\u0002\u0010)¨\u0006*"}, d2 = {"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCount", "", "eachCountTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "mapValuesInPlace", "V", "f", "Lkotlin/Function1;", "", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"}, k = 2, mv = {1, 1, 8})
/* compiled from: Grouping.kt */
public final class GroupingKt {
    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <T, K, R> Map<K, R> aggregate(@NotNull Grouping<T, ? extends K> $receiver, @NotNull Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Map<K, R> linkedHashMap = new LinkedHashMap<>();
        Iterator sourceIterator = $receiver.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv = sourceIterator.next();
            Object key$iv = $receiver.keyOf(e$iv);
            Object accumulator$iv = linkedHashMap.get(key$iv);
            linkedHashMap.put(key$iv, operation.invoke(key$iv, accumulator$iv, e$iv, Boolean.valueOf(accumulator$iv == null && !linkedHashMap.containsKey(key$iv))));
        }
        return linkedHashMap;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <T, K, R, M extends Map<? super K, R>> M aggregateTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination, @NotNull Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Iterator sourceIterator = $receiver.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e = sourceIterator.next();
            Object key = $receiver.keyOf(e);
            Object accumulator = destination.get(key);
            destination.put(key, operation.invoke(key, accumulator, e, Boolean.valueOf(accumulator == null && !destination.containsKey(key))));
        }
        return destination;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <T, K, R> Map<K, R> fold(@NotNull Grouping<T, ? extends K> $receiver, @NotNull Function2<? super K, ? super T, ? extends R> initialValueSelector, @NotNull Function3<? super K, ? super R, ? super T, ? extends R> operation) {
        boolean z;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(initialValueSelector, "initialValueSelector");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping $receiver$iv = $receiver;
        Map<K, R> linkedHashMap = new LinkedHashMap<>();
        Iterator sourceIterator = $receiver$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv$iv = sourceIterator.next();
            Object key$iv$iv = $receiver$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = linkedHashMap.get(key$iv$iv);
            if (accumulator$iv$iv != null || linkedHashMap.containsKey(key$iv$iv)) {
                z = false;
            } else {
                z = true;
            }
            Object key = key$iv$iv;
            if (z) {
                accumulator$iv$iv = initialValueSelector.invoke(key, e$iv$iv);
            }
            linkedHashMap.put(key$iv$iv, operation.invoke(key, accumulator$iv$iv, e$iv$iv));
        }
        return linkedHashMap;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <T, K, R, M extends Map<? super K, R>> M foldTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination, @NotNull Function2<? super K, ? super T, ? extends R> initialValueSelector, @NotNull Function3<? super K, ? super R, ? super T, ? extends R> operation) {
        boolean z;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(initialValueSelector, "initialValueSelector");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping $receiver$iv = $receiver;
        Iterator sourceIterator = $receiver$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv = sourceIterator.next();
            Object key$iv = $receiver$iv.keyOf(e$iv);
            Object accumulator$iv = destination.get(key$iv);
            if (accumulator$iv != null || destination.containsKey(key$iv)) {
                z = false;
            } else {
                z = true;
            }
            Object key = key$iv;
            if (z) {
                accumulator$iv = initialValueSelector.invoke(key, e$iv);
            }
            destination.put(key$iv, operation.invoke(key, accumulator$iv, e$iv));
        }
        return destination;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <T, K, R> Map<K, R> fold(@NotNull Grouping<T, ? extends K> $receiver, R initialValue, @NotNull Function2<? super R, ? super T, ? extends R> operation) {
        boolean z;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping $receiver$iv = $receiver;
        Map<K, R> linkedHashMap = new LinkedHashMap<>();
        Iterator sourceIterator = $receiver$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv$iv = sourceIterator.next();
            Object key$iv$iv = $receiver$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = linkedHashMap.get(key$iv$iv);
            if (accumulator$iv$iv != null || linkedHashMap.containsKey(key$iv$iv)) {
                z = false;
            } else {
                z = true;
            }
            Object obj = key$iv$iv;
            if (z) {
                accumulator$iv$iv = initialValue;
            }
            linkedHashMap.put(key$iv$iv, operation.invoke(accumulator$iv$iv, e$iv$iv));
        }
        return linkedHashMap;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <T, K, R, M extends Map<? super K, R>> M foldTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination, R initialValue, @NotNull Function2<? super R, ? super T, ? extends R> operation) {
        boolean z;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping $receiver$iv = $receiver;
        Iterator sourceIterator = $receiver$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv = sourceIterator.next();
            Object key$iv = $receiver$iv.keyOf(e$iv);
            Object accumulator$iv = destination.get(key$iv);
            if (accumulator$iv != null || destination.containsKey(key$iv)) {
                z = false;
            } else {
                z = true;
            }
            Object obj = key$iv;
            if (z) {
                accumulator$iv = initialValue;
            }
            destination.put(key$iv, operation.invoke(accumulator$iv, e$iv));
        }
        return destination;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <S, T extends S, K> Map<K, S> reduce(@NotNull Grouping<T, ? extends K> $receiver, @NotNull Function3<? super K, ? super S, ? super T, ? extends S> operation) {
        boolean z;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping $receiver$iv = $receiver;
        Map<K, S> linkedHashMap = new LinkedHashMap<>();
        Iterator sourceIterator = $receiver$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv$iv = sourceIterator.next();
            Object key$iv$iv = $receiver$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = linkedHashMap.get(key$iv$iv);
            if (accumulator$iv$iv != null || linkedHashMap.containsKey(key$iv$iv)) {
                z = false;
            } else {
                z = true;
            }
            Object key = key$iv$iv;
            if (!z) {
                e$iv$iv = operation.invoke(key, accumulator$iv$iv, e$iv$iv);
            }
            linkedHashMap.put(key$iv$iv, e$iv$iv);
        }
        return linkedHashMap;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <S, T extends S, K, M extends Map<? super K, S>> M reduceTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination, @NotNull Function3<? super K, ? super S, ? super T, ? extends S> operation) {
        boolean z;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping $receiver$iv = $receiver;
        Iterator sourceIterator = $receiver$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object e$iv = sourceIterator.next();
            Object key$iv = $receiver$iv.keyOf(e$iv);
            Object accumulator$iv = destination.get(key$iv);
            if (accumulator$iv != null || destination.containsKey(key$iv)) {
                z = false;
            } else {
                z = true;
            }
            Object key = key$iv;
            if (!z) {
                e$iv = operation.invoke(key, accumulator$iv, e$iv);
            }
            destination.put(key$iv, e$iv);
        }
        return destination;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <T, K> Map<K, Integer> eachCount(@NotNull Grouping<T, ? extends K> $receiver) {
        Object obj;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Map linkedHashMap = new LinkedHashMap();
        Grouping $receiver$iv$iv = $receiver;
        Iterator sourceIterator = $receiver$iv$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object key$iv$iv = $receiver$iv$iv.keyOf(sourceIterator.next());
            Object accumulator$iv$iv = linkedHashMap.get(key$iv$iv);
            Object key$iv = key$iv$iv;
            if (accumulator$iv$iv == null && !linkedHashMap.containsKey(key$iv$iv)) {
                obj = new IntRef();
            } else {
                obj = accumulator$iv$iv;
            }
            Object obj2 = key$iv;
            IntRef $receiver2 = (IntRef) obj;
            $receiver2.element++;
            linkedHashMap.put(key$iv$iv, $receiver2);
        }
        for (Entry entry : linkedHashMap.entrySet()) {
            if (entry == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
            }
            TypeIntrinsics.asMutableMapEntry(entry).setValue(Integer.valueOf(((IntRef) entry.getValue()).element));
        }
        return TypeIntrinsics.asMutableMap(linkedHashMap);
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <T, K, M extends Map<? super K, Integer>> M eachCountTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination) {
        boolean z;
        Object obj;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Integer initialValue$iv = Integer.valueOf(0);
        Grouping $receiver$iv$iv = $receiver;
        Iterator sourceIterator = $receiver$iv$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object key$iv$iv = $receiver$iv$iv.keyOf(sourceIterator.next());
            Object accumulator$iv$iv = destination.get(key$iv$iv);
            if (accumulator$iv$iv != null || destination.containsKey(key$iv$iv)) {
                z = false;
            } else {
                z = true;
            }
            Object obj2 = key$iv$iv;
            if (z) {
                obj = initialValue$iv;
            } else {
                obj = accumulator$iv$iv;
            }
            destination.put(key$iv$iv, Integer.valueOf(((Number) obj).intValue() + 1));
        }
        return destination;
    }

    @PublishedApi
    @InlineOnly
    private static final <K, V, R> Map<K, R> mapValuesInPlace(@NotNull Map<K, V> $receiver, Function1<? super Entry<? extends K, ? extends V>, ? extends R> f) {
        for (Entry it : $receiver.entrySet()) {
            if (it == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
            }
            TypeIntrinsics.asMutableMapEntry(it).setValue(f.invoke(it));
        }
        if ($receiver != null) {
            return TypeIntrinsics.asMutableMap($receiver);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, R>");
    }
}
