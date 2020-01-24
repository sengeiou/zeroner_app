package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.AnnotationsDirectory;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class AnnotationsDirectorySectionPatchAlgorithm extends DexSectionPatchAlgorithm<AnnotationsDirectory> {
    private Section patchedAnnotationsDirectorySec = null;
    private TableOfContents.Section patchedAnnotationsDirectoryTocSec = null;

    public AnnotationsDirectorySectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedAnnotationsDirectoryTocSec = patchedDex.getTableOfContents().annotationsDirectories;
            this.patchedAnnotationsDirectorySec = patchedDex.openSection(this.patchedAnnotationsDirectoryTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().annotationsDirectories;
    }

    /* access modifiers changed from: protected */
    public AnnotationsDirectory nextItem(DexDataBuffer section) {
        return section.readAnnotationsDirectory();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(AnnotationsDirectory item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public AnnotationsDirectory adjustItem(AbstractIndexMap indexMap, AnnotationsDirectory item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(AnnotationsDirectory patchedItem) {
        this.patchedAnnotationsDirectoryTocSec.size++;
        return this.patchedAnnotationsDirectorySec.writeAnnotationsDirectory(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapAnnotationsDirectoryOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markAnnotationsDirectoryDeleted(deletedOffset);
    }
}
