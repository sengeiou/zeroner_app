package com.tencent.tinker.commons.dexpatcher.algorithms.patch;

import com.tencent.tinker.android.dex.ClassData;
import com.tencent.tinker.android.dex.Dex;
import com.tencent.tinker.android.dex.Dex.Section;
import com.tencent.tinker.android.dex.TableOfContents;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.commons.dexpatcher.struct.DexPatchFile;
import com.tencent.tinker.commons.dexpatcher.util.AbstractIndexMap;
import com.tencent.tinker.commons.dexpatcher.util.SparseIndexMap;

public class ClassDataSectionPatchAlgorithm extends DexSectionPatchAlgorithm<ClassData> {
    private Section patchedClassDataSec = null;
    private TableOfContents.Section patchedClassDataTocSec = null;

    public ClassDataSectionPatchAlgorithm(DexPatchFile patchFile, Dex oldDex, Dex patchedDex, SparseIndexMap oldToPatchedIndexMap) {
        super(patchFile, oldDex, oldToPatchedIndexMap);
        if (patchedDex != null) {
            this.patchedClassDataTocSec = patchedDex.getTableOfContents().classDatas;
            this.patchedClassDataSec = patchedDex.openSection(this.patchedClassDataTocSec);
        }
    }

    /* access modifiers changed from: protected */
    public TableOfContents.Section getTocSection(Dex dex) {
        return dex.getTableOfContents().classDatas;
    }

    /* access modifiers changed from: protected */
    public ClassData nextItem(DexDataBuffer section) {
        return section.readClassData();
    }

    /* access modifiers changed from: protected */
    public int getItemSize(ClassData item) {
        return item.byteCountInDex();
    }

    /* access modifiers changed from: protected */
    public ClassData adjustItem(AbstractIndexMap indexMap, ClassData item) {
        return indexMap.adjust(item);
    }

    /* access modifiers changed from: protected */
    public int writePatchedItem(ClassData patchedItem) {
        this.patchedClassDataTocSec.size++;
        return this.patchedClassDataSec.writeClassData(patchedItem);
    }

    /* access modifiers changed from: protected */
    public void updateIndexOrOffset(SparseIndexMap sparseIndexMap, int oldIndex, int oldOffset, int newIndex, int newOffset) {
        if (oldOffset != newOffset) {
            sparseIndexMap.mapClassDataOffset(oldOffset, newOffset);
        }
    }

    /* access modifiers changed from: protected */
    public void markDeletedIndexOrOffset(SparseIndexMap sparseIndexMap, int deletedIndex, int deletedOffset) {
        sparseIndexMap.markClassDataDeleted(deletedOffset);
    }
}
