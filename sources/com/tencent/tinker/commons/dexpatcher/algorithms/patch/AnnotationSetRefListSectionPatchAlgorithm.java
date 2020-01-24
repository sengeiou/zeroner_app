package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.AnnotationSetRefList;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class AnnotationSetRefListSectionPatchAlgorithm extends DexSectionPatchAlgorithm<AnnotationSetRefList> {
    private Section patchedAnnotationSetRefListSec = null;
    private TableOfContents.Section patchedAnnotationSetRefListTocSec = null;

    public AnnotationSetRefListSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedAnnotationSetRefListTocSec = patchedDex.getTableOfContents().annotationSetRefLists;
            this.patchedAnnotationSetRefListSec = patchedDex.openSection(this.patchedAnnotationSetRefListTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().annotationSetRefLists;
    }

    /* access modifiers changed from: protected */
    public AnnotationSetRefList nextItem(DexDataBuffer section) {
        return section.readAnnotationSetRefList();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(AnnotationSetRefList item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public AnnotationSetRefList adjustItem(AbstractIndexMap indexMap, AnnotationSetRefList item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(AnnotationSetRefList patchedItem) {
        this.patchedAnnotationSetRefListTocSec.size++;
        return this.patchedAnnotationSetRefListSec.writeAnnotationSetRefList(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapAnnotationSetRefListOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markAnnotationSetRefListDeleted(deletedOffset);
    }
}
