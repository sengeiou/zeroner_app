package kotlin.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\u001a\u001b\u001cB\u0019\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0001\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\b\u00128\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0002J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0014J\u001a\u0010\u0007\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t0\bJ \u0010\f\u001a\u00020\u00002\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\rJ\u001a\u0010\n\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b0\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0004¢\u0006\u0002\n\u0000R@\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rX\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lkotlin/io/FileTreeWalk;", "Lkotlin/sequences/Sequence;", "Ljava/io/File;", "start", "direction", "Lkotlin/io/FileWalkDirection;", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;)V", "onEnter", "Lkotlin/Function1;", "", "onLeave", "", "onFail", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "f", "Ljava/io/IOException;", "e", "maxDepth", "", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;I)V", "iterator", "", "depth", "function", "DirectoryState", "FileTreeWalkIterator", "WalkState", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
/* compiled from: FileTreeWalk.kt */
public final class FileTreeWalk implements Sequence<File> {
    /* access modifiers changed from: private */
    public final FileWalkDirection direction;
    /* access modifiers changed from: private */
    public final int maxDepth;
    /* access modifiers changed from: private */
    public final Function1<File, Boolean> onEnter;
    /* access modifiers changed from: private */
    public final Function2<File, IOException, Unit> onFail;
    /* access modifiers changed from: private */
    public final Function1<File, Unit> onLeave;
    /* access modifiers changed from: private */
    public final File start;

    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lkotlin/io/FileTreeWalk$DirectoryState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootDir", "Ljava/io/File;", "(Ljava/io/File;)V", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
    /* compiled from: FileTreeWalk.kt */
    private static abstract class DirectoryState extends WalkState {
        public DirectoryState(@NotNull File rootDir) {
            Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
            super(rootDir);
            if (_Assertions.ENABLED) {
                boolean isDirectory = rootDir.isDirectory();
                if (_Assertions.ENABLED && !isDirectory) {
                    throw new AssertionError("rootDir must be verified to be directory beforehand.");
                }
            }
        }
    }

    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\r\u000e\u000fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0002J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0010R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;", "Lkotlin/collections/AbstractIterator;", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk;)V", "state", "Ljava/util/Stack;", "Lkotlin/io/FileTreeWalk$WalkState;", "computeNext", "", "directoryState", "Lkotlin/io/FileTreeWalk$DirectoryState;", "root", "gotoNext", "BottomUpDirectoryState", "SingleFileState", "TopDownDirectoryState", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
    /* compiled from: FileTreeWalk.kt */
    private final class FileTreeWalkIterator extends AbstractIterator<File> {
        private final Stack<WalkState> state = new Stack<>();

        @Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\nX\u000e¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$BottomUpDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "failed", "", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "step", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
        /* compiled from: FileTreeWalk.kt */
        private final class BottomUpDirectoryState extends DirectoryState {
            private boolean failed;
            private int fileIndex;
            private File[] fileList;
            private boolean rootVisited;
            final /* synthetic */ FileTreeWalkIterator this$0;

            public BottomUpDirectoryState(@NotNull FileTreeWalkIterator $outer, File rootDir) {
                Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
                this.this$0 = $outer;
                super(rootDir);
            }

