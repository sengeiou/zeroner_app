package kotlin.io;

import com.tencent.tauth.AuthActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\b\u001a2\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\b¢\u0006\u0002\u0010\u001e¨\u0006\u001f"}, d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}, k = 2, mv = {1, 1, 8})
@JvmName(name = "TextStreamsKt")
/* compiled from: ReadWrite.kt */
public final class TextStreamsKt {
    @InlineOnly
    static /* bridge */ /* synthetic */ BufferedReader buffered$default(Reader $receiver, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            bufferSize = 8192;
        }
        return $receiver instanceof BufferedReader ? (BufferedReader) $receiver : new BufferedReader($receiver, bufferSize);
    }

    @InlineOnly
    private static final BufferedReader buffered(@NotNull Reader $receiver, int bufferSize) {
        return $receiver instanceof BufferedReader ? (BufferedReader) $receiver : new BufferedReader($receiver, bufferSize);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ BufferedWriter buffered$default(Writer $receiver, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            bufferSize = 8192;
        }
        return $receiver instanceof BufferedWriter ? (BufferedWriter) $receiver : new BufferedWriter($receiver, bufferSize);
    }

    @InlineOnly
    private static final BufferedWriter buffered(@NotNull Writer $receiver, int bufferSize) {
        return $receiver instanceof BufferedWriter ? (BufferedWriter) $receiver : new BufferedWriter($receiver, bufferSize);
    }

    public static final void forEachLine(@NotNull Reader $receiver, @NotNull Function1<? super String, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, AuthActivity.ACTION_KEY);
        Reader $receiver$iv = $receiver;
        Closeable bufferedReader = $receiver$iv instanceof BufferedReader ? (BufferedReader) $receiver$iv : new BufferedReader($receiver$iv, 8192);
        try {
            Function1 action$iv = action;
            for (Object element$iv : lineSequence((BufferedReader) bufferedReader)) {
                action$iv.invoke(element$iv);
            }
            Unit unit = Unit.INSTANCE;
            bufferedReader.close();
        } catch (Exception e) {
            try {
                bufferedReader.close();
            } catch (Exception e2) {
            }
            throw e;
        } catch (Throwable th) {
            if (1 == 0) {
                bufferedReader.close();
            }
            throw th;
        }
    }

    @NotNull
    public static final List<String> readLines(@NotNull Reader $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        ArrayList result = new ArrayList();
        forEachLine($receiver, new TextStreamsKt$readLines$1(result));
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0045  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> T useLines(@org.jetbrains.annotations.NotNull java.io.Reader r6, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super kotlin.sequences.Sequence<java.lang.String>, ? extends T> r7) {
        /*
            r5 = 1
            java.lang.String r2 = "$receiver"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r2)
            java.lang.String r2 = "block"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r2)
            r3 = 8192(0x2000, float:1.14794E-41)
            boolean r2 = r6 instanceof java.io.BufferedReader
            if (r2 == 0) goto L_0x0030
            java.io.BufferedReader r6 = (java.io.BufferedReader) r6
            r2 = r6
        L_0x0016:
            java.io.Closeable r2 = (java.io.Closeable) r2
            r4 = 0
            r0 = r2
            java.io.BufferedReader r0 = (java.io.BufferedReader) r0     // Catch:{ Exception -> 0x0036, all -> 0x004e }
            r1 = r0
            kotlin.sequences.Sequence r3 = lineSequence(r1)     // Catch:{ Exception -> 0x0036, all -> 0x004e }
            java.lang.Object r3 = r7.invoke(r3)     // Catch:{ Exception -> 0x0036, all -> 0x004e }
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            r2.close()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            return r3
        L_0x0030:
            java.io.BufferedReader r2 = new java.io.BufferedReader
            r2.<init>(r6, r3)
            goto L_0x0016
        L_0x0036:
            r3 = move-exception
            r2.close()     // Catch:{ Exception -> 0x004c }
        L_0x003b:
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch:{ all -> 0x003e }
            throw r3     // Catch:{ all -> 0x003e }
        L_0x003e:
            r3 = move-exception
            r4 = r5
        L_0x0040:
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            if (r4 != 0) goto L_0x0048
            r2.close()
        L_0x0048:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            throw r3
        L_0x004c:
            r4 = move-exception
            goto L_0x003b
        L_0x004e:
            r3 = move-exception
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.TextStreamsKt.useLines(java.io.Reader, kotlin.jvm.functions.Function1):java.lang.Object");
    }

    @InlineOnly
    private static final StringReader reader(@NotNull String $receiver) {
        return new StringReader($receiver);
    }

    @NotNull
    public static final Sequence<String> lineSequence(@NotNull BufferedReader $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return SequencesKt.constrainOnce(new LinesSequence($receiver));
    }

    @NotNull
    public static final String readText(@NotNull Reader $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        StringWriter buffer = new StringWriter();
        copyTo$default($receiver, buffer, 0, 2, null);
        String stringWriter = buffer.toString();
        Intrinsics.checkExpressionValueIsNotNull(stringWriter, "buffer.toString()");
        return stringWriter;
    }

    public static /* bridge */ /* synthetic */ long copyTo$default(Reader reader, Writer writer, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        return copyTo(reader, writer, i);
    }

    public static final long copyTo(@NotNull Reader $receiver, @NotNull Writer out, int bufferSize) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(out, "out");
        long charsCopied = 0;
        char[] buffer = new char[bufferSize];
        int chars = $receiver.read(buffer);
        while (chars >= 0) {
            out.write(buffer, 0, chars);
            charsCopied += (long) chars;
            chars = $receiver.read(buffer);
        }
        return charsCopied;
    }

    @InlineOnly
    private static final String readText(@NotNull URL $receiver, Charset charset) {
        return new String(readBytes($receiver), charset);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ String readText$default(URL $receiver, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new String(readBytes($receiver), charset);
    }

    @NotNull
    public static final byte[] readBytes(@NotNull URL $receiver) {
        boolean z = false;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Closeable openStream = $receiver.openStream();
        try {
            byte[] readBytes$default = ByteStreamsKt.readBytes$default((InputStream) openStream, 0, 1, null);
            if (openStream != null) {
                openStream.close();
            }
            return readBytes$default;
        } catch (Exception e) {
            if (openStream != null) {
                try {
                    openStream.close();
                } catch (Exception e2) {
                }
            }
            throw e;
        } catch (Throwable th) {
            th = th;
            z = true;
            openStream.close();
            throw th;
        }
    }
}
