package kotlin.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Ljava/io/BufferedReader;", "invoke"}, k = 3, mv = {1, 1, 8})
/* compiled from: Console.kt */
final class ConsoleKt$stdin$2 extends Lambda implements Function0<BufferedReader> {
    public static final ConsoleKt$stdin$2 INSTANCE = new ConsoleKt$stdin$2();

    ConsoleKt$stdin$2() {
        super(0);
    }

    @NotNull
    public final BufferedReader invoke() {
        return new BufferedReader(new InputStreamReader(new InputStream() {
            public int read() {
                return System.in.read();
            }

            public void reset() {
                System.in.reset();
            }

            public int read(@NotNull byte[] b) {
                Intrinsics.checkParameterIsNotNull(b, "b");
                return System.in.read(b);
            }

            public void close() {
                System.in.close();
            }

            public void mark(int readlimit) {
                System.in.mark(readlimit);
            }

            public long skip(long n) {
                return System.in.skip(n);
            }

            public int available() {
                return System.in.available();
            }

            public boolean markSupported() {
                return System.in.markSupported();
            }

            public int read(@NotNull byte[] b, int off, int len) {
                Intrinsics.checkParameterIsNotNull(b, "b");
                return System.in.read(b, off, len);
            }
        }));
    }
}
