package kotlin.collections;

import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\u001a3\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001aQ\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00032!\u0010\b\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u0002H\u00010\t\u001aX\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\f2!\u0010\b\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u0002H\u00010\tH\u0007¢\u0006\u0002\b\r¨\u0006\u000e"}, d2 = {"getOrImplicitDefault", "V", "K", "", "key", "getOrImplicitDefaultNullable", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "withDefault", "defaultValue", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "", "withDefaultMutable", "kotlin-stdlib"}, k = 5, mv = {1, 1, 8}, xi = 1, xs = "kotlin/collections/MapsKt")
/* compiled from: MapWithDefault.kt */
class MapsKt__MapWithDefaultKt {
    @PublishedApi
    @JvmName(name = "getOrImplicitDefaultNullable")
    public static final <K, V> V getOrImplicitDefaultNullable(@NotNull Map<K, ? extends V> $receiver, K key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver instanceof MapWithDefault) {
            return ((MapWithDefault) $receiver).getOrImplicitDefault(key);
        }
        Map $receiver$iv = $receiver;
        Object value$iv = $receiver$iv.get(key);
        if (value$iv != null || $receiver$iv.containsKey(key)) {
            return value$iv;
        }
        throw new NoSuchElementException("Key " + key + " is missing in the map.");
    }

    @NotNull
    public static final <K, V> Map<K, V> withDefault(@NotNull Map<K, ? extends V> $receiver, @NotNull Function1<? super K, ? extends V> defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        if ($receiver instanceof MapWithDefault) {
            return MapsKt.withDefault(((MapWithDefault) $receiver).getMap(), defaultValue);
        }
        return new MapWithDefaultImpl<>($receiver, defaultValue);
    }

    @NotNull
    @JvmName(name = "withDefaultMutable")
    public static final <K, V> Map<K, V> withDefaultMutable(@NotNull Map<K, V> $receiver, @NotNull Function1<? super K, ? extends V> defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        if ($receiver instanceof MutableMapWithDefault) {
            return MapsKt.withDefaultMutable(((MutableMapWithDefault) $receiver).getMap(), defaultValue);
        }
        return new MutableMapWithDefaultImpl<>($receiver, defaultValue);
    }
}
