package com.tencent.tinker.commons.dexpatcher;

import com.tencent.tinker.android.dex.Annotation;
import com.tencent.tinker.android.dex.AnnotationSet;
import com.tencent.tinker.android.dex.AnnotationSetRefList;
import com.tencent.tinker.android.dex.AnnotationsDirectory;
import com.tencent.tinker.android.dex.ClassData;
import com.tencent.tinker.android.dex.ClassDef;
import com.tencent.tinker.android.dex.Code;
import com.tencent.tinker.android.dex.DebugInfoItem;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.EncodedValue;
import com.tencent.tinker.android.dex.FieldId;
import com.tencent.tinker.android.dex.MethodId;
import com.tencent.tinker.android.dex.ProtoId;
import com.tencent.tinker.android.dex.StringData;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.TypeList;
import com.tencent.tinker.android.dex.util.CompareUtils;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.AnnotationSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.AnnotationSetRefListSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.AnnotationSetSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.AnnotationsDirectorySectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.ClassDataSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.ClassDefSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.CodeSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.DebugInfoItemSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.DexSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.FieldIdSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.MethodIdSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.ProtoIdSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.StaticValueSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.StringDataSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.TypeIdSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.algorithms.patch.TypeListSectionPatchAlgorithm;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;
import com.tencent.tinker.commons.util.StreamUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class DexPatchApplier {
    private DexSectionPatchAlgorithm<Annotation> annotationSectionPatchAlg;
    private DexSectionPatchAlgorithm<AnnotationSetRefList> annotationSetRefListSectionPatchAlg;
    private DexSectionPatchAlgorithm<AnnotationSet> annotationSetSectionPatchAlg;
    private DexSectionPatchAlgorithm<AnnotationsDirectory> annotationsDirectorySectionPatchAlg;
    private DexSectionPatchAlgorithm<ClassData> classDataSectionPatchAlg;
    private DexSectionPatchAlgorithm<ClassDef> classDefSectionPatchAlg;
    private DexSectionPatchAlgorithm<Code> codeSectionPatchAlg;
    private DexSectionPatchAlgorithm<DebugInfoItem> debugInfoSectionPatchAlg;
    private DexSectionPatchAlgorithm<EncodedValue> encodedArraySectionPatchAlg;
    private DexSectionPatchAlgorithm<FieldId> fieldIdSectionPatchAlg;
    private DexSectionPatchAlgorithm<MethodId> methodIdSectionPatchAlg;
    private final Dex oldDex;
    private final SparseIndexMap oldToPatchedIndexMap;
    private final DexPatchFile patchFile;
    private final Dex patchedDex;
    private DexSectionPatchAlgorithm<ProtoId> protoIdSectionPatchAlg;
    private DexSectionPatchAlgorithm<StringData> stringDataSectionPatchAlg;
    private DexSectionPatchAlgorithm<Integer> typeIdSectionPatchAlg;
    private DexSectionPatchAlgorithm<TypeList> typeListSectionPatchAlg;

    public DexPatchApplier(File oldDexIn, File patchFileIn) throws IOException {
        this(new Dex(oldDexIn), new DexPatchFile(patchFileIn));
    }

    public DexPatchApplier(InputStream oldDexIn, InputStream patchFileIn) throws IOException {
        this(new Dex(oldDexIn), new DexPatchFile(patchFileIn));
    }

    public DexPatchApplier(Dex oldDexIn, DexPatchFile patchFileIn) {
        this.oldDex = oldDexIn;
        this.patchFile = patchFileIn;
        this.patchedDex = new Dex(patchFileIn.getPatchedDexSize());
        this.oldToPatchedIndexMap = new SparseIndexMap();
    }

    public void executeAndSaveTo(OutputStream out) throws IOException {
        byte[] oldDexSign = this.oldDex.computeSignature(false);
        if (oldDexSign == null) {
            throw new IOException("failed to compute old dex's signature.");
        } else if (this.patchFile == null) {
            throw new IllegalArgumentException("patch file is null.");
        } else {
            byte[] oldDexSignInPatchFile = this.patchFile.getOldDexSignature();
            if (CompareUtils.uArrCompare(oldDexSign, oldDexSignInPatchFile) != 0) {
                throw new IOException(String.format("old dex signature mismatch! expected: %s, actual: %s", new Object[]{Arrays.toString(oldDexSign), Arrays.toString(oldDexSignInPatchFile)}));
            }
            TableOfContents patchedToc = this.patchedDex.getTableOfContents();
            patchedToc.header.off = 0;
            patchedToc.header.size = 1;
            patchedToc.mapList.size = 1;
            patchedToc.stringIds.off = this.patchFile.getPatchedStringIdSectionOffset();
            patchedToc.typeIds.off = this.patchFile.getPatchedTypeIdSectionOffset();
            patchedToc.typeLists.off = this.patchFile.getPatchedTypeListSectionOffset();
            patchedToc.protoIds.off = this.patchFile.getPatchedProtoIdSectionOffset();
            patchedToc.fieldIds.off = this.patchFile.getPatchedFieldIdSectionOffset();
            patchedToc.methodIds.off = this.patchFile.getPatchedMethodIdSectionOffset();
            patchedToc.classDefs.off = this.patchFile.getPatchedClassDefSectionOffset();
            patchedToc.mapList.off = this.patchFile.getPatchedMapListSectionOffset();
            patchedToc.stringDatas.off = this.patchFile.getPatchedStringDataSectionOffset();
            patchedToc.annotations.off = this.patchFile.getPatchedAnnotationSectionOffset();
            patchedToc.annotationSets.off = this.patchFile.getPatchedAnnotationSetSectionOffset();
            patchedToc.annotationSetRefLists.off = this.patchFile.getPatchedAnnotationSetRefListSectionOffset();
            patchedToc.annotationsDirectories.off = this.patchFile.getPatchedAnnotationsDirectorySectionOffset();
            patchedToc.encodedArrays.off = this.patchFile.getPatchedEncodedArraySectionOffset();
            patchedToc.debugInfos.off = this.patchFile.getPatchedDebugInfoSectionOffset();
            patchedToc.codes.off = this.patchFile.getPatchedCodeSectionOffset();
            patchedToc.classDatas.off = this.patchFile.getPatchedClassDataSectionOffset();
            patchedToc.fileSize = this.patchFile.getPatchedDexSize();
            Arrays.sort(patchedToc.sections);
            patchedToc.computeSizesFromOffsets();
            this.stringDataSectionPatchAlg = new StringDataSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.typeIdSectionPatchAlg = new TypeIdSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.protoIdSectionPatchAlg = new ProtoIdSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.fieldIdSectionPatchAlg = new FieldIdSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.methodIdSectionPatchAlg = new MethodIdSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.classDefSectionPatchAlg = new ClassDefSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.typeListSectionPatchAlg = new TypeListSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.annotationSetRefListSectionPatchAlg = new AnnotationSetRefListSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.annotationSetSectionPatchAlg = new AnnotationSetSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.classDataSectionPatchAlg = new ClassDataSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.codeSectionPatchAlg = new CodeSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.debugInfoSectionPatchAlg = new DebugInfoItemSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.annotationSectionPatchAlg = new AnnotationSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.encodedArraySectionPatchAlg = new StaticValueSectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.annotationsDirectorySectionPatchAlg = new AnnotationsDirectorySectionPatchAlgorithm(this.patchFile, this.oldDex, this.patchedDex, this.oldToPatchedIndexMap);
            this.stringDataSectionPatchAlg.execute();
            this.typeIdSectionPatchAlg.execute();
            this.typeListSectionPatchAlg.execute();
            this.protoIdSectionPatchAlg.execute();
            this.fieldIdSectionPatchAlg.execute();
            this.methodIdSectionPatchAlg.execute();
            this.annotationSectionPatchAlg.execute();
            this.annotationSetSectionPatchAlg.execute();
            this.annotationSetRefListSectionPatchAlg.execute();
            this.annotationsDirectorySectionPatchAlg.execute();
            this.debugInfoSectionPatchAlg.execute();
            this.codeSectionPatchAlg.execute();
            this.classDataSectionPatchAlg.execute();
            this.encodedArraySectionPatchAlg.execute();
            this.classDefSectionPatchAlg.execute();
            patchedToc.writeHeader(this.patchedDex.openSection(patchedToc.header.off));
            patchedToc.writeMap(this.patchedDex.openSection(patchedToc.mapList.off));
            this.patchedDex.writeHashes();
            this.patchedDex.writeTo(out);
        }
    }

    public void executeAndSaveTo(File file) throws IOException {
        OutputStream os = null;
        try {
            OutputStream os2 = new BufferedOutputStream(new FileOutputStream(file));
            try {
                executeAndSaveTo(os2);
                StreamUtil.closeQuietly(os2);
            } catch (Throwable th) {
                th = th;
                os = os2;
                StreamUtil.closeQuietly(os);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            StreamUtil.closeQuietly(os);
            throw th;
        }
    }
}
