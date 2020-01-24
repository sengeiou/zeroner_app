package kotlin.io;

import com.tencent.tauth.AuthActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\b\u001a<\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\b¢\u0006\u0002\u0010+\u001a\u0012\u0010,\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010.\u001a\u00020/*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\b¨\u00060"}, d2 = {"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, k = 5, mv = {1, 1, 8}, xi = 1, xs = "kotlin/io/FilesKt")
/* compiled from: FileReadWrite.kt */
class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
    @InlineOnly
    private static final InputStreamReader reader(@NotNull File $receiver, Charset charset) {
        return new InputStreamReader(new FileInputStream($receiver), charset);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ InputStreamReader reader$default(File $receiver, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new InputStreamReader(new FileInputStream($receiver), charset);
    }

    @InlineOnly
    private static final BufferedReader bufferedReader(@NotNull File $receiver, Charset charset, int bufferSize) {
        Reader inputStreamReader = new InputStreamReader(new FileInputStream($receiver), charset);
        return inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, bufferSize);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ BufferedReader bufferedReader$default(File $receiver, Charset charset, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i & 2) != 0) {
            bufferSize = 8192;
        }
        Reader inputStreamReader = new InputStreamReader(new FileInputStream($receiver), charset);
        return inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, bufferSize);
    }

    @InlineOnly
    private static final OutputStreamWriter writer(@NotNull File $receiver, Charset charset) {
        return new OutputStreamWriter(new FileOutputStream($receiver), charset);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ OutputStreamWriter writer$default(File $receiver, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new OutputStreamWriter(new FileOutputStream($receiver), charset);
    }

    @InlineOnly
    private static final BufferedWriter bufferedWriter(@NotNull File $receiver, Charset charset, int bufferSize) {
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream($receiver), charset);
        return outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, bufferSize);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ BufferedWriter bufferedWriter$default(File $receiver, Charset charset, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i & 2) != 0) {
            bufferSize = 8192;
        }
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream($receiver), charset);
        return outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, bufferSize);
    }

    @InlineOnly
    private static final PrintWriter printWriter(@NotNull File $receiver, Charset charset) {
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream($receiver), charset);
        return new PrintWriter(outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192));
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ PrintWriter printWriter$default(File $receiver, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream($receiver), charset);
        return new PrintWriter(outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192));
    }

    @NotNull
    public static final byte[] readBytes(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Closeable fileInputStream = new FileInputStream($receiver);
        try {
            FileInputStream input = (FileInputStream) fileInputStream;
            int offset = 0;
            long it = $receiver.length();
            if (it > ((long) Integer.MAX_VALUE)) {
                throw new OutOfMemoryError("File " + $receiver + " is too big (" + it + " bytes) to fit in memory.");
            }
            int remaining = (int) it;
            byte[] result = new byte[remaining];
            while (remaining > 0) {
                int read = input.read(result, offset, remaining);
                if (read < 0) {
                    break;
                }
                remaining -= read;
                offset += read;
            }
            if (remaining != 0) {
                result = Arrays.copyOf(result, offset);
                Intrinsics.checkExpressionValueIsNotNull(result, "java.util.Arrays.copyOf(this, newSize)");
            }
            fileInputStream.close();
            return result;
        } catch (Exception e) {
            try {
                fileInputStream.close();
            } catch (Exception e2) {
            }
            throw e;
        } catch (Throwable th) {
            if (1 == 0) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    public static final void writeBytes(@NotNull File $receiver, @NotNull byte[] array) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(array, "array");
        Closeable fileOutputStream = new FileOutputStream($receiver);
        try {
            ((FileOutputStream) fileOutputStream).write(array);
            Unit unit = Unit.INSTANCE;
            fileOutputStream.close();
        } catch (Exception e) {
            try {
                fileOutputStream.close();
            } catch (Exception e2) {
            }
            throw e;
        } catch (Throwable th) {
            if (1 == 0) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void appendBytes(@org.jetbrains.annotations.NotNull java.io.File r6, @org.jetbrains.annotations.NotNull byte[] r7) {
        /*
            r5 = 1
            java.lang.String r2 = "$receiver"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r2)
            java.lang.String r2 = "array"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r2)
            java.io.FileOutputStream r2 = new java.io.FileOutputStream
            r2.<init>(r6, r5)
            java.io.Closeable r2 = (java.io.Closeable) r2
            r4 = 0
            r0 = r2
            java.io.FileOutputStream r0 = (java.io.FileOutputStream) r0     // Catch:{ Exception -> 0x0023, all -> 0x0035 }
            r1 = r0
            r1.write(r7)     // Catch:{ Exception -> 0x0023, all -> 0x0035 }
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch:{ Exception -> 0x0023, all -> 0x0035 }
            r2.close()
            return
        L_0x0023:
            r3 = move-exception
            r2.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0028:
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch:{ all -> 0x002b }
            throw r3     // Catch:{ all -> 0x002b }
        L_0x002b:
            r3 = move-exception
            r4 = r5
        L_0x002d:
            if (r4 != 0) goto L_0x0032
            r2.close()
        L_0x0032:
            throw r3
        L_0x0033:
            r4 = move-exception
            goto L_0x0028
        L_0x0035:
            r3 = move-exception
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FilesKt__FileReadWriteKt.appendBytes(java.io.File, byte[]):void");
    }

    @NotNull
    public static final String readText(@NotNull File $receiver, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        return new String(FilesKt.readBytes($receiver), charset);
    }

    @NotNull
    public static /* bridge */ /* synthetic */ String readText$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readText(file, charset);
    }

    public static final void writeText(@NotNull File $receiver, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        FilesKt.writeBytes($receiver, bytes);
    }

    public static /* bridge */ /* synthetic */ void writeText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.writeText(file, str, charset);
    }

    public static final void appendText(@NotNull File $receiver, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        FilesKt.appendBytes($receiver, bytes);
    }

    public static /* bridge */ /* synthetic */ void appendText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.appendText(file, str, charset);
    }

    public static final void forEachBlock(@NotNull File $receiver, @NotNull Function2<? super byte[], ? super Integer, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, AuthActivity.ACTION_KEY);
        FilesKt.forEachBlock($receiver, 4096, action);
    }

    public static final void forEachBlock(@NotNull File $receiver, int blockSize, @NotNull Function2<? super byte[], ? super Integer, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, AuthActivity.ACTION_KEY);
        byte[] arr = new byte[RangesKt.coerceAtLeast(blockSize, 512)];
        FileInputStream fis = new FileInputStream($receiver);
        while (true) {
            try {
                int size = fis.read(arr);
                if (size > 0) {
                    action.invoke(arr, Integer.valueOf(size));
                } else {
                    return;
                }
            } finally {
                fis.close();
            }
        }
    }

    public static /* bridge */ /* synthetic */ void forEachLine$default(File file, Charset charset, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.forEachLine(file, charset, function1);
    }

    public static final void forEachLine(@NotNull File $receiver, @NotNull Charset charset, @NotNull Function1<? super String, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        Intrinsics.checkParameterIsNotNull(action, AuthActivity.ACTION_KEY);
        TextStreamsKt.forEachLine(new BufferedReader(new InputStreamReader(new FileInputStream($receiver), charset)), action);
    }

    @InlineOnly
    private static final FileInputStream inputStream(@NotNull File $receiver) {
        return new FileInputStream($receiver);
    }

    @InlineOnly
    private static final FileOutputStream outputStream(@NotNull File $receiver) {
        return new FileOutputStream($receiver);
    }

    @NotNull
    public static /* bridge */ /* synthetic */ List readLines$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readLines(file, charset);
    }

    @NotNull
    public static final List<String> readLines(@NotNull File $receiver, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        ArrayList result = new ArrayList();
        FilesKt.forEachLine($receiver, charset, new FilesKt__FileReadWriteKt$readLines$1(result));
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0060  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* bridge */ /* synthetic */ java.lang.Object useLines$default(java.io.File r6, java.nio.charset.Charset r7, kotlin.jvm.functions.Function1 r8, int r9, java.lang.Object r10) {
        /*
            r5 = 1
            r2 = r9 & 1
            if (r2 == 0) goto L_0x0007
            java.nio.charset.Charset r7 = kotlin.text.Charsets.UTF_8
        L_0x0007:
            java.lang.String r2 = "$receiver"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r2)
            java.lang.String r2 = "charset"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r2)
            java.lang.String r2 = "block"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r8, r2)
            r4 = 8192(0x2000, float:1.14794E-41)
            java.io.FileInputStream r2 = new java.io.FileInputStream
            r2.<init>(r6)
            java.io.InputStream r2 = (java.io.InputStream) r2
            java.io.InputStreamReader r3 = new java.io.InputStreamReader
            r3.<init>(r2, r7)
            r2 = r3
            java.io.Reader r2 = (java.io.Reader) r2
            boolean r3 = r2 instanceof java.io.BufferedReader
            if (r3 == 0) goto L_0x004a
            java.io.BufferedReader r2 = (java.io.BufferedReader) r2
        L_0x0030:
            java.io.Closeable r2 = (java.io.Closeable) r2
            r4 = 0
            r0 = r2
            java.io.BufferedReader r0 = (java.io.BufferedReader) r0     // Catch:{ Exception -> 0x0051, all -> 0x0069 }
            r1 = r0
            kotlin.sequences.Sequence r3 = kotlin.io.TextStreamsKt.lineSequence(r1)     // Catch:{ Exception -> 0x0051, all -> 0x0069 }
            java.lang.Object r3 = r8.invoke(r3)     // Catch:{ Exception -> 0x0051, all -> 0x0069 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            r2.close()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            return r3
        L_0x004a:
            java.io.BufferedReader r3 = new java.io.BufferedReader
            r3.<init>(r2, r4)
            r2 = r3
            goto L_0x0030
        L_0x0051:
            r3 = move-exception
            r2.close()     // Catch:{ Exception -> 0x0067 }
        L_0x0056:
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch:{ all -> 0x0059 }
            throw r3     // Catch:{ all -> 0x0059 }
        L_0x0059:
            r3 = move-exception
            r4 = r5
        L_0x005b:
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            if (r4 != 0) goto L_0x0063
            r2.close()
        L_0x0063:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            throw r3
        L_0x0067:
            r4 = move-exception
            goto L_0x0056
        L_0x0069:
            r3 = move-exception
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FilesKt__FileReadWriteKt.useLines$default(java.io.File, java.nio.charset.Charset, kotlin.jvm.functions.Function1, int, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x005a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> T useLines(@org.jetbrains.annotations.NotNull java.io.File r6, @org.jetbrains.annotations.NotNull java.nio.charset.Charset r7, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super kotlin.sequences.Sequence<java.lang.String>, ? extends T> r8) {
        /*
            r5 = 1
            java.lang.String r2 = "$receiver"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r2)
            java.lang.String r2 = "charset"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r2)
            java.lang.String r2 = "block"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r8, r2)
            r4 = 8192(0x2000, float:1.14794E-41)
            java.io.FileInputStream r2 = new java.io.FileInputStream
            r2.<init>(r6)
            java.io.InputStream r2 = (java.io.InputStream) r2
            java.io.InputStreamReader r3 = new java.io.InputStreamReader
            r3.<init>(r2, r7)
            r2 = r3
            java.io.Reader r2 = (java.io.Reader) r2
            boolean r3 = r2 instanceof java.io.BufferedReader
            if (r3 == 0) goto L_0x0044
            java.io.BufferedReader r2 = (java.io.BufferedReader) r2
        L_0x002a:
            java.io.Closeable r2 = (java.io.Closeable) r2
            r4 = 0
            r0 = r2
            java.io.BufferedReader r0 = (java.io.BufferedReader) r0     // Catch:{ Exception -> 0x004b, all -> 0x0063 }
            r1 = r0
            kotlin.sequences.Sequence r3 = kotlin.io.TextStreamsKt.lineSequence(r1)     // Catch:{ Exception -> 0x004b, all -> 0x0063 }
            java.lang.Object r3 = r8.invoke(r3)     // Catch:{ Exception -> 0x004b, all -> 0x0063 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            r2.close()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            return r3
        L_0x0044:
            java.io.BufferedReader r3 = new java.io.BufferedReader
            r3.<init>(r2, r4)
            r2 = r3
            goto L_0x002a
        L_0x004b:
            r3 = move-exception
            r2.close()     // Catch:{ Exception -> 0x0061 }
        L_0x0050:
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch:{ all -> 0x0053 }
            throw r3     // Catch:{ all -> 0x0053 }
        L_0x0053:
            r3 = move-exception
            r4 = r5
        L_0x0055:
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            if (r4 != 0) goto L_0x005d
            r2.close()
        L_0x005d:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            throw r3
        L_0x0061:
            r4 = move-exception
            goto L_0x0050
        L_0x0063:
            r3 = move-exception
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FilesKt__FileReadWriteKt.useLines(java.io.File, java.nio.charset.Charset, kotlin.jvm.functions.Function1):java.lang.Object");
    }
}
