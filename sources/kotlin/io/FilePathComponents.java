package kotlin.io;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\r\b\b\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J#\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0013HÖ\u0001J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0013J\t\u0010\u001f\u001a\u00020\rHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006 "}, d2 = {"Lkotlin/io/FilePathComponents;", "", "root", "Ljava/io/File;", "segments", "", "(Ljava/io/File;Ljava/util/List;)V", "isRooted", "", "()Z", "getRoot", "()Ljava/io/File;", "rootName", "", "getRootName", "()Ljava/lang/String;", "getSegments", "()Ljava/util/List;", "size", "", "getSize", "()I", "component1", "component2", "copy", "equals", "other", "hashCode", "subPath", "beginIndex", "endIndex", "toString", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
/* compiled from: FilePathComponents.kt */
public final class FilePathComponents {
    @NotNull
    private final File root;
    @NotNull
    private final List<File> segments;

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<java.io.File>, for r3v0, types: [java.util.List] */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* bridge */ /* synthetic */ kotlin.io.FilePathComponents copy$default(kotlin.io.FilePathComponents r1, java.io.File r2, java.util.List<java.io.File> r3, int r4, java.lang.Object r5) {
        /*
            r0 = r4 & 1
            if (r0 == 0) goto L_0x0006
            java.io.File r2 = r1.root
        L_0x0006:
            r0 = r4 & 2
            if (r0 == 0) goto L_0x000c
            java.util.List<java.io.File> r3 = r1.segments
        L_0x000c:
            kotlin.io.FilePathComponents r0 = r1.copy(r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FilePathComponents.copy$default(kotlin.io.FilePathComponents, java.io.File, java.util.List, int, java.lang.Object):kotlin.io.FilePathComponents");
    }

    @NotNull
    public final File component1() {
        return this.root;
    }

    @NotNull
    public final List<File> component2() {
        return this.segments;
    }

    @NotNull
    public final FilePathComponents copy(@NotNull File root2, @NotNull List<? extends File> segments2) {
        Intrinsics.checkParameterIsNotNull(root2, "root");
        Intrinsics.checkParameterIsNotNull(segments2, "segments");
        return new FilePathComponents(root2, segments2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001a, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r2.segments, (java.lang.Object) r3.segments) != false) goto L_0x001c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r2 == r3) goto L_0x001c
            boolean r0 = r3 instanceof kotlin.io.FilePathComponents
            if (r0 == 0) goto L_0x001e
            kotlin.io.FilePathComponents r3 = (kotlin.io.FilePathComponents) r3
            java.io.File r0 = r2.root
            java.io.File r1 = r3.root
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 == 0) goto L_0x001e
            java.util.List<java.io.File> r0 = r2.segments
            java.util.List<java.io.File> r1 = r3.segments
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 == 0) goto L_0x001e
        L_0x001c:
            r0 = 1
        L_0x001d:
            return r0
        L_0x001e:
            r0 = 0
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FilePathComponents.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = 0;
        File file = this.root;
        int hashCode = (file != null ? file.hashCode() : 0) * 31;
        List<File> list = this.segments;
        if (list != null) {
            i = list.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "FilePathComponents(root=" + this.root + ", segments=" + this.segments + ")";
    }

    public FilePathComponents(@NotNull File root2, @NotNull List<? extends File> segments2) {
        Intrinsics.checkParameterIsNotNull(root2, "root");
        Intrinsics.checkParameterIsNotNull(segments2, "segments");
        this.root = root2;
        this.segments = segments2;
    }

    @NotNull
    public final File getRoot() {
        return this.root;
    }

    @NotNull
    public final List<File> getSegments() {
        return this.segments;
    }

    @NotNull
    public final String getRootName() {
        String path = this.root.getPath();
        Intrinsics.checkExpressionValueIsNotNull(path, "root.path");
        return path;
    }

    public final boolean isRooted() {
        return this.root.getPath().length() > 0;
    }

    public final int getSize() {
        return this.segments.size();
    }

    @NotNull
    public final File subPath(int beginIndex, int endIndex) {
        if (beginIndex < 0 || beginIndex > endIndex || endIndex > getSize()) {
            throw new IllegalArgumentException();
        }
        Iterable subList = this.segments.subList(beginIndex, endIndex);
        String str = File.separator;
        Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
        return new File(CollectionsKt.joinToString$default(subList, str, null, null, 0, null, null, 62, null));
    }
}
