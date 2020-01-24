package kotlin.io;

import java.io.Closeable;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0001\u001a8\u0010\u0005\u001a\u0002H\u0006\"\n\b\u0000\u0010\u0007*\u0004\u0018\u00010\u0002\"\u0004\b\u0001\u0010\u0006*\u0002H\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\tH\b¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"closeFinally", "", "Ljava/io/Closeable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}, k = 2, mv = {1, 1, 8})
@JvmName(name = "CloseableKt")
/* compiled from: Closeable.kt */
public final class CloseableKt {
    @InlineOnly
    private static final <T extends Closeable, R> R use(T $receiver, Function1<? super T, ? extends R> block) {
        try {
            R invoke = block.invoke($receiver);
            InlineMarker.finallyStart(1);
            if ($receiver != null) {
                $receiver.close();
            }
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Exception e) {
            if ($receiver != null) {
                try {
                    $receiver.close();
                } catch (Exception e2) {
                }
            }
            throw e;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            if (1 == 0 && $receiver != null) {
                $receiver.close();
            }
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    @SinceKotlin(version = "1.1")
    @PublishedApi
    public static final void closeFinally(@Nullable Closeable $receiver, @Nullable Throwable cause) {
        if ($receiver != null) {
            if (cause == null) {
                $receiver.close();
                return;
            }
            try {
                $receiver.close();
            } catch (Throwable closeException) {
                ExceptionsKt.addSuppressed(cause, closeException);
            }
        }
    }
}