            @Nullable
            public File step() {
                if (!this.failed && this.fileList == null) {
                    Function1 access$getOnEnter$p = FileTreeWalk.this.onEnter;
                    if (access$getOnEnter$p != null && !((Boolean) access$getOnEnter$p.invoke(getRoot())).booleanValue()) {
                        return null;
                    }
                    this.fileList = getRoot().listFiles();
                    if (this.fileList == null) {
                        Function2 access$getOnFail$p = FileTreeWalk.this.onFail;
                        if (access$getOnFail$p != null) {
                            Unit unit = (Unit) access$getOnFail$p.invoke(getRoot(), new AccessDeniedException(getRoot(), null, "Cannot list files in a directory", 2, null));
                        }
                        this.failed = true;
                    }
                }
                if (this.fileList != null) {
                    int i = this.fileIndex;
                    File[] fileArr = this.fileList;
                    if (fileArr == null) {
                        Intrinsics.throwNpe();
                    }
                    if (i < ((Object[]) fileArr).length) {
                        File[] fileArr2 = this.fileList;
                        if (fileArr2 == null) {
                            Intrinsics.throwNpe();
                        }
                        int i2 = this.fileIndex;
                        this.fileIndex = i2 + 1;
                        return fileArr2[i2];
                    }
                }
                if (!this.rootVisited) {
                    this.rootVisited = true;
                    return getRoot();
                }
                Function1 access$getOnLeave$p = FileTreeWalk.this.onLeave;
                if (access$getOnLeave$p == null) {
                    return null;
                }
                Unit unit2 = (Unit) access$getOnLeave$p.invoke(getRoot());
                return null;
            }
        }

        @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$SingleFileState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootFile", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "visited", "", "step", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
        /* compiled from: FileTreeWalk.kt */
        private final class SingleFileState extends WalkState {
            final /* synthetic */ FileTreeWalkIterator this$0;
            private boolean visited;

            public SingleFileState(@NotNull FileTreeWalkIterator $outer, File rootFile) {
                Intrinsics.checkParameterIsNotNull(rootFile, "rootFile");
                this.this$0 = $outer;
                super(rootFile);
                if (_Assertions.ENABLED) {
                    boolean isFile = rootFile.isFile();
                    if (_Assertions.ENABLED && !isFile) {
                        throw new AssertionError("rootFile must be verified to be file beforehand.");
                    }
                }
            }

            @Nullable
            public File step() {
                if (this.visited) {
                    return null;
                }
                this.visited = true;
                return getRoot();
            }
        }

        @Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bX\u000e¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "", "step", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
        /* compiled from: FileTreeWalk.kt */
        private final class TopDownDirectoryState extends DirectoryState {
            private int fileIndex;
            private File[] fileList;
            private boolean rootVisited;
            final /* synthetic */ FileTreeWalkIterator this$0;

            public TopDownDirectoryState(@NotNull FileTreeWalkIterator $outer, File rootDir) {
                Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
                this.this$0 = $outer;
                super(rootDir);
            }

