package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfek;
import com.google.android.gms.internal.zzfel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class zzfel<MessageType extends zzfek<MessageType, BuilderType>, BuilderType extends zzfel<MessageType, BuilderType>> implements zzfhf {
    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzffz.checkNotNull(iterable);
        if (iterable instanceof zzfgl) {
            List zzcyl = ((zzfgl) iterable).zzcyl();
            zzfgl zzfgl = (zzfgl) list;
            int size = list.size();
            for (Object next : zzcyl) {
                if (next == null) {
                    String str = "Element at index " + (zzfgl.size() - size) + " is null.";
                    for (int size2 = zzfgl.size() - 1; size2 >= size; size2--) {
                        zzfgl.remove(size2);
                    }
                    throw new NullPointerException(str);
                } else if (!(next instanceof zzfes)) {
                    zzfgl.add((String) next);
                }
            }
        } else if (iterable instanceof zzfhl) {
            list.addAll((Collection) iterable);
        } else {
            zzb(iterable, list);
        }
    }

    private static <T> void zzb(Iterable<T> iterable, List<? super T> list) {
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(((Collection) iterable).size() + list.size());
        }
        int size = list.size();
        for (Object next : iterable) {
            if (next == null) {
                String str = "Element at index " + (list.size() - size) + " is null.";
                for (int size2 = list.size() - 1; size2 >= size; size2--) {
                    list.remove(size2);
                }
                throw new NullPointerException(str);
            }
            list.add(next);
        }
    }

    /* access modifiers changed from: protected */
    public abstract BuilderType zza(MessageType messagetype);

    /* renamed from: zza */
    public abstract BuilderType zzb(zzffb zzffb, zzffm zzffm) throws IOException;

    /* renamed from: zzcvh */
    public abstract BuilderType clone();

    public final /* synthetic */ zzfhf zzd(zzfhe zzfhe) {
        if (zzcxq().getClass().isInstance(zzfhe)) {
            return zza((zzfek) zzfhe);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
