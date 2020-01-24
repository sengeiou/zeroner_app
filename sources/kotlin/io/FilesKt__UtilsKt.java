package kotlin.io;

import com.alibaba.android.arouter.utils.Consts;
import com.google.android.gms.fitness.FitnessActivities;
import com.tencent.tinker.loader.shareutil.SharePatchInfo;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u001a(\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a(\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002¢\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002¢\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002¢\u0006\u0002\b*\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004¨\u0006+"}, d2 = {"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"}, k = 5, mv = {1, 1, 8}, xi = 1, xs = "kotlin/io/FilesKt")
/* compiled from: Utils.kt */
class FilesKt__UtilsKt extends FilesKt__FileTreeWalkKt {
    @NotNull
    public static /* bridge */ /* synthetic */ File createTempDir$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "tmp";
        }
        return FilesKt.createTempDir(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : file);
    }

    @NotNull
    public static final File createTempDir(@NotNull String prefix, @Nullable String suffix, @Nullable File directory) {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        File dir = File.createTempFile(prefix, suffix, directory);
        dir.delete();
        if (dir.mkdir()) {
            Intrinsics.checkExpressionValueIsNotNull(dir, SharePatchInfo.OAT_DIR);
            return dir;
        }
        throw new IOException("Unable to create temporary directory " + dir + '.');
    }

    @NotNull
    public static /* bridge */ /* synthetic */ File createTempFile$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "tmp";
        }
        return FilesKt.createTempFile(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : file);
    }

    @NotNull
    public static final File createTempFile(@NotNull String prefix, @Nullable String suffix, @Nullable File directory) {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        File createTempFile = File.createTempFile(prefix, suffix, directory);
        Intrinsics.checkExpressionValueIsNotNull(createTempFile, "File.createTempFile(prefix, suffix, directory)");
        return createTempFile;
    }

    @NotNull
    public static final String getExtension(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.substringAfterLast($receiver.getName(), '.', "");
    }

    @NotNull
    public static final String getInvariantSeparatorsPath(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if (File.separatorChar != '/') {
            return StringsKt.replace$default($receiver.getPath(), File.separatorChar, '/', false, 4, (Object) null);
        }
        String path = $receiver.getPath();
        Intrinsics.checkExpressionValueIsNotNull(path, "path");
        return path;
    }

    @NotNull
    public static final String getNameWithoutExtension(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.substringBeforeLast$default($receiver.getName(), Consts.DOT, (String) null, 2, (Object) null);
    }

    @NotNull
    public static final String toRelativeString(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String relativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        if (relativeStringOrNull$FilesKt__UtilsKt != null) {
            return relativeStringOrNull$FilesKt__UtilsKt;
        }
        throw new IllegalArgumentException("this and base files have different roots: " + $receiver + " and " + base + '.');
    }

    @NotNull
    public static final File relativeTo(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        return new File(FilesKt.toRelativeString($receiver, base));
    }

    @NotNull
    public static final File relativeToOrSelf(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String p1 = toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        return p1 != null ? new File(p1) : $receiver;
    }

    @Nullable
    public static final File relativeToOrNull(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String p1 = toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        if (p1 != null) {
            return new File(p1);
        }
        return null;
    }

    private static final String toRelativeStringOrNull$FilesKt__UtilsKt(@NotNull File $receiver, File base) {
        FilePathComponents thisComponents = normalize$FilesKt__UtilsKt(FilesKt.toComponents($receiver));
        FilePathComponents baseComponents = normalize$FilesKt__UtilsKt(FilesKt.toComponents(base));
        if (!Intrinsics.areEqual((Object) thisComponents.getRoot(), (Object) baseComponents.getRoot())) {
            return null;
        }
        int baseCount = baseComponents.getSize();
        int thisCount = thisComponents.getSize();
        int i = 0;
        int maxSameCount = Math.min(thisCount, baseCount);
        while (i < maxSameCount && Intrinsics.areEqual((Object) (File) thisComponents.getSegments().get(i), (Object) (File) baseComponents.getSegments().get(i))) {
            i++;
        }
        int sameCount = i;
        StringBuilder res = new StringBuilder();
        int i2 = baseCount - 1;
        if (i2 >= sameCount) {
            while (!Intrinsics.areEqual((Object) ((File) baseComponents.getSegments().get(i2)).getName(), (Object) "..")) {
                res.append("..");
                if (i2 != sameCount) {
                    res.append(File.separatorChar);
                }
                if (i2 != sameCount) {
                    i2--;
                }
            }
            return null;
        }
        if (sameCount < thisCount) {
            if (sameCount < baseCount) {
                res.append(File.separatorChar);
            }
            Iterable drop = CollectionsKt.drop(thisComponents.getSegments(), sameCount);
            Appendable appendable = res;
            String str = File.separator;
            Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
            CollectionsKt.joinTo$default(drop, appendable, str, null, null, 0, null, null, 124, null);
        }
        return res.toString();
    }

    @NotNull
    public static /* bridge */ /* synthetic */ File copyTo$default(File file, File file2, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 8192;
        }
        return FilesKt.copyTo(file, file2, z, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0096 A[SYNTHETIC, Splitter:B:43:0x0096] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00a6  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.io.File copyTo(@org.jetbrains.annotations.NotNull java.io.File r10, @org.jetbrains.annotations.NotNull java.io.File r11, boolean r12, int r13) {
        /*
            r3 = 0
            r5 = 0
            r6 = 1
            java.lang.String r1 = "$receiver"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r10, r1)
            java.lang.String r1 = "target"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r11, r1)
            boolean r1 = r10.exists()
            if (r1 != 0) goto L_0x0023
            kotlin.io.NoSuchFileException r1 = new kotlin.io.NoSuchFileException
            java.lang.String r4 = "The source file doesn't exist."
            r5 = 2
            r2 = r10
            r6 = r3
            r1.<init>(r2, r3, r4, r5, r6)
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            throw r1
        L_0x0023:
            boolean r1 = r11.exists()
            if (r1 == 0) goto L_0x0043
            if (r12 != 0) goto L_0x0039
            r9 = r6
        L_0x002c:
            if (r9 == 0) goto L_0x0043
            kotlin.io.FileAlreadyExistsException r1 = new kotlin.io.FileAlreadyExistsException
            java.lang.String r2 = "The destination file already exists."
            r1.<init>(r10, r11, r2)
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            throw r1
        L_0x0039:
            boolean r1 = r11.delete()
            if (r1 != 0) goto L_0x0041
            r9 = r6
            goto L_0x002c
        L_0x0041:
            r9 = r5
            goto L_0x002c
        L_0x0043:
            boolean r1 = r10.isDirectory()
            if (r1 == 0) goto L_0x005a
            boolean r1 = r11.mkdirs()
            if (r1 != 0) goto L_0x0089
            kotlin.io.FileSystemException r1 = new kotlin.io.FileSystemException
            java.lang.String r2 = "Failed to create target directory."
            r1.<init>(r10, r11, r2)
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            throw r1
        L_0x005a:
            java.io.File r1 = r11.getParentFile()
            if (r1 == 0) goto L_0x0063
            r1.mkdirs()
        L_0x0063:
            java.io.FileInputStream r1 = new java.io.FileInputStream
            r1.<init>(r10)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r0 = r1
            java.io.FileInputStream r0 = (java.io.FileInputStream) r0     // Catch:{ Exception -> 0x009a, all -> 0x00ae }
            r7 = r0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x009a, all -> 0x00ae }
            r2.<init>(r11)     // Catch:{ Exception -> 0x009a, all -> 0x00ae }
            java.io.Closeable r2 = (java.io.Closeable) r2     // Catch:{ Exception -> 0x009a, all -> 0x00ae }
            r0 = r2
            java.io.FileOutputStream r0 = (java.io.FileOutputStream) r0     // Catch:{ Exception -> 0x008a, all -> 0x00b0 }
            r8 = r0
            java.io.InputStream r7 = (java.io.InputStream) r7     // Catch:{ Exception -> 0x008a, all -> 0x00b0 }
            java.io.OutputStream r8 = (java.io.OutputStream) r8     // Catch:{ Exception -> 0x008a, all -> 0x00b0 }
            kotlin.io.ByteStreamsKt.copyTo(r7, r8, r13)     // Catch:{ Exception -> 0x008a, all -> 0x00b0 }
            r2.close()     // Catch:{ Exception -> 0x009a, all -> 0x00ae }
            r1.close()
        L_0x0089:
            return r11
        L_0x008a:
            r3 = move-exception
            r2.close()     // Catch:{ Exception -> 0x00aa }
        L_0x008f:
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch:{ all -> 0x0092 }
            throw r3     // Catch:{ all -> 0x0092 }
        L_0x0092:
            r3 = move-exception
            r4 = r6
        L_0x0094:
            if (r4 != 0) goto L_0x0099
            r2.close()     // Catch:{ Exception -> 0x009a, all -> 0x00ae }
        L_0x0099:
            throw r3     // Catch:{ Exception -> 0x009a, all -> 0x00ae }
        L_0x009a:
            r2 = move-exception
            r1.close()     // Catch:{ Exception -> 0x00ac }
        L_0x009f:
            java.lang.Throwable r2 = (java.lang.Throwable) r2     // Catch:{ all -> 0x00a2 }
            throw r2     // Catch:{ all -> 0x00a2 }
        L_0x00a2:
            r2 = move-exception
            r5 = r6
        L_0x00a4:
            if (r5 != 0) goto L_0x00a9
            r1.close()
        L_0x00a9:
            throw r2
        L_0x00aa:
            r4 = move-exception
            goto L_0x008f
        L_0x00ac:
            r3 = move-exception
            goto L_0x009f
        L_0x00ae:
            r2 = move-exception
            goto L_0x00a4
        L_0x00b0:
            r3 = move-exception
            r4 = r5
            goto L_0x0094
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FilesKt__UtilsKt.copyTo(java.io.File, java.io.File, boolean, int):java.io.File");
    }

    public static /* bridge */ /* synthetic */ boolean copyRecursively$default(File file, File file2, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return FilesKt.copyRecursively(file, file2, z, (i & 4) != 0 ? FilesKt__UtilsKt$copyRecursively$1.INSTANCE : function2);
    }

    public static final boolean copyRecursively(@NotNull File $receiver, @NotNull File target, boolean overwrite, @NotNull Function2<? super File, ? super IOException, ? extends OnErrorAction> onError) {
        boolean stillExists;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(target, "target");
        Intrinsics.checkParameterIsNotNull(onError, "onError");
        if (!$receiver.exists()) {
            return !Intrinsics.areEqual((Object) (OnErrorAction) onError.invoke($receiver, new NoSuchFileException($receiver, null, "The source file doesn't exist.", 2, null)), (Object) OnErrorAction.TERMINATE);
        }
        try {
            Iterator it = FilesKt.walkTopDown($receiver).onFail(new FilesKt__UtilsKt$copyRecursively$2(onError)).iterator();
            while (it.hasNext()) {
                File src = (File) it.next();
                if (!src.exists()) {
                    if (Intrinsics.areEqual((Object) (OnErrorAction) onError.invoke(src, new NoSuchFileException(src, null, "The source file doesn't exist.", 2, null)), (Object) OnErrorAction.TERMINATE)) {
                        return false;
                    }
                } else {
                    File dstFile = new File(target, FilesKt.toRelativeString(src, $receiver));
                    if (dstFile.exists() && (!src.isDirectory() || !dstFile.isDirectory())) {
                        if (!overwrite) {
                            stillExists = true;
                        } else if (dstFile.isDirectory()) {
                            stillExists = !FilesKt.deleteRecursively(dstFile);
                        } else {
                            stillExists = !dstFile.delete();
                        }
                        if (stillExists) {
                            if (Intrinsics.areEqual((Object) (OnErrorAction) onError.invoke(dstFile, new FileAlreadyExistsException(src, dstFile, "The destination file already exists.")), (Object) OnErrorAction.TERMINATE)) {
                                return false;
                            }
                        }
                    }
                    if (src.isDirectory()) {
                        dstFile.mkdirs();
                    } else if (FilesKt.copyTo$default(src, dstFile, overwrite, 0, 4, null).length() != src.length()) {
                        if (Intrinsics.areEqual((Object) (OnErrorAction) onError.invoke(src, new IOException("Source file wasn't copied completely, length of destination file differs.")), (Object) OnErrorAction.TERMINATE)) {
                            return false;
                        }
                    } else {
                        continue;
                    }
                }
            }
            return true;
        } catch (TerminateException e) {
            return false;
        }
    }

    public static final boolean deleteRecursively(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean accumulator$iv = true;
        for (File file : FilesKt.walkBottomUp($receiver)) {
            accumulator$iv = (file.delete() || !file.exists()) && accumulator$iv;
        }
        return accumulator$iv;
    }

    public static final boolean startsWith(@NotNull File $receiver, @NotNull File other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FitnessActivities.OTHER);
        FilePathComponents components = FilesKt.toComponents($receiver);
        FilePathComponents otherComponents = FilesKt.toComponents(other);
        if (!(!Intrinsics.areEqual((Object) components.getRoot(), (Object) otherComponents.getRoot())) && components.getSize() >= otherComponents.getSize()) {
            return components.getSegments().subList(0, otherComponents.getSize()).equals(otherComponents.getSegments());
        }
        return false;
    }

    public static final boolean startsWith(@NotNull File $receiver, @NotNull String other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FitnessActivities.OTHER);
        return FilesKt.startsWith($receiver, new File(other));
    }

    public static final boolean endsWith(@NotNull File $receiver, @NotNull File other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FitnessActivities.OTHER);
        FilePathComponents components = FilesKt.toComponents($receiver);
        FilePathComponents otherComponents = FilesKt.toComponents(other);
        if (otherComponents.isRooted()) {
            return Intrinsics.areEqual((Object) $receiver, (Object) other);
        }
        int shift = components.getSize() - otherComponents.getSize();
        if (shift < 0) {
            return false;
        }
        return components.getSegments().subList(shift, components.getSize()).equals(otherComponents.getSegments());
    }

    public static final boolean endsWith(@NotNull File $receiver, @NotNull String other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FitnessActivities.OTHER);
        return FilesKt.endsWith($receiver, new File(other));
    }

    @NotNull
    public static final File normalize(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        FilePathComponents $receiver2 = FilesKt.toComponents($receiver);
        File root = $receiver2.getRoot();
        Iterable normalize$FilesKt__UtilsKt = normalize$FilesKt__UtilsKt($receiver2.getSegments());
        String str = File.separator;
        Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
        return FilesKt.resolve(root, CollectionsKt.joinToString$default(normalize$FilesKt__UtilsKt, str, null, null, 0, null, null, 62, null));
    }

    private static final FilePathComponents normalize$FilesKt__UtilsKt(@NotNull FilePathComponents $receiver) {
        return new FilePathComponents($receiver.getRoot(), normalize$FilesKt__UtilsKt($receiver.getSegments()));
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.util.List<java.io.File> normalize$FilesKt__UtilsKt(@org.jetbrains.annotations.NotNull java.util.List<? extends java.io.File> r5) {
        /*
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r5.size()
            r1.<init>(r2)
            java.util.List r1 = (java.util.List) r1
            java.util.Iterator r3 = r5.iterator()
        L_0x000f:
            boolean r2 = r3.hasNext()
            if (r2 == 0) goto L_0x0069
            java.lang.Object r0 = r3.next()
            java.io.File r0 = (java.io.File) r0
            java.lang.String r2 = r0.getName()
            if (r2 != 0) goto L_0x0025
        L_0x0021:
            r1.add(r0)
            goto L_0x000f
        L_0x0025:
            int r4 = r2.hashCode()
            switch(r4) {
                case 46: goto L_0x002d;
                case 1472: goto L_0x0037;
                default: goto L_0x002c;
            }
        L_0x002c:
            goto L_0x0021
        L_0x002d:
            java.lang.String r4 = "."
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0021
            goto L_0x000f
        L_0x0037:
            java.lang.String r4 = ".."
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0021
            boolean r2 = r1.isEmpty()
            if (r2 != 0) goto L_0x0065
            java.lang.Object r2 = kotlin.collections.CollectionsKt.last(r1)
            java.io.File r2 = (java.io.File) r2
            java.lang.String r2 = r2.getName()
            java.lang.String r4 = ".."
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r4)
            r2 = r2 ^ 1
            if (r2 == 0) goto L_0x0065
            int r2 = r1.size()
            int r2 = r2 + -1
            r1.remove(r2)
            goto L_0x000f
        L_0x0065:
            r1.add(r0)
            goto L_0x000f
        L_0x0069:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FilesKt__UtilsKt.normalize$FilesKt__UtilsKt(java.util.List):java.util.List");
    }

    @NotNull
    public static final File resolve(@NotNull File $receiver, @NotNull File relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        if (FilesKt.isRooted(relative)) {
            return relative;
        }
        String baseName = $receiver.toString();
        return ((baseName.length() == 0) || StringsKt.endsWith$default((CharSequence) baseName, File.separatorChar, false, 2, (Object) null)) ? new File(baseName + relative) : new File(baseName + File.separatorChar + relative);
    }

    @NotNull
    public static final File resolve(@NotNull File $receiver, @NotNull String relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        return FilesKt.resolve($receiver, new File(relative));
    }

    @NotNull
    public static final File resolveSibling(@NotNull File $receiver, @NotNull File relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        FilePathComponents components = FilesKt.toComponents($receiver);
        return FilesKt.resolve(FilesKt.resolve(components.getRoot(), components.getSize() == 0 ? new File("..") : components.subPath(0, components.getSize() - 1)), relative);
    }

    @NotNull
    public static final File resolveSibling(@NotNull File $receiver, @NotNull String relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        return FilesKt.resolveSibling($receiver, new File(relative));
    }
}
