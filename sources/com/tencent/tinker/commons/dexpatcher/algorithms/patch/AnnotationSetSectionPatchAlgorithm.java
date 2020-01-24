package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.AnnotationSet;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class AnnotationSetSectionPatchAlgorithm extends DexSectionPatchAlgorithm<AnnotationSet> {
    private Section patchedAnnotationSetSec = null;
    private TableOfContents.Section patchedAnnotationSetTocSec = null;

    public AnnotationSetSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedAnnotationSetTocSec = patchedDex.getTableOfContents().annotationSets;
            this.patchedAnnotationSetSec = patchedDex.openSection(this.patchedAnnotationSetTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().annotationSets;
    }

    /* access modifiers changed from: protected */
    public AnnotationSet nextItem(DexDataBuffer section) {
        return section.readAnnotationSet();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(AnnotationSet item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public AnnotationSet adjustItem(AbstractIndexMap indexMap, AnnotationSet item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(AnnotationSet patchedItem) {
        this.patchedAnnotationSetTocSec.size++;
        return this.patchedAnnotationSetSec.writeAnnotationSet(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapAnnotationSetOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markAnnotationSetDeleted(deletedOffset);
    }
}