            /* JADX WARNING: Code restructure failed: missing block: B:27:0x007c, code lost:
                if (((java.lang.Object[]) r0).length == 0) goto L_0x007e;
             */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.io.File step() {
                /*
                    r8 = this;
                    r2 = 0
                    boolean r0 = r8.rootVisited
                    if (r0 != 0) goto L_0x0028
                    kotlin.io.FileTreeWalk$FileTreeWalkIterator r0 = r8.this$0
                    kotlin.io.FileTreeWalk r0 = kotlin.io.FileTreeWalk.this
                    kotlin.jvm.functions.Function1 r0 = r0.onEnter
                    if (r0 == 0) goto L_0x0020
                    java.io.File r1 = r8.getRoot()
                    java.lang.Object r0 = r0.invoke(r1)
                    java.lang.Boolean r0 = (java.lang.Boolean) r0
                    boolean r0 = r0.booleanValue()
                    if (r0 != 0) goto L_0x0020
                L_0x001f:
                    return r2
                L_0x0020:
                    r0 = 1
                    r8.rootVisited = r0
                    java.io.File r2 = r8.getRoot()
                    goto L_0x001f
                L_0x0028:
                    java.io.File[] r0 = r8.fileList
                    if (r0 == 0) goto L_0x003a
                    int r1 = r8.fileIndex
                    java.io.File[] r0 = r8.fileList
                    if (r0 != 0) goto L_0x0035
                    kotlin.jvm.internal.Intrinsics.throwNpe()
                L_0x0035:
                    java.lang.Object[] r0 = (java.lang.Object[]) r0
                    int r0 = r0.length
                    if (r1 >= r0) goto L_0x00a4
                L_0x003a:
                    java.io.File[] r0 = r8.fileList
                    if (r0 != 0) goto L_0x0093
                    java.io.File r0 = r8.getRoot()
                    java.io.File[] r0 = r0.listFiles()
                    r8.fileList = r0
                    java.io.File[] r0 = r8.fileList
                    if (r0 != 0) goto L_0x006e
                    kotlin.io.FileTreeWalk$FileTreeWalkIterator r0 = r8.this$0
                    kotlin.io.FileTreeWalk r0 = kotlin.io.FileTreeWalk.this
                    kotlin.jvm.functions.Function2 r6 = r0.onFail
                    if (r6 == 0) goto L_0x006e
                    java.io.File r7 = r8.getRoot()
                    kotlin.io.AccessDeniedException r0 = new kotlin.io.AccessDeniedException
                    java.io.File r1 = r8.getRoot()
                    java.lang.String r3 = "Cannot list files in a directory"
                    r4 = 2
                    r5 = r2
                    r0.<init>(r1, r2, r3, r4, r5)
                    java.lang.Object r0 = r6.invoke(r7, r0)
                    kotlin.Unit r0 = (kotlin.Unit) r0
                L_0x006e:
                    java.io.File[] r0 = r8.fileList
                    if (r0 == 0) goto L_0x007e
                    java.io.File[] r0 = r8.fileList
                    if (r0 != 0) goto L_0x0079
                    kotlin.jvm.internal.Intrinsics.throwNpe()
                L_0x0079:
                    java.lang.Object[] r0 = (java.lang.Object[]) r0
                    int r0 = r0.length
                    if (r0 != 0) goto L_0x0093
                L_0x007e:
                    kotlin.io.FileTreeWalk$FileTreeWalkIterator r0 = r8.this$0
                    kotlin.io.FileTreeWalk r0 = kotlin.io.FileTreeWalk.this
                    kotlin.jvm.functions.Function1 r0 = r0.onLeave
                    if (r0 == 0) goto L_0x001f
                    java.io.File r1 = r8.getRoot()
                    java.lang.Object r0 = r0.invoke(r1)
                    kotlin.Unit r0 = (kotlin.Unit) r0
                    goto L_0x001f
                L_0x0093:
                    java.io.File[] r0 = r8.fileList
                    if (r0 != 0) goto L_0x009a
                    kotlin.jvm.internal.Intrinsics.throwNpe()
                L_0x009a:
                    int r1 = r8.fileIndex
                    int r2 = r1 + 1
                    r8.fileIndex = r2
                    r2 = r0[r1]
                    goto L_0x001f
                L_0x00a4:
                    kotlin.io.FileTreeWalk$FileTreeWalkIterator r0 = r8.this$0
                    kotlin.io.FileTreeWalk r0 = kotlin.io.FileTreeWalk.this
                    kotlin.jvm.functions.Function1 r0 = r0.onLeave
                    if (r0 == 0) goto L_0x001f
                    java.io.File r1 = r8.getRoot()
                    java.lang.Object r0 = r0.invoke(r1)
                    kotlin.Unit r0 = (kotlin.Unit) r0
                    goto L_0x001f
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FileTreeWalk.FileTreeWalkIterator.TopDownDirectoryState.step():java.io.File");
            }
        }

        public FileTreeWalkIterator() {
            if (FileTreeWalk.this.start.isDirectory()) {
                this.state.push(directoryState(FileTreeWalk.this.start));
            } else if (FileTreeWalk.this.start.isFile()) {
                this.state.push(new SingleFileState(this, FileTreeWalk.this.start));
            } else {
                done();
            }
        }

        /* access modifiers changed from: protected */
        public void computeNext() {
            File nextFile = gotoNext();
            if (nextFile != null) {
                setNext(nextFile);
            } else {
                done();
            }
        }

        private final DirectoryState directoryState(File root) {
            switch (FileTreeWalk.this.direction) {
                case TOP_DOWN:
                    return new TopDownDirectoryState(this, root);
                case BOTTOM_UP:
                    return new BottomUpDirectoryState(this, root);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        private final File gotoNext() {
            while (!this.state.empty()) {
                Object peek = this.state.peek();
                if (peek == null) {
                    Intrinsics.throwNpe();
                }
                WalkState topState = (WalkState) peek;
                File file = topState.step();
                if (file == null) {
                    this.state.pop();
                } else if (Intrinsics.areEqual((Object) file, (Object) topState.getRoot()) || !file.isDirectory() || this.state.size() >= FileTreeWalk.this.maxDepth) {
                    return file;
                } else {
                    this.state.push(directoryState(file));
                }
            }
            return null;
        }
    }

    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lkotlin/io/FileTreeWalk$WalkState;", "", "root", "Ljava/io/File;", "(Ljava/io/File;)V", "getRoot", "()Ljava/io/File;", "step", "kotlin-stdlib"}, k = 1, mv = {1, 1, 8})
    /* compiled from: FileTreeWalk.kt */
    private static abstract class WalkState {
        @NotNull
        private final File root;

        @Nullable
        public abstract File step();

        public WalkState(@NotNull File root2) {
            Intrinsics.checkParameterIsNotNull(root2, "root");
            this.root = root2;
        }

        @NotNull
        public final File getRoot() {
            return this.root;
        }
    }

    private FileTreeWalk(File start2, FileWalkDirection direction2, Function1<? super File, Boolean> onEnter2, Function1<? super File, Unit> onLeave2, Function2<? super File, ? super IOException, Unit> onFail2, int maxDepth2) {
        this.start = start2;
        this.direction = direction2;
        this.onEnter = onEnter2;
        this.onLeave = onLeave2;
        this.onFail = onFail2;
        this.maxDepth = maxDepth2;
    }

    /* synthetic */ FileTreeWalk(File file, FileWalkDirection fileWalkDirection, Function1 function1, Function1 function12, Function2 function2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        FileWalkDirection fileWalkDirection2;
        if ((i2 & 2) != 0) {
            fileWalkDirection2 = FileWalkDirection.TOP_DOWN;
        } else {
            fileWalkDirection2 = fileWalkDirection;
        }
        this(file, fileWalkDirection2, function1, function12, function2, (i2 & 32) != 0 ? Integer.MAX_VALUE : i);
    }

    public FileTreeWalk(@NotNull File start2, @NotNull FileWalkDirection direction2) {
        Intrinsics.checkParameterIsNotNull(start2, "start");
        Intrinsics.checkParameterIsNotNull(direction2, "direction");
        this(start2, direction2, null, null, null, 0, 32, null);
    }

    public /* synthetic */ FileTreeWalk(File file, FileWalkDirection fileWalkDirection, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            fileWalkDirection = FileWalkDirection.TOP_DOWN;
        }
        this(file, fileWalkDirection);
    }

    @NotNull
    public Iterator<File> iterator() {
        return new FileTreeWalkIterator<>();
    }

    @NotNull
    public final FileTreeWalk onEnter(@NotNull Function1<? super File, Boolean> function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        return new FileTreeWalk(this.start, this.direction, function, this.onLeave, this.onFail, this.maxDepth);
    }

    @NotNull
    public final FileTreeWalk onLeave(@NotNull Function1<? super File, Unit> function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        return new FileTreeWalk(this.start, this.direction, this.onEnter, function, this.onFail, this.maxDepth);
    }

    @NotNull
    public final FileTreeWalk onFail(@NotNull Function2<? super File, ? super IOException, Unit> function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, function, this.maxDepth);
    }

    @NotNull
    public final FileTreeWalk maxDepth(int depth) {
        if (depth > 0) {
            return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, this.onFail, depth);
        }
        throw new IllegalArgumentException("depth must be positive, but was " + depth + '.');
    }
}
