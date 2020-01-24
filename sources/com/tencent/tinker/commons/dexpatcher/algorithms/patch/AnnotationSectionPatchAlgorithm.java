package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.Annotation;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class AnnotationSectionPatchAlgorithm extends DexSectionPatchAlgorithm<Annotation> {
    private Section patchedAnnotationSec = null;
    private TableOfContents.Section patchedAnnotationTocSec = null;

    public AnnotationSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedAnnotationTocSec = patchedDex.getTableOfContents().annotations;
            this.patchedAnnotationSec = patchedDex.openSection(this.patchedAnnotationTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().annotations;
    }

    /* access modifiers changed from: protected */
    public Annotation nextItem(DexDataBuffer section) {
        return section.readAnnotation();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(Annotation item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public Annotation adjustItem(AbstractIndexMap indexMap, Annotation item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(Annotation patchedItem) {
        this.patchedAnnotationTocSec.size++;
        return this.patchedAnnotationSec.writeAnnotation(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapAnnotationOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markAnnotationDeleted(deletedOffset);
    }
}
