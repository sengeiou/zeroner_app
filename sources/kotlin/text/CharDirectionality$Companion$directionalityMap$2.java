package kotlin.text;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "", "Lkotlin/text/CharDirectionality;", "invoke"}, k = 3, mv = {1, 1, 8})
/* compiled from: CharDirectionality.kt */
final class CharDirectionality$Companion$directionalityMap$2 extends Lambda implements Function0<Map<Integer, ? extends CharDirectionality>> {
    public static final CharDirectionality$Companion$directionalityMap$2 INSTANCE = new CharDirectionality$Companion$directionalityMap$2();

    CharDirectionality$Companion$directionalityMap$2() {
        super(0);
    }

    @NotNull
    public final Map<Integer, CharDirectionality> invoke() {
        Object[] $receiver$iv = (Object[]) CharDirectionality.values();
        Map destination$iv$iv = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity($receiver$iv.length), 16));
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= $receiver$iv.length) {
                return destination$iv$iv;
            }
            Object element$iv$iv = $receiver$iv[i2];
            destination$iv$iv.put(Integer.valueOf(((CharDirectionality) element$iv$iv).getValue()), element$iv$iv);
            i = i2 + 1;
        }
    }
}
